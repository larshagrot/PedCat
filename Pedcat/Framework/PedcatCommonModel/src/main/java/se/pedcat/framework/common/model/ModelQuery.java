/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.pedcat.framework.common.config.FrameworkConfiguration;


// TODO: Auto-generated Javadoc
/**
 * The Class ModelQuery.
 *
 * @author laha
 */
public abstract class ModelQuery extends ValueObject {
	
	/**
	 * Means not to use caches in this call.
	 * 
	 */
	public static final int ROUNDTRIP_LEVEL = 888;
	
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.ValueObject#clear()
     */
    @Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
		this.result = null;
    }

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The counter. */
	private static int counter = 0;
    
    /** The Constant INT_NULL. */
    public static final int INT_NULL = -1;

	/** The Constant QUERY_CACHE_TIMEOUT_KEY. */
	private static final String QUERY_CACHE_TIMEOUT_KEY  = "se.pedcat.framework.common.model.query.cache.timeout";

	/** The Constant QUERY_CACHE_TIMEOUT_DEFAULT. */
	private static final int QUERY_CACHE_TIMEOUT_DEFAULT = 60000;
    
    /** Default value for cache. */
    private final static int QUERY_CACHE_TIMEOUT = FrameworkConfiguration.getInstance().getIntProperty(QUERY_CACHE_TIMEOUT_KEY,QUERY_CACHE_TIMEOUT_DEFAULT);
    
    /** The hash code. */
    protected int hashCode = 0;
    /** Holds value of property timestamp. */
    private long timestamp;
    /** Holds value of property result. */
    private Carrier[] result;
    /** Holds value of property findAll. */
    private boolean findAll;
    /** Holds value of property countResult. */
    private int countResult = ModelQuery.INT_NULL;
    
    /** Roundtrip means a deep call witch not using caches. */
    private boolean roundtrip = false;
    
    /** When cached. */
    private long storedTime;
    
    /** Found in cache. */
    private int hits;
    
    
    /** The page query. */
    private boolean pageQuery;
	
	/** The keys query. */
	private boolean keysQuery;
    
    
    /**
     * Checks if is roundtrip.
     *
     * @return true, if is roundtrip
     */
    public boolean isRoundtrip() {
		return this.roundtrip;
	}

	/**
	 * Sets the roundtrip.
	 *
	 * @param roundtrip the new roundtrip
	 */
	public void setRoundtrip(boolean roundtrip) {
		this.roundtrip = roundtrip;
	}

    /** The rowcount. */
    private int rowcount = FrameworkConfiguration.getInstance().getIntProperty("se.pedcat.framework.rowcount", 200);

	/** The page list. */
	private List<?> pageList = null;
	
	
    
    /**
     * Returnerar rowcount.
     *
     * @return the rowcount
     */
	public int getRowcount() {
		return this.rowcount;
	}

	/**
	 * Sätter rowcount.
	 *
	 * @param rowcount the rowcount to set
	 */
	public void setRowcount(int rowcount) {
		this.rowcount = rowcount;
	}

	/**
	 * Instantiates a new model query.
	 */
	public ModelQuery() {
        counter++;
    }

    /* GC-problem ?
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
		ModelQuery other = (ModelQuery) obj;
		if (this.findAll != other.findAll) {
			return false;
		}
		if (this.hashCode != other.hashCode) {
			return false;
		}
		if (this.keysQuery != other.keysQuery) {
			return false;
		}
		if (this.pageList == null) {
			if (other.pageList != null) {
				return false;
			}
		} else if (!this.pageList.equals(other.pageList)) {
			return false;
		}
		if (this.pageQuery != other.pageQuery) {
			return false;
		}
		if (this.roundtrip != other.roundtrip) {
			return false;
		}
		return true;
	}

    /**
     * Equals.
     *
     * @param s1 the s1
     * @param s2 the s2
     * @return true, if successful
     */
    protected static boolean equals(String s1, String s2) {
        return (s1 == null && s2 == null) ||
                (s1 != null && s2 != null && s1.equals(s2));
    }
    
    /**
     * Equals.
     *
     * @param i1 the i1
     * @param i2 the i2
     * @return true, if successful
     */
    protected static boolean equals(Integer i1, Integer i2) {
        return (i1 == null && i2 == null) ||
                (i1 != null && i2 != null && i1.intValue() == i2.intValue());
    }
    
    /**
     * Equals.
     *
     * @param i1 the i1
     * @param i2 the i2
     * @return true, if successful
     */
    protected static boolean equals(Double i1, Double i2) {
        return (i1 == null && i2 == null) ||
                (i1 != null && i2 != null && i1.doubleValue() == i2.doubleValue());
    }

    /**
     * Equals.
     *
     * @param date1 the date1
     * @param date2 the date2
     * @return true, if successful
     */
    protected static boolean equals(java.util.Date date1, java.util.Date date2) {
        return (date1 == null && date2 == null) ||
                (date1 != null && date2 != null && date1.equals(date2));
    }

    /**
     * Equals.
     *
     * @param key1 the key1
     * @param key2 the key2
     * @return true, if successful
     */
    protected static boolean equals(Key key1, Key key2) {
        return (key1 == null && key2 == null) ||
                (key1 != null && key2 != null && key1.equals(key2));
    }

    /**
     * Equals.
     *
     * @param b1 the b1
     * @param b2 the b2
     * @return true, if successful
     */
    protected static boolean equals(boolean b1, boolean b2) {
        return b1 == b2;
    }

    /**
     * Equals.
     *
     * @param i1 the i1
     * @param i2 the i2
     * @return true, if successful
     */
    protected static boolean equals(int i1, int i2) {
        return i1 == i2;
    }

    /**
     * Equals.
     *
     * @param values1 the values1
     * @param values2 the values2
     * @return true, if successful
     */
    protected boolean equals(String[] values1, String[] values2) {

        if (values1 != null && values2 != null && values1.length == values2.length) {
            Set aSet = new HashSet(Arrays.asList(values1));
            for (String values : values2) {
                if (!aSet.contains(values)) {
                    return false;
                }
            }
            return true;
        } else {
            return values1 == null && values2 == null;
        }

    }

    /** Getter for property timestamp.
     * @return Value of property timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /** Setter for property timestamp.
     * @param timestamp New value of property timestamp.
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /** Getter for property expired.
     * @return Value of property expired.
     */
    public boolean isExpired() {
        return (System.currentTimeMillis() - this.getTimestamp()) > getCachingTime();
    }

    /** Getter for property cachingTime.
     * @return Value of property cachingTime.
     */
    public long getCachingTime() {
        return QUERY_CACHE_TIMEOUT;
    }

    /** Getter for property result.
     * @return Value of property result.
     */
    public Carrier[] getResult() {
        return result;
    }

    /** Setter for property result.
     * @param result New value of property result.
     */
    public void setResult(Carrier[] result) {
        this.result = result;
    }

    /** Getter for property findAll.
     * @return Value of property findAll.
     */
    public boolean isFindAll() {
        return findAll;
    }

    /** Setter for property findAll.
     * @param findAll New value of property findAll.
     */
    public void setFindAll(boolean findAll) {
        this.findAll = findAll;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (this.findAll ? 1231 : 1237);
		result = prime * result + this.hashCode;
		result = prime * result + (this.keysQuery ? 1231 : 1237);
		result = prime * result
				+ ((this.pageList == null) ? 0 : this.pageList.hashCode());
		result = prime * result + (this.pageQuery ? 1231 : 1237);
		result = prime * result + (this.roundtrip ? 1231 : 1237);
		return result;
	}

    /**
     * Check.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean check(Key key) {
        boolean rc = false;
        if (key != null) {
            hashCode = key.getObjectId();
            rc = true;
        }
        return rc;
    }

    /**
     * Check.
     *
     * @param code the code
     * @return true, if successful
     */
    public boolean check(String code) {
        boolean rc = false;
        if (code != null) {
            hashCode = code.hashCode();
            rc = true;
        }
        return rc;
    }

    /** Getter for property countResult.
     * @return Value of property countResult.
     */
    public int getCountResult() {
        return countResult;
    }

    /** Setter for property countResult.
     * @param countResult New value of property countResult.
     */
    public void setCountResult(int countResult) {
        this.countResult = countResult;
    }



    /**
     * Checks if is all.
     *
     * @param valda the valda
     * @return true, if is all
     */
    protected boolean isAll(String[] valda) {
        if (valda != null && valda.length > 0) {
            Set set = new HashSet(Arrays.asList(valda));
            return set.contains("-1");
        } else {
            return true;
        }

    }


	/**
	 * Returnerar hits.
	 * @return the hits
	 */
	public int getHits() {
		return hits;
	}
	
	/**
	 * Increment hits.
	 */
	public void incrementHits()
	{
		this.hits++;
	}

	/**
	 * Returnerar storedTime.
	 * @return the storedTime
	 */
	public long getStoredTime() {
		return storedTime;
	}

	/**
	 * Sätter storedTime.
	 * @param storedTime the storedTime to set
	 */
	public void setStoredTime(long storedTime) {
		this.storedTime = storedTime;
	}

	/**
	 * Sets the page list.
	 *
	 * @param <KeyType> the generic type
	 * @param list the new page list
	 */
	public <KeyType> void setPageList(List<KeyType> list )
	{
		this.pageList = list;
	}

	/**
	 * Returnerar pageList.
	 *
	 * @return the pageList
	 */
	public  List<?> getPageList() {
		return this.pageList;
	}

	/**
	 * Returnerar pageQuery.
	 *
	 * @return the pageQuery
	 */
	public boolean isPageQuery() {
		return this.pageQuery;
	}

	/**
	 * Sätter pageQuery.
	 *
	 * @param pageQuery the pageQuery to set
	 */
	public void setPageQuery(boolean pageQuery) {
		this.pageQuery = pageQuery;
	}

	/**
	 * Returnerar keysQuery.
	 *
	 * @return the keysQuery
	 */
	public boolean isKeysQuery() {
		return this.keysQuery;
	}

	/**
	 * Sätter keysQuery.
	 *
	 * @param keysQuery the keysQuery to set
	 */
	public void setKeysQuery(boolean keysQuery) {
		this.keysQuery = keysQuery;
	}


	
}
