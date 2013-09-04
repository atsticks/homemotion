package org.homemotion.ui.admin.buildings;
//
//import java.util.List;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import org.homemotion.building.Building;
//import org.homemotion.building.BuildingManager;
//import org.homemotion.dao.ItemManager;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//
//@ManagedBean
//@RequestScoped
//public final class BuildingControl extends AbstractPageControl<Building>{
//	
//	private static final long serialVersionUID = 3538678863313025476L;
//
//	@Inject
//	private BuildingManager buildingManager;
//	
//	public BuildingControl(){
//		super("/admin/building/Building");
//	}
//	
//	@Override
//	protected Building createNewItem() {
//		return new Building();
//	}
//	
//	@Override
//	protected ItemManager<Building> getItemManager() {
//		return buildingManager;
//	}
//
//
//}
