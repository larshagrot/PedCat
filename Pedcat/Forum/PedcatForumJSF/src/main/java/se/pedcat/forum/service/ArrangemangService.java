/**
 * 
 */
package se.pedcat.forum.service;

import se.pedcat.forum.dao.ArrangemangDAO;
import se.pedcat.forum.model.Arrangemang;
import se.pedcat.framework.storage.service.GenericCarrierService;

/**
 * @author laha
 *
 */
public interface ArrangemangService extends
		GenericCarrierService<Arrangemang.ArrangemangKey,Arrangemang,Arrangemang.ArrangemangQuery>
{

}
