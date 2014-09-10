/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class ValueObject.
 */
public class ValueObject implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The object id. */
	private String objectId;

	/**
	 * Returnerar objectId.
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * Sätter objectId.
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		
            this.objectId = objectId;
	}
	
	/**
	 * Sets the object id.
	 *
	 * @param objectId the new object id
	 */
	public void setObjectId(int objectId) {
		this.objectId = ""+objectId;
	}
	
	/**
	 * Sets the object id as int.
	 *
	 * @param objectId the new object id as int
	 */
	public void setObjectIdAsInt(int objectId) {
		this.setObjectId(objectId);
	}
	
	/**
	 * Gets the empty string.
	 *
	 * @return the empty string
	 */
	public String getEmptyString()
	{
		return "";
	}
	
	/**
	 * Gets the object id as int.
	 *
	 * @return the object id as int
	 */
	public int getObjectIdAsInt()
	{
		try
		{
			return this.objectId!=null?Integer.parseInt(this.objectId):-1;
		}
		catch (Exception e)
		{
			return -1;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return super.toString();
		//return R7EArkivUtility.dumpProperties(this);
	}
	
	/**
	 * Clear.
	 */
	public void clear()
	{
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.objectId == null) ? 0 : this.objectId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValueObject other = (ValueObject) obj;
		if (this.objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!this.objectId.equals(other.objectId))
			return false;
		return true;
	}
}
