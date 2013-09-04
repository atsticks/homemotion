package org.homemotion.calendar;

import java.util.GregorianCalendar;

import org.homemotion.dao.spi.AbstractConfiguredItem;
import org.quartz.Calendar;

public abstract class AbstractCalendar extends AbstractConfiguredItem implements
		HMCalendar {

	public AbstractCalendar(String id, String binding) {
		super(id, binding);
	}

	protected abstract Calendar getQuartzCalendar();

	@Override
	public boolean isTimeIncluded(long timeStamp) {
		return getQuartzCalendar().isTimeIncluded(timeStamp);
	}

	@Override
	public long getNextIncludedTime(long timeStamp) {
		return getQuartzCalendar().getNextIncludedTime(timeStamp);
	}

	@Override
	public boolean isIncludedNow() {
		return isTimeIncluded(System.currentTimeMillis());
	}

	public String toString() {
		return getIdentifier() + ": " + getQuartzCalendar();
	}

	public String getDescription() {
		return getQuartzCalendar().getDescription();
	}

	public void setDescription(String desc) {
		getQuartzCalendar().setDescription(desc);
	}

	public void setBaseCalendar(AbstractCalendar calendar) {
		getQuartzCalendar().setBaseCalendar(calendar.getQuartzCalendar());
	}

	protected Calendar getBaseCalendar() {
		return getQuartzCalendar().getBaseCalendar();
	}

	protected java.util.Calendar createCalendar(String day) {
		String[] dmy = day.split("\\.");
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();
		for (int i = 0; i < dmy.length; i++) {
			switch (i) {
			case 0:
				cal.set(GregorianCalendar.DAY_OF_MONTH,
						Integer.parseInt(dmy[i]));
			case 1:
				cal.set(GregorianCalendar.MONTH, Integer.parseInt(dmy[i]));
			case 2:
				cal.set(GregorianCalendar.YEAR, Integer.parseInt(dmy[i]));
			}
		}
		return cal;
	}
}
