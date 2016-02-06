package com.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import static javax.persistence.FetchType.EAGER;

@Entity
public class EntityWhitelist  extends BaseEntity {
    
    @Column(unique = true)
    private String entityName;
    
    @ElementCollection(fetch = EAGER)
    private List<String> propertyNames;
    
    public EntityWhitelist() { }
    
    public EntityWhitelist(String entityName) {
        setEntityName(entityName);
        propertyNames = new ArrayList<String>();
    }
    
    public EntityWhitelist(String entityName, List<String> propertyNames) {
        this(entityName);
        setPropertyNames(propertyNames);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<String> getPropertyNames() {
        return propertyNames;
    }

    public void setPropertyNames(List<String> propertyNames) {
        this.propertyNames = propertyNames;
    }
    
    public void addPropertyName(String propertyName) {
        if(!propertyNames.contains(propertyName)) {
            propertyNames.add(propertyName);
        }
    }
    
    public void removePropertyName(String propertyName) {
        propertyNames.remove(propertyName);
    }
    
}

