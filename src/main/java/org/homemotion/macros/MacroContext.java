package org.homemotion.macros;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MacroContext {

	private MacroContext() {
	}

	private static final Map<String, Object> GLOBALS = new ConcurrentHashMap<String, Object>();
	private static final ThreadLocal<Map<String, Object>> CONTEXTS = new ThreadLocal<Map<String, Object>>() {
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	public static Object setGlobalValue(String key, Object value) {
		return GLOBALS.put(key, value);
	}

	public static Object getGlobalValue(String key) {
		return GLOBALS.get(key);
	}

	public static void init() {
		if (CONTEXTS.get().size() == 0) {
			CONTEXTS.get().putAll(GLOBALS);
		}
	}

	public static void init(Map<String, Object> initialContext) {
		init();
		CONTEXTS.get().putAll(initialContext);
	}

	public static void clear() {
		CONTEXTS.get().clear();
	}

	public static Object setValue(String key, Object value) {
		return CONTEXTS.get().put(key, value);
	}

	public static Object getValue(String key) {
		return CONTEXTS.get().get(key);
	}

	public static void printContext() {
		printContext(System.out);
	}

	public static void printContext(PrintStream stream) {
		stream.println("MAKROCONTEXT:");
		stream.println("-------------");
		stream.println(CONTEXTS.get());
	}

}
