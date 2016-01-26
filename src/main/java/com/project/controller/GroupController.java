package com.project.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.enumeration.GroupType;
import com.project.model.Group;
import com.project.model.User;
import com.project.model.repo.GroupRepo;
import com.project.model.repo.UserRepo;

@RestController
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Transactional
	@RequestMapping("/create")
	public Group create(Principal principal, @RequestBody Map<String, String> data) {
		String name = data.get("name");		
		GroupType type = GroupType.valueOf(data.get("type"));
		boolean exclusive = Boolean.parseBoolean(data.get("exclusive"));
		User creator = userRepo.findByUsername(principal.getName());
		Group group;
		try {
			group = groupRepo.save(new Group(name, creator, type, exclusive));
			return group;
		}
		catch(DataIntegrityViolationException e) {
			throw new CreateGroupException("Group " + name + " already exists.");
		}
	}

	public class CreateGroupException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public CreateGroupException(String message) {
			super(message);
		}
	}
	
}
