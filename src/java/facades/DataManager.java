/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author casper
 */
public class DataManager<T, PK> {
    
    EntityManager manager;
    EntityTransaction transaction;
    
    
    /**
     * The generic class we reference
     */
    Class<T> entityType;
    
    public DataManager() {
        manager = Persistence.createEntityManagerFactory("MomondoProjektPU").createEntityManager();
        transaction = manager.getTransaction();

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    /**
     * Saves an entity to the database.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param entity    Entity to create
     * @return          The entity created
     */
    public T create(T entity) {

        transaction.begin();
        manager.persist(entity);
        transaction.commit();

        return entity;
    }
    
    /**
     * Saves a list of entities in the database.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param entities List of entities to create
     */
    public void createFromList(List<T> entities) {
        // Now save the flights
        if (entities != null || entities.size() > 0) {
            transaction.begin();
            entities.stream().forEach((entity) -> {
                manager.persist(entity);
            });
            transaction.commit();
        }
    }
    
    /**
     * Lookup an entity by entity id.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param id    Id of the given entity to lookup
     * @return      The entity if found / otherwise null
     */
    public T find(PK id){
        T entity = manager.find(entityType, id);
        
        if(entity != null){
            return manager.find(entityType, id);
        }
        
        return null;
    }

    /**
     * Update entity.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param entity        The entity to update
     * @return entity       The updated entity
     */
    public T update(T entity) {

        transaction.begin();
        manager.merge(entity);
        transaction.commit();

        return entity;
    }

    /**
     * Removes the entity.
     *
     * Removes the given entity from the database vs using detach, that would
     * only remove it from the entity manager until next flush.
     *  
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     *
     * @param id
     * @return entity
     */
    public T delete(PK id) {
        transaction.begin();
        T entity = manager.find(entityType, id);

        if (entity != null) {
            manager.remove(entity);
            transaction.commit();
            return entity;
        }

        return null;
    }
    
    /**
     * Deletes all rows in a table.
     * 
     * NOTICE!!! We use a specific way of naming
     * the tables! If this isn't done correctly, the
     * method won't work!
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param entity 
     */
    public void deleteAll(T entity) {
        // We use a specific convention for naming the tables. 
        String table = entity.getClass().getName().toUpperCase() + "S";
        Query q = manager.createNativeQuery("DELETE FROM " + table);
        q.executeUpdate();
    }
}
