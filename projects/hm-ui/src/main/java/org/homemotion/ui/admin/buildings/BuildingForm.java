package org.homemotion.ui.admin.buildings;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//
//import org.homemotion.building.Building;
//import org.homemotion.building.BuildingManager;
//import org.homemotion.dao.ItemManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//
//@ManagedBean
//@ViewScoped
//public final class BuildingForm extends AbstractFormPage<Building>{
//	
//	@Inject
//	private BuildingManager buildingManager;
//	
//	public BuildingForm(){
//		super("/admin/building/Building", Building.class);
//	}
//	
//	
//	@Override
//	protected ItemManager<Building> getItemManager() {
//		return buildingManager;
//	}
//
//
//	@Override
//	protected Building createNewItem() {
//		return new Building();
//	}
//
//
//}
