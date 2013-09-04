package org.homemotion.ui.admin.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homemotion.calendar.MonthlyCalendar;

//public class MonthlyCalendarModel {
//
//	private MonthlyCalendar model;
//
//	public MonthlyCalendarModel(MonthlyCalendar calendar){
//		this.model = calendar;
//	}
//	
//	public  Map<String, String> getDays(){
//		Map<String, String> items = new HashMap<String, String>();
//		for(int i=0; i<32; i++){
//			items.put(String.valueOf(i),String.valueOf(i+1));
//		}
//		return items;
//	}
//	
//	public List<String> getSelectedDays(){
//		List<String> result = new ArrayList<String>();
//		boolean[] days = model.getDayset();
//		for(int i=0; i<days.length;i++){
//			if(days[i]){
//				result.add(String.valueOf(i));
//			}
//		}
//		return result;
//	}
//	
//	public void setSelectedDays(List<String> selectedDays){
//		for (int i=0; i<31;i++) {
//			model.setDay(i, selectedDays.contains(String.valueOf(i)));
//		}
//	}
//	
//	
//}
