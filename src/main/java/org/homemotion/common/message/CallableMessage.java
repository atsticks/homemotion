package org.homemotion.common.message;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CallableMessage extends AbstractMessage {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -107394880806535314L;
    private Object result;
    private boolean asynchronous = false;

    public CallableMessage(String channel, String sender, boolean asynch) {
        super(channel, sender);
        this.asynchronous = asynch;
    }
    
    public CallableMessage(String channel, String sender, Object... params) {
        this(channel, sender, false, params);
    }

    public CallableMessage(String channel, String sender,  boolean asynch, Object... params) {
    	super(channel, sender);
        this.asynchronous = asynch;
        for(Object o:params){
        	withAttribute(o);
        }
    }


	public final boolean isAsynchronous() {
        return asynchronous;
    }

    public Object setResult(Object value) {
    	if(isEditable()){
	        Object old = result;
	        result = value;
	        return old;
    	}
    	throw new IllegalStateException("Message is not editable: " + this);
    }

    public Object getResult() {
        return this.result;
    }

}
