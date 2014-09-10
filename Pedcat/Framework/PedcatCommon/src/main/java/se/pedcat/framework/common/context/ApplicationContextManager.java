/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * Håller context för pågående anrop.
 *
 * @author Administrator
 */
public class ApplicationContextManager
{
	
	/** The contexts. */
	private Map<String,ApplicationContext> contexts = Collections.synchronizedMap(new HashMap<String,ApplicationContext>());
	
	/** The instance. */
	private static ApplicationContextManager theInstance = new ApplicationContextManager(); 
	
	/**
	 * Gets the single instance of ApplicationContextManager.
	 *
	 * @return single instance of ApplicationContextManager
	 */
	public static ApplicationContextManager getInstance()
	{
		return theInstance;
	}
	
	/**
	 * Returnerar nuvarande context. Skapar en om det inte finns någon förut
	 *
	 * @return the current context
	 */
	public ApplicationContext getCurrentContext()
	{
		String threadId = Thread.currentThread().getName();
		ApplicationContext currentContext = (ApplicationContext) contexts.get(threadId);
		if (currentContext==null)
		{
			
			currentContext = new ApplicationContext();
			contexts.put(threadId,currentContext);
		}
		return currentContext;
	}
	
	/**
	 * Sets the current context.
	 *
	 * @param ac the new current context
	 */
	public void setCurrentContext(ApplicationContext ac)
	{
		String threadId = Thread.currentThread().getName();
		contexts.put(threadId,ac);
	}
	
	/**
	 * Removes the current context.
	 */
	public void removeCurrentContext()
	{
		String threadId = Thread.currentThread().getName();
		ApplicationContext currentContext = (ApplicationContext) contexts.remove(threadId);
		if (currentContext!=null)
		{
			currentContext.clear();
		}
		currentContext = null;
	}

	/**
	 * Adds the response statistics.
	 *
	 * @param key the key
	 * @param millisecs the millisecs
	 */
	public void addResponseStatistics(String key,long millisecs)
	{
		this.getCurrentContext().addResponseStatistics(key, millisecs);
	}
	
	/**
	 * Adds the message key.
	 *
	 * @param key the key
	 * @param info the info
	 */
	public void addMessageKey(String key, String info)
	{
		this.getCurrentContext().addMessageKey(key, info);
	}

	/**
	 * Gets the message keys.
	 *
	 * @return the message keys
	 */
	public String[] getMessageKeys()
	{
		return this.getCurrentContext().getMessageKeys();
	}

	/**
	 * Nolla context för tråden.
	 */
	public void clearCurrentContext()
	{
		this.getCurrentContext().clear();
		
	}

	/**
	 * Returnera all messages i trådens context.
	 *
	 * @return the message map
	 */
	public Map getMessageMap()
	{
		return  this.getCurrentContext().getMessageMap();
	}

	/**
	 * Lägg till message för nuvarande tråds.
	 *
	 * @param messageMap the message map
	 */
	public void addMessageMap(Map messageMap)
	{
		this.getCurrentContext().addMessageMap(messageMap);
		
	}
	
	/**
	 * Har denna tråd context?.
	 *
	 * @return true, if is checks for context
	 */
	public boolean isHasContext()
	{
		String threadId = Thread.currentThread().getName();
		return contexts.containsKey(threadId);
	}
	
	/**
	 * Antal context just nu.
	 *
	 * @return the context count
	 */
	public  int getContextCount()
	{
		return this.contexts.size();
	}
	
	
	/**
	 * Gets the contexts.
	 *
	 * @return the contexts
	 */
	public Map<String,ApplicationContext> getContexts()
	{
		return this.contexts;
	}

	/**
	 * Checks if is readonly.
	 *
	 * @return true, if is readonly
	 */
	public boolean isReadonly() {
		return  (this.isHasContext() && this.getCurrentContext().isReadonly());
	}
}
