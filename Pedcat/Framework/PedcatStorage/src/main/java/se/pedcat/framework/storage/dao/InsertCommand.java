/*
 * InsertCommand.java
 *
 * Created on den 17 mars 2001, 08:56
 */

package se.pedcat.framework.storage.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




/**
 * General interface for insert commands
 * Used by InfoHomeImpl.
 *
 * @param <Type> the generic type
 * @author  lars
 * <BR>
 * The purpose of this interface is to generalise the Insert Commands for database inserts by using domain value objects and stored procedures
 * <BR>
 * A typical implementation can be done by a DAO implementer, a inner class belonging to a DAO implementer or any class
 * <BR>
 * A convenient way to implement this interface is to let an inner class inherit from the InsertCommandAdapter class which has some edfault implementations of the
 * methods below
 * <BR>
 * @author  larh
 */
public interface InsertCommand<Type>
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
     * @domainValueObject  object to use
     * @roseuid 3AB4727903DA
     */
    //public void setInsertParameters(java.sql.CallableStatement statement, Carrier carrier) throws java.sql.SQLException;
    
    /**
     *
     *
     */
    public int setInsertParameters(int index,java.sql.CallableStatement statement,Type carrier) throws java.sql.SQLException;
    
    /**
     * Pre process command.
     *
     * @param statement the statement
     * @param carrier the carrier
     * @return the int
     * @throws SQLException the sQL exception
     */
    public int preProcessCommand(java.sql.CallableStatement statement,Type carrier) throws java.sql.SQLException;
    
    /**
     * Process command after execution.
     *
     * @param statement    executed statement
     * @param rs the rs
     * @param carrier the carrier
     * @throws SQLException the sQL exception
     * @domainValueObject  object to use
     */
    public void postProcessCommand(java.sql.CallableStatement statement, ResultSet rs, Type carrier) throws java.sql.SQLException;
    
    /**
     * Gets the insert string.
     *
     * @return a jdbc query string for a callable statement
     * @roseuid 3AB4727A0056
     */
    public String getInsertString();
    
    
	
	/**
	 * Post process batch.
	 *
	 * @param connection the connection
	 * @param types the types
	 * @return the list
	 * @throws SQLException the sQL exception
	 */
	public List<Type> postProcessBatch(Connection connection,List<Type> types) throws SQLException;
	
	/**
	 * Pre process batch.
	 *
	 * @param connection the connection
	 * @param types the types
	 * @return the list
	 * @throws SQLException the sQL exception
	 */
	public List<Type> preProcessBatch(Connection connection,List<Type> types) throws SQLException;
	
}
