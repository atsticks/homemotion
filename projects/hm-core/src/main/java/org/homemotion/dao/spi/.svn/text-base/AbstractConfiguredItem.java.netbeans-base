package org.homemotion.dao.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.homemotion.adapt.AbstractAdaptable;
import org.homemotion.dao.Identifiable;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public abstract class AbstractConfiguredItem extends AbstractAdaptable
		implements Identifiable {

	private String id;

	private String binding;

	private Map<String, Attribute> attributes = new HashMap<String, Attribute>();

	public AbstractConfiguredItem(String id, String binding) {
		if (id == null) {
			throw new IllegalArgumentException("Id is required.");
		}
		if (binding == null) {
			throw new IllegalArgumentException("binding is required.");
		}
		this.id = id;
		this.binding = binding;
	}

	public String getId() {
		return id;
	}

	public String getBinding() {
		return binding;
	}

	public String getName(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".name");
		} catch (Exception e) {
			return getBasename() + '[' + id + ".name" + ']';
		}
	}

	public String getDescription(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".description");
		} catch (Exception e) {
			return getBasename() + '[' + id + ".description" + ']';
		}
	}
	
	public String getIcon128(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".icon32");
		} catch (Exception e) {
			return getIcon16(locale);
		}
	}
	
	public String getIcon64(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".icon32");
		} catch (Exception e) {
			return getIcon16(locale);
		}
	}
	
	public String getIcon32(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".icon32");
		} catch (Exception e) {
			return getIcon16(locale);
		}
	}
	
	public String getIcon16(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".icon16");
		} catch (Exception e) {
			return getIcon(locale);
		}
	}
	
	public String getIcon(Locale locale) {
		try {
			return ResourceBundle.getBundle(getBasename(), locale).getString(
					id + ".icon");
		} catch (Exception e) {
			return getBasename() + '[' + id + ".icon" + ']';
		}
	}

	protected String getBasename() {
		return "/cfg/" + binding;
	}

	public String getIdentifier() {
		return getClass().getName() + ':' + getId();
	}

	public <T> boolean defineAttribute(String name, Class<T> type) {
		return defineAttribute(name, type, null);
	}

	public final <T> boolean defineAttribute(String name, Class<T> type,
			T initialValue) {
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
		return getAttribute(name, type, null);
	}
	
	@java.beans.Transient
	public <T> T getAttribute(String name, Class<T> type, T defaultValue) {
		Attribute attr = this.attributes.get(name);
		if (attr == null) {
			return defaultValue;
		}
		return (T) attr.getValue();
	}

	public boolean isAttributeDefined(String name) {
		return this.attributes.containsKey(name);
	}

	public Set<String> getAttributeNames() {
		return Collections.unmodifiableSet(attributes.keySet());
	}

	public final <T> void setAttribute(T value) {
		setAttribute(value.getClass().getSimpleName(), value, true);
	}

	public final <T> void setAttribute(String key, T value) {
		setAttribute(key, value, true);
	}

	public final <T> void setAttribute(String key, T value,
			boolean triggerEvents) {
		if (value == null) {
			setAttribute(key, value, Object.class, triggerEvents);
		} else {
			setAttribute(key, value, (Class<T>) value.getClass(), triggerEvents);
		}
	}

	public final <T> void setAttribute(String key, T value, Class<T> type,
			boolean triggerEvents) {
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

	public Collection<Attribute> getAttributes() {
		List<Attribute> list = new ArrayList<Attribute>(
				this.attributes.values());
		Collections.sort(list);
		return list;
	}

	public void setAttributes(Collection<Attribute> attributes) {
		for (Iterator iterator = attributes.iterator(); iterator.hasNext();) {
			Attribute attribute = (Attribute) iterator.next();
			this.attributes.put(attribute.getName(), attribute);
		}
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

	public Attribute getAttribute(String name) {
		return this.attributes.get(name);
	}

	public void configure(String config) {
		if (config == null) {
			LoggerFactory.getLogger(getClass()).debug("No config provided to trigger: " + this);
		}
		Map<String, String> configMap = new HashMap<String, String>();
		String[] entries = config.split(";");
		for (int i = 0; i < entries.length; i++) {
			entries[i] = entries[i].trim();
			int index = entries[i].indexOf('=');
			if (index <= 0) {
				LoggerFactory.getLogger(getClass()).error("Invalid configuration entry found: " + entries[i]);
				continue;
			}
			String key = entries[i].substring(0, index).trim();
			String value = entries[i].substring(index + 1);
			configMap.put(key, value);
		}
		configureInternal(configMap);
	}

	protected void configureInternal(Map<String, String> configMap) {
		LoggerFactory.getLogger(getClass()).warn("Trigger configuration not implemented on " + this
				+ ", config was: " + configMap);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((binding == null) ? 0 : binding.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractConfiguredItem other = (AbstractConfiguredItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (binding == null) {
			if (other.binding != null)
				return false;
		} else if (!binding.equals(other.binding))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + "]";
	}

}
