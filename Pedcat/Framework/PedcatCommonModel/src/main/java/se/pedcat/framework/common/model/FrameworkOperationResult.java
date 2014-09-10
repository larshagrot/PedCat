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
 * The Class FrameworkOperationResult.
 *
 * @param <DataType> the generic type
 */
public class FrameworkOperationResult<DataType> implements java.io.Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The data type. */
	private DataType[] dataType;
	
	/** The ac. */
	private ApplicationContext ac;
	
	/**
	 * Instantiates a new framework operation result.
	 *
	 * @param classObject the class object
	 * @param dataType the data type
	 * @param ac the ac
	 */
	public FrameworkOperationResult(Class<?> classObject,DataType dataType, ApplicationContext ac) {
		super();
		this.dataType = (DataType[]) java.lang.reflect.Array.newInstance(classObject, 1);// new DataType[]{dataType};
		this.dataType[0] = dataType;
		this.ac = ac;
	}
	
	/**
	 * Instantiates a new framework operation result.
	 *
	 * @param type the type
	 * @param currentContext the current context
	 */
	public FrameworkOperationResult(DataType[] type,
			ApplicationContext currentContext) {
		this.dataType = type;
	}
	
	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public DataType[] getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(DataType[] dataType) {
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
