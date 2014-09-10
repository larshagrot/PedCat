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
 * General base class for all Key classes.
 */
public class KeyImpl implements Key,Serializable
{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Maybe to be removed. */
	private static int counter=0;
	
	/** Hold any kind of object id as string. */
    private String objectId;
    
    /** And what is it then. */
    private boolean isInteger ;
    
    /**
     * Instantiates a new key impl.
     */
    public KeyImpl()
    {
        counter++;
    }
    
    /**
     * Instantiates a new key impl.
     *
     * @param objectId the object id
     */
    public KeyImpl(int objectId)
    {
        counter++;
        this.objectId = ""+objectId;
        this.isInteger = true;
    }
    
    /**
     * Instantiates a new key impl.
     *
     * @param objectId the object id
     */
    public KeyImpl(String objectId)
    {
        counter++;
        this.objectId = objectId;
    }

    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.Key#getObjectIdAsString()
     */
    public String getObjectIdAsString()
    {
        return objectId;
    }

    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.Key#getObjectId()
     */
    public int getObjectId()
    {
    	return this.getObjectIdAsInt();
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.Key#getObjectIdAsInt()
     */
    public int getObjectIdAsInt()
    {
        try
        {
        	return objectId!=null?Integer.parseInt(objectId):-1;
        }
        catch (Exception e)
        {
        	return -1;
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (object !=null && object.getClass().equals(this.getClass()))
        {
        	String otherId = ((KeyImpl)object).objectId;
            return (objectId!=null && otherId!=null &&  objectId.equals(otherId));
        }

        return false;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return ModelUtility.dumpFields(this); 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return objectId!=null?objectId.hashCode():0;
    }
    
    /**
     * A key is null if it is null or an integer < 1.
     *
     * @return true, if is null
     */
    public boolean isNull()
    {
    	if (this.isInteger)
    	{
    		return this.getObjectIdAsInt()<1;
    	}
    	else
    	{
    		return this.objectId == null;
    	}
    }
    
    /**
     * There is a GC-problem with this one
     * protected void finalize() throws Throwable
     * {
     * super.finalize();
     * counter--;
     * }
     *
     * @return the count
     */
    
    /**
     * The number of created objects!
     * 
     * 
     */
    public static int getCount()
    {
        return counter;
    }

	/**
	 * Type.
	 *
	 * @return true, if is int
	 */
	public boolean isInt() {
		
		return this.isInteger;
	}

	/**
	 * Type.
	 *
	 * @return true, if is string
	 */
	public boolean isString() {
		
		return !this.isInteger;
	}

}
