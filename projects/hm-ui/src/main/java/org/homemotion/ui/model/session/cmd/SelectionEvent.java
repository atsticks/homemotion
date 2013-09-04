package org.homemotion.ui.model.session.cmd;

import org.homemotion.events.BindingType;
import org.homemotion.events.EventGroup;
import org.homemotion.common.events.AbstractEvent;
import org.homemotion.events.NotificationDefinitionFactory;

public class SelectionEvent extends AbstractEvent {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5255841795842040997L;
	
	public static final BindingType NOTIFTYPE_GET_SELECTION = NotificationDefinitionFactory
			.createGetter(EventGroup.COMMON, "Selection:get",
					"Access the current selected item in this context.");
	public static final BindingType NOTIFTYPE_SET_SELECTION = NotificationDefinitionFactory
			.createSetter(EventGroup.COMMON, "Selection:get",
					"Sets the current selected item in this context.",
					Object.class);

	SelectionEvent(BindingType def) {
		super(def);
	}

	public Object getSelection() {
		return getData(NotificationDefinitionFactory.VALUE);
	}

}
