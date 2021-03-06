package org.homemotion.common.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;

import org.homemotion.dao.Identifiable;
import org.homemotion.common.system.ValueManager;
import org.homemotion.common.system.ValueStore;

@Singleton
@Default
public class LocalValueManager implements ValueManager {

	private Map<String, ValueStore> stores = new ConcurrentHashMap<String, ValueStore>();

	@Override
	public Object getValue(Identifiable instance, String property) {
		ValueStore store = getStore(instance, property);
		if (store != null) {
			return store.getValue();
		}
		return null;
	}

	@Override
	public Object getValue(Identifiable instance, String property,
			Object defaultValue) {
		ValueStore store = getStore(instance, property);
		Object val = null;
		if (store != null) {
			val = store.getValue();
		}
		if (val == null) {
			return defaultValue;
		}
		return val;
	}

	@Override
	public void setValue(Identifiable instance, String property, Object value) {
		ValueStore store = getOrCreateStore(instance, property);
		store.setValue(value);
	}

	@Override
	public void addValue(Identifiable instance, String property,
			Object value) {
		ValueStore store = getOrCreateStore(instance, property);
		store.addValue(value);
	}

	@Override
	public void addValue(Identifiable instance, String property,
			Object value, long timestamp) {
		ValueStore store = getOrCreateStore(instance, property);
		store.addValue(value, timestamp);
	}

	@Override
	public Collection<ValueStore> getStores(Identifiable instance) {
		List<ValueStore> result = new ArrayList<ValueStore>();
		for (Entry<String, ValueStore> entry : stores.entrySet()) {
			if (entry.getKey().startsWith(
					instance.getClass().getName() + '-'
							+ instance.getIdentifier() + '-')) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	@Override
	public ValueStore getStorage(Identifiable instance, String property) {
		// TODO Auto-generated method stub
		return null;
	}

	private ValueStore getStore(Identifiable instance, String property) {
		if (instance != null) {
			return stores.get(instance.getClass().getName() + '-'
					+ instance.getIdentifier() + '-' + property);
		}
		return null;
	}

	private ValueStore getOrCreateStore(Identifiable instance, String property) {
		if (instance == null) {
			throw new IllegalArgumentException("Instance required.");
		}
		ValueStore store = null;
		String storeId = instance.getClass().getName() + '-'
				+ instance.getIdentifier() + '-' + property;
		store = stores.get(storeId);
		if (store == null) {
			store = new LocalValueStore(storeId);
			stores.put(storeId, store);
		}
		return store;
	}

}
