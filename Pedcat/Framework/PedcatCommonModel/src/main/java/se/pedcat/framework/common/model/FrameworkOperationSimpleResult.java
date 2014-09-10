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
 * The Class FrameworkOperationSimpleResult.
 *
 * @param <DataType> the generic type
 */
public class FrameworkOperationSimpleResult<DataType> implements java.io.Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The data type. */
	private DataType dataType;
	
	/** The ac. */
	private ApplicationContext ac;
	
	/**
	 * Instantiates a new framework operation simple result.
	 *
	 * @param dataType the data type
	 * @param ac the ac
	 */
	public FrameworkOperationSimpleResult(DataType dataType, ApplicationContext ac) {
		super();
		this.dataType = dataType;
		this.ac = ac;
	}
	
	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public DataType getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Gets the ac.
	 *
	 * @return the ac
	 */
	public ApplicationContext getAc() {
		return ac;
	}
	
	/**
	 * Sets the ac.
	 *
	 * @param ac the new ac
	 */
	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}
}
