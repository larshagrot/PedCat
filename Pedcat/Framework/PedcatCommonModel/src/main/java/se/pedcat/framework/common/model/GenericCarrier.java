/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;


// TODO: Auto-generated Javadoc
/**
 * The Class GenericCarrier.
 */
public abstract class GenericCarrier extends Carrier {

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return  super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}

   
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see se.pedcat.framework.common.model.Carrier#getKey()
	 */
	@Override
    public Key getKey() {
        return new DefaultKey(getObjectIdAsInt());
    }

    /**
     * Calc fk.
     *
     * @param value the value
     * @param carrier the carrier
     * @return the int
     */
    protected static int calcFk(int value, Carrier carrier) {
        // TODO Auto-generated method stub
        if (value > 0) {
            return value;
        } else {
            if (carrier != null) {
                return carrier.getObjectIdAsInt();
            }
        }
        return 0;
    }
    
    /**
     * Calc fk.
     *
     * @param value the value
     * @param carrier the carrier
     * @return the int
     */
    protected static int calcFk(Integer value, Carrier carrier) {
        if (value!=null && value.intValue() > 0) {
            return value;
        } else {
            if (carrier != null) {
                return carrier.getObjectIdAsInt();
            }
            else
            {
            	return 0;
            }
        }
        
    }

	/**
	 * Calc fk.
	 *
	 * @param id the id
	 * @param carrier the carrier
	 * @return the string
	 */
	public static String calcFk(String id, Carrier carrier) {
		// TODO Auto-generated method stub
		if (id!=null && id.length()>0)
		{
			return id; 
		} else {
			   if (carrier != null) {
	                return carrier.getObjectId();
	            }
		}
		return null;
	}

	/**
	 * Evaluate fk null.
	 *
	 * @param parentOrganisationId the parent organisation id
	 * @return the integer
	 */
	public Integer evaluateFkNull(Integer parentOrganisationId) {
		// TODO Auto-generated method stub
		return parentOrganisationId==null?null:(parentOrganisationId.intValue()<1?null:parentOrganisationId.intValue());
	}
}
