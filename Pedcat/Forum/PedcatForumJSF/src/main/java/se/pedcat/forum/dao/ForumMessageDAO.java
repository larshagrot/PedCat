/**
 * 
 */
package se.pedcat.forum.dao;

import se.pedcat.forum.model.ForumMessage;
import se.pedcat.framework.storage.dao.HibernateDAO;



/**
 * @author laha
 *
 */
public class ForumMessageDAO extends HibernateDAO<ForumMessage.ForumMessageKey,ForumMessage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param carrierClass
	 */
	public ForumMessageDAO() {
		super(ForumMessage.class);
		// TODO Auto-generated constructor stub
	}

}
