/**
 *
 *
 * InsertCommandAdapter.java
 *
 * Created on den 16 april 2001, 22:17
 */

package se.pedcat.framework.storage.dao;
import java.sql.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.storage.util.DAOUtility;


/**
 * Default implementation of the InsertCommand interface.
 *
 * @param <Type> the generic type
 * @author  larh
 * <BR>
 */
public abstract class InsertCommandAdapter<Type extends Carrier> extends java.lang.Object implements InsertCommand<Type> {

	
    /* (non-Javadoc)
	 * @see se.signifikant.framework.storage.dao.InsertCommand#postProcessBatch(Type[])
	 */
	@Override
	public List<Type> postProcessBatch(Connection connection,List<Type> types) throws SQLException {
		// TODO Auto-generated method stub
		return types;
	}
 
	/* (non-Javadoc)
	 * @see se.signifikant.framework.storage.dao.InsertCommand#preProcessBatch(Type[])
	 */
	@Override
	public List<Type> preProcessBatch(Connection connection,List<Type> types) throws SQLException {
		// TODO Auto-generated method stub
		return types;
	}

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(InsertCommandAdapter.class);
	
    /** The user name. */
    private String userName = UserCache.getUserName(null);
    
    /**
     * Creates new InsertCommandAdapter.
     */
    public InsertCommandAdapter() 
    {
    	
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.storage.dao.InsertCommand#preProcessCommand(java.sql.CallableStatement, java.lang.Object)
     */
    @Override    
    public int preProcessCommand(java.sql.CallableStatement statement,Type carrier) throws java.sql.SQLException
    {
        
        int index = 1;
        statement.registerOutParameter(index++,java.sql.Types.INTEGER);
        statement.setNull(index-1,Types.INTEGER);
        statement.registerOutParameter(index++,java.sql.Types.TIMESTAMP);
        statement.setNull(index-1,Types.TIMESTAMP);
       
        DAOUtility.setStringOrNull(statement,index++,userName);
        return index;
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.storage.dao.InsertCommand#postProcessCommand(java.sql.CallableStatement, java.sql.ResultSet, java.lang.Object)
     */
    @Override
    public void postProcessCommand(java.sql.CallableStatement statement, ResultSet rs,Type carrier) throws java.sql.SQLException
    {
        int index=1;  
        Object object = statement.getObject(index++);
        if (object instanceof Number)
        {
            carrier.setObjectId(((Number) object).intValue());
        }
        else
        if (object instanceof String)
        {
            carrier.setObjectId((String) object);
        }
        else
        {
        	if (object!=null)
        	{
        		try
        		{
        			carrier.setObjectId((String) object.toString());
        			logger.warn("Not a String nor a number..." + object.getClass().getName());
        		}
        		catch(Exception e)
        		{
        			logger.error("Could not convert to string " + e);
        		}
        	}
        	else
        	{
        		logger.warn("Output parameter is null");
        	}
        	
        }
            
        carrier.setSkapadDatum(DAOUtility.fromTimestampToDate( statement.getTimestamp(index++)));
        carrier.setAndradDatum(carrier.getSkapadDatum());
        carrier.setSkapadAv(userName);
        carrier.setAndradAv(userName);
       
    }
    
    /**
     * Post process command x.
     *
     * @param statement the statement
     * @param rs the rs
     * @param carrier the carrier
     * @throws SQLException the sQL exception
     */
    public void postProcessCommandX(java.sql.CallableStatement statement,ResultSet rs,Type carrier) throws java.sql.SQLException
    {
    	
    	if (rs.next())
    	{
            int index=1;  
            ResultSetMetaData md = rs.getMetaData();
            if (md.getColumnType(index) == Types.VARCHAR)
            {
                carrier.setObjectId(rs.getString(index++));
            }
            else
            {
                carrier.setObjectId(rs.getInt(index++));
            }
            carrier.setSkapadDatum(DAOUtility.fromTimestampToDate( rs.getTimestamp(index++)));
            carrier.setAndradDatum(carrier.getSkapadDatum());
            carrier.setSkapadAv(userName);
            carrier.setAndradAv(userName);
    	}
        else
        {
            logger.warn("INGA RADER I RESULTATSET!!!!!");
        }
        
    }
    
   



    
   
    
}
