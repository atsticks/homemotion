package org.homemotion.dao.spi;

// package org.homemotion.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.homemotion.common.config.Configuration;
import org.homemotion.common.config.ConfigurationService;
import org.homemotion.dao.Attributable;
import org.homemotion.dao.Identifiable;
import org.homemotion.dao.NamedItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfiguredItemManager<T extends Identifiable>
		implements NamedItemManager<T> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final Map<String, T> items = new ConcurrentHashMap<String, T>();

	protected final Class<T> itemClass;

	protected final String binding;

	protected ConfigurationService configurationService;

	
	public AbstractConfiguredItemManager(Class<T> itemClass, String binding,
			ConfigurationService configurationService) {
		if (itemClass == null) {
			throw new IllegalArgumentException("Item class must not be null.");
		}
		if (binding == null) {
			throw new IllegalArgumentException("binding must not be null.");
		}
		this.itemClass = itemClass;
		this.binding = binding;
		this.configurationService = configurationService;
	}

	public Set<String> getIdentifiers() {
		return items.keySet();
	}

	public String getBinding() {
		return binding;
	}

	protected final void load() {
		Configuration cfg = configurationService.getConfiguration(binding);
		load(cfg, this.items);
	}

	protected abstract void load(Configuration configuration,
			Map<String, T> items);

	public void load(String type, String configuredItem) {
		throw new UnsupportedOperationException();
	}

	public void create(T item) {
		throw new UnsupportedOperationException(
				"Creation of JSON configured items is not supported.");
	}

	public T get(String id) {
		return this.items.get(id);
	}

	public void delete(T itemToDelete) {
		try {
			this.items.remove(itemToDelete.getIdentifier());
		} catch (Exception e) {
			throw new IllegalStateException("Failed to delete instance: "
					+ itemToDelete, e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findItems(String idExpression) {
		List<T> result = new ArrayList<T>();
		for (Entry<String, T> item : this.items.entrySet()) {
			if (item.getKey().matches(idExpression)) {
				result.add(item.getValue());
			}
		}
		return result;
	}

	public List<T> findItems(Map<String, Object> predicates) {
		if (!Attributable.class.isAssignableFrom(itemClass)) {
			return Collections.emptyList();
		}
		List<T> items = new ArrayList<T>();
		Collection<T> all = getAllItems();
		for (T item : all) {
			boolean itemMatch = true;
			for (Map.Entry<String, Object> entry : predicates.entrySet()) {
				Object value = ((Attributable) item).getAttribute(
						entry.getKey(), Object.class);
				if (value == null) {
					itemMatch = false;
					break;
				}
				if (!value.equals(entry.getValue())) {
					itemMatch = false;
					break;
				}
			}
			if (itemMatch) {
				items.add(item);
			}
		}
		return items;
	}

	@Override
	public Collection<T> getAllItems() {
		return new ArrayList<T>(this.items.values());
	}

	@Override
	public long getSize() {
		return this.items.size();
	}

	public T update(T item) {
		if (!this.items.containsKey(item.getIdentifier())) {
			throw new IllegalStateException("Item is not existing: " + item);
		}
		return item;
	}

	public T refresh(T item) {
		if (!this.items.containsKey(item.getIdentifier())) {
			throw new IllegalStateException("Item is not existing: " + item);
		}
		return item;
	}

	public boolean isPersistent(T item) {
		return this.items.containsKey(item.getIdentifier());
	}

	protected String getCurrentUserID() {
		return "N/A";
	}

	protected void setCreationFields(T item) {
		if (item instanceof AbstractEntity) {
			((AbstractEntity) item).setCreatedFrom(getCurrentUserID());
			((AbstractEntity) item).setUpdatedFrom(((AbstractEntity) item)
					.getCreatedFrom());
		}
	}

	protected void setUpdateFields(T item) {
		if (item instanceof AbstractEntity) {
			((AbstractEntity) item).setUpdatedDT(new Date());
			((AbstractEntity) item).setUpdatedFrom(getCurrentUserID());
		}
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.unmodifiableCollection(this.items.values())
				.iterator();
	}

}
