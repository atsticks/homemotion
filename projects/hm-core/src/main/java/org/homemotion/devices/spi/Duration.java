package org.homemotion.devices.spi;


public final class Duration {
	public static final Duration WEEK = new Duration(7,0,0,0);
	public static final Duration DAY = new Duration(1,0,0,0);
	public static final Duration HOUR = new Duration(0,1,0,0);
	public static final Duration THREE_HOURS = new Duration(0,3,0,0);
	public static final Duration SIX_HOURS = new Duration(0,6,0,0);
	public static final Duration HALF_DAY = new Duration(0,12,0,0);
	public static final Duration THIRTY_SECONDS = new Duration(0,0,0,30);
	public static final Duration MINUTE = new Duration(0,0,1,0);
	public static final Duration TWO_MINUTES= new Duration(0,0,2,0);
	public static final Duration THREE_MINUTES= new Duration(0,0,2,0);
	public static final Duration FIVE_MINUTES= new Duration(0,0,5,0);
	public static final Duration TEN_MINUTES = new Duration(0,0,10,0);
	public static final Duration QUARTER_HOUR = new Duration(0,0,30,0);
	public static final Duration HALF_HOUR = new Duration(0,0,30,0);
	public static final Duration TEN_SECONDS = new Duration(0,0,0,10);
	
	int days;
	int hours;
	int minutes;
	int seconds;

	public Duration() {
		// TODO Auto-generated constructor stub
	}
	
	public Duration(int days, int hours, int minutes, int seconds) {
		this.days = Math.abs(days);
		this.hours = Math.abs(hours);
		this.minutes = Math.abs(minutes);
		this.seconds = Math.abs(seconds);
	}
	
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public boolean isEmpty(){
		return this.seconds == 0 && 
		this.minutes == 0 &&
		this.hours == 0 &&
		this.days == 0;
	}
	
	public int toSeconds() {
		return getSeconds() +
		       getMinutes()*60 +
		       getHours() * 3600 +
		       getDays() * 3600*24;
	}
	
	public long toMillis() {
		return toSeconds() * 1000L;
	}
}
