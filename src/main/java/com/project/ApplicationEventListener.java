package com.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.model.User;
import com.project.model.repo.UserRepo;
import com.project.service.EntityService;
import com.project.service.FormDataService;
import com.project.service.IndexService;

@Component
@Profile("!test")
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private UserRepo userRepo;
		
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private FormDataService formDataService;
	
	@Autowired
	private EntityService entityService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		indexService.templateIndex();
		
		formDataService.initializeFormData();
		
		entityService.initializeWhitelist();
		
		
		new User("admin", passwordEncoder.encode("abc123"), "ROLE_ADMIN", true);
		
		new User("user", passwordEncoder.encode("abc123"), "ROLE_USER", true);
		
	}

}
