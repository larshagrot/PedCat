/**
 * 
 */
package se.pedcat.forum.dao;

import se.pedcat.forum.model.Medlem;
import se.pedcat.forum.model.Medlem.MedlemQuery;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.storage.dao.HibernateDAO;



/**
 * @author laha
 *
 */
public class MedlemDAO extends HibernateDAO<Medlem.MedlemKey,Medlem> {

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.HibernateDAO#findByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	@Override
	public Medlem[] findByQuery(ModelQuery query) {
		
		Medlem.MedlemQuery q = (MedlemQuery) query;
		if (q.getMedlemsnummer()!=null)
		{
			return super.findByHqlQuery("from " + Medlem.class.getName() + " where medlemsnummer = '" + q.getMedlemsnummer() + "'");
		}
		else
		if (q.isFindAll())	
		{
			return super.findAll();
		}
		else
		{
			return new Medlem[0];
		}
	}

	/**
	 * @param carrierClass
	 */
	public MedlemDAO() {
		super(Medlem.class);
		
	}

	
}
