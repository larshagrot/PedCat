/**
 * 
 */
package se.pedcat.forum.service;
import se.pedcat.forum.dao.ForumCarrierFactory;
import se.pedcat.forum.dao.ForeningDAO;
import se.pedcat.forum.model.Forening;
import se.pedcat.framework.storage.dao.CarrierFactory;
import se.pedcat.framework.storage.dao.GenericCarrierFactory;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;
/**
 * @author laha
 *
 */
public class ForeningServiceImpl extends GenericCarrierServiceImpl<Forening.ForeningKey,Forening,Forening.ForeningQuery,ForeningDAO> implements ForeningService
{
	static
	{
		ForumCarrierFactory.init();
		
		
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ForeningServiceImpl() {
		super(Forening.ForeningKey.class,Forening.class,ForeningDAO.class,Forening.ForeningQuery.class);
		this.getDAO().setCommitTransactions(true);
		
	}

	
}
