package org.homemotion.ui.admin.buildings;

//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.building.Building;
//import org.homemotion.building.BuildingManager;
//
//@ManagedBean(name="buildingLists")
//@ApplicationScoped
//public final class ListProvider {
//
//	@Inject
//	private BuildingManager buildingManager;
//	
//	public List<SelectItem> getAllBuildings() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Building> items = buildingManager.getAllItems();
//		for (Building item : items) {
//			selectItems.add(new SelectItem(item, item.getName()));
//		}
//		return selectItems;
//	}
//	
//}
