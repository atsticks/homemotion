package org.homemotion.common.system.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;

import org.homemotion.common.system.ValueStore;

@Singleton
@Default
public class LocalValueStore implements ValueStore {

	private String ownerId;
	private List<ValueEntry> values = Collections
			.synchronizedList(new ArrayList<ValueEntry>());

	public LocalValueStore(String ownerId) {
		if (ownerId == null) {
			throw new IllegalArgumentException("ownerId required.");
		}
		this.ownerId = ownerId;
	}

	@Override
	public String getOwnerId() {
		return ownerId;
	}

	@Override
	public Object getValue() {
		synchronized(values){
			if(!values.isEmpty()){
				return values.get(values.size()-1).getValue();
			}
		}
		return null;
	}

	@Override
	public void setValue(Object value) {
		synchronized(values){
			ValueEntry e = new ValueEntry(value, "TODO");
			values.add(e);
			Collections.sort(values);
		}
	}

	@Override
	public void addValue(Object value) {
		synchronized(values){
			ValueEntry e = new ValueEntry(value, "TODO");
			values.add(e);
			Collections.sort(values);
		}

	}

	@Override
	public void addValue(Object value, long timestamp) {
		synchronized(values){
			ValueEntry e = new ValueEntry(value, timestamp, "TODO");
			values.add(e);
			Collections.sort(values);
		}

	}

	@Override
	public Collection<ValueEntry> getValues() {
		synchronized(values){
			return new ArrayList(values);
		}
	}

	@Override
	public Collection<ValueEntry> getValues(long from) {
		List<ValueEntry> result = new ArrayList<ValueEntry>();
		synchronized(values){
			for(ValueEntry e: values){
				if(e.getTimestamp()>=from){
					result.add(e);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<ValueEntry> getValues(long from, long until) {
		List<ValueEntry> result = new ArrayList<ValueEntry>();
		synchronized(values){
			for(ValueEntry e: values){
				if(e.getTimestamp()>=from && e.getTimestamp()<=until){
					result.add(e);
				}
			}
		}
		return result;
	}

}
