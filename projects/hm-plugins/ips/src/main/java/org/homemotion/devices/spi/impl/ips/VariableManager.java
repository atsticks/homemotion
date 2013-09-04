package org.homemotion.devices.spi.impl.ips;

public interface VariableManager {

	public boolean getBooleanValue(String name, int parentID);
	public int getIntegerValue(String name, int parentID);
	public String getStringValue(String name, int parentID);
	public double getFloatValue(String name, int parentID);
	
	public void setValue(boolean value, String name, int parentID);
	public void setValue(int value, String name, int parentID);
	public void setValue(String value, String name, int parentID);
	public void setValue(double value, String name, int parentID);
	
	// todo add array types
}
