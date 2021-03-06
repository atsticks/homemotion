package org.homemotion.macros.impl;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.Status;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.common.config.Row;
import org.homemotion.common.context.ItemContextManager;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;
import org.homemotion.macros.Macro;
import org.homemotion.macros.MacroEngine;
import org.homemotion.macros.MacroManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MacroManagerImpl extends AbstractConfiguredItemManager<Macro>
		implements MacroManager {

	private static final Logger LOG = LoggerFactory
			.getLogger(MacroManagerImpl.class);

	private Map<String, MacroEngine> macroEngines = new TreeMap<String, MacroEngine>();

	@Inject
	private ItemContextManager contextManager;

	@Inject
	public MacroManagerImpl(ConfigurationService configurationService) {
		super(Macro.class, "runtime/Macros", configurationService);
		load();
	}

	public Object execute(Macro macro) throws Exception {
		return executeInternal(macro);
	}

	private Object executeInternal(Macro macro) throws Exception {
		long start = System.currentTimeMillis();
		try {
			MacroEngine engine = getExecutionEngine(macro.getEngine());
			if (engine == null) {
				contextManager.getContext(macro).setAttribute(Status.ERROR);
				update(macro);
				throw new IllegalStateException("No such engine: "
						+ macro.getEngine());
			}
			Object result = engine.execute(macro.getScript());
			long duration = System.currentTimeMillis() - start;
			contextManager.getContext(macro).setAttribute(Status.OK);
			contextManager.getContext(macro).setAttribute("execDuration",
					duration);
			contextManager.getContext(macro).setAttribute("execMessage",
					"Last executed at " + start + ", duration=" + duration);
			return result;
		} catch (Exception e) {
			if (macro != null) {
				LOG.error("Error running macro: " + macro.getId(), e);
				contextManager.getContext(macro).setAttribute(Status.ERROR);
				contextManager.getContext(macro).setAttribute("execFailure", e,
						Throwable.class, false);
				contextManager.getContext(macro).setAttribute("execMessage",
						"Execution failed at " + start);
				update(macro);
			}
			throw e;
		}
	}

	public MacroEngine getExecutionEngine(String id) {
		return macroEngines.get(id);
	}

	public void addExecutionEngine(MacroEngine engine) {
		if (macroEngines.containsKey(engine.getName())) {
			throw new IllegalArgumentException("Engine is alread registered: "
					+ engine.getName());
		}
		macroEngines.put(engine.getName(), engine);
	}

	public String[] getAllExecutionEngineNames() {
		return this.macroEngines.keySet().toArray(new String[0]);
	}

	@Override
	protected void load(Configuration configuration, Map<String, Macro> items) {
		ConfigSection section = configuration.getSection("macro-engines");
		if (section != null) {
			for (Row row : section) {
				String className = row.get("class");
				try {
					MacroEngine me = (MacroEngine) Class.forName(className)
							.newInstance();
					macroEngines.put(me.getName(), me);
				} catch (Exception e) {
					logger.error("Error loading Macro Engine: " + className, e);
				}
			}
		}
		logger.info("Configured Macro Engines: " + this.macroEngines.keySet());
		section = configuration.getSection("macros");
		if (section != null) {
			for (Row row : section) {
				String id = row.get("id");
				String engine = row.get("engine");
				String script = row.get("script");
				try {
					Macro m = new Macro(id, engine);
					m.setScript(script);
					items.put(id, m);
				} catch (Exception e) {
					logger.error("Error loading Macro: " + id, e);
				}
			}
			logger.info("Configured Macros: " + this.items.keySet());
		}
	}

}
