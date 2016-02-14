package com.project.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.model.SkyTuple;

@Service
public class SkylineService {
	
	private Map<Long, SkyTuple> skyline;
	
	public SkylineService() {
		skyline = new HashMap<Long, SkyTuple>();
	}
	
	public void clearSkyline() {
		skyline = new HashMap<Long, SkyTuple>();
	}
	
	public Map<Long, SkyTuple> getSkyline() {
		System.out.println("\n\nSKYLINE SIZE: " + skyline.size() + "\n\n");
		return skyline;
	}
	
	public SkyTuple getTupleByTimeStampId(Long timeStampId) {
		return skyline.get(timeStampId);
	}
	
	public boolean isTupleInSkyline(Long timeStampId) {
		return skyline.get(timeStampId) != null;
	}
	
	public synchronized void processTuple(SkyTuple tuple) {
		if(tuple.getRemove()) {
			System.out.println("\n\nREMOVE\n\n");
			skyline.remove(tuple.getTimeStampId());
		}
		else {
			System.out.println("\n\nADD\n\n");
			skyline.put(tuple.getTimeStampId(), tuple);
		}
	}
}
