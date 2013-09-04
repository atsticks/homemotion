package org.homemotion.devices.spi.impl.ips.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.homemotion.devices.AbstractItemEvent;
import org.homemotion.devices.Device;
import org.homemotion.devices.spi.impl.ips.IPSVar;
import org.homemotion.events.Event;
import org.homemotion.events.EventListener;

public abstract class AbstractIPSDeviceListener  implements EventListener{

	protected final Logger logger = Logger.getLogger(getClass());
	
	private static final String IPS_PROVIDER_ID = "IPS";

	protected static final String ID_PARAM = "id";
	

	protected AbstractIPSDeviceListener(){
		logger.info("Registered IPS Device Listener: " + getClass());
	}
	
	public boolean onEvent(Event event){
		if(event instanceof AbstractItemEvent){
			AbstractItemEvent evt = (AbstractItemEvent)event;
			if(!(evt.getOwner() instanceof Device)){
				return false;
			}
			Device device = IPSAccessor.getDevice(event);
			if(device!=null){
				if(device.getDescriptor()!=null && device.getDescriptor().getProviderID().equals(IPS_PROVIDER_ID)){
					onIPSDeviceEvent(evt, device);
				}
			}
		}
		return false;
	}
	

	protected abstract void onIPSDeviceEvent(AbstractItemEvent evt, Device device);

	
}
