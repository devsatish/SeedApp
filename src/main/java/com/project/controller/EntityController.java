package com.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.service.EntityService;

@RestController
@RequestMapping("/entity")
public class EntityController {
	
	@Autowired
	private EntityService entityService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/{entity}/get")
	public List<Map<String, Object>> getControlledVocabularyByEntity(@PathVariable String entity, @RequestBody Map<String, Object> data) {
		
		try {			
			List<String> properties = (List<String>) data.get("properties");
			Map<String, Map<String, String>> filters = (Map<String, Map<String, String>>) data.get("filters");

			List<Map<String, Object>> cv = new ArrayList<Map<String, Object>>();
			
			((List<Object>) entityService.getControlledVocabulary(entity, filters, properties.stream().toArray(String[]::new))).parallelStream().forEach(obj -> {
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 0; i < properties.size(); i++) {
					map.put(properties.get(i), ((Object[]) obj)[i]);
				}
				cv.add(map);
			});
			
			return cv;
			
		} catch (ClassNotFoundException e) {
			throw new EntityException("Could not get controlled vocabulary for provided properties of entity " + entity + "!");
		}
	}
	
	public class EntityException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public EntityException(String message) {
			super(message);
		}
	}
	
}
