package org.homemotion.common.context;

import org.homemotion.dao.spi.AbstractAttributableItem;

public final class ItemRuntimeContext extends AbstractAttributableItem {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1757763979900912194L;
	private Object item;

	public ItemRuntimeContext(Object item) {
		if (item == null) {
			throw new IllegalArgumentException("Item masu not be null.");
		}
		this.item = item;
	}

	public Object getItem() {
		return this.item;
	}

}
