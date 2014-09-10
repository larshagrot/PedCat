/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;

import java.util.ArrayList;
import java.util.List;

import se.pedcat.framework.pagecache.model.PageListinfo;





/**
 * The Class PageCacheDefaultImpl.
 *
 * @param <KeyType> the generic type
 * @param <RowType> the generic type
 */
public abstract  class PageCacheDefaultImpl <KeyType,RowType> implements PageCache<KeyType,RowType> 
{
	
	/** The antal rader per sida. */
	private int antalRaderPerSida = 30;
	
	/** The current page. */
	private int currentPage = 1;
	
	/** The antal sidor som visas. */
	private int antalSidorSomVisas = 9;
	
	/** The sortorder. */
	private int sortorder = 0;
	
	
	/**
	 * Gets the antal rader per sida.
	 *
	 * @return the antal rader per sida
	 */
	public int getAntalRaderPerSida() {
		return antalRaderPerSida;
	}


	/**
	 * Sets the antal rader per sida.
	 *
	 * @param antalRaderPerSida the new antal rader per sida
	 */
	public void setAntalRaderPerSida(int antalRaderPerSida) {
		this.antalRaderPerSida = antalRaderPerSida;
	}


	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getCurrentPage()
	 */
	public int getCurrentPage() {
		return currentPage;
	}


	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#setCurrentPage(int)
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	/**
	 * Gets the antal sidor som visas.
	 *
	 * @return the antal sidor som visas
	 */
	public int getAntalSidorSomVisas() {
		return antalSidorSomVisas;
	}


	/**
	 * Sets the antal sidor som visas.
	 *
	 * @param antalSidorSomVisas the new antal sidor som visas
	 */
	public void setAntalSidorSomVisas(int antalSidorSomVisas) {
		this.antalSidorSomVisas = antalSidorSomVisas;
	}


	 
	
	/**
	 * Instantiates a new page cache default impl.
	 */
	public PageCacheDefaultImpl() {
	
	}


	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getCurrentDokument()
	 */
	@Override
	public KeyType[] getCurrentDokument() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getCurrentDokument(java.lang.String)
	 */
	@Override
	public KeyType[] getCurrentDokument(String dokumentId) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#isHasVisibleDokument()
	 */
	@Override
	public boolean isHasVisibleDokument() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Paginate.
	 */
	protected void paginate()
	{
	    this.paginate(this.getRader());
	}
	
	/**
	 * Paginate.
	 *
	 * @param rader the rader
	 */
	protected void paginate(KeyType[] rader)
	{
		clearPagination();
		int sida = 0;
		List<KeyType> lista = new ArrayList<KeyType>();
		
		for(KeyType rad:rader)
		{
			lista.add(rad);
			if (lista.size() == antalRaderPerSida )
			{
				sida++;
				addPage(new Integer(sida),lista);
				lista = new ArrayList<KeyType>();
			}
		}
		if (!lista.isEmpty())
		{
			sida++;
			addPage(new Integer(sida),lista);
		}
	}

	/**
	 * Adds the page.
	 *
	 * @param integer the integer
	 * @param lista the lista
	 */
	public abstract void addPage(Integer integer, List<KeyType> lista);
	
	/**
	 * Clear pagination.
	 */
	public abstract void clearPagination();
	
	/**
	 * Gets the rader.
	 *
	 * @return the rader
	 */
	public abstract KeyType[] getRader();
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getAntalSidor()
	 */
	public abstract int getAntalSidor();
	
	   /* (non-Javadoc)
   	 * @see se.pedcat.framework.pagecache.PageCache#getPageListinfo(RowType[])
   	 */
   	@Override
	    public PageListinfo<KeyType,RowType> getPageListinfo(RowType[] type) {
	        
	       PageListinfo<KeyType,RowType> pageListinfo = new PageListinfo<KeyType,RowType>();
	       pageListinfo.setCurrentPage(this.getCurrentPage());
	       pageListinfo.setAntalRaderPerSida(antalRaderPerSida);
	       pageListinfo.setAntalSidor(this.getAntalSidor());
	       pageListinfo.setAntalRader(this.size());
	       pageListinfo.setAntalPoster(this.size());
	       
	       pageListinfo.setArray(type);
	       
	        return pageListinfo;
	    }
	

	   /* (non-Javadoc)
		 * @see se.pedcat.framework.pagecache.PageCache#getPageRader(int)
		 */
		@Override
		public List<KeyType> getPageRader(int page) {
		
			this.setCurrentPage(page);
			return this.getCurrentPageRader();
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.pagecache.PageCache#getPageListinfo(int, RowType[])
		 */
		@Override
		public PageListinfo<KeyType, RowType> getPageListinfo(int page,
				RowType[] type) {
			this.setCurrentPage(page);
			return this.getPageListinfo(type);
		}
		
		/**
		 * Sets the sortorder.
		 *
		 * @param sortorder the new sortorder
		 */
		public void setSortorder(int sortorder) {
			this.sortorder = sortorder;
			
		}

		/**
		 * Returnerar sortorder.
		 *
		 * @return the sortorder
		 */
		public int getSortorder() {
			return this.sortorder;
		}



}
