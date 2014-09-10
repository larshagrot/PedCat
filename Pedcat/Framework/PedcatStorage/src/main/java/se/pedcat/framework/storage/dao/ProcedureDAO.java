package se.pedcat.framework.storage.dao;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.model.ValueObject;
import se.pedcat.framework.common.model.util.ModelException;



/**
 * The Class ProcedureDAO.
 */
public abstract class ProcedureDAO {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ProcedureDAO.class);
	
	/** The connection pool. */
	private static StorageConnectionPool connectionPool = StorageConnectionPool.getInstance(); 
	
	/** The attribute column map. */
	private Map<String,String> attributeColumnMap = new java.util.HashMap<String,String>();
	
	/** The pk attribute column map. */
	private Map<String,String> pkAttributeColumnMap = new java.util.HashMap<String,String>();;
	
	/** The table. */
	private String table;

	
	/**
	 * Instantiates a new procedure dao.
	 */
	public ProcedureDAO()
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
			logger.info("Antal " + valueObjects.length);
			logger.info("Getting connection..." + ModelUtility.getElapsed());
			connection = this.getConnection();
			logger.info("Got connection..." + ModelUtility.getElapsed());	
			
			String sql = getInsertProcedureCall();
			logger.info(sql);
			CallableStatement statement = createCallableStatement(connection,sql);
			logger.info("Created statement.." + ModelUtility.getElapsed());
			
			for(ValueObject valueObject:valueObjects)
			{
				setInsertParameters(statement,valueObject);
				logger.info("Parameters set.." + ModelUtility.getElapsed());
				statement.executeQuery();
				logger.info("Update executed.." + ModelUtility.getElapsed());
				statement.clearParameters();
				logger.info("Created object.." + ModelUtility.getElapsed());
			}
			logger.info("Commit");
			connection.commit();
			 
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Got exception", e);
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
			throw new ModelException("" , e);
		} 
		catch (ModelException e)
		{
			throw e;
		}
		catch (Throwable  e)
		{
			new ModelException("" , e);
		}
		finally
		{
			releaseConnection(connection);
		}
		
		
		
		return valueObjects;
	}
	
	/**
	 * Update.
	 *
	 * @param valueObjects the value objects
	 * @return the value object[]
	 */
	public ValueObject[] update(ValueObject[] valueObjects)
	{
		Connection connection = null;
		try {
			logger.info("Antal " + valueObjects.length);
			logger.info("Getting connection..." + ModelUtility.getElapsed());
			connection = this.getConnection();
			logger.info("Got connection..." + ModelUtility.getElapsed());	
			
			String sql = getUpdateProcedureCall();
			logger.info(sql);
			CallableStatement statement = createCallableStatement(connection,sql);
			logger.info("Created statement.." + ModelUtility.getElapsed());
			
			for(ValueObject valueObject:valueObjects)
			{
				setUpdateParameters(statement,valueObject);
				logger.info("Parameters set.." + ModelUtility.getElapsed());
				statement.executeQuery();
				logger.info("Update executed.." + ModelUtility.getElapsed());
				statement.clearParameters();
				logger.info("Created object.." + ModelUtility.getElapsed());
			}
			logger.info("Commit");
			connection.commit();
			 
		} 
		catch (SQLException e) {
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
			throw new ModelException("" , e);
		} 
		catch (ModelException e)
		{
			throw e;
		}
		catch (Throwable  e)
		{
			new ModelException("" , e);
		}
		finally
		{
			releaseConnection(connection);
		}
		
		
		
		return valueObjects;
	}

	
	/**
	 * Sets the update parameters.
	 *
	 * @param statement the statement
	 * @param valueObject the value object
	 */
	private void setUpdateParameters(CallableStatement statement,
			ValueObject valueObject) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Gets the update procedure call.
	 *
	 * @return the update procedure call
	 */
	private String getUpdateProcedureCall() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the insert procedure call.
	 *
	 * @return the insert procedure call
	 */
	public abstract String getInsertProcedureCall();

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
		return ModelUtility.getValue(key,valueObject);
	}




	

	/**
	 * Creates the callable statement.
	 *
	 * @param connection the connection
	 * @param sql the sql
	 * @return the callable statement
	 * @throws SQLException the sQL exception
	 */
	private CallableStatement createCallableStatement(Connection connection, String sql) throws SQLException {
		// TODO Auto-generated method stub
		return connection.prepareCall(sql);
	}


	/**
	 * Creates the insert statement string.
	 *
	 * @return the string
	 */
	public String createInsertStatementString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(this.getInsertProcedureName());
		//sb.append("(");
		sb.append(this.getPlaceholderlist());
		
		//sb.append(")");
		return sb.toString();
	}

	/**
	 * Gets the insert procedure name.
	 *
	 * @return the insert procedure name
	 */
	private String getInsertProcedureName() {
		// TODO Auto-generated method stub
		return ""; //EArkivStorageConfiguration.getInstance().getInsertProcedureName(this);
	}

	/**
	 * Gets the placeholderlist.
	 *
	 * @return the placeholderlist
	 */
	private Object getPlaceholderlist() {
		// TODO Auto-generated method stub
		int count = 0; //EArkivStorageConfiguration.getInstance().getInsertProcedureParameterCount(this);
		logger.info(" Parameters " + count);
		StringBuffer sb = new StringBuffer("(");
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
	private Object getInsertColumnslist() 
	{
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
	public String getTable() 
	{
		return this.table;
	}

	/**
	 * Sets the table.
	 *
	 * @param table the table to set
	 */
	public void setTable(String table) 
	{
		this.table = table;
	}

	/**
	 * Exists.
	 *
	 * @param valueObject the value object
	 * @return true, if successful
	 */
	public boolean exists(ValueObject valueObject) {
		// TODO Auto-generated method stub
		Connection connection = null;
		try 
		{
			logger.info("Getting connection...");
			connection = this.getConnection();

			logger.info("Got connection...");	
			String sql = this.getFindByKeyProcedureCall();

			logger.info(sql);
			CallableStatement statement = createCallableStatement(connection,sql);
			
			logger.info("Created statement..");
			setPKParameters(statement,valueObject);
			logger.info("Executing ....");
			ResultSet rs =  statement.executeQuery();
			
			while (rs.next())
			{
				logger.info(""+ rs.getInt(1));
				return true;
			}
			return false;
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
			throw new ModelException("" , e); 
			
		}
		catch (ModelException e)
		{
			throw e;
		}
		catch (Throwable  e)
		{
			new ModelException("" , e);
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
	 * @throws SQLException the sQL exception
	 */
	public abstract  void setPKParameters(CallableStatement statement,
			ValueObject valueObject) throws  SQLException;
	/*
	public void setPKParameters(CallableStatement statement,
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
*/
	/**
	 * Gets the find by key procedure call.
	 *
	 * @return the find by key procedure call
	 */
	public abstract String getFindByKeyProcedureCall();
	
	/**
	 * Sets the insert parameters.
	 *
	 * @param statement the statement
	 * @param valueObject the value object
	 * @throws SQLException the sQL exception
	 */
	public abstract void setInsertParameters(CallableStatement statement,ValueObject valueObject) throws SQLException;

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
			logger.info(valueObject.getObjectId() + " already exists");
		}
	}

	/**
	 * Creates the.
	 *
	 * @param valueObject the value object
	 * @return the value object
	 */
	public ValueObject create(ValueObject valueObject) {
		// TODO Auto-generated method stub
		Object[] array = (Object[])java.lang.reflect.Array.newInstance(valueObject.getClass(), 1);
		array[0] = valueObject;
		return create((ValueObject[])array)[0];
	}
	
	/**
	 * Update.
	 *
	 * @param valueObject the value object
	 * @return the value object
	 */
	public ValueObject update(ValueObject valueObject) {
		// TODO Auto-generated method stub
		Object[] array = (Object[])java.lang.reflect.Array.newInstance(valueObject.getClass(), 1);
		array[0] = valueObject;
		return update((ValueObject[])array)[0];
	}


	/**
	 * Ensure.
	 *
	 * @param valueObjects the value objects
	 */
	public void ensure(ValueObject[] valueObjects) {
		// TODO Auto-generated method stub
		for(ValueObject valueObject:valueObjects)
		{
			ensure(valueObject);
		}
	}
	
	
	
	
	

}
