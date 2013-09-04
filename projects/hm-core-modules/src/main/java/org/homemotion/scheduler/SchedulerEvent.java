package org.homemotion.scheduler;

import org.homemotion.common.message.AbstractMessage;
import org.quartz.JobExecutionContext;

public final class SchedulerEvent extends AbstractMessage{

    private static final long serialVersionUID = 3573984527721705161L;
    private Type type;
    private JobExecutionContext jobContext;

    public static enum Type {

        FIRED, MISFIRED, COMPLETED, VETOED
    }

    public SchedulerEvent(JobExecutionContext jobContext, Type type) {
        super("org.homemotion.scheduler.JobEvent", jobContext.getJobDetail().getFullName());
        if (type == null) {
            throw new IllegalArgumentException("type must not be null.");
        }
        this.jobContext = jobContext;
        if (!type.equals(Type.MISFIRED)) {
            if (jobContext == null) {
                throw new IllegalArgumentException(
                        "jobContext is null only if misfired).");
            }
            this.jobContext = jobContext;
        }
    }

    public Type getType() {
        return type;
    }

    public JobExecutionContext getJobExecutionContext() {
        return jobContext;
    }

    public String getDetails() {
        return "type=" + getType() + ", context=" + getJobExecutionContext();
    }
}
