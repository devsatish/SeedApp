package com.project.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	public User findByUsername(String username);	
	
}
