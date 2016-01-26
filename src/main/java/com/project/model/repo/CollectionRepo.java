package com.project.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Collection;
import com.project.model.Community;

public interface CollectionRepo extends JpaRepository<Collection, Long> {

	public Collection findByNameAndCommunity(String name, Community community);
		
}
