/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.util.Date;


// TODO: Auto-generated Javadoc
/**
 * The Class Carrier.
 */
public abstract class Carrier extends ValueObject implements Cloneable {


	
	/** The skapad av. */
	private String skapadAv;
    
    /** The skapad datum. */
    private Date skapadDatum;
    
    /** The andrad av. */
    private String andradAv;
    
    /** The andrad datum. */
    private Date andradDatum;
    
    /** TODO: Ska flyttas neråt i hierarkin ?. */ 
    private String  leveransobjektId;
    
    /** The leveransobjekt fk. */
    private Integer leveransobjektFk;

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((this.andradAv == null) ? 0 : this.andradAv.hashCode());
		result = prime
				* result
				+ ((this.andradDatum == null) ? 0 : this.andradDatum.hashCode());
		result = prime
				* result
				+ ((this.leveransobjektFk == null) ? 0 : this.leveransobjektFk
						.hashCode());
		result = prime
				* result
				+ ((this.leveransobjektId == null) ? 0 : this.leveransobjektId
						.hashCode());
		result = prime * result
				+ ((this.skapadAv == null) ? 0 : this.skapadAv.hashCode());
		result = prime
				* result
				+ ((this.skapadDatum == null) ? 0 : this.skapadDatum.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Carrier other = (Carrier) obj;
		if (this.andradAv == null) {
			if (other.andradAv != null) {
				return false;
			}
		} else if (!this.andradAv.equals(other.andradAv)) {
			return false;
		}
		if (this.andradDatum == null) {
			if (other.andradDatum != null) {
				return false;
			}
		} else if (!this.andradDatum.equals(other.andradDatum)) {
			return false;
		}
		if (this.leveransobjektFk == null) {
			if (other.leveransobjektFk != null) {
				return false;
			}
		} else if (!this.leveransobjektFk.equals(other.leveransobjektFk)) {
			return false;
		}
		if (this.leveransobjektId == null) {
			if (other.leveransobjektId != null) {
				return false;
			}
		} else if (!this.leveransobjektId.equals(other.leveransobjektId)) {
			return false;
		}
		if (this.skapadAv == null) {
			if (other.skapadAv != null) {
				return false;
			}
		} else if (!this.skapadAv.equals(other.skapadAv)) {
			return false;
		}
		if (this.skapadDatum == null) {
			if (other.skapadDatum != null) {
				return false;
			}
		} else if (!this.skapadDatum.equals(other.skapadDatum)) {
			return false;
		}
		return true;
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The counter. */
	private static int counter = 0;

    /**
     * Instantiates a new carrier.
     */
    public Carrier() {
        counter++;
    }

    /*
    protected void finalize() throws Throwable {
        super.finalize();
        counter--;
    }*/

    /**
     * Gets the count.
     *
     * @return the count
     */
    public static int getCount() {
        return counter;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
    
    /**
     * Returnerar skapadAv.
     * @return the skapadAv
     */
    public String getSkapadAv() {
        return skapadAv;
    }

    /**
     * Sätter skapadAv.
     * @param skapadAv the skapadAv to set
     */
    public void setSkapadAv(String skapadAv) {
        this.skapadAv = skapadAv;
    }

    /**
     * Returnerar skapadDatum.
     * @return the skapadDatum
     */
    public Date getSkapadDatum() {
        return skapadDatum;
    }

    /**
     * Sätter skapadDatum.
     * @param skapadDatum the skapadDatum to set
     */
    
    public void setSkapadDatum(Date skapadDatum) {
        this.skapadDatum = skapadDatum;
    }

    /**
     * Returnerar andradAv.
     * @return the andradAv
     */
    public String getAndradAv() {
        return andradAv;
    }

    /**
     * Sätter andradAv.
     * @param andradAv the andradAv to set
     */
    public void setAndradAv(String andradAv) {
        this.andradAv = andradAv;
    }

    /**
     * Returnerar andradDatum.
     * @return the andradDatum
     */
    public Date getAndradDatum() {
        return andradDatum;
    }

    /**
     * Sätter andradDatum.
     * @param andradDatum the andradDatum to set
     */
    public void setAndradDatum(Date andradDatum) {
        this.andradDatum = andradDatum;
    }
    
    /**
     * Gets the skapad datum as string.
     *
     * @return the skapad datum as string
     */
    public String getSkapadDatumAsString()
    {
    	return ModelUtility.fromDate2String(this.skapadDatum);
    }
    
    /**
     * Gets the skapad datum tid as string.
     *
     * @return the skapad datum tid as string
     */
    public String getSkapadDatumTidAsString()
    {
    	return ModelUtility.formateraDatumTid(this.skapadDatum);
    }
    
    /**
     * Gets the andrad datum as string.
     *
     * @return the andrad datum as string
     */
    public String getAndradDatumAsString()
    {
    	return ModelUtility.fromDate2String(this.andradDatum);
    }
    
    /**
     * Gets the andrad datum tid as string.
     *
     * @return the andrad datum tid as string
     */
    public String getAndradDatumTidAsString()
    {
    	return ModelUtility.formateraDatumTid(this.andradDatum);
    }

    /**
     * Abstract method which has to be implemented by concrete
     * sub classes
     * 
     * The implementation is supposed to instantiate the
     * primary key object for the carrier.
     *
     * @return the key
     */
    public abstract Key getKey();

    /**
     * Checks if is new.
     *
     * @return true, if is new
     */
    public boolean isNew() {
        return (this.getObjectId() == null || this.getObjectId().equals("-1") ||  this.getObjectId().equals("0")) && this.getObjectIdAsInt() < 1;
    }

    /**
     * Gets the formaterad skapad datum.
     *
     * @return the formaterad skapad datum
     */
    public String getFormateradSkapadDatum() {
        return ModelUtility.formateraDatumTid(this.getSkapadDatum());
    }

    /**
     * Gets the formaterad andrad datum.
     *
     * @return the formaterad andrad datum
     */
    public String getFormateradAndradDatum() {
        return ModelUtility.formateraDatumTid(this.getAndradDatum());
    }

    /**
     * Gets the leveransobjekt id.
     *
     * @return the leveransobjekt id
     */
    public String getLeveransobjektId() {
        return leveransobjektId;
    }

    /**
     * Sets the leveransobjekt id.
     *
     * @param leveransobjektId the new leveransobjekt id
     */
    public void setLeveransobjektId(String leveransobjektId) {
        this.leveransobjektId = leveransobjektId;
    }

	/**
	 * Returnerar leveransobjektFk.
	 * @return the leveransobjektFk
	 */
	public Integer getLeveransobjektFk() {
		return leveransobjektFk;
	}

	/**
	 * Sätter leveransobjektFk.
	 * @param leveransobjektFk the leveransobjektFk to set
	 */
	public void setLeveransobjektFk(Integer leveransobjektFk) {
		this.leveransobjektFk = leveransobjektFk;
	}
}
