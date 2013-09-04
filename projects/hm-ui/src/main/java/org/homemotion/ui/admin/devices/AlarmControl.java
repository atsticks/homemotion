package org.homemotion.ui.admin.devices;

//import java.util.List;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.context.FacesContext;
//
//import org.homemotion.devices.DeviceManager;
//import org.homemotion.di.Registry;
//import org.homemotion.security.AlarmEvent;
//
//@ManagedBean
//@RequestScoped
//public final class AlarmControl {
//
//	@Inject
//	private DeviceManager deviceManager;
//
//	public AlarmControl() {
//		Registry.injectMembers(this);
//	}
//
//	public boolean isAlarmActive() {
//		return alarmManager.getAlarms().size() > 0;
//	}
//
//	public boolean isActive() {
//		return org.homemotion.System.isSecurityActive();
//	}
//
//	public void setActive(boolean active) {
//		org.homemotion.System.setSecurityActive(active);
//	}
//
//	public List<AlarmEvent> getAlarms() {
//		return alarmManager.getAlarms();
//	}
//
//	public String clearAlarms() {
//		int alarmsCleared = alarmManager.clearAlarms();
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				alarmsCleared + " Alarme deaktiviert.", alarmsCleared
//						+ " Alarme deaktiviert.");
//		ctx.addMessage(null, msg);
//		return null;
//	}
//
//	public String clearTestAlarms() {
//		alarmManager.clearTestAlarms();
//		FacesContext ctx = FacesContext.getCurrentInstance();
//		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Testalarme deaktiviert.", "Testalarme deaktiviert.");
//		ctx.addMessage(null, msg);
//		return null;
//	}
//
//	public boolean isWaterAlarmActive() {
//		return alarmManager.getAlarms("Wasseralarm").size() > 0;
//	}
//
//	public boolean isFireAlarmActive() {
//		return alarmManager.getAlarms("Feueralarm").size() > 0;
//	}
//
//	public boolean isGazAlarmActive() {
//		return alarmManager.getAlarms("Gasalarm").size() > 0;
//	}
//
//	public boolean isIntruderAlarmActive() {
//		return alarmManager.getAlarms("Bewegung").size() > 0
//				|| alarmManager.getAlarms("Kontakt geöffnet").size() > 0
//				|| alarmManager.getAlarms("Kontakt geschlossen").size() > 0;
//	}
//}
