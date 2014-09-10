package se.pedcat.framework.storage.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkException;



/**
 * The Class StorageConnectionPool.
 */
public class StorageConnectionPool 
{
	
	/** The Constant DEFAULT_POOL_KEY. */
	public static final String DEFAULT_POOL_KEY = "se.pedcat.framework.storage.defaultPool";
	
	/** The Constant DEFAULT_POOL_DEFAULT_VALUE. */
	public static final String DEFAULT_POOL_DEFAULT_VALUE = "defaultPool";
	
	/** The Constant DEFAULT_POOL. */
	public static final String DEFAULT_POOL = System.getProperty(DEFAULT_POOL_KEY,DEFAULT_POOL_DEFAULT_VALUE);
	  
	/**
	 * The Class DatasourceConnectionPool.
	 */
	public static class DatasourceConnectionPool extends StorageConnectionPool {
		
		/** The ds. */
		private javax.sql.DataSource ds = null;
		
		/**
		 * Instantiates a new datasource connection pool.
		 *
		 * @param c the c
		 */
		public DatasourceConnectionPool(Configuration c) {
			super(c);
			logger.info("Lookup " + "java:" + this.getConfiguration().getJndiname());
			try {
				ds = (DataSource) new InitialContext().lookup("java:" + this.getConfiguration().getJndiname());
			} catch (NamingException e) {
				
				e.printStackTrace();
			} 
			
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#clearTransaction()
		 */
		@Override
		public void clearTransaction() throws SQLException {
			// TODO Auto-generated method stub
			//super.clearTransaction();
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#commitTransaction()
		 */
		@Override
		public void commitTransaction() throws SQLException {
			// TODO Auto-generated method stub
			//super.commitTransaction();
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#getConfiguration()
		 */
		@Override
		public Configuration getConfiguration() {
			// TODO Auto-generated method stub
			return super.getConfiguration();
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#getConnection()
		 */
		@Override
		public Connection getConnection() throws ClassNotFoundException,
				SQLException {
			super.leasedConnections++;
			if (ModelUtility.isEmpty(this.getConfiguration().getJndiname()))
			{
				return super.getConnection();
			}
			else
			try
			{
				if (logger.isTraceEnabled())
				{
					logger.trace("Returning connection from "+this.getConfiguration().getJndiname());
				}
				return ds.getConnection();
			}
			catch (Exception e)
			{
				logger.info("Lookup " + "java:" + this.getConfiguration().getJndiname());
				try {
					ds = (DataSource) new InitialContext().lookup("java:" + this.getConfiguration().getJndiname());
					return ds.getConnection();
				} catch (Exception ne) {
					throw new FrameworkException("Kan inte få connection " + ModelUtility.dumpProperties(this.getConfiguration()),ne);
				} 
				

			}
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#getLeasedConnections()
		 */
		@Override
		public int getLeasedConnections() {
			// TODO Auto-generated method stub
			return super.leasedConnections++;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#releaseConnection(java.sql.Connection)
		 */
		@Override
		public void releaseConnection(Connection connection) {
			// TODO Auto-generated method stub
			try {
				super.leasedConnections--;
				connection.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#rollBackTransaction()
		 */
		@Override
		public void rollBackTransaction() throws SQLException {
			// TODO Auto-generated method stub
			//super.rollBackTransaction();
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.StorageConnectionPool#startTransaction()
		 */
		@Override
		public void startTransaction() throws ClassNotFoundException,
				SQLException {
			// TODO Auto-generated method stub
			//super.startTransaction();
		}

	}

	
	/**
	 * The Class Configuration.
	 */
	public static class Configuration 
	{
		
		/** The jndiname. */
		private String jndiname = null;
		
		/** The driver. */
		private String driver = null; // EArkivStorageConfiguration.getInstance().getProperty("se.earkiv.storage.dao.jdbc.driver");
		
		/** The url. */
		private String url = null; //EArkivStorageConfiguration.getInstance().getProperty("se.earkiv.storage.dao.jdbc.url","jdbc:sybase:Tds:signifik-lb7ln8:5000/r7earkivDb");
		
		/** The user. */
		private String user = null; //EArkivStorageConfiguration.getInstance().getProperty("se.earkiv.storage.dao.jdbc.user");
		
		/** The password. */
		private String password = null; //EArkivStorageConfiguration.getInstance().getProperty("se.earkiv.storage.dao.jdbc.password");

		/** The check sql. */
		private String checkSql = null;// EArkivStorageConfiguration.getInstance()	.getProperty("se.earkiv.storage.dao.jdbc.check","select 1 from dbo.TB_User where 1=0");

		/** The max connections. */
		private int maxConnections = 1000; // EArkivStorageConfiguration.getInstance().getIntProperty("se.earkiv.storage.dao.jdbc.maxConnections",0);

		/** The wait millis. */
		private int waitMillis = 1000; //7sEArkivStorageConfiguration				.getInstance()				.getIntProperty("se.earkiv.storage.dao.jdbc.waitMillis", 1000);

		
		/** The name. */
		private String name  = System.getProperty("se.pedcat.framework.storage.defaultPool","defaultPool");
		
		/**
		 * Gets the driver.
		 *
		 * @return the driver
		 */
		public String getDriver() {
			return driver;
		}

		/**
		 * Sets the driver.
		 *
		 * @param driver the driver to set
		 */
		public void setDriver(String driver) {
			this.driver = driver;
		}

		/**
		 * Gets the url.
		 *
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

		/**
		 * Sets the url.
		 *
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}

		/**
		 * Gets the user.
		 *
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * Sets the user.
		 *
		 * @param user the user to set
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * Gets the password.
		 *
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * Sets the password.
		 *
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * Gets the check sql.
		 *
		 * @return the checkSql
		 */
		public String getCheckSql() {
			return checkSql;
		}

		/**
		 * Sets the check sql.
		 *
		 * @param checkSql the checkSql to set
		 */
		public void setCheckSql(String checkSql) {
			this.checkSql = checkSql;
		}

		/**
		 * Gets the max connections.
		 *
		 * @return the maxConnections
		 */
		public int getMaxConnections() {
			return maxConnections;
		}

		/**
		 * Sets the max connections.
		 *
		 * @param maxConnections the maxConnections to set
		 */
		public void setMaxConnections(int maxConnections) {
			this.maxConnections = maxConnections;
		}

		/**
		 * Gets the wait millis.
		 *
		 * @return the waitMillis
		 */
		public int getWaitMillis() {
			return waitMillis;
		}

		/**
		 * Sets the wait millis.
		 *
		 * @param waitMillis the waitMillis to set
		 */
		public void setWaitMillis(int waitMillis) {
			this.waitMillis = waitMillis;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 *
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * Gets the jndiname.
		 *
		 * @return the jndiname
		 */
		public String getJndiname() {
			return jndiname;
		}

		/**
		 * Sets the jndiname.
		 *
		 * @param jndiname the new jndiname
		 */
		public void setJndiname(String jndiname) {
			this.jndiname = jndiname;
		}

	}


	/** The Constant PREFIX. */
	public static final String PREFIX = "se.earkiv.storage.dao.";

	/** The Constant DRIVER_SUFFIX. */
	public static final String DRIVER_SUFFIX = ".jdbc.driver";

	/** The Constant URL_SUFFIX. */
	public static final String URL_SUFFIX = ".jdbc.url";

	/** The Constant USER_SUFFIX. */
	public static final String USER_SUFFIX = ".jdbc.user";

	/** The Constant PASSWORD_SUFFIX. */
	public static final String PASSWORD_SUFFIX = ".jdbc.password";

	/** The Constant CHECK_SUFFIX. */
	public static final String CHECK_SUFFIX = ".jdbc.check";

	/** The Constant JNDINAME_SUFFIX. */
	public static final String JNDINAME_SUFFIX = ".jdbc.jndiName";
	

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(StorageConnectionPool.class);

	/** The instance map. */
	private static Map<String, StorageConnectionPool> instanceMap = new HashMap<String, StorageConnectionPool>();
	
	/** The miljo innehallstyp poolnam map. */
	private static Map<String, Map<String,String>> miljoInnehallstypPoolnamMap = new HashMap<String,Map<String,String>>();
	
	/** The miljo poolnamn map. */
	private static Map<String,String> miljoPoolnamnMap = new HashMap<String,String>();
	
	static 
	{
		//createPool(new Configuration());
	}
	
	
	/**
	 * Creates the pool.
	 *
	 * @param configuration the configuration
	 */
	public static void createPool(Configuration configuration)
	{
		logger.info("Creating pool " + ModelUtility.dumpProperties(configuration));
		if (!ModelUtility.isEmpty(configuration.getJndiname()))
		{
			logger.info("Putting " + configuration.getName()  + " in pools " );
			instanceMap.put(configuration.getName(),new  DatasourceConnectionPool(configuration));
		}
		else
		{
			logger.info("Putting " + configuration.getName()  + " in jdbcpools " );
			
			instanceMap.put(configuration.getName(),new  StorageConnectionPool(configuration));	
		}
		
	}
	
	
	
	/** The leased connections. */
	private int leasedConnections = 0;

	/** The connection list. */
	private List<Connection> connectionList = new ArrayList<Connection>();
	
	/** The connection map. */
	private Map<String, Connection> connectionMap = new HashMap<String, Connection>();

	/** The configuration. */
	private Configuration configuration;

	/**
	 * Clear transaction.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void clearTransaction() throws SQLException {
		String name = Thread.currentThread().getName();
		Connection connection = this.connectionMap.remove(name);
		connection.setAutoCommit(true);
		this.releaseConnection(connection);

	}

	/**
	 * Commit transaction.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void commitTransaction() throws SQLException {
		try
		{
			throw new Exception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String name = Thread.currentThread().getName();
		Connection connection = this.connectionMap.get(name);
		connection.commit();
	}

	/**
	 * Roll back transaction.
	 *
	 * @throws SQLException the sQL exception
	 */
	public void rollBackTransaction() throws SQLException {
		String name = Thread.currentThread().getName();
		Connection connection = this.connectionMap.get(name);
		connection.rollback();
	}

	/**
	 * Start transaction.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	public void startTransaction() throws ClassNotFoundException, SQLException {
		String name = Thread.currentThread().getName();
		Connection connection = this.getConnection();
		connection.setAutoCommit(false);
		this.connectionMap.put(name, connection);
	}

	
	/**
	 * Instantiates a new storage connection pool.
	 *
	 * @param configuration the configuration
	 */
	private StorageConnectionPool(Configuration configuration) {
		// TODO Auto-generated constructor stub
		this.configuration = configuration; 
	}

	/**
	 * Gets the single instance of StorageConnectionPool.
	 *
	 * @param poolName the pool name
	 * @return single instance of StorageConnectionPool
	 */
	public static StorageConnectionPool getInstance(final String poolName) {
		
		StorageConnectionPool pool = instanceMap.get(poolName);
		if (pool==null)
		{
			logger.error("No connectionpool !" + poolName);
			Configuration configuration = new Configuration();
			configuration.setJndiname("Mellanarkiv");
			configuration.setName(poolName);
			createPool(configuration);
			return instanceMap.get(poolName);
		}
		else  
		{
			if (logger.isTraceEnabled())
			{
				logger.trace("Returned pool " + poolName);
				if (false && poolName.equalsIgnoreCase("defaultPool"))
				{
					try
					{
						throw new Exception();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			return pool;
		}
		
	}
	
	/**
	 * Gets the single instance of StorageConnectionPool.
	 *
	 * @return single instance of StorageConnectionPool
	 */
	public static StorageConnectionPool getInstance() {
		return StorageConnectionPool.getInstance(DEFAULT_POOL);
	}

	/**
	 * Release connection.
	 *
	 * @param connection the connection
	 */
	public void releaseConnection(Connection connection) {
		if (!isThreadConnection()) {
			synchronized (connectionList) {
				leasedConnections--;
				connectionList.add(connection);
			}
		}

	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Connection returnedConnection = getThreadConnection();
		if (returnedConnection != null) {
			return returnedConnection;
		}
		synchronized (connectionList) {
			if (connectionList.size() > 0) {
				returnedConnection = (Connection) connectionList.remove(0);
			}
		}
		if (returnedConnection != null) {
			returnedConnection = checkConnection(returnedConnection);
		}
		if (returnedConnection == null) {
			returnedConnection = this.createNewConnection();
		}
		leasedConnections++;
		return returnedConnection;

	}

	/**
	 * Check connection.
	 *
	 * @param connection the connection
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	private Connection checkConnection(Connection connection)
			throws ClassNotFoundException, SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(this.configuration.getCheckSql());
		} catch (Exception e) {
			logger.warn("Can not test " + this.configuration.getCheckSql());
			connection = createNewConnection();
		} finally {
			if (statement != null) {
				try
				{
					statement.close();
				}
				catch (Exception e)
				{
					logger.warn("Could not close statement" + e.getMessage());
				}
			}
		}
		return connection;
	}

	/**
	 * Creates the new connection.
	 *
	 * @return the connection
	 */
	private Connection createNewConnection() {

		try {
			if (this.leasedConnections >= configuration.maxConnections) {
				logger.warn("EXCEEDING MAX CONNECTIONS "
						+ this.leasedConnections + " (" + configuration.maxConnections
						+ ")");

				Thread.sleep(configuration.waitMillis);
				if (this.leasedConnections >= configuration.maxConnections) {
					logger.warn("STILL EXCEEDING MAX CONNECTIONS "
							+ this.leasedConnections + " ("
							+ configuration.maxConnections + ")");
				} else {
					logger.warn("NOT EXCEEDING MAX CONNECTIONS"
							+ this.leasedConnections + " ("
							+ configuration.maxConnections + ")");
				}
			}

			Class.forName(configuration.driver);

			Connection connection = java.sql.DriverManager.getConnection(configuration.url,
					configuration.user, configuration.password);

			return connection;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
			e = e.getNextException();
			while (e != null) {
				logger.error(e.getSQLState() + "  " + e.getMessage(), e);
				e = e.getNextException();

			}
			
			logger.error(CommonUtility.dumpProperties(configuration));
			logger.error("URL:" + configuration.getUrl());
			logger.error("Driver:" + configuration.getDriver());
			logger.error("User:" + configuration.getUser());
			
			throw new FrameworkException("Kan inte skapa connection!", e,
					"se.earkiv.storage.no.connection", new String[] { configuration.getUrl(),
					configuration.getUser(), configuration.getPassword()});
		} catch (Exception e) {
			CommonUtility.dumpProperties(configuration);
			throw new FrameworkException("Kan inte skapa connection!", e,
					"se.earkiv.storage.no.connection", new String[] { configuration.url,
					configuration.getUser(), configuration.getPassword() });
		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		try {

			StorageConnectionPool.getInstance("default").getConnection();
			System.out.println("SUCCESS");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the thread connection.
	 *
	 * @return the thread connection
	 */
	private Connection getThreadConnection() {
		String name = Thread.currentThread().getName();
		return this.connectionMap.get(name);
	}

	/**
	 * Checks if is thread connection.
	 *
	 * @return true, if is thread connection
	 */
	private boolean isThreadConnection() {
		String name = Thread.currentThread().getName();
		return this.connectionMap.containsKey(name);
	}

	/**
	 * Gets the leased connections.
	 *
	 * @return the leased connections
	 */
	public int getLeasedConnections() {
		return leasedConnections;
	}
	
	/**
	 * Release all.
	 */
	public static void releaseAll()
	{
		Iterator<StorageConnectionPool> iterator = instanceMap.values().iterator();
		while (iterator.hasNext())
		{
			iterator.next().releaseConnections();
		}
		Enumeration<Driver> e =  java.sql.DriverManager.getDrivers();
		
		while (e.hasMoreElements())
		{
			try {
				java.sql.DriverManager.deregisterDriver(e.nextElement());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Release connections.
	 */
	private void releaseConnections() 
	{
		
		Iterator<Connection> iterator = this.connectionList.iterator();
		while(iterator.hasNext())
		{
			try {
				logger.info("Closing connection");
				iterator.next().close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.connectionList.clear();
		
		
	}

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return this.configuration;
	}

	/**
	 * Bind pool.
	 *
	 * @param miljo the miljo
	 * @param innehallstyp the innehallstyp
	 * @param name the name
	 */
	public static void bindPool(String miljo, String innehallstyp, String name) 
	{
		logger.info("Binding " + miljo + " " +innehallstyp + " " +name);
		if (miljo!=null && innehallstyp!=null)
		{
			Map<String,String> innehallstypMap = miljoInnehallstypPoolnamMap.get(miljo);
			if (innehallstypMap==null)
			{
				innehallstypMap =  new HashMap<String,String>();
				miljoInnehallstypPoolnamMap.put(miljo, innehallstypMap);
			}
			innehallstypMap.put(innehallstyp, name);
		}
		else
			if (miljo!=null && innehallstyp==null)
			{
				miljoPoolnamnMap.put(miljo, name);
			}	
		
	}

}
