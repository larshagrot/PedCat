/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;

import java.util.HashMap;
import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * Håller information som hör till ett anrop.
 * 
 * 
 * @author laha
 *
 */
public class ApplicationContext implements java.io.Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The Class ResponseStatistics.
	 */
	class ResponseStatistics implements java.io.Serializable
	{
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The frequence. */
		private int frequence;
		
		/** The millisecs. */
		private long millisecs;
		
		/**
		 * Gets the frequence.
		 *
		 * @return the frequence
		 */
		public int getFrequence() {
			return frequence;
		}
		
		/**
		 * Sets the frequence.
		 *
		 * @param frequence the new frequence
		 */
		public void setFrequence(int frequence) {
			this.frequence = frequence;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return frequence + ":" + (millisecs/1000.0);
		}
		
		/**
		 * Instantiates a new response statistics.
		 *
		 * @param frequence the frequence
		 * @param millisecs the millisecs
		 */
		public ResponseStatistics(int frequence, long millisecs) {
			super();
			this.frequence = frequence;
			this.millisecs = millisecs;
		}
		
		/**
		 * Adds the.
		 *
		 * @param i the i
		 * @param millisecs2 the millisecs2
		 */
		public void add(int i, long millisecs2) {
			this.frequence +=i;
			this.millisecs += millisecs2;
			
		}
	}
	
	/** The message map. */
	private Map<String,String> messageMap = null;
	
	/** The response statistics map. */
	private Map<String,ResponseStatistics> responseStatisticsMap = null;
	
	/** The user name. */
	private String userName = "framework";
	
	/** The ticket. */
	private String ticket;
	
	/** The readonly. */
	private boolean readonly; 

	/**
	 * Clear.
	 */
	public void clear()
	{
		if (this.messageMap != null)
		{
			this.messageMap.clear();
			this.messageMap = null;
		}
		if (this.responseStatisticsMap != null)
		{
			this.responseStatisticsMap.clear();
			this.responseStatisticsMap = null;
		}
		this.userName = null;
	}

	/**
	 * Adds the response statistics.
	 *
	 * @param key the key
	 * @param millisecs the millisecs
	 */
	public void addResponseStatistics(String key,long millisecs)
	{
		ResponseStatistics responseStatistics = null;
		if (this.responseStatisticsMap==null)
		{
			this.responseStatisticsMap = new HashMap<String,ResponseStatistics>();
		}
		else
		{
			responseStatistics = this.responseStatisticsMap.get(key); 
		}
		if (responseStatistics==null)
		{
			this.responseStatisticsMap.put(key,new ResponseStatistics(1,millisecs));
		}
		else
		{
			responseStatistics.add(1,millisecs);
		}
		
	}
	
	/**
	 * Adds the message key.
	 *
	 * @param key the key
	 * @param info the info
	 */
	public void addMessageKey(String key, String info)
	{
		if (this.messageMap == null)
		{
			this.messageMap = new HashMap<String,String>();
		}
		messageMap.put(key, info);
	}

	/**
	 * Adds the message key.
	 *
	 * @param key the key
	 */
	public void addMessageKey(String key)
	{
		this.addMessageKey(key, null);
	}

	/**
	 * Gets the message keys.
	 *
	 * @return the message keys
	 */
	public String[] getMessageKeys()
	{
		if (this.messageMap != null)
		{
			return (String[]) this.messageMap.keySet().toArray(new String[0]);
		}
		else
		{
			return new String[0];
		}
	}

	/**
	 * Gets the message info.
	 *
	 * @param key the key
	 * @return the message info
	 */
	public String getMessageInfo(String key)
	{
		if (this.messageMap != null)
		{
			return (String) this.messageMap.get(key);
		}
		else
		{
			return "";
		}
	}

	/**
	 * Gets the message map.
	 *
	 * @return the message map
	 */
	public Map getMessageMap()
	{
		return this.messageMap;
	}
	
	/**
	 * Adds the message map.
	 *
	 * @param map the map
	 */
	public void addMessageMap(Map map)
	{
		if (map!=null)
		{
			if (messageMap==null)
			{
				messageMap = new HashMap();
			}
			messageMap.putAll(map);
		}
			
	}

	/**
	 * Checks if is checks for messages.
	 *
	 * @return true, if is checks for messages
	 */
	public boolean isHasMessages()
	{
		// TODO Auto-generated method stub
		return this.messageMap!=null && this.messageMap.size()>0;
	}
	
	/**
	 * Checks if is checks for statistics.
	 *
	 * @return true, if is checks for statistics
	 */
	public boolean isHasStatistics()
	{
		// TODO Auto-generated method stub
		return this.responseStatisticsMap!=null && this.responseStatisticsMap.size()>0;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	} 

	/**
	 * Gets the ticket.
	 *
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket==null?this.getUserName():ticket;
	}

	/**
	 * Sets the ticket.
	 *
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Gets the response statistics map.
	 *
	 * @return the response statistics map
	 */
	public Map<String, ResponseStatistics> getResponseStatisticsMap() {
		return responseStatisticsMap;
	}

	/**
	 * Sets the response statistics map.
	 *
	 * @param responseStatisticsMap the response statistics map
	 */
	public void setResponseStatisticsMap(
			Map<String, ResponseStatistics> responseStatisticsMap) {
		this.responseStatisticsMap = responseStatisticsMap;
	}

	/**
	 * Sets the readonly.
	 *
	 * @param b the new readonly
	 */
	public void setReadonly(boolean b) {
		// TODO Auto-generated method stub
		this.readonly = b;
	}

	/**
	 * Returnerar readonly.
	 *
	 * @return the readonly
	 */
	public boolean isReadonly() {
		return this.readonly;
	}

	/**
	 * Removes the message key.
	 *
	 * @param key the key
	 */
	public void removeMessageKey(String key) {
		if (this.messageMap!=null)
		{
			this.messageMap.remove(key);
		}
		
	}


}
