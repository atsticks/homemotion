package org.homemotion.devices.spi.impl.ips;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.homemotion.devices.spi.impl.ips.model.AbstractIPSDeviceListener;

public interface IPSServer {

	public Date getStartTime();
	public String getVersion();
	public String getLicensee();
	public String getUpdateVersion();
	
	public File getIpsRootDir();
	public File getIpsScriptsDir();
	public URL getServerContext();
	public File getIPSDirectory();
	public boolean isRunning();
	public boolean isAccessible();
	
	public String callFunction(String command) throws Exception;
	public String callFunction(String command, Map<String,String> params)throws Exception;
}
