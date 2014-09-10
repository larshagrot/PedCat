/*
 * Trace.java
 *
 * Created on November 27, 2001, 2:25 PM
 */

package se.pedcat.framework.common.util;
//import javax.ejb.*;

import java.util.*;
import java.lang.reflect.*;
import java.rmi.*;
import java.io.*;
import java.text.*;

import org.slf4j.Logger;


import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkMessage;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.Log;
import se.pedcat.framework.common.util.Configuration;


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
public class Trace implements Serializable
{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	// We are counting objects for debug reasons
    /** The counter. */
	private static int counter = 0;
    
    // Here is a prefix to be used for the perfmorance log file
    /** The Constant PERFORMANCE_LOG_FILE_DEFAULT_NAME. */
    private final static String PERFORMANCE_LOG_FILE_DEFAULT_NAME = "Performance";
    
    // Here is a file name for trace properties
    /** The Constant TRACE_PROPERTY_FILE_NAME. */
    private static final String TRACE_PROPERTY_FILE_NAME = "trace.properties";
    
    // This is reference to the performance log file
    /** The _out. */
    private static PrintWriter _out;
    
    // This map holdes information about which classes to trace
    /** The tracing classes. */
    private static Map<String,String> tracingClasses = new HashMap<String,String>();
    
    // This property tells about all tracing is off
    /** The is no tracing. */
    private static boolean isNoTracing = false;
    
    // This property tells about all tracing is on
    /** The is full tracing. */
    private static boolean isFullTracing = false;
    
    // This is a formatter for date part of the filename...
    /** The date formatter. */
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddHHmmss");
    
    // Time measure starts when the object is created
    /** The _start time. */
    private long _startTime = System.currentTimeMillis();
    
    /** Remembers class and method. */
    private String _classAndMethodName;
    
    /** Remembers current user. */
    private String  _userId ;
    
    /** Remembers whether this call is verbose or not. */
    private boolean _verbose;
    
    /** Holds a caught exception. */
    private Throwable  throwable;
    
    /** The logger. */
    private Logger logger = null;
    



	static
    {
        loadProperties();

        //if (DAOUtility.isServer())
        {
            //IFRAtrackAdapter.start();
        }
    }
    
    /**
     * Creates new Trace.
     *
     * @param logger the logger
     * @param object the object
     * @param classAndMethodName the class and method name
     * @param verbose the verbose
     */
    protected Trace(Logger logger,Object object,String classAndMethodName,boolean verbose)
    {
    	this.setLogger(logger);
        this._classAndMethodName = classAndMethodName;
        this._verbose = verbose;
        this.setUser(object);
        if (isVerbose())
        {
            Log.logMessage(logger,this.getClassAndMethodName(),">>>>> IN >>>>> (" + _userId +")");
        }
        counter++;
    }
    /*
    protected void finalize() throws Throwable
    {
        super.finalize();
        counter--;
    }*/
    
    /**
     * Gets the count.
     *
     * @return the count
     */
    public static int getCount()
    {
        return counter;
    }


    /**
     * Factory method which is supposed to be used inside instance methods.
     *
     * @param logger the logger
     * @param object the object
     * @param methodName the method name
     * @param verbose the verbose
     * @return the trace
     */
    public static Trace startTrace1(Logger logger,Object object,String methodName,boolean verbose)
    {
        return startTrace1(logger,object,object.getClass(), methodName, verbose,false);
    }
    
    /**
     * Start trace performance.
     *
     * @param logger the logger
     * @param object the object
     * @param methodName the method name
     * @param verbose the verbose
     * @return the trace
     */
    public static Trace startTracePerformance(Logger logger,Object object,String methodName,boolean verbose)
    {
        return startTrace1(logger,object,object.getClass(), methodName, verbose,true);
    }
    
    /**
     * Start trace.
     *
     * @param logger the logger
     * @param classObject the class object
     * @param methodName the method name
     * @param verbose the verbose
     * @return the trace
     */
    public static Trace startTrace1(Logger logger,Class classObject,String methodName,boolean verbose)
    {
        return startTrace1(logger,null,classObject, methodName, verbose,false);
    }
    
    
    /**
     * Do assert.
     *
     * @param expression the expression
     * @param message the message
     * @param messageKey the message key
     */
    public void doAssert(boolean expression,String message,String messageKey)
    {
        if (!expression)
        {
            this.throwFrameworkException(message,messageKey);
        }
    }
    
    /**
     * Do assert.
     *
     * @param expression the expression
     * @param messageKey the message key
     */
    public void doAssert(boolean expression,String messageKey)
    {
        if (!expression)
        {
            this.throwFrameworkException("Assertion failed",messageKey);
        }
    }
    
    /**
     * Do assert.
     *
     * @param expression the expression
     */
    public void doAssert(boolean expression)
    {
        if (!expression)
        {
            this.throwFrameworkException("Assertion failed","");
        }
    }
    
    /**
     * Gets the logger.
     *
     * @return the logger
     */
    public Logger getLogger() {
		return logger;
	}

	/**
	 * Sets the logger.
	 *
	 * @param logger the new logger
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

    
    
    /**
     * Factory method which is supposed to be used inside class methods.
     *
     * @param logger the logger
     * @param object the object
     * @param classObject the class object
     * @param methodName the method name
     * @param verbose the verbose
     * @param isPerformance the is performance
     * @return the trace
     */
    public static Trace startTrace1(Logger logger,Object object,Class classObject,String methodName,boolean verbose,boolean isPerformance)
    {
        boolean isFrontEnd = isPerformance;
        
        String classAndMethodName = classObject.getName()+ "." + methodName;
        verbose = !Trace.isNoTracing &&  (verbose || isPropertyOn(classObject.getName()) || Trace.isFullTracing);
        if (verbose)
        {
            if (isFrontEnd)
            {
                return new TraceFrontEnd(logger,object,classAndMethodName,verbose);
            }
            else
            {
                return new Trace(logger,object,classAndMethodName,verbose);
            }
        }
        else
        {
            if (isFrontEnd)
            {
                return new TraceFrontEndNullObject(logger,object,classAndMethodName);
            }
            else
            {
                return new TraceNullObject(logger,object,classAndMethodName);
            }
        }
    }
    
    /**
     * Gets the elapsed seconds.
     *
     * @return the elapsed seconds
     */
    public double getElapsedSeconds()
    {
        return (System.currentTimeMillis() - _startTime)/1000.0;
    }
    
    /**
     * End.
     *
     * @param returned the returned
     */
    public void end(int returned)
    {
        if (this._userId!=null)
        {
            Log.logMessage(logger,this.getClassAndMethodName(),"<<< OUT <<< (" + _userId + ") " + getElapsedSeconds() + " " + returned);
        }
        else
        {
            Log.logMessage(logger,this.getClassAndMethodName(),"<<< OUT <<< " + getElapsedSeconds()+ " " + returned);
        }
    }
    
    /**
     * End.
     */
    public void end()
    {
        this.end(-1);
    }
    
    /**
     * End.
     *
     * @param collection the collection
     */
    public void end(Collection collection)
    {
        if (collection!=null)
        {
            this.end(collection.size());
        }
        else
        {
            this.end();
        }
    }
    
    /**
     * End.
     *
     * @param objects the objects
     */
    public void end(Object[] objects)
    {
        if (objects!=null)
        {
            this.end(objects.length);
        }
        else
        {
            this.end();
        }
    }
    
    /**
     * Gets the class and method name.
     *
     * @return the class and method name
     */
    public String getClassAndMethodName()
    {
        return this._classAndMethodName;
    }
    
    /**
     * Checks if is verbose.
     *
     * @return true, if is verbose
     */
    public boolean isVerbose()
    {
        return _verbose;
    }
    
    /**
     * Log error.
     *
     * @param message the message
     */
    public void logError(String message)
    {
        Log.logError(logger,getClassAndMethodName(),"***** ERROR " + message +" *****");
    }
    
    /**
     * Log warning.
     *
     * @param message the message
     */
    public void logWarning(String message)
    {
        Log.logWarning(logger,getClassAndMethodName(),"***** WARNING " + message +" *****");
    }
    
    /**
     * Log message.
     *
     * @param message the message
     */
    public void logMessage(String message)
    {
        Log.logMessage(logger,this.getClassAndMethodName(),message + " {" + getElapsedSeconds() + "}");
    }
    
    /**
     * Log exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public void logException(String message,Throwable throwable)
    {
        Log.logException(logger,this.getClassAndMethodName(),message,throwable);
        if (throwable!=null )
        {
            setException(throwable);
            parseException(throwable);
        }
    }
    
    /**
     * Sets the exception.
     *
     * @param throwable the new exception
     */
    private void setException(Throwable throwable)
    {
        if (this.throwable==null)
        {
            this.throwable = throwable;
        }
    }
    
    /**
     * Log exception.
     *
     * @param throwable the throwable
     */
    public void logException(Throwable throwable)
    {
        this.logException("", throwable);
    }
    
    
    /**
     * Throw runtime exception.
     *
     * @param message the message
     */
    public void throwRuntimeException(String message)
    {
        this.logError(message);
        throw createRuntimeException(message);
    }
    
    /**
     * Creates the runtime exception.
     *
     * @param message the message
     * @return the runtime exception
     */
    private RuntimeException createRuntimeException(String message) {
		// TODO Auto-generated method stub
		return new FrameworkException(message);
	}

	/**
	 * Throw runtime exception.
	 *
	 * @param exception the exception
	 */
	public void throwRuntimeException(Exception exception)
    {
        throwRuntimeException(null,exception);
    }
    
    /**
     * Throw runtime exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public void throwRuntimeException(String message,Exception exception)
    {
        this.logException(message,exception);
        if (isRuntimeException(exception))
        {
            throw  (RuntimeException)exception;
        }
        else
        {
            throw createRuntimeException(message,exception);
        }
    }
    
    /**
     * Creates the runtime exception.
     *
     * @param message the message
     * @param exception the exception
     * @return the runtime exception
     */
    private RuntimeException createRuntimeException(String message, Exception exception) {
		// TODO Auto-generated method stub
		return new FrameworkException(message,exception);
	}

	/**
	 * Checks if is runtime exception.
	 *
	 * @param exception the exception
	 * @return true, if is runtime exception
	 */
	private boolean isRuntimeException(Exception exception) {
		// TODO Auto-generated method stub
		return exception!=null && exception instanceof RuntimeException;
	}

	/**
	 * Throw system exception.
	 *
	 * @param message the message
	 */
	public void throwSystemException(String message)
    {
        this.throwRuntimeException(message,null);
    }
    
    /**
     * Throw system exception.
     *
     * @param exception the exception
     */
    public void throwSystemException(Exception exception)
    {
        throwRuntimeException("",exception);
    }
    
    /**
     * Throw framework exception.
     *
     * @param message the message
     * @param exception the exception
     */
    public void throwFrameworkException(String message,Exception exception)
    {
        this.logException(message,exception);
        if (isFrameworkException(exception))
        {
            throw (FrameworkException) exception;
        }
        else
        {
            throw new  FrameworkException(message,exception);
        }
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     */
    public void throwFrameworkException(String msg,String messageKey)
    {
        this.throwFrameworkException(msg, messageKey,null);
    }
    
    /**
     * Throw framework exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param nestedException the nested exception
     */
    public void throwFrameworkException(String msg,String messageKey,java.lang.Throwable nestedException)
    {
        throw new FrameworkException(msg,nestedException,messageKey);
    }
    
        
    
    
    
    
    
    
    /**
     * Checks if is framework exception.
     *
     * @param exception the exception
     * @return true, if is framework exception
     */
    public boolean isFrameworkException(Throwable exception) {
		
    	Throwable t = exception;
    	while (t!=null && ! (t instanceof FrameworkException ) && t.getCause()!=null)
		{
			exception = t;
			t = t.getCause();
		}
		if (t == null)
		{
			t = exception;
		}
		return t instanceof FrameworkException;
		
	}

	/**
	 * Test.
	 *
	 * @param verbose the verbose
	 */
	private void test(boolean verbose)
    {
        Trace trace = Trace.startTrace1(logger,this,"test",verbose);
        try
        {
            trace.logMessage("Testing message");
            trace.logError("Testing error");
            throw new Exception("Throwing");
        }
        catch (Exception e)
        {
            trace.logException("Testing exception",e);
        }
        finally
        {
            trace.end();
        }
        
    }
    
    /**
     * Dump.
     *
     * @param object the object
     */
    public void dump(Object object)
    {
        this.logMessage(CommonUtility.dumpFields(object) + CommonUtility.dumpProperties(object));
    }
    
    /**
     * Dump fields.
     *
     * @param object the object
     */
    public void dumpFields(Object object)
    {
        this.logMessage(CommonUtility.dumpFields(object) );
    }
    
    /**
     * Dump properties.
     *
     * @param object the object
     */
    public void dumpProperties(Object object)
    {
        this.logMessage(CommonUtility.dumpProperties(object));
    }
    
    /**
     * Dump fields.
     *
     * @param objects the objects
     */
    public void dumpFields(Object[] objects)
    {
        this.logMessage(CommonUtility.dumpFields(objects));
    }
    
    /**
     * Compare fields.
     *
     * @param object1 the object1
     * @param object2 the object2
     */
    public void compareFields(Object object1,Object object2)
    {
        try
        {
            Field[] equalFields = CommonUtility.compareFields(object1,object2,true);
            Field[] differentFields = CommonUtility.compareFields(object1,object2,false);
            Log.logMessage(this.logger,":","EQUAL");
            for(int index=0;index<equalFields.length;index++)
            {
                Log.logMessage(this.logger,equalFields[index].getName(),equalFields[index].get(object1).toString());
            }
            Log.logMessage(this.logger,":","DIFFERENT");
            for(int index=0;index<differentFields.length;index++)
            {
                Log.logMessage(this.logger,differentFields[index].getName(),differentFields[index].get(object1) + ":" + differentFields[index].get(object2));
            }
        }
        catch (Exception e)
        {
            this.logException(e);
        }
    }
    
    
    /**
     * Checks if is property on.
     *
     * @param className the class name
     * @return true, if is property on
     */
    public static boolean isPropertyOn(String className)
    {
        return tracingClasses.containsKey(className);
    }
    
    /**
     * Checks if is property on.
     *
     * @param className the class name
     * @param defaultValue the default value
     * @return true, if is property on
     */
    public static boolean isPropertyOn(String className,boolean defaultValue)
    {
        String property =  (String)tracingClasses.get(className);
        if (property!=null)
        {
            return property.equals("true");
        }
        else
        {
            return defaultValue;
        }
    }
    
    /**
     * Load properties.
     */
    public static void loadProperties()
    {
        try
        {
            /*InputStream inputStream = new java.io.FileInputStream(TRACE_PROPERTY_FILE_NAME);
            Properties properties = new Properties();
            properties.load(inputStream);
            setProperties(properties);
            inputStream.close();*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    /**
     * Parses the exception.
     *
     * @param exception the exception
     */
    public  void  parseException(Throwable exception)
    {
        if (exception!=null)
        {
            //this.logError(this.logger,null//CommonUtility.parseException(exception));
        }
    }
    
    /**
     * Sets the trace property.
     *
     * @param logger the logger
     * @param key the key
     * @param value the value
     */
    public static void setTraceProperty(Logger logger,String key,String value)
    {
        Log.logMessage(logger,"","Setting " + key + " " + value);
        if (value.equals("true"))
        {
            tracingClasses.put(key,value);
            if (key.equalsIgnoreCase("fulltracing"))
            {
                Trace.isFullTracing = true;
                Trace.isNoTracing = false;
            }
            if (key.equalsIgnoreCase("notracing"))
            {
                Trace.isFullTracing = false;
                Trace.isNoTracing = true;
            }
        }
        else
        {
            tracingClasses.remove(key);
            if (key.equalsIgnoreCase("fulltracing"))
            {
                Trace.isFullTracing = false;
                
            }
            if (key.equalsIgnoreCase("notracing"))
            {
                Trace.isNoTracing = false;
            }
        }
        
    }
    
    
    
    /**
     * Sets the properties.
     *
     * @param properties the new properties
     */
    public static void setProperties(Properties properties)
    {
        tracingClasses.clear();
        Enumeration enumeration = properties.keys();
        while (enumeration.hasMoreElements())
        {
            String key = (String) enumeration.nextElement();
            if (properties.getProperty(key,"false").equals("true"))
            {
                System.out.println("tracing activated for " + key);
                tracingClasses.put(key,"true");
                if (key.equalsIgnoreCase("fulltracing"))
                {
                    Trace.isFullTracing = true;
                    Trace.isNoTracing = false;
                }
                if (key.equalsIgnoreCase("notracing"))
                {
                    Trace.isFullTracing = true;
                    Trace.isNoTracing = true;
                }
            }
            else
            {
                //trace.logMessage("tracing not activated for " + key);
                System.out.println("tracing NOT activated for " + key);
            }
        }
    }
    
    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public static Properties getProperties()
    {
        Properties p = new Properties();
        Set set = tracingClasses.keySet();
        Iterator i = set.iterator();
        while (i.hasNext())
        {
            Object key = i.next();
            p.put(key,tracingClasses.get(key));
        }
        return p;
    }
    
    /**
     * Sets the user.
     *
     * @param object the new user
     */
    public void setUser(Object object)
    {
        
        if (_userId==null)
        {
         /*   if (object!=null && object instanceof DefaultSessionBeanImpl)
            {
                DefaultSessionBeanImpl defaultSessionBeanImpl = (DefaultSessionBeanImpl) object;
                try
                {
                    _userId = defaultSessionBeanImpl.getCurrentUser();
                }
                catch (Exception e)
                {
                }
            }
            else
            {
                _userId = UserCache.getUserName(null);
            }*/
        }
        
    }
    
    /**
     * Gets the user.
     *
     * @return the user
     */
    public String getUser()
    {
        return this._userId;
    }
    
    /**
     * Sets the user.
     *
     * @param userId the new user
     */
    public void setUser(String userId)
    {
        this._userId = userId;
    }
    
    /**
     * Checks if is exception.
     *
     * @return true, if is exception
     */
    public boolean isException()
    {
        return this.throwable!=null;
    }
    
    /**
     * Gets the exception.
     *
     * @return the exception
     */
    public Throwable getException()
    {
        return this.throwable;
    }
    
    
    
    
    /**
     * Get singleton.
     *
     * @return the writer
     * @throws FrameworkException the framework exception
     */
    public static PrintWriter getWriter() throws FrameworkException
    {
        try
        {
            if (_out == null)
            {
                //String logFileName = Configuration.getProperty(LOG_FILE_PROPERTY_NAME,LOG_FILE_DEFAULT_NAME);
                String logFileName = PERFORMANCE_LOG_FILE_DEFAULT_NAME;
                _out = new PrintWriter(new BufferedWriter(new FileWriter(File.separatorChar + "tmp" +
                File.separatorChar + Configuration.getMandatoryProperty(Configuration.SERVER_TYPE_KEY) + logFileName+ dateFormatter.format(new java.util.Date())+ ".txt")) ,true);
            }
        }
        catch (IOException e)
        {
            //throw new FrameworkException("Could not open logfile","Log.getWriter",e);
            e.printStackTrace();
            System.err.println(e.toString());
        }
        return _out;
    }
    
    

	

    
}