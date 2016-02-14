package com.project.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.SkylineService;
import com.project.model.SkyTuple;

@RestController
@RequestMapping("/skyline")
public class SkylineController {
	
	@Autowired
	private SkylineService skylineService;
	
	@RequestMapping("/get")
	public @ResponseBody Map<Long, SkyTuple> getSkyline() {
    	return skylineService.getSkyline();
	}
	
}
