package org.homemotion.util.impl;

import org.homemotion.util.Encrypter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Default
public final class MD5Encrypter implements Encrypter{

	private static Logger LOG = LoggerFactory.getLogger(MD5Encrypter.class);
	private MessageDigest mdEnc;
	
	public MD5Encrypter(){
		try {
			mdEnc = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Failed to load MD5 algorithm!",e);
		} 
	}
	
	public String encrypt(String toEnc){
		// Encryption algorithm
		this.mdEnc.update(toEnc.getBytes(), 0, toEnc.length());
		return new BigInteger(1, this.mdEnc.digest()).toString(16); // Encrypted string
	}
	
}
