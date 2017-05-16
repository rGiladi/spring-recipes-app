package com.roy.security.Services;


public interface SecurityService {
	
	String findLoggedInUsername();
	
	void autologin(String username, String password);
}
