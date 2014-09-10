/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.service;

import se.pedcat.framework.common.context.ApplicationContext;
import se.pedcat.framework.common.model.RemoteResponse;



// TODO: Auto-generated Javadoc
/**
 * The Interface RemoteCallService.
 */
public interface RemoteCallService extends FrameworkService {
	
	/**
	 * Call.
	 *
	 * @param ac the ac
	 * @param interfaceClassName the interface class name
	 * @param methodName the method name
	 * @param parameterTypes the parameter types
	 * @param parameters the parameters
	 * @return the remote response
	 */
	RemoteResponse call(ApplicationContext ac,String interfaceClassName, String methodName,
			Class<?>[] parameterTypes, Object[] parameters);
	
}
