package org.homemotion.ui.admin.devices;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.devices.Device;
//import org.homemotion.devices.DeviceManager;
//import org.homemotion.ui.state.UserSettings;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//@ManagedBean
//@RequestScoped
//public final class DeviceControl extends AbstractPageControl<Device> {
//
//	private static final long serialVersionUID = 3033070975417682704L;
//	
//	@Inject
//	private DeviceManager deviceManager;
//	
//	@ManagedProperty("#{userSettings}")
//	private UserSettings sessionState;
//
//	public DeviceControl() {
//		super("/admin/devices/Device");
//	}
//
//	@Override
//	protected Device createNewItem() {
//		Device device = new Device();
//		device.setBuilding(sessionState.getCurrentBuilding());
//		return device;
//	}
//
//	@Override
//	protected ItemManager<Device> getItemManager() {
//		return deviceManager;
//	}
//
//
//	public void setSessionState(UserSettings sessionState) {
//		this.sessionState = sessionState;
//	}
//
//	
//}
