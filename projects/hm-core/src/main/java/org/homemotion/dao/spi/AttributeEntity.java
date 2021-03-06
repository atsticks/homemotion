package org.homemotion.dao.spi;

import org.homemotion.dao.spi.AbstractNamedItem;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity
@Table(name="HMAttribute")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AttributeEntity extends AbstractNamedItem implements
		Comparable<AttributeEntity> {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -6737939471995608555L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AttributeEntity.class);

	@Column(nullable = false)
	private String typeName;

        @Transient
	private Class type;

        @Lob
	private Serializable value;

	@Column(nullable = false)
	private boolean serialized = false;

	public AttributeEntity() {

	}

	public AttributeEntity(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		setName(name);
	}

	public <T extends Serializable> AttributeEntity(String name, T value) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		setName(name);
		setValue(value);
	}

	public <T extends Serializable> AttributeEntity(String name, Class<T> type) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		setName(name);
		setType(type);
	}

	public <T extends Serializable> AttributeEntity(String name, Class<T> type,
			T value) {
		if (name == null) {
			throw new IllegalArgumentException("Name is null.");
		}
		setName(name);
		setType(type);
		setValue(value);
	}

		public Class<Serializable> getType() {
		if (type == null) {
			try {
				this.type = Class.forName(this.typeName,
						true, Thread.currentThread().getContextClassLoader());
			} catch (ClassNotFoundException e) {
				LOGGER.error("Error loading type for device attribute: "
						+ typeName, e);
			}
		}
		return type;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
		if (!isTextMappableType(typeName)) {
			this.serialized = true;
		}
	}

	public <T extends Serializable> void setType(Class<T> t) {
		if (t == null) {
			throw new IllegalArgumentException("Type may not be null.");
		}
		setTypeName(t.getName());
		this.type = t;
	}

	public final void setValue(Serializable val) {
		if (val != null && !getType().isAssignableFrom(val.getClass())) {
			throw new IllegalArgumentException("Value has invalid type "
					+ val.getClass() + ", required was " + getType() + ".");
		}
		this.value = (Serializable) val;
	}

		public Serializable getValue() {
		return this.value;
	}

	public String getStringValue() {
		return convertToString(this.value);
	}

	public void setStringValue(String value) {
		if (value != null && value.startsWith("<binary[")) {
			return;
		}
		this.value = convertFromString(value);
	}

	@Transient
	public boolean isSet() {
		return this.value != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (serialized ? 1231 : 1237);
		result = prime * result
				+ ((typeName == null) ? 0 : typeName.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		AttributeEntity other = (AttributeEntity) obj;
		if (serialized != other.serialized)
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceAttribute [getName()=" + getName() + ", typeName="
				+ typeName + ", serialized=" + serialized + ", value=" + value
				+ "]";
	}

	public int compareTo(AttributeEntity o) {
		if (o == null) {
			return 1;
		}
		AttributeEntity other = (AttributeEntity) o;
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

	private Serializable convertFromString(String input) {
		if (input == null) {
			return null;
		}
		if (String.class.getName().equals(getTypeName())) {
			return input;
		}
		if (Boolean.class.getName().equals(getTypeName())) {
			return Boolean.valueOf(input);
		}
		if (Byte.class.getName().equals(getTypeName())) {
			return Byte.valueOf(input);
		}
		if (Character.class.getName().equals(getTypeName())) {
			if (input.length() != 1) {
				throw new IllegalArgumentException(
						"Character can be only be of size one, but was '"
								+ input + "'.");
			}
			return Character.valueOf(input.charAt(0));
		}
		if (Short.class.getName().equals(getTypeName())) {
			return Short.valueOf(input);
		}
		if (Integer.class.getName().equals(getTypeName())) {
			return Integer.valueOf(input);
		}
		if (Long.class.getName().equals(getTypeName())) {
			return Long.valueOf(input);
		}
		if (Float.class.getName().equals(getTypeName())) {
			return Float.valueOf(input);
		}
		if (Double.class.getName().equals(getTypeName())) {
			return Double.valueOf(input);
		}
		throw new IllegalArgumentException(
				"Type is not mappable from text, but not serializable: "
						+ getTypeName());
	}

}
