package org.homemotion.calendar;

import java.util.Map;
import java.util.TimeZone;

import org.quartz.Calendar;

public class MonthlyCalendar extends AbstractCalendar {

	private org.quartz.impl.calendar.MonthlyCalendar calendar = new org.quartz.impl.calendar.MonthlyCalendar();

	public MonthlyCalendar(String id) {
		super(id, "calendar/Calendars");
	}

	public Calendar getQuartzCalendar() {
		return calendar;
	}

	public void setTimezone(String tz) {
		calendar.setTimeZone(TimeZone.getTimeZone(tz));
	}

	public String getTimezone() {
		return calendar.getTimeZone().getID();
	}

	public void setDays(boolean excluded, int... days) {
		for (int day : days) {
			calendar.setDayExcluded(day, excluded);
		}
	}

	public boolean getAllDaysExcluded() {
		return calendar.areAllDaysExcluded();
	}

	public boolean isDayIncluded(int day) {
		return calendar.isDayExcluded(day);
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
				setDays(true, Integer.parseInt(day));
			}
		}
		val = configMap.get("included");
		if (val != null) {
			String[] days = val.split(",");
			for (String day : days) {
				setDays(true, Integer.parseInt(day));
			}
		}
	}

}
