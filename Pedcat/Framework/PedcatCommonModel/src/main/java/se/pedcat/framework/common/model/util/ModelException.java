/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model.util;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.FrameworkMessageFactory;



// TODO: Auto-generated Javadoc
/**
 * The Class ModelException.
 */
public class ModelException extends FrameworkException {
	
	  /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Holds value of property key. */
    private Key key;
    
    /** Holds value of property carrier. */
    private Carrier carrier;
    
    /** Holds value of property carrier. */
    private Carrier[] carriers;
    
    
    /** Holds value of property thrownBy. */
    private String thrownBy;

    
    /** Holds value of property query. */
    private ModelQuery query;    

	
	/**
	 * Instantiates a new model exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public ModelException(String message, Throwable throwable)
	{
		super(message,throwable);
	}
	
	/**
	 * Instantiates a new model exception.
	 *
	 * @param message the message
	 */
	public ModelException(String message)
	{
		super(message);
	}
	
    /**
     * Instantiates a new model exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param thrownBy the thrown by
     */
    public ModelException(String msg,String messageKey,String thrownBy) {
        this(msg,messageKey,thrownBy,null);
    }
    
    /**
     * Instantiates a new model exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param thrownBy the thrown by
     * @param nestedException the nested exception
     */
    public ModelException(String msg,String messageKey,String thrownBy,java.lang.Throwable nestedException)
    {
        this(msg,messageKey,thrownBy,nestedException,null,null);
        
    }
    
     /**
      * Instantiates a new model exception.
      *
      * @param msg the msg
      * @param messageKey the message key
      * @param thrownBy the thrown by
      * @param nestedException the nested exception
      * @param key the key
      */
     public ModelException(String msg,String messageKey,String thrownBy,java.lang.Throwable nestedException,Key key)
    {
    	 this(msg,messageKey,thrownBy,nestedException,null,key);
    }
    
    /**
     * Instantiates a new model exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param thrownBy the thrown by
     * @param nestedException the nested exception
     * @param carrier the carrier
     */
    public ModelException(String msg,String messageKey,String thrownBy,java.lang.Throwable nestedException,Carrier carrier)
    {
        this(msg,messageKey,thrownBy,nestedException,carrier,null);
    }
    
    /**
     * Instantiates a new model exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param thrownBy the thrown by
     * @param nestedException the nested exception
     * @param carrier the carrier
     * @param key the key
     */
    public ModelException(String msg,String messageKey,String thrownBy,java.lang.Throwable nestedException,Carrier carrier,Key key)
    {
        this(msg,messageKey,thrownBy,nestedException,carrier,null,key,null);
        
    }
    
    /**
     * Instantiates a new model exception.
     *
     * @param msg the msg
     * @param messageKey the message key
     * @param thrownBy the thrown by
     * @param nestedException the nested exception
     * @param carrier the carrier
     * @param carriers the carriers
     * @param key the key
     * @param query the query
     */
    public ModelException(String msg,String messageKey,String thrownBy,java.lang.Throwable nestedException,Carrier carrier,Carrier[] carriers,Key key,ModelQuery query)
    {
        super(msg,nestedException);
        this.thrownBy = thrownBy;
        this.carrier = carrier;

        this.key = key;
        this.carriers = carriers;
        setMessageKey(messageKey);
        this.query = query;
    }
	/**
	 * Returnerar key.
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	/**
	 * Sätter key.
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	/**
	 * Returnerar carrier.
	 * @return the carrier
	 */
	public Carrier getCarrier() {
		return carrier;
	}
	/**
	 * Sätter carrier.
	 * @param carrier the carrier to set
	 */
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	/**
	 * Returnerar carriers.
	 * @return the carriers
	 */
	public Carrier[] getCarriers() {
		return carriers;
	}
	/**
	 * Sätter carriers.
	 * @param carriers the carriers to set
	 */
	public void setCarriers(Carrier[] carriers) {
		this.carriers = carriers;
	}
	/**
	 * Returnerar thrownBy.
	 * @return the thrownBy
	 */
	public String getThrownBy() {
		return thrownBy;
	}
	/**
	 * Sätter thrownBy.
	 * @param thrownBy the thrownBy to set
	 */
	public void setThrownBy(String thrownBy) {
		this.thrownBy = thrownBy;
	}
	/**
	 * Returnerar query.
	 * @return the query
	 */
	public ModelQuery getQuery() {
		return query;
	}
	/**
	 * Sätter query.
	 * @param query the query to set
	 */
	public void setQuery(ModelQuery query) {
		this.query = query;
	}

	/** Getter for property prodexMessage.
     * @return Value of property prodexMessage.
     */
    public String getR7EArkivMessage()
    {
        return FrameworkMessageFactory.getInstance().getMessage(super.getMessageKey());
    }
	
}
