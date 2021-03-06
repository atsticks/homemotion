package org.homemotion.common.message;

import org.homemotion.common.message.impl.PersistableEvent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.homemotion.dao.ItemManager;

public interface PersistableEventManager extends ItemManager<PersistableEvent, Long>{

	public List<PersistableEvent> getEventsOfSource(String source);
	public List<PersistableEvent> getEvents(int days);
	public List<PersistableEvent> getEvents(Severity severity);
	public List<PersistableEvent> getEvents(UUID itemID);
	public List<PersistableEvent> getEvents(UUID itemID, Severity severity);
	public List<PersistableEvent> getEvents(UUID itemID, Severity severity, int days);
	public List<PersistableEvent> getEvents(UUID itemID, Severity severity, Date date);
	public List<PersistableEvent> getEvents(UUID itemID, Severity severity, Date from, Date to);
	public List<PersistableEvent> getEvents(String group, String name);
	public List<PersistableEvent> getEvents(String group, String name, Date from, Date to);
	public List<PersistableEvent> getEvents(String binding);
	public List<PersistableEvent> getEvents(String binding, Date from, Date to);
	
	public void archiveEvents();
	
}