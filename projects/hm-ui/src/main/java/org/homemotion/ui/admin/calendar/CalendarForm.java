package org.homemotion.ui.admin.calendar;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ActionEvent;
//import javax.inject.Inject;
//
//import org.homemotion.calendar.AnnualCalendar;
//import org.homemotion.calendar.Calendar;
//import org.homemotion.calendar.CalendarManager;
//import org.homemotion.calendar.CombinedCalendar;
//import org.homemotion.calendar.HolidayCalendar;
//import org.homemotion.dao.ItemManager;
//import org.homemotion.ui.widgets.AbstractFormPage;
//import org.primefaces.event.DateSelectEvent;
//import org.primefaces.event.ScheduleEntryMoveEvent;
//import org.primefaces.event.ScheduleEntryResizeEvent;
//import org.primefaces.event.ScheduleEntrySelectEvent;
//import org.primefaces.model.DefaultScheduleEvent;
//import org.primefaces.model.DualListModel;
//import org.primefaces.model.ScheduleEvent;
//import org.primefaces.model.ScheduleModel;
//
//@ManagedBean
//@SessionScoped
//public class CalendarForm extends AbstractFormPage<Calendar> {
//
//	private CalendarUIModel calendarModel;
//
//	private ScheduleEvent selectedScheduleEvent = new DefaultScheduleEvent();
//
//	@Inject
//	private CalendarManager calendarManager;
//
//	public CalendarForm() {
//		super("/admin/calendar/Calendar", Calendar.class);
//	}
//
//	@Override
//	protected ItemManager<Calendar> getItemManager() {
//		return calendarManager;
//	}
//
//	@Override
//	protected Calendar createNewItem() {
//		this.calendarModel = null;
////		this.model = null;
//		return new Calendar();
//	} 
//
//	public DualListModel<Calendar> getCombinedCalListModel() {
//		Calendar item = getItem();
//		if (item.getCalendarType() instanceof CombinedCalendar) {
//			CombinedCalendar ccal = (CombinedCalendar) item.getCalendarType();
//			return new DualListModel<Calendar>(calendarManager.getAllItems(),
//					ccal.getCalendars());
//		}
//		return null;
//	}
//	
//	public void setCombinedCalListModel(DualListModel<Calendar> model){
//		Calendar item = getItem();
//		if (item.getCalendarType() instanceof CombinedCalendar) {
//			CombinedCalendar ccal = (CombinedCalendar) item.getCalendarType();
//			ccal.setCalendars(model.getTarget());
//		}
//	}
//
//	public ScheduleModel getScheduleModel() {
//		if (calendarModel != null && !calendarModel.isValidFor(getItem())) {
//			this.calendarModel = null;
//		}
//		if (calendarModel == null) {
//			if (getItem().getCalendarType() instanceof AnnualCalendar) {
//				calendarModel = new AnnualCalendarModel(
//						(AnnualCalendar) getItem().getCalendarType());
//			} else if (getItem().getCalendarType() instanceof HolidayCalendar) {
//				calendarModel = new HolidayCalendarModel(
//						(HolidayCalendar) getItem().getCalendarType());
//			}
//		}
//		if (calendarModel instanceof ScheduleModel) {
//			return (ScheduleModel) calendarModel;
//		}
//		return null;
//	}
//
//	public void addEvent(ActionEvent actionEvent) {
//		ScheduleModel scheduleModel = getScheduleModel();
//		if (scheduleModel == null) {
//			throw new IllegalArgumentException("Not a scheduled based item.");
//		}
//		if (selectedScheduleEvent.getId() == null) {
//			scheduleModel.addEvent(selectedScheduleEvent);
//		} else {
//			scheduleModel.updateEvent(selectedScheduleEvent);
//		}
//		selectedScheduleEvent = new DefaultScheduleEvent();
//	}
//
//	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
//		selectedScheduleEvent = selectEvent.getScheduleEvent();
//	}
//
//	public void onDateSelect(DateSelectEvent selectEvent) {
//		selectedScheduleEvent = new DefaultScheduleEvent(Math.random() + "",
//				selectEvent.getDate(), selectEvent.getDate());
//	}
//
//	public void onEventMove(ScheduleEntryMoveEvent event) {
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Event moved", "Day delta:" + event.getDayDelta()
//						+ ", Minute delta:" + event.getMinuteDelta());
//
//		addMessage(message);
//	}
//
//	public void onEventResize(ScheduleEntryResizeEvent event) {
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Event resized", "Day delta:" + event.getDayDelta()
//						+ ", Minute delta:" + event.getMinuteDelta());
//
//		addMessage(message);
//	}
//
//	private void addMessage(FacesMessage message) {
//		FacesContext.getCurrentInstance().addMessage(null, message);
//	}
//
//	public ScheduleEvent getSelectedScheduleEvent() {
//		return selectedScheduleEvent;
//	}
//
//	public void setSelectedScheduleEvent(ScheduleEvent selectedScheduleEvent) {
//		this.selectedScheduleEvent = selectedScheduleEvent;
//	}
//
//	// Getters and Setters
//}
