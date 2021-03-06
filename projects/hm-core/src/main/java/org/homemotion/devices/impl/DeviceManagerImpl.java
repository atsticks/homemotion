package org.homemotion.devices.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.building.Building;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;

@Singleton
@SuppressWarnings({ "unchecked" })
public class DeviceManagerImpl extends AbstractConfiguredItemManager<Device>
		implements DeviceManager {

	@Inject
	public DeviceManagerImpl(ConfigurationService configurationService) {
		super(Device.class, "runtime/Devices.conf", configurationService);
	}

	public Collection<Device> getAllItems(Building building) {
		if (building == null) {
			return getAllItems();
		}
		List<Device> result = new ArrayList<Device>();
		for (Device room : getAllItems()) {
			if (room.getBuilding() != null
					&& room.getBuilding().equals(building)) {
				result.add(room);
			}
		}
		return result;
	}

	@Override
	protected void load(Configuration configuration, Map<String, Device> items) {
		// TODO Auto-generated method stub
		logger.info("Configured Devices (TODO): " + this.items.keySet());
	}

}
