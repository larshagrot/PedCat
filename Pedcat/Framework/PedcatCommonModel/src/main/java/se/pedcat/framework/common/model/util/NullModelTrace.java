/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model.util;

import org.slf4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class NullModelTrace.
 */
public class NullModelTrace extends ModelTrace {

	/**
	 * Instantiates a new null model trace.
	 *
	 * @param logger the logger
	 * @param object the object
	 * @param classAndMethodName the class and method name
	 * @param verbose the verbose
	 */
	protected NullModelTrace(Logger logger, Object object,
			String classAndMethodName, boolean verbose) {
		super(logger, object, classAndMethodName,false);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
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
