package org.homemotion.devices.spi.impl.ips.impl;

public final class FS20IntensityConverter {

	private FS20IntensityConverter(){};
	
	public static int getFS20Value(int prozValue){
		if(prozValue<0 || prozValue>100){
			throw new IllegalArgumentException("Percentage value must be between 0 and 100, was: " + prozValue);
		}
		return 16/100*prozValue;
	}
	
	public static int getIntensity(int fs20Value){
		return fs20Value/16*100;
	}
	
}
