package org.homemotion.adapt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractAdaptable implements Adaptable {

	private Map<Class, Adapter> adapters = new HashMap<Class, Adapter>();

	private Object LOCK = new Object();

	public Collection<Class> getAdapterClasses() {
		return adapters.keySet();
	}

	public boolean isAdaptable(Class type) {
		return this.getClass().isAssignableFrom(type)
				|| this.adapters.containsKey(type);
	}

	public <T> Adapter<T> getAdapterByType(Class<T> type) {
		return (Adapter<T>) this.adapters.get(type);
	}

	public <T> Adapter<T> removeAdapterByType(Class<T> type) {
		synchronized (LOCK) {
			for (Adapter a : adapters.values()) {
				if (a.getTargetType().equals(type)) {
					return (Adapter<T>) adapters.remove(a.getTargetType());
				}
			}
			return null;
		}
	}

	@Override
	public <T> Adapter<T> setAdapter(Class<T> targetType,
			Adapter<T> adapter) {
		synchronized (LOCK) {
			if (adapter == null) {
				return removeAdapterByType(targetType);
			}
			adapter.init((Adapter<T>)this, targetType);
			return adapters.put(targetType, adapter);
		}
	}

	public final <A extends Adapter> A getAdapter(Class<A> adapterType) {
		synchronized (LOCK) {
			return (A) adapters.get(adapterType);
		}
	}
	
	@Transient
	public final Collection<Adapter> getAdapters() {
		return adapters.values();
	}

	@Override
	public final <A extends Adapter> A removeAdapter(Class<A> adapterType) {
		synchronized (LOCK) {
			for (Adapter a : adapters.values()) {
				if (a.getClass().equals(adapterType)) {
					return (A) adapters.remove(a.getTargetType());
				}
			}
			return null;
		}
	}

	public final void setAdapterClasses(
			Collection<Class<? extends Adapter>> adapterClasses) {
		if (adapterClasses != null) {
			this.adapters.clear();
			for (Class<? extends Adapter> clazz : adapterClasses) {
				try {
					Adapter adapter = (Adapter) clazz.newInstance();
					setAdapter(adapter.getTargetType(), adapter);
				} catch (Exception e) {
					LoggerFactory.getLogger(getClass()).error(
							"Error adapting device " + this, e);
				}
			}
		}
	}

	public <T> T adapt(Class<T> type) {
		if (type.isAssignableFrom(this.getClass())) {
			return (T) this;
		}
		if (!isAdaptable(type)) {
			throw new IllegalArgumentException(
					"Invalid adapter target type encountered: " + type);
		}
		try {
			Adapter adapter = adapters.get(type);
			if (adapter != null) {
				return (T) adapter.getTarget();
			}
		} catch (Exception e) {
			LoggerFactory.getLogger(getClass()).error(
					"Error adapting to target: " + type, e);
		}
		return null;
	}

	@Override
	public boolean isAdapted(Class<Adapter> adapterClass) {
		return this.adapters.containsKey(adapterClass);
	}
	
}
