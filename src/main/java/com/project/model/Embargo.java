package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Embargo extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String guarantor;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(nullable = false)
    private Integer duration;

    @Lob
    @Column(nullable = false)
    private String description;

    public Embargo() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}

