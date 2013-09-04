package org.homemotion.scheduler.spi;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javassist.expr.MethodCall;

import org.homemotion.common.message.CallableMessage;
import org.homemotion.common.system.Container;
import org.homemotion.dao.spi.AbstractConfiguredItem;
import org.homemotion.macros.MacroManager;
import org.homemotion.scheduler.TriggerManager;
import org.homemotion.scheduler.TriggerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTrigger extends AbstractConfiguredItem implements
        Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory
            .getLogger(AbstractTrigger.class);
    public static final String EVENT_TYPE = "runtime";
    public static final String LAST_RESULT = "lastResult";
    private boolean executeSynchronized = false;
    private long initializedAt;
    private long executionCount;
    private long finishedAt;
    private List<String> scriptIDs = new ArrayList<String>();
    private TriggerStatus triggerStatus = TriggerStatus.LOADED;

    /*
     * (non-Javadoc)
     * 
     * @see org.homemotion.trigger.Trigger#getScripts()
     */
    public Collection<String> getScripts() {
        return Collections.unmodifiableCollection(scriptIDs);
    }

    public void addScripts(String... scriptIDs) {
        for (String id : scriptIDs) {
            this.scriptIDs.add(id);
        }
    }

    public AbstractTrigger(String id) {
        super(id, "runtime/Triggers");
    }

    public String getLastResults() {
        return getAttribute(LAST_RESULT, String.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.homemotion.trigger.Trigger#getTriggerStatus()
     */
    public TriggerStatus getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(TriggerStatus triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.homemotion.trigger.Trigger#getLastExecuted()
     */
    public long getInitializedAt() {
        return initializedAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.homemotion.trigger.Trigger#getStarted()
     */
    public long getExecutionCount() {
        return executionCount;
    }

    public final void executeTrigger() {
        StringBuilder result = new StringBuilder();
        this.initializedAt = System.currentTimeMillis();
        result.append("TRIGGER INITIALIZED at ");
        result.append(this.initializedAt);
        result.append("\n");
        if (this.triggerStatus != TriggerStatus.ACTIVE) {
            this.triggerStatus = TriggerStatus.ACTIVE;
        }
        this.executionCount++;
        TriggerManager triggerManager = Container
                .getInstance(TriggerManager.class);
        MacroManager macroManager = Container.getInstance(MacroManager.class);
        if (triggerManager.isPersistent(this)) {
            triggerManager.update(this);
        }
        for (String methodCall : this.scriptIDs) {
            CallableMessage call = null;
            try {
                if (executeSynchronized) {
                    call = new CallableMessage(methodCall, getIdentifier(), false);
                } else {
                    call = new CallableMessage(methodCall, getIdentifier(), true);
                }
            } catch (Exception e) {
                StringWriter w = new StringWriter();
                w.append("Error executing trigger: ").append(this.toString()).append('\n');
                e.printStackTrace(new PrintWriter(w));
                result.append(w.getBuffer().toString());
                LOG.error(w.getBuffer().toString());
                try {
                    w.close();
                } catch (IOException e1) {
                    Logger logger = LoggerFactory.getLogger(getClass());
                    logger.warn("Error closing writer.", e);
                }
                result.append("\n");
                this.triggerStatus = TriggerStatus.FAILED;
            }
        }
        this.finishedAt = System.currentTimeMillis();
        result.append("TRIGGER FINISHED at ");
        result.append(this.finishedAt);
        result.append('\n');
        setLastResults(result.toString());
        if (triggerManager.isPersistent(this)) {
            triggerManager.update(this);
        }
    }

    private void setLastResults(String results) {
        setAttribute(LAST_RESULT, results);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.homemotion.trigger.Trigger#getLastFinished()
     */
    public long getFinishedAt() {
        return finishedAt;
    }

    public abstract void install();

    public abstract void uninstall();

    public void reset() {
        uninstall();
        install();
    }

    public boolean isExecuteSynchronized() {
        return executeSynchronized;
    }

    public void setExecuteSynchronized(boolean executeSynchronized) {
        this.executeSynchronized = executeSynchronized;
    }
}
