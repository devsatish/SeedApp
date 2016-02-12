package com.project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.model.Skyline;

@Service
public class SkylineService {
	
	private Skyline skyline = new Skyline();
	
	public Skyline getSkyline() {
		return skyline;
	}
	
	public void addVectorAsString(String vectorAsMessageAsSting) {
		skyline.vectors.add(vectorAsMessageAsSting);
	}
		
}
