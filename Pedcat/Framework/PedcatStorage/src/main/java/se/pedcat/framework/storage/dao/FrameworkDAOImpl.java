/*
 *
 * Created on den 30 januari 2001, 18:54
 */

package se.pedcat.framework.storage.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.model.util.ModelException;
import se.pedcat.framework.common.model.util.ModelTrace;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.FrameworkMessage;
import se.pedcat.framework.common.util.Log;
import se.pedcat.framework.common.util.Trace;
import se.pedcat.framework.storage.util.DAOUtility;
import se.pedcat.framework.storage.util.StorageException;


/**
 * General abstract base class for queries that uses stored procedures or
 * functions in an Oracle database Its an implementation of a DAO pattern. Sub
 * classes communicates by implementing one or more interfaces
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author lars <BR>
 * 
 * 30 03-10-31 11:45 Ulf Changed the caching mecanism after advise from
 * Lars H
 * 
 * 
 * 28 02-05-10 17:12 Larh
 * 
 * 27 02-05-08 17:46 Larh
 * 
 * 26 02-04-22 14:56 Larh
 * 
 * 
 * -->
 */
public abstract class FrameworkDAOImpl<KeyType extends Key, Type extends Carrier>
		extends FrameworkGenericBaseImpl<KeyType, Type> implements
		FrameworkDAO<KeyType, Type> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see se.signifikant.framework.storage.dao.FrameworkDAO#delete(java.util.List)
	 */

	@Override
	public Type[] findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkBaseImpl#getConnectionPoolName()
	 */
	@Override
	public String getConnectionPoolName() {
		String name=null;
		if (ApplicationContextManager.getInstance().isHasContext())
		{
			name = ApplicationContextManager.getInstance().getCurrentContext().getMessageInfo("connectionPoolName");
		}
		if (ModelUtility.isEmpty(name))
		{
			name=  super.getConnectionPoolName();
		}
		if (logger.isTraceEnabled())
		{
			logger.trace("Using pool " + name);
		}
		
		return name;
	}


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(FrameworkDAOImpl.class);

	/** Instance can hold a cached connection. */
	private java.sql.Connection cachedConnection = null;

	/**
	 * Creates new DAO.
	 *
	 * @roseuid 3AB472750258
	 */
	public FrameworkDAOImpl() {

	}

	/**
	 * Used to create instance with special connection.
	 *
	 * @param connection the connection
	 */
	public FrameworkDAOImpl(java.sql.Connection connection) {
		this.cachedConnection = connection;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#delete(java.util.List)
	 */
	@Override
	public void delete(List<KeyType> keys) {
		// TODO Auto-generated method stub
		Connection connection = this.getConnection();
		try {
			this.delete(keys, connection);
		} finally {
			this.cleanUp(null, null, connection);
		}

	}

	/**
	 * Method that sub classes must implement They are supposed to create,
	 * initiate and return a "Carrier"-object.
	 *
	 * @param resultSet the result set
	 * @return the type
	 * @throws SQLException the sQL exception
	 * @roseuid 3AB4727502EE
	 */
	abstract protected Type buildCarrier(ResultSet resultSet)
			throws java.sql.SQLException;

	/**
	 * Adds a new carrier object to a collection Stores object in cache if
	 * caching is on.
	 *
	 * @param collection the collection
	 * @param resultSet the result set
	 * @throws SQLException the sQL exception
	 */
	protected void addCarrier(Collection<Type> collection, ResultSet resultSet)
			throws java.sql.SQLException {
		try {
			Type carrier = this.buildCarrier(resultSet);
			this.setCommonProperties(carrier, resultSet);
			collection.add(carrier);
			carrier = this.storeInCache(carrier);
		} catch (SQLException e) {
			//DAOUtility.dumpColumns(resultSet);
			logger.error("Could not add carrier " + e.getMessage(),e);
			throw e;
		}

	}

	/**
	 * Returns by primary key.
	 *
	 * @param key the key
	 * @return the type
	 */
	public Type findByPrimaryKey(KeyType key) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "findByPrimaryKey(Key)",
				isVerbose(),false);
		Type carrier = null;
		Connection connection = null;
		try {
			carrier = this.getFromCache(key);
			if (carrier == null) {
				// Get Connection
				connection = getConnection();
				carrier = this.findByPrimaryKey(key, connection);
				if (carrier != null) {
					storeInCache(carrier);
					// Map map = getReadCacheMap(true);
					// map.put(carrier.getKey(), carrier);
					trace.logMessage("Found carrier in database...");
					foundInDB++;
				}

			} else {
				foundInCache++;
				trace.logMessage("Found carrier in cache...");
			}
		} catch (Exception e) {
			trace.throwFrameworkException("Could not query for object",
					FrameworkMessage.COULD_NOT_QUERY, e, key);
		} finally {
			if (trace.isVerbose())
				trace.logMessage("carrier == " + carrier);
			// Clean up connection
			cleanUp(null, null, connection);
			trace.end();
		}
		return carrier;
	}

	/**
	 * Find by primary keys.
	 *
	 * @param keys the keys
	 * @return the type[]
	 */
	public Type[] findByPrimaryKeys(KeyType[] keys) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "findByPrimaryKeys(Key)", logger
				.isTraceEnabled(),false);
		Type[] carriers = null;
		Connection connection = null;
		int index = 0;
		try {
			carriers = getFromCacheOLD(keys);
			connection = getConnection();
			for (index = 0; index < carriers.length; index++) {
				if (carriers[index] == null) {
					carriers[index] = this.findByPrimaryKey(keys[index],
							connection);
					trace.logMessage("Found carrier in database...");
				} else {
					trace.logMessage("Found carrier in cache...");
				}
			}
		} catch (Exception e) {
			trace.throwFrameworkException("Could not query for object",
					FrameworkMessage.COULD_NOT_QUERY, e, keys[index]);
		} finally {
			// Clean up connection
			cleanUp(null, null, connection);
			trace.end();
		}
		return carriers;
	}

	/**
	 * Tries to find domain value object by primary key The connection shall be
	 * opened and not null The caller is responsible for closing the connection.
	 *
	 * @param key Primary key object
	 * @param connection connection tgo be used
	 * @return found domain value object
	 * @roseuid 3AB4727503C1
	 */
	public Type findByPrimaryKey(KeyType key, Connection connection) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "findByPrimaryKey", isVerbose(),false);

		// Do the thing
		Collection<Type> collection = new ArrayList<Type>();
		Statement statement = null;
		Type carrier = null;
		ResultSet resultSet = null;
		try {

			QueryCommand<KeyType, Type> command = this
					.getQueryByPrimaryKeyCommand();
			// Get query string from command objecct
			String query = command.getQueryString();

			if (command.isCallable())
			{
				CallableStatement callableStatement; 	
				// Prepare callable statement
				statement = callableStatement = connection.prepareCall(query);
	
				command.setQueryParameters(callableStatement, key);
	
				// Execute statement
				resultSet = callableStatement.executeQuery();
			}
			else
			{
				PreparedStatement preparedStatement; 	
				// Prepare callable statement
				statement = preparedStatement = connection.prepareStatement(query);

				command.setQueryParameters(preparedStatement, key);

				// Execute statement
				resultSet = preparedStatement.executeQuery();
				
				
			}
			// Pick up resultset (cursor)
			// resultSet = (ResultSet) command.getResultSet(statement);

			// Iterate
			if (resultSet.next()) {
				// Let sub class instantiate carrier
				carrier = this.buildCarrier(resultSet);
				this.setCommonProperties(carrier, resultSet);
				carrier = this.storeInCache(carrier);
			} else {
				trace.logMessage("Object not found!");
			}
			queriedObjects += 1;
			
		} catch (Exception e) {
			trace.throwFrameworkException("Could not query for object",
					FrameworkMessage.COULD_NOT_QUERY, e, key);
		} finally {
			// Always clean up
			// Not the connection because the callee is responsible for
			// cleanup the connection
			cleanUp(resultSet, statement, null);
			trace.end();
		}
		return carrier;
	}

	/**
	 * Returns a connection Caller is responsible for cleaning up.
	 *
	 * @return the connection
	 * @roseuid 3AB47276006F
	 */
	protected Connection getConnection() {
		Trace trace = Trace.startTrace1(logger,this, "getConnection", isVerbose());

		Connection returnedConnection = null;
		try {
			// Use this.connection if it exist
			returnedConnection = this.cachedConnection;
			if (returnedConnection == null) {
				// Otherwise get one from datasource
				try {
					returnedConnection = this.getConnectionPool()
							.getConnection();
					logger.trace("Getting connection");
				} catch (SQLException e) {
					logger.error("Could not get connection", e);
					throw new StorageException(e);
				}
			} else {
				logger.trace("Returning cached connection");
			}
		} catch (Exception e) {
			trace.throwRuntimeException(e);
		}
		return returnedConnection;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#setConnection(java.sql.Connection)
	 */
	public void setConnection(java.sql.Connection connection) {
		// Log.logMessage("setConnection","setConnection");
		this.cachedConnection = connection;
	}

	/**
	 * Connection shall be opened Caller is responsible for closing the
	 * connection.
	 *
	 * @param keyList the key list
	 * @param connection the connection
	 * @roseuid 3AB472760372
	 */
	public void delete(List<KeyType> keyList, Connection connection) {
		Trace trace = Trace.startTrace1(logger,this, "delete", isVerbose());

		DeleteCommand<KeyType, Type> deleteCommand = getDeleteCommand();

		CallableStatement statement = null;

		ResultSet rs = null;

		int index = 0;
		try {
			// Get query string from command objecct
			String query = deleteCommand.getDeleteString();

			trace.logMessage(query);
			// Prepare callable statement
			statement = connection.prepareCall(query);

			for (index = 0; index < keyList.size(); index++) {
				trace.logMessage(keyList.get(index).getObjectId() + "");
				// Let command object set parameters
				deleteCommand
						.setDeleteParameters(statement, keyList.get(index));

				// Execute statement
				statement.execute();
				statement.clearParameters();
			}
			trace.logMessage("deleted " + keyList.size()
					+ " objects in database " + keyList.getClass().getName());
			removeFromCache(keyList);
			deletedObjects += counter;
		} catch (SQLException e) {
			trace.logException(e);
			trace.throwRuntimeException("Can not delete "
					+ deleteCommand.getDeleteString(), e);
		} finally {
			if (keyList != null && keyList.size() > MAX_DELETE) {
				trace.logWarning("MORE THAN " + MAX_DELETE + " DELETED "
						+ keyList.size());
			}
			// Always clean up
			cleanUp(rs, statement, null);
			trace.end();
		}
	}

	/**
	 * Gets the insert command.
	 *
	 * @return the insert command
	 * @roseuid 3AB4727700CA
	 */
	public InsertCommand<Type> getInsertCommand() {
		throw new ModelException("Insert not supported");
	}

	/**
	 * Gets the update command.
	 *
	 * @return the update command
	 * @roseuid 3AB4727700FC
	 */
	public UpdateCommand<Type> getUpdateCommand() {
		throw new ModelException("Update not supported");
	}

	/**
	 * Gets the delete command.
	 *
	 * @return the delete command
	 * @roseuid 3AB472770138
	 */
	public DeleteCommand<KeyType, Type> getDeleteCommand() {
		throw new ModelException("Delete not supported");
	}

	/**
	 * Gets the query by primary key command.
	 *
	 * @return the query by primary key command
	 * @roseuid 3AB4727700CA
	 */
	public QueryCommand<KeyType, Type> getQueryByPrimaryKeyCommand() {
		return new QueryCommandAdapter<KeyType, Type>() {
			public String getQueryString() {
				return getFindByPrimaryKeyString();
			}

		};
	}

	/**
	 * Do insert of a value object.
	 *
	 * @param carrier domain value object to be inserted
	 * @return newly created object
	 * @roseuid 3AB4727602DC
	 */
	// @SuppressWarnings("unchecked")
	public Type create(Type carrier) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "create(Carrier)", isVerbose(),false);
		Connection connection = null;
		try {
			List<Type> carrierList = new ArrayList<Type>();
			carrierList.add(carrier);
			// Get Connection
			connection = this.getConnection();
			carrierList = this.create(carrierList, connection);
			carrier = carrierList.get(0);
		} catch (Exception e) {
			trace.throwFrameworkException("Could not insert",
					FrameworkMessage.COULD_NOT_CREATE, e, carrier);
		} finally {
			cleanUp(null, null, connection);
			trace.end();
		}
		return carrier;
	}

	/**
	 * Do insert of a value object The connection shall be opened and not null
	 * The caller is responsible for closing the connection.
	 *
	 * @param carrier domain value object to be inserted
	 * @param connection connection to be used
	 * @return newly created object
	 * @roseuid 3AD53268005A
	 */
	public Type create(Type carrier, Connection connection) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "create(Carrier,Connection)",
				isVerbose(),false);
		try {
			List<Type> carrierList = new ArrayList<Type>();
			carrierList.add(carrier);
			carrierList = this.create(carrierList, connection);
			carrier = carrierList.get(0);

		} catch (Exception e) {
			trace.throwFrameworkException("Could not insert",
					FrameworkMessage.COULD_NOT_CREATE, e, carrier);
		} finally {
			trace.end();
		}
		return carrier;
	}

	/**
	 * Do bulk inserts of array with value objects.
	 *
	 * @param carriers array with domain value objects
	 * @return array with newly created objects
	 * @roseuid 3AD53268005A
	 */
	public List<Type> create(List<Type> carriers) {
		Trace trace = Trace.startTrace1(logger,this, "create(Carrier[])", isVerbose());
		Connection connection = null;
		try {
			// Get Connection
			connection = this.getConnection();
			// Do the thing...
			carriers = this.create(carriers, connection);
		} catch (Exception e) {
			trace.throwFrameworkException("Could not insert",
					FrameworkMessage.COULD_NOT_CREATE, e);
		} finally {
			cleanUp(null, null, connection);
			trace.end();
		}
		return carriers;

	}

	/**
	 * Do bulk inserts of array with value objects The connection shall be
	 * opened and not null The caller is responsible for closing the connection.
	 *
	 * @param carrierList array with domain value objects
	 * @param connection connection to be used
	 * @return array with newly created objects
	 * @roseuid 3AD53268005A
	 */
	public List<Type> create(List<Type> carrierList, Connection connection) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "create(Carrier[],Connection)",
				isVerbose(),false);

		// Test Preconditions

		InsertCommand<Type> insertCommand = getInsertCommand();
		
		
		CallableStatement statement = null;
		//PreparedStatement statement = null;
		int index = 0;
		try {
			
			carrierList = insertCommand
					.preProcessBatch(connection, carrierList);

			trace.logMessage("getting insertString "
					+ (insertCommand != null ? insertCommand.getClass()
							.getName() : "null"));
			// Get query string from command objecct
			String query = insertCommand.getInsertString();

			trace.logMessage("got insertString " + query);

			// trace.logMessage("prepare call");
			trace.logMessage("Prepare call....");
			
			
			// Prepare callable statement
			statement = connection.prepareCall(query);

			// trace.logMessage("preprocess...");

			for (index = 0; index < carrierList.size(); index++) {

				trace.logMessage("Prepare call....");
				int parameterIndex = insertCommand.preProcessCommand(statement,
						carrierList.get(index));

				trace.logMessage("set parameters...");
				// Let command object set parameters
				insertCommand.setInsertParameters(parameterIndex, statement,
						carrierList.get(index));

				trace.logMessage("executing....");
				ResultSet rs = null;
				boolean updateCount = statement.execute();
				if (!updateCount)
				{
					if (true ||statement.getUpdateCount()>0 )
					{
						if ( statement.getMoreResults())
						{
							rs = statement.getResultSet();
						
						}
						// Post process
						insertCommand.postProcessCommand(statement, rs, carrierList.get(index));
				
					}
					else
					{
						logger.error(this.getClass().getName() + " update count == 0 för "+carrierList.get(index).getObjectId());
					}
					
				}

				

				trace.logMessage(carrierList.get(index).getObjectId());
				// carriers[index] = this.storeInCache(carriers[index]);

				trace.logMessage("clear parameters....");
				statement.clearParameters();
			}
			trace.logMessage("created " + carrierList.size()
					+ " objects in database "
					+ carrierList.getClass().getName());
			createdObjects += carrierList.size();
			carrierList = insertCommand.postProcessBatch(connection,
					carrierList);
		} catch (SQLException e) {
			logger.error("FEL VID CREATE " + insertCommand.getInsertString(), e);
			logger.error(ModelUtility.dumpProperties(carrierList.get(index)));
			trace
					.throwFrameworkException("Could not insert object "
							+ insertCommand.getInsertString(),
							"se.earkiv.storage.insert.error", e, carrierList
									.get(index));
			
			//trace.dumpProperties(carrierList.get(index));
		} finally {
			// Always clean up
			cleanUp(null, statement, null);
			if (carrierList != null && carrierList.size() > MAX_CREATE) {
				trace.logWarning("MORE THAN " + MAX_CREATE + " CREATED "
						+ carrierList.size());
			}

			trace.end();
		}

		return carrierList;
	}

	/**
	 * update a value objects.
	 *
	 * @param carrier domain value objects
	 * @return newly updated object
	 * @roseuid 3AD53268005A
	 */
	public Type update(Type carrier) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "update(Carrier)", isVerbose(),false);
		Connection connection = null;
		try {
			List<Type> carrierList = new ArrayList<Type>();
			carrierList.add(carrier);
			// Get Connection
			connection = this.getConnection();

			carrierList = this.update(carrierList, connection);
			carrier = carrierList.get(0);
		} catch (Exception e) {
			trace.throwFrameworkException("Could not update",
					FrameworkMessage.COULD_NOT_UPDATE, e, carrier);
		} finally {
			cleanUp(null, null, connection);
		}
		return carrier;
	}

	/**
	 * update a value objects The connection shall be opened and not null The
	 * caller is responsible for closing the connection.
	 *
	 * @param carrier domain value object
	 * @param connection connection to be used
	 * @return newly updated object
	 * @roseuid 3AD53268005A
	 */
	public Type update(Type carrier, Connection connection) {
		// To use the general method wich takes an array we have to pass the
		// object in an array
		// Create array
		List<Type> carrierList = new ArrayList<Type>();
		carrierList.add(carrier);
		// Update
		carrierList = this.update(carrierList, connection);

		// Pick up object
		carrier = carrierList.get(0);

		// Return
		return carrier;
	}

	/**
	 * Do bulk updates of array with value objects.
	 *
	 * @param carriers array with domain value objects
	 * @return array with newly updated objects
	 * @roseuid 3AD53268005A
	 */
	public List<Type> update(List<Type> carriers) {
		Trace trace = Trace.startTrace1(logger,this, "update(Carrier[])", isVerbose());
		Connection connection = null;
		try {
			trace.logMessage("UPDATING");
			// Get Connection
			connection = this.getConnection();
			// Do the thing...
			carriers = this.update(carriers, connection);

			trace.logMessage("UPDATED");
		} catch (Exception e) {
			// trace.throwFrameworkException("Could not
			// update",FrameworkMessage.COULD_NOT_UPDATE,e,carriers);
		} finally {
			// Pass connection
			cleanUp(null, null, connection);
		}
		return carriers;

	}

	/**
	 * Do bulk updates of array with value objects The connection shall be
	 * opened and not null The caller is responsible for closing the connection.
	 *
	 * @param carriers array with domain value objects
	 * @param connection connection to be used
	 * @return array with newly updated objects
	 * @roseuid 3AD532680105
	 */
	public List<Type> update(List<Type> carriers, Connection connection) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "update", isVerbose(),false);

		CallableStatement statement = null;

		int index = 0;
		UpdateCommand<Type> updateCommand = getUpdateCommand();
		try {

			// Test Preconditions
			if (carriers == null) {
				trace.throwRuntimeException("Invalid carriers == null ");
			}
			if (connection == null) {
				trace.throwRuntimeException("Connection null or closed");
			}
			if (connection.isClosed()) {
				trace.logMessage("CONNECTION CLOSED! WORKS????");
			}
			if (updateCommand == null) {
				trace.throwRuntimeException("Update not supported");
			}

			// Get query string from command objecct
			String query = updateCommand.getUpdateString();

			// Prepare callable statement
			statement = connection.prepareCall(query);

			ResultSet rs = null;
			for (index = 0; index < carriers.size(); index++) {
				trace.logMessage("preprocess");
				int parameterIndex = updateCommand.preProcessCommand(statement,
						carriers.get(index));
				// Let command object set parameters
				trace.logMessage("set update params");
				updateCommand.setUpdateParameters(parameterIndex, statement,
						carriers.get(index));

				trace.logMessage("execute");
				boolean updateCount = statement.execute();
				if (!updateCount)
				{
					if (true || statement.getUpdateCount()>0 )
					{
						if ( statement.getMoreResults())
						{
							rs = statement.getResultSet();
						
						}
						// Post process
						updateCommand.postProcessCommand(statement, rs, carriers.get(index));
				
					}
					else
					{
						logger.error(this.getClass().getName() + " update count == 0 för "+carriers.get(index).getObjectId());
					}
					
				}
				
				statement.clearParameters();
			}
			trace.logMessage("updated " + carriers.size()
					+ " objects in database " + carriers.getClass().getName());
			updatedObjects += carriers.size();
		} catch (SQLException e) {
			
			trace.throwFrameworkException("Could not update object " + index
					+ " " + updateCommand.getUpdateString(),
					FrameworkMessage.COULD_NOT_UPDATE, e, (Type[]) null);
		} finally {
			if (carriers != null && carriers.size() > MAX_UPDATE) {
				trace.logWarning("MORE THAN " + MAX_UPDATE + " UPDATED "
						+ carriers.size());
			}
			// Always clean up
			cleanUp(null, statement, null);
			trace.end();
		}
		return carriers;
	}

	/**
	 * Delete.
	 *
	 * @param domainKey the domain key
	 * @roseuid 3AD53268014B
	 */
	public void delete(KeyType domainKey) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "delete(Key)", isVerbose(),false);
		Connection connection = null;
		try {
			// Get Connection
			connection = this.getConnection();

			// Do the thing...
			this.delete(domainKey, connection);
		} catch (Exception e) {
			trace.throwFrameworkException("Could not delete object",
					FrameworkMessage.COULD_NOT_DELETE, e, domainKey);
		} finally {
			// Pass connection
			cleanUp(null, null, connection);
		}
	}

	/**
	 * Delete.
	 *
	 * @param domainKey the domain key
	 * @param connection the connection
	 */
	public void delete(KeyType domainKey, Connection connection) {
		List<KeyType> keyList = new ArrayList<KeyType>();
		keyList.add(domainKey);
		// Do the thing...
		this.delete(keyList, connection);

	}

	/**
	 * Delete.
	 *
	 * @param keys the keys
	 */
	public void delete(KeyType[] keys) {
		ModelTrace trace = ModelTrace.startTrace(logger,this, null, "delete(Key[])", isVerbose(),false);
		Connection connection = null;
		try {
			// Get Connection
			connection = this.getConnection();
			List<KeyType> keyList = new ArrayList<KeyType>();
			keyList.addAll(Arrays.asList(keys));
			// Do the thing...
			this.delete(keyList, connection);
		} catch (Exception e) {
			trace.throwFrameworkException("Could not delete objects",
					FrameworkMessage.COULD_NOT_DELETE, e, keys[0]);
		} finally {
			// Pass connection
			cleanUp(null, null, connection);
		}
	}

	/**
	 * Delete.
	 *
	 * @param carrier the carrier
	 * @roseuid 3AD53268019B
	 */
	public void delete(Type carrier) {
		this.delete((KeyType) carrier.getKey());
	}

	/**
	 * Delete.
	 *
	 * @param carriers the carriers
	 * @roseuid 3AD5326801E1
	 */
	public void delete(Type[] carriers) {
		Connection connection = null;
		try {
			// Get Connection
			connection = this.getConnection();

			// Do the thing...
			this.delete(carriers, connection);
		} finally {
			// Pass connection
			cleanUp(null, null, connection);
		}
	}

	/**
	 * Delete.
	 *
	 * @param carriers the carriers
	 * @param connection the connection
	 */
	public void delete(Type[] carriers, Connection connection) {
		List<KeyType> keyList = new ArrayList<KeyType>();

		for (int index = 0; index < carriers.length; index++) {
			keyList.add((KeyType) carriers[index].getKey());
		}
		this.delete(keyList, connection);
	}

	/**
	 * Delete.
	 *
	 * @param carrier the carrier
	 * @param connection the connection
	 */
	public void delete(Type carrier, Connection connection) {

		this.delete((KeyType) carrier.getKey(), connection);
	}

	/**
	 * Gets the find by primary key string.
	 *
	 * @return the find by primary key string
	 * @roseuid 3AD5326802A9
	 */
	protected String getFindByPrimaryKeyString() {
		
		throw new ModelException("FindByPrimaryKey not supported " + this.getClass().getName());
	}

	/**
	 * Find by query command.
	 *
	 * @param queryCommand the query command
	 * @return the collection
	 */
	public Collection<Type> findByQueryCommand(
			QueryCommand<KeyType, Type> queryCommand) {
		Connection connection = null;
		Collection<Type> collection = null;
		try {
			// Get Connection
			connection = this.getConnection();
			// Do the thing...
			collection = this.findByQueryCommand(queryCommand, connection);
		} finally {
			cleanUp(null, null, connection);
		}
		return collection;

	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#executeCommand(se.pedcat.framework.storage.dao.SQLCommand)
	 */
	public void executeCommand(SQLCommand sqlCommand) {
		Connection connection = null;

		try {
			// Get Connection
			connection = this.getConnection();
			// Do the thing...
			executeCommand(sqlCommand, connection);
		} finally {
			cleanUp(null, null, connection);
		}

	}

	/**
	 * Execute command.
	 *
	 * @param sqlCommand the sql command
	 * @param connection the connection
	 */
	public void executeCommand(SQLCommand sqlCommand, Connection connection) {
		Trace trace = Trace.startTrace1(logger,this, "executeCommand", isVerbose());

		CallableStatement statement = null;
		ResultSet rs = null;
		try {
			// Get query string from command objecct
			String query = sqlCommand.getQueryString();

			trace.logMessage(query);

			trace.logMessage("prepareCall");

			// Prepare callable statement
			statement = connection.prepareCall(query);

			trace.logMessage("setQueryParameters");

			// Let command object set parameters
			sqlCommand.setParameters(statement);

			trace.logMessage("execute...");

			// Execute statement
			if (!statement.execute())
			{
				statement.getUpdateCount();
			}
			sqlCommand.postProcess(statement);

		} catch (Exception e) {
			trace.throwRuntimeException("Can not query for objects", e);
		} finally {
			// Always clean up
			cleanUp(rs, statement, null);
			trace.end();
		}
	}

	/**
	 * Find by query command.
	 *
	 * @param queryCommand the query command
	 * @param connection the connection
	 * @return the collection
	 */
	public Collection<Type> findByQueryCommand(
			QueryCommand<KeyType, Type> queryCommand, Connection connection) {
		Trace trace = Trace.startTrace1(logger,this, "findByQueryCommand", isVerbose());
		Collection<Type> collection = new ArrayList<Type>();
		ResultSet rs = null;
		Statement statement = null;
		try {

			if (queryCommand.isCallable()) {
				CallableStatement callableStatement = null;

				// Get query string from command objecct
				String query = queryCommand.getQueryString();

				trace.logMessage(query);

				trace.logMessage("prepareCall");

				statement = callableStatement = connection.prepareCall(query);
				trace.logMessage("setQueryParameters");

				// Let command object set parameters
				queryCommand.setQueryParameters(callableStatement);

				trace.logMessage("execute...");

				// Execute statement
				rs =  callableStatement.executeQuery();
				
				if (rs==null)
				{
					// Pick up resultset (cursor)
					rs = queryCommand.getResultSet(callableStatement);
				}
				

				int counter = 0;

				if (queryCommand.isCarrierbuilder()) {
					collection.addAll(queryCommand.buildCarriers(rs));
					collection = storeInCache(collection);
				} else {
					// Yes we want all
					// Iterate as long there is rows
					while (rs!=null && rs.next()) {
						counter++;
						// Let sub class instantiate info object and add to
						// collection
						addCarrier(collection, rs);
					}
				}
				trace.logMessage(query + " " + counter
						+ " objects from database");
				queriedObjects += counter;
			} else {

				PreparedStatement preparedStatement = null;

				// Get query string from command objecct
				String query = queryCommand.getQueryString();

				trace.logMessage(query);

				trace.logMessage("prepareCall");

				statement = preparedStatement = connection
						.prepareStatement(query);
				trace.logMessage("setQueryParameters");

				// Let command object set parameters
				queryCommand.setQueryParameters(preparedStatement);

				trace.logMessage("execute...");

				// Execute statement
				boolean count = preparedStatement.execute();

				if (!count)
				{
					preparedStatement.getUpdateCount();
				}
				//if (preparedStatement.getMoreResults()) {
					// Pick up resultset (cursor)
					rs = queryCommand.getResultSet(preparedStatement);
				//}

				int counter = 0;

				if (queryCommand.isCarrierbuilder()) {
					collection.addAll(queryCommand.buildCarriers(rs));
					collection = storeInCache(collection);
				} else {
					// Yes we want all
					// Iterate as long there is rows
					while (rs!=null && rs.next()) {
						counter++;
						// Let sub class instantiate info object and add to
						// collection
						addCarrier(collection, rs);
					}
				}
				trace.logMessage(query + " " + counter
						+ " objects from database");
				queriedObjects += counter;
			}
		} catch (Exception e) {
			trace.throwRuntimeException("Can not query for objects "
					+ queryCommand.getQueryString(), e);
		} finally {
			if (collection != null && collection.size() > MAX_READ) {
				trace.logWarning("MORE THAN " + MAX_READ + " READ "
						+ collection.size());
			}
			// Always clean up
			cleanUp(rs, statement, null);
			trace.end();
		}
		return collection;
	}

	/**
	 * Clean up.
	 *
	 * @param rs the rs
	 * @param statement the statement
	 * @param connection the connection
	 */
	public void cleanUp(ResultSet rs, Statement statement, Connection connection) {
		try {
			// Do not close a cached connection
			if (this.cachedConnection == null && connection != null) {
				// Log.logMessage("cleanUp","Closing connection");
				// Utility.cleanUp(rs,statement,
				// cachedConnection!=null?null:connection);
				DAOUtility.cleanUp(this.getConnectionPoolName(), rs, statement,
						connection);
				// Utility.cleanUp(rs,statement, null);
			} else {
				DAOUtility.cleanUp(this.getConnectionPoolName(), rs, statement,
						null);
			}
		} catch (Exception e) {
			throw new ModelException("Could not clean up connection");
		}
	}

	/**
	 * Uses oracle specific type.
	 *
	 * @param index the index
	 * @param statement the statement
	 * @throws SQLException the sQL exception
	 */
	public static void registerCursorOutParameter(int index,
			CallableStatement statement) throws java.sql.SQLException {
		DAOUtility.registerCursorOutParameter(index, statement);
	}

	/**
	 * Sets the common properties.
	 *
	 * @param carrier the carrier
	 * @param resultSet the result set
	 * @throws SQLException the sQL exception
	 */
	public void setCommonProperties(Type carrier, ResultSet resultSet)
			throws java.sql.SQLException {
		if (this.isHasCommonProperties()) {
			carrier.setSkapadDatum(resultSet.getTimestamp("skapad_datum"));
			carrier.setAndradAv(resultSet.getString("andrad_av"));
			carrier.setAndradDatum(resultSet.getTimestamp("andrad_datum"));
			carrier.setSkapadAv(resultSet.getString("skapad_av"));
		}
	}

	/**
	 * Checks if is checks for common properties.
	 *
	 * @return true, if is checks for common properties
	 */
	protected boolean isHasCommonProperties() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * The Class FindByKeyQueryCommand.
	 */
	protected class FindByKeyQueryCommand extends
			QueryCommandAdapter<KeyType, Type> {
		
		/** The query string. */
		private String queryString;
		
		/** The key. */
		private KeyType key;

		/**
		 * Instantiates a new find by key query command.
		 *
		 * @param key the key
		 * @param queryString the query string
		 */
		public FindByKeyQueryCommand(KeyType key, String queryString) {
			this.queryString = queryString;
			this.key = key;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#getQueryString()
		 */
		public String getQueryString() {
			return this.queryString;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#setQueryParameters(java.sql.CallableStatement)
		 */
		public void setQueryParameters(CallableStatement statement)
				throws SQLException {
			super.setQueryParameters(statement, this.key);
		}
	}

	/**
	 * The Class FindByStringQueryCommand.
	 */
	protected class FindByStringQueryCommand extends
			QueryCommandAdapter<KeyType, Type> {
		
		/** The query string. */
		private String queryString;
		
		/** The string. */
		private String string;

		/**
		 * Instantiates a new find by string query command.
		 *
		 * @param string the string
		 * @param queryString the query string
		 */
		public FindByStringQueryCommand(String string, String queryString) {
			this.queryString = queryString;
			this.string = string;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#getQueryString()
		 */
		public String getQueryString() {
			return this.queryString;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#setQueryParameters(java.sql.CallableStatement)
		 */
		public void setQueryParameters(CallableStatement statement)
				throws SQLException {
			// registerCursorOutParameter(1,statement);
			statement.setString(1, string);
		}
	}

	/**
	 * The Class DeleteByStringCommand.
	 */
	protected class DeleteByStringCommand extends
			DeleteCommandAdapter<KeyType, Type> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * se.signifikant.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java
		 * .sql.CallableStatement, se.pedcat.framework.common.model.Key)
		 */

		/** The delete string. */
		private String deleteString;
		
		/** The string. */
		private String string;

		/**
		 * Instantiates a new delete by string command.
		 *
		 * @param string the string
		 * @param queryString the query string
		 */
		public DeleteByStringCommand(String string, String queryString) {
			this.deleteString = queryString;
			this.string = string;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java.sql.CallableStatement)
		 */
		@Override
		public void setDeleteParameters(CallableStatement statement)
				throws SQLException {
			statement.setString(1, string);
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#getDeleteString()
		 */
		@Override
		public String getDeleteString() {
			// TODO Auto-generated method stub
			return deleteString;
		}
	}

	/**
	 * The Class DeleteByIntCommand.
	 */
	protected class DeleteByIntCommand extends
			DeleteCommandAdapter<KeyType, Type> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * se.signifikant.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java
		 * .sql.CallableStatement, se.pedcat.framework.common.model.Key)
		 */

		/** The delete string. */
		private String deleteString;
		
		/** The value. */
		private int value;

		/**
		 * Instantiates a new delete by int command.
		 *
		 * @param value the value
		 * @param queryString the query string
		 */
		public DeleteByIntCommand(int value, String queryString) {
			this.deleteString = queryString;
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java.sql.CallableStatement)
		 */
		@Override
		public void setDeleteParameters(CallableStatement statement)
				throws SQLException {
			statement.setInt(1, value);
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#getDeleteString()
		 */
		@Override
		public String getDeleteString() {
			// TODO Auto-generated method stub
			return deleteString;
		}
	}
	
	/**
	 * The Class DeleteByIntsCommand.
	 */
	protected class DeleteByIntsCommand extends
	DeleteCommandAdapter<KeyType, Type> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * se.signifikant.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java
		 * .sql.CallableStatement, se.pedcat.framework.common.model.Key)
		 */
		
		/** The delete string. */
		private String deleteString;
		
		/** The value. */
		private int[] value;
		
		/**
		 * Instantiates a new delete by ints command.
		 *
		 * @param value the value
		 * @param queryString the query string
		 */
		public DeleteByIntsCommand(int[] value, String queryString) {
			this.deleteString = queryString;
			this.value = value;
		}
		
		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#setDeleteParameters(java.sql.CallableStatement)
		 */
		@Override
		public void setDeleteParameters(CallableStatement statement)
				throws SQLException {
			for(int i=0;i<value.length;i++)
			{
				statement.setInt(i+1, value[i]);
			}
		}
		
		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.DeleteCommandAdapter#getDeleteString()
		 */
		@Override
		public String getDeleteString() {
			// TODO Auto-generated method stub
			return deleteString;
		}
	}

	
	

	/**
	 * The Class FindByIntQueryCommand.
	 */
	protected class FindByIntQueryCommand extends
			QueryCommandAdapter<KeyType, Type> {
		
		/** The query string. */
		private String queryString;
		
		/** The int param. */
		private int intParam;

		/**
		 * Instantiates a new find by int query command.
		 *
		 * @param intParam the int param
		 * @param queryString the query string
		 */
		public FindByIntQueryCommand(int intParam, String queryString) {
			this.queryString = queryString;
			this.intParam = intParam;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#getQueryString()
		 */
		public String getQueryString() {
			return this.queryString;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#setQueryParameters(java.sql.CallableStatement)
		 */
		@Override
		public void setQueryParameters(CallableStatement statement)
				throws SQLException {
			int index = 1;
			// registerCursorOutParameter(index++,statement);
			statement.setInt(index++, this.intParam);
		}
	}

	/**
	 * The Class FindWithoutParametersQueryCommand.
	 */
	protected class FindWithoutParametersQueryCommand extends
			QueryCommandAdapter<KeyType, Type> {
		
		/** The query string. */
		private String queryString;

		/**
		 * Instantiates a new find without parameters query command.
		 *
		 * @param queryString the query string
		 */
		public FindWithoutParametersQueryCommand(String queryString) {
			this.queryString = queryString;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#getQueryString()
		 */
		public String getQueryString() {
			return this.queryString;
		}

		/* (non-Javadoc)
		 * @see se.pedcat.framework.storage.dao.QueryCommandAdapter#setQueryParameters(java.sql.CallableStatement)
		 */
		public void setQueryParameters(CallableStatement statement)
				throws SQLException {
			registerCursorOutParameter(1, statement);
		}

	}

	/**
	 * Find by key.
	 *
	 * @param key the key
	 * @param queryString the query string
	 * @return the collection
	 */
	public Collection<Type> findByKey(KeyType key, String queryString) {
		return this.findByQueryCommand(new FindByKeyQueryCommand(key,
				queryString));
	}

	/**
	 * Find by string.
	 *
	 * @param string the string
	 * @param queryString the query string
	 * @return the collection
	 */
	public Collection<Type> findByString(String string, String queryString) {
		return this.findByQueryCommand(new FindByStringQueryCommand(string,
				queryString));
	}

	/**
	 * Find by int.
	 *
	 * @param intParam the int param
	 * @param queryString the query string
	 * @return the collection
	 */
	public Collection<Type> findByInt(int intParam, String queryString) {
		return this.findByQueryCommand(new FindByIntQueryCommand(intParam,
				queryString));
	}

	/**
	 * Find without parameters.
	 *
	 * @param queryString the query string
	 * @return the collection
	 */
	public Collection<Type> findWithoutParameters(String queryString) {
		return this.findByQueryCommand(new FindWithoutParametersQueryCommand(
				queryString));
	}

	/**
	 * Find by query.
	 *
	 * @param query the query
	 * @return the type[]
	 * @roseuid 3AB47270012E
	 */
	public Type[] findByQuery(ModelQuery query) {
		Log.logWarning(logger,"DAOImpl.findByQuery", "THIS SHOUL NEVER BE CALLED");
		return null;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#countByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	public int countByQuery(ModelQuery query) {
		return findByQuery(query).length;
	}

	/**
	 * Gets the statistics.
	 *
	 * @return the statistics
	 */
	public static String getStatistics() {
		StringBuffer sb = new StringBuffer();
		sb.append("DAOImpl.readMapCache used " + readMapCache.used() + " free"
				+ readMapCache.free() + "\n");
		sb.append("Created      " + createdObjects + "\n");
		sb.append("Updated      " + updatedObjects + "\n");
		sb.append("Deleted      " + deletedObjects + "\n");
		sb.append("Queried      " + queriedObjects + "\n");
		sb.append("FoundInCache " + foundInCache + "\n");
		sb.append("FoundInDB    " + foundInDB + "\n");
		return sb.toString();
	}

	/*
	protected void finalize() throws Throwable {
		super.finalize();
		counter--;
	}*/

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public static int getCount() {
		return counter;
	}

	/**
	 * Gets the package name.
	 *
	 * @return the package name
	 */
	public String getPackageName() {
		return getSchemeName();// Configuration.getProperty("pck_product","pck_product");
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(Type[])
	 */
	@Override
	public Type[] ensure(Type[] carriers) {
		// TODO Auto-generated method stub
		for (Type type : carriers) {
			type = ensure(type);
		}
		return carriers;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public Type ensure(Type carrier) {

		Type answer = null;
		if (!carrier.isNew()) {
			answer = this.findByPrimaryKey((KeyType) carrier.getKey());
		}
		if (answer != null) {

			if (logger.isTraceEnabled()) {
				logger.trace("Found " + carrier.getObjectId() + " "
						+ carrier.getClass().getName());
			}
			return answer;
		} else {
			logger.warn("Not found  " + carrier.getObjectId() + " "
					+ carrier.getClass().getName());
			return this.create(carrier);
		}
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#ensure(java.util.List)
	 */
	@Override
	public List<Type> ensure(List<Type> carriers) {
		// TODO Auto-generated method stub
		for (Type type : carriers) {
			type = ensure(type);
		}
		return carriers;
	}

	/**
	 * Sets the string or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setStringOrNull(PreparedStatement statement, int index,
			String value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			statement.setNull(index, Types.VARCHAR);
		} else {
			statement.setString(index, value);
		}
	}

	/**
	 * Sets the int or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param key the key
	 * @throws SQLException the sQL exception
	 */
	public void setIntOrNull(CallableStatement statement, int index, Key key)
			throws SQLException {
		// TODO Auto-generated method stub
		if (key == null || key.isNull()) {
			setIntOrNull(statement, index, (Integer) null);
		} else {
			setIntOrNull(statement, index, new Integer(key.getObjectIdAsInt()));

		}
	}

	/**
	 * Sets the int or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setIntOrNull(CallableStatement statement, int index,
			Integer value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null || value.intValue() <= 0) {
			statement.setNull(index, Types.INTEGER);
		} else {
			statement.setInt(index, value);
		}
	}
	
	/**
	 * Sets the integer.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setInteger(CallableStatement statement, int index,
			Integer value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			statement.setNull(index, Types.INTEGER);
		} else {
			statement.setInt(index, value);
		}
	}

	/**
	 * Sets the boolean or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setBooleanOrNull(CallableStatement statement, int index,
			Boolean value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			statement.setBoolean(index, new Boolean(false));
			// statement.setNull(index, Types.BOOLEAN);
		} else {
			statement.setBoolean(index, value);
		}
	}

	/**
	 * Sets the int or null.
	 *
	 * @param statement the statement
	 * @param i the i
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setIntOrNull(CallableStatement statement, int i, Boolean value)
			throws SQLException {
		this.setIntOrNull(statement, i,
				(value == null ? null : (value ? 1 : 0)));

	}

	/**
	 * Sets the long or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setLongOrNull(CallableStatement statement, int index,
			Long value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null || value.intValue() <= 0) {
			statement.setNull(index, Types.INTEGER);
		} else {
			statement.setLong(index, value);
		}
	}

	/**
	 * Sets the datetime or null.
	 *
	 * @param statement the statement
	 * @param index the index
	 * @param value the value
	 * @throws SQLException the sQL exception
	 */
	protected void setDatetimeOrNull(CallableStatement statement, int index,
			Date value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			statement.setNull(index, Types.TIMESTAMP);
		} else {
			DAOUtility.setDateParameter(statement, index, value);
		}
	}

	/**
	 * Gets the int or null.
	 *
	 * @param value the value
	 * @return the int or null
	 */
	protected Integer getIntOrNull(int value) {
		// TODO Auto-generated method stub
		if (value <= 0) {
			return null;
		} else {
			return value;
		}
	}

	/**
	 * Builds the carriers.
	 *
	 * @param collection the collection
	 * @param resultSet the result set
	 * @throws SQLException the sQL exception
	 */
	public void buildCarriers(Collection<Type> collection, ResultSet resultSet)
			throws SQLException {
		while (resultSet.next()) {
			collection.add(this.buildCarrier(resultSet));
		}

	}

	/**
	 * Delete by string.
	 *
	 * @param parameter the parameter
	 * @param string the string
	 */
	public void deleteByString(String parameter, String string) {
		// TODO Auto-generated method stub
		Connection connection = this.getConnection();

		DeleteByStringCommand deleteCommand = new DeleteByStringCommand(
				parameter, string);
		CallableStatement statement = null;

		ResultSet rs = null;

		try {
			// Get query string from command objecct
			String query = deleteCommand.getDeleteString();

			// Prepare callable statement
			statement = connection.prepareCall(query);

			// Let command object set parameters
			deleteCommand.setDeleteParameters(statement);

			// Execute statement
			statement.execute();
			this.flushCache();

		} catch (SQLException e) {
			throw new FrameworkException(e);
			//ModelUtility.ensureFrameworkException(e.getMessage(), e,
			//		FrameworkMessage.COULD_NOT_DELETE);

		} finally {
			cleanUp(rs, statement, connection);

		}

	}

	
	/**
	 * Delete by int.
	 *
	 * @param value the value
	 * @param statementString the statement string
	 * @return the int
	 */
	public int deleteByInt(int value,String statementString)
	{
		return this.deleteByInts(new int[]{value}, statementString);
	}
	
	/**
	 * Delete by ints.
	 *
	 * @param value the value
	 * @param statementString the statement string
	 * @return the int
	 */
	public int deleteByInts(int[] value,  String statementString) {
		// TODO Auto-generated method stub
		
		int rc = -1;
		Connection connection = this.getConnection();

		DeleteByIntsCommand deleteCommand = new DeleteByIntsCommand(value,
				statementString);
		CallableStatement statement = null;

		ResultSet rs = null;

		try {
			// Get query string from command objecct
			String query = deleteCommand.getDeleteString();

			// Prepare callable statement
			statement = connection.prepareCall(query);

			// Let command object set parameters
			deleteCommand.setDeleteParameters(statement);

			// Execute statement
			if (!statement.execute())
			{
				rc = statement.getUpdateCount();
			}
			else
			{
				
					
			}
			this.flushCache();

		} catch (SQLException e) {
			throw new FrameworkException(e);
			/*ModelUtility.ensureFrameworkException(e.getMessage(), e,
					FrameworkMessage.COULD_NOT_DELETE);
*/
		} finally {
			cleanUp(rs, statement, connection);

		}
		return rc;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#updateStatistics()
	 */
	public void updateStatistics() 
	{
		
	}
	
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.FrameworkDAO#findKeysByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	public <KeyType2> KeyType2[] findKeysByQuery(ModelQuery q)
	{
		throw new FrameworkException("findKeysByQuery not implemented by " + this.getClass().getName());
	}

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433;databaseName=r7earkiv_db_mellanarkiv","r7earkiv_login","r7earkiv_login");
				
				CallableStatement stmt = conn.prepareCall("{ call SP_LogginfoFindByQuery(?,?,?,?,?,?)}");
				stmt.setInt(1,6);
				stmt.setInt(2,1);
				stmt.setInt(3,9);
				stmt.setNull(4,Types.VARCHAR);
				stmt.setNull(5,Types.VARCHAR);
				stmt.setNull(6,Types.VARCHAR);
				ResultSet rs = stmt.executeQuery();
				
				System.out.println("false " + stmt.getMoreResults() + " " + (rs!=null));
				
				
				
				
				
				
				

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
		

}
