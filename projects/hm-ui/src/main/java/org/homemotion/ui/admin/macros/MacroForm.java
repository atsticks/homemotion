package org.homemotion.ui.admin.macros;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.macros.Macro;
//import org.homemotion.macros.MacroManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//
//@ManagedBean
//@ViewScoped
//public final class MacroForm extends AbstractFormPage<Macro>{
//	
//	@Inject
//	private MacroManager macroManager;
//	
//	public MacroForm(){
//		super("/admin/macros/Macro", Macro.class);
//	}
//	
//	@Override
//	protected ItemManager<Macro> getItemManager() {
//		return macroManager;
//	}
//	
//	@Override
//	protected Macro createNewItem() {
//		return new Macro();
//	}
//
//}
