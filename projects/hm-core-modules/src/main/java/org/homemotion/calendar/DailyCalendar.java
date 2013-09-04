package org.homemotion.calendar;

import java.util.Map;
import java.util.TimeZone;

import org.homemotion.Extension;

@Extension
public final class DailyCalendar extends AbstractCalendar {

	private static final long serialVersionUID = 3505525164556237217L;

	private org.quartz.impl.calendar.DailyCalendar quartzCalendar;

	private String start;

	private String end;

	public DailyCalendar(String id) {
		super(id, "calendar/Calendars");
	}

	public void setTimezone(String tz) {
		quartzCalendar.setTimeZone(TimeZone.getTimeZone(tz));
	}

	public String getTimezone() {
		return quartzCalendar.getTimeZone().getID();
	}

	protected org.quartz.Calendar getQuartzCalendar() {
		initCalendar();
		return quartzCalendar;
	}

	private void initCalendar() {
		if (start != null && end != null && quartzCalendar == null) {
			quartzCalendar = new org.quartz.impl.calendar.DailyCalendar(start,
					end);
		}
	}

	public boolean isInvertingTimeRange() {
		return quartzCalendar.getInvertTimeRange();
	}

	public void setInvertingTimeRange(boolean invert) {
		this.quartzCalendar.setInvertTimeRange(invert);
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public void setStart(String value) {
		this.start = value;
		initCalendar();
	}

	public void setEnd(String value) {
		this.end = value;
		initCalendar();
	}

	protected void configureInternal(Map<String, String> configMap) {
		String val = configMap.get("timeZone");
		if (val != null) {
			setTimezone(val.trim());
		}
		val = configMap.get("timeRange");
		if (val != null) {
			String[] times = val.split("-");
			this.start = times[0];
			this.end = times[1];
			initCalendar();
		}
		val = configMap.get("invertTimeRange");
		if (val != null) {
			setInvertingTimeRange(Boolean.parseBoolean(val));
		}
	}
}