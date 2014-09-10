/**
 * 
 */
package se.pedcat.forum.service;
import se.pedcat.forum.dao.ForumCarrierFactory;
import se.pedcat.forum.dao.FunktionarDAO;
import se.pedcat.forum.model.Funktionar;
import se.pedcat.framework.storage.dao.CarrierFactory;
import se.pedcat.framework.storage.dao.GenericCarrierFactory;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;
/**
 * @author laha
 *
 */
public class FunktionarServiceImpl extends GenericCarrierServiceImpl<Funktionar.FunktionarKey,Funktionar,Funktionar.FunktionarQuery,FunktionarDAO> implements FunktionarService
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
	public FunktionarServiceImpl() {
		super(Funktionar.FunktionarKey.class,Funktionar.class,FunktionarDAO.class,Funktionar.FunktionarQuery.class);
		this.getDAO().setCommitTransactions(true);
		
	}

	
}
