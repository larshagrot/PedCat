/*
 * Trace.java
 *
 * Created on November 27, 2001, 2:25 PM
 */

package se.pedcat.framework.common.model.util;
import java.io.*;
import java.text.*;

import org.slf4j.Logger;

import se.pedcat.framework.common.util.Trace;


// TODO: Auto-generated Javadoc
/**
 * The Class TraceFrontEnd.
 *
 * @author  larh
 * @version
 */
public class TraceFrontEnd extends Trace
{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/** The ELAPSE d_ warnin g_ level. */
	private static double ELAPSED_WARNING_LEVEL  = 15;
    
    
    /** The date formatter. */
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMdd HHmmss");
    
    /**
     * Creates new Trace.
     *
     * @param logger the logger
     * @param object the object
     * @param classAndMethodName the class and method name
     * @param verbose the verbose
     */
    protected TraceFrontEnd(Logger logger,Object object, String classAndMethodName,boolean verbose)
    {
        super(logger,object,classAndMethodName,verbose);
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end()
     */
    public void end()
    {
        end(-1);
    }
 
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.util.Trace#end(int)
     */
    public void end(int returned)
    {
        super.end(returned);
        writeLog(returned);
    }
    
    /**
     * Write log.
     *
     * @param returned the returned
     */
    protected void writeLog(int returned)
    {
        try
        {
            String status = "OK";
            double elapsed = this.getElapsedSeconds();
            PrintWriter printWriter = getWriter();
            if (this.isException())
            {
                status = "EXCEPTION! " ; //+ //CommonUtility.getNestedException(this.getException()).getMessage();
                this.logWarning(status);
            }
            else
            if (elapsed > ELAPSED_WARNING_LEVEL)
            {
                status = "WARNING! CALL ELAPSED " + elapsed + " SECONDS!!!";
                this.logWarning(status);
            }
            printWriter.println(elapsed + ":"  + returned + ":" + status + ":"+ this.getUser() + ":" + getDateString() + ":" + this.getClassAndMethodName() +":"+Thread.currentThread().getName());
        }
        catch (Exception  e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the date string.
     *
     * @return the date string
     */
    private static String getDateString()
    {
        return dateFormatter.format(new java.util.Date());
    }
    
    
    
}
