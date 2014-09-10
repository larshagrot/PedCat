/**
 * 
 */
package se.pedcat.forum.service;

import se.pedcat.forum.dao.ForeningDAO;
import se.pedcat.forum.model.Forening;
import se.pedcat.framework.storage.service.GenericCarrierService;

/**
 * @author laha
 *
 */
public interface ForeningService extends
		GenericCarrierService<Forening.ForeningKey,Forening,Forening.ForeningQuery>
{

}
