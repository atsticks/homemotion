package org.homemotion.comm;

import org.zoolu.sip.provider.SipProvider;

public interface SIPService {

	public SipProvider getSipProvider();

	public SIPUserAgent createUserAgent();
	
}