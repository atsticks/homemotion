package org.homemotion.scheduler.impl;

import javax.persistence.Transient;

//import org.homemotion.common.events.AbstractEvent;
//import org.homemotion.macros.MacroEngine;
//import org.homemotion.scheduler.spi.AbstractTrigger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class Scriptable extends AbstractTrigger{
//
//	private static final long serialVersionUID = -6130954132723624047L;
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(Scriptable.class);
//
//	private String script;
//
//	private String scriptingEngine;
//	
//	
//	@Transient
//	public boolean isInterested(Notification event) {
//		String engineID = getEngineID();
//		String script = getScript();
//		MacroEngine engine = getMacroManager().getExecutionEngine(engineID);
//		if (engine == null) {
//			LOGGER.error("Error executing trigger " + this
//					+ ": no such engine: " + engineID);
//			return false;
//		}
//		try {
//			Object result = engine.execute(script);
//			if (result instanceof Boolean) {
//				return ((Boolean) result).booleanValue();
//			} else {
//				LOGGER
//						.error("Invalid trigger script resturn type, must be java.lang.Boolean for: "
//								+ this);
//				return false;
//			}
//		} catch (Exception e) {
//			LOGGER.error("Error executing trigger: " + this, e);
//		}
//		return true;
//	}
//
//	public String getScript() {
//		return getTrigger().getAttribute(SCRIPT, String.class);
//	}
//
//	public void setScript(String script) {
//		getTrigger().setAttribute(SCRIPT, script);
//	}
//
//	public String getEngineID() {
//		return getTrigger().getAttribute(SCRIPT_ENGINE, String.class);
//	}
//
//	public void setEngineID(String engineID) {
//		getTrigger().setAttribute(SCRIPT_ENGINE, engineID);
//	}
//
//	@Override
//	public String toString() {
//		String engineID = getEngineID();
//		String script = getScript();
//		return "ScriptableEventTrigger ["
//				+ (script != null ? "script=" + script + ", " : "")
//				+ (engineID != null ? "engineID=" + engineID + ", " : "") + "]";
//	}
//
//	@Override
//	public void configure(String config) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
