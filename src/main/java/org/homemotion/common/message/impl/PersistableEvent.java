package org.homemotion.common.message.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.homemotion.dao.spi.AbstractEntity;
import org.homemotion.common.message.Severity;

@SuppressWarnings("unchecked")
@Entity
@Table(name = "HMEvent")
public class PersistableEvent extends AbstractEntity {
	private static final long serialVersionUID = -3616620214662973147L;

	private String binding = "N/A";
	@Column(nullable = false, length = 1024)
	private String message;
	private long timestamp;
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Severity severity;

	// JPA only
	public PersistableEvent() {
	}

	public PersistableEvent(String binding, long timestamp,
			Severity severity, String message) {
		if (binding == null) {
			throw new IllegalArgumentException("binding required.");
		}
		if (message == null) {
			throw new IllegalArgumentException("message required.");
		}
		if (severity == null) {
			throw new IllegalArgumentException("severity required.");
		}
		this.binding = binding;
		this.setTimestamp(timestamp);
		this.message = message;
		this.severity = severity;
	}

	public Severity getSeverity() {
		return this.severity;
	}

	public void setSeverity(Severity severity) {
		if (this.severity == null) {
			throw new IllegalArgumentException("Severity null.");
		}
		this.severity = severity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PersistableEvent [binding=" + binding + ", message=" + message + ", timestamp=" + timestamp
				+ ", severity=" + severity + "]";
	}

	public String getBinding() {
		return binding;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @param bindingId the bindingId to set
	 */
	public void setBinding(String binding) {
		this.binding = binding;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((binding == null) ? 0 : binding.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((severity == null) ? 0 : severity.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersistableEvent other = (PersistableEvent) obj;
		if (binding == null) {
			if (other.binding != null)
				return false;
		} else if (!binding.equals(other.binding))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (severity != other.severity)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

	
}
