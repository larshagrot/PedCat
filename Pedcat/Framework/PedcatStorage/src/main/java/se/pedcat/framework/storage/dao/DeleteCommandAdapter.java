/*
 * DeleteCommandAdapter.java
 *
 * Created on den 17 mars 2001, 08:56
 */

package se.pedcat.framework.storage.dao;


import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.storage.util.DAOUtility;

import java.sql.CallableStatement;
import java.sql.SQLException;




/**
 * Default implementation of the DeleteCommand interface
 * 
 * * <!--
 * $Revision: 2 $
 * $Log: /prodex-utv/u1.2.2/framework/DeleteCommandAdapter.java $
 * 
 * 2     02-04-22 14:56 Larh
 * 
 * 
 * -->
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author  larh
 */
public abstract class DeleteCommandAdapter<KeyType extends Key,Type extends Carrier> implements DeleteCommand<KeyType,Type>
{
    
    /* (non-Javadoc)
	 * @see se.signifikant.framework.storage.dao.DeleteCommand#setDeleteParameters(java.sql.CallableStatement)
	 */
	@Override
	public void setDeleteParameters(CallableStatement statement)
			throws SQLException {
		throw new RuntimeException("This should never  be called!" );
		
	}

	/**
	 * Call back method
	 * The implementer is supposed to set parameters that are implied by the query string.
	 *
	 * @param statement the statement
	 * @param key the key
	 * @throws SQLException the sQL exception
	 */
    public void setDeleteParameters(java.sql.CallableStatement statement, KeyType key) throws java.sql.SQLException
    {
        int index = 1;
        statement.setInt(index++,key.getObjectId());
        DAOUtility.setDateParameter(statement, index,null);
    }
    
    /**
     * Gets the delete string.
     *
     * @return a jdbc query string for a callable statement
     */
    abstract public String getDeleteString();
    
    
}
