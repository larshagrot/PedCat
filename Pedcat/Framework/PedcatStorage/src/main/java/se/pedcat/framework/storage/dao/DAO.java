package se.pedcat.framework.storage.dao;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.ValueObject;
import se.pedcat.framework.common.util.CommonUtility;



/**
 * The Class DAO.
 */
public abstract class DAO {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(DAO.class);
	
	/** The connection pool. */
	private static StorageConnectionPool connectionPool = StorageConnectionPool.getInstance(); 
	
	/** The attribute column map. */
	private Map<String,String> attributeColumnMap = new java.util.HashMap<String,String>();
	
	/** The pk attribute column map. */
	private Map<String,String> pkAttributeColumnMap = new java.util.HashMap<String,String>();;
	
	/** The table. */
	private String table = "DBA.TB_Journalpost";

	
	/**
	 * Instantiates a new dAO.
	 */
	public DAO()
	{
		init();
	}
	
	/**
	 * Inits the.
	 */
	protected abstract void init(); 
	
	/**
	 * Adds the attribute and column name.
	 *
	 * @param attribute the attribute
	 * @param column the column
	 */
	protected void addAttributeAndColumnName(String attribute, String column) {
		// TODO Auto-generated method stub
		attributeColumnMap.put(attribute,column);
	}
	
	/**
	 * Adds the attribute and column name.
	 *
	 * @param attribute the attribute
	 * @param column the column
	 * @param pk the pk
	 */
	protected void addAttributeAndColumnName(String attribute, String column,boolean pk) {
		// TODO Auto-generated method stub
		attributeColumnMap.put(attribute,column);
		if (pk)
		{
			addPkAttributeAndColumnName(attribute,column);
		}
	}
	
	/**
	 * Adds the pk attribute and column name.
	 *
	 * @param attribute the attribute
	 * @param column the column
	 */
	protected void addPkAttributeAndColumnName(String attribute, String column) {
		// TODO Auto-generated method stub
		pkAttributeColumnMap.put(attribute,column);
	}
	
	/**
	 * Creates the.
	 *
	 * @param valueObjects the value objects
	 * @return the value object[]
	 */
	public ValueObject[] create(ValueObject[] valueObjects)
	{
		Connection connection = null;
		try {
			
			logger.info("Getting connection..." + CommonUtility.getElapsed());
			connection = this.getConnection();
			logger.info("Got connection..." + CommonUtility.getElapsed());	
			
			String sql = createInsertStatementString();
			logger.info(sql);
			PreparedStatement statement = createStatement(connection,sql);
			logger.info("Created statement.." + CommonUtility.getElapsed());
			
			for(ValueObject valueObject:valueObjects)
			{
				setParameters(statement,valueObject);
				logger.info("Parameters set.." + CommonUtility.getElapsed());
				statement.executeUpdate();
				logger.info("Update executed.." + CommonUtility.getElapsed());
				statement.clearParameters();
				logger.info("Created object.." + CommonUtility.getElapsed());
			}
			
			connection.commit();
			 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (connection!=null)
				{
					SQLWarning w = connection.getWarnings();
					while (w!=null)
					{
						logger.warn(w.getMessage());
						w = w.getNextWarning();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			releaseConnection(connection);
		}
		
		
		
		return valueObjects;
	}
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return connectionPool.getConnection();
	}
	
	/**
	 * Release connection.
	 *
	 * @param connection the connection
	 */
	private void releaseConnection(Connection connection) {
		// TODO Auto-generated method stub
		connectionPool.releaseConnection(connection);
	}

	/**
	 * Sets the parameters.
	 *
	 * @param statement the statement
	 * @param valueObject the value object
	 * @throws SQLException the sQL exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws IntrospectionException the introspection exception
	 */
	private void setParameters(PreparedStatement statement,
			ValueObject valueObject) throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		int index = 1;
		
		if (valueObject==null)
		{
			logger.error("journalpost==null");
		}
		
		Set<String> keySet = attributeColumnMap.keySet();	
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String column = attributeColumnMap.get(key);
			
			Object value = getValue(key,valueObject);
			logger.info(key +":" + value);
			if (value!=null)
			{
				statement.setObject(index++,value );
			}
			else
			{
				
				statement.setNull(index++,java.sql.Types.VARCHAR);
			}
			
		}
		
		
	}

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @param valueObject the value object
	 * @return the value
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws IntrospectionException the introspection exception
	 */
	private Object getValue(String key, ValueObject valueObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException 
	{
		// TODO Auto-generated method stub
		return CommonUtility.getValue(key,valueObject);
	}




	

	/**
	 * Creates the statement.
	 *
	 * @param connection the connection
	 * @param sql the sql
	 * @return the prepared statement
	 * @throws SQLException the sQL exception
	 */
	private PreparedStatement createStatement(Connection connection, String sql) throws SQLException {
		// TODO Auto-generated method stub
		return connection.prepareStatement(sql);
	}


	/**
	 * Creates the insert statement string.
	 *
	 * @return the string
	 */
	private String createInsertStatementString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(this.getTable());
		sb.append(this.getInsertColumnslist());
		sb.append(" values ");
		sb.append(this.getPlaceholderlist());
		
		return sb.toString();
	}

	/**
	 * Gets the placeholderlist.
	 *
	 * @return the placeholderlist
	 */
	private Object getPlaceholderlist() {
		// TODO Auto-generated method stub
		int count = attributeColumnMap.size();
		StringBuffer sb = new StringBuffer(" (");
		for(int i=0;i<count;i++)
		{
			sb.append('?');
			if (i<count-1)
			{
				sb.append(',');
			}
		}
		
		return sb.append(") ").toString();
	}

	/**
	 * Gets the insert columnslist.
	 *
	 * @return the insert columnslist
	 */
	private Object getInsertColumnslist() {
		StringBuffer sb = new StringBuffer(" (");
		Set<String> keySet = attributeColumnMap.keySet();	
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String column = attributeColumnMap.get(key);
			sb.append(column);
			if (iterator.hasNext())
			{
				sb.append(',');
			}
		}
		
		return sb.append(") ").toString();
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public String getTable() {
		return this.table;
	}

	/**
	 * Sets the table.
	 *
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;}

	/**
	 * Exists.
	 *
	 * @param valueObject the value object
	 * @return true, if successful
	 */
	public boolean exists(ValueObject valueObject) {
		// TODO Auto-generated method stub
		Connection connection = null;
		try {
			
			logger.info("Getting connection...");
			connection = this.getConnection();
			logger.info("Got connection...");	
			
			String sql = createExistsStatementString();
			logger.info(sql);
			PreparedStatement statement = createStatement(connection,sql);
			logger.info("Created statement..");
			
			setPKParameters(statement,valueObject);
			return statement.executeQuery().next();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (connection!=null)
				{
					SQLWarning w = connection.getWarnings();
					while (w!=null)
					{
						logger.warn(w.getMessage());
						w = w.getNextWarning();
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			releaseConnection(connection);
		}
		return false;
	}

	/**
	 * Sets the pk parameters.
	 *
	 * @param statement the statement
	 * @param valueObject the value object
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws IntrospectionException the introspection exception
	 * @throws SQLException the sQL exception
	 */
	private void setPKParameters(PreparedStatement statement,
			ValueObject valueObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException, SQLException {
		Set<String> keySet = pkAttributeColumnMap.keySet();	
		Iterator<String> iterator = keySet.iterator();
		int index=1;
		while (iterator.hasNext())
		{
			String key = iterator.next();
			Object value = getValue(key,valueObject);
			logger.info(key +":" + value);
			if (value!=null)
			{
				statement.setObject(index++,value );
			}
			else
			{
				
				statement.setNull(index++,java.sql.Types.VARCHAR);
			}
		}
		
	}

	/**
	 * Creates the exists statement string.
	 *
	 * @return the string
	 */
	private String createExistsStatementString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		sb.append(" select 1 from ");
		sb.append(this.getTable());
		sb.append(" where ");
		sb.append(this.getPKColumnWhereClause());
		return sb.toString();
	}

	/**
	 * Gets the pK column where clause.
	 *
	 * @return the pK column where clause
	 */
	private Object getPKColumnWhereClause() {
		StringBuffer sb = new StringBuffer();
		
		Set<String> keySet = pkAttributeColumnMap.keySet();	
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			String key = iterator.next();
			String column = attributeColumnMap.get(key);
			sb.append(column);
			sb.append("=?");
			
			if (iterator.hasNext())
			{
				sb.append(" AND ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Ensure.
	 *
	 * @param valueObject the value object
	 */
	public void ensure(ValueObject valueObject) {
		// TODO Auto-generated method stub
		if (!exists(valueObject))
		{
			create(valueObject);
		}
		else
		{
			logger.trace(valueObject.getObjectId() + " already exists");
		}
	}

	/**
	 * Creates the.
	 *
	 * @param valueObject the value object
	 * @return the value object
	 */
	private ValueObject create(ValueObject valueObject) {
		// TODO Auto-generated method stub
		Object[] array = (Object[])java.lang.reflect.Array.newInstance(valueObject.getClass(), 1);
		array[0] = valueObject;
		return create((ValueObject[])array)[0];
	}


}
