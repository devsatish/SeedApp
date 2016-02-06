package com.project.model;

import static javax.persistence.FetchType.EAGER;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "account")
public class User extends BaseEntity {

	@Column(length = 50, nullable = false, unique = true)
	private String username;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column(length = 20, nullable = false)
	private String role;
	
	@Column(nullable = false)
	private boolean active;

	@ElementCollection(fetch = EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	private Map<String, String> profile;
	
	@ElementCollection(fetch = EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	private Map<String, String> settings;

	public User() {
		profile = new HashMap<String, String>();
		settings = new HashMap<String, String>();
		setActive(false);
	}

	public User(String username, String password, String role) {
		this();
		setUsername(username);
		setPassword(password);
		setRole(role);
	}
	
	public User(String username, String password, String role, boolean active) {
		this();
		setUsername(username);
		setPassword(password);
		setRole(role);
		setActive(active);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}
	
	public void addSettingsDescriptor(String key, String value) {
		settings.put(key, value);
	}
	
	public void removeSettingsDescriptor(String key) {
		settings.remove(key);
	}
	
	public void clearSettings() {
		settings.clear();
	}
	
}
