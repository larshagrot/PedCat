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
 * The Class FrameworkUserContextServiceImpl.
 */
public class FrameworkUserContextServiceImpl  implements FrameworkUserContextService
{
	
	/** The user context map. */
	private static Map userContextMap = new HashMap();
	
	/**
	 * Instantiates a new framework user context service impl.
	 */
	public FrameworkUserContextServiceImpl()
	{
		
	}
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#createUserContext(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createUserContext(String sessionId,
	                                String anvandarId,
	                                String patientId,
	                                String distinguishedName,
	                                String systemId, 
	                                String leveransId,
		                                  
	                                String SSOTicket,
	                                String securityTicket)
	{
		FrameworkUserContextImpl userContext = new FrameworkUserContextImpl();
		userContext.setAnvandarId(anvandarId);
		userContext.setPatientId(patientId);
		userContext.setDistinguishedName(distinguishedName);
		userContext.setSystemId(systemId);
		userContext.setLeveransobjektIds(leveransId);
		userContext.setSecurityTicket(securityTicket);
		userContext.setSSOTicket(SSOTicket);
		this.setUserContext(sessionId, userContext);
		return userContext.getSSOTicket(); 
	}

	
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#getUserContext(java.lang.String)
	 */
	public FrameworkUserContext getUserContext(String ticket)
	{
		return (FrameworkUserContext) userContextMap.get(ticket);
	}
	
	/**
	 * Sets the user context.
	 *
	 * @param ticket the ticket
	 * @param userContext the user context
	 */
	protected void setUserContext(String ticket,FrameworkUserContext userContext )
	{
		userContextMap.put(ticket,userContext);
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#removeContext(java.lang.String)
	 */
	public void removeContext(String ticket)
	{
		// TODO Auto-generated method stub
		userContextMap.remove(ticket);
	}

}
