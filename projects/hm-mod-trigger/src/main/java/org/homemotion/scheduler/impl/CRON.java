package org.homemotion.scheduler.impl;

import java.text.ParseException;

import org.homemotion.scheduler.spi.AbstractQuartzTrigger;
import org.quartz.CronTrigger;
import org.quartz.Trigger;

public class CRON extends AbstractQuartzTrigger {
	
	private String cronExpression;
	
	public CRON(String id) {
		super(id);
	}

	private static final long serialVersionUID = 4615051196804591016L;
	
	public static final String CRON_EXPRESSION = "CRON Expression";
	
	protected Trigger createTrigger(String id) {
		try {
			return new CronTrigger(id, getTriggerGroup(), getCronExpression());
		} catch (ParseException e) {
			throw new IllegalStateException("Error creating CRON trigger.",e);
		}
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
