package com.project;

import static com.project.enumeration.GroupType.ACCESS;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.model.Group;
import com.project.model.User;
import com.project.model.repo.GroupRepo;
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
	private GroupRepo groupRepo;
	
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
		
		
		User admin = new User("admin", passwordEncoder.encode("abc123"), "ROLE_ADMIN", true);
		
		Map<String, String> profile = new HashMap<String, String>();
		
		profile.put("firstName", "Admin");
		profile.put("lastName", "McGee");
		profile.put("email", "wwelling@outlook.com");
		
		admin.setProfile(profile);
		
		userRepo.save(admin);
		
		User user = new User("user", passwordEncoder.encode("abc123"), "ROLE_USER", true);
		
		profile = new HashMap<String, String>();
		
		profile.put("firstName", "User");
		profile.put("lastName", "McGee");
		profile.put("email", "wwelling@outlook.com");
		
		user.setProfile(profile);
		
		userRepo.save(user);
		
		Group group = new Group("Everyone", admin, ACCESS, false);
		
		group.addUser(user);
		
		groupRepo.save(group);
	}

}
