package org.homemotion.common.message;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractMessage implements Serializable {

	private static final long serialVersionUID = -3616620214662973147L;

	private UUID id = UUID.randomUUID();

	private long createdDT = System.currentTimeMillis();

	private String sender;

	private String channel;

	private boolean editable = true;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	private boolean completed;

	public AbstractMessage(String channel, String sender) {
		if (channel == null) {
			throw new IllegalArgumentException("channel required.");
		}
		if (sender == null) {
			throw new IllegalArgumentException("sender required.");
		}
		this.sender = sender;
		this.channel = channel;
	}

	public final String getChannel() {
		return channel;
	}

	public final String getFQEventName() {
		if (channel.isEmpty()) {
			return getClass().getSimpleName();
		}
		else {
			return channel + '/' + getClass().getSimpleName();
		}
	}

	public String getEventType() {
		return getClass().getSimpleName();
	}

	public final UUID getId() {
		return id;
	}

	public long getCreatedDT() {
		return this.createdDT;
	}

	public String getSender() {
		return sender;
	}

	public final boolean isCompleted() {
		return completed;
	}

	public final AbstractMessage markCompleted() {
		this.completed = true;
		return this;
	}

	/**
	 * @return the editable
	 */
	public final boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable
	 *            the editable to set
	 */
	public final AbstractMessage markReadOnly() {
		this.editable = false;
		return this;
	}

	/**
	 * @return the attributes
	 */
	public final Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public final void setAttributes(Map<String, Object> attributes) {
		if (!this.editable) {
			throw new IllegalStateException("Message not editable: " + this);
		}
		this.attributes = attributes;
	}

	public <T> AbstractMessage withAttribute(String key, T value) {
		this.attributes.put(key, value);
		return this;
	}

	public AbstractMessage withAttribute(Object o) {
		this.attributes.put(o.getClass().getName(), o);
		return this;
	}

	public final <T> T getAttribute(Class<T> type) {
		return (T) this.attributes.get(type.getName());
	}

	public final <T> T getAttribute(String key, Class<T> type) {
		return (T) this.attributes.get(key);
	}

	public final Class<? extends Object> getAttributeType(String key) {
		Object val = this.attributes.get(key);
		if (val != null) {
			return val.getClass();
		}
		return null;
	}

	public final Map<String, Class> getAttributeTypes() {
		Map<String, Class> types = new HashMap<String, Class>();
		for (Map.Entry<String, Object> en : this.attributes.entrySet()) {
			types.put(en.getKey(), en.getValue().getClass());
		}
		return types;
	}
	
	public final int getAttributeCount() {
        return this.attributes.size();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		return true;
	}

	@Override
	public String toString() {
		return getCreatedDT() + " " + getChannel() + " [" + getEventType()
				+ "] ";
	}
}
