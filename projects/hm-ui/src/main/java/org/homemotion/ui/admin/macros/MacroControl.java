package org.homemotion.ui.admin.macros;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.macros.Macro;
//import org.homemotion.macros.MacroManager;
//import org.homemotion.ui.state.UserSettings;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//@ManagedBean
//@RequestScoped
//public final class MacroControl extends AbstractPageControl<Macro> {
//
//	private static final long serialVersionUID = 3033070975417682704L;
//	
//	@Inject
//	private MacroManager macroManager;
//
//	@ManagedProperty("#{userSettings}")
//	private UserSettings sessionState;
//
//	public MacroControl() {
//		super("/admin/macros/Macro");
//	}
//
//	@Override
//	protected Macro createNewItem() {
//		Macro item = new Macro();
//		return item;
//	}
//
//	@Override
//	protected ItemManager<Macro> getItemManager() {
//		return macroManager;
//	}
//
//	public void setSessionState(UserSettings sessionState) {
//		this.sessionState = sessionState;
//	}
//
//}
