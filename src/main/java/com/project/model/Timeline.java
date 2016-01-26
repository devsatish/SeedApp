package com.project.model;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Timeline extends BaseEntity {

	@OneToMany(cascade = ALL, orphanRemoval = true)
	private List<Event> events;
	
	public Timeline() {
		events = new ArrayList<Event>();
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

	public void removeAllEvents() {
		events.clear();
	}
	
}
