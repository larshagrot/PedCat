/**
 * 
 */
package se.pedcat.forum.service;

import se.pedcat.forum.dao.ForumMessageDAO;
import se.pedcat.forum.model.ForumMessage;
import se.pedcat.framework.storage.service.GenericCarrierService;

/**
 * @author laha
 *
 */
public interface ForumService extends
		GenericCarrierService<ForumMessage.ForumMessageKey,ForumMessage,ForumMessage.ForumMessageQuery>
{

}
