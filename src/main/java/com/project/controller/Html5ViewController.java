package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.service.IndexService;

@EnableWebMvc
@ControllerAdvice
public class Html5ViewController {
	
	@Autowired
	private IndexService indexService;

	@Value("${info.build.version}")
	private String version;
	
	private HttpHeaders headers;
	
	public Html5ViewController() {
		headers = new HttpHeaders();
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })	
	public ResponseEntity<String> view() {
		return new ResponseEntity<String>(indexService.getIndex(), headers, HttpStatus.ACCEPTED);
	}
	
}
