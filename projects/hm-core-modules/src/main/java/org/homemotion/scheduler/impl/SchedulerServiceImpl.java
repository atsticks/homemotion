package org.homemotion.scheduler.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.macros.Macro;
import org.homemotion.scheduler.SchedulerEvent;
import org.homemotion.scheduler.SchedulerService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SchedulerServiceImpl implements SchedulerService {

	private static final String MACRO_GROUP_ID = "Macros";
	private static final String JOB_GROUP_ID = "Jobs";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SchedulerServiceImpl.class);
	private Properties config;
	private StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private Scheduler scheduler;
	
	@Inject
	private Event<SchedulerEvent> eventSender;

	public boolean execute(Macro macro) {
		String idName = System.currentTimeMillis() + " Immediate macro,id="
				+ macro.getId();
		SimpleTrigger nowTrigger = new SimpleTrigger(idName, MACRO_GROUP_ID);
		JobDetail jobDescription = new JobDetail(idName, MACRO_GROUP_ID,
				MacroJob.class);
		jobDescription.getJobDataMap().put("MacroID", macro.getId());
		try {
			// TODO Create Event
			getScheduler().scheduleJob(jobDescription, nowTrigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error(
					"Error scheduling macro for immediate execution,macro="
							+ macro.getId(), e);
			return false;
		}
	}

	public boolean execute(JobDetail job) {
		String idName = System.currentTimeMillis() + " Immediate job,job="
				+ job;
		SimpleTrigger nowTrigger = new SimpleTrigger(idName, JOB_GROUP_ID);
		try {
			// TODO Create Event
			getScheduler().scheduleJob(job, nowTrigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Error scheduling job for immediate execution, job="
					+ job, e);
			return false;
		}
	}

	public boolean execute(JobDetail job, Trigger trigger) {
		try {
			// TODO Create Event
			Scheduler sched = getScheduler();
			if(sched.isShutdown()){
				LOGGER.error("Error scheduling job for execution, job=" + job
						+ ",trigger=" + trigger + ": Scheduler is shut down.");
				return false;
			}
			getScheduler().scheduleJob(job, trigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Error scheduling job for execution, job=" + job
					+ ",trigger=" + trigger, e);
			return false;
		}
	}

	public boolean execute(Trigger trigger) {
		try {
			// TODO Create Event
			getScheduler().scheduleJob(trigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Error scheduling job for execution, trigger="
					+ trigger, e);
			return false;
		}
	}

	@Produces
	public Scheduler getScheduler() {
		if (scheduler == null) {
			initScheduler();
		}
		return scheduler;
	}

	private void initScheduler() {
		if (scheduler == null) {
			loadDefaultConfig();
			try {
				scheduler = schedulerFactory.getScheduler();
				scheduler.addGlobalTriggerListener(new TriggerEventForwarder(eventSender));
				scheduler.addGlobalJobListener(new JobEventProducer(eventSender));
			} catch (SchedulerException e) {
				LOGGER.error("Error creating scheduler.", e);
			}
		}
	}

	public boolean rescheduleMacro(String triggerName, Macro macro,
			Trigger newTrigger) {
		try {
			getScheduler().rescheduleJob(triggerName, MACRO_GROUP_ID,
					newTrigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error("Error rescheduling job.", e);
			return false;
		}
	}

	public boolean execute(Macro macro, Trigger trigger) {
		JobDetail jobDescription = new JobDetail("ScheduledMacro,id="
				+ macro.getId() + ",trigger=" + trigger.getName(),
				MACRO_GROUP_ID, MacroJob.class);
		jobDescription.getJobDataMap().put("MacroID", macro.getId());
		try {
			// TODO Create Event
			getScheduler().scheduleJob(jobDescription, trigger);
			return true;
		} catch (SchedulerException e) {
			LOGGER.error(
					"Error scheduling macro for immediate execution,macro="
							+ macro.getId(), e);
			return false;
		}
	}

	@PreDestroy
	public void shutdown() {
		shutdown(true);
	}

	public void shutdown(boolean waitForJobsToComplete) {
		if (scheduler != null) {
			LOGGER.info("Stopping Scheduler...");
			try {
				scheduler.shutdown(waitForJobsToComplete);
			} catch (SchedulerException e) {
				LOGGER.error("Error shutting down scheduler.", e);
				return;
			}
			LOGGER.info("Scheduler stopped.");
		}
	}

	@PostConstruct
	public void startup() {
		initScheduler();
		try {
			if (!scheduler.isStarted()) {
				scheduler.start();
			}
		} catch (Exception e) {
			LOGGER.error("Error starting scheduler.", e);
			return;
		}
	}

	private void loadDefaultConfig() {
		if (this.config == null) {
			InputStream is = null;
			Properties props = new Properties();
			try {
				is = Thread
						.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(
								"org/homemotion/scheduler/impl/SchedulerConfig.properties");
				props.load(is);
				this.config = props;
			} catch (Exception e) {
				LOGGER.error("Error loading scheduler configuration.", e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOGGER.debug("Error closnig stream.", e);
					}
				}
			}
		}
	}

	public Properties getSchedulerConfig() {
		return config;
	}

	public void setSchedulerConfig(Properties config) throws SchedulerException {
		this.config = config;
		if (scheduler != null) {
			boolean startup = scheduler.isStarted();
			if (startup) {
				scheduler.shutdown(true);
			}
			scheduler = null;
			if (startup) {
				startup();
			}
		}
	}

}