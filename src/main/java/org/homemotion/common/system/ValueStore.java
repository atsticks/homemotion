package org.homemotion.common.system;

import java.io.Serializable;
import java.util.Collection;

public interface ValueStore {

	public String getOwnerId();

	public Object getValue();

	public void setValue(Object value);

	public void addValue(Object value);

	public void addValue(Object value, long timestamp);

	public Collection<ValueEntry> getValues();

	public Collection<ValueEntry> getValues(long from);

	public Collection<ValueEntry> getValues(long from, long until);

	public final static class ValueEntry implements Serializable,
			Comparable<ValueEntry> {
		private static final long serialVersionUID = -5216507663988980657L;
		private static final String DEFAULT = "default";
		private long timestamp;
		private String user;
		private String valueType = DEFAULT;
		private Object value;

		public ValueEntry(Object value, String user) {
			this(null, value, System.currentTimeMillis(), user);
		}

		public ValueEntry(Object value, long timestamp, String user) {
			this(null, value, timestamp, user);
		}

		public ValueEntry(String valueType, Object value, long timestamp,
				String user) {
			if (user == null) {
				throw new IllegalArgumentException("User required.");
			}
			if (valueType != null) {
				this.valueType = valueType;
			}
			this.value = value;
			this.user = user;
			this.timestamp = timestamp;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public String getValueType() {
			return valueType;
		}

		public Object getValue() {
			return value;
		}

		public String getUser() {
			return user;
		}

		@Override
		public int compareTo(ValueEntry o) {
			if (o == null) {
				return -1;
			}
			int compare = (int) (getTimestamp() - o.getTimestamp());
			if (compare == 0) {
				compare = getValueType().compareTo(o.getValueType());
			}
			return compare;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			result = prime * result
					+ ((valueType == null) ? 0 : valueType.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ValueEntry other = (ValueEntry) obj;
			if (timestamp != other.timestamp)
				return false;
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			if (valueType == null) {
				if (other.valueType != null)
					return false;
			} else if (!valueType.equals(other.valueType))
				return false;
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ValueEntry [valueType=" + valueType + ", value=" + value
					+ ", timestamp=" + timestamp + ", user=" + user + "]";
		}

	}

}
