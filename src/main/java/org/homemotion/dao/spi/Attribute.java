package org.homemotion.dao.spi;

import javax.persistence.Lob;
import javax.persistence.Transient;
import org.homemotion.dao.Identifiable;

import org.homemotion.common.system.Container;
import org.homemotion.common.system.ValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attribute implements Comparable<Attribute> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6737939471995608555L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Attribute.class);

	private Identifiable owner;
	
	private Class type;

	private String name;

	public Attribute(Identifiable owner) {
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
	}

	public Attribute(String name) {
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
		setName(name);
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
		this.name = name;

	}

	public <T> Attribute(Identifiable owner, String name, T value) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
		setName(name);
		setValue(value);
	}

	public <T> Attribute(Identifiable owner, String name, Class<T> type) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
		setName(name);
		setType(type);
	}

	public <T> Attribute(Identifiable owner, String name, Class<T> type,
			T value) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		if (owner == null) {
			throw new IllegalArgumentException("owner is null.");
		}
		this.owner = owner;
		setName(name);
		setType(type);
		setValue(value);
	}

	public String getName() {
		return this.name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> t) {
		if (t == null) {
			throw new IllegalArgumentException("Type may not be null.");
		}
		this.type = t;
	}

	public final void setValue(Object val) {
		if(val instanceof String && !getType().equals(String.class)){
			setStringValue((String)val);
		}
		else if (val != null && !getType().isAssignableFrom(val.getClass())) {
			throw new IllegalArgumentException("Value has invalid type "
					+ val.getClass() + ", required was " + getType() + ".");
		}
		Container.getInstance(ValueManager.class).setValue(owner, name, val);
	}

	@Lob
	public Object getValue() {
		return Container.getInstance(ValueManager.class).getValue(owner, name);
	}

	public String getStringValue() {
		return convertToString(getValue());
	}

	public void setStringValue(String value) {
		if (value != null && value.startsWith("<binary[")) {
			return;
		}
		setValue(convertFromString(value));
	}

	@Transient
	public boolean isSet() {
		return getValue() != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
		Object val = getValue();
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute other = (Attribute) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		Object val = getValue();
		if (val == null) {
			if (other.getValue() != null)
				return false;
		} else if (!val.equals(other.getValue()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceAttribute [getName()=" + getName() + ", type="
				+ type.getName() + ", value=" + getValue() + "]";
	}

	public int compareTo(Attribute o) {
		if (o == null) {
			return 1;
		}
		Attribute other = (Attribute) o;
		return getName().compareTo(other.getName());
	}

	private boolean isTextMappableType(String type) {
		if (type == null) {
			throw new IllegalArgumentException("Type param required.");
		}
		if (String.class.getName().equals(type)) {
			return true;
		}
		if (Boolean.class.getName().equals(type)) {
			return true;
		}
		if (Byte.class.getName().equals(type)) {
			return true;
		}
		if (Character.class.getName().equals(type)) {
			return true;
		}
		if (Short.class.getName().equals(type)) {
			return true;
		}
		if (Integer.class.getName().equals(type)) {
			return true;
		}
		if (Long.class.getName().equals(type)) {
			return true;
		}
		if (Float.class.getName().equals(type)) {
			return true;
		}
		if (Double.class.getName().equals(type)) {
			return true;
		}
		return false;
	}

	private String convertToString(Object object) {
		if (object == null) {
			return null;
		}
		if (String.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Boolean.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Byte.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Character.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Short.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Integer.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Long.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Float.class.equals(object.getClass())) {
			return object.toString();
		}
		if (Double.class.equals(object.getClass())) {
			return object.toString();
		}
		return "<binary[" + object.toString() + "]>";
	}

	private Object convertFromString(String input) {
		if (input == null) {
			return null;
		}
		if (String.class.equals(getType())) {
			return input;
		}
		if (Boolean.class.equals(getType())) {
			return Boolean.valueOf(input);
		}
		if (Byte.class.equals(getType())) {
			return Byte.valueOf(input);
		}
		if (Character.class.equals(getType())) {
			if (input.length() != 1) {
				throw new IllegalArgumentException(
						"Character can be only be of size one, but was '"
								+ input + "'.");
			}
			return Character.valueOf(input.charAt(0));
		}
		if (Short.class.equals(getType())) {
			return Short.valueOf(input);
		}
		if (Integer.class.equals(getType())) {
			return Integer.valueOf(input);
		}
		if (Long.class.equals(getType())) {
			return Long.valueOf(input);
		}
		if (Float.class.equals(getType())) {
			return Float.valueOf(input);
		}
		if (Double.class.equals(getType())) {
			return Double.valueOf(input);
		}
		throw new IllegalArgumentException(
				"Type is not mappable from text, but not serializable: "
						+ getType().getName());
	}

}
