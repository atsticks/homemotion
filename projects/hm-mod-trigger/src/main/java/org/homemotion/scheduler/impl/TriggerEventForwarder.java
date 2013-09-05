package org.homemotion.scheduler.impl;

import javax.enterprise.event.Event;

import org.homemotion.scheduler.SchedulerEvent;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

final class TriggerEventForwarder implements TriggerListener {

	private Event<SchedulerEvent> sender;

	public TriggerEventForwarder(Event<SchedulerEvent> sender) {
		this.sender = sender;
	}

	public String getName() {
		return "Homemotion-TriggerEventForwarder";
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			int triggerInstructionCode) {
		SchedulerEvent evt = new SchedulerEvent(context,
				SchedulerEvent.Type.COMPLETED);
		sender.fire(evt);
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		SchedulerEvent evt = new SchedulerEvent(
				context, SchedulerEvent.Type.FIRED);
		sender.fire(evt);
	}

	public void triggerMisfired(Trigger trigger) {
		SchedulerEvent evt = new SchedulerEvent(
				null, SchedulerEvent.Type.MISFIRED);
		sender.fire(evt);
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		return false;
	}
}