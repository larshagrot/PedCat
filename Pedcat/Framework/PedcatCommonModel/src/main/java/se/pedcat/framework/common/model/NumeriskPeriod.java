/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.io.Serializable;


import se.pedcat.framework.common.model.ModelUtility;


// TODO: Auto-generated Javadoc
/**
 * The Class NumeriskPeriod.
 */
public class NumeriskPeriod implements Serializable {

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result
				+ ((this.start == null) ? 0 : this.start.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NumeriskPeriod other = (NumeriskPeriod) obj;
		if (this.end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!this.end.equals(other.end)) {
			return false;
		}
		if (this.start == null) {
			if (other.start != null) {
				return false;
			}
		} else if (!this.start.equals(other.start)) {
			return false;
		}
		return true;
	}



	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The start. */
	private Integer start;
	
	/** The end. */
	private Integer end;	


	
	/**
	 * Instantiates a new numerisk period.
	 */
	public NumeriskPeriod()
	{
		
	}
	
	/**
	 * Instantiates a new numerisk period.
	 *
	 * @param nums the nums
	 */
	public NumeriskPeriod(String nums)
	{
		if (ModelUtility.isNotEmpty(nums))
		{
			 
			if (nums.indexOf("--")!=-1)
			{
				String[] nummer = nums.split("--");
				if (nums.startsWith("--") && nummer.length==1)
				{
					this.setEndAsString(nummer[0]);
				}
				else
					if (nums.endsWith("--") && nummer.length==1)
					{
						this.setStartAsString(nummer[0]);
					}	
					
					else
					if (nummer.length==2)
					{
							this.setStartAsString(nummer[0]);
							this.setEndAsString(nummer[1]);
					}
			}
			else
			{
				this.setStartAsString(nums);
			}
		}
	}
	
	/**
	 * Instantiates a new numerisk period.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public NumeriskPeriod(String start,String end)
	{
		this.setStartAsString(start);
		this.setEndAsString(end);
	}

	/**
	 * Instantiates a new numerisk period.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public NumeriskPeriod(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}

	
	/**
	 * Returnerar start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
	    if (this.start == null) {
	        return Integer.MIN_VALUE;
	    }
		return this.start;
	}

	/**
	 * Sätter start.
	 *
	 * @param start the start to set
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * Returnerar end.
	 *
	 * @return the end
	 */
	public Integer getEnd() {
	    if (this.end == null) {
	        return Integer.MAX_VALUE;
	    }
		return this.end;
	}

	/**
	 * Sätter end.
	 *
	 * @param end the end to set
	 */
	public void setEnd(Integer end) {
		this.end = end;
	}

	/**
	 * Returnerar startAsString.
	 *
	 * @return the startAsString
	 */
	public String getStartAsString() {
	    if (this.start == null) {
	        return Integer.MIN_VALUE + "";
	    }
		return Integer.toString(this.start.intValue());
	}

	/**
	 * Sätter startAsString.
	 *
	 * @param startAsString the startAsString to set
	 */
	public void setStartAsString(String startAsString) {
	    try {
	        this.start = Integer.valueOf(startAsString);
	    }
	    catch (Exception e) {
	        this.start = null;
	    }
	}

	/**
	 * Returnerar endAsString.
	 *
	 * @return the endAsString
	 */
	public String getEndAsString() {
	    if (end == null) {
	        return "" + Integer.MAX_VALUE;
	    }
		return Integer.toString(this.end.intValue());
	}

	/**
	 * Sätter endAsString.
	 *
	 * @param endAsString the endAsString to set
	 */
	public void setEndAsString(String endAsString) {
	    try {
	        this.end =  Integer.valueOf(endAsString);
	    }
	    catch (Exception e) {
	        this.end = null;
	    }
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.getStartAsString() + "--" + this.getEndAsString();
	}
	
	
	
}
