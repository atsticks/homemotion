package org.homemotion.scheduler.spi;

import javax.persistence.Transient;

import org.homemotion.common.system.Container;
import org.homemotion.scheduler.SchedulerService;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuartzTrigger extends AbstractTrigger {

	public AbstractQuartzTrigger(String id) {
		super(id);
	}

	private static final long serialVersionUID = 1L;
	
	private Trigger quartzTrigger;
	
	private int repeatCount = -1;
	
	protected abstract Trigger createTrigger(String triggerId);

	public void install() {
		try {
			if(this.quartzTrigger==null){
				this.quartzTrigger = createTrigger(getIdentifier());
			} 
			JobDetail job = new JobDetail();
			job.setGroup(getTriggerGroup());
			job.setName(getIdentifier());
			job.setJobClass(QuartzTriggerJob.class);
			job.getJobDataMap().put(QuartzTriggerJob.class.getName(), this);
			Container.getInstance(SchedulerService.class).execute(job, this.quartzTrigger);
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error("Error registering trigger (id="+getIdentifier()+")",e);
		}
	}

	public void uninstall() {
		try {
			Container.getInstance(SchedulerService.class).getScheduler().deleteJob(getIdentifier(),
					getTriggerGroup());
		} catch (SchedulerException e) {
			LoggerFactory.getLogger(getClass()).warn("Could not remove trigger.", e);
		}
	}

	protected String getTriggerGroup() {
		return "triggers";
	}

	@Override
	public void reset() {
		try {
			Container.getInstance(SchedulerService.class).getScheduler().rescheduleJob(getIdentifier(),
					getTriggerGroup(), this.quartzTrigger);
		} catch (SchedulerException e) {
			LoggerFactory.getLogger(getClass()).warn("Could not reset trigger.", e);
		}
	}

	public static final class QuartzTriggerJob implements Job {

		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			AbstractQuartzTrigger trigIntern = (AbstractQuartzTrigger) context
					.getMergedJobDataMap().get(QuartzTriggerJob.class.getName());
			if(trigIntern!=null){
				trigIntern.executeTrigger();
			}
		}

	}

	public final int getRepeatCount() {
		return repeatCount;
	}

	public final void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Transient
	public final Trigger getQuartzTrigger() {
		return quartzTrigger;
	}

}
