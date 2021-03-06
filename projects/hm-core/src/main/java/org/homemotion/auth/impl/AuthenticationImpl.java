package org.homemotion.auth.impl;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.auth.Authentication;
import org.homemotion.auth.SecurityService;
import org.homemotion.auth.User;
import org.homemotion.util.Encrypter;

@Singleton
public class AuthenticationImpl implements Authentication {

	@Inject
	private SecurityService securityServices;
	
	@Inject
	private Encrypter encrypter;
	
	private User currentUser;
	
	@Override
	public User authenticate(String uid, String pwd) {
		if(this.currentUser!=null) {
			throw new SecurityException("User already authenticated: " + getPrincipal());
		}
		User user = securityServices.getUser(uid);
		if(user==null){
			throw new SecurityException("Authentication failed.");
		}
		String encryptedPwd = encrypter.encrypt(pwd);
		if(!encryptedPwd.equals(user.getPassword())){
			throw new SecurityException("Authentication failed.");
		}
		this.currentUser = user;
		return getPrincipal();
	}

	@Override
	@Produces
	public User getPrincipal() {
		if(currentUser!=null){
			return currentUser;
		}
		return securityServices.getAnonymousUser();
	}

	@Override
	public boolean isAuthenticated() {
		return currentUser != null && !currentUser.equals(securityServices.getAnonymousUser());
	}

	@Override
	public void logout() {
		this.currentUser = null;
	}

}
