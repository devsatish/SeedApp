package com.project.controller;

import static com.project.enumeration.GroupType.ADMINISTRATIVE;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Community;
import com.project.model.Group;
import com.project.model.User;
import com.project.model.repo.CommunityRepo;
import com.project.model.repo.GroupRepo;
import com.project.model.repo.UserRepo;

@RestController
@RequestMapping("/community")
public class CommunityController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private CommunityRepo communityRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Transactional
	@RequestMapping("/create")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Community create(Principal principal, @RequestBody Map<String, String> data) {
		
		String name = data.get("name");		
		String parentCommunityIdString = data.get("parentCommunity");
		
		User admin = userRepo.findByUsername(principal.getName());
		
		String groupName = name + "-" + parentCommunityIdString + "-Admin";
		
		Group admins = groupRepo.findByName(groupName);
				
		if(admins == null) {
			admins = new Group(groupName, admin, ADMINISTRATIVE, true);			
			admins.addUser(admin);
		}
		
		Community community;
		try {
			if(parentCommunityIdString != null) {
				Community parentCommunity = communityRepo.findById(Long.valueOf(parentCommunityIdString));
				if(parentCommunity == null) {
					throw new CreateCommunityException("Parent community with id" + parentCommunityIdString + " does not exist.");
				}
				
				community = communityRepo.save(new Community(name, admins, parentCommunity));
								
				parentCommunity.addCommunity(community);
				parentCommunity = communityRepo.save(parentCommunity);
				
				simpMessagingTemplate.convertAndSend("/broadcast/community/all", parentCommunity);
			}
			else {
				community = communityRepo.save(new Community(name, admins));				
				simpMessagingTemplate.convertAndSend("/broadcast/community/all", community);
			}
			return community;
		}
		catch(DataIntegrityViolationException e) {
			throw new CreateCommunityException("Community " + name + " already exists.");
		}
	}
	
	@RequestMapping("/all/get")
	public List<Community> getAll() {
		return communityRepo.findByCommunityIsNull();
	}

	public class CreateCommunityException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public CreateCommunityException(String message) {
			super(message);
		}
	}
	
}
