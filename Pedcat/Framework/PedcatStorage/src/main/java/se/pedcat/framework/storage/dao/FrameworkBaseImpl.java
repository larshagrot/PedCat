/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.pedcat.framework.storage.dao;

import java.util.Map;

import se.pedcat.framework.common.model.util.MapCache;



/**
 * The Class FrameworkBaseImpl.
 *
 * @author laha
 */
class FrameworkBaseImpl implements java.io.Serializable{

    
    

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The created objects. */
	protected static int createdObjects  = 0;
    
    /** The updated objects. */
    protected static int updatedObjects  = 0;
    
    /** The deleted objects. */
    protected static int deletedObjects  = 0;
    
    /** The queried objects. */
    protected static int queriedObjects  = 0;
    
    /** The found in cache. */
    protected static int foundInCache  = 0;
    
    /** The found in db. */
    protected static int foundInDB  = 0;
    
    /** The counter. */
    protected static int counter=0;
    
    /** The MA x_ create. */
    public static int MAX_CREATE = 1000;
    
    /** The MA x_ delete. */
    public static int MAX_DELETE = 1000;
    
    /** The MA x_ update. */
    public static int MAX_UPDATE = 1000;
    
    /** The MA x_ read. */
    public static int MAX_READ   = 1000;
    
    /** The read map cache. */
    protected static MapCache readMapCache    = new MapCache();
    
    /** The cache map map map. */
    protected static Map<String,Map<Class<?>,Map<?,?>>> cacheMapMapMap        = new java.util.HashMap<String,Map<Class<?>,Map<?,?>>>();
    
    //private StorageConnectionPool connectionPool = null;
    
    static 
    {
        //DataLoader.ensureData();
    }
    
    
    /**
     * Instantiates a new framework base impl.
     */
    public FrameworkBaseImpl()
    {
            counter++;
    }


	/**
	 * Inits the connection pool.
	 *
	 * @return the storage connection pool
	 */
	protected StorageConnectionPool initConnectionPool() {
		// TODO Auto-generated method stub
		return isHasConnectionPool()?StorageConnectionPool.getInstance(this.getConnectionPoolName()):null;
	}


	/**
	 * Checks if is checks for connection pool.
	 *
	 * @return true, if is checks for connection pool
	 */
	protected boolean isHasConnectionPool() {
		// TODO Auto-generated method stub
		return true;
	}


	/**
	 * Gets the connection pool name.
	 *
	 * @return the connection pool name
	 */
	public String getConnectionPoolName() {
		// TODO Auto-generated method stub
		return StorageConnectionPool.DEFAULT_POOL;
	}
	
	/**
	 * Gets the connection pool.
	 *
	 * @return the connection pool
	 */
	public StorageConnectionPool getConnectionPool()
	{
	/*	if (this.connectionPool==null)
		{
			connectionPool = initConnectionPool();
		}
		return connectionPool;*/
		return this.initConnectionPool();
	}
	
	/**
	 * Gets the scheme name.
	 *
	 * @return the scheme name
	 */
	public String getSchemeName() 
	{
		return null;
	}
	
}
