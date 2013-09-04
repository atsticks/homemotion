package org.homemotion.common.message.impl;

//import javax.enterprise.event.Observes;
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import org.apache.log4j.Logger;
//import org.homemotion.events.Notification;
//import org.homemotion.events.PersistableEvent;
//import org.homemotion.events.PersistableEventManager;
//
///**
// * Called via Camel routing.
// * 
// * @author Anatole
// */
//@Singleton
//public final class EventPersister {
//
//	private static final Logger logger = Logger.getLogger(EventPersister.class);
//
//	private PersistableEventManager eventManager;
//
//	@Inject
//	public EventPersister(PersistableEventManager eventManager) {
//		this.eventManager = eventManager;
//	}
//
//	public void notified(@Observes Notification event) {
//		PersistableEvent pEvt = event.createPersistableEvent();
//		if (!eventManager.isPersistent(pEvt)) {
//			try {
//				eventManager.create(pEvt);
//			} catch (Exception e) {
//				logger.error("Error persisting event: " + event, e);
//			}
//		}
//	}
//}
