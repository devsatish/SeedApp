package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"position", "theme_id"}))
public class Color extends OrderedBaseEntity {

	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String hex;
	
	@Column(nullable = false)
	private double opacity;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	private Theme theme;
	
	public Color() {
		
	}
	
	public Color(String name, String hex, double opacity, Theme theme) {
		this.name = name;
		this.hex = hex;
		this.opacity = opacity;
		this.theme = theme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	
}
