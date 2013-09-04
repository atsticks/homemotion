package org.homemotion.ui.model.session.cmd;

import java.util.Observable;
import java.util.concurrent.Future;

import org.homemotion.events.BindingType;
import org.homemotion.events.EventGroup;
import org.homemotion.common.events.AbstractEvent;
import org.homemotion.events.NotificationDefinitionFactory;
import org.homemotion.events.NotificationProvider;
import org.homemotion.events.NotificationService;
import org.homemotion.common.events.annot.NotificationExtension;

@NotificationExtension
public final class Selection extends Observable {
	
	@Override
	public BindingType[] getNotificationDefinitions() {
		return new BindingType[] { SelectionEvent.NOTIFTYPE_GET_SELECTION,
				SelectionEvent.NOTIFTYPE_SET_SELECTION, };
	}
	

}
