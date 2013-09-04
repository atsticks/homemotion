package org.homemotion.comm;

import java.io.File;

public interface SIPUserAgent {

	public abstract void setRegistrationExpiry(int seconds);

	public abstract void setUnregistrating(boolean value);

	public abstract void setCallTarget(String target);

	public abstract void setAudioEnabled(boolean enabled);

	/**
	 * (first) local media port.
	 * 
	 * @param port
	 */
	public abstract void setMediaPort(int port);

	/**
	 * send keep-alive packets each <millisecs>
	 * 
	 * @param ms
	 */
	public abstract void setKeepAlive(long ms);

	/**
	 * user's address-of-record (AOR)
	 * 
	 * @param ms
	 */
	public abstract void setFromURL(String url);

	/**
	 * user's contact url
	 * 
	 * @param ms
	 */
	public abstract void setContactURL(String url);

	/**
	 * user name used for authentication
	 * 
	 * @param userName
	 */
	public abstract void setUserName(String userName);

	/**
	 * realm used for authentication
	 * 
	 * @param realm
	 */
	public abstract void setUserRealm(String realm);

	/**
	 * passwd used for authentication
	 * 
	 * @param pwd
	 */
	public abstract void setUserPassword(String pwd);

	/**
	 * send only mode, no media is received
	 */
	public abstract void setSendOnlyMode(boolean value);

	/**
	 * send only mode, an audio test tone is generated
	 */
	public abstract void setSendTestTone(boolean value);

	/**
	 * audio is played from the specified file
	 * 
	 * @param file
	 */
	public abstract void sendFile(File sendFile);

}