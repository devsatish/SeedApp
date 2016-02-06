package com.project.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity implements Comparable<BaseEntity> {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(this.getClass())) {
			return ((BaseEntity) obj).getId().equals(this.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * 31 + getId().hashCode();
	}
	
	@Override
	public int compareTo(BaseEntity o) {
		return (this.getId() > o.getId()) ? 1 : (this.getId() < o.getId()) ? -1 : 0;
	}

}
