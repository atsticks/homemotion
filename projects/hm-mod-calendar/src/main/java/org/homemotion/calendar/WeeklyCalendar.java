package org.homemotion.calendar;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.homemotion.Extension;
import org.homemotion.scheduler.spi.Weekday;

@Extension
public final class WeeklyCalendar extends AbstractCalendar {

	private static final long serialVersionUID = -7953101034759999669L;

	private org.quartz.impl.calendar.WeeklyCalendar calendar = new org.quartz.impl.calendar.WeeklyCalendar();

	public WeeklyCalendar(String id) {
		super(id, "calendar/Calendars");
	}

	public org.quartz.impl.calendar.WeeklyCalendar getQuartzCalendar() {
		return calendar;
	}

	public Set<Weekday> getDayset() {
		Set<Weekday> result = new HashSet<Weekday>();
		if (isMondayExcluded()) {
			result.add(Weekday.Mo);
		}
		if (isTuesdayExcluded()) {
			result.add(Weekday.Tu);
		}
		if (isWednesdayExcluded()) {
			result.add(Weekday.We);
		}
		if (isThursdayExcluded()) {
			result.add(Weekday.Th);
		}
		if (isFridayExcluded()) {
			result.add(Weekday.Fr);
		}
		if (isSaturdayExcluded()) {
			result.add(Weekday.Sa);
		}
		if (isSundayExcluded()) {
			result.add(Weekday.Su);
		}
		return result;
	}

	public boolean isMondayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.MONDAY);
	}

	public boolean isTuesdayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.TUESDAY);
	}

	public boolean isWednesdayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.WEDNESDAY);
	}

	public boolean isThursdayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.THURSDAY);
	}

	public boolean isFridayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.FRIDAY);
	}

	public boolean isSaturdayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.SATURDAY);
	}

	public boolean[] getExcludedDays(boolean value) {
		return calendar.getDaysExcluded();
	}

	public boolean isSundayExcluded() {
		return calendar.isDayExcluded(java.util.Calendar.SUNDAY);
	}

	public void setDays(boolean excluded, int... days) {
		for(int day:days){
			calendar.setDayExcluded(day, excluded);
		}
	}
	
	public void setDays(boolean excluded, Weekday... days) {
		for(Weekday day:days){
			calendar.setDayExcluded(day.getValue(), excluded);
		}
	}
	
	protected void configureInternal(Map<String, String> configMap) {
		String val = configMap.get("weekDays");
		if (val != null) {
			String[] days = val.split(",");
			for (String day : days) {
				setDays(true, Weekday.valueOf(day.trim()));
			}
		}
	}

}
