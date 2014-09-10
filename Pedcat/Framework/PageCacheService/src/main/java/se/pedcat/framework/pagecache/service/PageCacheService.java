/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache.service;

import java.util.Comparator;
import java.util.List;

import se.pedcat.framework.pagecache.model.PageCacheTicket;
import se.pedcat.framework.pagecache.model.PageListinfo;


// TODO: Auto-generated Javadoc
/**
 * The Interface PageCacheService.
 */

/**
 * @author laha
 *
 */
public interface PageCacheService {

	/**
	 * Cache result.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param array the array
	 * @return the page cache ticket
	 */
	<KeyType,RowType> PageCacheTicket cacheResult(KeyType[] array);
	
	/**
	 * Gets the page.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param ticket the ticket
	 * @param page the page
	 * @return the page
	 */
	<KeyType,RowType> List<KeyType> getPage(PageCacheTicket ticket, int page);
	
	/**
	 * Checks if is checks for page cache.
	 *
	 * @param ticket the ticket
	 * @return true, if is checks for page cache
	 */
	boolean isHasPageCache(PageCacheTicket ticket);
	
	
	/**
	 * Gets the page listinfo.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param ticket the ticket
	 * @param page the page
	 * @param rows the rows
	 * @return the page listinfo
	 */
	<KeyType, RowType> PageListinfo<KeyType, RowType> getPageListinfo(PageCacheTicket ticket,
			int page, RowType[] rows);
	
	/**
	 * Cache result.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param array the array
	 * @param comparators the comparators
	 * @return the page cache ticket[]
	 */
	<KeyType, RowType> PageCacheTicket[] cacheResult(KeyType[] array, Comparator<?>[] comparators);
	
	
}
