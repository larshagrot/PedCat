/*
 * DeleteCommand.java
 *
 * Created on den 17 mars 2001, 08:56
 */

package se.pedcat.framework.storage.dao;


import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;



/**
 * General interface for delete commands
 * Used by DAOImpl
 * Defined by user defined DAO implementers.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author  larh
 * <BR>
 * The purpose of this interface is to generalise the Delete Commands for database deletes by using domain value objects and stored procedures
 * <BR>
 * A typical implementation can be done by a DAO implementer, a inner class belonging to a DAO implementer or any class
 * <BR>
 * A convenient way to implement this interface is to let an inner class inherit from the DeleteCommandAdapter class which has some edfault implementations of the
 * methods below
 * <BR>
 * 
 * <!--
 * $Revision: 2 $
 * $Log: /prodex-utv/u1.2.2/framework/DeleteCommand.java $
 * 
 * 2     02-04-22 14:56 Larh
 * 
 * 
 * -->
 */
public interface DeleteCommand <KeyType extends Key,Type extends Carrier>
{
    
    /**
     * Call back method
     * The implementer is supposed to set parameters that are implied by the query string.
     *
     * @param statement the statement
     * @param key the key
     * @throws SQLException the sQL exception
     * @roseuid 3AB4727A02F5
     */
    public void setDeleteParameters(java.sql.CallableStatement statement, KeyType key) throws java.sql.SQLException;
    
    /**
     * Call back method
     * The implementer is supposed to set parameters that are implied by the query string.
     *
     * @param statement the new delete parameters
     * @throws SQLException the sQL exception
     * @roseuid 3AB4727A02F5
     */
    public void setDeleteParameters(java.sql.CallableStatement statement) throws java.sql.SQLException;
    
    
    /**
     * Gets the delete string.
     *
     * @return a jdbc query string for a callable statement
     * @roseuid 3AB4727A035A
     */
    public String getDeleteString();
}
