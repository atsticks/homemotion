package org.homemotion.dao.spi;

import org.homemotion.adapt.Adapter;


@SuppressWarnings("unchecked")
public abstract class AbstractAdapter<T> implements Adapter<T> {

	private T target;
	private Object source;
	private Class<T> targetType;
	

	@Override
	public void init(Object source, Class<T> targetType) {
		this.source = source;
		this.targetType = targetType;
	}
	
	public void reset(){
		// by default this is empty
	}
	
	public String getName(){
		return '[' + getClass().getSimpleName() + ']';
	}
	
	public String getDescription(){
		return null;
	}
	
	public T getTarget() {
		if(this.target==null){
			this.target = createTarget();
		}
		return this.target;
	}
	
	public Object getSource() {
		return source;
	}
	
	public Class<?> getSourceType(){
		return (Class<?>) source.getClass();
	}
	
	public Class<T> getTargetType(){
		return targetType;
	}
	
	protected abstract T createTarget();

}
