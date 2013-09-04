package org.homemotion.devices.spi.impl.ips.model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.homemotion.devices.AbstractItemEvent;
import org.homemotion.devices.Device;
import org.homemotion.devices.ClimateControlDevice;
import org.homemotion.devices.OperationEvent;
import org.homemotion.devices.spi.impl.ips.IPSServer;
import org.homemotion.di.Registry;

public final class IPSHeatingDeviceListener extends AbstractIPSDeviceListener {
	private static final Logger LOGGER = Logger
			.getLogger(IPSHeatingDeviceListener.class);

	private static final String TEMPARATURE_PARAM = "temparature";
	private static final String HOUR_PARAM = "h";
	private static final String YEAR_PARAM = "y";
	private static final String MONTH_PARAM = "m";
	private static final String DAY_PARAM = "d";
	private static final String ADDRESS_PARAM = "address";
	private static final String OK_VALUE = "OK";

	protected void onIPSDeviceEvent(AbstractItemEvent evt, Device device) {
		if (evt instanceof OperationEvent){
			OperationEvent oe = (OperationEvent)evt;
			if(evt.getOwner() instanceof ClimateControlDevice) {
				ClimateControlDevice hd = (ClimateControlDevice)oe.getOwner();
				if("setTargetTemparature".equals(oe.getOperation())){
					setTargetTemparature((Float)oe.getParam(0), hd);
				}
				if("temparatureDown".equals(oe.getOperation())){
					temparatureDown(hd);
				}
				if("temparatureUp".equals(oe.getOperation())){
					temparatureUp(hd);
				}
				if("synch".equals(oe.getOperation())){
					synch(hd);
				}
				if("synchTime".equals(oe.getOperation())){
					synchTime(hd);
				}
			}
		}
		
	}

	public boolean setDeviceAddress(String address, Device device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor()
					.getItemID()));
			params.put(ADDRESS_PARAM, address);
			return OK_VALUE.equals(Registry.getInstance(IPSServer.class)
					.callFunction("fht.php?cmd=setAddress", params));
		} catch (Exception e) {
			throw new RuntimeException("setDeviceAddress on of device failed.",
					e);
		}
	}

	private boolean synchTime(ClimateControlDevice device) {
		try {
			Calendar dateTime = Calendar.getInstance();
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor()
					.getItemID()));
			params.put(DAY_PARAM, String.valueOf(dateTime
					.get(Calendar.DAY_OF_MONTH)));
			params.put(MONTH_PARAM, String
					.valueOf(dateTime.get(Calendar.MONTH) + 1));
			params.put(YEAR_PARAM, String.valueOf(dateTime.get(Calendar.YEAR)));
			params.put(HOUR_PARAM, String.valueOf(dateTime
					.get(Calendar.HOUR_OF_DAY)));
			params.put(MONTH_PARAM, String.valueOf(dateTime
					.get(Calendar.MINUTE)));
			return OK_VALUE.equals(Registry.getInstance(IPSServer.class)
					.callFunction("fht.php?cmd=setDateTime", params));
		} catch (Exception e) {
			LOGGER.error("Error synching date/time for " + device,e);
			return false;
		}
	}

	// else if($command == "setDate"){
	// $year = (Integer)$_GET['y'];
	// $month = (Integer)$_GET['m'];
	// $day = (Integer)$_GET['d'];
	// $result = FHT_SetYear($id, $year);
	// $result = FHT_SetMonth($id, $year);
	// $result = FHT_SetDay($id, $day);
	// }
	// else if($command == "setTime"){
	// $hour = (Integer)$_GET['h'];
	// $minute = (Integer)$_GET['m'];
	// $result = FHT_SetHour($id, $hour);
	// $result = FHT_SetMinute($id, $minute);
	// }
	// else if($command == "requestData"){
	// $result = FHT_ReuqestData($id);
	// }
	// else if($command == "getAddress"){
	// print_r(FHT_GetAddress($id));
	// return;
	// }

	// @Override
	// public boolean setMode(ClimateControlDevice device, HeatingMode mode) {
	// IPSDevice ipsDevice = InstanceParser.getIPSDevice(device);
	// try {
	// Map<String, String> params = new HashMap<String, String>();
	// ipsDevice.addParams(params);
	// switch (mode) {
	// case AUTO:
	// params.put("mode", String.valueOf("auto"));
	// break;
	// case DAY:
	// params.put("mode", String.valueOf("day"));
	// break;
	// case MANUAL:
	// params.put("mode", String.valueOf("manual"));
	// break;
	// case NIGHT:
	// params.put("mode", String.valueOf("night"));
	// break;
	// default:
	// return false;
	// }
	// String result = ipsServer.callFunction(
	// "fht.php?cmd=setTemparature", params);
	// return "OK".equals(result);
	// } catch (Exception e) {
	// throw new RuntimeException("dimDown of device failed.", e);
	// }
	// }

	private boolean setTargetTemparature(float value, ClimateControlDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor()
					.getItemID()));
			params.put(TEMPARATURE_PARAM, String.valueOf(value));
			String result = Registry.getInstance(IPSServer.class).callFunction(
					"fht.php?cmd=setTemparature", params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			throw new RuntimeException("dimDown of device failed.", e);
		}
	}

	private boolean temparatureDown(ClimateControlDevice device) {
		device.setTargetTemparature(device.getTargetTemparature() - 0.5f);
		return true;
	}

	private boolean temparatureUp(ClimateControlDevice device) {
		device.setTargetTemparature(device.getTargetTemparature() + 0.5f);
		return true;
	}

	private boolean synch(Device device) {
		LOGGER.error("synch is not yet implemented.");
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor()
					.getItemID()));
			String result = Registry.getInstance(IPSServer.class).callFunction(
					"fht.php?cmd=requestData", params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			throw new RuntimeException("dimDown of device failed.", e);
		}
	}

}
