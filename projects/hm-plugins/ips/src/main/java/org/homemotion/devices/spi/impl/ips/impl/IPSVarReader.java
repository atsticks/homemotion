package org.homemotion.devices.spi.impl.ips.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.homemotion.ItemDescriptor;
import org.homemotion.devices.AbstractItemEvent;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;
import org.homemotion.devices.spi.impl.ips.IPSDeviceType;
import org.homemotion.devices.spi.impl.ips.IPSVar;
import org.homemotion.di.Registry;
import org.homemotion.events.Event;
import org.homemotion.events.EventListener;

public final class IPSVarReader{

	protected final Logger logger = Logger.getLogger(getClass());

	private IPSVarReader() {
		// Singleton
	}
	
	public static Map<String, IPSVar> parseVariables(String ipsVarList) throws IOException{
		Map<String, IPSVar> vars = new HashMap<String, IPSVar>();
		if (ipsVarList == null) {
			throw new IllegalArgumentException("Can not handle null.");
		}
		if (ipsVarList.isEmpty()) {
			return vars;
		}
		Map<String, IPSVar> newVars = new HashMap<String, IPSVar>();
		BufferedReader input = new BufferedReader(new StringReader(ipsVarList));
		String line = null;
		try{
			line = input.readLine();
			while(line!=null){
				IPSVar var = new IPSVar(line);
				vars.put(var.getName(), var);
			}
		}
		finally{
			if(input!=null){
				input.close();
			}
		}
		return vars;
	}
	
}
