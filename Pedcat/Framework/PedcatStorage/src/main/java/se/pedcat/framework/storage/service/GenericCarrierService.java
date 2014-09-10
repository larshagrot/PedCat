package se.pedcat.framework.storage.service;

import java.util.List;

import se.pedcat.framework.pagecache.model.PageCacheTicket;
import se.pedcat.framework.pagecache.model.PageListinfo;
import se.pedcat.framework.common.model.FrameworkOperationSimpleResult;
import se.pedcat.framework.common.model.FrameworkQueryOperation;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.service.FrameworkService;
import se.pedcat.framework.common.model.FrameworkDataOperation;
import se.pedcat.framework.common.model.FrameworkOperationResult;


/**
 * 
 * Generellt gränsnitt mot services
 * 
 * @author laha
 *
 * @param <KeyType>
 * @param <Type>
 * @param <QueryType>
 */
public interface GenericCarrierService<KeyType extends Key,Type extends Carrier,QueryType> extends FrameworkService{
	public Type save(Type type);
	public FrameworkOperationResult<Type> save(FrameworkDataOperation<Type> type);

	public Type create(Type type);
	public FrameworkOperationResult<Type> create(FrameworkDataOperation<Type> type);
	
	public Type[] create(Type[] type);
	public List<Type> create(List<Type> type);
	public Type update(Type type);
	public FrameworkOperationResult<Type> update(FrameworkDataOperation<Type> type);

	public List<Type> update(List<Type> type);
	public Type[] update(Type[] type);
	public void delete(KeyType keytype);
	public void delete(Type type);
	
	public Type[] findAll();
	public Type[] ensure(Type[] type);
	public Type ensure(Type type);
    public Type findByKey(KeyType key);
    public Type findByKey2(Key key);
    public Type[] findByQuery(QueryType query);
	public FrameworkOperationResult<Type> findByQuery(FrameworkQueryOperation<QueryType> type);

    public Integer findByQueryCount(QueryType query);
    public FrameworkOperationSimpleResult<Integer> findByQueryCount(FrameworkQueryOperation<QueryType> type);
    
	void onChange();
	void flushQueryCache();
	void updateStatistics();
	
	public PageListinfo<?,?> findPageByQuery(PageCacheTicket ticket,int page,QueryType query);
	
	
	
        
}
