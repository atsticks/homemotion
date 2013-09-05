package org.homemotion.adapt.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.Extension;
import org.homemotion.adapt.Adapter;
import org.homemotion.adapt.AdapterManager;
import org.homemotion.common.system.AnnotationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@SuppressWarnings("rawtypes")
public class AdapterManagerImpl implements AdapterManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdapterManagerImpl.class);

	private Map<String, Set<Class>> registeredAdapterClasses = new ConcurrentHashMap<String, Set<Class>>();

	@Inject
	private AnnotationManager annotationManager;

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<? extends Adapter<T>>[] getAdapters(Class<T> targetType) {
		Set<Class> result = registeredAdapterClasses.get(targetType.getName());
		if (result != null) {
			synchronized (result) {
				return result.toArray(new Class[result.size()]);
			}
		}
		return new Class[0];
	}

	@Override
	public <T> void registerAdapter(Class<Adapter<T>> adapterType,
			Class<T> targetType) {
		Set<Class> result = registeredAdapterClasses.get(targetType.getName());
		if (result == null) {
			result = new HashSet<Class>();
			registeredAdapterClasses.put(targetType.getName(), result);
		}
		synchronized (result) {
			if (!result.contains(adapterType)) {
				result.add(adapterType);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void load() {
		Set<String> extensionClasses = annotationManager
				.getAnnotatedClasses(Extension.class.getName());
		if (extensionClasses != null) {
			for (String className : extensionClasses) {
				try {
					Class c = Class.forName(className);
					if (Adapter.class.isAssignableFrom(c)) {
						Extension extension = (Extension) c
								.getAnnotation(Extension.class);
						registerAdapter(extension.target(), c);
					}
				} catch (Exception e) {
					LOGGER.error("Error registering adapter: " + className, e);
				}
			}
		}
	}

}
