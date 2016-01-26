package com.project.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"position", "user_id"}))
public class Theme extends OrderedBaseEntity {

	private String name;

	private boolean selected;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	private User user;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Font> fonts;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Color> colors;
	
	@ElementCollection(fetch = EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	private Map<String, String> map;

	public Theme() {
		map = new HashMap<String, String>();
		fonts = new ArrayList<Font>();
		colors = new ArrayList<Color>();
	}

	public Theme(String name, boolean selected) {
		this();
		setName(name);
		setSelected(selected);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void addDescriptor(String name, String value) {
		map.put(name, value);
	}

	public String getValue(String name) {
		return map.get(name);
	}

	public List<Font> getFonts() {
		return fonts;
	}

	public void setFonts(List<Font> fonts) {
		this.fonts = fonts;
	}
	
	public void addFont(Font font) {
		fonts.add(font);
	}
	
	public void removeFont(Font font) {
		fonts.remove(font);
	}
	
	public void clearFonts() {
		fonts.clear();
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	
	public void addColor(Color color) {
		colors.add(color);
	}
	
	public void removeColor(Color color) {
		colors.remove(color);
	}
	
	public void clearColors() {
		colors.clear();
	}
	
}
