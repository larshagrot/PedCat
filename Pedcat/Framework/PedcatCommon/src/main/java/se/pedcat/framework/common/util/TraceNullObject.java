/*
 * Trace.java
 *
 * Created on November 27, 2001, 2:25 PM
 */

package se.pedcat.framework.common.util;

import org.slf4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * This is an implementation of the NullObject design pattern
 *
 * This particular instantiation is a Dummy which is used to turn of tracing without 
 * having to tell client code.
 *
 * The empty methods which is for instance logMessage
 *
 * Warnings and errors are not implemented
 *
 *
 *<!--
 * $Revision: 6 $
 * $Log: 
 * 
 * 6     02-04-25 16:35 Larh
 *
 *
 *
 *-->

 * @author  larh
 * @version 
 */
public class TraceNullObject extends Trace{

    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new Trace.
	 *
	 * @param logger the logger
	 * @param object the object
	 * @param classAndMethodName the class and method name
	 */
    protected TraceNullObject(Logger logger,Object object,String classAndMethodName) 
    {
        super(logger,object,classAndMethodName,false);
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end()
     */
    public void end()
    {

    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end(int)
     */
    public void end(int returned)
    {

    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end(java.util.Collection)
     */
    public void end(java.util.Collection collection)
    {
     
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end(java.lang.Object[])
     */
    public void end(Object[] objects)
    {
     
    }
 
    
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#logMessage(java.lang.String)
     */
    public void logMessage(String message)
    {
        
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#logWarning(java.lang.String)
     */
    public void logWarning(String message)
    {
        
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#dumpFields(java.lang.Object)
     */
    public void dumpFields(Object object)
    {
        
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#dumpFields(java.lang.Object[])
     */
    public void dumpFields(Object[] objects)
    {
        
    }


}
