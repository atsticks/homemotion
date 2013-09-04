package org.homemotion.comm.impl;

import local.ua.RegisterAgent;
import local.ua.RegisterAgentListener;
import local.ua.UserAgent;
import local.ua.UserAgentListener;

import org.apache.log4j.Logger;
import org.zoolu.sip.address.NameAddress;

public final class SIPUserAgentListener implements UserAgentListener,
		RegisterAgentListener {

	static final Logger LOGGER = Logger.getLogger(SIPUserAgentListener.class);

	// ******************* UserAgent callback functions ******************

	/** When a new call is incoming */
	public void onUaCallIncoming(UserAgent ua, NameAddress callee,
			NameAddress caller) {
		LOGGER.info("incoming call from " + caller.toString());
	}

	/** When an outgoing call is remotly ringing */
	public void onUaCallRinging(UserAgent ua) {
		LOGGER.debug("Calling (ringing)...");
	}

	/** When an ougoing call has been accepted */
	public void onUaCallAccepted(UserAgent ua) {
		LOGGER.debug("Call accepted.");
	}

	/** When a call has been trasferred */
	public void onUaCallTrasferred(UserAgent ua) {
		LOGGER.debug("Call transferred.");
	}

	/** When an incoming call has been cancelled */
	public void onUaCallCancelled(UserAgent ua) {
		LOGGER.debug("Incoming call cancelled.");
	}

	/** When an ougoing call has been refused or timeout */
	public void onUaCallFailed(UserAgent ua) {
		LOGGER.error("Call failed.");
	}

	/** When a call has been locally or remotely closed */
	public void onUaCallClosed(UserAgent ua) {
		LOGGER.info("Call closed.");
	}

	// **************** RegisterAgent callback functions *****************

	/** When a UA has been successfully (un)registered. */
	public void onUaRegistrationSuccess(RegisterAgent ra, NameAddress target,
			NameAddress contact, String result) {
		LOGGER.info("Registration success: " + result);
	}

	/** When a UA failed on (un)registering. */
	public void onUaRegistrationFailure(RegisterAgent ra, NameAddress target,
			NameAddress contact, String result) {
		LOGGER.info("Registration failure: " + result);
	}

}
