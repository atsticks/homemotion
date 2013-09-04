package org.homemotion.devices.spi.impl.ips;

public final class IPSVar {
    private int ipsID;
	private String name;
	private Class<?> type;
	private Object value;
	private boolean dirty;
	
	public IPSVar(String ipsInfo){
		int index = ipsInfo.indexOf(':');
		if(index < 0){
			throw new IllegalArgumentException("Invalid format, expected format ID:Name");
		}
		try{
			this.ipsID = Integer.parseInt(ipsInfo.substring(0,index));
			this.name = ipsInfo.substring(index+1);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid ID (not an int), expected format ID:Name");
		}
	}
	
	public void parseValue(String ipsValue){
		this.dirty = false;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if(value==this.value){
			return;
		}
		else if(value!=null){
			if(value.equals(this.value)){
				return;
			}
		}
		this.value = value;
		this.dirty = true;
	}

	public int getIpsID() {
		return ipsID;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}
	
	public boolean isDirty(){
		return this.dirty;
	}
	
	public final static String getVarName(String fqVarName) {
		String varName = fqVarName;
		if (varName.startsWith("[")) {
			varName = varName.substring(1);
		}
		if (varName.endsWith("]")) {
			varName = varName.substring(0, varName.length() - 1);
		}
		int index = varName.lastIndexOf(':');
		if (index > 0) {
			return varName.substring(index + 1);
		}
		return varName;
	}
}
