package org.homemotion.devices.spi.impl.ips.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.homemotion.Configuration;
import org.homemotion.devices.spi.impl.ips.IPSServer;
import org.homemotion.devices.spi.impl.ips.IPSVar;
import org.homemotion.devices.spi.impl.ips.model.AbstractIPSDeviceListener;

import com.google.inject.Singleton;

@Singleton
public final class IPSServerImpl implements IPSServer {

	private URL context = null;
	private File ipsRootDir = new File(Configuration.get("core.devices.spi.ips.rootdir"));

	public IPSServerImpl() {
		try {
			context = new URL(Configuration.get("core.devices.spi.ips.url"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final File getIpsScriptsDir() {
		return new File(ipsRootDir, Configuration.get("core.devices.spi.ips.scriptsdir"));
	}

	public URL getServerContext() {
		return context;
	}

	@Override
	public File getIPSDirectory() {
		return ipsRootDir;
	}

	@Override
	public String getLicensee() {
		return Configuration.get("core.devices.spi.ips.scriptsdir", "N/A");
	}

	@Override
	public Date getStartTime() {
		try {
			String uptime = callFunction("kernel.php?cmd=getUptime");
			long uptimeMS = Long.parseLong(uptime);
			long started = System.currentTimeMillis() - uptimeMS;
			return new Date(started);
		} catch (Exception e) {
			throw new RuntimeException("getStartTime on of kernel failed.", e);
		}
	}

	@Override
	public String getUpdateVersion() {
		return "N/A";
	}

	@Override
	public String getVersion() {
		try {
			return callFunction("kernel.php?cmd=getVersion");
		} catch (Exception e) {
			throw new RuntimeException("getVersion on of kernel failed.", e);
		}
	}

	@Override
	public boolean isAccessible() {
		try {
			callFunction("kernel.php?cmd=getVersion");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isRunning() {
		return isAccessible();
	}

	@Override
	public File getIpsRootDir() {
		return ipsRootDir;
	}

	@Override
	public String callFunction(String command) throws Exception {
		URL call = new URL(context, command);
		InputStream is = null;
		InputStreamReader reader = null;
		BufferedReader input = null;
		try {
			is = call.openStream();
			reader = new InputStreamReader(is, Charset.forName("ISO-8859-1"));
			input = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while (line != null) {
				if ((line = input.readLine()) != null) {
					buffer.append(line);
				}
			}
			return buffer.toString();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String callFunction(String command, Map<String, String> params)
			throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append(command);
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buffer.append('&');
				buffer.append(entry.getKey());
				buffer.append('=');
				buffer.append(entry.getValue());
			}
		}
		return callFunction(buffer.toString());
	}

//	@Override
//	public void updateVars(AbstractIPSDeviceListener device) throws Exception {
//		String vars = callFunction("ips.php?cmd=getChildrenVariables");
//		updateVariables(device, vars);
//		for (IPSVar var : device.getVars().values()) {
//			String ipsValue = callFunction("ips.php?cmd=getVar&id="
//					+ var.getIpsID());
//			var.parseValue(ipsValue);
//		}
//	}
	
	
}
