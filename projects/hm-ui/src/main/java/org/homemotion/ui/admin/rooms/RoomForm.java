package org.homemotion.ui.admin.rooms;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.rooms.Room;
//import org.homemotion.rooms.RoomManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//
//@ManagedBean
//@ViewScoped
//public final class RoomForm extends AbstractFormPage<Room>{
//	
//	@Inject
//	private RoomManager roomManager;
//	
//	public RoomForm(){
//		super("/admin/rooms/Room", Room.class);
//	}
//	
//	@Override
//	protected ItemManager<Room> getItemManager() {
//		return roomManager;
//	}
//	
//	@Override
//	protected Room createNewItem() {
//		return new Room();
//	}
//
//}
