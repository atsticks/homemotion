package org.homemotion.scheduler;

import org.homemotion.macros.Macro;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;

public interface SchedulerService {

	public Scheduler getScheduler();
	
	public boolean rescheduleMacro(String id, Macro macro, Trigger trigger);
	
	public boolean execute(Macro macro);
	public boolean execute(Macro macro, Trigger trigger);
	public boolean execute(JobDetail job);
	public boolean execute(JobDetail job, Trigger trigger);
	public boolean execute(Trigger trigger);
	
}
