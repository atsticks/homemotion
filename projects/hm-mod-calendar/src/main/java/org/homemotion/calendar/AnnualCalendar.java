package org.homemotion.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

public final class AnnualCalendar extends AbstractCalendar implements
		org.homemotion.calendar.HMCalendar {

	private static final long serialVersionUID = 3505525164556237217L;

	private org.quartz.impl.calendar.AnnualCalendar quartzCalendar = new org.quartz.impl.calendar.AnnualCalendar();

	public AnnualCalendar(String id) {
		super(id, "calendars/Calendars");
	}

	public void setTimezone(String tz) {
		quartzCalendar.setTimeZone(TimeZone.getTimeZone(tz));
	}

	public String getTimezone() {
		return quartzCalendar.getTimeZone().getID();
	}

	protected org.quartz.Calendar getQuartzCalendar() {
		return quartzCalendar;
	}

	public void setDays(boolean excluded, Calendar... days) {
		for (Calendar day : days) {
			this.quartzCalendar.setDayExcluded(day, excluded);
		}
	}

	public void removeExcludedDays(Calendar... days) {
		for (Calendar day : days) {
			this.quartzCalendar.removeExcludedDay(day);
		}
	}

	public void configure(String config) {
		// TODO
	}

	public String getDetails() {
		return this.quartzCalendar.toString();
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
				setDays(true, createCalendar(day));
			}
		}
		val = configMap.get("included");
		if (val != null) {
			String[] days = val.split(",");
			for (String day : days) {
				setDays(true, createCalendar(day));
			}
		}
	}

	
}
