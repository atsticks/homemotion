package org.homemotion.ui.admin.macros;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.macros.Macro;
//import org.homemotion.macros.MacroManager;
//
//
//@ManagedBean(name="macroLists")
//@ApplicationScoped
//public final class ListProvider {
//	
//	@Inject
//	private MacroManager macroManager;
//	
//	public List<SelectItem> getAllMacros() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Macro> items = macroManager.getAllItems();
//		for (Macro item : items) {
//			selectItems.add(new SelectItem(item, item.getName()));
//		}
//		return selectItems;
//	}
//	
//	public List<SelectItem> getAllMacroEngineNames() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		String[] items = macroManager.getAllExecutionEngineNames();
//		for (String engName : items) {
//			selectItems.add(new SelectItem(engName));
//		}
//		return selectItems;
//	}
//	
//}
