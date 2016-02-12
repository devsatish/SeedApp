package com.project.receiver;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.project.service.SkylineService;

public class StormQueueReceiver {
	
	private SkylineService skylineService;
	
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public StormQueueReceiver(SkylineService skylineService, SimpMessagingTemplate simpMessagingTemplate) {
		this.skylineService = skylineService;
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
    	
    	skylineService.addVectorAsString(message);
    	
    	enter(2, "Received <" + message + ">");        
        if(simpMessagingTemplate != null) {
        	simpMessagingTemplate.convertAndSend("/broadcast/skyline", message);
        }
        else {
        	enter(2, "Cannot send websocket message!!!");
        }

    }
    
}