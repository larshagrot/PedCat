/*
 * UpdateCommandAdapter.java
 *
 * Created on den 16 april 2001, 22:17
 */

package se.pedcat.framework.storage.dao;


import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.storage.util.DAOUtility;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Default implementation of the UpdateCommand interface.
 *
 * @param <Type> the generic type
 * @author  larh
 * <BR>
 * <!--
 * $Revision: 3 $
 * $Log: /prodex-utv/u1.2.2/framework/UpdateCommandAdapter.java $
 * 
 * 3     02-04-25 16:35 Larh
 * 
 * 
 * 
 * -->
 */
public abstract class UpdateCommandAdapter<Type extends Carrier> extends java.lang.Object implements UpdateCommand <Type>
{
    
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(UpdateCommandAdapter.class);
	
	/** The user name. */
	private String userName = UserCache.getUserName(null);
    
    
    /**
     * Creates new UpdateCommandAdapter.
     */
    public UpdateCommandAdapter()
    {
    }

    /* (non-Javadoc)
     * @see se.pedcat.framework.storage.dao.UpdateCommand#postProcessCommand(java.sql.CallableStatement, java.sql.ResultSet, se.pedcat.framework.common.model.Carrier)
     */
    public int postProcessCommand(java.sql.CallableStatement statement,ResultSet rs, Type carrier) throws java.sql.SQLException
    {
    	if (rs!=null && rs.next())
    	{
	        carrier.setAndradAv(userName);
	          
	        carrier.setAndradDatum(DAOUtility.fromTimestampToDate(rs.getTimestamp("andrad_datum")));
    	}
    	return 0;
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.storage.dao.UpdateCommand#preProcessCommand(java.sql.CallableStatement, se.pedcat.framework.common.model.Carrier)
     */
    public int preProcessCommand(java.sql.CallableStatement statement, Type carrier) throws java.sql.SQLException
    {
        
        int index = 1;
        
        statement.setInt(index++,carrier.getObjectIdAsInt());
        DAOUtility.setDateParameter(statement,index++,carrier.getAndradDatum());
        //statement.registerOutParameter(index++,java.sql.Types.TIMESTAMP);
        
        statement.setString(index++,userName);
        return index;
    }
    
}
