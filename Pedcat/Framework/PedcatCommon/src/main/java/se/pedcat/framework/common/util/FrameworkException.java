/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.util;





// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkException.
 */
public class FrameworkException  extends RuntimeException{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The message key. */
	private String messageKey;
	
	/** The parameters. */
	private String[] parameters;
	
	/**
	 * Instantiates a new framework exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 * @param messageKey the message key
	 * @param parameters the parameters
	 */
	public FrameworkException(String message, Throwable throwable,String messageKey,String[] parameters)
	{
		super(message,throwable);
		this.setMessageKey(messageKey);
		this.setParameters(parameters);
		
	}
	
	/**
	 * Instantiates a new framework exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 * @param messageKey the message key
	 */
	public FrameworkException(String message, Throwable throwable,String messageKey)
	{
		this(message,throwable,messageKey,null);
		
		
	}
	
	/**
	 * Instantiates a new framework exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public FrameworkException(String message, Throwable throwable)
	{
		this(message,throwable,null);
	}
	
	/**
	 * Instantiates a new framework exception.
	 *
	 * @param message the message
	 */
	public FrameworkException(String message)
	{
		this(message,null,null);
	}
    
    /**
     * Instantiates a new framework exception.
     *
     * @param throwable the throwable
     */
    public FrameworkException(Throwable throwable)
	{
		this(null,throwable,null);
	}
    
	/**
	 * Instantiates a new framework exception.
	 *
	 * @param string the string
	 * @param propertyMissingKey the property missing key
	 */
	public FrameworkException(String string, String propertyMissingKey) {
		this(string,null,propertyMissingKey,null);
	}
	
	/**
	 * Gets the message key.
	 *
	 * @return the messageKey
	 */
	public String getMessageKey() {
		return messageKey;
	}
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public String[] getParameters() {
		return parameters;
	}
	
	/**
	 * Sets the parameters.
	 *
	 * @param parameters the parameters to set
	 */
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Sets the message key.
	 *
	 * @param messageKey the messageKey to set
	 */
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	
	
	
}
