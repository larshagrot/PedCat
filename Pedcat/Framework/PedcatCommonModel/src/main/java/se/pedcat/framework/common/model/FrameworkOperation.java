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
 * The Class FrameworkOperation.
 */
public class FrameworkOperation implements Serializable {
	
	/** The application context. */
	private ApplicationContext applicationContext;
	
	/**
	 * Instantiates a new framework operation.
	 *
	 * @param ac the ac
	 */
	public FrameworkOperation( ApplicationContext ac) {
		super();
	
		this.applicationContext = ac;
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}
	
	/**
	 * Sets the application context.
	 *
	 * @param ac the new application context
	 */
	public void setApplicationContext(ApplicationContext ac) {
		this.applicationContext = ac;
	}
	

}
