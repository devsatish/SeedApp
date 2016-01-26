package com.project.controller;

import static com.project.enumeration.GroupType.CURATOR;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Collection;
import com.project.model.Community;
import com.project.model.Group;
import com.project.model.User;
import com.project.model.repo.CollectionRepo;
import com.project.model.repo.CommunityRepo;
import com.project.model.repo.GroupRepo;
import com.project.model.repo.UserRepo;

@RestController
@RequestMapping("/collection")
public class CollectionController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private CollectionRepo collectionRepo;
	
	@Autowired
	private CommunityRepo communityRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Transactional
	@RequestMapping("/create")
	public @ResponseBody Collection create(Principal principal, @RequestBody Map<String, String> data) {
		
		String name = data.get("name");
		String groupName = data.get("group");
		String communityIdString = data.get("community");
		
		User owner = userRepo.findByUsername(principal.getName());
		
		Group curators;
		if(groupName != null) {
			curators = groupRepo.findByName(groupName);
			if(curators == null) {
				throw new CreateCollectionException("Group " + groupName + " does not exist.");
			}
		}
		else {
			curators = new Group(name + "_Curators", owner, CURATOR, true);		
			curators.addUser(owner);
		}
		
		Collection collection;
		try {
			Community community = communityRepo.findById(Long.valueOf(communityIdString)); 
			if(community == null) {
				throw new CreateCollectionException("Community with id " + communityIdString + " does not exist.");
			}
			
			collection = collectionRepo.save(new Collection(name, owner, curators, community));
			
			community.addCollection(collection);
			community = communityRepo.save(community); 
			
			simpMessagingTemplate.convertAndSend("/broadcast/community/all", community);			
			simpMessagingTemplate.convertAndSend("/broadcast/collection/all", collection);
			
			return collection;
		}
		catch(DataIntegrityViolationException e) {
			throw new CreateCollectionException("Collection " + name + " already exists.");
		}
	}
	
	@RequestMapping("/all/get")
	public List<Collection> getAll() {
		return collectionRepo.findAll();
	}
	
	public class CreateCollectionException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public CreateCollectionException(String message) {
			super(message);
		}
	}
	
}
