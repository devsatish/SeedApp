package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "community_id"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Community.class, property = "name")
public class Community extends BaseEntity {
	
	@Column(nullable = false)
	private String name;

	@JsonIgnore
	@OneToOne(cascade = { DETACH, MERGE, PERSIST, REFRESH }, optional = false)	
	private Group admins;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = true)	
	@JsonIdentityReference(alwaysAsId = true)
	private Community community;

	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Community> communities;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Collection> collections;
	
	@Column(nullable = false)
	private boolean isPublic;

	public Community() {
		communities = new ArrayList<Community>();
		collections = new ArrayList<Collection>();
		setPublic(true);
	}
	
	public Community(String name, Group admins) {
		this();
		setName(name);
		setAdmins(admins);
	}
	
	public Community(String name, Group admins, Community community) {
		this(name, admins);
		setCommunity(community);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getAdmins() {
		return admins;
	}

	public void setAdmins(Group admins) {
		this.admins = admins;
	}
	
	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public List<Community> getCommunities() {
		return communities;
	}

	public void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	
	public void addCommunity(Community community) {
		communities.add(community);
	}
	
	public void removeCollection(Community community) {
		communities.remove(community);
	}
	
	public void clearCommunities() {
		communities.clear();
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
	
	public void clearCollections() {
		collections.clear();
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
		
}
