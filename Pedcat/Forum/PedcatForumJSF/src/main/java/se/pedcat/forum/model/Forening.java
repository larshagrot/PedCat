/**
 * 
 */
package se.pedcat.forum.model;

import se.pedcat.forum.model.ForumMessage.ForumMessageKey;
import se.pedcat.framework.common.model.GenericCarrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;



/**
 * @author laha
 *
 */
public class Forening extends GenericCarrier {

	public static class ForeningKey extends KeyImpl{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ForeningKey(String objectId) {
			super(objectId);
			// TODO Auto-generated constructor stub
		}
		
	}
	public static class ForeningQuery extends ModelQuery
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	public Key getKey()
	{
		return new ForeningKey(this.getObjectId());
	}

	
	private String namn;

	/**
	 *  Returnerar namn
	 *
	 * @return the namn
	 */
	public String getNamn() {
		return this.namn;
	}

	/**
	 * Sätter namn
	 * @param namn the namn to set
	 */
	public void setNamn(String namn) {
		this.namn = namn;
	}
	
	
}
