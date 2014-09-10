/**
 * 
 */
package se.pedcat.forum.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import se.pedcat.forum.model.Funktionar;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.storage.dao.HibernateDAO;



/**
 * @author laha
 *
 */
public class FunktionarDAO extends HibernateDAO<Funktionar.FunktionarKey,Funktionar> {

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.HibernateDAO#findByQuery(se.pedcat.framework.common.model.ModelQuery)
	 */
	@Override
	public Funktionar[] findByQuery(ModelQuery aQuery) {
		
		
		Funktionar.FunktionarQuery query = (Funktionar.FunktionarQuery) aQuery;
		
		Session session = this.getSession();
		Transaction tx  = this.beginTransaction(session);
		try
		{
			String queryString = "from Funktionar t where t.medlemId = :medlemId" ;
			
			  
			Funktionar[] k = (Funktionar[]) session.createQuery(queryString).setString("medlemId",query.getMedlemId()).list().toArray(new Funktionar[0]);
			
			commitTransaction(tx);
			return k;
		}
		catch (Exception e)
		{
			rollbackTransaction(tx);
			throw new RuntimeException(e);
		}

	}


	/**
	 * @param carrierClass
	 */
	public FunktionarDAO() {
		super(Funktionar.class);
		// TODO Auto-generated constructor stub
	}

}
