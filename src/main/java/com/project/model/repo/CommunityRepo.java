package com.project.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Community;

public interface CommunityRepo extends JpaRepository<Community, Long> {

	public Community findById(Long id);
	
	public List<Community> findByName(String name);
	
	public List<Community> findByCommunityIsNull();	
	
}
