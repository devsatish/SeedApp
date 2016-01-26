package com.project.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class Exhibit extends BaseEntity {
	
	private String name;
	
	@Lob
	@Column(nullable = true)
	private String description;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	private User user;
	
	@ManyToOne(cascade = ALL, optional = false)
	private Theme theme;
		
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Collection> collections;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Asset> assets;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Event> events;
	
	public Exhibit() {
		collections = new ArrayList<Collection>();
		assets = new ArrayList<Asset>();
		events = new ArrayList<Event>();
	}
	
	public Exhibit(String name, User user, Theme theme) {
		this();		
		this.name = name;
		this.user = user;
		this.theme = theme;
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

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}
	
	public void addCollection(Collection collection) {
		collections.add(collection);
	}
	
	public void removeCollection(Collection collection) {
		collections.remove(collection);
	}
	
	public void removeAllCollection() {
		collections.clear();
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
	
	public void removeAllAsset() {
		assets.clear();
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public void removeAllEvent() {
		events.clear();
	}
	
}
