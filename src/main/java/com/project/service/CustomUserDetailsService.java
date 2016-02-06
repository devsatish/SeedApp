package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.model.SecurityUser;
import com.project.model.User;
import com.project.model.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);		
		if(user == null) {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		if(!user.isActive()) {
			throw new LockedException("Account not active");
		}
		return new SecurityUser(user);
	}
	
}
