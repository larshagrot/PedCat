/*
 *
 * Created on den 30 januari 2001, 21:20
 */

package se.pedcat.framework.storage.dao;

import java.util.List;
import java.util.Map;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;





/**
 * General interface for a DAO pattern implementation
 * Wrapping datasources like Jdbc connections.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author  lars
 * <BR>
 * <!--
 * $Revision: 14 $
 * $Log: /prodex-utv/u1.2.2/framework/DAO.java $
 * 
 * 14    02-04-22 14:56 Larh
 * 
 * 
 * -->
 */
public interface FrameworkDAO <KeyType extends Key,Type extends Carrier>  
{
    
    /**
     * Finds carrier objects by parsing a query object
     * The returned array is a homogenous array with carriers of the corresponding type to of the query object.
     *
     * @param query the query
     * @return  array with carriers
     */
    public Type[] findByQuery(ModelQuery query);
    
    /**
     * Counts carrier objects by parsing a query object
     * The returned value is.
     *
     * @param query the query
     * @return  number of carriers matching the query
     */
    public int countByQuery(ModelQuery query);
    
    /**
     * Sets a excplicit connection to be used by this DAO.
     *
     * @param connection the new connection
     */
    public void setConnection(java.sql.Connection connection);
    
    
    /**
     * Creates a carrier,.
     *
     * @param carrier to be created
     * @return carrier the created carrier (has a new object id)
     */
    public Type create(Type carrier);
    
    /**
     * Creates a number of carriers.
     *
     * @param carrier the carrier
     * @return carriers the created carriers (has a new object id)
     */
    public List<Type> create(List<Type> carrier);
    
    
    /**
     * Updates a number of carriers.
     *
     * @param carrier the carrier
     * @return carriers the updated carriers
     */
    
    public List<Type> update(List<Type> carrier);
    
    
    /**
     * Updates a carrier,.
     *
     * @param carrier to be updated
     * @return carrier the updated carrier (has a new object id)
     */
    public Type update(Type carrier);
    
    /**
     * Deletes a carrier,.
     *
     * @param key for carrier to be deleted
     */
    public void delete(KeyType key);
    
    
    /**
     * Deletes a number of carriers.
     *
     * @param keys for carriers to be deleted
     */
    public void delete(List<KeyType> keys);
    
    /**
     * Deletes a carrier,.
     *
     * @param carrier to be deleted
     */
    public void delete(Type carrier);
    
    /**
     * Deletes a number of carriers.
     *
     * @param carrier the carrier
     */
    public void delete(Type[] carrier);
    
    /**
     * Finds a carrier by primary key.
     *
     * @param key for carrier to be found
     * @return the type
     * @carrier found carrier
     */
    public Type findByPrimaryKey(KeyType key);
    
    /**
     * Flushes cache for this DAO.
     */
    public void flushCache();
    
    
    /**
     * Store in cache.
     *
     * @param carrier the carrier
     * @return the type
     */
    public Type   storeInCache(Type carrier);
    
    /**
     * Store in cache.
     *
     * @param carrier the carrier
     * @return the type[]
     */
    public Type[] storeInCache(Type[] carrier);
    
    /**
     * Sync with cache.
     *
     * @param carrier the carrier
     * @return the type[]
     */
    public Type[] syncWithCache(Type[] carrier);
    
    //public Type[] findByPrimaryKeys(KeyType[] key,Class<Type> carrierClass);
    
    /**
     * Removes the from cache.
     *
     * @param key the key
     */
    public void removeFromCache(KeyType key);
    
    /**
     * Checks if is caching.
     *
     * @return true, if is caching
     */
    public boolean isCaching();
    
    /**
     * Checks if is query caching.
     *
     * @return true, if is query caching
     */
    public boolean isQueryCaching();
    
    /**
     * Gets the from cache.
     *
     * @param carrierKey the carrier key
     * @param clone the clone
     * @return the from cache
     */
    public Type getFromCache(KeyType carrierKey,boolean clone);
    
    /**
     * Gets the cache.
     *
     * @return the cache
     */
    public Map getCache();
    
    /**
     * Refresh types.
     *
     * @param carriers the carriers
     * @return the type[]
     */
    public Type[] refreshTypes(Type[] carriers);
    
    /**
     * Execute command.
     *
     * @param sqlCommand the sql command
     */
    public void executeCommand(SQLCommand sqlCommand);

	/**
	 * Find all.
	 *
	 * @return the type[]
	 */
	public Type[] findAll();

	/**
	 * Ensure.
	 *
	 * @param carriers the carriers
	 * @return the type[]
	 */
	public Type[] ensure(Type[] carriers);
    
    /**
     * Ensure.
     *
     * @param carriers the carriers
     * @return the list
     */
    public List<Type> ensure(List<Type> carriers);
	
	/**
	 * Ensure.
	 *
	 * @param carrier the carrier
	 * @return the type
	 */
	public Type ensure(Type carrier);
	
	/**
	 * Update statistics.
	 */
	public void updateStatistics();
	
	/**
	 * Find keys by query.
	 *
	 * @param <KeyType2> the generic type
	 * @param q the q
	 * @return the key type2[]
	 */
	public <KeyType2> KeyType2[] findKeysByQuery(ModelQuery q);

	/**
	 * Gets the connection pool name.
	 *
	 * @return the connection pool name
	 */
	public String  getConnectionPoolName();
	
    
}
