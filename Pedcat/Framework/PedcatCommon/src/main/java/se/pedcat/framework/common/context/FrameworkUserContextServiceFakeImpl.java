/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;


// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkUserContextServiceFakeImpl.
 */
public class FrameworkUserContextServiceFakeImpl implements FrameworkUserContextService
{
	
	
	
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#getUserContext(java.lang.String)
	 */
	public FrameworkUserContext getUserContext(String ticket)
	{
		
		FrameworkUserContextImpl userContext = new FrameworkUserContextImpl();
		userContext.setAnvandarId("111111111111");
		userContext.setPatientId("111111111111");
		userContext.setDistinguishedName("distinguishedName");
		userContext.setSystemId("verifikationstest");
		userContext.setLeveransobjektIds("leveransId");
		userContext.setSecurityTicket("securityTicket");
		userContext.setSSOTicket("SSOTicket");
		
		
		return userContext;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#createUserContext(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createUserContext(String sessionId, String anvandarId, String patientId, String distinguishedName, String systemId, String leveransId, String SSOTicket, String securityTicket)
	{
		// TODO Auto-generated method stub
		String t = ""+System.currentTimeMillis();
		FrameworkUserContextImpl userContext = new FrameworkUserContextImpl();
		userContext.setAnvandarId("111111111111");
		userContext.setPatientId("111111111111");
		userContext.setDistinguishedName("distinguishedName");
		userContext.setSystemId("verifikationstest");
		userContext.setLeveransobjektIds("leveransId");
		userContext.setSecurityTicket(t);
		userContext.setSSOTicket(t);

		return t;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.context.FrameworkUserContextService#removeContext(java.lang.String)
	 */
	public void removeContext(String ticket)
	{
		// TODO Auto-generated method stub
		
	}


}
