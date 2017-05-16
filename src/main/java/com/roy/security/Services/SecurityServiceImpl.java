package com.roy.security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService UserDetailsService;
	
	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		} 
		else {
			return null;
		}
	}

	@Override
	public void autologin(String username, String password) {
		UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		authenticationManager.authenticate(userAuthToken);
		
		if (userAuthToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(userAuthToken);
		}
	}

}
