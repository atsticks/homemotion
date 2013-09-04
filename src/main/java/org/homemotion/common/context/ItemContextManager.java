package org.homemotion.common.context;

public interface ItemContextManager {

	public ItemRuntimeContext getContext(Object item);
	
	public ItemRuntimeContext getContext(Object item, boolean createIfMissing);
	
	public ItemRuntimeContext getHardContext(Object item, boolean createIfMissing);
	
	public ItemRuntimeContext removeContext(Object item);
	
	public void clear();
	
}
