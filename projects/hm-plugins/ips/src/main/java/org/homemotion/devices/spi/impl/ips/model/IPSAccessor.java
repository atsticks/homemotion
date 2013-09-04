package org.homemotion.devices.spi.impl.ips.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.homemotion.ItemDescriptor;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;
import org.homemotion.devices.ClimateControlDevice;
import org.homemotion.devices.SensorDevice;
import org.homemotion.devices.spi.impl.ips.IPSDeviceType;
import org.homemotion.devices.spi.impl.ips.IPSServer;
import org.homemotion.di.Registry;
import org.homemotion.events.Event;

public final class IPSAccessor {
	
	private static final Logger LOGGER = Logger.getLogger(IPSAccessor.class);
	private static final String ID_PARAM = "";
	private static final String TEMPARATURE_PARAM = "temparature";
	private static final String HOUR_PARAM = "h";
	private static final String YEAR_PARAM = "y";
	private static final String MONTH_PARAM = "m";
	private static final String DAY_PARAM = "d";
	private static final String ADDRESS_PARAM = "address";
	private static final String OK_VALUE = "OK";

	
	private IPSAccessor() {
		// Singleton
	}
	
	public static boolean setDeviceAddress(ClimateControlDevice device, String address) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			params.put(ADDRESS_PARAM, address);
			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction("fht.php?cmd=setAddress",
					params));
		} catch (Exception e) {
			throw new RuntimeException("setDeviceAddress on of device failed.",
					e);
		}
	}

	public static boolean setDateTime(ClimateControlDevice device, Calendar dateTime) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			params.put(DAY_PARAM, String.valueOf(dateTime
							.get(Calendar.DAY_OF_MONTH)));
			params.put(MONTH_PARAM, String.valueOf(dateTime.get(Calendar.MONTH) + 1));
			params.put(YEAR_PARAM, String.valueOf(dateTime.get(Calendar.YEAR)));
			params.put(HOUR_PARAM, String.valueOf(dateTime.get(Calendar.HOUR_OF_DAY)));
			params.put(MONTH_PARAM, String.valueOf(dateTime.get(Calendar.MINUTE)));
			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction(
					"fht.php?cmd=setDateTime", params));
		} catch (Exception e) {
			throw new RuntimeException("setDateTime on of device failed.", e);
		}
	}


	public static Device getDevice(Event evt) {
		Device device = null;
		if(evt.getOwner() instanceof Device){
			device = (Device)evt.getOwner();
		}
		ItemDescriptor devDesc = device.getDescriptor();
		if(devDesc==null){
			return null;
		}
		if(!devDesc.getProviderID().equals("IPS")){
			return null;
		}
		int ipsID = 0;
		try {
			ipsID = Integer.parseInt(devDesc.getItemID());
		} catch (Exception e) {
			return null;
		}
		IPSDeviceType deviceType = null;
		try {
			deviceType = IPSDeviceType.valueOf(devDesc.getItemType());
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Invalid FS device type encountered for " + device
							+ ", was " + devDesc);
		}
		return device;
	}


//	@Override
//	public boolean synch() {
//		LOGGER.error("synch is not yet implemented.");
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(getIpsID()));
//			String result = Registry.getInstance(IPSServer.class).callFunction("fht.php?cmd=requestData",
//					params);
//			return OK_VALUE.equals(result);
//		} catch (Exception e) {
//			throw new RuntimeException("dimDown of device failed.", e);
//		}
//	}


}
