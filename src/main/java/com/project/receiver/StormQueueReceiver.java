package com.project.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import com.project.model.SkyTuple;
import com.project.service.SkylineService;

public class StormQueueReceiver {
	
	private SkylineService skylineService;
		
	private ObjectMapper objectMapper;
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public StormQueueReceiver(SkylineService skylineService, ObjectMapper objectMapper, SimpMessagingTemplate simpMessagingTemplate) {
		this.skylineService = skylineService;
		this.objectMapper = objectMapper;
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	
	public void enter(int times, String message) {
		for(int i = 0; i < times; i++)
			System.out.println();
		System.out.println(message);
		for(int i = 0; i < times; i++)
			System.out.println();
	}
	
    public void receiveMessage(String message) {
    	    	
    	SkyTuple skyTuple = null;
    	try {
    		skyTuple = objectMapper.readValue(message, SkyTuple.class);
    	}
    	catch(Exception e) {
    		enter(2, "Exception mapping message to SkyTuple!!!");
    	}
    	
    	enter(2, "SkyTuple \n" + skyTuple.toString() + "\n");   
    	
    	if(skyTuple != null) {
    	
	    	if(skylineService != null) {
	    		skylineService.processTuple(skyTuple);
	    	}
	    	else {
	    		enter(2, "Cannot process tuple!!! Skyline service is null!!!");
	    	}		
	    	
	        if(simpMessagingTemplate != null) {
	        	simpMessagingTemplate.convertAndSend("/broadcast/skyline", skyTuple);
	        }
	        else {
	        	enter(2, "Cannot send websocket message!!!");
	        }
        
    	}
    	else {
    		enter(2, "SkyTuple node is null!!!");
    	}
    }
    
}