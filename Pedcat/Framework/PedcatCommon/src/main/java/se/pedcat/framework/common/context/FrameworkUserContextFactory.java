/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.context;



// TODO: Auto-generated Javadoc
/**
 * A factory for creating FrameworkUserContext objects.
 */
public class FrameworkUserContextFactory
{
	
	/** The user context service. */
	private static FrameworkUserContextService userContextService = new FrameworkUserContextServiceImpl();

	static
	{
		String className = "FrameworkUserContextServiceImpl";
		Class userContextServiceClass = null;
		try
		{
			userContextServiceClass = Class.forName(className);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (userContextServiceClass != null)
		{
			try
			{
				userContextService = (FrameworkUserContextService) userContextServiceClass.newInstance();
			}
			catch (InstantiationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets the user context service.
	 *
	 * @return the user context service
	 */
	public static FrameworkUserContextService getUserContextService()
	{
		return userContextService;
	}
}
