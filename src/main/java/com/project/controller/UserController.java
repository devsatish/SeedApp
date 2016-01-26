package com.project.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.User;
import com.project.model.repo.UserRepo;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Transactional
	@RequestMapping("/get")
	public User getUser(Principal principal) {		
		return userRepo.findByUsername(principal.getName());
	}
	
	@RequestMapping("/update")
	public String updateUser(Principal principal, @RequestBody Map<String, Map<String, String>> data) {
		String username = principal.getName();
		User user = userRepo.findByUsername(username);
		for (Map.Entry<String, Map<String, String>> propertyMap : data.entrySet()) {
		    String property = propertyMap.getKey();
		    switch(property) {
		    	case "profile": {
		    		user.setProfile(propertyMap.getValue());
		    		user = userRepo.save(user);
		    		Map<String, Map<String, String>> response = new HashMap<String, Map<String, String>>();
		    		response.put(property, user.getProfile());
		    		simpMessagingTemplate.convertAndSendToUser(username, "/queue/user", response);
		    	} break;
		    	case "settings": {
		    		user.setSettings(propertyMap.getValue());
		    		user = userRepo.save(user);
		    		Map<String, Map<String, String>> response = new HashMap<String, Map<String, String>>();
		    		response.put(property, user.getSettings());
		    		simpMessagingTemplate.convertAndSendToUser(username, "/queue/user", response);
		    	} break;
		    	default: {
		    		throw new PropertyNotFoundException("No property " + property + " on user object!");
		    	}
		    }
		}		
		return "SUCCESS";
	}
	
	public class PropertyNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public PropertyNotFoundException(String message) {
			super(message);
		}
	}
	 
}
