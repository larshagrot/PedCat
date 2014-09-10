/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;


import java.util.List;

import se.pedcat.framework.pagecache.model.PageListinfo;



/**
 * Generellt ifc för cachning av stora resultat som ska pagineras.
 *
 * @param <KeyType> the generic type
 * @param <RowType> the generic type
 * @author laha
 */
public interface PageCache <KeyType,RowType> {
	 
 	/**
 	 * Gets the current dokument.
 	 *
 	 * @return the current dokument
 	 */
 	KeyType[] getCurrentDokument();
	 
 	/**
 	 * Gets the current dokument.
 	 *
 	 * @param dokumentId the dokument id
 	 * @return the current dokument
 	 */
 	KeyType[] getCurrentDokument(String dokumentId);
	 
 	/**
 	 * Checks if is checks for visible dokument.
 	 *
 	 * @return true, if is checks for visible dokument
 	 */
 	boolean isHasVisibleDokument();
	
	 /**
 	 * Gets the current page rader.
 	 *
 	 * @return the current page rader
 	 */
 	List<KeyType> getCurrentPageRader();
	 
 	/**
 	 * Gets the page rader.
 	 *
 	 * @param page the page
 	 * @return the page rader
 	 */
 	List<KeyType> getPageRader(int page);
	 
 	/**
 	 * Sets the current page.
 	 *
 	 * @param page the new current page
 	 */
 	void setCurrentPage(int page);
	 
 	/**
 	 * Gets the antal sidor.
 	 *
 	 * @return the antal sidor
 	 */
 	int getAntalSidor();
	 
 	/**
 	 * Size.
 	 *
 	 * @return the int
 	 */
 	int size();
	 
 	/**
 	 * Gets the current page.
 	 *
 	 * @return the current page
 	 */
 	int getCurrentPage();
	 
 	/**
 	 * Clear.
 	 */
 	void clear();
	 
 	/**
 	 * Gets the page listinfo.
 	 *
 	 * @param type the type
 	 * @return the page listinfo
 	 */
 	PageListinfo<KeyType,RowType> getPageListinfo(RowType[] type); 
	 
 	/**
 	 * Gets the page listinfo.
 	 *
 	 * @param page the page
 	 * @param type the type
 	 * @return the page listinfo
 	 */
 	PageListinfo<KeyType,RowType> getPageListinfo(int page,RowType[] type);
	 
 	/**
 	 * Sets the sortorder.
 	 *
 	 * @param order the new sortorder
 	 */
 	void setSortorder(int order);
	
}
