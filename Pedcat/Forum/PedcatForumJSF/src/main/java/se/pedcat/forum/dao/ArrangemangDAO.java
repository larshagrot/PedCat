/**
 * 
 */
package se.pedcat.forum.dao;

import se.pedcat.forum.model.Arrangemang;
import se.pedcat.forum.model.ForumMessage;
import se.pedcat.framework.storage.dao.HibernateDAO;



/**
 * @author laha
 *
 */
public class ArrangemangDAO extends HibernateDAO<Arrangemang.ArrangemangKey,Arrangemang> {

	/**
	 * @param carrierClass
	 */
	public ArrangemangDAO() {
		super(Arrangemang.class);
		// TODO Auto-generated constructor stub
	}

}
