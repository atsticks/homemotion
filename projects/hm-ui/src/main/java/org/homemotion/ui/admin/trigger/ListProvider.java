package org.homemotion.ui.admin.trigger;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.trigger.TriggerCheckType;
//import org.homemotion.trigger.TriggerManager;
//import org.homemotion.trigger.TriggerStatus;
//import org.homemotion.trigger.TriggerStrategy;
//import org.homemotion.trigger.Weekday;
//
//
//@ManagedBean(name="triggerLists")
//@ApplicationScoped
//public final class ListProvider {
//
//	@Inject
//	private TriggerManager triggerManager;
//	
//	public List<SelectItem> getAllWeekdays() {
//		List<SelectItem> items = new ArrayList<SelectItem>(Weekday.values().length);
//		items.add(new SelectItem(Weekday.Mo, "Montag"));
//		items.add(new SelectItem(Weekday.Tu, "Dienstag"));
//		items.add(new SelectItem(Weekday.We, "Mittwoch"));
//		items.add(new SelectItem(Weekday.Th, "Donnerstag"));
//		items.add(new SelectItem(Weekday.Sa, "Samstag"));
//		items.add(new SelectItem(Weekday.Su, "Sonntag"));
//		return items;
//	}
//	
//	public List<SelectItem> getTriggerCheckTypes() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		items.add(new SelectItem(TriggerCheckType.FIXED, "Fix"));
//		items.add(new SelectItem(TriggerCheckType.PERIODIC, "Periodisch"));
//		return items;
//	}
//
//	public List<SelectItem> getTriggerStati() {
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		items.add(new SelectItem(TriggerStatus.LOADED, "Bereit"));
//		items.add(new SelectItem(TriggerStatus.ACTIVE, "Aktiv"));
//		items.add(new SelectItem(TriggerStatus.WARNING, "Warnung"));
//		items.add(new SelectItem(TriggerStatus.FAILED, "Fehlerhaft"));
//		return items;
//	}
//	
//	public List<SelectItem> getTriggerStrategies() {
//		List<Class<TriggerStrategy>> triggers = triggerManager.getStrategies();
//		List<SelectItem> items = new ArrayList<SelectItem>();
//		for(Class<TriggerStrategy> triggerClass: triggers){
//			items.add(new SelectItem(triggerClass.getName(), triggerClass.getSimpleName()));
//		}
//		return items;
//	}
//}
