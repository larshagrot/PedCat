/*
 * SQLCommand.java
 *
 * Created on den 11 april 2002, 13:12
 */

package se.pedcat.framework.storage.dao;


/**
 * The Interface SQLCommand.
 *
 * @author  larh
 * @version
 */
public interface SQLCommand 
{
    
    /**
     * Sets the parameters.
     *
     * @param statement the new parameters
     * @throws SQLException the sQL exception
     */
    public void setParameters(java.sql.CallableStatement statement)throws java.sql.SQLException;
    
    /**
     * Gets the query string.
     *
     * @return the query string
     */
    public String getQueryString();
    
    /**
     * Post process.
     *
     * @param statement the statement
     * @throws SQLException the sQL exception
     */
    public void postProcess(java.sql.CallableStatement statement) throws java.sql.SQLException;
}

