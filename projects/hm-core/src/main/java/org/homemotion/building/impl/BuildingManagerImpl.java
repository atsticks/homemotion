package org.homemotion.building.impl;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.building.Building;
import org.homemotion.building.BuildingManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.Row;
import org.homemotion.dao.spi.AbstractConfiguredItem;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

@Singleton
public class BuildingManagerImpl extends AbstractConfiguredItemManager<Building>
		implements BuildingManager {

	@Inject
	public BuildingManagerImpl() {
		super(Building.class, "buildings/Buildings");
		load();
	}

	/**
	 * [Buildings]
	 * 
	 * @id @name @description @attributes
	 */
	@Override
	protected void load(Configuration configuration, Map<String, Building> items) {
		ConfigSection section = configuration.getSection("buildings");
		if (section != null) {
			for (Row row : section) {
				String[] fields = row.getFields("id", "attributes");
				Building b = new Building(fields[0], getBinding());
				setAttributes(b, fields[1]);
				items.put(fields[0], b);
			}
		}
		System.out.println("Buildings loaded: " + items);
	}

	protected void setAttributes(AbstractConfiguredItem b, String attributes) {
		// TODO parse all attributes
	}

	public static void main(String[] args) {
		new BuildingManagerImpl();
	}

}
