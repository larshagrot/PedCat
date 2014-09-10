/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import se.pedcat.framework.common.context.ApplicationContext;


// TODO: Auto-generated Javadoc
/**
 * The Class GenericRemoteResponse.
 *
 * @param <Type> the generic type
 */
public class GenericRemoteResponse<Type> extends RemoteResponse {

	/**
	 * Instantiates a new generic remote response.
	 *
	 * @param returnedObject the returned object
	 * @param applicationContext the application context
	 */
	public GenericRemoteResponse(Type returnedObject,
			ApplicationContext applicationContext) {
		super(returnedObject, applicationContext);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the returned type.
	 *
	 * @return the returned type
	 */
	@SuppressWarnings("unchecked")
	public Type getReturnedType()
	{
		return (Type) super.getReturnedObject();
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

}
