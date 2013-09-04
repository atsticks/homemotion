package org.homemotion.adapt;

import java.util.Collection;



@SuppressWarnings("rawtypes")
public interface Adaptable {
	public Collection<Class> getAdapterClasses();
	public Collection<Adapter> getAdapters();
	public <A extends Adapter>  Adapter getAdapter(Class<A> adapterClass);
	public boolean isAdaptable(Class type);
	public <T> Adapter<T> setAdapter(Class<T> targetType, Adapter<T> adapter);
	public <T> Adapter<T> getAdapterByType(Class<T> type);
	public <A extends Adapter> A removeAdapter(Class<A> adapterType);
	public <T> Adapter<T> removeAdapterByType(Class<T> adapterType);
	public <T>  T adapt(Class<T> adapterClass);
	public boolean isAdapted(Class<Adapter> adapterClass);
}
