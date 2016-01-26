package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.TemporalType.DATE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class Event extends BaseEntity {
	
	@Column(nullable = false)
	private String name;

	@Lob
	@Column(nullable = true)
	private String description;
	
	@Temporal(DATE)
	private Date time;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Asset> assets;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<User> users;
	
	public Event() {}
	
	public Event(String name, String description, Date time) {		
		assets = new ArrayList<Asset>();
		users = new ArrayList<User>();
		setName(name);
		setDescription(description);
		setTime(time);		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public void removeAllUsers() {
		users.clear();
	}
	
}
