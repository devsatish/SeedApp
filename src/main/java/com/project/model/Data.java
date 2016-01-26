package com.project.model;

import java.util.HashMap;
import java.util.Map;

public class Data {

	private String gloss;
	
	private String type;
	
	private String[] select;
	
	private String entity;
	
	private boolean required;
	
	private String[] prerequisites;
	
	private Map<String, String> validations;
	
	private String[] facets;
	
	private Map<String, Map<String, String>> filters;
	
	public Data() {
		validations = new HashMap<String, String>();
		filters = new HashMap<String, Map<String, String>>();
	}
	
	public String getGloss() {
		return gloss;
	}

	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String[] getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String[] prerequisites) {
		this.prerequisites = prerequisites;
	}

	public Map<String, String> getValidations() {
		return validations;
	}

	public void setValidations(Map<String, String> validations) {
		this.validations = validations;
	}

	public String[] getFacets() {
		return facets;
	}

	public void setFacets(String[] facets) {
		this.facets = facets;
	}

	public Map<String, Map<String, String>> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Map<String, String>> filters) {
		this.filters = filters;
	}
	
}
