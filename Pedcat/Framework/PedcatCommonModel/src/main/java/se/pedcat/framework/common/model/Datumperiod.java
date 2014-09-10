/**
 * 
 */
package se.pedcat.framework.common.model;

import java.io.Serializable;
import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class Datumperiod.
 *
 * @author laha
 */
public class Datumperiod implements Serializable {

	
	
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
		Datumperiod other = (Datumperiod) obj;
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
	private Date start;
	
	/** The end. */
	private Date end;	


	
	/**
	 * Instantiates a new datumperiod.
	 */
	public Datumperiod()
	{
		
	}
	
	/**
	 * Instantiates a new datumperiod.
	 *
	 * @param dates the dates
	 */
	public Datumperiod(String dates)
	{
		if (ModelUtility.isNotEmpty(dates) && dates.length()>2)
		{
			 
			if (dates.indexOf("--")!=-1)
			{
				String[] datum = dates.split("--");
				if (dates.startsWith("--") && datum.length==1)
				{
					this.setEndAsString(datum[0]);
				}
				else
					if (dates.endsWith("--") && datum.length==1)
					{
						this.setStartAsString(datum[0]);
					}	
					
					else
					if (datum.length==2)
					{
							this.setStartAsString(datum[0]);
							this.setEndAsString(datum[1]);
					}
			}
			else
			{
				this.setStartAsString(dates);
			}
		}
	}
	
	/**
	 * Instantiates a new datumperiod.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public Datumperiod(String start,String end)
	{
		this.setStartAsString(start);
		this.setEndAsString(end);
	}

	/**
	 * Instantiates a new datumperiod.
	 *
	 * @param start the start
	 * @param end the end
	 */
	public Datumperiod(Date start, Date end) {
		super();
		this.start = start;
		this.end = end;
	}

	
	/**
	 * Returnerar start.
	 *
	 * @return the start
	 */
	public Date getStart() {
		return this.start;
	}

	/**
	 * Sätter start.
	 *
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Returnerar end.
	 *
	 * @return the end
	 */
	public Date getEnd() {
		return this.end;
	}

	/**
	 * Sätter end.
	 *
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * Returnerar startAsString.
	 *
	 * @return the startAsString
	 */
	public String getStartAsString() {
		return ModelUtility.fromDate2String(this.start);
	}

	/**
	 * Sätter startAsString.
	 *
	 * @param startAsString the startAsString to set
	 */
	public void setStartAsString(String startAsString) {
		this.start = ModelUtility.fromString2Date(startAsString);
	}

	/**
	 * Returnerar endAsString.
	 *
	 * @return the endAsString
	 */
	public String getEndAsString() {
		return ModelUtility.fromDate2String(this.end);
	}

	/**
	 * Sätter endAsString.
	 *
	 * @param endAsString the endAsString to set
	 */
	public void setEndAsString(String endAsString) {
		this.end =  ModelUtility.fromString2Date(endAsString);
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.getStartAsString() + "--" + this.getEndAsString();
	}
	
	
	
}
