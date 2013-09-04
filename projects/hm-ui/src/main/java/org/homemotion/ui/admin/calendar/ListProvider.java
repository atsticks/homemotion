package org.homemotion.ui.admin.calendar;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.bean.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.faces.model.SelectItem;
//import javax.inject.Inject;
//
//import org.homemotion.calendar.Calendar;
//import org.homemotion.calendar.CalendarManager;
//import org.homemotion.calendar.CalendarType;
//import org.homemotion.calendar.CombinationType;
//
//@ManagedBean(name="calendarLists")
//@ApplicationScoped
//public final class ListProvider {
//
//	@Inject
//	private CalendarManager calendarManager;
//	
//	public List<SelectItem> getAllCalendars() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Calendar> items = calendarManager.getAllItems();
//		for (Calendar item : items) {
//			selectItems.add(new SelectItem(item, item.getName()));
//		}
//		return selectItems;
//	}
//	
//	public List<SelectItem> getAllCalendarTypes() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Class<CalendarType>> types = calendarManager.getTypes();
//		for (Class<CalendarType> type : types) {
//			selectItems.add(new SelectItem(type, type.getSimpleName()));
//		}
//		return selectItems;
//	}
//	
//	public List<SelectItem> getAllCalendarTypeNames() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>();
//		List<Class<CalendarType>> types = calendarManager.getTypes();
//		for (Class<CalendarType> type : types) {
//			selectItems.add(new SelectItem(type.getName(), type.getSimpleName()));
//		}
//		return selectItems;
//	}
//	
//	public List<SelectItem> getAllCalendarCombinationTypes() {
//		List<SelectItem> selectItems = new ArrayList<SelectItem>(CombinationType.values().length);
//		for (CombinationType type : CombinationType.values()) {
//			selectItems.add(new SelectItem(type));
//		} 
//		return selectItems;
//	}
//	
//}
