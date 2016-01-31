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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "community_id"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Collection.class, property = "name")
public class Collection extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@JsonIgnore
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	private User owner;

	@JsonIgnore
	@ManyToOne(cascade = { DETACH, MERGE, PERSIST, REFRESH }, optional = false)
	private Group curators;

	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Community community;
	
	@OneToMany(cascade = { DETACH, MERGE, REFRESH }) 
	@JsonIdentityReference(alwaysAsId = true)
	private List<Asset> assets;
	
	@Column(nullable = false)
	private boolean isPublic;
	
	public Collection() {
		assets = new ArrayList<Asset>();
		setPublic(true);
	}

	public Collection(String name, User owner, Group curators, Community communtiy) {
		this();
		setName(name);
		setOwner(owner);
		setCurators(curators);
		setCommuntiy(communtiy);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Group getCurators() {
		return curators;
	}

	public void setCurators(Group curators) {
		this.curators = curators;
	}
	
	public Community getCommuntiy() {
		return community;
	}

	public void setCommuntiy(Community communtiy) {
		this.community = communtiy;
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
	
	public void clearAssets() {
		assets.clear();
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
