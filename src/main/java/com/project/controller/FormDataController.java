package com.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Data;
import com.project.service.FormDataService;

@RestController
@RequestMapping("/data")
public class FormDataController {
	
	@Autowired
	private FormDataService formDataService;

	@RequestMapping("/{model}/{property}/get")
	public Map<String, Data> getDataByModelAndProperty(@PathVariable String model, @PathVariable String property) {
		return formDataService.getDataByModelAndProperty(model, property);
	}
	
	@RequestMapping("/{model}/get")
	public Map<String, Map<String, Data>> getDataByModel(@PathVariable String model) {
		return formDataService.getDataByModel(model);
	}
	
	@RequestMapping("/get")
	public Map<String, Map<String, Map<String, Data>>> getFormData() {
		return formDataService.getPropertyData();
	}
	
}
