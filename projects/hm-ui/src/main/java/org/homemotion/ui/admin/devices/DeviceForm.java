package org.homemotion.ui.admin.devices;
//
//import java.util.List;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.devices.Device;
//import org.homemotion.devices.DeviceManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//
//@ManagedBean
//@ViewScoped
//public final class DeviceForm extends AbstractFormPage<Device>{
//	
//	private static final long serialVersionUID = 1L;
//	
//	@Inject
//	private DeviceManager deviceManager;
//	
//	public DeviceForm(){
//		super("/admin/devices/Device", Device.class);
//	}
//	
//	@Override
//	protected Device createNewItem() {
//		return new Device();
//	}
//	
//	@Override
//	protected ItemManager<Device> getItemManager() {
//		return deviceManager;
//	}
//
//	public String getStatusMessages(){
//		StringBuilder b = new StringBuilder();
//		List<String> messages = getItem().getStatusMessages();
//		for (String msg : messages) {
//			b.append(msg);
//			b.append("\n");
//		}
//		return b.toString();
//	}
//
//}
