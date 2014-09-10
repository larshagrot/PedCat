/**
 * 
 */
package se.pedcat.forum.dao;

import se.pedcat.forum.model.Arrangemang;
import se.pedcat.forum.model.Forening;
import se.pedcat.forum.model.ForumMessage;
import se.pedcat.forum.model.Funktionar;
import se.pedcat.forum.model.Medlem;
import se.pedcat.framework.storage.dao.CarrierFactory;



/**
 * @author laha
 *
 */
public class ForumCarrierFactory extends CarrierFactory {

	static 
	{
		init();
	}
	private static boolean i = false;
	
	/**
	 * 
	 */
	public static void init() {
		System.out.println("Registering?" );
		
		if (!i)
		{	
			System.out.println("Registering " );
			try
			{
				registerDAO(ForumMessage.ForumMessageKey.class, ForumMessage.class,  ForumMessage.ForumMessageQuery.class,  se.pedcat.forum.dao.ForumMessageDAO.class);
				registerDAO(Medlem.MedlemKey.class, Medlem.class,  Medlem.MedlemQuery.class,  se.pedcat.forum.dao.MedlemDAO.class);
				registerDAO(Funktionar.FunktionarKey.class, Funktionar.class,  Funktionar.FunktionarQuery.class,  se.pedcat.forum.dao.FunktionarDAO.class);
				registerDAO(Arrangemang.ArrangemangKey.class, Arrangemang.class,  Arrangemang.ArrangemangQuery.class,  se.pedcat.forum.dao.ArrangemangDAO.class);
				registerDAO(Forening.ForeningKey.class, Forening.class,  Forening.ForeningQuery.class,  se.pedcat.forum.dao.ForeningDAO.class);
				System.out.println("Registered " );
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			CarrierFactory.getDAO(new Medlem.MedlemKey(""));
			i = true;
		}
		else
		{
			System.out.println("Not Registering " );
				
		}
	}
}
