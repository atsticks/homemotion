package org.homemotion.devices.spi.impl.ips;

import org.homemotion.ItemDescriptor;
import org.homemotion.devices.Device;

public final class InstanceParser {

	private InstanceParser() {
	}

	public static final String EXPLANATION = "IPS:<deviceID>:<type>,\n"
			+ "    whereas type=[FS20|HM],"
			+ "    deviceID=The IPS instance ID.";

	public static int getIPSDeviceID(Device device){
		ItemDescriptor desc = device.getDescriptor();
			if(desc==null){
				return -1;
			}
		
		try{
			return Integer.parseInt(desc.getItemID());
		}
		catch(Exception e){
			throw new IllegalArgumentException("Invalid format encountered for "+device + ",\nexpected int type at position 2, was "+desc.getItemID());
		}
	}


	public static IPSDeviceType getIPSDeviceType(Device device) {
		ItemDescriptor desc = device.getDescriptor();
		try {
			return IPSDeviceType.valueOf(desc.getItemType());
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Invalid FS device type encountered for " + device
							+ ", was " + desc);
		}
	}

	public static final boolean isIPSType(Device device) {
		ItemDescriptor desc = device.getDescriptor();
		return desc!=null && desc.getProviderID().equals("IPS");
	}
}
