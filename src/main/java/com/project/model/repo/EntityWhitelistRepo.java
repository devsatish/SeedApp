package com.project.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.EntityWhitelist;

public interface EntityWhitelistRepo extends JpaRepository<EntityWhitelist, Long> {

    EntityWhitelist findByEntityName(String entityName);
    
    Long deleteByEntityName(String entityName);
    
}
