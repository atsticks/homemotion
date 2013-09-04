package org.homemotion.calendar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.homemotion.dao.spi.AbstractConfiguredItem;
import org.quartz.Calendar;

public final class CombinedCalendar extends AbstractConfiguredItem implements
		Calendar {

	private static final long serialVersionUID = 3505525164556237217L;

	private List<org.homemotion.calendar.HMCalendar> calendars = new ArrayList<org.homemotion.calendar.HMCalendar>();

	private CombinationType combinationType = CombinationType.UNION;

	private String description;

	public CombinedCalendar(String id) {
		super(id, "modules/Calendars");
	}

	public void setCalendars(
			Collection<org.homemotion.calendar.HMCalendar> calendars) {
		this.calendars.addAll(calendars);
	}

	public Collection<org.homemotion.calendar.HMCalendar> getCalendars() {
		return Collections.unmodifiableCollection(this.calendars);
	}

	public void addCalendar(org.homemotion.calendar.HMCalendar calendar) {
		this.calendars.add(calendar);
	}

	public void removeCalendar(org.homemotion.calendar.HMCalendar calendar) {
		this.calendars.remove(calendar);
	}

	public Calendar getBaseCalendar() {
		return null;
	}

	public long getNextIncludedTime(long timeStamp) {
		long value = Long.MIN_VALUE;
		for (org.homemotion.calendar.HMCalendar cal : calendars) {
			long nextIncluded = cal.getNextIncludedTime(timeStamp);
			if (nextIncluded > value) {
				if (isTimeIncluded(nextIncluded)) {
					value = nextIncluded;
				}
			}
		}
		return value;
	}

	public boolean isTimeIncluded(long timeStamp) {
		switch (combinationType) {
		case UNION:
			for (org.homemotion.calendar.HMCalendar cal : calendars) {
				if (cal.isTimeIncluded(timeStamp)) {
					return true;
				}
			}
			return false;
		case INTERSECT:
			for (org.homemotion.calendar.HMCalendar cal : calendars) {
				if (!cal.isTimeIncluded(timeStamp)) {
					return false;
				}
			}
			return true;
		default:
			return false;
		}
	}

	public void setBaseCalendar(Calendar baseCalendar) {
		throw new UnsupportedOperationException();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCombinationType(CombinationType combinationType) {
		if (combinationType == null) {
			throw new IllegalArgumentException(
					"Combination Type may not be null.");
		}
		this.combinationType = combinationType;
	}

	public CombinationType getCombinationType() {
		return combinationType;
	}
}
