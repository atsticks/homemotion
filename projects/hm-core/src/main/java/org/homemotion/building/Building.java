package org.homemotion.building;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.homemotion.dao.spi.AbstractConfiguredItem;

public class Building extends AbstractConfiguredItem {

	private static final long serialVersionUID = 7099284420729363771L;

	private String icon;

	private String image;
	
	private List<Zone> zones = new ArrayList<Zone>();
	
	public Building(String id, String binding) {
		super(id, binding);
	}
	
	public final void addZone(Zone zone) {
		if(!this.zones.contains(zone)){
			zones.add(zone);
//			zone.setBuilding(this);
		}
	}

	public final Collection<Zone> getZones() {
		return zones;
	}
	
	public final void setIcon(String icon) {
		this.icon = icon;
	}

	public final String getIcon() {
		return icon;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
