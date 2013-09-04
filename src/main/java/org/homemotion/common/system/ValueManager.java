package org.homemotion.common.system;

import java.io.Serializable;
import java.util.Collection;

import org.homemotion.dao.Identifiable;

public interface ValueManager {

	
	public Object getValue(Identifiable instance, String property);
	public Object getValue(Identifiable instance, String property, Object defaultValue);
	
	public void setValue(Identifiable instance, String property, Object value);
	public void addValue(Identifiable instance, String property, Object value);
	public void addValue(Identifiable instance, String property, Object value, long timestamp);

	public Collection<ValueStore> getStores(Identifiable instance);
	public ValueStore getStorage(Identifiable instance, String property);
	
}
