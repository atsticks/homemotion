package org.homemotion.dao.spi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import org.homemotion.dao.Attributable;

import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class AbstractAttributableItem extends AbstractEntity implements
		Attributable {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapKey(name = "name")
	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	public <T extends Serializable> boolean defineAttribute(String name,
			Class<T> type) {
		return defineAttribute(name, type, null);
	}

	public final <T extends Serializable> boolean defineAttribute(String name,
			Class<T> type, T initialValue) {
		Attribute attr = this.attributes.get(name);
		if (attr == null) {
			attr = new Attribute(this, name, type, initialValue);
			this.attributes.put(name, attr);
			return true;
		} else {
			if (!attr.getType().isAssignableFrom(type)) {
				return false;
			}
			Object curValue = attr.getValue();
			if (curValue == null && initialValue != null) {
				attr.setValue(initialValue);
				return true;
			}
		}
		return false;
	}

	public Attribute removeAttribute(String name) {
		return this.attributes.remove(name);
	}

	@java.beans.Transient
	public Class<?> getAttributeType(String name) {
		Attribute attr = this.attributes.get(name);
		if (attr == null) {
			return null;
		}
		try {
			return attr.getType();
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error(
					"Error accessing class of attribute type: " + attr, e);
			return null;
		}
	}

	@java.beans.Transient
	public <T> T getAttribute(String name, Class<T> type) {
		Attribute attr = this.attributes.get(name);
		if (attr == null) {
			return null;
		}
		return (T) attr.getValue();
	}

	
	public boolean isAttributeDefined(String name) {
		return this.attributes.containsKey(name);
	}

	public Set<String> getAttributeNames() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	public final <T extends Serializable> void setAttribute(T value) {
		setAttribute(value.getClass().getSimpleName(), value, true);
	}
	
	public final <T extends Serializable> void setAttribute(String key, T value) {
		setAttribute(key, value, true);
	}

	public final <T extends Serializable> void setAttribute(String key,
			T value, boolean triggerEvents) {
		if (value == null) {
			setAttribute(key, value, Serializable.class, triggerEvents);
		} else {
			setAttribute(key, value, (Class<Serializable>) value.getClass(),
					triggerEvents);
		}
	}

	public final <T extends Serializable> void setAttribute(String key,
			T value, Class<T> type, boolean triggerEvents) {
		Attribute attribute = this.attributes.get(key);
		if (attribute == null) {
			attribute = new Attribute(this, key, type, value);
			this.attributes.put(key, attribute);
		} else {
			attribute.setValue(value);
		}
	}

	public void clearAttributes() {
		this.attributes.clear();
	}

	public List<Attribute> getAttributes() {
		List<Attribute> list = new ArrayList<Attribute>(
				this.attributes.values());
		Collections.sort(list);
		return list;
	}

	public void defineBooleanAttribute(String name) {
		defineBooleanAttribute(name, false);
	}

	public void defineBooleanAttribute(String name, boolean initialValue) {
		defineAttribute(name, Boolean.class, Boolean.valueOf(initialValue));
	}

	public void setBooleanAttribute(String name, boolean value) {
		setAttribute(name, Boolean.valueOf(value));
	}

	public boolean getBooleanAttribute(String name) {
		Boolean bool = getAttribute(name, Boolean.class);
		if (bool != null) {
			return bool.booleanValue();
		}
		return false;
	}

	public void defineIntegerAttribute(String name) {
		defineIntegerAttribute(name, 0);
	}

	public void defineIntegerAttribute(String name, int initialValue) {
		defineAttribute(name, Integer.class, Integer.valueOf(initialValue));
	}

	public void setIntegerAttribute(String name, int value) {
		setAttribute(name, Integer.valueOf(value));
	}

	public int getIntegerAttribute(String name) {
		Integer val = getAttribute(name, Integer.class);
		if (val != null) {
			return val.intValue();
		}
		return 0;
	}

	public void defineLongAttribute(String name) {
		defineLongAttribute(name, 0);
	}

	public void defineLongAttribute(String name, long initialValue) {
		defineAttribute(name, Long.class, Long.valueOf(initialValue));
	}

	public void setLongAttribute(String name, long value) {
		setAttribute(name, Long.valueOf(value));
	}

	public long getLongAttribute(String name) {
		Long val = getAttribute(name, Long.class);
		if (val != null) {
			return val.longValue();
		}
		return 0;
	}

	public void defineFloatAttribute(String name) {
		defineFloatAttribute(name, 0);
	}

	public void defineFloatAttribute(String name, float initialValue) {
		defineAttribute(name, Float.class, Float.valueOf(initialValue));
	}

	public void setFloatAttribute(String name, float value) {
		setAttribute(name, Float.valueOf(value));
	}

	public float getFloatAttribute(String name) {
		Float val = getAttribute(name, Float.class);
		if (val != null) {
			return val.floatValue();
		}
		return 0;
	}

	public void defineDoubleAttribute(String name) {
		defineFloatAttribute(name, 0);
	}

	public void defineDoubleAttribute(String name, double initialValue) {
		defineAttribute(name, Double.class, Double.valueOf(initialValue));
	}

	public void setDoubleAttribute(String name, double value) {
		setAttribute(name, Double.valueOf(value));
	}

	public double getDoubleAttribute(String name) {
		Double val = getAttribute(name, Double.class);
		if (val != null) {
			return val.doubleValue();
		}
		return 0;
	}

	public void defineShortAttribute(String name) {
		defineShortAttribute(name, (short) 0);
	}

	public void defineShortAttribute(String name, short initialValue) {
		defineAttribute(name, Short.class, Short.valueOf(initialValue));
	}

	public void setShortAttribute(String name, short value) {
		setAttribute(name, Short.valueOf(value));
	}

	public short getShortAttribute(String name) {
		Short val = getAttribute(name, Short.class);
		if (val != null) {
			return val.shortValue();
		}
		return 0;
	}

	public void defineByteAttribute(String name) {
		defineByteAttribute(name, (byte) 0);
	}

	public void defineByteAttribute(String name, byte initialValue) {
		defineAttribute(name, Byte.class, Byte.valueOf(initialValue));
	}

	public void setByteAttribute(String name, byte value) {
		setAttribute(name, Byte.valueOf(value));
	}

	public byte getByteAttribute(String name) {
		Byte val = getAttribute(name, Byte.class);
		if (val != null) {
			return val.byteValue();
		}
		return 0;
	}

	@Override
	public Attribute getAttribute(String name) {
		return this.attributes.get(name);
	}

}
