/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache.model;


/**
 * The Class PageCacheTicket.
 *
 * @author laha
 */
public class PageCacheTicket implements java.io.Serializable{
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.sortorder;
		result = prime * result
				+ ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
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
		PageCacheTicket other = (PageCacheTicket) obj;
		if (this.sortorder != other.sortorder) {
			return false;
		}
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new page cache ticket.
	 *
	 * @param value the value
	 */
	public PageCacheTicket(Long value) {
		super();
		this.value = value;
	}

	/**
	 * Instantiates a new page cache ticket.
	 */
	public PageCacheTicket() {
		this.value = System.currentTimeMillis();
		
	}

	/** The value. */
	private Long value;
	
	/** The sortorder. */
	private int sortorder;

	/**
	 * Returnerar value.
	 *
	 * @return the value
	 */
	public Long getValue() {
		return this.value;
	}

	/**
	 * Sätter value.
	 *
	 * @param value the value to set
	 */
	public void setValue(Long value) {
		this.value = value;
	}

	/**
	 * Sets the sortorder.
	 *
	 * @param sortorder the new sortorder
	 */
	public void setSortorder(int sortorder) {
		this.sortorder = sortorder;
		
	}

	/**
	 * Returnerar sortorder.
	 *
	 * @return the sortorder
	 */
	public int getSortorder() {
		return this.sortorder;
	}
}
