 package com.project.model;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.MERGE;
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
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "revision", "collection_id"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Asset.class, property = "name")
public class Asset extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String url;
	
	@Column(nullable = false)
	private int revision;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = true)
	private Asset version;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Collection collection;
	
	@ManyToOne(cascade = { DETACH, MERGE, REFRESH }, optional = true)
	private Embargo embargo;

	@OneToMany(cascade = { DETACH, MERGE, REFRESH })
	private List<Metadata> metadata;

	public Asset() {
		metadata = new ArrayList<Metadata>();
		setRevision(0);
	}

	public Asset(String name, String url) {
		this();
		setName(name);
		setUrl(url);
	}
	
	public Asset(String name, String url, int revision) {
		this();
		setName(name);
		setUrl(url);
		setRevision(revision);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	public Asset getVersion() {
		return version;
	}

	public void setVersion(Asset version) {
		this.version = version;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	public Embargo getEmbargo() {
		return embargo;
	}

	public void setEmbargo(Embargo embargo) {
		this.embargo = embargo;
	}

	public List<Metadata> getMetadata() {
		return metadata;
	}

	public void setMetadata(List<Metadata> metadata) {
		this.metadata = metadata;
	}
	
	public void addMetadata(Metadata metadatum) {
		metadata.add(metadatum);
	}
	
	public void removeMetadata(Metadata metadatum) {
		metadata.remove(metadatum);
	}
	
	public void clearMetadatas() {
		metadata.clear();
	}
	
}
