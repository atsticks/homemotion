package org.homemotion.common.context.impl;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;

import org.apache.commons.lang.ObjectUtils;
import org.homemotion.common.context.ItemContextManager;
import org.homemotion.common.context.ItemRuntimeContext;
import org.homemotion.dao.spi.AbstractEntity;

@Singleton
public class ItemContextManagerImpl implements ItemContextManager {

	private Map<String, ItemRuntimeContext> hardContexts = new ConcurrentHashMap<String, ItemRuntimeContext>();
	private Map<String, SoftReference<ItemRuntimeContext>> softContexts = new ConcurrentHashMap<String, SoftReference<ItemRuntimeContext>>();

	protected String getItemId(Object item) {
		if (item instanceof AbstractEntity) {
			return item.getClass().getName() + ':'
					+ ((AbstractEntity) item).getId();
		} else if (item instanceof AbstractEntity) {
			return item.getClass().getName() + ':'
					+ ((AbstractEntity) item).getId();
		}
		return ObjectUtils.identityToString(item);
	}

	@Override
	public ItemRuntimeContext getContext(Object item) {
		return getContext(item, true);
	}

	@Override
	public ItemRuntimeContext getContext(Object item, boolean createIfMissing) {
		ItemRuntimeContext ctx = this.hardContexts.get(getItemId(item));
		if (ctx == null) {
			SoftReference<ItemRuntimeContext> ref = this.softContexts.get(getItemId(item));
			if(ref!=null){
				ctx = ref.get();
			}
		}
		if (ctx == null) {
			ctx = new ItemRuntimeContext(item);
			this.softContexts.put(getItemId(item),
					new SoftReference<ItemRuntimeContext>(ctx));
		}
		return ctx;
	}

	@Override
	public ItemRuntimeContext getHardContext(Object item,
			boolean createIfMissing) {
		ItemRuntimeContext ctx = this.hardContexts.get(getItemId(item));
		if (ctx == null) {
			ctx = new ItemRuntimeContext(item);
			this.hardContexts.put(getItemId(item), ctx);
		}
		return ctx;
	}

	@Override
	public ItemRuntimeContext removeContext(Object item) {
		String key = getItemId(item);
		SoftReference<ItemRuntimeContext> ref = this.softContexts.remove(key);
		if (ref != null) {
			return ref.get();
		}
		return this.hardContexts.remove(key);
	}

	@Override
	public void clear() {
		this.hardContexts.clear();
		this.softContexts.clear();
	}

}
