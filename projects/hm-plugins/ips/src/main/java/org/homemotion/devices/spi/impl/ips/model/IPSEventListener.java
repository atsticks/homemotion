package org.homemotion.devices.spi.impl.ips.model;

import java.util.Date;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;
import org.homemotion.devices.DeviceStatus;
import org.homemotion.devices.ClimateControlDevice;
import org.homemotion.devices.SecurityDevice;
import org.homemotion.devices.ActorDevice;
import org.homemotion.devices.spi.impl.ips.IPSDeviceLocator;
import org.homemotion.devices.spi.impl.ips.IPSDeviceType;
import org.homemotion.devices.spi.impl.ips.IPSFileEventListener;
import org.homemotion.devices.spi.impl.ips.IPSVar;
import org.homemotion.devices.spi.impl.ips.InstanceParser;
import org.homemotion.devices.spi.impl.ips.impl.FS20IntensityConverter;
import org.homemotion.di.Registry;
import org.homemotion.events.Event;
import org.homemotion.events.EventListener;
import org.homemotion.io.FileObserverEvent;

public final class IPSEventListener implements EventListener {

	private static final String VAR_STATUS = "Status";

	private static final Logger LOG = Logger.getLogger(IPSEventListener.class);

	private static final String VAR_BATTERY = "Batterie";
	private static final String VAR_WINDOW_OPEN = "Fenster geöffnet";
	private static final String VAR_TARGET_TEMPARATURE = "Target Temparature";
	private static final String VAR_TEMPARATURE = "Temparatur";
	private static final String VAR_POSITION = "Stellung";
	private static final String VAR_TARGET_MODE = "Target Mode";
	private static final String DATA_POSITION = "Position";
	private static final String DATA_WINDOW_OPENED = "Window Opened";
	private static final String STATUS_MESSAGE = "StatusMessage";

	@Override
	public boolean onEvent(Event evt) {
		if (evt instanceof FileObserverEvent) {
			FileObserverEvent fe = (FileObserverEvent) evt;
			String expression = fe.getLine();
			String[] parts = expression.split("=");
			if (parts.length == 2) {
				String fqName = parts[0];
				String value = parts[1];
				IPSDeviceLocator devLocator = Registry
						.getInstance(IPSDeviceLocator.class);
				// TODO fix this here...
				String deviceID = fqName;
				if (deviceID != null) {
					Device device = devLocator.getDeviceFromVar(Integer
							.parseInt(deviceID));
					if (device instanceof ActorDevice) {
						updateSwitchDevice((ActorDevice) device, fqName, value);
					} else if (device instanceof SecurityDevice) {
						updateSecurityDevice((SecurityDevice) device, fqName,
								value);
					} else if (device instanceof ClimateControlDevice) {
						updateHeatingDevice((ClimateControlDevice) device, fqName,
								value);
					}
					// Event will be created/triggered by device manager
					// instance!
				} else {
					LOG.warn("No matching device found for IPS itemID: "
							+ deviceID + ", ignoring message: " + expression);
				}
			} else {
				LOG.warn("Ignoring non interpretable IPS message: "
						+ expression);
			}
		}
		return true;
	}

	protected void updateSwitchDevice(ActorDevice device, String fqVarName,
			String value) {
		String varName = IPSVar.getVarName(fqVarName);
		DeviceManager deviceManager = Registry.getInstance(DeviceManager.class);
		boolean update = updateCommonProperties(device, fqVarName, value);
		switch (InstanceParser.getIPSDeviceType(device)) {
		case FS20:
			if ("Intensität".equals(varName)) {
				int percValue = FS20IntensityConverter.getIntensity(Integer
						.parseInt(value));
				device.setIntensity(percValue);
				deviceManager.update(device);
			}
			break;
		default:
			LOG.warn("Device is not adaptable, change event will be ignored "
					+ "(Device: id=" + device.getId() + ",name="
					+ device.getName() + "; Evt: var=" + fqVarName + ",val="
					+ value + ')');
		}

		if (update) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Updating device due to IPS Event " + "(Device="
						+ device + "; evt: varname=" + fqVarName + ",value="
						+ value + ')');
			}
			deviceManager.update(device);
			return;
		}
	}

	protected void updateHeatingDevice(ClimateControlDevice device, String fqVarName,
			String value) {
		String varName = IPSVar.getVarName(fqVarName);

		boolean update = updateCommonProperties(device, fqVarName, value);
		DeviceManager deviceManager = Registry.getInstance(DeviceManager.class);
		if (InstanceParser.getIPSDeviceType(device).equals(IPSDeviceType.FHT)) {
			if (VAR_TARGET_MODE.equals(varName)) {
				// ldev.setMode(FHTTargetMode.valueOf(Integer.parseInt(value)).toHeatingMode());
				// update = true;
			} else if (VAR_POSITION.equals(varName)) {
				int position = Integer.valueOf(value);
				device.setData(DATA_POSITION, value);
				if (position > 0) {
					device
							.setData(STATUS_MESSAGE, "Heating at " + device
									+ " %.");
				} else {
					device.setData(STATUS_MESSAGE, "Heating off.");
				}
				update = true;
			} else if (VAR_TEMPARATURE.equals(varName)) {
				device.setCurrentTemparature(Float.parseFloat(value));
				update = true;
			} else if (VAR_TARGET_TEMPARATURE.equals(varName)) {
				device.setTargetTemparature(Float.parseFloat(value));
				update = true;
			} else if (VAR_WINDOW_OPEN.equals(varName)) {
				device.setData(DATA_WINDOW_OPENED, value);
				boolean windowOpened = Boolean.parseBoolean(value);
				if (windowOpened) {
					device.setData(STATUS_MESSAGE,
							"Heating minimized (window open).");
				} else {
					Object posValue = device.getData(DATA_POSITION);
					int position = -1;
					if(posValue!=null){
						position = Integer.parseInt(posValue.toString());
					}
					if (position > 0) {
						device.setData(STATUS_MESSAGE, "Heating at " + device
								+ " %.");
					} else {
						device.setData(STATUS_MESSAGE, "Heating off.");
					}
				}
				update = true;
			}
		} else {
			LOG.warn("Device is not adaptable, change event will be ignored "
					+ "(Device: id=" + device.getId() + ",name="
					+ device.getName() + "; Evt: var=" + fqVarName + ",val="
					+ value + ')');
		}
		if (update) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Updating device due to IPS Event " + "(Device="
						+ device + "; evt: varname=" + fqVarName + ",value="
						+ value + ')');
			}
			deviceManager.update(device);
			return;
		}
	}

	protected void updateSecurityDevice(SecurityDevice device,
			String fqVarName, String value) {
		String varName = IPSVar.getVarName(fqVarName);

		boolean update = updateCommonProperties(device, fqVarName, value);
		DeviceManager deviceManager = Registry.getInstance(DeviceManager.class);
		switch (InstanceParser.getIPSDeviceType(device)) {
		case FS20:
			if (VAR_STATUS.equals(varName)) {
				boolean newValue = Boolean.parseBoolean(value.trim());
				device.setAlarmed(newValue);
				if (device.isAlarmed()) {
					device.setLastTrigger(new Date());
				}
				update = true;
			}
			break;
		case HMS:
			if (VAR_STATUS.equals(varName)) {
				device.setAlarmed(Boolean.parseBoolean(value));
				if (device.isAlarmed()) {
					device.setLastTrigger(new Date());
				}
				update = true;
			}
			break;
		default:
			if (LOG.isDebugEnabled()) {
				LOG
						.debug("Device is not adaptable, change event will be ignored "
								+ "(Device="
								+ device
								+ "; evt: varname="
								+ fqVarName + ",value=" + value + ')');
			}
		}
		if (update) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Updating device due to IPS Event " + "(Device="
						+ device + "; evt: varname=" + fqVarName + ",value="
						+ value + ')');
			}
			deviceManager.update(device);
			return;
		}
	}

	protected boolean updateCommonProperties(Device device, String fqVarName,
			String value) {
		String varName = IPSVar.getVarName(fqVarName);
		switch (InstanceParser.getIPSDeviceType(device)) {
		case FS20:
			if (VAR_STATUS.equals(varName)) {
				device.setData("Active",Boolean.valueOf(value).toString());
				return true;
			} else if (VAR_BATTERY.equals(varName)) {
				if (Boolean.valueOf(value)) {
					device.setDeviceStatus(DeviceStatus.WARNING);
					device.setData("StatusMessage", "Batterie auswechseln!");
				} else {
					device.setDeviceStatus(DeviceStatus.OK);
					device.setData("StatusMessage", "OK");
				}
				return true;
			}
			break;
		case HMS:
		default:
			// do nothing here...
		}
		return false;
	}
}
