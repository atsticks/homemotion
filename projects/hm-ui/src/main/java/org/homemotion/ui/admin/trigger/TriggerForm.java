package org.homemotion.ui.admin.trigger;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//
//import org.homemotion.dao.ItemManager;
//import org.homemotion.trigger.Trigger;
//import org.homemotion.trigger.TriggerManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//
//
//@ManagedBean
//@ViewScoped
//public final class TriggerForm extends AbstractFormPage<Trigger>{
//	
//	@Inject
//	private TriggerManager triggerManager;
//	
//	public TriggerForm(){
//		super("/admin/trigger/Trigger", Trigger.class);
//	}
//	
//	@Override
//	protected ItemManager<Trigger> getItemManager() {
//		return triggerManager;
//	}
//
//	@Override
//	protected Trigger createNewItem() {
//		return new Trigger();
//	}
//}
