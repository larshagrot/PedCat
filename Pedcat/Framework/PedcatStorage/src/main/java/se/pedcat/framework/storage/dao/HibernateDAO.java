package se.pedcat.framework.storage.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.config.FrameworkConfiguration;
import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.storage.dao.FrameworkDAO;
import se.pedcat.framework.storage.util.StorageException;


/**
 * The Class HibernateDAO.
 *
 * @param <KeyType> the generic Key type
 * @param <Type> the generic Carrier type
 */
public abstract class HibernateDAO<KeyType extends Key, Type extends Carrier> extends
		FrameworkGenericBaseImpl<KeyType, Type> implements
		FrameworkDAO<KeyType, Type> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(HibernateDAO.class);

	/**
	 * The Class HibernateQueryCommand.
	 */
	public abstract class HibernateQueryCommand {
		
		/**
		 * Gets the query string.
		 *
		 * @return the query string
		 */
		public abstract String getQueryString();

		/**
		 * Sets the query parameters.
		 *
		 * @param query the query
		 * @return the org.hibernate. query
		 */
		public org.hibernate.Query setQueryParameters(org.hibernate.Query query) {

			return query;
		}

	}
	
	
	/** The session map. */
	private static Map<String, Session> sessionMap = new HashMap<String, Session>();
	
	/** The transaction map. */
	private static Map<String, Transaction> transactionMap = new HashMap<String, Transaction>();
	
	/** The use thread transaction. */
	private static boolean useThreadTransaction = false;
	
	/** The session factory map. */
	private static Map<String,SessionFactory> sessionFactoryMap = new HashMap<String,SessionFactory>();

	/**
	 * Start thread transaction.
	 */
	public static void startThreadTransaction() {
		if (useThreadTransaction) {
			String name = Thread.currentThread().getName();
			Session session = sessionFactoryMap.get("").openSession();
			Transaction tx = session.beginTransaction();
			sessionMap.put(name, session);
			transactionMap.put(name, tx);
		}
	}

	/**
	 * Commit thread transaction.
	 */
	public static void commitThreadTransaction() {
		if (useThreadTransaction) {
			String name = Thread.currentThread().getName();
			Session session = sessionMap.remove(name);
			try {
				if (session != null) {
					session.flush();
				}
				Transaction tx = transactionMap.remove(name);
				if (tx != null) {
					tx.commit();
				}
			} finally {
				if (session != null) {
					session.close();
				}
			}
		}
	}

	/**
	 * Rollback thread transaction.
	 */
	public static void rollbackThreadTransaction() {
		if (useThreadTransaction) {
			String name = Thread.currentThread().getName();
			Session session = sessionMap.remove(name);
			Transaction tx = transactionMap.remove(name);
			tx.rollback();
			session.close();
		}
	}

	/** The carrier class. */
	Class<Type> carrierClass;
	
	/** The use transactions. */
	private boolean useTransactions = true;
	
	/** The commit transactions. */
	private boolean commitTransactions;

	/**
	 * Returnerar commitTransactions.
	 *
	 * @return the commitTransactions
	 */
	public boolean isCommitTransactions() {
		return this.commitTransactions;
	}

	/**
	 * Sätter commitTransactions.
	 *
	 * @param commitTransactions the commitTransactions to set
	 */
	public void setCommitTransactions(boolean commitTransactions) {
		this.commitTransactions = commitTransactions;
	}

	/**
	 * Instantiates a new hibernate dao.
	 *
	 * @param carrierClass the carrier class
	 */
	public HibernateDAO(Class<Type> carrierClass) {
		super();
		this.carrierClass = carrierClass;
	}

	

	/**
	 * Inits the.
	 *
	 * @param jndiName the jndi name
	 */
	public static void init(String jndiName) {

		try {
			// Create the SessionFactory from hibernate.cfg.xml
			/*
			 * Configuration c = new Configuration();
			 * c.setProperty("connection.username"
			 * ,EArkivStorageConfiguration.getInstance()
			 * .getProperty("se.signifikant.framework.storage.dao.jdbc.user","r7earkiv"));
			 * c.setProperty("connection.password",
			 * EArkivStorageConfiguration.getInstance()
			 * .getProperty("se.signifikant.framework.storage.dao.jdbc.password","r7earkiv"));
			 * c.setProperty("connection.url",
			 * EArkivStorageConfiguration.getInstance
			 * ().getProperty("se.signifikant.framework.storage.dao.jdbc.url"
			 * ,"jdbc:sybase:Tds:laha-dator:5000/r7earkiv_db"));
			 * c.setProperty("connection.driver_class",
			 * EArkivStorageConfiguration
			 * .getInstance().getProperty("se.signifikant.framework.storage.dao.jdbc.driver"
			 * ,"com.sybase.jdbc3.jdbc.SybDriver")); c.setProperty("dialect",
			 * "org.hibernate.dialect.SybaseDialect");
			 * c.setProperty("current_session_context_class", "thread");
			 * 
			 * /*c.addResource("Kallsystem.hbm.xml");
			 * c.addResource("Kallsystemversion.hbm.xml");
			 * c.addResource("Exportprogram.hbm.xml");
			 * c.addResource("Exportprogramversion.hbm.xml");
			 * c.addResource("Innehallstyp.hbm.xml");
			 */
			
			if (false)
			{
				/*sessionFactory = new Configuration().configure()
					.buildSessionFactory();*/
			}
			else
			{
				try
				{
					logger.info("Lookup " + jndiName);
					InitialContext ctx      = new InitialContext();
					sessionFactoryMap.put(jndiName,  (SessionFactory)
					ctx.lookup(jndiName));
				}
				catch(Exception e)
				{
					logger.error("Could not lookup " + jndiName);
					sessionFactoryMap.put(jndiName,new Configuration().configure()
					.buildSessionFactory());
			
				}
			}
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("FEL", ex);
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Session getSession() {

		String name = this.getHibernateSessionFactoryJNDIName();
		if (ModelUtility.isEmpty(name))
		{
			logger.error("Fatalt fel jndiname == null");
			throw new FrameworkException("");
		}
		if (!sessionFactoryMap.containsKey(name)) {
			init(name);
		}
		logger.trace("Using " + name);
		SessionFactory sessionFactory =  sessionFactoryMap.get(name);
		String tname = Thread.currentThread().getName();
		Session session = sessionMap.get(tname);
		if (session != null) {
			logger.trace("Found " + name);
			return session;
		} else {
			try
			{
				logger.trace("get current " + name);
				return sessionFactory.getCurrentSession();
			}
			catch (Exception  e)
			{
				logger.error("open session " + name,e);
				return sessionFactory.openSession();
			}
			
		}

	}

	/**
	 * Gets the default hibernate session factory jndi name.
	 *
	 * @return the default hibernate session factory jndi name
	 */
	protected String getDefaultHibernateSessionFactoryJNDIName() 
	{
		return FrameworkConfiguration.getInstance().getProperty("se.pedcat.framework.storage.hibernate.jdniName", "java:/hibernate/SessionFactory");
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#countByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	public int countByQuery(ModelQuery query) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#create(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public Type create(Type carrier) {
		// TODO Auto-generated method stub
		List<Type> carriers = new ArrayList<Type>();
		carriers.add(carrier);
		return this.create(carriers).get(0);
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#create(java.util.List)
	 */
	@Override
	public List<Type> create(List<Type> carriers) {
		// TODO Auto-generated method stub

		logger.info("Skapar " + carriers.size() + " "
				+ this.carrierClass.getName());
		long start = System.currentTimeMillis();
		Session session = this.getSession();
		Transaction t = this.beginTransaction(session);
		Carrier currentCarrier = null;
		try {
			Date date = new Date();
			String user = ApplicationContextManager.getInstance().getCurrentContext().getUserName();
			logger.info("user=" + user);
			if (user== null) user = "unknown";
			for (Carrier carrier : carriers) {
				currentCarrier = carrier;
				carrier.setSkapadAv(user);
				carrier.setSkapadDatum(date);
				session.save(carrier);
			}
			commitTransaction(t);
			logger.info("Skapade " + carriers.size() + " "
					+ ((System.currentTimeMillis() - start) / 1000.));
		} catch (Exception e) {
			logger.error("ERROR", e);
			logger.error(ModelUtility.dumpProperties(currentCarrier));
			logger.error(ModelUtility.dumpProperties(carriers));
			rollbackTransaction(t);
			throw new RuntimeException(e);
		}
		return carriers;
	}

	/**
	 * Begin transaction.
	 *
	 * @param session the session
	 * @return the transaction
	 */
	protected Transaction beginTransaction(Session session) {
		if (this.useTransactions) {
			String name = Thread.currentThread().getName();
			Session tsession = sessionMap.get(name);
			if (tsession != null && tsession.getTransaction().isActive()) {
				return tsession.getTransaction();
			} else {
				Transaction t = session.beginTransaction();

				return t;
			}
		}
		return null;

	}

	/**
	 * Rollback transaction.
	 *
	 * @param t the t
	 */
	protected void rollbackTransaction(Transaction t) {
		if (this.useTransactions) {
			String name = Thread.currentThread().getName();
			Session tsession = sessionMap.get(name);
			if (tsession != null) {

			} else {
				t.rollback();
			}
			//throw new FrameworkException("ROLLBACK");
		}
	}

	/**
	 * Commit transaction.
	 *
	 * @param t the t
	 */
	protected void commitTransaction(Transaction t) {

		if (this.useTransactions && this.commitTransactions) {
			String name = Thread.currentThread().getName();
			Session tsession = sessionMap.get(name);
			if (tsession != null) {

			} else {
				t.commit();
			}
		}
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#delete(se.pedcat.framework.common.model.Key)
	 */
	@Override
	public void delete(KeyType key) {
		List<KeyType> list = new ArrayList<KeyType>();
		list.add(key);
		delete(list);
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#delete(java.util.List)
	 */
	@Override
	public void delete(List<KeyType> keys) {

		Session session = this.getSession();
		Transaction t = this.beginTransaction(session);
		try {
			logger.trace(">>>>>>>>>>>>>>>>>>>>>> delete");
			for (int i = 0; i < keys.size(); i++) {
				if (keys.get(i).isInt())
				{
					int id = ((Key) keys.get(i)).getObjectIdAsInt();
					Object object = session.load(this.carrierClass, id);
					session.delete(object);
				}
				else
					if (keys.get(i).isString())
					{
						String id = ((Key) keys.get(i)).getObjectIdAsString();
						Object object = session.load(this.carrierClass, id);
						session.delete(object);
					}
					
				super.removeFromCache(keys.get(i));
			}
			session.flush();
			commitTransaction(t);
		} catch (Exception e) {
			rollbackTransaction(t);
			logger.error("Rolling back transaction",e);
			logger.error("Nested " +CommonUtility.getNestedException(e));
			throw new FrameworkException("Kan inte ta bort objekt:",  CommonUtility.getNestedException(e));
		}
		finally
		{
			logger.trace("<<<<<<<<<<<<<<<<<<<<<< delete");
		}

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#delete(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public void delete(Type carrier) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#delete(Type[])
	 */
	@Override
	public void delete(Type[] carrier) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(Type[])
	 */
	@Override
	public Type[] ensure(Type[] carriers) {
		// TODO Auto-generated method stub
		for (int i = 0; i < carriers.length; i++) {
			carriers[i] = ensure(carriers[i]);
		}
		return carriers;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(java.util.List)
	 */
	@Override
	public List<Type> ensure(List<Type> carriers) {
		List<Type> newList = new ArrayList<Type>();
		for (Type type : carriers) {
			type = ensure(type);
			newList.add(type);
		}
		carriers.clear();
		carriers = null;
		return newList;

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public Type ensure(Type carrier) {
		// TODO Auto-generated method stub
		Type answer = this.findByPrimaryKey((KeyType) carrier.getKey());
		if (answer != null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Found " + carrier.getObjectId());
			}
			answer = this.update(carrier);
			return answer;
		} else {
			logger.trace("Not found  ");
			return this.create(carrier);
		}

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#executeCommand(se.pedcat.framework.storage.dao.SQLCommand)
	 */
	@Override
	public void executeCommand(SQLCommand sqlCommand) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#findAll()
	 */
	@Override
	public Type[] findAll() {
		List<Type> list = null;
		Session session = this.getSession();
		Transaction t = this.beginTransaction(session);
		try {
			list = session.createQuery("from " + carrierClass.getName()).setReadOnly(true).list();

			commitTransaction(t);
		} catch (Exception e) {
			rollbackTransaction(t);

			throw new RuntimeException(e);
		}

		return list.toArray((Type[]) Array.newInstance(this.carrierClass, 0));
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#findByPrimaryKey(se.pedcat.framework.common.model.Key)
	 */
	@Override
	public Type findByPrimaryKey(Key key) {
		// TODO Auto-generated method stub
		if (key == null || key.isNull()) {
			logger.trace("Key is null " + this.getClass().getName() + " " + (key==null?"":key.getClass().getName()));
			
			return null;
		}
		Type returned = null;
		returned = this.getFromCache((KeyType) key);
		if (returned == null) {
			// Get Connection
			Session session = this.getSession();

			Transaction t = beginTransaction(session);

			try {
				if (key.isInt())
				{
					int id = key.getObjectId();
					logger.trace("Trying to find " + id);
					returned = (Type) session.load(this.carrierClass, id);
				}
				else
					if (key.isString())
					{
						String id = key.getObjectIdAsString();
						logger.trace("Trying to find " + id);
						returned = (Type) session.load(this.carrierClass, id);
					}	
				if (returned != null) {
					Hibernate.initialize(returned);
				}

				commitTransaction(t);

			} catch (ObjectNotFoundException e) {
				logger.info("Object not found " + key.getObjectIdAsString());
				rollbackTransaction(t);
				returned = null;
			} catch (Exception e) {
				rollbackTransaction(t);
				throw new RuntimeException(e);
			}
			if (returned != null) {
				storeInCache(returned);
				// Map map = getReadCacheMap(true);
				// map.put(returned.getKey(), returned);
				logger.trace("Found carrier in database...");
				foundInDB++;
			}

		} else {
			foundInCache++;
			logger.trace("Found carrier in cache...");
		}
		return returned;

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#findByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	@Override
	public Type[] findByQuery(ModelQuery query) {
		// TODO Auto-generated method stub
		if (query.isFindAll()) {
			return this.findAll();
		} else {
			throw new RuntimeException(
					"FindByQuery called but not overridden by "
							+ this.getClass().getName());
		}
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkGenericBaseImpl#isCaching()
	 */
	@Override
	public boolean isCaching() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkGenericBaseImpl#isQueryCaching()
	 */
	@Override
	public boolean isQueryCaching() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#update(java.util.List)
	 */
	@Override
	public List<Type> update(List<Type> carriers) {
		// TODO Auto-generated method stub
		Session session = this.getSession();
		Transaction t = this.beginTransaction(session);
		try {
			Date date = new Date();
			String user = ApplicationContextManager.getInstance().getCurrentContext().getUserName();
			for (Carrier carrier : carriers) {
				carrier.setAndradAv(user);
				carrier.setAndradDatum(date);
				session.update(carrier);

			}
			commitTransaction(t);
		} catch (Exception e) {
			logger.error("ERROR", e);
			rollbackTransaction(t);
			throw new RuntimeException(e);

		} finally {
			this.flushCache();
		}
		return carriers;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#update(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public Type update(Type carrier) {

		List<Type> list = new ArrayList<Type>();
		list.add(carrier);
		list = this.update(list);

		return list.get(0);

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#updateStatistics()
	 */
	@Override
	public void updateStatistics() {
		// TODO Auto-generated method stub

	}

	/**
	 * Find by hql query.
	 *
	 * @param hdqlquery the hdqlquery
	 * @return the type[]
	 */
	@SuppressWarnings("unchecked")
	protected Type[] findByHqlQuery(String hdqlquery) {
		// TODO Auto-generated method stub
		List list = null;
		Session session = this.getSession();
		Transaction t = this.beginTransaction(session);
		try {
			list = session.createQuery(hdqlquery).setReadOnly(true).list();
			commitTransaction(t);
			// list = storeInCache(list);
		} catch (Exception e) {
			logger.error("hdqlQuery", e);
			rollbackTransaction(t);
			throw new StorageException(e.getMessage(), e);
		}
		try
		{
			return (Type[])  list.toArray((Type[])Array.newInstance(this.carrierClass, 0));
		}
		catch (Exception e)
		{ 
			try
			{
				logger.error(hdqlquery + " " + e.getMessage() + " " + this.carrierClass.getName() + " " + list.size(),e);
				for(Object o:list)
				{
					logger.error(o.getClass().getName());
				}
				
			}
			catch (Exception e2)
			{
				logger.error("",e2);
			}
			return (Type[]) Array.newInstance(this.carrierClass,0);
		}
	}

	/**
	 * Find by hibernate query.
	 *
	 * @param hibernateQueryCommand the hibernate query command
	 * @return the list
	 */
	public List<Type> findByHibernateQuery(
			HibernateQueryCommand hibernateQueryCommand) {

		Session session = this.getSession();
		Transaction tx = null;

		tx = this.beginTransaction(session);
		try {
			String queryString = hibernateQueryCommand.getQueryString();
			org.hibernate.Query query = session.createQuery(queryString);
			query = hibernateQueryCommand.setQueryParameters(query);

			List<Type> list = query.setReadOnly(true).list();
			commit(tx);
			return list;

		} catch (Exception e) {
			logger.error("", e);
			rollback(tx);
			throw new RuntimeException(e);
		} finally {

		}
	}

	/**
	 * Rollback.
	 *
	 * @param tx the tx
	 */
	private void rollback(Transaction tx) {
		if (useTransactions) {
			tx.rollback();
		}

	}

	/**
	 * Commit.
	 *
	 * @param tx the tx
	 */
	private void commit(Transaction tx) {
		if (useTransactions) {
			tx.commit();
		}

	}

	/**
	 * Find by int.
	 *
	 * @param value the value
	 * @param query the query
	 * @param parameterName the parameter name
	 * @return the list
	 */
	public List<Type> findByInt(final int value, final String query,
			final String parameterName) {
		return this.findByHibernateQuery(new HibernateQueryCommand() {

			@Override
			public String getQueryString() {
				// TODO Auto-generated method stub
				return query;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @seese.signifikant.framework.storage.dao.HibernateDAO.HibernateQueryCommand#
			 * setQueryParameters(org.hibernate.Query)
			 */
			@Override
			public org.hibernate.Query setQueryParameters(
					org.hibernate.Query query) {
				return query.setInteger(parameterName, value);

			}
		});
	}

	/**
	 * Find by string.
	 *
	 * @param value the value
	 * @param query the query
	 * @param parameterName the parameter name
	 * @return the list
	 */
	public List<Type> findByString(final String value, final String query,
			final String parameterName) {
		return this.findByHibernateQuery(new HibernateQueryCommand() {

			@Override
			public String getQueryString() {
				// TODO Auto-generated method stub
				return query;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @seese.signifikant.framework.storage.dao.HibernateDAO.HibernateQueryCommand#
			 * setQueryParameters(org.hibernate.Query)
			 */
			@Override
			public org.hibernate.Query setQueryParameters(
					org.hibernate.Query query) {
				return query.setString(parameterName, value);

			}

		});

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#findKeysByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	public <KeyType2> KeyType2[] findKeysByQuery(ModelQuery q)
	{
		throw new FrameworkException("findKeysByQuery not implemented by " + this.getClass().getName());
	}
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkBaseImpl#isHasConnectionPool()
	 */
	protected boolean isHasConnectionPool() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Gets the hibernate session factory jndi name.
	 *
	 * @return the hibernate session factory jndi name
	 */
	protected String getHibernateSessionFactoryJNDIName() 
	{
		String name=null;
		if (ApplicationContextManager.getInstance().isHasContext())
		{
			name = ApplicationContextManager.getInstance().getCurrentContext().getMessageInfo("hibernateSessionFactory");
		}
		if (ModelUtility.isEmpty(name))
		{
			name=  this.getDefaultHibernateSessionFactoryJNDIName();
		}
		if (logger!=null && logger.isTraceEnabled())
		{
			logger.trace("Using session factory " + name);
		}
		
		return name;

	}

}
