/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;


// TODO: Auto-generated Javadoc
/**
 * Representerar user context.
 *
 * @author laha
 */

public class FrameworkUserContextImpl implements FrameworkUserContext 
{
	
	/** The anvandar id. */
	private String anvandarId;
	
	/** The patient id. */
	private String patientId;
	
	/** The SSO ticket. */
	private String SSOTicket;
	
	/** The security ticket. */
	private String securityTicket;
	
	/** The distinguished name. */
	private String distinguishedName;
	
	/** The system id. */
	private String systemId;
	
	/** The leverans id. */
	private String leveransId;
	
	
	
	/**
	 * Gets the anvandar id.
	 *
	 * @return the anvandarId
	 */
	public String getAnvandarId()
	{
		return anvandarId;
	}
	
	/**
	 * Sets the anvandar id.
	 *
	 * @param anvandarId the anvandarId to set
	 */
	public void setAnvandarId(String anvandarId)
	{
		this.anvandarId = anvandarId;
	}
	
	/**
	 * Gets the distinguished name.
	 *
	 * @return the distinguishedName
	 */
	public String getDistinguishedName()
	{
		return distinguishedName;
	}
	
	/**
	 * Sets the distinguished name.
	 *
	 * @param distinguishedName the distinguishedName to set
	 */
	public void setDistinguishedName(String distinguishedName)
	{
		this.distinguishedName = distinguishedName;
	}
	
	/**
	 * Gets the patient id.
	 *
	 * @return the patientId
	 */
	public String getPatientId()
	{
		return patientId;
	}
	
	/**
	 * Sets the patient id.
	 *
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}
	
	/**
	 * Gets the security ticket.
	 *
	 * @return the securityTicket
	 */
	public String getSecurityTicket()
	{
		return securityTicket;
	}
	
	/**
	 * Sets the security ticket.
	 *
	 * @param securityTicket the securityTicket to set
	 */
	public void setSecurityTicket(String securityTicket)
	{
		this.securityTicket = securityTicket;
	}
	
	/**
	 * Gets the sSO ticket.
	 *
	 * @return the sSOTicket
	 */
	public String getSSOTicket()
	{
		return SSOTicket;
	}
	
	/**
	 * Sets the sSO ticket.
	 *
	 * @param ticket the sSOTicket to set
	 */
	public void setSSOTicket(String ticket)
	{
		SSOTicket = ticket;
	}
	
	/**
	 * Gets the leverans id.
	 *
	 * @return the leveransId
	 */
	public String getLeveransId()
	{
		return leveransId;
	}
	
	/**
	 * Sets the leveransobjekt ids.
	 *
	 * @param leveransId the leveransId to set
	 */
	public void setLeveransobjektIds(String leveransId)
	{
		this.leveransId = leveransId;
	}
	
	/**
	 * Gets the system id.
	 *
	 * @return the systemId
	 */
	public String getSystemId()
	{
		return systemId;
	}
	
	/**
	 * Sets the system id.
	 *
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}
}
