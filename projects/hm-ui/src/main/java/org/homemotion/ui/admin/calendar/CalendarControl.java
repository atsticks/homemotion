package org.homemotion.ui.admin.calendar;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//
//import org.homemotion.calendar.AnnualCalendar;
//import org.homemotion.calendar.Calendar;
//import org.homemotion.calendar.CalendarManager;
//import org.homemotion.calendar.CombinedCalendar;
//import org.homemotion.calendar.DailyCalendar;
//import org.homemotion.calendar.HolidayCalendar;
//import org.homemotion.calendar.MonthlyCalendar;
//import org.homemotion.calendar.WeeklyCalendar;
//import org.homemotion.dao.ItemManager;
//import org.homemotion.ui.widgets.AbstractPageControl;
//
//
//@ManagedBean
//@RequestScoped
//public final class CalendarControl extends AbstractPageControl<Calendar>{
//	
//	private static final long serialVersionUID = 3538678863313025476L;
//
//	@Inject
//	private CalendarManager calendarManager;
//	
//	public CalendarControl(){
//		super("/admin/calendar/Calendar");
//	}
//	
//	@Override
//	protected Calendar createNewItem() {
//		Calendar calendar = new Calendar();
//		calendar.setName("newCalendar");
//		return calendar;
//	}
//	
//	public String createNewDaily() {
//		Calendar calendar = new Calendar();
//		calendar.setCalendarType(new DailyCalendar());
//		calendar.setName("newDailyCalendar");
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	public String createNewWeekly() {
//		Calendar calendar = new Calendar();
//		calendar.setCalendarType(new WeeklyCalendar());
//		calendar.setName("newWeeklyCalendar");
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	public String createNewMonthly() {
//		Calendar calendar = new Calendar();
//		calendar.setCalendarType(new MonthlyCalendar());
//		calendar.setName("newMonthlyCalendar");
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	public String createNewAnnual() {
//		Calendar calendar = new Calendar();
//		calendar.setName("newAnnualCalendar");
//		calendar.setCalendarType(new AnnualCalendar());
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	public String createNewHoliday() {
//		Calendar calendar = new Calendar();
//		calendar.setCalendarType(new HolidayCalendar());
//		calendar.setName("newHolidayCalendar");
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	public String createNewCombined() {
//		Calendar calendar = new Calendar();
//		calendar.setCalendarType(new CombinedCalendar());
//		calendar.setName("newCombinedCalendar");
//		HttpServletRequest hrequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		hrequest.setAttribute(getItemName(), calendar);
//		return getCreateTarget();
//	}
//	
//	
//	@Override
//	protected ItemManager<Calendar> getItemManager() {
//		return calendarManager;
//	}
//
//}
