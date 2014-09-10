package se.pedcat.framework.storage.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.pagecache.model.PageCacheTicket;
import se.pedcat.framework.pagecache.model.PageListinfo;
import se.pedcat.framework.pagecache.service.PageCacheService;
import se.pedcat.framework.pagecache.service.PageCacheServiceImpl;
import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.FrameworkDataOperation;
import se.pedcat.framework.common.model.FrameworkOperationResult;
import se.pedcat.framework.common.model.FrameworkOperationSimpleResult;
import se.pedcat.framework.common.model.FrameworkQueryOperation;
import se.pedcat.framework.common.model.GenericCarrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.storage.dao.FrameworkDAO;
import se.pedcat.framework.storage.dao.GenericCarrierFactory;
import se.pedcat.framework.storage.dao.StorageConnectionPool;
import se.pedcat.framework.storage.util.DAOUtility;
import se.pedcat.framework.storage.util.StorageException;


public abstract class GenericCarrierServiceImpl<KeyType extends Key, Type extends Carrier, QueryType extends ModelQuery, DAOType extends FrameworkDAO<KeyType, Type>>
		implements GenericCarrierService<KeyType, Type, QueryType> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<Class<? extends Carrier>> classes2BFlusched = new HashSet<Class<? extends Carrier>>();
	
	private PageCacheService pageCacheService = new PageCacheServiceImpl();
	
	/**
	 *  Returnerar pageCacheService
	 *
	 * @return the pageCacheService
	 */
	public PageCacheService getPageCacheService() {
		return this.pageCacheService;
	}



	/**
	 * Sätter pageCacheService
	 * @param pageCacheService the pageCacheService to set
	 */
	public void setPageCacheService(PageCacheService pageCacheService) {
		this.pageCacheService = pageCacheService;
	}

	static Logger logger = LoggerFactory
			.getLogger(GenericCarrierServiceImpl.class);
	protected GenericCarrierFactory<KeyType, Type, QueryType, DAOType> carrierFactory = null;
	
	protected Class<Type> carrierClass;
	protected Class<QueryType> queryClass;
	private static int count = 0;
	public GenericCarrierServiceImpl(Class<Type> carrierClass,
			Class<DAOType> daoClass, Class<QueryType> queryClass) {
		this(null, carrierClass, daoClass, queryClass);
	}

	
	
	public GenericCarrierServiceImpl(Class<KeyType> keyClass,
			Class<Type> carrierClass, Class<DAOType> daoClass,
			Class<QueryType> queryClass) {

		try {
			if (keyClass == null) {
				Object o = ((GenericCarrier) carrierClass.newInstance())
						.getKey();
				keyClass = (Class<KeyType>) o.getClass();
			}

			this.carrierClass = carrierClass;
			carrierFactory = new GenericCarrierFactory<KeyType, Type, QueryType, DAOType>(
					keyClass, carrierClass, daoClass, queryClass);

			this.queryClass = queryClass;
		} catch (Exception e) {
			throw new StorageException(e);
		}
		finally
		{
			count++;
		}

	}
	/*
	protected void finalize() throws Throwable {
        super.finalize();
        count--;
    }*/
	public Type create(Type type) {
		try {
			return (Type) carrierFactory.create(type);
		} finally {
			onChange();
		}
	}

	public List<Type> create(List<Type> type) {
		try {
			return carrierFactory.create(type);
		} finally {
			onChange();
		}
	}

	@Override
	public Type[] update(Type[] type) {
		// TODO Auto-generated method stub
		try {
			List<Type> list = new ArrayList<Type>();
			list.addAll(Arrays.asList(type));
			list = this.update(list);
			return list.toArray(type);
		} finally {
			onChange();
		}
	}

	public Type update(Type type) {
		try {
			return (Type) carrierFactory.update(type);
		} finally {
			onChange();
		}
	}

	public List<Type> update(List<Type> list) {
		try {
			return carrierFactory.update(list);
		} finally {
			onChange();
		}
	}

	public void delete(Type type) {
		try {
			logger.trace(">>>>>>>>>>>>>>>>>>>>>> delete");			
			carrierFactory.delete(type);
		} finally {
			logger.trace("<<<<<<<<<<<<<<<<<<<<<< delete");
			onChange();
		}
	}

	public void delete(KeyType type) {
		try {
			logger.trace(">>>>>>>>>>>>>>>>>>>>>> delete");
			carrierFactory.delete(type);
		} finally {
			logger.trace("<<<<<<<<<<<<<<<<<<<<<< delete");
			onChange();
		}
	}

	public Type[] findAll() {
		try {
			// TODO Auto-generated method stub
			QueryType query = (QueryType) this.queryClass.newInstance();
			query.setFindAll(true);
			return (Type[]) carrierFactory.findByQuery(query);
		} catch (FrameworkException e) {
			throw e;
		} catch (Exception e) {
			throw new StorageException("", e);
		}
	}

	public Type[] ensure(Type[] type) {

		type = (Type[]) carrierFactory.ensure(type);
		for (Type aType : type) {
			if (aType.getObjectIdAsInt() < 1) {
				logger.error(ModelUtility.dumpProperties(aType));

			}
		}
		return type;
	}

	public Type ensure(Type type) {
		return (Type) carrierFactory.ensure(type);
	}

	protected void clearTransaction() throws SQLException {
		StorageConnectionPool.getInstance().clearTransaction();

	}

	protected void commitTransaction() throws SQLException {
		StorageConnectionPool.getInstance().commitTransaction();
		DAOUtility.afterCompletion(true);
		// HibernateDAO.commitThreadTransaction();
	}

	protected void rollBackTransaction() throws SQLException {
		StorageConnectionPool.getInstance().rollBackTransaction();
		DAOUtility.afterCompletion(false);
		// HibernateDAO.rollbackThreadTransaction();
	}

	protected void startTransaction() throws ClassNotFoundException,
			SQLException {
		StorageConnectionPool.getInstance().startTransaction();
		// HibernateDAO.startThreadTransaction();

	}

	public Type[] create(Type[] carriers) {
		// TODO Auto-generated method stub
		try {
			List<Type> list = new ArrayList<Type>();
			list.addAll(Arrays.asList(carriers));
			list = create(list);
			return list.toArray(carriers);
		} finally {
			onChange();
		}
	}

	public Type findByKey(KeyType key) {

		return (Type) carrierFactory.findByPrimaryKey(key);

	}

	public String ping() {
		return this.getClass().getName();
	}

	public Type[] findByQuery(QueryType query) {
		return this.carrierFactory.findByQuery((QueryType) query);

	}

	public DAOType getDAO() {
		return this.carrierFactory.getDAO();
	}

	public void flushQueryCache() {
		GenericCarrierFactory.flushQueryCache(carrierClass);
	}

	public void flushQueryCache(Class<?> class1) {
		GenericCarrierFactory.flushQueryCache(class1);
	}

	public void onChange() {
		try
		{
			flushQueryCache();
		}
		catch (Exception e)
		{
			
		}
	}

	public void updateStatistics() {
		try
    	{
        	logger.trace("updateStatistics();");
        	this.carrierFactory.updateStatistics();
    	}
    	catch (Exception e)
    	{
    		logger.warn("Could not update statistics!",e);
    	}
		
	}

	public Type save(Type type) {
		if (type.isNew()) {
			return this.create(type);
		} else {
			return this.update(type);
		}
	}

	@Override
	public Type findByKey2(Key key) {
		// TODO Auto-generated method stub
		return this.findByKey((KeyType) key);
	}

	public  Type ensureDependent(Type type)
	{
		return type;
	}
	
	public static int count()
	{
		return count;
	}
	
	public void addClass2BFlushed(Class<? extends Carrier> aClazz)
	{
		this.classes2BFlusched.add(aClazz);
	}
	
	public Integer findByQueryCount(QueryType query)
	{
		return this.carrierFactory.findByQueryCount(query);
	}
	
	public  PageListinfo<?,?> findPageByQuery(PageCacheTicket ticket ,int page,QueryType query)
	{
		if (this.getPageCacheService().isHasPageCache(ticket))
		{
			List<KeyType> list =  this.getPageCacheService().getPage(ticket, page);
			query.setPageList(list);
			query.setPageQuery(true);
			Type[] rows = this.carrierFactory.findByQuery(query);
			return this.getPageCacheService().getPageListinfo(ticket, page, rows);
		}
		else
		{
			Object[] rows = this.carrierFactory.findKeysByQuery(query);
			ticket = this.getPageCacheService().cacheResult(rows);
			return this.findPageByQuery(ticket, page, query);
		}
		
	}



	@Override
	public FrameworkOperationResult<Type> save(FrameworkDataOperation<Type> type) {
		
		if (!ApplicationContextManager.getInstance().isHasContext())
		{
			ApplicationContextManager.getInstance().setCurrentContext(type.getApplicationContext());
			
		}
		Type[] data = type.getData();
		for(Type adata: data)
		{
			adata = save(adata);
		}
		return new FrameworkOperationResult<Type>(data ,ApplicationContextManager.getInstance().getCurrentContext());	
	}


	@Override
	public FrameworkOperationResult<Type> create(
			FrameworkDataOperation<Type> type) {
		if (!ApplicationContextManager.getInstance().isHasContext())
		{
			ApplicationContextManager.getInstance().setCurrentContext(type.getApplicationContext());
		}
		return new FrameworkOperationResult<Type>(this.create(type.getData()),ApplicationContextManager.getInstance().getCurrentContext());
	}



	@Override
	public FrameworkOperationResult<Type> update(
			FrameworkDataOperation<Type> type) {
		if (!ApplicationContextManager.getInstance().isHasContext())
		{
			ApplicationContextManager.getInstance().setCurrentContext(type.getApplicationContext());
		}
		return new FrameworkOperationResult<Type>(this.update(type.getData()),ApplicationContextManager.getInstance().getCurrentContext());
	}



	@Override
	public FrameworkOperationResult<Type> findByQuery(
			FrameworkQueryOperation<QueryType> type) {
		if (!ApplicationContextManager.getInstance().isHasContext())
		{
			ApplicationContextManager.getInstance().setCurrentContext(type.getApplicationContext());
		}
		return new FrameworkOperationResult<Type>( this.findByQuery(type.getQuery()),ApplicationContextManager.getInstance().getCurrentContext());
	}



	@Override
	public FrameworkOperationSimpleResult<Integer> findByQueryCount(
			FrameworkQueryOperation<QueryType> type) {

		if (!ApplicationContextManager.getInstance().isHasContext())
		{
			ApplicationContextManager.getInstance().setCurrentContext(type.getApplicationContext());
		}
		return new FrameworkOperationSimpleResult<Integer>( this.findByQueryCount(type.getQuery()),ApplicationContextManager.getInstance().getCurrentContext());	}
	
	
}
