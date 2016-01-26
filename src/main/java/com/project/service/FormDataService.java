package com.project.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.Data;

@Service
public class FormDataService {

	private Map<String, Map<String, Map<String, Data>>> formData;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public FormDataService() {
		formData = new HashMap<String, Map<String, Map<String, Data>>>();
	}
	
	public void initializeFormData() {
		try {
			formData.put("user", objectMapper.readValue(resourceLoader.getResource("classpath:forms/user.json").getInputStream(), new TypeReference<HashMap<String, Map<String, Data>>>(){}));
			formData.put("community", objectMapper.readValue(resourceLoader.getResource("classpath:forms/community.json").getInputStream(), new TypeReference<HashMap<String, Map<String, Data>>>(){}));
			formData.put("collection", objectMapper.readValue(resourceLoader.getResource("classpath:forms/collection.json").getInputStream(), new TypeReference<HashMap<String, Map<String, Data>>>(){}));
			formData.put("group", objectMapper.readValue(resourceLoader.getResource("classpath:forms/group.json").getInputStream(), new TypeReference<HashMap<String, Map<String, Data>>>(){}));
		} catch (IOException e) {
			System.out.println("Unable to read and map property keys from json.");
			e.printStackTrace();
		}
	}

	public Map<String, Map<String, Map<String, Data>>> getPropertyData() {
		return formData;
	}

	public void setPropertyData(Map<String, Map<String, Map<String, Data>>> propertyKeys) {
		this.formData = propertyKeys;
	}
	
	public Map<String, Map<String, Data>> getDataByModel(String model) {
		return formData.get(model);
	}
	
	public Map<String, Data> getDataByModelAndProperty(String model, String property) {
		return formData.get(model).get(property);
	}
	
	public void setDataForModel(String model, Map<String, Map<String, Data>> data) {
		formData.put(model, data);
	}
	
	public void setDataForModelAndProperty(String model, String property, Map<String, Data> data) {
		formData.get(model).put(property, data);
	}
}
