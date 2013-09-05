package org.homemotion.scheduler.impl;

import java.util.Map;

import org.homemotion.scheduler.spi.AbstractQuartzTrigger;
import org.homemotion.scheduler.spi.Weekday;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

public final class Periodic extends AbstractQuartzTrigger {
	
	public Periodic(String id) {
		super(id);
	}

	private Integer seconds;
	private Weekday weekday;
	private Integer hours;
	private Integer minutes;
	private static final long serialVersionUID = 4342028542225594756L;


	protected Trigger createTrigger(String triggerId) {
		if(getWeekday()!=null){
			return TriggerUtils.makeWeeklyTrigger(triggerId, getWeekday().getValue(), getHours(), getMinutes());
		}
		if(getSeconds()!=null){
			return TriggerUtils.makeSecondlyTrigger(triggerId, getSeconds(), getRepeatCount());
		}
		if(getHours()==null){
			return TriggerUtils.makeMinutelyTrigger(triggerId, getMinutes(), getRepeatCount());
		}
		return TriggerUtils.makeDailyTrigger(triggerId, getHours(), getMinutes());
	}

	public Integer getMinutes() {
		if(minutes==null){
			return Integer.valueOf(0);
		}
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getHours() {
		if(hours==null){
			return Integer.valueOf(0);
		}
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	public Weekday getWeekday() {
		return this.weekday;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Integer getSeconds() {
		if(seconds==null){
			return Integer.valueOf(0);
		}
		return this.seconds;
	}

	protected void configureInternal(Map<String, String> configMap) {
		String val = configMap.get("seconds");
		if(val!=null){
			setSeconds(Integer.parseInt(val.trim()));
		}
		val = configMap.get("minutes");
		if(val!=null){
			setMinutes(Integer.parseInt(val.trim()));
		}
		val = configMap.get("hours");
		if(val!=null){
			setHours(Integer.parseInt(val.trim()));
		}
		val = configMap.get("repeatCount");
		if(val!=null){
			setRepeatCount(Integer.parseInt(val.trim()));
		}
		val = configMap.get("weekday");
		if(val!=null){
			setWeekday(Weekday.valueOf(val));
		}
	}
}
