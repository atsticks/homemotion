package org.homemotion.scheduler.impl;

import javax.enterprise.event.Event;

import org.homemotion.scheduler.SchedulerEvent;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;


final class JobEventProducer implements JobListener {

	private Event<SchedulerEvent> sender;
	
	JobEventProducer(Event<SchedulerEvent> sender){
		this.sender = sender;
	}
	
	public String getName() {
		return "Homemotion-JobEventProducer";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		SchedulerEvent evt = new SchedulerEvent(context, SchedulerEvent.Type.VETOED);
		sender.fire(evt);
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		SchedulerEvent evt =  new SchedulerEvent(context, SchedulerEvent.Type.FIRED);
		sender.fire(evt);
	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		SchedulerEvent evt =  new SchedulerEvent(context, SchedulerEvent.Type.COMPLETED);
		sender.fire(evt);
	}

}