package org.homemotion.ui.admin.devices;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.Mode;
//import org.homemotion.Status;
//import org.homemotion.devices.Device;
//import org.homemotion.devices.DeviceAdapter;
//import org.homemotion.devices.DeviceManager;
//import org.homemotion.ui.state.UserSettings;
//
//@ManagedBean(name="deviceLists")
//@ApplicationScoped
//public final class ListProvider {
//	
//	@Inject
//	private DeviceManager deviceManager;
//	
//	public List<SelectItem> getAllDeviceStati() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		for (Status status : Status.values()) {
//			items.add(new SelectItem(status));
//		}
//		return items;
//	}
//
//
//	public List<SelectItem> getAllDevices() {
//		UserSettings sessionState = org.homemotion.ui.state.UISystem.resolveExpression("#{sessionState}", UserSettings.class);
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Device> items = deviceManager.getAllItems(sessionState.getCurrentBuilding());
//		for (Device item : items) {
//			selectItems.add(new SelectItem(item, item.getName()));
//		}
//		return selectItems;
//	}
//
//	
//	public List<SelectItem> getAllModes() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		items.add(new SelectItem(Mode.AUTO, "Automatisch"));
//		items.add(new SelectItem(Mode.ABSENT, "Abwesend"));
//		items.add(new SelectItem(Mode.NIGHT, "Nacht"));
//		items.add(new SelectItem(Mode.ABSENT, "Kurzabwesenheit"));
//		items.add(new SelectItem(Mode.HOLIDAY, "Ferien"));
//		items.add(new SelectItem(Mode.OFF, "Wartung/deaktiviert"));
//		return items;
//	}
//	
//	public List<SelectItem> getAllDeviceAdapters() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Class<DeviceAdapter>> items = deviceManager.getDeviceAdapters();
//		for (Class<DeviceAdapter> item : items) {
//			selectItems.add(new SelectItem(item.getName(), item.getSimpleName()));
//		}
//		return selectItems;
//	}
//	
//}
