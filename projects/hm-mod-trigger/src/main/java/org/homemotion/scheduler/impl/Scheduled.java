package org.homemotion.scheduler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.homemotion.common.system.Container;
import org.homemotion.scheduler.SchedulerService;
import org.homemotion.scheduler.spi.AbstractTrigger;
import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Scheduled extends AbstractTrigger {

	public Scheduled(String id) {
		super(id);
	}

	private static final long serialVersionUID = -4179751697275092050L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Scheduled.class);

	private Calendar calendar;

	private Set<String> executions = new HashSet<String>();

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setExecution(Set<String> executions) {
		this.executions.clear();
		this.executions.addAll(executions);
	}

	public Set<String> getExecutions() {
		return Collections.unmodifiableSet(executions);
	}

	@Override
	public void install() {
		JobDetail job = new JobDetail();
		job.setGroup(getTriggerGroup());
		job.setName(getIdentifier());
		job.setJobClass(CalendarTriggerJob.class);
		job.getJobDataMap().put(Scheduled.class, this);
		Collection<org.quartz.Trigger> triggers = createTriggers();
		for (org.quartz.Trigger tr : triggers) {
			Container.getInstance(SchedulerService.class).execute(job, tr);
		}
	}

	private Collection<Trigger> createTriggers() {
		List<Trigger> result = new ArrayList<Trigger>();
		Trigger[] triggers = new Trigger[executions.size()];
		for (String execTime : executions) {
			String[] time = execTime.trim().split(":");
			if (time.length == 1) {
				result.add(TriggerUtils.makeDailyTrigger(Integer
						.parseInt(time[0]), 0));
			} else if (time.length == 2) {
				result.add(TriggerUtils.makeDailyTrigger(Integer
						.parseInt(time[0]), Integer.parseInt(time[1])));
			} else {
				throw new IllegalArgumentException(
						"Invalid fixed time format: " + execTime.trim());
			}
		}
		return result;
	}

	protected String getTriggerGroup() {
		return "triggers";
	}

	public static class CalendarTriggerJob implements Job {
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			Scheduled trigIntern = (Scheduled) context
					.getMergedJobDataMap().get(Scheduled.class);
			if (trigIntern.getCalendar().isTimeIncluded(
					System.currentTimeMillis())) {
				trigIntern.executeTrigger();
			}
		}
	}

	@Override
	public void uninstall() {
		try {
			Container.getInstance(SchedulerService.class).getScheduler()
					.deleteJob(getIdentifier(),
							getTriggerGroup());
		} catch (SchedulerException e) {
			LOGGER.warn("Could not remove trigger.", e);
		}
	}

	@Override
	public void configure(String config) {
		// TODO Auto-generated method stub

	}

}
