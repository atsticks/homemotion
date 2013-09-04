package org.homemotion.ui.admin.rooms;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.Status;
//import org.homemotion.rooms.Room;
//import org.homemotion.rooms.RoomAdapter;
//import org.homemotion.rooms.RoomManager;
//import org.homemotion.ui.state.UserSettings;
//
//@ManagedBean(name="roomLists")
//@ApplicationScoped
//public final class ListProvider {
//	
//	@Inject
//	private RoomManager roomManager;
//	
//	public List<SelectItem> getAllRoomStati() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		for (Status status : Status.values()) {
//			items.add(new SelectItem(status));
//		}
//		return items;
//	}
//
//
//	public List<SelectItem> getAllRooms() {
//		UserSettings sessionState = org.homemotion.ui.state.UISystem.resolveExpression("#{sessionState}", UserSettings.class);
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Room> items = roomManager.getAllItems(sessionState.getCurrentBuilding());
//		for (Room item : items) {
//			selectItems.add(new SelectItem(item, item.getName()));
//		}
//		return selectItems;
//	}
//
//	
//	
//	public List<SelectItem> getAllRoomAdapters() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Class<RoomAdapter>> items = roomManager.getRoomAdapters();
//		for (Class<RoomAdapter> item : items) {
//			selectItems.add(new SelectItem(item.getName(), item.getSimpleName()));
//		}
//		return selectItems;
//	}
//	
//}
