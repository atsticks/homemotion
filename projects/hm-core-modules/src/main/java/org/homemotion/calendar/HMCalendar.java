package org.homemotion.calendar;

public interface HMCalendar {

	String getId();

	boolean isTimeIncluded(long timeStamp);

	long getNextIncludedTime(long timeStamp);

	public boolean isIncludedNow();

}
