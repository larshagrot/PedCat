/*
 * UserCache.java
 *
 * Created on den 15 februari 2002, 09:43
 */

package se.pedcat.framework.storage.dao;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.context.ApplicationContextManager;
import se.pedcat.framework.common.util.Log;


/**
 *
 *
 *
 *
  *<!--
 * $Revision: 13 $
 * $Log: /prodex-utv/u1.2.2/framework/UserCache.java $
 * 
 * 13    02-05-02 14:40 Larh
 * 
 * 12    02-04-25 16:35 Larh
 *
 *
 *
 *-->
 *
 *
 *
 *
 * @author  larh
 * @version
 */
public class UserCache
{
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(UserCache.class);
    
    /** The user map. */
    static Map userMap = new HashMap();
    
    /** The ip map. */
    static Map ipMap = new HashMap();
    
    /**
     * Creates new UserCache.
     */
    public UserCache()
    {
    }
    
    /**
     * Bind user.
     *
     * @param name the name
     */
    public static void bindUser(String name)
    {
        String threadName = Thread.currentThread().getName();
        int index = threadName.indexOf('-');
        String ipAddress = threadName.substring(index+1);
        ipMap.put(ipAddress,name);
        Log.logMessage(logger,"UserCache.bindUser","Caching user name  " + name + " from " + ipAddress);
    }
    
    /**
     * Gets the user name.
     *
     * @param userObjectId the user object id
     * @return the user name
     */
    public static String getUserName(String userObjectId)
    {
        String name = ApplicationContextManager.getInstance().getCurrentContext().getUserName();
        if (name==null)
        {
        	name = "system";
        }
        return name;
    }
    
    
}
