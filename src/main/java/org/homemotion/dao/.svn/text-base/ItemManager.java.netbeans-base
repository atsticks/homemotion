package org.homemotion.dao;

import java.util.Collection;

public interface ItemManager<T, K> extends Iterable<T> {

	public void create(T item);

	public T get(K id);

	public Collection<T> findItems(String nameExpression);

	public Collection<T> getAllItems();

	public T update(T item);

	public void delete(T item);

	public boolean isPersistent(T item);

	public T refresh(T item);

	/**
	 * Get the count of all Users.<br>
	 * 
	 * @return the number of items for this table.
	 */
	public long getSize();

}
