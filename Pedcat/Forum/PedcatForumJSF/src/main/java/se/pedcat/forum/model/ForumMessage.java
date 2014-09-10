/**
 * 
 */
package se.pedcat.forum.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import se.pedcat.framework.common.model.GenericCarrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;

/**
 * @author laha
 *
 */
public class ForumMessage extends GenericCarrier implements Comparable<ForumMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class ForumMessageKey extends KeyImpl{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ForumMessageKey(String objectId) {
			super(objectId);
			// TODO Auto-generated constructor stub
		}
		
	}
	public static class ForumMessageQuery extends ModelQuery
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	public Key getKey()
	{
		return new ForumMessageKey(this.getObjectId());
	}
	
	private String userName;
	private String rubrik;
	private int readCount;

	/**
	 *  Returnerar readCountString
	 *
	 * @return the readCountString
	 */
	public String getReadCountString() {
		return ""+ this.readCount;
	}
	/**
	 * Sätter readCountString
	 * @param readCountString the readCountString to set
	 */
	public void setReadCountString(String readCountString) {
		try
		{
			this.readCount = Integer.parseInt(readCountString);
		}
		catch (Exception e)
		{
			this.readCount = 1;
		}
	}
	/**
	 *  Returnerar readCount
	 *
	 * @return the readCount
	 */
	public int getReadCount() {
		return this.readCount;
	}
	/**
	 * Sätter readCount
	 * @param readCount the readCount to set
	 */
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	/**
	 *  Returnerar rubrik
	 *
	 * @return the rubrik
	 */
	public String getRubrik() {
		if (this.rubrik==null || this.rubrik.trim().length()==0)
		{
			if (this.message!=null)
			{
				if (this.message.length()>50)
				{
					return  this.message.substring(0,50) + "...";
				}
				else
				{
					return this.message;
				}
			}
			else
			{
				return "?";
			}
		}
		else
		{
			return this.rubrik; 
		}
	}
	/**
	 * Sätter rubrik
	 * @param rubrik the rubrik to set
	 */
	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}

	private String message;
	private Date   datum;
	private String epost;
	private String ip;
	//private boolean registered;
	private String anmald;
	private String parentId;
	private ForumMessage[] childMessages;
	private ForumMessage parent;
	
	
	


	/**
	 *  Returnerar parent
	 *
	 * @return the parent
	 */
	public ForumMessage getParent() {
		return this.parent;
	}
	/**
	 * Sätter parent
	 * @param parent the parent to set
	 */
	public void setParent(ForumMessage parent) {
		this.parent = parent;
	}
	/**
	 *  Returnerar parentId
	 *
	 * @return the parentId
	 */
	public String getParentId() {
		return this.parentId;
	}
	/**
	 * Sätter parentId
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 *  Returnerar childMessages
	 *
	 * @return the childMessages
	 */
	public ForumMessage[] getChildMessages() {
		return this.childMessages;
	}
	/**
	 * Sätter childMessages
	 * @param childMessages the childMessages to set
	 */
	public void setChildMessages(ForumMessage[] childMessages) {
		this.childMessages = childMessages;
	}
	/**
	 *  Returnerar anmald
	 *
	 * @return the anmald
	 */
	public String getAnmald() {
		return this.anmald;
	}
	/**
	 * Sätter anmald
	 * @param anmald the anmald to set
	 */
	public void setAnmald(String anmald) {
		this.anmald = anmald;
	}
	/**
	 *  Returnerar registered
	 *
	 * @return the registered
	 */
	public boolean isRegistered() {
		return this.anmald!=null && this.anmald.equals("1");
	}
	/**
	 * Sätter registered
	 * @param registered the registered to set
	 */
	public void setRegistered(boolean registered) {
		this.setAnmald(registered?"1":"0");
	}
	/**
	 *  Returnerar ip
	 *
	 * @return the ip
	 */
	public String getIp() {
		return this.ip;
	}
	/**
	 * Sätter ip
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 *  Returnerar epost
	 *
	 * @return the epost
	 */
	public String getEpost() {
		return this.epost;
	}
	/**
	 * Sätter epost
	 * @param epost the epost to set
	 */
	public void setEpost(String epost) {
		this.epost = epost;
	}
	/**
	 *  Returnerar datum
	 *
	 * @return the datum
	 */
	public Date getDatum() {
		return this.datum;
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public String getDatumAsString()
	{
		try
		{
			return sdf.format(this.datum);
			
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	/**
	 * Sätter datum
	 * @param datum the datum to set
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	/**
	 *  Returnerar user
	 *
	 * @return the user
	 */
	public String getUserName() {
		return this.userName;
	}
	/**
	 * Sätter user
	 * @param user the user to set
	 */
	public void setUserName(String user) {
		this.userName = user;
	}
	/**
	 *  Returnerar message
	 *
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}
	/**
	 * Sätter message
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ForumMessage o) {

		return this.datum!=null && o.datum!=null?-1*this.datum.compareTo(o.datum):0;
	}
	
	public boolean isHtml()
	{
		return this.message!=null && this.message.contains("<a href") ;
	}

	public boolean isReply()
	{
		return this.parentId!=null;
	}
	/**
	 * @return
	 */
	public boolean isHasParent() {
		// TODO Auto-generated method stub
		return this.parentId!=null && this.parentId.trim().length()>0;
	}
	public boolean isHasChildren() {
		// TODO Auto-generated method stub
		return this.childMessages!=null && this.childMessages.length>0;
	}

	public int getAntalSvar()
	{
		return this.childMessages!=null?this.childMessages.length:0;
	}
	public String getRubrikShort()
	{
		String rubrik = this.getRubrik();
		if (rubrik!=null)
		{
			if (rubrik.trim().length()>30)
			{
				return rubrik.substring(0,30) + "...";
			}
			else
			{
				return rubrik;
			}
		}
		else
		{
			return "Utan rubrik";
		}
	}
	
	public String getMessageShort()
	{
		String message = this.getMessage();
		if (message!=null)
		{
			if (message.trim().length()>50)
			{
				return message.substring(0,50) + "...";
			}
			else
			{
				return message;
			}
		}
		else
		{
			return "Utan meddelande?";
		}
	}
	
}
