package org.homemotion.calendar.impl;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.calendar.AbstractCalendar;
import org.homemotion.calendar.CalendarManager;
import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.dao.spi.AbstractConfiguredItemManager;

@Singleton
public class CalendarManagerImpl extends
		AbstractConfiguredItemManager<AbstractCalendar>
		implements CalendarManager {

	@Inject
	public CalendarManagerImpl(ConfigurationService configurationService ) {
		super(AbstractCalendar.class, "modules/Calendars", configurationService);
	}

	@Override
	protected void load(Configuration configuration,
			Map<String, AbstractCalendar> items) {
		// TODO Auto-generated method stub
	}

}
