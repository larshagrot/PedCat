/*
 * Log.java
 *
 * Created on November 27, 2001, 1:04 PM
 */

package se.pedcat.framework.common.util;
import java.io.*;
import java.util.*;
import java.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class Log.
 *
 * @author  larh
 * @version
 */
public class Log 
{
    

	/** The logger. */
	static Logger logger = LoggerFactory.getLogger(Log.class);
    
    /** The is buffering. */
    private static boolean isBuffering  = false;
    
    /** The is collection. */
    private static boolean isCollection = false;
    
    /** The is on file. */
    private static boolean isOnFile     = false;
    
    /** The is on std out. */
    private static boolean isOnStdOut   = true;
    
    /** The Constant MESSAGE. */
    private static final int MESSAGE   = 0;
    
    /** The Constant INFO. */
    private static final int INFO      = 0;
    
    /** The Constant WARNING. */
    private static final int WARNING   = 1;
    
    /** The Constant ERROR. */
    private static final int ERROR     = 2;
    
    /** The Constant EXCEPTION. */
    private static final int EXCEPTION = 3;
    
    /** The LO g_ fil e_ propert y_ name. */
    private static String  LOG_FILE_PROPERTY_NAME = "logFileName";
    
    /** The LO g_ fil e_ defaul t_ name. */
    private static String  LOG_FILE_DEFAULT_NAME  = "ProdexLog";
    
    /** The DELIMETER. */
    private static String DELIMETER = "##";
    
    /** The _out. */
    private static PrintWriter _out = null;
    
    /** The log string buffer. */
    private static StringBuffer logStringBuffer = new StringBuffer(0xFFFF);
    
    /** The log collection. */
    private static Collection logCollection = new java.util.ArrayList(10000);
    
    /** The date formatter. */
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMddHH:mm:ss");
    
    /** The date formatter2. */
    private static SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MMddHHmmss");
    
    static
    {
        isBuffering  = Configuration.getBooleanProperty(Configuration.IS_LOG_BUFFERING_KEY,false);
        isCollection = false;
        try
        {
            if (Configuration.getProperty(Configuration.SERVER_TYPE_KEY)!=null && !Configuration.getProperty(Configuration.SERVER_TYPE_KEY).equals("testclient"))
            {
                isOnFile     = Configuration.getBooleanProperty(Configuration.IS_LOG_FILE_KEY,true);
            }
        }
        catch (Exception e)
        {
            isOnFile = false;
        }
        isOnStdOut   = Configuration.getBooleanProperty(Configuration.IS_LOG_STDOUT_KEY,true);
    }
    
    
    /**
     * Creates new Log.
     */
    private Log()
    {
    }
    
    /**
     * Log exception.
     *
     * @param logger the logger
     * @param caller the caller
     * @param message the message
     * @param exception the exception
     */
    public static void logException(Logger logger, String caller,String message,Throwable exception)
    {
        String exceptionMessage = "";
        if (exception!=null)
        {
            exception = CommonUtility.getNestedException(exception);
            if (exception!=null)
            {
                exceptionMessage = exception.getMessage();
            }
            if (exceptionMessage==null)
            {
                exceptionMessage = "NO MESSAGE";
            }
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            exception.printStackTrace(printWriter);
            exceptionMessage = exceptionMessage + "\n" + sw.toString();
        }
        write(logger,caller,EXCEPTION,message + ":" + exceptionMessage);
        flushLog();
    }
    
    /**
     * Log exception.
     *
     * @param logger the logger
     * @param caller the caller
     * @param exception the exception
     */
    public static void logException(Logger logger,String caller,Throwable exception)
    {
        logException(logger,caller,null,exception);
    }
    
    /**
     * Log warning.
     *
     * @param logger the logger
     * @param caller the caller
     * @param warning the warning
     */
    public static void logWarning(Logger logger,String caller,String warning)
    {
        write(logger,caller,WARNING,warning);
    }
    
    /**
     * Log message.
     *
     * @param logger the logger
     * @param caller the caller
     * @param message the message
     */
    public static void logMessage(Logger logger, String caller,String message)
    {
        write(logger,caller,MESSAGE,message);
    }
    
    /**
     * Log error.
     *
     * @param logger the logger
     * @param caller the caller
     * @param message the message
     */
    public static void logError(Logger logger,String caller,String message)
    {
        write(logger,caller,ERROR,message);
        flushLog();
    }
    
    /**
     * Flush log.
     */
    public static void flushLog()
    {
        if (isBuffering)
        {
            long start = System.currentTimeMillis();
            writeLogString(logger,"FLUSHING LOGBUFFER ");
            if (isCollection)
            {
                Iterator iterator = logCollection.iterator();
                logCollection = new ArrayList();
                while (iterator.hasNext())
                {
                    writeLogString(logger,(String) iterator.next());
                }
            }
            else
            {
                synchronized(logStringBuffer)
                {
                    String logString = logStringBuffer.toString();
                    logStringBuffer = new StringBuffer(0xFFFF);
                    writeLogString(logger,logString);
                }
            }
            writeLogString(logger,"LOGBUFFER FLUSHED "  + (System.currentTimeMillis()-start)/1000.);
        }
    }
    
    
    
    /**
     * Write.
     *
     * @param logger the logger
     * @param caller the caller
     * @param priority the priority
     * @param message the message
     */
    public static void write(Logger logger, String caller,int priority,String message)
    {
        
    	if (!isLogging(logger,priority))
    	{
    		return;
    	}
    	
    	
        if (!isBuffering)
        {
        	
        	String dateString = getDateString();
            StringBuffer logSStringBuffer = new StringBuffer();
            logSStringBuffer.append(dateString);
            logSStringBuffer.append(DELIMETER);
            logSStringBuffer.append(caller);
            logSStringBuffer.append(DELIMETER);
            logSStringBuffer.append(message);
            //logSStringBuffer.append('\n');
            writeLogString(logger,logSStringBuffer.toString());
        }
        
        else
        {
            if (isCollection)
            {
                String dateString = getDateString();
                StringBuffer logSStringBuffer = new StringBuffer();
                logSStringBuffer.append(dateString);
                logSStringBuffer.append(DELIMETER);
                logSStringBuffer.append(caller);
                logSStringBuffer.append(DELIMETER);
                logSStringBuffer.append(message);
                //logSStringBuffer.append('\n');
                logCollection.add(logSStringBuffer.toString());
                if (logCollection.size()==10000)
                {
                    flushLog();
                }
            }
            else
            {
                String dateString = getDateString();
                logStringBuffer.append(dateString);
                logStringBuffer.append(DELIMETER);
                logStringBuffer.append(caller);
                logStringBuffer.append(DELIMETER);
                logStringBuffer.append(message);
                logStringBuffer.append('\n');
            }
        }
    }
    
    
    /**
     * Checks if is logging.
     *
     * @param logger2 the logger2
     * @param priority the priority
     * @return true, if is logging
     */
    private static boolean isLogging(Logger logger2, int priority) {
	
    	switch (priority)
    	{
    		case ERROR:return logger2.isErrorEnabled();
    		case EXCEPTION:return logger2.isErrorEnabled();
    		case WARNING:return logger2.isWarnEnabled();
    		case MESSAGE:return logger2.isTraceEnabled();
    		default:return false;
    		
    		
    	}
		
	}

	/**
	 * Write log string.
	 *
	 * @param logger the logger
	 * @param logString the log string
	 */
	public static void writeLogString(Logger logger, String logString)
    {
        try
        {
        	logger.info(logString);
            
        	
        	if (isOnStdOut)
            {
            	//System.out.println(logString);
            }
            
            if(isOnFile)
            {
                PrintWriter writer = getWriter();
                writer.println(logString);
            }
        }
        catch (Exception e)
        {
            System.err.println("Fatal exception in Log class");
            e.printStackTrace();
        }
    }
    
    /**
     * Get singleton.
     *
     * @return the writer
     */
    private static PrintWriter getWriter() 
    {
        try
        {
            if (_out == null)
            {
                File directory = new File(File.separatorChar + "tmp");
                if (!directory.exists())
                {
                    directory.mkdirs();
                }
                
                //String logFileName = Configuration.getProperty(LOG_FILE_PROPERTY_NAME,LOG_FILE_DEFAULT_NAME);
                String logFileName = LOG_FILE_DEFAULT_NAME;
                _out = new PrintWriter(new BufferedWriter(new FileWriter(File.separatorChar + "tmp" +
                File.separatorChar + Configuration.getMandatoryProperty(Configuration.SERVER_TYPE_KEY) + logFileName+ dateFormatter2.format(new java.util.Date()) + ".txt")) ,true);
            }
        }
        catch (IOException e)
        {
            //throw new ProdexException("Could not open logfile","Log.getWriter",e);
            e.printStackTrace();
            System.err.println(e.toString());
        }
        return _out;
    }
    
    /**
     * Gets the date string.
     *
     * @return the date string
     */
    private static String getDateString()
    {
        return dateFormatter.format(new Date());
    }
    
    
   /**
    * The main method.
    *
    * @param args the arguments
    */
   public static void main(String[] args)
   {
	   Log.write(logger, "", 1, "hej");
   }
    
    
    
}
