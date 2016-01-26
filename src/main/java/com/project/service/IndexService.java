package com.project.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
	
	@Autowired
	private ServletContext servletContext;
	
	@Value("${info.build.version}")
	private String version;

	private String index;
	
	public IndexService() { }
	
	public void templateIndex() {
		String base = servletContext.getContextPath();		
		try {
			index = new String(Files.readAllBytes(Paths.get(servletContext.getRealPath("/WEB-INF/view/index.asc"))));
		} catch (IOException e) {
			System.out.println("Unable to read and template index.asc.");
			e.printStackTrace();
		}		
		index = index.replace("${base}", base).replace("${version}", version);
	}

	public String getIndex() {
		return index;
	}
	
}
