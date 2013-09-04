package org.homemotion.ui.admin.rooms;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.rooms.Room;
//import org.homemotion.rooms.RoomManager;
//import org.homemotion.ui.state.UserSettings;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//@ManagedBean
//@RequestScoped
//public final class RoomControl extends AbstractPageControl<Room> {
// 
//	private static final long serialVersionUID = 3033070975417682704L;
//	@Inject
//	private RoomManager roomManager;
//	
//	@ManagedProperty("#{userSettings}")
//	private UserSettings sessionState;
//
//	public RoomControl() {
//		super("/admin/rooms/Room");
//	}
//
//	@Override
//	protected Room createNewItem() {
//		Room room = new Room();
//		room.setBuilding(sessionState.getCurrentBuilding());
//		return room;
//	}
//
//	@Override
//	protected ItemManager<Room> getItemManager() {
//		return roomManager;
//	}
//
//
//	public void setSessionState(UserSettings sessionState) {
//		this.sessionState = sessionState;
//	}
//
//	
//}
