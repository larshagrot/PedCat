/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;


// TODO: Auto-generated Javadoc
/**
 * The Interface FrameworkUserContextService.
 */
public interface FrameworkUserContextService
{
	
	/**
	 * Gets the user context.
	 *
	 * @param ticket the ticket
	 * @return the user context
	 */
	FrameworkUserContext getUserContext(String ticket);

	/**
	 * Creates the user context.
	 *
	 * @param sessionId the session id
	 * @param anvandarId the anvandar id
	 * @param patientId the patient id
	 * @param distinguishedName the distinguished name
	 * @param systemId the system id
	 * @param leveransId the leverans id
	 * @param SSOTicket the sSO ticket
	 * @param securityTicket the security ticket
	 * @return the string
	 */
	String createUserContext(String sessionId,
                            String anvandarId,
                            String patientId,
                            String distinguishedName,
                            String systemId, 
                            String leveransId,
                            String SSOTicket,
                            String securityTicket );
	
	/**
	 * Removes the context.
	 *
	 * @param ticket the ticket
	 */
	void removeContext(String ticket);

	
}
