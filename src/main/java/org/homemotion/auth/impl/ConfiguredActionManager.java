package org.homemotion.auth.impl;

import java.util.Map;

import org.homemotion.auth.Action;
import org.homemotion.auth.ActionManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

public class ConfiguredActionManager extends AbstractConfiguredItemManager<Action>
		implements ActionManager {

	public ConfiguredActionManager() {
		super(Action.class, "security/Actions");
		load();
	}

	/**
	 * [Actions]
	 * 
	 * @id
	 */
	@Override
	protected void load(Configuration configuration, Map<String,Action> items) {
		ConfigSection section = configuration.getSection("actions");
		if(section!=null){
			for (Row row : section) {
				 String[] fields = row.getFields("id");
				 Action b = new Action(fields[0]);
				 items.put(b.getIdentifier(), b);
			}
		}
		logger.info("Configured Actions: " + this.items.keySet());
	}
}
