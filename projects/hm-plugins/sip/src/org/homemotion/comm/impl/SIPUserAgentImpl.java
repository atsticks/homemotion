package org.homemotion.comm.impl;

import java.io.File;

import local.ua.CommandLineUA;
import local.ua.RegisterAgent;
import local.ua.UserAgent;
import local.ua.UserAgentProfile;

import org.apache.log4j.Logger;
import org.homemotion.comm.SIPUserAgent;
import org.zoolu.sip.provider.SipProvider;

public class SIPUserAgentImpl implements SIPUserAgent {

	static final Logger LOGGER = Logger.getLogger(CommandLineUA.class);

	boolean initialized = false;

	boolean regist = false;
	boolean unregist = false;
	boolean unregistAll = false;
	int expires = -1;
	long keepaliveTime = -1;
	boolean noOffer = false;
	String callTo = null;
	int reinviteTime = -1;
	int acceptTime = -1;
	int hangupTime = -1;
	String redirectTo = null;
	String transferTo = null;
	int transferTime = -1;
	int oreinviteTime = -1;
	boolean audio = false;
	boolean video = false;
	int mediaPort = 0;
	boolean recvOnly = false;
	boolean sendOnly = false;
	boolean sendTone = false;
	File sendFile = null;
	File recvFile = null;
	boolean noPrompt = false;

	String fromUrl = null;
	String contactUrl = null;
	String username = null;
	String realm = null;
	String passwd = null;

	private UserAgent ua;
	private UserAgentProfile user_profile;
	private RegisterAgent ra;
	private SipProvider sipProvider;

	public SIPUserAgentImpl(SipProvider sipProvider) {
		this.sipProvider = sipProvider;
	}

	public void setAutoHangup(int seconds) {
		this.hangupTime = seconds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setRegistrationExpiry(int)
	 */
	public void setRegistrationExpiry(int seconds) {
		this.expires = seconds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setUnregistrating(boolean)
	 */
	public void setUnregistrating(boolean value) {
		this.unregist = value;
	}

	public void setUnregistratingAll(boolean value) {
		this.unregistAll = value;
	}

	public void setNoOfferInInvite(boolean value) {
		this.noOffer = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.homemotion.comm.impl.SIPUserAgent#setCallTarget(java.lang.String)
	 */
	public void setCallTarget(String target) {
		this.callTo = target;
	}

	public void setAutoAnswerTime(int seconds) {
		this.acceptTime = seconds;
	}

	public void setReinviteTime(int seconds) {
		this.reinviteTime = seconds;
	}

	public void setRedirectTo(String url) {
		this.redirectTo = url;
	}

	public void setCallTransferAfterSeconds(String url, int seconds) {
		this.transferTime = seconds;
		this.transferTo = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setAudioEnabled(boolean)
	 */
	public void setAudioEnabled(boolean enabled) {
		this.audio = enabled;
	}

	public void setVideoEnabled(boolean enabled) {
		this.video = enabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setMediaPort(int)
	 */
	public void setMediaPort(int port) {
		this.mediaPort = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setKeepAlive(long)
	 */
	public void setKeepAlive(long ms) {
		this.keepaliveTime = ms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setFromURL(java.lang.String)
	 */
	public void setFromURL(String url) {
		this.fromUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.homemotion.comm.impl.SIPUserAgent#setContactURL(java.lang.String)
	 */
	public void setContactURL(String url) {
		this.contactUrl = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setUserName(java.lang.String)
	 */
	public void setUserName(String userName) {
		this.username = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setUserRealm(java.lang.String)
	 */
	public void setUserRealm(String realm) {
		this.realm = realm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.homemotion.comm.impl.SIPUserAgent#setUserPassword(java.lang.String)
	 */
	public void setUserPassword(String pwd) {
		this.passwd = pwd;
	}

	/**
	 * receive only mode, no media is sent
	 */
	public void setReceiveOnlyMode(boolean value) {
		this.recvOnly = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setSendOnlyMode(boolean)
	 */
	public void setSendOnlyMode(boolean value) {
		this.sendOnly = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#setSendTestTone(boolean)
	 */
	public void setSendTestTone(boolean value) {
		this.sendTone = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.homemotion.comm.impl.SIPUserAgent#sendFile(java.io.File)
	 */
	public void sendFile(File sendFile) {
		this.sendFile = sendFile;
	}

	/**
	 * audio is recorded to the specified file
	 * 
	 * @param file
	 */
	public void receiveFile(File file) {
		this.recvFile = file;
	}

	/**
	 * do not prompt.
	 */
	public void setNoPrompt(boolean value) {
		this.noPrompt = value;
	}

	public void run() {
		try {
			user_profile = new UserAgentProfile(null);
			if (regist) {
				user_profile.do_register = true;
			}
			if (unregist) {
				user_profile.do_unregister = true;
			}
			if (unregistAll) {
				user_profile.do_unregister_all = true;
			}
			if (expires > 0) {
				user_profile.expires = expires;
			}
			if (keepaliveTime >= 0) {
				user_profile.keepalive_time = keepaliveTime;
			}
			if (noOffer) {
				user_profile.no_offer = true;
			}
			if (callTo != null) {
				user_profile.call_to = callTo;
			}
			if (acceptTime >= 0) {
				user_profile.accept_time = acceptTime;
			}
			if (hangupTime > 0) {
				user_profile.hangup_time = hangupTime;
			}
			if (redirectTo != null) {
				user_profile.redirect_to = redirectTo;
			}
			if (reinviteTime > 0) {
				user_profile.re_invite_time = reinviteTime;
			}
			if (transferTo != null) {
				user_profile.transfer_to = transferTo;
			}
			if (transferTime > 0) {
				user_profile.transfer_time = transferTime;
			}
			if (audio) {
				user_profile.audio = true;
			}
			if (video) {
				user_profile.video = true;
			}
			if (mediaPort > 0) {
				user_profile.video_port = (user_profile.audio_port = mediaPort) + 2;
			}
			if (fromUrl != null) {
				user_profile.from_url = fromUrl;
			}
			if (contactUrl != null) {
				user_profile.contact_url = contactUrl;
			}
			if (username != null) {
				user_profile.username = username;
			}
			if (realm != null)
				user_profile.realm = realm;
			if (passwd != null) {
				user_profile.passwd = passwd;
			}
			if (recvOnly) {
				user_profile.recv_only = true;
			}
			if (sendOnly) {
				user_profile.send_only = true;
			}
			if (sendTone) {
				user_profile.send_tone = true;
			}
			if (sendFile != null) {
				user_profile.send_file = sendFile.getAbsolutePath();
			}
			if (recvFile != null) {
				user_profile.recv_file = recvFile.getAbsolutePath();
			}
			if (noPrompt) {
				user_profile.no_prompt = true;
			}

			// use audio as default media in case of..
			if ((recvOnly || sendOnly || sendTone || sendFile != null || recvFile != null)
					&& !video)
				user_profile.audio = true;

			// run
			SIPUserAgentListener runner = new SIPUserAgentListener();
			UserAgent ua = new UserAgent(sipProvider, user_profile, runner);
			RegisterAgent ra = new RegisterAgent(sipProvider,
					user_profile.from_url, user_profile.contact_url,
					user_profile.username, user_profile.realm,
					user_profile.passwd, runner);

			if (user_profile.do_unregister_all) {
				LOGGER.info("UNREGISTER ALL contact URLs");
				unregisterall();
			}

			if (user_profile.do_unregister) {
				LOGGER.info("UNREGISTER the contact URL");
				unregister();
			}

			if (user_profile.do_register) {
				LOGGER.info("REGISTRATION");
				loopRegister(user_profile.expires, user_profile.expires / 2,
						user_profile.keepalive_time);
			}
			if (user_profile.call_to != null) {
				ua.hangup();
				LOGGER.info("UAC: CALLING " + user_profile.call_to);
				if (!user_profile.audio && !user_profile.video) {
					LOGGER.warn("ONLY SIGNALING, NO MEDIA");
				}
				ua.call(user_profile.call_to);
				// TODO wait???
				ua.hangup();
			}
		} catch (Exception e) {
			LOGGER.error("Error performing SIP User Agent init.", e);
		}
	}

	/**
	 * Register with the registrar server.
	 * 
	 * @param expire_time
	 *            expiration time in seconds
	 */
	void register(int expire_time) {
		if (ra.isRegistering())
			ra.halt();
		ra.register(expire_time);
	}

	/**
	 * Periodically registers the contact address with the registrar server.
	 * 
	 * @param expire_time
	 *            expiration time in seconds
	 * @param renew_time
	 *            renew time in seconds
	 * @param keepalive_time
	 *            keep-alive packet rate (inter-arrival time) in milliseconds
	 */
	void loopRegister(int expire_time, int renew_time, long keepalive_time) {
		if (ra.isRegistering())
			ra.halt();
		ra.loopRegister(expire_time, renew_time, keepalive_time);
	}

	/** Unregister with the registrar server */
	void unregister() {
		if (ra.isRegistering())
			ra.halt();
		ra.unregister();
	}

	/** Unregister all contacts with the registrar server */
	void unregisterall() {
		if (ra.isRegistering())
			ra.halt();
		ra.unregisterall();
	}
}
