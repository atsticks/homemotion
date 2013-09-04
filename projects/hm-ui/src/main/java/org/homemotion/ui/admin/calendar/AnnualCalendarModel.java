package org.homemotion.ui.admin.calendar;
//
//import org.homemotion.calendar.AnnualCalendar;
//import org.homemotion.calendar.Calendar;
//import org.homemotion.calendar.CalendarEntry;
//import org.homemotion.calendar.CalendarType;
//import org.primefaces.model.DefaultScheduleEvent;
//import org.primefaces.model.DefaultScheduleModel;
//import org.primefaces.model.ScheduleEvent;
//
//public class AnnualCalendarModel extends DefaultScheduleModel implements CalendarUIModel{
//	
//	private AnnualCalendar model;
//
//	public AnnualCalendarModel(AnnualCalendar calendar){
//		this.model = calendar;
//		for (CalendarEntry entry: this.model.getDates()) {
//			DefaultScheduleEvent evt = new DefaultScheduleEvent(entry.getName(), entry.getStartDate(), entry.getEndDate(), entry.isAllDay());
//			evt.setData(entry.getId());
//			super.addEvent(evt);
//		}
//	}
//	
//	@Override
//	public void addEvent(ScheduleEvent event) {
//		CalendarEntry e = new CalendarEntry(event.getTitle(),event.getStartDate(), event.getEndDate(), event.isAllDay());
//		this.model.addEntry(e);
//		super.addEvent(event);
//	}
//	
//	@Override
//	public boolean deleteEvent(ScheduleEvent event) {
//		CalendarEntry e = this.model.getEntry((Long)event.getData());
//		if(e!=null){
//			this.model.removeEntry(e);
//		}
//		return super.deleteEvent(event);
//	}
//	
//	@Override
//	public void updateEvent(ScheduleEvent event) {
//		CalendarEntry e = this.model.getEntry((Long)event.getData());
//		if(e!=null){
//			e.setAllDay(event.isAllDay());
//			e.setStartDate(event.getStartDate());
//			e.setEndDate(event.getEndDate());
//			e.setName(event.getTitle());
//		}
//		super.updateEvent(event);
//	}
//
//	public CalendarType getCalendarType() {
//		return model;
//	}
//	
//	public boolean isValidFor(Calendar item) {
//		return model.getCalendar().equals(item);
//	}
//	
//}
