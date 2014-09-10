/**
 * 
 */
package se.pedcat.forum.service;

import se.pedcat.forum.dao.MedlemDAO;
import se.pedcat.forum.model.Medlem;
import se.pedcat.framework.storage.service.GenericCarrierService;

/**
 * @author laha
 *
 */
public interface MedlemService extends
		GenericCarrierService<Medlem.MedlemKey,Medlem,Medlem.MedlemQuery>
{

}
