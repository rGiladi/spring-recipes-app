package com.roy.security.Services;

import com.roy.models.User;

public interface UserService {
	
	void save(User user);
	
	User findByUsername(String username);
	
	User findByUserId(long id);
	
	User findByEmail(String email);
	
}
