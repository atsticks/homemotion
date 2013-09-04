package org.homemotion.devices.spi.impl.ips;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.homemotion.ItemDescriptor;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public final class IPSDeviceLocator {

	private static final Logger LOGGER = Logger.getLogger(IPSDeviceLocator.class);
	@Inject
	private DeviceManager deviceManager;
	@Inject
	private IPSServer ipsServer;

	public IPSDeviceLocator() {
	}

	public Device getDevice(ItemDescriptor desc) {
		Device dev = null;
		try {
			LOGGER.debug("Searching for device using IPS ID: "
					+ desc);
			dev = getDevice(desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dev != null) {
			LOGGER.debug("Returning device for IPS ID: " + desc + " "
					+ dev.getId() + '/' + dev.getName());
		} else {
			LOGGER.debug("NO device found for IPS ID: " + desc);
		}
		return dev;
	}

	public Device getDeviceFromVar(int ipsVarID) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", String.valueOf(ipsVarID));
			String parentID = ipsServer.callFunction("ips.php?cmd=getParent",
					params);
			LOGGER.debug("Found IPS parentID for " + ipsVarID + ": "
					+ parentID);
			return getDevice(new ItemDescriptor("IPS",null,parentID));
		} catch (Exception e) {
			LOGGER.error(
					"Error evaluating parent to IPS variable " + ipsVarID, e);
			return null;
		}
	}

	public IPSServer getIpsServer() {
		return this.ipsServer;
	}

}
