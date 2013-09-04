package org.homemotion.dao.spi;

import org.homemotion.common.message.AbstractMessage;

public final class CRUDDataEvent extends AbstractMessage {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -4166964762444944196L;
    private static final String CHANNEL = "data.operation";
    private Operation operation;
    private Object instance;

    public static enum Operation {
        CREATE, UPDATE, READ, DELETE, UNKNOWN, REFRESH
    }

    public CRUDDataEvent(Object instance, Operation operation, String sender) {
        super(CHANNEL, sender);
        if (instance == null) {
            throw new IllegalArgumentException("instance required.");
        }
        if (operation == null) {
            throw new IllegalArgumentException("operation required.");
        }
        this.operation = operation;
        this.instance = instance;
    }

    @SuppressWarnings("rawtypes")
    public Class getItemType() {
        return instance.getClass();
    }

    public Operation getOperation() {
        return operation;
    }

    public Object sgetInstance() {
        return instance;
    }
}
