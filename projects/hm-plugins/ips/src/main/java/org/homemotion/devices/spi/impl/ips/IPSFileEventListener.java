package org.homemotion.devices.spi.impl.ips;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.homemotion.ItemDescriptor;
import org.homemotion.devices.Device;
import org.homemotion.devices.DeviceManager;
import org.homemotion.di.Registry;
import org.homemotion.events.Event;
import org.homemotion.events.EventBus;
import org.homemotion.events.EventDefinition;
import org.homemotion.events.EventListener;
import org.homemotion.events.dao.TechEvent;
import org.homemotion.io.FileObserver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public final class IPSFileEventListener implements EventListener{

	private static final Logger LOGGER = Logger.getLogger(IPSFileEventListener.class);
	
	private SimpleDateFormat df = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm:ss.SSS");

	private EventDefinition evtDef;
	private File currentFile;
	private FileObserver fileObserver;

	@Inject
	public IPSFileEventListener(FileObserver fileObserver) {
		LOGGER.info("Initializing IPS subsystem...");
		EventBus.getInstance().registerEventListener(this);
		this.fileObserver = fileObserver;
		initLogFile();
	}
	
	public void initLogFile(){
		File logDir = new File(System.getProperty("ipsLogDir"));
		File[] files = logDir.listFiles();
		File logFile = null;
		for (File file : files) {
			if(!file.getName().endsWith(".log")){
				continue;
			}
			if(logFile==null){
				logFile = file;
				continue;
			}
			if(logFile.lastModified()<file.lastModified()){
				logFile = file;
			}
		}
		if(!logFile.equals(currentFile)){
			LOGGER.info("Initializing IPS target log file to: " + logFile+" ...");
			if(currentFile!=null){
				this.fileObserver.removeFile(currentFile);
			}
			currentFile = logFile;
			this.fileObserver.addFile(logFile, "ISO-8859-1");
		}
	}

//	private void registerEvents() {
//		evtDef = new EventDefinition("IPS Event", getClass(),IPSFileEvent.class);
//		evtDef.setSeverity(Severity.INFO);
//		evtDef.setDescription("<IPS Symcon Log Message expression>");
//		EventBus.getInstance().registerEventDefinition(evtDef);
//	}

	@Override
	public boolean onEvent(Event event){
		if(event instanceof TechEvent){
			TechEvent foe = (TechEvent)event;
			if(!foe.getEventIdentifier().equals(FileObserver.EVENT_IDENTIFIER)){
				// ignoring other events
				return false;
			}
			String line = (String)foe.getData();
			String[] items = line.split("\\|");
			if(items.length<4){
				LOGGER.debug("Ignoring unparseable message from IPS: " + line);
				return false;
			}
			Date dateTime = null;
			try {
				dateTime = df.parse(items[0].trim());
			} catch (ParseException e) {
				LOGGER.error("Error parsing DateTime from IPS log line '"+line+"'.", e);
			}
			
			String level = items[2].trim();
			String source = items[3].trim();
			if (!source.equals("VariableManager")) {
				LOGGER.debug("Ignoring message from IPS: " + line);
				return false;
			}
			int ipsItemID = 0;
			if(items[1].trim().length()>0){
				try{
					ipsItemID = Integer.valueOf(items[1].trim());
				} catch(Exception e){
					LOGGER.error("Error parsing IPS Id from IPS log line '"+line+"'.", e);
				}
			}
			DeviceManager dm = Registry.getInstance(DeviceManager.class);
			ItemDescriptor desc = new ItemDescriptor("IPS",null,String.valueOf(ipsItemID));
			List<Device> devices = dm.findItemsByDescriptor(desc);
			if(devices.size()==1){
				// multiple matches
//	todo			synch(devices.get(0), items[4].trim());
			}
			
		}
		return true;
	}
	
	private void synch(Device device, String ipsVarList) throws IOException{
		if (ipsVarList == null) {
			throw new IllegalArgumentException("Can not handle null.");
		}
		if (ipsVarList.isEmpty()) {
			return;
		}
		BufferedReader input = new BufferedReader(new StringReader(ipsVarList));
		String line = null;
		try{
			line = input.readLine();
			while(line!=null){
				IPSVar var = new IPSVar(line);
				device.setData(var.getName(), (Serializable)var.getValue());
			}
		}
		finally{
			if(input!=null){
				input.close();
			}
		}
	}
	
}
