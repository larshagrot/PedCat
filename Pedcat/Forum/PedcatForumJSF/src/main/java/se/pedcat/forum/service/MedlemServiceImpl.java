/**
 * 
 */
package se.pedcat.forum.service;
import se.pedcat.forum.dao.ForumCarrierFactory;
import se.pedcat.forum.dao.MedlemDAO;
import se.pedcat.forum.model.Medlem;
import se.pedcat.forum.model.Medlem.MedlemKey;
import se.pedcat.framework.storage.dao.CarrierFactory;
import se.pedcat.framework.storage.dao.GenericCarrierFactory;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;
/**
 * @author laha
 *
 */
public class MedlemServiceImpl extends GenericCarrierServiceImpl<Medlem.MedlemKey,Medlem,Medlem.MedlemQuery,MedlemDAO> implements MedlemService
{
	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.service.GenericCarrierServiceImpl#ensure(se.pedcat.framework.common.model.Carrier)
	 */
	@Override
	public Medlem ensure(Medlem type) 
	{
	
		if (type.isNew() && type.getMedlemsnummer()!=null && type.getMedlemsnummer().trim().length()>0)
		{
			Medlem.MedlemQuery q = new Medlem.MedlemQuery();
			q.setMedlemsnummer(type.getMedlemsnummer());
			Medlem[] found = this.findByQuery(q);
			if (found==null || found.length==0)
			{
				return this.create(type);
			}
			else
			{
				if (found.length>0)
				{
					return found[0];
				}
			}
		}
			if (!type.isNew() && type.getMedlemsnummer()!=null && type.getMedlemsnummer().trim().length()>0)
			{
				return create(type);
			}
			else
			{
				return null;
			}
		
	
	}
	/* (non-Javadoc)
	 * @see se.pedcat.framework.storage.service.GenericCarrierServiceImpl#findByKey(se.pedcat.framework.common.model.Key)
	 */
	@Override
	public Medlem findByKey(MedlemKey key) {
		// TODO Auto-generated method stub
		return  (Medlem) ForumCarrierFactory.findByPrimaryKey(key, null);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public MedlemServiceImpl() {
		super(Medlem.MedlemKey.class,Medlem.class,MedlemDAO.class,Medlem.MedlemQuery.class);
		this.getDAO().setCommitTransactions(true);
		
	}
	
	

	
}
