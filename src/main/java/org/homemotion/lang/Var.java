package org.homemotion.lang;

import java.math.BigDecimal;

import org.homemotion.common.system.Container;
import org.homemotion.common.system.VariableManager;

public final class Var {

	private Var() {
	}

	public static <T> T get(String key, Class<T> type) {
		return Container.getInstance(VariableManager.class).getValue(key, type);
	}

	public static boolean getBoolean(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Boolean.class);
	}

	public static byte getByte(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Byte.class);
	}

	public static int getInt(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Integer.class);
	}

	public static long getLong(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Long.class);
	}

	public static short getShort(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Short.class);
	}

	public static float getFloat(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				Float.class);
	}

	public static double getDouble(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				double.class);
	}

	public static BigDecimal getBigDecimal(String key) {
		return Container.getInstance(VariableManager.class).getValue(key,
				BigDecimal.class);
	}

	public static void setBoolean(String key, boolean value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setByte(String key, byte value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setShort(String key, short value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setInt(String key, int value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setLong(String key, long value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setFloat(String key, float value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}

	public static void setDouble(String key, double value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}
	
	public static <T> void set(String key, T value) {
		set(key, (Class<T>)value.getClass(), value);
	}
	
	public static <T> void set(String key, Class<T> type, T value) {
		Container.getInstance(VariableManager.class).setValue(key, value);
	}
}
