package org.homemotion.scheduler.impl;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;
import org.homemotion.scheduler.TriggerManager;
import org.homemotion.scheduler.spi.AbstractTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@SuppressWarnings("unchecked")
public class TriggerManagerImpl extends
		AbstractConfiguredItemManager<AbstractTrigger>
		implements TriggerManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TriggerManagerImpl.class);

	@Inject
	public TriggerManagerImpl(ConfigurationService configurationService) {
		super(AbstractTrigger.class, "modules/Triggers", configurationService);
		load();
	}

	@PostConstruct
	public void start() {
		LOGGER.info("Starting TriggerManager...");
		Collection<AbstractTrigger> triggers = getAllItems();
		LOGGER.info("-- Strarting triggers...");
		for (AbstractTrigger trigger : getAllItems()) {
			try {
				LOGGER.info("Trying to install trigger: " + trigger);
				trigger.install();
				LOGGER.info("Trigger: " + trigger + " installed successfully.");
			} catch (Exception e) {
				LOGGER.error("Error installing trigger: " + trigger, e);
			}
		}
		LOGGER.info("-- Triggers started.");
	}

	@PreDestroy
	public void stop() {
		LOGGER.info("Stopping TriggerManager...");
		LOGGER.info("-- Stopping triggers...");
		for (AbstractTrigger trigger : getAllItems()) {
			trigger.uninstall();
		}
		LOGGER.info("-- Triggers stopped.");
	}

	/**
	 * [triggerStrategies]
	 * 
	 * @id @class @config
	 * 
	 *     [triggers]
	 * @id @strategy @scripts
	 */
	@Override
	protected void load(Configuration configuration,
			Map<String, AbstractTrigger> items) {

		ConfigSection section = configuration.getSection("triggers");
		if (section != null) {
			for (Row row : section) {
				String id = row.get("id");
				if (id == null) {
					logger.warn("Id missing for trigger strategy, ignoring.");
					continue;
				}
				try {
					Constructor<AbstractTrigger> constr = (Constructor<AbstractTrigger>) Class
							.forName(row.get("class")).getConstructor(
									String.class);
					AbstractTrigger trigger = constr.newInstance(id);
					String config = row.get("config");
					trigger.configure(config);
					String[] scriptIds = null;
					try {
						scriptIds = row.get("scripts").split(",");

					} catch (Exception e) {
						logger.warn("Failed to initialize variable '" + id
								+ "'.", e);
					}
					trigger.addScripts(scriptIds);
					String synchVal = row.get("synchronous");
					if (synchVal != null) {
						trigger.setExecuteSynchronized(Boolean
								.parseBoolean(synchVal));
					}
					items.put(id, trigger);
				} catch (Exception e) {
					logger.warn("Failed to initialize variable '" + id + "'.",
							e);
				}
			}
		}
		logger.info("Configured Triggers: " + this.items);
	}

}
