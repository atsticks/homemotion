package org.homemotion.dao;

import java.util.Collection;
import java.util.Map;

public interface NamedItemManager<T extends Identifiable> extends Iterable<T> {

	public T get(String id);

	public Collection<T> findItems(String nameExpression);

	public Collection<T> findItems(Map<String, Object> predicate);

	public Collection<T> getAllItems();

	public T update(T item);

	public void delete(T item);

	/**
	 * Get the count of all Users.<br>
	 * 
	 * @return the number of items for this table.
	 */
	public long getSize();

}
