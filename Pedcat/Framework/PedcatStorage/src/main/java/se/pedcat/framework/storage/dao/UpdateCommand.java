/*
 * InsertCommand.java
 *
 * Created on den 17 mars 2001, 08:56
 */

package se.pedcat.framework.storage.dao;

import se.pedcat.framework.common.model.Carrier;



/**
 * The purpose of this interface is to generalise the Update Commands for database updates by using domain value objects and stored procedures
 * <BR>
 * A typical implementation can be done by a DAO implementer, a inner class belonging to a DAO implementer or any class
 * <BR>
 * A convenient way to implement this interface is to let an inner class inherit from the UpdateCommandAdapter class which has some edfault implementations of the
 * methods below
 * <BR>.
 *
 * @param <Type> the generic type
 * @author  larh
 * 
 * <!--
 * $Revision: 2 $
 * $Log: /prodex-utv/u1.2.2/framework/UpdateCommand.java $
 * 
 * 2     02-04-25 16:35 Larh
 * 
 * 
 * 
 * -->
 * 
 * TODO: Comment purpose, usage and responsibility for this class/interface please
 */
public interface UpdateCommand<Type extends Carrier>
{
    
    /**
     * Call back method
     * The implementer is supposed to set parameters that are implied by the query string.
     *
     * @param index the index
     * @param statement    executed statement
     * @param carrier the carrier
     * @return the int
     * @throws SQLException the sQL exception
     * @carrier  object to use
     */
    public int setUpdateParameters(int index,java.sql.CallableStatement statement, Type carrier) throws java.sql.SQLException;
    
    /**
     * Process command after execution.
     *
     * @param statement    executed statement
     * @param rs the rs
     * @param carrier the carrier
     * @return the int
     * @throws SQLException the sQL exception
     * @carrier  object to use
     */
    public int postProcessCommand(java.sql.CallableStatement statement,java.sql.ResultSet rs, Type carrier) throws java.sql.SQLException;
    
    /**
     * Supposed to be called before execution of statement...
     *
     * @param statement    executed statement
     * @param carrier the carrier
     * @return the int
     * @throws SQLException the sQL exception
     * @carrier  object to use
     */
    public int preProcessCommand(java.sql.CallableStatement statement, Type carrier) throws java.sql.SQLException;
    
    
    /**
     * Gets the update string.
     *
     * @return a jdbc query string for a callable statement
     */
    public String getUpdateString();
}
