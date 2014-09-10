/*
 * Configuration.java
 *
 * Created on November 27, 2001, 2:09 PM
 */

package se.pedcat.framework.common.util;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// TODO: Auto-generated Javadoc
/**
 * Handles configuration properties
 * 
 * 
 * <!--
 * $Revision: 14 $
 * 
 * 
 * 14    02-05-14 14:39 Larh
 * 
 * 13    02-04-22 14:56 Larh
 * 
 * 
 * -->.
 *
 * @author  larh
 * @version
 */
public class Configuration extends ConfigurationProperties
{
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	/** The Constant IS_LOG_BUFFERING_KEY. */
	public static final String IS_LOG_BUFFERING_KEY = "";

	/** The Constant SERVER_TYPE_KEY. */
	public static final String SERVER_TYPE_KEY = "1";

	/** The Constant IS_LOG_FILE_KEY. */
	public static final String IS_LOG_FILE_KEY = "2";

	/** The Constant IS_LOG_STDOUT_KEY. */
	public static final String IS_LOG_STDOUT_KEY = "3";

	

	/** Default name for property file. */
    private static String PROPERTY_FILE_NAME = "portalframework.properties";
    
    /** The _properties. */
    private static Properties  _properties;
    
    static
    {
        loadDefaultProperties();
    }
    
    
    /**
     * Creates new Configuration.
     */
    private Configuration()
    {
    }
    
    /**
     * Gets the mandatory property.
     *
     * @param key the key
     * @return the mandatory property
     */
    public static String  getMandatoryProperty(String key)
    {
        String value = getProperty(key);
        if (value==null)
        {
            throw new FrameworkException(key + " is missing supposed to exist in " + PROPERTY_FILE_NAME, FrameworkMessage.PROPERTY_MISSING_KEY);
        }
        return value;
    }
    
    
    /**
     * Gets the property.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the property
     */
    public static String getProperty(String key,String defaultValue)
    {
        Properties properties = getProperties();
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets the property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(String key)
    {
        Properties properties = getProperties();
        return properties.getProperty(key);
    }
    
    
    /**
     * Gets the long property.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the long property
     */
    public static long getLongProperty(String key,long defaultValue)
    {
        long returnValue = defaultValue;
        Properties properties = getProperties();
        String value = properties.getProperty(key);
        if (value!=null)
        {
            returnValue = Long.parseLong(value);
        }
        return returnValue;
    }
    
    /**
     * Gets the int property.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the int property
     */
    public static int getIntProperty(String key,int defaultValue)
    {
        int returnValue = defaultValue;
        Properties properties = getProperties();
        String value = properties.getProperty(key);
        if (value!=null)
        {
            value.trim();
            try
            {
                returnValue = Integer.parseInt(value);
            }
            catch(Exception e)
            {
                Log.logError(logger,"Configuration","Could not parse " + value);
            }
        }
        return returnValue;
    }
    
    /**
     * Gets the boolean property.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the boolean property
     */
    public static boolean getBooleanProperty(String key,boolean defaultValue)
    {
        boolean returnValue = defaultValue;
        Properties properties = getProperties();
        String value = properties.getProperty(key);
        if (value!=null)
        {
            returnValue = value.equalsIgnoreCase("true");
        }
        return returnValue;
    }
    
    
    /**
     * Sets the properties.
     *
     * @param properties the new properties
     */
    public static void setProperties(Properties properties)
    {
        _properties = properties;
        try
        {
            dump(_properties);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public static Properties getProperties()
    {
        if (_properties == null)
        {
            
            _properties = new Properties();
            try
            {
                InputStream inputStream = getInputStream();
                if (inputStream!=null)
                {
                    System.out.println("Loading properties...");
                    _properties.load(inputStream);
                    dump(_properties);
                }
                else
                {
                    System.err.println("Not  loading properties...");
                }
            }
            catch (Exception e)
            {
                System.err.println("Fatal error in Configuration.getProperties");
                
            }
        }
        return _properties;
    }
    
    /**
     * Gets the input stream.
     *
     * @return the input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static InputStream getInputStream() throws IOException
    {
        // ClassLoader.getSystemResourceAsStream(PROPERTY_FILE_NAME);
        InputStream inputStream = null;
        try
        {
            File file = new java.io.File(PROPERTY_FILE_NAME);
            inputStream =  new BufferedInputStream(new FileInputStream(file));
        }
        catch (Exception e)
        {
            // Ignore
        }
        return inputStream;
    }
    
    /**
     * Adds the.
     *
     * @param key the key
     * @param value the value
     */
    public static void add(String key,String value)
    {
        Properties properties = getProperties();
        if (!properties.containsKey(key))
        {
            Log.logMessage(logger,"add","ADDING " + key + "\t=\t" + value + " (DEFAULT VALUE) \n");
            properties.put(key, value);
        }
        else
        {
            
        }
    }
    
    /**
     * Dump.
     *
     * @param properties the properties
     * @throws IllegalAccessException the illegal access exception
     */
    private static void dump(Properties properties) throws java.lang.IllegalAccessException
    {
        StringBuffer stringBuffer = new StringBuffer("\n ***** CONFIGURATION PROPERTIES *****\n");
        Enumeration enums = properties.keys();
        while (enums.hasMoreElements())
        {
            String key = (String) enums.nextElement();
            stringBuffer.append(key);
            stringBuffer.append("\t\t=");
            stringBuffer.append(properties.getProperty(key));
            stringBuffer.append("\n");
        }
        stringBuffer.append("\n\n **************' CHECKING MANDATORY PROPERTIES SUPPOSED TO EXIST IN " + PROPERTY_FILE_NAME + "\n");
        java.lang.reflect.Field[] fields =  MandatoryConfigurationProperties.class.getFields();
        for(int index=0;index<fields.length;index++)
        {
            if (properties.containsKey(fields[index].get(null)))
            {
                stringBuffer.append(fields[index].get(null));
                stringBuffer.append("\t\t=");
                stringBuffer.append(properties.getProperty((String)fields[index].get(null)));
                stringBuffer.append("\n");
            }
            else
            {
                stringBuffer.append(fields[index].get(null));
                stringBuffer.append("\t\t=");
                stringBuffer.append(" *** MISSING ***\n");
                stringBuffer.append("\n");
            }
        }
        stringBuffer.append("**********************************************************************");
        Log.logMessage(logger,"dump",stringBuffer.toString());
        
    }
    
    
    /**
     * Load default properties.
     */
    public static void loadDefaultProperties()
    {
        Field[] fields = ConfigurationProperties.class.getFields();
        for(int index=0;index<fields.length;index++)
        {
            String fieldName = fields[index].getName();
            if (fieldName.endsWith("_KEY"))
            {
                String defaultFieldName = fieldName.substring(0,fieldName.length()-"_KEY".length()) + "_DEFAULT";
                try
                {
                    Field defaultField = ConfigurationProperties.class.getField(defaultFieldName);
                    if (defaultField!=null)
                    {
                        add(""+fields[index].get(null),"" + defaultField.get(null));
                    }
                }
                catch (java.lang.NoSuchFieldException e)
                {
                    Log.logMessage(logger,"Configuration.initialize","Could not find " + defaultFieldName);
                    // Dont care
                }
                catch (java.lang.IllegalAccessException e)
                {
                    e.printStackTrace();
                    // Dont care
                }
            }
        }
    }
    
    /**
     * Reload properties.
     */
    public static void reloadProperties()
    {
        _properties = null;
        loadDefaultProperties();
    }
    
    /**
     * Start up.
     */
    public static void startUp()
    {
        //Log.logMessage("startUp","Starting up server....");
        //QueueUtility.clientLog("Starting up server....");
    }
    
}
