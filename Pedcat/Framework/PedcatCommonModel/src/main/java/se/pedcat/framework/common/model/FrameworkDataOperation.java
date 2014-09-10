/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.io.Serializable;
import java.lang.reflect.Array;

import se.pedcat.framework.common.context.ApplicationContext;
import se.pedcat.framework.common.model.FrameworkOperation;


// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkDataOperation.
 *
 * @param <DataType> the generic type
 */
public class FrameworkDataOperation<DataType> extends FrameworkOperation implements Serializable {
	
	/** The data. */
	private DataType[]     data;
	
	/**
	 * Instantiates a new framework data operation.
	 *
	 * @param data the data
	 * @param ac the ac
	 */
	public FrameworkDataOperation(DataType[] data, ApplicationContext ac) {
		super(ac);
		this.data = data;
		
	}
	
	/**
	 * Instantiates a new framework data operation.
	 *
	 * @param classObject the class object
	 * @param data the data
	 * @param ac the ac
	 */
	public FrameworkDataOperation(Class classObject,DataType data, ApplicationContext ac) {
		super(ac);
		this.data = ((DataType[])Array.newInstance(classObject, 1));
		this.data[0] = data;
		
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public DataType[] getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(DataType[] data) {
		this.data = data;
	}
	
	

}
