package org.homemotion.dao.spi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.Transient;

import org.homemotion.adapt.Adaptable;
import org.homemotion.adapt.Adapter;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractAdaptable implements Adaptable {

    @Transient
    private Map<Class, Adapter> adapters = new HashMap<Class, Adapter>();
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "ADAPTER_CLASS")
    private Set<String> adapterClasses = new HashSet<String>();
    private Object LOCK = new Object();

    public Class<?>[] getAssignedAdapters() {
        return adapters.keySet().toArray(new Class[adapters.size()]);
    }

    public boolean isAdaptable(Class type) {
        return this.getClass().isAssignableFrom(type) || this.adapters.containsKey(type);
    }

    public <T> Adapter<T> getAdapterByType(Class<T> type) {
        return (Adapter<T>) this.adapters.get(type);
    }

    public <T> Adapter<T> removeAdapterByType(Class<T> type) {
        synchronized (LOCK) {
            for (Adapter a : adapters.values()) {
                if (a.getTargetType().equals(type)) {
                    adapterClasses.remove(a.getClass().getName());
                    return (Adapter<T>) adapters.remove(a.getTargetType());
                }
            }
            return null;
        }
    }

    public final Adapter setAdapter(Class targetType, Adapter adapter) {
        synchronized (LOCK) {
            if (adapter == null) {
                return removeAdapterByType(targetType);
            }
            this.adapterClasses.add(adapter.getClass().getName());
            adapter.init(this, targetType);
            return adapters.put(targetType, adapter);
        }
    }

    public final <A extends Adapter> A getAdapter(Class<A> adapterType) {
        synchronized (LOCK) {
            return (A) adapters.get(adapterType);
        }
    }

    public final Collection<Adapter> getAdapters() {
        return Collections.unmodifiableCollection(adapters.values());
    }

    @Override
    public final <A extends Adapter> A removeAdapter(Class<A> adapterType) {
        synchronized (LOCK) {
            this.adapterClasses.remove(adapterType.getName());
            for (Adapter a : adapters.values()) {
                if (a.getClass().equals(adapterType)) {
                    adapterClasses.remove(a.getClass().getName());
                    return (A) adapters.remove(a.getTargetType());
                }
            }
            return null;
        }
    }

    public String getSimpleAdapterNames() {
        if (getAdapterClasses().size() == 0) {
            return "-";
        }
        StringBuilder result = new StringBuilder();
        for (String className : this.adapterClasses) {
            result.append(getSimpleName(className));
            result.append(',');
        }
        result.setLength(result.length() - 1);
        return result.toString();
    }

    protected final String getSimpleName(String className) {
        int index = className.lastIndexOf('.');
        if (index >= 0) {
            return className.substring(index + 1);
        }
        return className;
    }

    public final void setAdapterClasses(Set<String> adapterClasses) {
        this.adapterClasses = adapterClasses;
        if (adapterClasses != null) {
            for (String className : adapterClasses) {
                try {
                    Class<Adapter> clazz = (Class<Adapter>) Class
                            .forName(className, true, Thread.currentThread()
                            .getContextClassLoader());
                    Adapter adapter = (Adapter) clazz.newInstance();
                    setAdapter(adapter.getTargetType(), adapter);

                } catch (Exception e) {
                    LoggerFactory.getLogger(getClass()).error("Error adapting device " + this, e);
                }
            }
        }
    }

    public final Collection<Class> getAdapterClasses() {
        return Collections.unmodifiableCollection(adapters.keySet());
    }

    public <T> T adapt(Class<T> type) {
        if (type.isAssignableFrom(this.getClass())) {
            return (T) this;
        }
        if (!isAdaptable(type)) {
            throw new IllegalArgumentException("Invalid adapter target type encountered: " + type);
        }
        try {
            Adapter adapter = adapters.get(type);
            if (adapter != null) {
                return (T) adapter.getTarget();
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Error adapting to target: " + type, e);
        }
        return null;
    }

    @Override
    public boolean isAdapted(Class<Adapter> adapterClass) {
        return this.adapterClasses.contains(adapterClass);
    }

    private void readObject(ObjectInputStream ois)
            throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        this.adapters = new HashMap<Class, Adapter>();
        setAdapterClasses(this.adapterClasses);
    }
}
