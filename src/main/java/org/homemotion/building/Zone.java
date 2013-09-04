package org.homemotion.building;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import org.homemotion.dao.Identifiable;
import org.homemotion.dao.spi.AbstractConfiguredItem;

public class Zone extends AbstractConfiguredItem implements Comparable<Zone>,
		Serializable {

	private static final long serialVersionUID = 6432853116958475275L;

	private Zone parent;

	private Set<Object> items = new HashSet<Object>();

	private List<Zone> childZones = new ArrayList<Zone>();

	public Zone(String id) {
		super(id, "building/Zones");
	}

	public boolean isRoot() {
		return this.parent == null;
	}

	public Zone getParent() {
		return parent;
	}

	public void setParent(Zone newparent) {
		this.parent = newparent;
	}

	public void addItem(Object item) {
		if (!items.contains(item)) {
			this.items.add(item);
		}
	}

	public Collection<Zone> getChildZones() {
		return Collections.unmodifiableList(childZones);
	}

	public Zone getChildZone(String name) {
		if (name.contains("/")) {
			Zone z = this;
			String[] names = name.split("/");
			for (String zn : names) {
				if (zn.isEmpty()) {
					continue;
				}
				z = z.getChildZone(zn);
				if (z == null) {
					return null;
				}
			}
			return z;
		}
		else {
			for (Zone z : childZones) {
				if (name.equals(z.getIdentifier())) {
					return z;
				}
			}
		}
		return null;
	}

	public <T> Collection<T> getItems(Class<T> type) {
		Set<T> result = new HashSet<T>();
		for (Object o : items) {
			if (type.isAssignableFrom(o.getClass())) {
				result.add((T) o);
			}
		}
		return result;
	}

	public <T extends Identifiable> T getItem(Class<T> type, String identifier) {
		for (T t : getItems(type)) {
			if (t.getIdentifier().equals(identifier)) {
				return t;
			}
		}
		return null;
	}

	@Transient
	public String getFullName() {
		if (parent == null) {
			return getIdentifier();
		}
		return parent.getFullName() + '.' + getIdentifier();
	}

	public Zone withItem(Object item) {
		if (item != null) {
			this.items.add(item);
		}
		return this;
	}

	public Zone withZone(Zone zone) {
		if (!childZones.contains(zone)) {
			childZones.add(zone);
			zone.setParent(this);
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tree: ");
		addString("", this, builder);
		return builder.toString();
	}

	private void addString(String inset, Zone node, StringBuilder builder) {
		builder.append('\n' + inset);
		buildNodeString(builder, node);
		for (Zone child : node.getChildZones()) {
			addString(inset + "  ", child, builder);
		}
	}

	private void buildNodeString(StringBuilder builder, Zone node) {
		builder.append(node.getId() + '/');
	}

	public int compareTo(Zone other) {
		if (other == null) {
			return -1;
		}
		return this.getIdentifier().compareTo(other.getIdentifier());
	}

	public Zone withChildZone(String path) {
		String[] paths = path.split("/");
		Zone current = this;
		for (String name : paths) {
			name = name.trim();
			if (name.isEmpty()) {
				continue;
			}
			Zone child = current.getChildZone(name);
			if (child == null) {
				child = new Zone(name);
				current.withZone(child);
				current = child;
			}
		}
		return current;
	}

}
