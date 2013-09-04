package org.homemotion.building.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.building.Building;
import org.homemotion.building.BuildingManager;
import org.homemotion.building.Zone;
import org.homemotion.building.ZoneManager;
import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.Row;
import org.homemotion.common.system.Variable;
import org.homemotion.common.system.VariableManager;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;
import org.homemotion.macros.Macro;
import org.homemotion.macros.MacroManager;

@Singleton
public class ZoneManagerImpl extends AbstractConfiguredItemManager<Zone>
		implements
		ZoneManager {

	private static final String NODES_PREFIX = "Nodes:";

	private Map<String, Zone> roots = new ConcurrentHashMap<String, Zone>();

	@Inject
	private DeviceManager deviceManager;
	@Inject
	private BuildingManager buildingManager;
	@Inject
	private MacroManager macroManager;
	@Inject
	private VariableManager variableManager;

	public ZoneManagerImpl() {
		super(Zone.class, "tree/Nodes");
	}

	public Zone get(Building building) {
		return roots.get(building.getId());
	}

	@PostConstruct
	public void init() {
		load();
	}

	/**
	 * [Nodes:buildingID]
	 * 
	 * @id @parent @devices @macros @trigger @rooms @variables
	 */
	@Override
	protected void load(Configuration configuration, Map<String, Zone> items) {
		for (ConfigSection sect : configuration.getSections()) {
			if (sect.getId().startsWith(NODES_PREFIX)) {
				String buildingId = sect.getId().substring(
						NODES_PREFIX.length());
				Building b = buildingManager.get(buildingId);
				if (b == null) {
					logger.error("Failed to load tree for building '"
							+ buildingId + "': building not found.");
					continue;
				}
				Zone root = roots.get(buildingId);
				if (root == null) {
					root = new Zone("");
					roots.put(buildingId, root);
				}
				for (Row r : sect) {
					String id = r.get("id");
					String parent = r.get("parent");
					Zone zone = new Zone(id);
					Zone parentGroup = root;
					if (parent != null && !parent.isEmpty()) {
						parentGroup = root.withChildZone(parent);
					}
					parentGroup.withZone(zone);
					// Devices
					String[] deviceIds = extractIds(r.get("devices"));
					for (int i = 0; i < deviceIds.length; i++) {
						if (deviceIds[i].isEmpty()) {
							continue;
						}
						Device d = deviceManager.get(deviceIds[i]);
						if (d == null) {
							logger.error("Failed to add device '"
									+ deviceIds[i] + "' to group '" + id
									+ "': device not found.");
							continue;
						}
						zone.addItem(d);
					}
					// Macros
					String[] macroIds = extractIds(r.get("macros"));
					for (int i = 0; i < macroIds.length; i++) {
						if (macroIds[i].isEmpty()) {
							continue;
						}
						Macro m = macroManager.get(macroIds[i]);
						if (m == null) {
							logger.error("Failed to add macro '" + macroIds[i]
									+ "' to group '" + id
									+ "': macro not found.");
							continue;
						}
						zone.addItem(m);
					}
					// Rooms
					// String[] roomIds = extractIds(r.get("rooms"));
					// for (int i = 0; i < roomIds.length; i++) {
					// if (roomIds[i].isEmpty()) {
					// continue;
					// }
					// Room room = roomManager.get(roomIds[i]);
					// if (room == null) {
					// logger.error("Failed to add room '" + roomIds[i]
					// + "' to group '" + id
					// + "': trigger not found.");
					// continue;
					// }
					// group.addChild(r);
					// }
					// // Floors
					// String[] floorIds = extractIds(r.get("floors"));
					// for (int i = 0; i < floorIds.length; i++) {
					// if (floorIds[i].isEmpty()) {
					// continue;
					// }
					// Floor f = floorManager.get(floorIds[i]);
					// if (f == null) {
					// logger.error("Failed to add floor '" + floorIds[i]
					// + "' to group '" + id
					// + "': floor not found.");
					// continue;
					// }
					// group.addChild(f);
					// }
					// Variables
					String[] variableIds = extractIds(r.get("variables"));
					for (int i = 0; i < variableIds.length; i++) {
						if (variableIds[i].isEmpty()) {
							continue;
						}
						Variable v = variableManager.get(variableIds[i]);
						if (v == null) {
							logger.error("Failed to add variable '"
									+ variableIds[i] + "' to group '" + id
									+ "': variable not found.");
							continue;
						}
						zone.addItem(v);
					}
				}
			}
		}
		logger.info("Configured trees: " + this.roots);
	}

	protected String[] extractIds(String value) {
		if (value == null) {
			return new String[0];
		}
		String[] ids = value.split(",");
		for (int i = 0; i < ids.length; i++) {
			ids[i] = ids[i].trim();
		}
		return ids;
	}

}
