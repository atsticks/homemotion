package org.homemotion.common.message.impl;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import javax.enterprise.inject.Default;
//import javax.inject.Singleton;
//import javax.persistence.Query;
//
//import org.apache.log4j.Logger;
//import org.homemotion.dao.AbstractJPAItemManagerImpl;
//import org.homemotion.events.BindingType;
//import org.homemotion.events.PersistableEvent;
//import org.homemotion.events.PersistableEventManager;
//import org.homemotion.events.Severity;
//
//@SuppressWarnings("unchecked")
//@Singleton
//@Default
//public class PersistableEventManagerImpl extends
//		AbstractJPAItemManagerImpl<PersistableEvent> implements
//		PersistableEventManager {
//
//	private static final Logger LOGGER = Logger
//			.getLogger(PersistableEventManagerImpl.class);
//
//	public static final int DEFAULT_DAYS = -5;
//
//	@Override
//	public void create(PersistableEvent item) {
//		super.create(item);
//	}
//
//	@Override
//	public PersistableEvent update(PersistableEvent item) {
//		return super.update(item);
//	}
//
//	public List<PersistableEvent> getEventsOfSource(final String source) {
//		Query q = getEntityManager().createNamedQuery("Event.find4OwnerType");
//		q.setParameter("ownerType", source);
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(UUID itemID, Severity severity,
//			Date date) {
//		return getEvents(itemID, severity, date, date);
//	}
//
//	public List<PersistableEvent> getEvents(final UUID ownerID,
//			final Severity severity, final Date from, final Date to) {
//		Query q = getEntityManager().createNamedQuery(
//				"Event.find4ItemAndSeverityAndRange");
//		q.setParameter("id", ownerID);
//		q.setParameter("severity", severity);
//		q.setParameter("from", from.getTime());
//		q.setParameter("to", System.currentTimeMillis());
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(int days) {
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, -days);
//		final Date ts = cal.getTime();
//		Query q = getEntityManager().createNamedQuery("Event.find4Days");
//		q.setParameter("startDT", ts.getTime());
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(final Severity severity) {
//		Query q = getEntityManager().createNamedQuery("Event.find4Severity");
//		q.setParameter("severity", severity);
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(final UUID itemID,
//			final Severity severity) {
//		Query q = getEntityManager().createNamedQuery(
//				"Event.find4ItemAndSeverity");
//		q.setParameter("id", itemID);
//		q.setParameter("severity", severity);
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(final UUID itemID,
//			final Severity severity, int days) {
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_YEAR, -days);
//		final Date ts = cal.getTime();
//		Query q = getEntityManager().createNamedQuery(
//				"Event.find4OwnerAndSeverity");
//		q.setParameter("id", itemID);
//		q.setParameter("severity", severity);
//		q.setParameter("startDT", ts.getTime());
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(final UUID itemID) {
//		Query q = getEntityManager().createNamedQuery("Event.find4Item");
//		q.setParameter("id", itemID);
//		return q.getResultList();
//	}
//
//	public void archiveEvents() {
//		LOGGER.warn("Archiving of events is not yet implemented.");
//	}
//
//	public List<PersistableEvent> getEvents(final BindingType evtDef) {
//		return getEvents(evtDef.getBindingId(), evtDef.getItemId());
//	}
//
//	@Override
//	protected Class<PersistableEvent> getItemClass() {
//		return PersistableEvent.class;
//	}
//
//	public List<PersistableEvent> getEvents(final String group,
//			final String name) {
//		Query q = getEntityManager().createNamedQuery("Event.find4EventClass");
//		q.setParameter("group", group);
//		q.setParameter("name", name);
//		return q.getResultList();
//	}
//
//	public List<PersistableEvent> getEvents(final BindingType def,
//			final Date from, final Date to) {
//		return getEvents(def.getBindingId(), def.getItemId(), from, to);
//	}
//
//	public List<PersistableEvent> getEvents(final String group,
//			final String name, final Date from, final Date to) {
//		Query q = getEntityManager().createNamedQuery(
//				"Event.find4EventClassAndRange");
//		q.setParameter("group", group);
//		q.setParameter("name", name);
//		q.setParameter("from", from.getTime());
//		q.setParameter("to", to.getTime());
//		return q.getResultList();
//	}
//
//}