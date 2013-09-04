package org.homemotion.ui.model.session.cmd;

import java.util.concurrent.Future;


public class CurrentSelection {

	private CurrentSelection() {
		// singleton
	}

	public static Future<SelectionEvent> get() {
		SelectionEvent notif = new SelectionEvent(
				SelectionEvent.NOTIFTYPE_GET_SELECTION);
		return NotificationService.get().publishEvent(notif,
				SelectionEvent.class);
	}

	public static Future<SelectionEvent> set(Object selection) {
		SelectionEvent notif = new SelectionEvent(
				SelectionEvent.NOTIFTYPE_GET_SELECTION);
		notif.setData(NotificationDefinitionFactory.VALUE, selection);
		return NotificationService.get().publishEvent(notif,
				SelectionEvent.class);
	}
}
