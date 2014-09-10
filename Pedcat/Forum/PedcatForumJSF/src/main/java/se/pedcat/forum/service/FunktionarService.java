/**
 * 
 */
package se.pedcat.forum.service;

import se.pedcat.forum.dao.FunktionarDAO;
import se.pedcat.forum.model.Funktionar;
import se.pedcat.framework.storage.service.GenericCarrierService;

/**
 * @author laha
 *
 */
public interface FunktionarService extends
		GenericCarrierService<Funktionar.FunktionarKey,Funktionar,Funktionar.FunktionarQuery>
{

}
