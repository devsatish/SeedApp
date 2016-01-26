package com.project.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class OrderedBaseEntity extends BaseEntity {

	@Column(nullable = false)
	private Long position;

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}
	
}
