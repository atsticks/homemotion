package org.homemotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ConfigSection {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigSection.class);
	private Map<String, ConfigValue> values = new HashMap<String, ConfigValue>();
	private List<String> orderedValues = new ArrayList<String>();
	private String sectionName;

	public ConfigSection(String sectionName) {
		if (sectionName == null) {
			throw new IllegalArgumentException("sectionName must not be null.");
		}
		this.sectionName = sectionName;
	}

	public String getName() {
		return sectionName;
	}

	public void readLine(String line) {
		if (line.trim().startsWith("#")) {
			return; // a comment
		}
		int index = line.indexOf('=');
		String key = null;
		String value = null;
		if (index < 0) {
			key = escape(line);
			value = key;
		} else {
			key = escape(line.substring(0, index));
			value = escape(line.substring(index + 1));
		}
		ConfigValue newValue = new ConfigValue(value);
		if(newValue.value!=null){
			orderedValues.add(newValue.value);
		}
		ConfigValue oldValue = values.put(key, newValue);
		if (oldValue != null) {
			if (oldValue.prio == newValue.prio) {
				if (oldValue.value.equals(newValue.value)) {
					return;
				}
				throw new IllegalArgumentException(
						"Encountered different values for '" + key
								+ "' with same prio. old='" + oldValue.value
								+ "', new='" + newValue.value + "'.");
			}
			if (oldValue.prio > newValue.prio) {
				// put back!
				values.put(key, oldValue);
			}
		}
	}

	private String escape(String line) {
		return line.replace("\\n", "\n").replace("\\r", "\r")
				.replace("\\t", "\t").replace("\\\\", "\\");
	}

	public String getValue(String key) {
		return getValue(key, null);
	}

	public String getValue(String key, String defaultValue) {
		ConfigValue value = values.get(key);
		if (value == null) {
			return defaultValue;
		}
		return value.value;
	}

	public String getValue(Class<?> clazz, String key) {
		return getValue(clazz.getName() + '.' + key);
	}

	public String getValue(Class<?> clazz, String key, String def) {
		return getValue(clazz.getName() + '.' + key, def);
	}

	public long getLong(String key) {
		return getLong(key, 0L);
	}

	public long getLong(String key, long defaultValue) {
		String value = getValue(key, null);
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				LOG.error("Problem converting to long: " + value, e);
			}
		}
		return defaultValue;
	}

	public int getInteger(String key) {
		return getInteger(key, 0);
	}

	public int getInteger(String key, int defaultValue) {
		String value = getValue(key, null);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				LOG.error("Problem converting to int: " + value, e);
			}
		}
		return defaultValue;
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key, null);
		if (value != null) {
			try {
				return Boolean.parseBoolean(value);
			} catch (Exception e) {
				LOG.error("Problem converting to long: " + value, e);
			}
		}
		return defaultValue;
	}

	public boolean isDefined(String key) {
		return this.values.containsKey(key);
	}

	public Map<String, String> getAsMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (Map.Entry<String, ConfigValue> entry : this.values.entrySet()) {
			map.put(entry.getKey(), entry.getValue().value);
		}
		return map;
	}
	
	public List<String> getAsList() {
		return Collections.unmodifiableList(this.orderedValues);
	}

	@Override
	public String toString() {
		return "ConfigSection [sectionName=" + sectionName + ", values="
				+ values + "]";
	}

	public <T> Class<T> getConfiguredClass(String key, Class<T> type,
			Class<T> defaultType) {
		String className = getValue(key, null);
		try {
			if (className == null) {
				return defaultType;
			}
			Class clazz = Class.forName(className);
			if (type.isAssignableFrom(clazz)) {
				return clazz;
			} else {
				LOG.error("Class of type: " + className
						+ " is not assignable to " + type);
			}
		} catch (Exception e) {
			LOG.error("Error loading class of type: " + className, e);
		}
		return defaultType;
	}

	public <T> T getConfiguredInstance(String key, Class<T> type,
			T defaultInstance) {
		String className = getValue(key, null);
		try {
			if (className == null) {
				return defaultInstance;
			}
			Class clazz = Class.forName(className);
			if (type.isAssignableFrom(clazz)) {
				return (T) clazz.newInstance();
			} else {
				LOG.error("Class of type: " + className
						+ " is not assignable to " + type);
			}
		} catch (Exception e) {
			LOG.error("Error loading class of type: " + className, e);
		}
		return defaultInstance;
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<Class<T>> getConfiguredClasses(Class<T> type) {
		Set<Class<T>> result = new HashSet<Class<T>>();
		for (ConfigValue value : this.values.values()) {
			try {
				Class clazz = Class.forName(value.value);
				if (type.isAssignableFrom(clazz)) {
					result.add(clazz);
				}
			} catch (Exception e) {
				LOG.error("Error loading class of type: " + value.value, e);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> getConfiguredInstances(Class<T> type) {
		Set<T> result = new HashSet<T>();
		for (ConfigValue value : this.values.values()) {
			try {
				Class clazz = Class.forName(value.value);
				if (type.isAssignableFrom(clazz)) {
					result.add((T) clazz.newInstance());
				}
			} catch (Exception e) {
				LOG.error("Error instantiating class of type: " + value.value,
						e);
			}
		}
		return result;
	}

	private static final class ConfigValue {
		int prio;
		String value;

		ConfigValue(String value) {
			int index = value.indexOf(':');
			if (index < 0) {
				this.prio = 0;
				this.value = value;
			} else {
				try{
					this.prio = Integer.parseInt(value.substring(0, index));
					this.value = value.substring(index + 1);
				}
				catch(NumberFormatException e){
					this.prio = 0;
					this.value = value;
				}
			}
		}
	}

	public List<String> getList(String key) {
		String value = getValue(key);
		if(value!=null){
			String[] values = value.split(",");
			return Arrays.asList(values);
		}
		return Collections.emptyList();
	}

}
