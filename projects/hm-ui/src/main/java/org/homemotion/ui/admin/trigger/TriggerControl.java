package org.homemotion.ui.admin.trigger;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.trigger.Trigger;
//import org.homemotion.trigger.TriggerManager;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//@ManagedBean
//@RequestScoped
//public final class TriggerControl extends AbstractPageControl<Trigger>{
//	
//	@Inject
//	private TriggerManager triggerManager;
//
//	public TriggerControl(){
//		super("/admin/trigger/Trigger");
//	}
//	
//	@Override
//	protected Trigger createNewItem() {
//		return new Trigger();
//	}
//	
//	@Override
//	protected ItemManager<Trigger> getItemManager() {
//		return triggerManager;
//	}
//
//
//}
//
