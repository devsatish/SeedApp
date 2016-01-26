package com.project.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
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

	@OneToMany(cascade = ALL, orphanRemoval = true)
	private List<Theme> themes;
	
	@OneToMany(cascade = ALL, orphanRemoval = true)
	private List<Exhibit> exhibits;

	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Asset> assets;

	@ManyToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Group> groups;
	
	@ManyToOne(cascade = ALL, optional = false)
    private Timeline timeLine;

	public User() {
		profile = new HashMap<String, String>();
		settings = new HashMap<String, String>();
		themes = new ArrayList<Theme>();
		exhibits = new ArrayList<Exhibit>();
		assets = new ArrayList<Asset>();
		groups = new ArrayList<Group>();
		setActive(false);
		setTimeLine(new Timeline());
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

	public Map<String, String> getProfile() {
		return profile;
	}

	public void setProfile(Map<String, String> profile) {
		this.profile = profile;
	}
	
	public void addProfileDescriptor(String key, String value) {
		profile.put(key, value);
	}
	
	public void removeProfileDescriptor(String key) {
		profile.remove(key);
	}
	
	public void clearProfile() {
		profile.clear();
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

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}

	public void addTheme(Theme theme) {
		if (theme.isSelected()) {
			themes.parallelStream().forEach(t -> {
				t.setSelected(false);
			});
		}
		themes.add(theme);
	}

	public void setSelectedTheme(String themeName) {
		themes.parallelStream().forEach(t -> {
			if (t.isSelected()) {
				t.setSelected(false);
			}
			if (t.getName().equals(themeName)) {
				t.setSelected(true);
			}
		});
	}

	public void removeTheme(Theme theme) {
		themes.remove(theme);
	}

	public void clearThemes() {
		themes.clear();
	}
	
	public List<Exhibit> getExhibits() {
		return exhibits;
	}

	public void setExhibits(List<Exhibit> exhibits) {
		this.exhibits = exhibits;
	}

	public void addExhibit(Exhibit exhibit) {		
		exhibits.add(exhibit);
	}

	public void removeExhibit(Exhibit exhibit) {
		exhibits.remove(exhibit);
	}

	public void clearExhibits() {
		exhibits.clear();
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public void addAsset(Asset asset) {
		assets.add(asset);
	}

	public void removeAsset(Asset asset) {
		assets.remove(asset);
	}

	public void removeAllAssets() {
		assets.clear();
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void addGroup(Group group) {
		groups.add(group);
	}

	public void removeGroup(Group group) {
		groups.remove(group);
	}

	public void removeAllGroups() {
		groups.clear();
	}

	public Timeline getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(Timeline timeLine) {
		this.timeLine = timeLine;
	}
	
}
