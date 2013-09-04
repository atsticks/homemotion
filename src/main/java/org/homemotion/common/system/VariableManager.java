package org.homemotion.common.system;

import org.homemotion.dao.ItemManager;

public interface VariableManager extends ItemManager<Variable, String> {

	public boolean define(String name, Class<?> type);

	public Class<?> getVariableType(String name);

	public Object getValue(String name);

	public <T> T getValue(String name, Class<T> type);

	public void setValue(String name, Object value);

}
