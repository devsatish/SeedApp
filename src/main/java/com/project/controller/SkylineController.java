package com.project.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.User;
import com.project.model.Skyline;
import com.project.model.repo.UserRepo;
import com.project.service.SkylineService;

@RestController
@RequestMapping("/skyline")
public class SkylineController {
	
	@Autowired
	private SkylineService skylineService;

	@RequestMapping("/get")
	public @ResponseBody Skyline getSkyline() {
		System.out.println("\nGetting skyline!!!!\n");
		return skylineService.getSkyline();
	}
	
}
