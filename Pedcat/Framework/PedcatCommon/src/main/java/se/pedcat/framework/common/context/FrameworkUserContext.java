/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;


// TODO: Auto-generated Javadoc
/**
 * The Interface FrameworkUserContext.
 */
public interface FrameworkUserContext 
{
	
	/**
	 * Gets the anvandar id.
	 *
	 * @return the anvandar id
	 */
	public String getAnvandarId();
	
	/**
	 * Sets the anvandar id.
	 *
	 * @param anvandarId the new anvandar id
	 */
	public void setAnvandarId(String anvandarId);
	
	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public String getPatientId();
	
	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(String patientId);
	
	/**
	 * Gets the distinguished name.
	 *
	 * @return the distinguished name
	 */
	public String getDistinguishedName();
	
	/**
	 * Sets the distinguished name.
	 *
	 * @param distinguishedName the new distinguished name
	 */
	public void setDistinguishedName(String distinguishedName);
	
	/**
	 * Gets the system id.
	 *
	 * @return the system id
	 */
	public String getSystemId();
	
	/**
	 * Sets the system id.
	 *
	 * @param systemId the new system id
	 */
	public void setSystemId(String systemId);
	
	/**
	 * Gets the leverans id.
	 *
	 * @return the leverans id
	 */
	public String getLeveransId();
	
	/**
	 * Sets the leveransobjekt ids.
	 *
	 * @param leveransId the new leveransobjekt ids
	 */
	public void setLeveransobjektIds(String leveransId);
	
	/**
	 * Gets the sSO ticket.
	 *
	 * @return the sSO ticket
	 */
	public String getSSOTicket();
	
	/**
	 * Sets the sSO ticket.
	 *
	 * @param SSOTicket the new sSO ticket
	 */
	public void setSSOTicket(String SSOTicket);
	
	/**
	 * Gets the security ticket.
	 *
	 * @return the security ticket
	 */
	public String getSecurityTicket();
	
	/**
	 * Sets the security ticket.
	 *
	 * @param securityTicket the new security ticket
	 */
	public void setSecurityTicket(String securityTicket);
	
}
