/**
 * 
 */
package se.pedcat.forum.service;
import se.pedcat.forum.dao.ForumCarrierFactory;
import se.pedcat.forum.dao.ArrangemangDAO;
import se.pedcat.forum.model.Arrangemang;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;
/**
 * @author laha
 *
 */
public class ArrangemangServiceImpl extends GenericCarrierServiceImpl<Arrangemang.ArrangemangKey,Arrangemang,Arrangemang.ArrangemangQuery,ArrangemangDAO> implements ArrangemangService
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
	public ArrangemangServiceImpl() {
		super(Arrangemang.ArrangemangKey.class,Arrangemang.class,ArrangemangDAO.class,Arrangemang.ArrangemangQuery.class);
		this.getDAO().setCommitTransactions(true);
		
	}

	
}
