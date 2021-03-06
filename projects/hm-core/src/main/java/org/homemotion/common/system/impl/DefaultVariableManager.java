package org.homemotion.common.system.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.common.config.ConfigSection;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.common.config.Row;
import org.homemotion.common.system.Variable;
import org.homemotion.common.system.VariableManager;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;
import org.slf4j.LoggerFactory;

@Singleton
public class DefaultVariableManager extends AbstractConfiguredItemManager<Variable>
		implements VariableManager {

	@Inject
	public DefaultVariableManager(ConfigurationService configurationService) {
		super(Variable.class, "runtime/Variables", configurationService);
	}

	public boolean define(String name, Class<?> type) {
		return define(name, type, null);
	}

	public final <T> boolean define(String name, Class<T> type, T initialValue) {
		return define(name, type, initialValue, false);
	}

	public final <T> boolean define(String name, Class<T> type, T initialValue,
			boolean readOnly) {
		Variable attr = this.get(name);
		if (attr == null) {
			attr = new Variable(name, type, initialValue);
			attr.setReadOnly(readOnly);
			this.items.put(attr.getName(), attr);
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

	@java.beans.Transient
	public Class<?> getVariableType(String name) {
		Variable attr = get(name);
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

	public <T> T getValue(String name, Class<T> type) {
		Variable attr = get(name);
		if (attr == null) {
			return null;
		}
		return (T) attr.getValue();
	}

	public Object getValue(String name) {
		Variable attr = get(name);
		if (attr == null) {
			return null;
		}
		return attr.getValue();
	}

	public boolean isVariableDefined(String name) {
		return get(name) != null;
	}

	public Set<String> getVariableNames() {
		return Collections.unmodifiableSet(getIdentifiers());
	}

	public final void setValue(String key, Object value) {
		setValue(key, value, true);
	}

	public final void setValue(String key, Object value, boolean triggerEvents) {
		Variable variable = get(key);
		if (variable == null) {
			throw new IllegalArgumentException("Undefined variable: " + key);
		} else {
			variable.setValue(value);
		}
	}

	public final <T> void setValue(String key, T value, Class<T> type,
			boolean triggerEvents) {
	}

	public void defineBoolean(String name) {
		defineBoolean(name, false);
	}

	public void defineBoolean(String name, boolean initialValue) {
		define(name, Boolean.class, Boolean.valueOf(initialValue));
	}

	public void setValue(String name, boolean value) {
		setValue(name, Boolean.valueOf(value));
	}

	public boolean getBoolean(String name) {
		Boolean bool = getValue(name, Boolean.class);
		if (bool != null) {
			return bool.booleanValue();
		}
		return false;
	}

	public void defineInteger(String name) {
		defineInteger(name, 0);
	}

	public void defineInteger(String name, int initialValue) {
		define(name, Integer.class, Integer.valueOf(initialValue));
	}

	public void setValue(String name, int value) {
		setValue(name, Integer.valueOf(value));
	}

	public int getIntegerValue(String name) {
		Integer val = getValue(name, Integer.class);
		if (val != null) {
			return val.intValue();
		}
		return 0;
	}

	public void defineLong(String name) {
		defineLong(name, 0);
	}

	public void defineLong(String name, long initialValue) {
		define(name, Long.class, Long.valueOf(initialValue));
	}

	public void setValue(String name, long value) {
		setValue(name, Long.valueOf(value));
	}

	public long getLong(String name) {
		Long val = getValue(name, Long.class);
		if (val != null) {
			return val.longValue();
		}
		return 0;
	}

	public void defineFloat(String name) {
		defineFloat(name, 0);
	}

	public void defineFloat(String name, float initialValue) {
		define(name, Float.class, Float.valueOf(initialValue));
	}

	public void setValue(String name, float value) {
		setValue(name, Float.valueOf(value));
	}

	public float getFloatValue(String name) {
		Float val = getValue(name, Float.class);
		if (val != null) {
			return val.floatValue();
		}
		return 0;
	}

	public void defineDouble(String name) {
		defineDouble(name, 0);
	}

	public void defineDouble(String name, double initialValue) {
		define(name, Double.class, Double.valueOf(initialValue));
	}

	public void setValue(String name, double value) {
		setValue(name, Double.valueOf(value));
	}

	public double getDoubleValue(String name) {
		Double val = getValue(name, Double.class);
		if (val != null) {
			return val.doubleValue();
		}
		return 0;
	}

	public void defineShort(String name) {
		defineShort(name, (short) 0);
	}

	public void defineShort(String name, short initialValue) {
		define(name, Short.class, Short.valueOf(initialValue));
	}

	public void setShort(String name, short value) {
		setValue(name, Short.valueOf(value));
	}

	public short getShortValue(String name) {
		Short val = getValue(name, Short.class);
		if (val != null) {
			return val.shortValue();
		}
		return 0;
	}

	public void defineByte(String name) {
		defineByte(name, (byte) 0);
	}

	public void defineByte(String name, byte initialValue) {
		define(name, Byte.class, Byte.valueOf(initialValue));
	}

	public void setValue(String name, byte value) {
		setValue(name, Byte.valueOf(value));
	}

	public byte getByteValue(String name) {
		Byte val = getValue(name, Byte.class);
		if (val != null) {
			return val.byteValue();
		}
		return 0;
	}

	/**
	 * @id @type @default @readonly
	 */
	@Override
	protected void load(Configuration configuration, Map<String, Variable> items) {
		ConfigSection section = configuration.getSection("variables");
		if (section != null) {
			for (Row row : section) {
				String id = row.get("id");
				if (id == null) {
					logger.warn("Id missing for variable, ignoring.");
					continue;
				}
				try {
					Class varType = getType(row.get("type"));
					Object defaultValue = getDefault(varType,
							row.get("default"));
					boolean readOnly = Boolean.parseBoolean(row
							.get("readonly"));
					define(id, varType, defaultValue, readOnly);
				} catch (Exception e) {
					logger.warn("Failed to initialize variable '" + id + "'.",
							e);
				}

			}
			logger.info("Variables initialized: " + getVariableNames());
		}
	}

	private <T> T getDefault(Class<T> type, String value)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		if (Float.class.equals(type)) {
			return (T) Float.valueOf(value);
		} else if (Double.class.equals(type)) {
			return (T) Double.valueOf(value);
		} else if (Byte.class.equals(type)) {
			return (T) Byte.valueOf(value);
		} else if (Short.class.equals(type)) {
			return (T) Short.valueOf(value);
		} else if (Boolean.class.equals(type)) {
			return (T) Boolean.valueOf(value);
		} else if (Integer.class.equals(type)) {
			return (T) Integer.valueOf(value);
		} else if (Long.class.equals(type)) {
			return (T) Long.valueOf(value);
		} else if (String.class.equals(type)) {
			return (T) value;
		} else {
			Constructor<T> constr = type.getDeclaredConstructor(String.class);
			if (!constr.isAccessible()) {
				constr.setAccessible(true);
			}
			return (T) constr.newInstance(value);
		}
	}

	private Class getType(String type) throws ClassNotFoundException {
		if ("float".equalsIgnoreCase(type)) {
			return Float.class;
		} else if ("double".equalsIgnoreCase(type)
				|| "number".equalsIgnoreCase(type)) {
			return Double.class;
		} else if ("byte".equalsIgnoreCase(type)) {
			return Byte.class;
		} else if ("short".equalsIgnoreCase(type)) {
			return Short.class;
		} else if ("boolean".equalsIgnoreCase(type)) {
			return Boolean.class;
		} else if ("int".equalsIgnoreCase(type)
				|| "integer".equalsIgnoreCase(type)) {
			return Integer.class;
		} else if ("long".equalsIgnoreCase(type)) {
			return Long.class;
		} else if ("string".equalsIgnoreCase(type)
				|| "text".equalsIgnoreCase(type)) {
			return String.class;
		} else {
			return Class.forName(type);
		}
	}

}
