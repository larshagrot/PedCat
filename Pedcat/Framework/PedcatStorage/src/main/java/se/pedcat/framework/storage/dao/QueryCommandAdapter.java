/*
 * QueryCommandAdapter.java
 *
 * Created on den 16 april 2001, 22:24
 */

package se.pedcat.framework.storage.dao;
import java.sql.*;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;



/**
 * Default implementation of the QueryCommand interface.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author  larh
 * <BR>
 */
public abstract class QueryCommandAdapter<KeyType extends Key,Type extends Carrier> extends DefaultQueryCommandAdapter<KeyType,Type> 
{
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.storage.dao.QueryCommand#setQueryParameters(java.sql.PreparedStatement, se.pedcat.framework.common.model.Key)
     */
    @Override
	public void setQueryParameters(PreparedStatement statement, KeyType key)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.QueryCommand#getResultSet(java.sql.PreparedStatement)
	 */
	@Override
	public ResultSet getResultSet(PreparedStatement statement)
			throws SQLException {
		ResultSet rs =       statement.getResultSet();
    	if (rs==null)
    	{
    		if (statement.getMoreResults())
    		{
    			rs = statement.getResultSet();
    		}
    	}
    	return rs;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.QueryCommand#setQueryParameters(java.sql.PreparedStatement)
	 */
	@Override
	public void setQueryParameters(PreparedStatement statement) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	/** The DEFAUL t_ maxrows. */
	public final int DEFAULT_MAXROWS = 10000000;
    
    /** The RESULTSE t_ index. */
    public final int RESULTSET_INDEX = 1;
    
    /** The start index. */
    private long startIndex = 1;
    
    /** The stop index. */
    private long stopIndex  = DEFAULT_MAXROWS;

    
    
    /**
     * Creates new QueryCommandAdapter.
     */
    public QueryCommandAdapter() 
    {
    
    }

    /**
     * Call back method
     * The implementer is supposed to set parameters that are implied by the query string.
     *
     * @param statement the new query parameters
     * @throws SQLException the sQL exception
     * @roseuid 3AB4727801C6
     */
    public void setQueryParameters(java.sql.CallableStatement statement) throws java.sql.SQLException
    {
    
    }
    
    /**
     * Call back method
     * The implementer is supposed to set parameters that are implied by the query string.
     *
     * @param statement the statement
     * @param key the key
     * @throws SQLException the sQL exception
     * @roseuid 3AB4727801C6
     */
    public void setQueryParameters(java.sql.CallableStatement statement,KeyType key) throws java.sql.SQLException 
    {
        //FrameworkDAOImpl.registerCursorOutParameter(1,statement);
        //statement.setInt(2,key.getHomeId());
    	if (key.isString())
    	{
    		statement.setString(1,key.getObjectIdAsString());
    	}
    	else
    	{
    		statement.setInt(1,key.getObjectIdAsInt());
    	}
    }
    
    
    /**
     * Gets the query string.
     *
     * @return a jdbc query string for a callable statement
     * @roseuid 3AB47278020C
     */
    public String getQueryString() 
    {
        return "THIS ONE SHOULD NEVER BE CALLED!!!!!";
    }
    
    
    
    /**
     * Picks up the resultset after a executed query
     * Assumes that the first parameter is the returned Cursor.
     *
     * @param statement the statement
     * @return the result set
     * @throws SQLException the sQL exception
     */
    public ResultSet getResultSet(java.sql.CallableStatement statement) throws java.sql.SQLException
    {
    	ResultSet rs =       statement.getResultSet();
    	if (rs==null)
    	{
    		if (statement.getMoreResults())
    		{
    			rs =       statement.getResultSet();
    		}
    	}
    	return rs;
    }
    
    /**
     * Returns last index (row) wich shall be a part of the answer.
     *
     * @return the stop index
     */
    public long getStopIndex() 
    {
        return stopIndex;
    }
    
    /**
     * Returns first index (row) wich shall be a part of the answer.
     *
     * @return the start index
     */
   public  long getStartIndex()
   {
        return startIndex;
   }
   
   /**
    * Returns true if all rows shall be a part of the answer.
    *
    * @return true, if is all objects
    */
   public boolean isAllObjects()
   {
        return (this.getStopIndex() - this.getStartIndex()) == DEFAULT_MAXROWS;
   }
    
    /**
     * Returns true if an index (row) shall be a part of the answer.
     *
     * @param index the index
     * @return true, if successful
     */
   public boolean addable(int index) 
   {
        return index >= this.getStartIndex() && index<=this.getStopIndex();
   }
   
    /**
     * Returns true if an index (row) is lower than stop index.
     *
     * @param index the index
     * @return true, if successful
     */ 
   public boolean checkIndex(int index) 
   {
        return index <= this.getStopIndex();
   }
   
    
   
   
}
