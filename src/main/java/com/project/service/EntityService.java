package com.project.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.model.EntityWhitelist;
import com.project.model.repo.EntityWhitelistRepo;

/**
 * Service in which provides management and selection of controlled vocabulary from 
 * any class managed by the entity manager. Allows for enable/disabling the ability to 
 * select controlled vocabulary from any given entity using a whitelist. The controlled vocabulary
 * is returned as a unique list of the datatype of the entity property.
 * 
 */
@Service
public class EntityService {

	private final static String PROJECT_PACKAGE = "com.project.";    
	private final static String MODEL_PACKAGE = "com.project.model.";
	
    // default whitelist
    private static final EntityWhitelist[] defaultWhitelistedCV = new EntityWhitelist[] {
            new EntityWhitelist("Community", Arrays.asList(new String[] {"id", "name"})),
            new EntityWhitelist("Collection", Arrays.asList(new String[] {"name"})),
            new EntityWhitelist("Group", Arrays.asList(new String[] {"name", "type"}))
    };

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private EntityWhitelistRepo entityWhitelistRepo;
    
    // cached entity names
    private List<String> entityNames = null;
    
    // whitelist, which entities properties are enabled to be selected as a controlled vocabulary
    private Map<String, List<String>> whitelist;
    
    public EntityService() { 
    	whitelist = new HashMap<String, List<String>>();
    }
    
    public void initializeWhitelist() {
    	if(entityWhitelistRepo.count() > 0) {
            entityWhitelistRepo.findAll().forEach(entityCVWhitelist -> {
                whitelist.put(entityCVWhitelist.getEntityName(), entityCVWhitelist.getPropertyNames());
            });
        }
    	else {
    		for(EntityWhitelist ecvw : defaultWhitelistedCV) {
                whitelist.put(ecvw.getEntityName(), ecvw.getPropertyNames());
                entityWhitelistRepo.save(new EntityWhitelist(ecvw.getEntityName(), ecvw.getPropertyNames()));
            }
            System.out.println("\n\nDEFAULT WHITELIST:\n" + whitelist + "\n\n");
    	}
    }
    
    /**
     * Method to add all properties of an entity to be selectable as controlled vocabulary.
     * Validates that the entityName is an actual entity. Puts the entity with all its properties
     * in the whitelist. Persists the entity and properties in the EntityCVWhitelistRepo.
     * 
     * @param entityName
     *          String which matches the class name of an entity in which whitelist
     * @throws ClassNotFoundException
     *          thrown when an entityName does not match a class, never thrown
     */
    public void addEntityToWhitelist(String entityName) throws ClassNotFoundException {
        if(entityNames.contains(entityName)) {
            if(whitelist.get(entityName) == null) {                
                List<String> propertyNames = getPropertyNames(entityName);                
                whitelist.put(entityName, propertyNames);                
                if(entityWhitelistRepo.findByEntityName(entityName) != null) {
                    entityWhitelistRepo.save(new EntityWhitelist(entityName, propertyNames));
                }
            }
        }
        else {
            System.out.println("Entity " + entityName + " is not an available entity!\n");
        }
    }
    
    /**
     * Method to remove an entity and all its properties from being able to be selected as a
     * controlled vocabulary. Removes entity and properties from the whitelist and the persistance
     * in the EntityCVWhitelistRepo.
     * 
     * @param entityName
     *          String which matches the class name of an entity in which to remove
     */
    public void removeEntityFromWhitelist(String entityName) {
        whitelist.remove(entityName);
        entityWhitelistRepo.deleteByEntityName(entityName);
    }
    
    /**
     * Method to add a property of an entity to be selectable as controlled vocabulary.
     * Validates that the entityName is an actual entity. Validates the propertyName 
     * from the given properties of an entity from the entity manager. Creates an
     * EntityCVWhitelist if none exist for entityName. Otherwise adds property to
     * EntityCVWhitelist and saves to the EntityCVWhitelistRepo. Updates whitelist.
     * 
     * @param entityName
     *          String which matches the class name of an entity in which to add one of its properties
     * @param propertyName
     *          String which matches a property of an entity in which to whitelist
     * @throws ClassNotFoundException
     *          thrown when an entityType does not match a class, never thrown
     */
    public void addEntityPropertyToWhitelist(String entityName, String propertyName) throws ClassNotFoundException {
        List<String> propertyNames = new ArrayList<String>();        
        if(whitelist.get(entityName) == null) {
            if(getPropertyNames(entityName).contains(propertyName)) {
                propertyNames.add(propertyName);
            }
            else {
                System.out.println("Property " + propertyName + " is not an available property on entity " + entityName + "!\n");
                return;
            }
            whitelist.put(entityName, propertyNames);
            
            EntityWhitelist entityCVWhitelist;
            if((entityCVWhitelist = entityWhitelistRepo.findByEntityName(entityName)) == null) {
                entityWhitelistRepo.save(new EntityWhitelist(entityName, propertyNames));
            }
            else {
                entityCVWhitelist.addPropertyName(propertyName);
                entityWhitelistRepo.save(entityCVWhitelist);
            }
        }
    }
    
    /**
     * Method to remove a property of an entity from being selectable as controlled vocabulary.
     * Removes property from EntityCVWhitelist and updates whitelist.
     * 
     * @param entityName
     *          String which matches the class name of an entity in which to remove one of its properties
     * @param propertyName
     *          String which matches a property of an entity in which to remove from whitelist
     */
    public void removeEntityPropertyFromWhitelist(String entityName, String propertyName) {
        List<String> propertyNames = whitelist.get(entityName);
        if(propertyNames != null) {
            propertyNames.remove(propertyName);
            // may not be needed! pointer?
            whitelist.put(entityName, propertyNames);
            
            EntityWhitelist entityCVWhitelist;
            if((entityCVWhitelist = entityWhitelistRepo.findByEntityName(entityName)) != null) {
                entityCVWhitelist.getPropertyNames().remove(propertyName);
                entityWhitelistRepo.save(entityCVWhitelist);
            }
        }
        else {
            System.out.println("Entity " + entityName + " is not an available entity!\n");
        }
    }
        
    /**
     * Method to retrieve a controlled vocabulary from a property of an entity. The entity must be
     * managed by the entity manager and both the entity and property must be whitelisted.
     * 
     * @param entity
     *          Class<?> generic class of the entity in which to get a controlled vocabulary of one of its property
     * @param filters
     * 			Map<String, Map<String, String>> in which holds filters
     * @param properties
     *          String which matches a property of the entity in which to get a controlled vocabulary of
     * @return List<Object>
     *          return generic list of objects that is castable to the properties datatype
     */
    @SuppressWarnings("unchecked")
	public synchronized List<Object> getControlledVocabulary(Class<?> entity, Map<String, Map<String, String>> filters, String ... properties) {
        
        Metamodel meta = entityManager.getMetamodel();
        EntityType<?> entityType = meta.entity(entity);
        
        String entityName = entityType.getName();
        
        List<String> whitelisted = whitelist.get(entityName);
       
        if(whitelisted != null) {
        	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> query = builder.createQuery();
            Root<?> root = query.from(entity);            
            List<Selection<?>> select = new ArrayList<Selection<?>>();
            for(String property : properties) {
                if(!whitelisted.contains(property)) {
                	System.out.println("Entity " + entityName + " with property " + property + " not allowed to be a controlled vocabulary!\n");
                    return new ArrayList<Object>();
                }
                Path<Object> path = root.get(property);
                
                Map<String, String> map = filters.get(property);
               
                if(map != null) {
                	
                	if(map.get("package") != null) {
                		try {
                			Class<?> clazz = Class.forName(PROJECT_PACKAGE + map.get("package") + "." + map.get("class"));
                			
                			if(clazz.isEnum()) {
								@SuppressWarnings("rawtypes")
								final Class<? extends Enum> enumType = (Class<? extends Enum>) clazz;
								query.where(builder.equal(path, Enum.valueOf(enumType, map.get("value"))));	
                			}
                			else {
                				query.where(builder.equal(path, clazz.cast(map.get("value"))));
                			}
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
                		
                	}
                	else {
                		
                		if(map.get("value") != null) {
                		
	                		try {
	                			Class<?> clazz = Class.forName(map.get("class"));
								try {
									query.where(builder.equal(path, clazz.getConstructor(new Class[] {String.class}).newInstance(map.get("value"))));
								} catch (InstantiationException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
								} catch (SecurityException e) {
									e.printStackTrace();
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
	                		
                		}
                	}
                }
               
                select.add(path);                
            }
            query.multiselect(select).distinct(true);
           
            return entityManager.createQuery(query).getResultList();
        }
        
        System.out.println("Entity " + entityName + " with property " + properties + " not allowed to be a controlled vocabulary!\n");

        // return empty array list
        return new ArrayList<Object>();
    }
    
    /**
     * Convenience method for the above method to retrieve the controlled vocabulary of a property of an entity.
     * 
     * @param entityName
     *          String which matches the class name of an entity in which to retrieve a controlled vocabulary of one of its proprties 
     * @param properties
     *          String which matches a property of the entity in which to get a controlled vocabulary of
     * @param filters
     * 			Map<String, Map<String, String>> in which holds filters
     * @return List<Object>
     *          generic list of objects that is castable to the properties datatype
     *          
     * @throws ClassNotFoundException
     *          thrown when an entityType does not match a class
     */
    public List<?> getControlledVocabulary(String entityName, Map<String, Map<String, String>> filters, String ... properties) throws ClassNotFoundException {
        return getControlledVocabulary(Class.forName(MODEL_PACKAGE + entityName), filters, properties);
    }

    /**
     * Method in which either returns the cached entity names or retrieves the entity names from the 
     * entity manager.
     *
     * @return List<String>
     *          list of all entity names managed
     */
    public List<String> getEntityNames() {
        if(this.entityNames != null) return this.entityNames;
        List<String> entityNames = new ArrayList<String>();        
        entityManager.getMetamodel().getEntities().parallelStream().forEach(entity -> {
            entityNames.add(entity.getName()); 
        });
        this.entityNames = entityNames;
        return entityNames;
    }
    
    /**
     * Method in which returns all entities managed and all their properties.
     * 
     * @return Map<String, List<String>>
     *          entity names as key and list of entities properties as values
     */
    public Map<String, List<String>> getAllEntityPropertyNames() {        
        Map<String, List<String>> propertyMap = new HashMap<String, List<String>>();               
        entityManager.getMetamodel().getEntities().parallelStream().forEach(entity -> {
            List<String> propertyNames = new ArrayList<String>();
            entity.getAttributes().forEach(attribute -> {
                propertyNames.add(attribute.getName());
            });
            propertyMap.put(entity.getName(), propertyNames);
        });
        return propertyMap;
    }

    /**
     * Method to get the property names of an entity.
     * 
     * @param entity
     *          Class<?> generic class of the entity in which to get a list its property names
     * @return List<String>
     *          list of entities property names
     */
    public List<String> getPropertyNames(Class<?> entity) {
        List<String> propertyNames = new ArrayList<String>();
        for(Field field : entity.getDeclaredFields()) {
            propertyNames.add(field.getName());
        }
        return propertyNames;
    }
    
    /**
     * Convinience method for the above method to return property names of an entity
     * 
     * @param entityName
     *          String which matches the class name of an entity in which to retrieve its property names
     * @return List<String
     *          list of entities property names   
     * @throws ClassNotFoundException
     *          thrown when an entityType does not match a class
     */
    public List<String> getPropertyNames(String entityName) throws ClassNotFoundException {
        return getPropertyNames(Class.forName(MODEL_PACKAGE + entityName));
    }
    
    /**
     * Method to get the current whitelist. A map of the enabled properties of entities that can 
     * be selected a controlled vocabulary.
     * 
     * @return Map<String, List<String>>
     *          entity names as key and list of entities properties as values
     */
    public Map<String, List<String>> getWhitelist() {
        return whitelist;
    }
    
}

