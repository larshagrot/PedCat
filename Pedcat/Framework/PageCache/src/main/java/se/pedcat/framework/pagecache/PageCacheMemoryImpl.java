/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;


import java.util.List;
import java.util.TreeMap;




/**
 * The Class PageCacheMemoryImpl.
 *
 * @param <KeyType> the generic type
 * @param <RowType> the generic type
 */
public class PageCacheMemoryImpl<KeyType,RowType> extends PageCacheDefaultImpl<KeyType,RowType> {

	/** The rader. */
	private KeyType[] rader;
	
	/** The sid map. */
	private TreeMap<Integer,List<KeyType>> sidMap = new TreeMap<Integer,List<KeyType>>();
	
	/**
	 * Instantiates a new page cache memory impl.
	 *
	 * @param rader the rader
	 */
	public PageCacheMemoryImpl(KeyType[] rader) {
		super();
		this.rader = rader;
		paginate();
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#getRader()
	 */
	@Override
	public KeyType[] getRader() {
		// TODO Auto-generated method stub
		return rader;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getCurrentPageRader()
	 */
	@Override
	public List<KeyType> getCurrentPageRader() {
		return sidMap.get(super.getCurrentPage());
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#addPage(java.lang.Integer, java.util.List)
	 */
	@Override
	public void addPage(Integer page, List<KeyType> lista) {
		
		this.sidMap.put(page, lista);
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#clearPagination()
	 */
	@Override
	public void clearPagination() {
		this.sidMap.clear();
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#getAntalSidor()
	 */
	@Override
	public int getAntalSidor() {
		// TODO Auto-generated method stub
		return this.sidMap.size();
	}

    /* (non-Javadoc)
     * @see se.pedcat.framework.pagecache.PageCache#clear()
     */
    @Override
    public void clear() {
        this.rader = null;
        this.sidMap.clear();
        
    }

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getPageRader(int)
	 */
	@Override
	public List<KeyType> getPageRader(int page) {
		
		return this.sidMap.get(page);
	}
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#size()
	 */
	public int size()
	{
		return this.rader.length;
	}
 
}