package org.homemotion.devices;

import java.io.Serializable;

import org.homemotion.building.Building;
import org.homemotion.dao.spi.AbstractConfiguredItem;

public class Device extends AbstractConfiguredItem implements Serializable {

	private static final long serialVersionUID = 8244699449321737136L;

	private Building building;

	public Device(String id) {
		super(id, "devices/Devices");
	}
	
	public final void setBuilding(Building building) {
		this.building = building;
	}

	public final Building getBuilding() {
		return building;
	}

}
