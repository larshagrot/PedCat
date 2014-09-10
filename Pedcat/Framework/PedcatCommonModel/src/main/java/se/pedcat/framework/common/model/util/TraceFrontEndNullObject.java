/*
 * Trace.java
 *
 * Created on November 27, 2001, 2:25 PM
 */

package se.pedcat.framework.common.model.util;

import org.slf4j.Logger;


// TODO: Auto-generated Javadoc
/**
 * The Class TraceFrontEndNullObject.
 *
 * @author  larh
 * @version
 */
public class TraceFrontEndNullObject extends TraceFrontEnd{

    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates new Trace.
	 *
	 * @param logger the logger
	 * @param object the object
	 * @param classAndMethodName the class and method name
	 */
    protected TraceFrontEndNullObject(Logger logger,Object object, String classAndMethodName) 
    {
        super(logger,object,classAndMethodName,false);
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
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.util.TraceFrontEnd#end()
     */
    public void end() 
    {
         writeLog(-1);
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.util.TraceFrontEnd#end(int)
     */
    public void end(int returned) 
    {
         writeLog(returned);
    }
    


}
