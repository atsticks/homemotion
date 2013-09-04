package org.homemotion.scheduler.impl;


//public class Observer extends AbstractEventTrigger {
//
//	private static final long serialVersionUID = -6548881132566777229L;
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(Observer.class);
//
//	private static final String EVENT_TYPE = "Event Type";
//
//	private static final String SEVERITY = "Severity";
//
//	@Override
//	protected void initAttributes() {
//		getTrigger().defineAttribute(EVENT_TYPE, String.class);
//		getTrigger().defineAttribute(SEVERITY, Severity.class);
//	}
//
//	public String getEventType() {
//		return getTrigger().getAttribute(EVENT_TYPE, String.class);
//	}
//
//	public void setEventType(String eventType) {
//		getTrigger().setAttribute(EVENT_TYPE, eventType);
//	}
//
//	public Severity getSeverity() {
//		return getTrigger().getAttribute(SEVERITY, Severity.class);
//	}
//
//	public void setSeverity(Severity severity) {
//		getTrigger().setAttribute(SEVERITY, severity);
//	}
//
//	@Transient
//	public boolean isInterested(Notification event) {
//		String eventType = getEventType();
//		if (eventType != null && !event.getClass().getName().matches(eventType)) {
//			LOGGER.debug("Trigger will not fire: no matching type.");
//			return false;
//		}
//		Severity severity = getSeverity();
//		if (severity != null && !severity.equals(event.getSeverity())) {
//			LOGGER.debug("Trigger will not fire: no matching severity.");
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		String eventType = getEventType();
//		Severity severity = getSeverity();
//		return "ConfigurableEventTrigger ["
//				+ (eventType != null ? "eventType=" + eventType + ", " : "")
//				+ (severity != null ? "severity=" + severity + ", " : "") + "]";
//	}
//
//	@Override
//	public void configure(String config) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
