package dao;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import com.microsoft.sqlserver.jdbc.SQLServerStatement;
import deploy.DeploymentConfiguration;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Generic DAO class.
 * 
 * This class holds various methods for handling data between the 
 * database and the system.
 * 
 * @author Casper Schultz
 */
public abstract class DataManager<T, PK> {
    
    protected EntityManager manager;
    protected EntityTransaction transaction;
    
    
    /**
     * The generic class we reference
     */
    Class<T> entityType;
    
    public DataManager() {
        manager = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME).createEntityManager();
        transaction = manager.getTransaction();
        
        // We need this in order to use the generics. 
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityType = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }
    
    public EntityManager getManager() {
        return this.manager;
    }
    
    public EntityTransaction getTransaction() {
        return this.transaction;
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
      
        if (entities != null || entities.size() > 0) {
            transaction.begin();
            for (T entity : entities) 
                manager.persist(entity);
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
    public T find(PK id) {
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
     * @param table      name of the table to delete contents from. 
     */
    public void deleteAll(String table) {
        // We use a specific convention for naming the tables.
        transaction.begin();
        Query q = manager.createNativeQuery("DELETE FROM " + table);
        q.executeUpdate();
        transaction.commit();
    }
}
