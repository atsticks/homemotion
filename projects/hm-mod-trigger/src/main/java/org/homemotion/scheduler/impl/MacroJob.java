package org.homemotion.scheduler.impl;

import java.util.Arrays;

import javax.enterprise.event.Event;

import org.homemotion.common.message.CallableMessage;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This is just a simple job that says "Hello" to the world.
 * </p>
 * 
 * @author Bill Kratzer
 */
public final class MacroJob implements Job {

	private static Logger LOGGER = LoggerFactory.getLogger(MacroJob.class);

	private Event<CallableMessage> sender;

	/**
	 * <p>
	 * Empty constructor for job initilization
	 * </p>
	 * <p>
	 * Quartz requires a public empty constructor so that the scheduler can
	 * instantiate the class whenever it needs.
	 * </p>
	 */
	public MacroJob(Event<CallableMessage> sender) {
		this.sender = sender;
	}

	/**
	 * <p>
	 * Called by the <code>{@link org.quartz.Scheduler}</code> when a
	 * <code>{@link org.quartz.Trigger}</code> fires that is associated with the
	 * <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String target = (String) context.get("target");
		String method = (String) context.get("method");
		String params = (String) context.get("params");
		Object[] paramArr = getArray(params);
		String message = target + '#' + method;
		String reqInfo = "Starting macro: " + message + '('
				+ Arrays.toString(paramArr) + ')';
		LOGGER.info(reqInfo);
		try {
			sender.fire(new CallableMessage("runtime.macro."+message, context.getJobDetail()
					.getFullName(), true, paramArr));
		} catch (Exception e) {
			throw new JobExecutionException("Error firing execution request "
					+ reqInfo + ".", e);
		}
	}

	private Object[] getArray(String params) {
		if (params != null) {
			// TODO
			return new Object[0];
		}
		return new Object[0];
	}
}