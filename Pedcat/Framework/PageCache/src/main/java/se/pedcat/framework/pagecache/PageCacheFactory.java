/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;


/**
 * A factory for creating PageCache objects.
 */
public class PageCacheFactory {

	/** The instance. */
	private static PageCacheFactory instance;
	
	/**
	 * Gets the single instance of PageCacheFactory.
	 *
	 * @return single instance of PageCacheFactory
	 */
	public static PageCacheFactory getInstance()
	{
		if (instance == null)
		{
			instance = new PageCacheFactory(); 
		}
		return instance;
	}

    /** The file cache. */
    private boolean fileCache;
	
	/**
	 * Creates a new PageCache object.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param array the array
	 * @return the page cache< key type, row type>
	 */
	public <KeyType,RowType> PageCache<KeyType,RowType> createPageCache(KeyType[] array )
	{
	    if (!fileCache)
	        return new PageCacheMemoryImpl<KeyType,RowType>(array);
	    else
	        return new PageCacheFileImpl<KeyType,RowType>(array);
	    
	}

    /**
     * Sets the file cache.
     *
     * @param b the new file cache
     */
    public void setFileCache(boolean b) {
        this.fileCache = b;
        
    }
}
