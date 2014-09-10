/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.storage.dao;

//import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.util.ModelTrace;
import se.pedcat.framework.common.util.FrameworkMessage;
import se.pedcat.framework.common.util.Trace;



/**
 * A factory for creating GenericCarrier objects.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @param <QueryType> the generic type
 * @param <DAOType> the generic type
 */
public class GenericCarrierFactory <KeyType extends Key,Type extends Carrier,QueryType extends ModelQuery,DAOType extends FrameworkDAO<KeyType,Type>> extends CarrierFactory{

	/**
	 * The Class Connection.
	 */
	private class Connection
	{
		
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	private DAOType dao = null;
	
	/** The refresh types. */
	private boolean refreshTypes;
	
	/** The sync with cache. */
	private boolean syncWithCache;
	
	
	/**
	 * Instantiates a new generic carrier factory.
	 *
	 * @param keyClass the key class
	 * @param carrierClass the carrier class
	 * @param daoClass the dao class
	 * @param queryClass the query class
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public GenericCarrierFactory(Class<KeyType> keyClass,
			Class<Type> carrierClass, Class<DAOType> daoClass,
			Class<QueryType> queryClass) throws InstantiationException, IllegalAccessException
	{
		dao = (DAOType)this.getDAO(daoClass,null);
		if (dao==null)
		{
			registerDAO(keyClass,carrierClass,queryClass,daoClass);
			dao = (DAOType)this.getDAO(daoClass,null);
		}
		dao.flushCache();
	}
	
	
	/**
	 * Gets the dAO.
	 *
	 * @return the dAO
	 */
	public DAOType getDAO()
	{
		return this.dao;
	}
	
	/**
	 * Creates a new domain value object.
	 *
	 * @param carrier the carrier
	 * @return a new object
	 * @roseuid 3AD5325B0048
	 */
    public Type create(Type carrier) 
    {
        return create(carrier,null);
    }
    
    /**
     * Creates a new domain value object.
     *
     * @param carrier the carrier
     * @param connection the connection
     * @return a new object
     * @roseuid 3AD5325B0048
     */
    public Type create(Type carrier,Connection connection) 
    {
        Trace trace = startTrace("create",isVerbose());
        trace.doAssert(carrier!=null,"Cannot create null carrier ",FrameworkMessage.CAN_NOT_CREATE_NULL_CARRIER);
        
        
        List<Type> carriers = new ArrayList<Type>();
        carriers.add(carrier);
        
        carriers = create(carriers,connection);
        carrier = carriers!=null& carriers.size()>0? carriers.get(0):null;
        return carrier;
    }
    
    
    /**
     * Creates several domain value objects.
     *
     * @param carriers the carriers
     * @return the new objects
     * @roseuid 3AD5325B004A
     */
    public  List<Type> create(List<Type> carriers) 
    {
        Trace trace = startTrace("create",isVerbose());
        trace.doAssert(carriers!=null,"Cannot create null carriers ",FrameworkMessage.CAN_NOT_CREATE_NULL_CARRIER);
        if (carriers.size()>0)
        {
            carriers = create(carriers,null);
        }
        else
        {
        	trace.logWarning("Carriers is empty");
        }
        return carriers;
    }
    
    
    /**
     * Creates several domain value objects.
     *
     * @param carriers the carriers
     * @param connection the connection
     * @return the new objects
     * @roseuid 3AD5325B004A
     */
    public List<Type> create(List<Type> carriers,Connection connection) 
    {
        ModelTrace trace = ModelTrace.startTrace(logger,logger.getClass(),"create(Type[],Connection",isVerbose());
        trace.doAssert(carriers!=null,"Cannot create null carrier ",FrameworkMessage.CAN_NOT_CREATE_NULL_CARRIER);
        
        try
        {
            if (carriers.size()>0)
            {
                carriers =  dao.create(carriers);
                addToTransactionCache((Carrier[])carriers.toArray(new Carrier[0]),dao);
                flushQueryCache(carriers.get(0).getClass());
            }
            else
            {
                
            }
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not create carriers",FrameworkMessage.TODO,e,(Carrier[])carriers.toArray(new Carrier[0]));
        }
        return carriers;
    }
    
    /**
     * Add to  cache for inserted and/or updated carriers.
     *
     * @param carriers the carriers
     * @param dao the dao
     */
    public void addToTransactionCache(List<Carrier> carriers, FrameworkDAO<KeyType,Type> dao)
    {
        if (dao.isCaching())
        {
            Map map = getTransactionCache(true);
            for(int index=0;index<carriers.size();index++)
            {
                map.put(carriers.get(index).getKey(),carriers.get(index));
            }
        }
        if (dao.isQueryCaching() && carriers!=null && carriers.size()>0)
        {
            setUpdating(carriers.get(0).getClass());
        }
    }

	/**
	 * Updates a new domain value object.
	 *
	 * @param carrier the carrier
	 * @return a updated object
	 * @roseuid 3AD5325B0053
	 */
    public Type update(Type carrier) 
    {
        Trace trace = startTrace("update",false);
        trace.doAssert(carrier!=null,"Cannot update null carriers ",FrameworkMessage.CANNOT_UPDATE_NULL_CARRIER);
        
        List<Type> carriers = new ArrayList<Type>();
        carriers.add(carrier);
        carriers =  update(carriers,null);
        carrier = carriers.get(0);
        
        return carrier;
        
    }
    
    /**
     * Updates a several domain value objects.
     *
     * @param carriers the carriers
     * @return the updated objects
     * @roseuid 3AD5325B005D
     */
    public List<Type>  update(List<Type> carriers) 
    {
        return update(carriers,null);
    }
    
    /**
     * Updates a several domain value objects.
     *
     * @param carriers the carriers
     * @param connection the connection
     * @return the updated objects
     * @roseuid 3AD5325B005D
     */
    public List<Type>  update(List<Type> carriers,Connection connection) 
    {
        ModelTrace trace = ModelTrace.startTrace(logger,(Object)null,CarrierFactory.class,"update(Type[],Connection",false, false);
        try
        {
            trace.doAssert(carriers!=null,"Cannot update null carriers ",FrameworkMessage.CANNOT_UPDATE_NULL_CARRIER);
            
            if (carriers.size()>0)
            {
                carriers =  dao.update(carriers);
                addToTransactionCache((Carrier[])carriers.toArray(new Carrier[0]),dao);
                flushQueryCache(carriers.get(0).getClass());
            }
            
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could NOT update carriers",FrameworkMessage.TODO,e,(Carrier[])carriers.toArray(new Carrier[0]));
        }
        return carriers;
    }
    
    /**
     * Update.
     *
     * @param carrier the carrier
     * @param connection the connection
     * @return the type
     */
    public  Type update(Type carrier,Connection connection) 
    {
        Trace trace = startTrace("update",false);
        trace.doAssert(carrier!=null,"Cannot update null carriers ",FrameworkMessage.CANNOT_UPDATE_NULL_CARRIER);
        
        
        List<Type> carriers = new ArrayList<Type>();
        carriers.add(carrier);
        carriers =  update(carriers,connection);
        carrier = carriers.get(0);
        
        return carrier;
    }
    
    /**
     * Deletes a  domain value object.
     *
     * @param key the key
     * @roseuid 3AD5325B0066
     */
    public  void delete(KeyType key) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",true);
        trace.doAssert(key!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        
        List<KeyType> keyList = new ArrayList<KeyType>();
        keyList.add(key);
        delete(keyList,null);
        
    }
    
    /**
     * Deletes a  domain value object.
     *
     * @param carrier the carrier
     * @roseuid 3AD5325B0066
     */
    public void delete(Type carrier) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(carrier!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        
        delete((KeyType)carrier.getKey());
        
    }
    
    /**
     * Deletes a  domain value object.
     *
     * @param carrier the carrier
     * @param connection to be used
     * @roseuid 3AD5325B0066
     */
    
    public  void delete(Type carrier,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(carrier!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        delete((KeyType)carrier.getKey(),connection);
    }
    
    /**
     * Deletes array with  domain value object.
     *
     * @param carriers the carriers
     */
    public void delete(Type[] carriers) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(carriers!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        
        if (carriers.length>0)
        {
            List<KeyType> keyList = new ArrayList<KeyType>();
            for(int index=0;index<carriers.length;index++)
            {
            	keyList.add((KeyType)carriers[index].getKey());
            }
            delete(keyList,null);
        }
    }
    
    /**
     * Deletes array with  domain value object.
     *
     * @param carriers the carriers
     * @param connection to be used
     */
    public  void delete(Type[] carriers,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(carriers!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        
        if (carriers.length>0)
        {
            List<KeyType> keyList = new ArrayList<KeyType>();
            for(int index=0;index<carriers.length;index++)
            {
            	keyList.add((KeyType)carriers[index].getKey());
            }
            delete(keyList,connection);
        }
    }
    
    /**
     * Deletes array with  domain value object.
     *
     * @param keys the keys
     */
    public void delete(KeyType[] keys) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(keys!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        
        if (keys.length>0)
        {
            List<KeyType> keyList = new ArrayList<KeyType>();
            keyList.addAll(Arrays.asList(keys));
        	
        	delete(keyList,null);
            
        }
    }
    
    /**
     * Deletes a  domain value object.
     *
     * @param key the key
     * @param connection the connection
     */
    public void delete(KeyType key,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",false);
        trace.doAssert(key!=null,"Cannot delete null key ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        List<KeyType> keyList = new ArrayList<KeyType>();
        keyList.add(key);
        
        delete(keyList,connection);
        
    }
    
    /**
     * Delete.
     *
     * @param keyList the key list
     * @param connection the connection
     */
    public  void delete(List<KeyType> keyList,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"delete",true);
        trace.doAssert(keyList!=null,"Cannot delete null keys ",FrameworkMessage.CANNOT_DELETE_NULL_KEY);
        try
        {
            if (keyList.size()>0)
            {
                dao.delete(keyList);
                addToTransactionDeleteCache(keyList.toArray(new Key[0]),dao);
                //flushQueryCache((Class) keyCarrierMap.get(keys[0].getClass().getName()));
            }
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not delete",FrameworkMessage.TODO,e);
        }
    }
    
    
    /**
     * Find by query count.
     *
     * @param query the query
     * @return the integer
     */
    public Integer findByQueryCount(QueryType query)
    {
    	ModelTrace trace = ModelTrace.startTrace(logger,CarrierFactory.class,"findByQuery("+query.getClass().getName(),isVerbose());
        
        trace.doAssert(query!=null,"Can not find by NULL query",FrameworkMessage.CANNOT_FIND_BY_NULL_QUERY);
        Integer carriers = null;
        try
        {
            //trace.dumpProperties(query);
            boolean isUpdating = isUpdating(query);
            if (!query.isRoundtrip() && dao.isQueryCaching() && !isUpdating)
            {
                carriers =  getCachedResultCount(dao,query);
                if (carriers!=null)
                {
                    trace.logMessage("Getting cached carriers from carrier cache " + carriers);
                    cachedQueries++;
                }
            }
            if (carriers==null)
            {
                trace.logMessage("Have to query database...");
                carriers =  dao.countByQuery(query);
                newQueries++;
                
                if (!query.isRoundtrip() && dao.isQueryCaching())
                {
                    trace.logMessage("Caching result...");
                    setCachedResultCount(dao,query);
                    trace.logMessage("Stored in carrier query cache...");
                }
            }
            
            return carriers;
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could no query for carriers",FrameworkMessage.TODO,e,query);
        }
        finally
        {
            trace.end();
        }
        return carriers;
    }
    
    

	/**
	 * Parses a query and returns matching objects.
	 *
	 * @param query the query
	 * @return array with objects
	 * @roseuid 3AD5325B0070
	 */
    public Type[] findByQuery(QueryType query) 
    {
        ModelTrace trace = ModelTrace.startTrace(logger,CarrierFactory.class,"findByQuery("+query.getClass().getName(),isVerbose());
        
        trace.doAssert(query!=null,"Can not find by NULL query",FrameworkMessage.CANNOT_FIND_BY_NULL_QUERY);
        Type[] carriers = null;
        try
        {
            //trace.dumpProperties(query);
            boolean isUpdating = isUpdating(query);
            if (!query.isRoundtrip() && dao.isQueryCaching() && !isUpdating)
            {
                carriers = (Type[]) getCachedResult(dao,query);
                if (carriers!=null)
                {
                    trace.logMessage("Getting cached carriers from carrier cache " + carriers.length + " " + query.getClass().getName());
                    if (refreshTypes)
                    {
                    	carriers = dao.refreshTypes(carriers);
                    }
                    trace.logMessage("Refreshed carriers");
                    cachedQueries++;
                    cachedObjects += carriers.length;
                }
            }
            if (carriers==null)
            {
                trace.logMessage("Have to query database...");
                carriers =  dao.findByQuery(query);
                newQueries++;
                if (syncWithCache && !query.isRoundtrip() && dao.isCaching() && carriers!=null && carriers.length>0)
                {
                    carriers = dao.syncWithCache(carriers);
                    trace.logMessage("Stored in carrier cache..." + carriers.length);
                }
                if (!query.isRoundtrip() && dao.isQueryCaching())
                {
                    trace.logMessage("Caching result...");
                    setCachedResult(dao,query,carriers);
                    trace.logMessage("Stored in carrier query cache...");
                }
            }
            if (carriers!=null)
            {
                returnedObjects += carriers.length;
            }
            return carriers;
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could no query for carriers",FrameworkMessage.TODO,e,query);
        }
        finally
        {
            trace.end();
        }
        return carriers;
    }
    
    /**
     * Find all.
     *
     * @return the type[]
     */
    public  Type[] findAll() {
		// TODO Auto-generated method stub
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"findAll",isVerbose());
        Type[] carriers = null;
        try
        {
            carriers = dao.findAll();
            
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not query for carriers",FrameworkMessage.TODO,e);
        }
        finally
        {
            trace.end();
        }
        return carriers; 
    }
    
    /**
     * Ensure.
     *
     * @param carrier the carrier
     * @return the type
     */
    public Type ensure(Type carrier)
    {
        List<Type> carrierList = new ArrayList<Type>();
        carrierList.add(carrier);
        carrierList = ensure(carrierList);
        if (!carrierList.isEmpty())
        {
                return carrierList.get(0);
        }
        else
        {
                return null;
        }
    }
	
    /**
     * Ensure.
     *
     * @param carriers the carriers
     * @return the type[]
     */
    public Type[] ensure(Type[] carriers)
	{
		
    	Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"ensure",isVerbose());
      
        try
        {
            trace.logMessage("Before 2 ");
            
            carriers = dao.ensure(carriers);
            trace.logMessage("After 3");
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not query for carriers",FrameworkMessage.TODO,e);
        }
        finally
        {
            trace.end();
        }
        return carriers;
	
	}
	
    
	/**
	 * Ensure.
	 *
	 * @param carrierList the carrier list
	 * @return the list
	 */
	public List<Type> ensure(List<Type> carrierList)
	{
		
		Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"ensure",isVerbose());
      
        try
        {
            
            trace.logMessage("Before 2 ");
            carrierList = dao.ensure(carrierList);
            trace.logMessage("After 3");
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not query for carriers",FrameworkMessage.TODO,e);
        }
        finally
        {
            trace.end();
        }
        return carrierList;
	
	}
        
        /**
         * Finds object by primary key
         * Caller is responsible for connection handling.
         *
         * @param key key object
         * @return Carrier found object
         * @roseuid 3AD5325B0072
         */
    public  Type findByPrimaryKey(KeyType key) 
    {
        return (Type) CarrierFactory.findByPrimaryKey(key,null);
    }


		/**
		 * Update statistics.
		 */
		public void updateStatistics() {
			// TODO Auto-generated method stub
			dao.updateStatistics();
		}
	
	/**
	 * Find keys by query.
	 *
	 * @param q the q
	 * @return the object[]
	 */
	public Object[] findKeysByQuery(QueryType q)
	{
		return dao.findKeysByQuery(q);
	}
}
