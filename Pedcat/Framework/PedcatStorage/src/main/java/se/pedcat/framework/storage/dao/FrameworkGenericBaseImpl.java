/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.pedcat.framework.storage.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.model.util.ModelException;
import se.pedcat.framework.common.util.Trace;



/**
 * The Class FrameworkGenericBaseImpl.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author laha
 */
class FrameworkGenericBaseImpl<KeyType extends Key, Type extends Carrier> extends FrameworkBaseImpl implements java.io.Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(FrameworkDAOImpl.class);

	
	// public Carrier getFromCache(Key carrierKey)
	// {
	// return getFromCacheNEW(carrierKey);
	// }

	/**
	 * Gets the from cache.
	 *
	 * @param carrierKey the carrier key
	 * @param clone the clone
	 * @return the from cache
	 */
	public Type getFromCache(KeyType carrierKey, boolean clone) {

		return getFromCacheNEW(carrierKey);
	}

	/**
	 * Gets the from cache.
	 *
	 * @param carrierKey the carrier key
	 * @return the from cache
	 */
	public Type getFromCache(KeyType carrierKey) {
		Trace trace = Trace.startTrace1(logger,this, "getFromCache "
				+ carrierKey.getClass().getName(), isVerbose());
		Type carrier = null;
		if (this.isCaching()) {
			// First try to get it from ongoing transaction objects
			carrier = (Type) CarrierFactory
					.getFromTransactionCache((Key) carrierKey);
			if (carrier == null) {
				// Second try to get it from read objects in this call...
				//Map<KeyType, Type> readCacheMap = this.getReadCacheMap(false);
				//if (readCacheMap != null) {
					//carrier = readCacheMap.get(carrierKey);
				//}
				if (carrier == null) {
					// Third try to get it from cache
					Map<KeyType, Type> cacheMap = this.getCache();
					synchronized (cacheMap) {
						carrier = cacheMap.get(carrierKey);
					}
				} else {
					trace.logMessage("Got carrier from ReadCache");
				}
			} else {
				trace.logMessage("Got carrier from TransactionCache");
			}
		}
		return carrier;
	}

	/**
	 * Gets the from cache new.
	 *
	 * @param carrierKey the carrier key
	 * @return the from cache new
	 */
	public Type getFromCacheNEW(KeyType carrierKey) {
		Trace trace = Trace.startTrace1(logger,this, "getFromCacheNEW", isVerbose());
		Type carrier = null;
		if (this.isCaching()) {
			// Third try to get it from cache
			Map cacheMap = this.getCache();
			synchronized (cacheMap) {
				carrier = (Type) cacheMap.get(carrierKey);
			}
		}
		return carrier;
	}

	/**
	 * Gets the from cache old.
	 *
	 * @param carrierKey the carrier key
	 * @param clone the clone
	 * @return the from cache old
	 */
	public Carrier getFromCacheOLD(KeyType carrierKey, boolean clone) {
		Trace trace = Trace.startTrace1(logger,this, "getFromCache", isVerbose());
		Carrier carrier = null;
		if (this.isCaching()) {
			// First try to get it from ongoing transaction objects
			carrier = CarrierFactory.getFromTransactionCache(carrierKey);
			if (carrier == null) {
				// Second try to get it from read objects in this call...
				//Map readCacheMap = this.getReadCacheMap(false);
				//if (readCacheMap != null) {
					//carrier = (Carrier) readCacheMap.get(carrierKey);
				//}
				if (carrier == null) {
					// Third try to get it from cache
					Map cacheMap = this.getCache();
					synchronized (cacheMap) {
						carrier = (Carrier) cacheMap.get(carrierKey);
					}
					if (carrier != null && clone) {
						try {
							carrier = (Carrier) carrier.clone();
							// Add to readcache so we get this again if we
							// search for it
							//readCacheMap.put(carrier.getKey(), carrier);
							trace.logMessage("Got carrier from cache...");
						} catch (Exception e) {
							throw new ModelException("", e);
						}

					}
				} else {
					trace.logMessage("Got carrier from ReadCache");
				}
			} else {
				trace.logMessage("Got carrier from TransactionCache");
			}
		}
		return carrier;
	}

	/**
	 * Gets the from cache old.
	 *
	 * @param carrierKeys the carrier keys
	 * @return the from cache old
	 */
	public Type[] getFromCacheOLD(KeyType[] carrierKeys) {
		Type[] carriers = null;
		if (this.isCaching()) {
			Map<KeyType, Type> cacheMap = this.getCache();
			carriers = (Type[]) java.lang.reflect.Array.newInstance(null,
					carrierKeys.length);
			synchronized (cacheMap) {
				try {
					for (int index = 0; index < carriers.length; index++) {
						carriers[index] = cacheMap.get(carrierKeys[index]);
						if (carriers[index] != null) {
							carriers[index] = (Type) carriers[index].clone();
						}
					}
				} catch (Exception e) {
					throw new ModelException("", e);
				}
			}
		}
		return carriers;
	}

	/**
	 * Tells if this DAO is caching objectss.
	 *
	 * @return true, if is caching
	 */
	public boolean isCaching() {
		return true;
	}

	/**
	 * Checks if is query caching.
	 *
	 * @return true, if is query caching
	 */
	public boolean isQueryCaching() {
		return true;
	}

	/**
	 * Flushes cache for this type of carriers.
	 */
	public void flushCache() {
		if (this.isCaching()) {
			Map[] cacheMaps = this.getCaches();
			if (cacheMaps!=null)
			{
				for(Map cacheMap:cacheMaps)
				{
					synchronized (cacheMap) {
						cacheMap.clear();
					}
				}
			}
		}
	}

	/**
	 * Gets the caches.
	 *
	 * @return the caches
	 */
	private Map[] getCaches() {
		List<Map> mapList = new ArrayList<Map>();
    		for(Map map:cacheMapMapMap.values())
    		{
    			Map qmap = (Map) map.get(this.getClass());
    			if (qmap!=null)
    			{
    				mapList.add(qmap);
    			}
    		}
    	
    	return mapList.toArray(new Map[0]);	}

	/**
	 * Gets the cache.
	 *
	 * @return the cache
	 */
	public Map<KeyType, Type> getCache() {
		if (this.isCaching()) {
			Map<KeyType, Type> map = null;
			synchronized (cacheMapMapMap) {
				Map cacheMapMap = (Map) cacheMapMapMap.get(this.getConnectionPoolName());
				if (cacheMapMap==null)
				{
					cacheMapMap = new HashMap<Class<?>,Map<KeyType, Type>>();
					cacheMapMapMap.put(this.getConnectionPoolName(),cacheMapMap);
				}
				map = (Map<KeyType, Type>) cacheMapMap.get(this.getClass());
				if (map == null) {
					map = new java.util.HashMap<KeyType, Type>();
					cacheMapMap.put(this.getClass(), map);
				}
			}
			return map;
		} else {
			return null;
		}
	}

	/**
	 * Gets the read cache map.
	 *
	 * @param create the create
	 * @return the read cache map
	 */
	public Map<KeyType, Type> getReadCacheMap(boolean create) {
		return (Map<KeyType, Type>) readMapCache.getMap(create);
	}

	/**
	 * Removes a number og Carrier objects from cache.
	 *
	 * @param keys the keys
	 */
	public void removeFromCache(KeyType[] keys) {
		if (this.isCaching()) {
			Map cacheMap = this.getCache();
			synchronized (cacheMap) {
				for (int index = 0; index < keys.length; index++) {
					cacheMap.remove(keys[index]);
				}
			}

		}
	}
	
	/**
	 * Removes the from cache.
	 *
	 * @param keyList the key list
	 */
	protected void removeFromCache(List<KeyType> keyList) {
		// TODO Auto-generated method stub
		if (this.isCaching()) {
			Map cacheMap = this.getCache();
			synchronized (cacheMap) {
				for (int index = 0; index < keyList.size(); index++) {
					cacheMap.remove(keyList.get(index));
				}
			}

		}
	}


	/**
	 * Removes the from cache.
	 *
	 * @param key the key
	 */
	public void removeFromCache(KeyType key) {
		if (this.isCaching()) {
			Map<KeyType, Type> cacheMap = this.getCache();
			synchronized (cacheMap) {
				cacheMap.remove(key);
			}

		}
	}

	/**
	 * Flush all caches.
	 */
	public static void flushAllCaches() {
		// Flush by replacing map with new empty map
		synchronized (cacheMapMapMap) {
			cacheMapMapMap = new java.util.HashMap();
		}
	}
	
	/**
	 * Store in cache.
	 *
	 * @param collection the collection
	 * @return the collection
	 */
	protected Collection<Type> storeInCache(Collection<Type> collection) {
		if (this.isCaching()) {

			Map<KeyType,Type> cacheMap = this.getCache();
			synchronized (cacheMap) {

				for (Type carrier: collection) {
					cacheMap.put((KeyType)carrier.getKey(), carrier);
				}

			}

		}
		return collection;
		
	}
	
	/**
	 * Store in cache.
	 *
	 * @param collection the collection
	 * @return the list
	 */
	protected List<Type> storeInCache(List<Type> collection) {
		if (this.isCaching()) {

			Map<KeyType,Type> cacheMap = this.getCache();
			synchronized (cacheMap) {

				for (Type carrier: collection) {
					cacheMap.put((KeyType)carrier.getKey(), carrier);
				}

			}

		}
		return collection;
		
	}

	
	/**
	 * Store in cache.
	 *
	 * @param carrier the carrier
	 * @return the type
	 */
	public Type storeInCache(Type carrier) {
		if (this.isCaching()) {
			Map cacheMap = this.getCache();
			synchronized (cacheMap) {
				cacheMap.put(carrier.getKey(), carrier);
			}

		}
		return carrier;
	}

	/**
	 * Store in cache.
	 *
	 * @param carriers the carriers
	 * @return the type[]
	 */
	public Type[] storeInCache(Type[] carriers) {
		if (this.isCaching()) {

			Map cacheMap = this.getCache();
			synchronized (cacheMap) {

				for (int index = 0; index < carriers.length; index++) {
					cacheMap.put(carriers[index].getKey(), carriers[index]);
				}

			}

		}
		return carriers;
	}
	
	/**
	 * Sync with cache.
	 *
	 * @param carriers the carriers
	 * @return the type[]
	 */
	public Type[] syncWithCache(Type[] carriers) {
		Trace trace = Trace.startTrace1(logger,this, "syncWithCache "
				+ (carriers != null ? carriers.length : 0), false);
		Map<KeyType, Type> map = this.getCache();

		for (int i = 0; i < carriers.length; i++) {
			Key key = carriers[i].getKey();
			Type carrier = map.get(key);
			if (carrier != null) {
				java.util.Date cachedDate = carrier.getAndradDatum();
				java.util.Date carrierDate = carriers[i].getAndradDatum();
				if (cachedDate != null && carrierDate != null) {
					if (cachedDate.getTime() > carrierDate.getTime()) {
						trace.logError("Replacing carrier "
								+ ModelUtility.dumpProperties(carriers[i]));
						trace.logError("Replacing with cashed carrier "
								+ ModelUtility.dumpProperties(carrier));
						trace.logMessage("Replacing with new carrier "
								+ cachedDate + " " + carrierDate);
						carriers[i] = carrier;
					} else {
						this.storeInCache(carriers[i]);
					}
				}
			} else {
				this.storeInCache(carriers[i]);
			}

		}
		trace.end();
		return carriers;
	}

	/**
	 * Flush read cache.
	 */
	public static void flushReadCache() {
		readMapCache.release();
	}

	/**
	 * Refresh types.
	 *
	 * @param carriers the carriers
	 * @return the type[]
	 */
	public Type[] refreshTypes(Type[] carriers) {
		Trace trace = Trace.startTrace1(logger,this, "refreshCarriers", false);
		for (int index = 0; index < carriers.length; index++) {
			Type cachedCarrier = this.getFromCache((KeyType) carriers[index]
					.getKey());
			if (cachedCarrier != null) {
				java.util.Date cachedDate = cachedCarrier.getAndradDatum();
				java.util.Date carrierDate = carriers[index].getAndradDatum();
				if (cachedDate != null && carrierDate != null) {
					if (carrierDate.getTime() < cachedDate.getTime()) {
						trace.logMessage("Replacing with new carrier "
								+ cachedDate + " " + carrierDate);
						carriers[index] = cachedCarrier;
					} else {
						this.storeInCache(carriers[index]);
					}
				}
			}
		}
		return carriers;
	}


	/**
	 * Checks if is verbose.
	 *
	 * @return true, if is verbose
	 * @roseuid 3AB47276024F
	 */
	public boolean isVerbose() {

		return logger.isTraceEnabled();
	}
	
	
}
