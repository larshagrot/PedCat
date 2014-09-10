/**
 * 
 */
package se.pedcat.forum.dao;

import se.pedcat.forum.model.Forening;
import se.pedcat.framework.storage.dao.HibernateDAO;



/**
 * @author laha
 *
 */
public class ForeningDAO extends HibernateDAO<Forening.ForeningKey,Forening> {

	/**
	 * @param carrierClass
	 */
	public ForeningDAO() {
		super(Forening.class);
		// TODO Auto-generated constructor stub
	}

}
