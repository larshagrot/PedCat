/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.pedcat.framework.pagecache.PageCache;
import se.pedcat.framework.pagecache.PageCacheFactory;
import se.pedcat.framework.pagecache.model.PageCacheTicket;
import se.pedcat.framework.pagecache.model.PageListinfo;


// TODO: Auto-generated Javadoc
/**
 * The Class PageCacheServiceImpl.
 */

/**
 * @author laha
 * 
 */
public class PageCacheServiceImpl implements PageCacheService {
	
	/** The map. */
	private static Map<PageCacheTicket, PageCache<?, ?>> map = new HashMap<PageCacheTicket, PageCache<?, ?>>();
	
	/** The last access. */
	private static Map<PageCacheTicket, Long> lastAccess = new HashMap<PageCacheTicket, Long>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.pedcat.framework.pagecache.service.PageCacheService#cacheResult(RowType
	 * [])
	 */
	@Override
	public <KeyType, RowType> PageCacheTicket cacheResult(KeyType[] array) {
		PageCache<KeyType, RowType> pageCache = PageCacheFactory.getInstance()
				.createPageCache(array);
		PageCacheTicket ticket = new PageCacheTicket();
		storeCache(ticket, pageCache);
		return ticket;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.service.PageCacheService#cacheResult(KeyType[], java.util.Comparator<?>[])
	 */
	@Override
	public <KeyType, RowType> PageCacheTicket[] cacheResult(KeyType[] array,
			Comparator<?>[] comparators) {
		if (comparators.length == 0) {
			return new PageCacheTicket[] { cacheResult(array) };
		} else {
			PageCacheTicket ticket = new PageCacheTicket();
			PageCacheTicket[] tickets = new PageCacheTicket[comparators.length];
			for (int i = 0; i < comparators.length; i++) {
				Arrays.sort(array, (Comparator<KeyType>) comparators[i]);
				PageCache<KeyType, RowType> pageCache = PageCacheFactory
						.getInstance().createPageCache(array);
				pageCache.setSortorder(i);
				PageCacheTicket nticket = new PageCacheTicket();
				nticket.setValue(ticket.getValue());
				nticket.setSortorder(i);
				storeCache(nticket, pageCache);
				tickets[i] = nticket;
			}

			return tickets;
		}
	}

	/**
	 * Store cache.
	 *
	 * @param ticket the ticket
	 * @param pageCache the page cache
	 */
	private void storeCache(PageCacheTicket ticket, PageCache<?, ?> pageCache) {
		map.put(ticket, pageCache);
		lastAccess.put(ticket, System.currentTimeMillis());
	}

	/**
	 * Gets the cache.
	 *
	 * @param <KeyType> the generic type
	 * @param <RowType> the generic type
	 * @param ticket the ticket
	 * @return the cache
	 */
	private <KeyType, RowType> PageCache<?, ?> getCache(PageCacheTicket ticket) {
		if (map.containsKey(ticket)) 
		{
			lastAccess.put(ticket, System.currentTimeMillis());
			return map.get(ticket);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.pedcat.framework.pagecache.service.PageCacheService#getPage(se.signifikant
	 * .oef.pagecache.model.PageCacheTicket, int)
	 */
	@Override
	public <KeyType, RowType> PageListinfo<KeyType, RowType> getPageListinfo(
			PageCacheTicket ticket, int page, RowType[] rows) {
		PageCache<KeyType, RowType> cache = (PageCache<KeyType, RowType>) this
				.getCache(ticket);
		if (cache != null) {
			PageListinfo<KeyType, RowType> pli = cache.getPageListinfo(page,
					rows);
			pli.setPageCacheTicket(ticket);
			return pli;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.pedcat.framework.pagecache.service.PageCacheService#isHasPageCache(
	 * se.pedcat.framework.pagecache.model.PageCacheTicket)
	 */
	@Override
	public boolean isHasPageCache(PageCacheTicket ticket) {
		return map.containsKey(ticket);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * se.pedcat.framework.pagecache.service.PageCacheService#getPage(se.signifikant
	 * .oef.pagecache.model.PageCacheTicket, int)
	 */
	@Override
	public <KeyType, RowType> List<KeyType> getPage(PageCacheTicket ticket,
			int page) {

		PageCache<KeyType, RowType> cache = (PageCache<KeyType, RowType>) this
				.getCache(ticket);
		if (cache != null) {
			return cache.getPageRader(page);
		} else {
			return null;
		}
	}
}
