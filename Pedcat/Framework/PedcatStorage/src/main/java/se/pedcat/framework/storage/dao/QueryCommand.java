/*
 * QueryCommand.java
 *
 * Created on den 30 januari 2001, 19:08
 */

package se.pedcat.framework.storage.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;


/**
 * A general query command interface
 * Used by DAO
 * Implemented by command objects
 * 
 * The purpose of this interface is to generalise the Query Commands for database queries by using domain value objects and stored procedures
 * <BR>
 * A typical implementation can be done by a DAO implementer, a inner class belonging to a DAO implementer or any class
 * <BR>
 * A convenient way to implement this interface is to let an inner class inherit from the QueryCommandAdapter class which has some edfault implementations of the
 * methods below
 * <BR>.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author  larh
 * 
 * TODO: Comment purpose, usage and responsibility for this class/interface please
 */
public interface QueryCommand<KeyType extends Key,Type extends Carrier> 
{
   
   /**
    * Call back method
    * The implementer is supposed to set parameters that are implied by the query string.
    *
    * @param statement the new query parameters
    * @throws SQLException the sQL exception
    * @throws NamingException the naming exception
    * @roseuid 3AB4727801C6
    */
   public void setQueryParameters(java.sql.CallableStatement statement) throws java.sql.SQLException,javax.naming.NamingException;
   
   /**
    * Sets the query parameters.
    *
    * @param statement the statement
    * @param key the key
    * @throws SQLException the sQL exception
    * @throws NamingException the naming exception
    */
   public void setQueryParameters(java.sql.CallableStatement statement,KeyType key) throws java.sql.SQLException,javax.naming.NamingException;
   
   
   /**
    * Gets the query string.
    *
    * @return a jdbc query string for a callable statement
    * @roseuid 3AB47278020C
    */
   public String getQueryString();
   
   /**
    * Picks up the resultset after a executed query.
    *
    * @param statement the statement
    * @return the result set
    * @throws SQLException the sQL exception
    */
   public ResultSet getResultSet(PreparedStatement statement) throws java.sql.SQLException;

   /**
    * Gets the result set.
    *
    * @param statement the statement
    * @return the result set
    * @throws SQLException the sQL exception
    */
   public ResultSet getResultSet(CallableStatement statement) throws java.sql.SQLException;

   /**
    * Checks if is carrierbuilder.
    *
    * @return true, if is carrierbuilder
    */
   public boolean isCarrierbuilder();
   
   /**
    * Builds the carriers.
    *
    * @param rs the rs
    * @return the collection
    */
   public Collection<Type> buildCarriers(java.sql.ResultSet rs);

   /**
    * Sets the query parameters.
    *
    * @param statement the new query parameters
    * @throws SQLException the sQL exception
    */
   public void setQueryParameters(PreparedStatement statement) throws java.sql.SQLException;

   /**
    * Checks if is callable.
    *
    * @return true, if is callable
    */
   public boolean isCallable();

   /**
    * Sets the query parameters.
    *
    * @param statement the statement
    * @param key the key
    * @throws SQLException the sQL exception
    */
   void setQueryParameters(PreparedStatement statement, KeyType key)
		throws SQLException;
   
}
