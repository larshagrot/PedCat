/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.pedcat.framework.storage.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;


/**
 * The Class DefaultQueryCommandAdapter.
 *
 * @param <KeyType> the generic type
 * @param <Type> the generic type
 * @author laha
 */
public abstract class DefaultQueryCommandAdapter<KeyType extends Key,Type extends Carrier> implements QueryCommand<KeyType,Type> 
{
   
   /* (non-Javadoc)
    * @see se.pedcat.framework.storage.dao.QueryCommand#isCarrierbuilder()
    */
   @Override 
   public boolean isCarrierbuilder()
   {
       return false;
   }
   
   /* (non-Javadoc)
    * @see se.pedcat.framework.storage.dao.QueryCommand#buildCarriers(java.sql.ResultSet)
    */
   @Override
   public Collection<Type> buildCarriers(ResultSet rs)
   {
       return new ArrayList<Type>();
   }

	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.dao.QueryCommand#isCallable()
	 */
	@Override
	public boolean isCallable() {
		// TODO Auto-generated method stub
		return true;
	}

}
