/**
 * 
 */
package se.pedcat.forum.service;
import se.pedcat.forum.dao.ForumCarrierFactory;
import se.pedcat.forum.dao.ForumMessageDAO;
import se.pedcat.forum.model.ForumMessage;
import se.pedcat.framework.storage.dao.CarrierFactory;
import se.pedcat.framework.storage.dao.GenericCarrierFactory;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;
/**
 * @author laha
 *
 */
public class ForumServiceImpl extends GenericCarrierServiceImpl<ForumMessage.ForumMessageKey,ForumMessage,ForumMessage.ForumMessageQuery,ForumMessageDAO> implements ForumService
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
	public ForumServiceImpl() {
		super(ForumMessage.ForumMessageKey.class,ForumMessage.class,ForumMessageDAO.class,ForumMessage.ForumMessageQuery.class);
		this.getDAO().setCommitTransactions(true);
		
	}

	
}
