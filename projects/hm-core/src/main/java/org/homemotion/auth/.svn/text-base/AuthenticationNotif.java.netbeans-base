package org.homemotion.auth;
//
//import java.util.concurrent.Future;
//
//import org.homemotion.events.AbstractEventAdapter;
//import org.homemotion.events.EventGroup;
//import org.homemotion.events.Notification;
//import org.homemotion.events.NotificationDefinition;
//import org.homemotion.events.NotificationService;
//import org.homemotion.events.Severity;
//
//public final class AuthenticationNotif extends AbstractEventAdapter {
//
//	private static final String USER = "user";
//
//	public static final NotificationDefinition NOTIFTYPE_AUTHENTICATED = new NotificationDefinition(
//			EventGroup.AUTHORIZATION_AUTHENTICATION.toString(),
//			"Authentication:authenticated",
//			"A user has been authenticate on the system.", Severity.INFO)
//			.defineParameter(USER, SecUser.class, true).setReadOnly();
//	public static final NotificationDefinition NOTIFTYPE_LOGGEDOUT = new NotificationDefinition(
//			EventGroup.AUTHORIZATION_AUTHENTICATION.toString(),
//			"Authentication:loggedOut",
//			"A user has been logged off from the system.", Severity.INFO)
//			.defineParameter(USER, SecUser.class, true).setReadOnly();
//	public static final NotificationDefinition DEF_AUTHFAILURE = new NotificationDefinition(
//			EventGroup.AUTHORIZATION_AUTHENTICATION.toString(),
//			"Authentication:authenticationFailed",
//			"A user failed to authenticate.", Severity.INFO).defineParameter(
//			USER, SecUser.class, true).setReadOnly();
//
//	public AuthenticationNotif(Notification notif) {
//		super(notif);
//	}
//
//	public static Future<Notification> sendAuthenticated(Object owner,
//			SecUser user) {
//		Notification evt = new Notification(owner, NOTIFTYPE_AUTHENTICATED);
//		evt.setData(USER, user);
//		evt.setReadOnly();
//		new AuthenticationNotif(evt);
//		return NotificationService.get().publishEvent(evt);
//	}
//
//	public static Future<Notification> sendLoggedOut(Object owner, SecUser user) {
//		Notification evt = new Notification(owner, NOTIFTYPE_LOGGEDOUT);
//		evt.setData(USER, user);
//		evt.setReadOnly();
//		new AuthenticationNotif(evt);
//		return NotificationService.get().publishEvent(evt);
//	}
//
//	public SecUser getUser() {
//		return getNotification().getData(USER, SecUser.class);
//	}
//
//}
