package com.project.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Group;

public interface GroupRepo extends JpaRepository<Group, Long> {

	public Group findByName(String name);
	
}
