package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.EnumType.STRING;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.enumeration.GroupType;

@Entity
@Table(name = "association")
public class Group extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String name;
	
	@JsonIgnore
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	private User creator;
	
	@Column(nullable = false)
	@Enumerated(STRING)
	private GroupType type;
	
	@Column(nullable = false)
	private boolean exclusive;
	
	@ManyToMany(cascade = { DETACH, MERGE, REFRESH })
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = User.class, property = "username") 
	@JsonIdentityReference(alwaysAsId = true)
	private List<User> users;

	public Group() {
		users = new ArrayList<User>();
		setExclusive(true);
	}
	
	public Group(String name, User creator, GroupType type) {
		this();
		setName(name);
		setCreator(creator);
		setType(type);
	}
	
	public Group(String name, User creator, GroupType type, boolean exclusive) {
		this(name, creator, type);
		setExclusive(exclusive);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}
	
	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
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
	
	public void clearUsers() {
		users.clear();
	}
	
}
