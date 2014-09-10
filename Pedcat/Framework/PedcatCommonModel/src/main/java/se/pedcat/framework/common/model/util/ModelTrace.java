/*
 * Trace.java
 *
 * Created on November 27, 2001, 2:25 PM
 */

package se.pedcat.framework.common.model.util;
//import javax.ejb.*;

import java.util.*;
import java.lang.reflect.*;
import java.rmi.*;
import java.io.*;
import java.text.*;

import org.slf4j.Logger;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkMessage;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.Log;
import se.pedcat.framework.common.util.Trace;
import se.pedcat.framework.common.util.Configuration;
import se.pedcat.framework.common.util.TraceFrontEnd;
import se.pedcat.framework.common.util.TraceFrontEndNullObject;
import se.pedcat.framework.common.util.TraceNullObject;


// TODO: Auto-generated Javadoc
/**
 * This is a general Trace class. It it supposed to be used and instantiated in 
 * methods. The general pattern to use is to
 * 
 *<pre>
 *   Trace trace  = Trace.startTrace(this,"methodName",false);
 *
 *   try
 *   {
 *       ....
 *       trace.logMessage("Shall be logged...");
 *       ...
 *       trace.logError("Shall be ...");
 *       ...
 *       trace.logWarning("Shall be ...");
 *   }
 *   catch(Exception e)
 *   {
 *       trace.throwProdexException(e,......);
 *   }   
 *   finnally
 *   {
 *      trace.end();
 *   }
 *</pre>
 *
 * 
 *
 * This class is to be "merged" with the logging mechanisms in jdk 1.4
 *  
 *
 *
 *
 * @author  larh
 * @version
 */
public class ModelTrace extends Trace
{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates new Trace.
	 *
	 * @param logger the logger
	 * @param object the object
	 * @param classAndMethodName the class and method name
	 * @param verbose the verbose
	 */
    protected ModelTrace(Logger logger,Object object,String classAndMethodName,boolean verbose)
    {
    	super(logger,object,classAndMethodName,verbose);
    }
    
    /**
     * Start trace.
     *
     * @param logger the logger
     * @param object the object
     * @param classObject the class object
     * @param methodName the method name
     * @param verbose the verbose
     * @param isPerformance the is performance
     * @return the model trace
     */
    public static ModelTrace startTrace(Logger logger,Object object,Class<?> classObject,String methodName,boolean verbose,boolean isPerformance)
    {
        if (verbose)
        {
        	return new ModelTrace(logger,classObject,methodName,verbose);
        	
        }
        else
        {
        	return new NullModelTrace(logger,classObject,methodName,verbose);
        }
        
    }
    
    /**
     * Start trace.
     *
     * @param logger the logger
     * @param classObject the class object
     * @param methodName the method name
     * @param verbose the verbose
     * @return the model trace
     */
    public static ModelTrace startTrace(Logger logger,Class<?> classObject,String methodName,boolean verbose)
    {
    	if (verbose)
    	{
    		return new ModelTrace(logger,classObject,methodName,verbose);
    	}
    	else
    	{
    		return new NullModelTrace(logger,classObject,methodName,verbose);
    	}
        
    }


    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param carrier the carrier
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException,Carrier carrier)
    {
        this.throwFrameworkException(msg,messageKey, nestedException, carrier,null,null,null,false);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param query the query
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException,ModelQuery query)
    {
        this.throwFrameworkException(msg,messageKey, nestedException, null,null,null,query,false);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param carriers the carriers
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException,Carrier[] carriers)
    {
        this.throwFrameworkException(msg, messageKey,nestedException,null, carriers,null,null,false);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param carriers the carriers
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException,List<Carrier> carriers)
    {
        this.throwFrameworkException(msg, messageKey,nestedException,null, carriers.toArray(new Carrier[0]),null,null,false);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param key the key
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException,Key key)
    {
        this.throwFrameworkException(msg,messageKey, nestedException,null,null,key,null,false);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     * @param carrier the carrier
     * @param carriers the carriers
     * @param key the key
     * @param query the query
     * @param systemException the system exception
     */
    public void throwFrameworkException(   String msg,
    String messageKey,
    java.lang.Throwable nestedException,
    Carrier carrier,
    Carrier[] carriers,
    Key key,
    ModelQuery query,
    boolean systemException)
    {
        this.logException(msg,nestedException);
        this.dumpFields(carrier);
        this.dumpFields(carriers);
        this.dumpProperties(query);
        try
        {
            if (isFrameworkException(nestedException))
            {
                throw (FrameworkException) nestedException;
            }
            else
                {
                    throw new  ModelException(msg,messageKey,this.getClassAndMethodName(),nestedException,carrier,carriers,key,query);
                }
        }
        catch (Exception e)
        {
            if (systemException)
            {
                this.throwRuntimeException(e);
            }
            else
            {
                if (e instanceof FrameworkException)
                {
                    throw (FrameworkException) e;
                }
                else
                {
                    throw (FrameworkException) e;
                }
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
}