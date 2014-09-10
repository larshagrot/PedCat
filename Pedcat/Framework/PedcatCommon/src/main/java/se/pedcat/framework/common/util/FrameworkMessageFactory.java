/*
 * 
 */

package se.pedcat.framework.common.util;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;



// TODO: Auto-generated Javadoc
/**
 * A factory for creating FrameworkMessage objects.
 *
 * @author  larh
 * @version
 */
public class FrameworkMessageFactory  
{
    
    /** The Constant DEFAULT_BUNDLE. */
    public static final String DEFAULT_BUNDLE = "se.pedcat.framework.common.util.FrameworkResourceBundle";
	
	/** The message number. */
	private static int messageNumber = 0;
    
	/** The message map. */
	private static Map<Integer,String> messageMap = new HashMap<Integer,String>(); 
    
    /** The factories. */
    private static Map<String,FrameworkMessageFactory> factories = new HashMap<String,FrameworkMessageFactory>();  

    /** The Constant formatter. */
    private static final MessageFormat formatter = new MessageFormat("");
    
    /** The property file. */
    private String propertyFile;
    
    /** The resourcebundle. */
    private ResourceBundle resourcebundle = null;
    
    /** The locale. */
    private Locale locale = null;
    
    /**
     * Creates new MessageFactory.
     *
     * @param propertyFile the property file
     * @param locale the locale
     */
    public FrameworkMessageFactory(String propertyFile,Locale locale) 
    {
    	this.propertyFile = propertyFile;
    	this.locale = locale;
    }
    
    /**
     * Gets the single instance of FrameworkMessageFactory.
     *
     * @return single instance of FrameworkMessageFactory
     */
    public static FrameworkMessageFactory getInstance()
    {
    	return getInstance(FrameworkMessageFactory.class,DEFAULT_BUNDLE);
    }
    
    /**
     * Gets the single instance of FrameworkMessageFactory.
     *
     * @param factory the factory
     * @param defaultBundle the default bundle
     * @return single instance of FrameworkMessageFactory
     */
    private static FrameworkMessageFactory getInstance(
			Class<FrameworkMessageFactory> factory, String defaultBundle) {
		return getInstance(factory,defaultBundle,null);
	}

	/**
	 * Gets the single instance of FrameworkMessageFactory.
	 *
	 * @param propertyFile the property file
	 * @return single instance of FrameworkMessageFactory
	 */
	public static FrameworkMessageFactory getInstance(String propertyFile)
    {
    	return getInstance(FrameworkMessageFactory.class,propertyFile,null);
    }

    /**
     * Gets the single instance of FrameworkMessageFactory.
     *
     * @param factory the factory
     * @param propertyFile the property file
     * @param locale the locale
     * @return single instance of FrameworkMessageFactory
     */
    public static FrameworkMessageFactory getInstance(Class<?> factory,String propertyFile,Locale locale)
    {
    	if (!factories.containsKey(propertyFile))
    	{
    		try {
				factories.put(propertyFile,(FrameworkMessageFactory) factory.getConstructor(new Class<?>[]{String.class,Locale.class}).newInstance(new Object[]{propertyFile,locale}));
			} catch (Exception e) {
				
				throw new FrameworkException(e);
			}
    	}
    	return factories.get(propertyFile);
    }
    
    /**
     * Gets the single instance of FrameworkMessageFactory.
     *
     * @param propertyFile the property file
     * @param locale the locale
     * @return single instance of FrameworkMessageFactory
     */
    public static FrameworkMessageFactory getInstance(String propertyFile,Locale locale)
    {
    	return getInstance(FrameworkMessageFactory.class,propertyFile,locale);
    }
    
    
    /**
     * Gets the next number.
     *
     * @return the next number
     */
    static int getNextNumber()
    {
        return ++messageNumber;
    }
    
    /**
     * Gets the message.
     *
     * @param code the code
     * @return the message
     */
    public String getMessage(int code)
    {
        return (String) messageMap.get(new Integer(code));
    }
    
    /**
     * Gets the message.
     *
     * @param key the key
     * @return the message
     */
    public String getMessage(String key)
    {
    	return this.getMessage(key, null);
    }
    
    /**
     * Gets the message.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the message
     */
    public String getMessage(String key,String defaultValue)
    {
    	try
    	{
	    	if (this.resourcebundle==null)
	    	{
	    		//if (false && this.locale!=null)
	    		//{
	    		//	this.resourcebundle = ResourceBundle.getBundle(this.propertyFile,this.locale);
	    		//}
	    		//else
	    		//{
	    			this.resourcebundle = ResourceBundle.getBundle(this.propertyFile);
	    		//}
	    	}
	    	if (this.resourcebundle!=null)
	    	{
		    	String msg = this.resourcebundle.getString(key);
		    	if (msg==null && defaultValue!=null)
		    	{
		    		msg = defaultValue;
		    	}
		    		
		    		
		    	return msg;
	    	}
	    	else
	    	{
	    		return "No bundle";
	    	}
	    }
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return "";
    		//return "Exc No bundle " + e.getMessage();
    		
    	}
    }
    
	/**
	 * Creates a compound message for default locale.
	 *
	 * @param key Key to message template
	 * @param arguments Arguments to apply to message template.
	 * @return formatted message
	 */
	public String getString(String key, Object[] arguments) {
		
		String msgTemplate = this.getMessage(key);

		formatter.applyPattern(msgTemplate);
		String msg = formatter.format(arguments);

		return msg;
	}

    
    /**
     * Adds the.
     *
     * @param code the code
     * @param message the message
     */
    public void add(int code,String message)
    {
        messageMap.put(new Integer(code),message);
    }
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
    	System.out.println(FrameworkMessageFactory.getInstance().getMessage("se.r7earkiv.common.label.choose"));
    }

}
