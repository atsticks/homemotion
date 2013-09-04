package org.homemotion.devices.spi.impl.ips.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.homemotion.devices.AbstractItemEvent;
import org.homemotion.devices.Device;
import org.homemotion.devices.OperationEvent;
import org.homemotion.devices.ActorDevice;
import org.homemotion.devices.spi.impl.ips.IPSServer;
import org.homemotion.devices.spi.impl.ips.impl.FS20IntensityConverter;
import org.homemotion.di.Registry;

import com.google.inject.Inject;

public final class IPSSwitchDeviceListener extends AbstractIPSDeviceListener {

	private static final Logger LOGGER = Logger.getLogger(IPSSwitchDeviceListener.class);
	
//	private static final String HOME_CODE_PARAM = "homeCode";
//	private static final String ADDRESS_PARAM = "address";
//	private static final String SUBADDRESS_PARAM = "subaddress";
//	private static final String ENABLE_PARAM = "enable";
//	private static final String DURATION_PARAM = "duration";
	private static final String OK_VALUE = "OK";
//	private static final String CODE_PARAM = "code";

	@Inject
	public IPSSwitchDeviceListener() {
	}

	private boolean dimDown(ActorDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			String result = Registry.getInstance(IPSServer.class).callFunction("fs20.php?cmd=dimDown",
					params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			LOGGER.error("dimDown of device failed.", e);
			return true;
		}
	}

	private boolean dimUp(ActorDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			String result = Registry.getInstance(IPSServer.class)
					.callFunction("fs20.php?cmd=dimUp", params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			LOGGER.error("dimUp of device failed.", e);
			return true;
		}
	}


	private boolean switchOff(ActorDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			String result = Registry.getInstance(IPSServer.class).callFunction("fs20.php?cmd=switchOff",
					params);
			result = Registry.getInstance(IPSServer.class).callFunction("switchOff", params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			LOGGER.error("switchOff of device failed.", e);
			return false;
		}
	}

	private boolean switchOn(ActorDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			String result = Registry.getInstance(IPSServer.class).callFunction("fs20.php?cmd=switchOn",
					params);
			result = Registry.getInstance(IPSServer.class).callFunction("switchOff", params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			LOGGER.error("switchOn of device failed.", e);
			return false;
		}
	}

	private boolean synch(ActorDevice device) {
		LOGGER.error("synch is not yet implemented.");
		return false;
	}

//	private String getHomeCode(ActorDevice device) {
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
//			return Registry.getInstance(IPSServer.class).callFunction("fs20.php?cmd=getHomeCode", params);
//		} catch (Exception e) {
//			LOGGER.error("getHomeCode on of device failed.", e);
//			return null;
//		}
//	}
//
//	private boolean setHomeCode(ActorDevice device, String homeCode) {
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
//			params.put(CODE_PARAM, homeCode);
//			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction(
//					"fs20.php?cmd=setHomeCode", params));
//		} catch (Exception e) {
//			throw new RuntimeException("setHomeCode on of device failed.", e);
//		}
//	}

	private boolean setIntensity(int value, ActorDevice device) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
			params.put("intensity", String.valueOf(FS20IntensityConverter
					.getFS20Value(value)));
			String result = Registry.getInstance(IPSServer.class).callFunction("fs20.php?cmd=setIntensity",
					params);
			return OK_VALUE.equals(result);
		} catch (Exception e) {
			LOGGER.error("setIntensity on of device failed.", e);
			return false;
		}
	}

//	private boolean enableTimer(ActorDevice device, boolean value) {
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
//			params.put(ENABLE_PARAM, String.valueOf(value));
//			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction(
//					"fs20.php?cmd=enableTimer", params));
//		} catch (Exception e) {
//			throw new RuntimeException("enableTimer on of device failed.", e);
//		}
//	}
//
//	private boolean enableReceive(ActorDevice device, boolean value) {
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
//			params.put(ENABLE_PARAM, String.valueOf(value));
//			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction(
//					"fs20.php?cmd=enableReceive", params));
//		} catch (Exception e) {
//			throw new RuntimeException("enableReceive on of device failed.", e);
//		}
//	}
//
//	private boolean setDeviceAddress(ActorDevice device, String homeCode,
//			String address, String subaddress) {
//		try {
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(ID_PARAM, String.valueOf(device.getDescriptor().getItemID()));
//			params.put(SUBADDRESS_PARAM, subaddress);
//			params.put(ADDRESS_PARAM, address);
//			params.put(HOME_CODE_PARAM, homeCode);
//			return OK_VALUE.equals(Registry.getInstance(IPSServer.class).callFunction(
//					"fs20.php?cmd=setDeviceAddress", params));
//		} catch (Exception e) {
//			throw new RuntimeException("setDeviceAddress on of device failed.",
//					e);
//		}
//	}

	@Override
	protected void onIPSDeviceEvent(AbstractItemEvent evt, Device device) {
		if (evt instanceof OperationEvent && evt.getOwner() instanceof
				ActorDevice) {
			OperationEvent oe = (OperationEvent)evt;
			ActorDevice sd = (ActorDevice)oe.getOwner();
			if("dimDown".equals(oe.getOperation())){
				dimDown(sd);
			}
			else if("dimUp".equals(oe.getOperation())){
				dimUp(sd);
			}
			else if("switchOff".equals(oe.getOperation())){
				switchOff(sd);
			}
			else if("switchOn".equals(oe.getOperation())){
				switchOn(sd);
			}
			else if("setIntensity".equals(oe.getOperation())){
				setIntensity((Integer)oe.getParam(0), sd);
			}
			else if("synch".equals(oe.getOperation())){
				synch(sd);
			}
		}
	}

}
