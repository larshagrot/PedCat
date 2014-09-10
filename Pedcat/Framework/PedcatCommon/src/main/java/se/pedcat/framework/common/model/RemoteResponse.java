/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.io.Serializable;

import se.pedcat.framework.common.context.ApplicationContext;


// TODO: Auto-generated Javadoc
/**
 * The Class RemoteResponse.
 */
public class RemoteResponse implements Serializable {
	
	/**
	 * Instantiates a new remote response.
	 *
	 * @param returnedObject the returned object
	 * @param applicationContext the application context
	 */
	public RemoteResponse(Object returnedObject,
			ApplicationContext applicationContext) {
		super();
		this.returnedObject = returnedObject;
		this.applicationContext = applicationContext;
	
	}
	
	/**
	 * Instantiates a new remote response.
	 *
	 * @param returnedObject the returned object
	 * @param applicationContext the application context
	 */
	public RemoteResponse(Exception returnedObject,
			ApplicationContext applicationContext) {
		super();
		this.returnedException = returnedObject;
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Instantiates a new remote response.
	 */
	public RemoteResponse() {
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The returned object. */
	private Object returnedObject;
	
	/** The application context. */
	private ApplicationContext applicationContext;
	
	/** The returned exception. */
	private Throwable returnedException;
	
	/**
	 * Gets the returned exception.
	 *
	 * @return the returned exception
	 */
	public Throwable getReturnedException() {
		return returnedException;
	}
	
	/**
	 * Sets the returned exception.
	 *
	 * @param returnedException the new returned exception
	 */
	public void setReturnedException(Throwable returnedException) {
		this.returnedException = returnedException;
	}
	
	/**
	 * Gets the returned object.
	 *
	 * @return the returned object
	 */
	public Object getReturnedObject() {
		return returnedObject;
	}
	
	/**
	 * Sets the returned object.
	 *
	 * @param returnedObject the new returned object
	 */
	public void setReturnedObject(Object returnedObject) {
		this.returnedObject = returnedObject;
	}
	
	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
