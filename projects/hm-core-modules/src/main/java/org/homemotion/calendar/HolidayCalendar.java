package org.homemotion.calendar;

import java.util.Map;
import java.util.TimeZone;

public class HolidayCalendar extends AbstractCalendar {

	private static final long serialVersionUID = -2355279752530745680L;

	private org.quartz.impl.calendar.HolidayCalendar calendar = new org.quartz.impl.calendar.HolidayCalendar();

	public HolidayCalendar(String id) {
		super(id, "calendar/Calendars");
	}

	protected org.quartz.Calendar getQuartzCalendar() {
		return calendar;
	}

	public void setTimezone(String tz) {
		calendar.setTimeZone(TimeZone.getTimeZone(tz));
	}

	public String getTimezone() {
		return calendar.getTimeZone().getID();
	}

	public void addExcludedDays(java.util.Calendar... days) {
		for (java.util.Calendar day : days) {
			calendar.addExcludedDate(day.getTime());
		}
	}

	public void removeExcludedDays(java.util.Calendar... days) {
		for (java.util.Calendar day : days) {
			calendar.removeExcludedDate(day.getTime());
		}
	}

	public String toString() {
		return getIdentifier() + ": " + calendar.toString();
	}
	
	protected void configureInternal(Map<String, String> configMap) {
		String val = configMap.get("timeZone");
		if (val != null) {
			setTimezone(val.trim());
		}
		val = configMap.get("excluded");
		if (val != null) {
			String[] days = val.split(",");
			for (String day : days) {
				addExcludedDays(createCalendar(day));
			}
		}
	}
}
