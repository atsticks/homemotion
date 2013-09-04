package org.homemotion.comm.impl;

import java.io.File;

import local.ua.CommandLineUA;

import org.apache.log4j.Logger;
import org.homemotion.comm.SIPService;
import org.homemotion.comm.SIPUserAgent;
import org.zoolu.net.SocketAddress;
import org.zoolu.sip.provider.SipProvider;
import org.zoolu.sip.provider.SipStack;

public class SIPServiceImpl implements SIPService {

	static final Logger LOGGER = Logger.getLogger(CommandLineUA.class);
	
	private File file = null;
	private int debug_level = -1;
	private File logPath = null;
	private String outboundProxy = null;
	private String viaAddr = SipProvider.AUTO_CONFIGURATION;
	private int hostPort = SipStack.default_port;
	
	private SipProvider sipProvider;
	
	public SIPUserAgent createUserAgent(){
		return new SIPUserAgentImpl(getSipProvider());
	}
	
	public void setConfigFile(File file) {
		this.file = file;
	}
	
	
	/**
	 * local SIP port, used ONLY without configFile option.
	 * 
	 * @param port
	 */
	public void setLocalSIPPort(int port) {
		this.hostPort = port;
	}

	/**
	 * use the specified outbound proxy.
	 */
	public void setOutboundProxy(String proxy) {
		this.outboundProxy = proxy;
	}

	/**
	 * host via address, used ONLY without configFile option
	 * 
	 * @param addr
	 */
	public void setViaHost(String addr) {
		this.viaAddr = addr;
	}

	/**
	 * debug level (level=0 means no log)
	 * 
	 * @param level
	 */
	public void setDebugLevel(int level) {
		this.debug_level = level;
	}

	/**
	 * base path for all logs (./log is the default value)
	 * 
	 * @param file
	 */
	public void setLogPath(File file) {
		this.logPath = file;
	}

	public void init() {
		try {
			if (file != null) {
				SipStack.init(file.getAbsolutePath());
			} else {
				SipStack.init(null);
			}
			if (debug_level >= 0) {
				SipStack.debug_level = debug_level;
			}
			if (logPath != null) {
				SipStack.log_path = logPath.getAbsolutePath();
			}
			if (file != null) {
				sipProvider = new SipProvider(file != null ? file
						.getAbsolutePath() : null);
			} else {
				sipProvider = new SipProvider(viaAddr, hostPort);
			}
			if (outboundProxy != null) {
				sipProvider.setOutboundProxy(new SocketAddress(outboundProxy));
			}
		} catch (Exception e) {
			LOGGER.error("Error performing SIP communication.", e);
		}
	}


	/* (non-Javadoc)
	 * @see org.homemotion.comm.impl.SIPService#getSipProvider()
	 */
	public SipProvider getSipProvider() {
		return sipProvider;
	}
}
