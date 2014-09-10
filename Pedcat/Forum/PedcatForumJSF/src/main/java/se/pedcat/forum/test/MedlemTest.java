/**
 * 
 */
package se.pedcat.forum.test;

import java.util.Date;

import se.pedcat.forum.model.Arrangemang;
import se.pedcat.forum.model.Forening;
import se.pedcat.forum.model.Medlem;
import se.pedcat.forum.model.Medlem.MedlemKey;
import se.pedcat.forum.service.ArrangemangService;
import se.pedcat.forum.service.ArrangemangServiceImpl;
import se.pedcat.forum.service.ForeningService;
import se.pedcat.forum.service.ForeningServiceImpl;
import se.pedcat.forum.service.MedlemService;
import se.pedcat.forum.service.MedlemServiceImpl;

/**
 * @author laha
 *
 */
public class MedlemTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MedlemService m = new MedlemServiceImpl();
		ArrangemangService a = new ArrangemangServiceImpl();
		m.findAll();
		ForeningService f = new ForeningServiceImpl();
		
		Forening forening = new Forening(); 
		forening.setNamn("Test");
		
		forening = f.create(forening);
		Medlem[] medlemmar = new Medlem[10];
		for(int i=0;i<medlemmar.length;i++)
		{
			Medlem medlem = new Medlem();
			medlem.setFornamn(""+i);
			medlem.setEfternamn(""+i);
			medlem.setForeningId(forening.getObjectId());
			medlemmar[i] = m.create(medlem);
			medlemmar[i] = m.findByKey(new Medlem.MedlemKey(medlemmar[i].getObjectId()));
			if (medlemmar[i]!=null)
			{
				System.out.println("OK");
			}
			else
			{
				System.out.println("ERR");
			}
		}
		Arrangemang[] arrangemang = new Arrangemang[10];
		for(int i=0;i<arrangemang.length;i++)
		{
			Arrangemang aarrangemang = new Arrangemang();
			aarrangemang.setNamn(""+i);
			aarrangemang.setDatum(new Date());
			aarrangemang.setForeningId(forening.getObjectId());
			arrangemang[i] = a.create(aarrangemang);
		}
		
		
		
		System.out.println(f.findAll().length);
		System.out.println(m.findAll().length);
		System.out.println(a.findAll().length);
		for(int i=0;i<medlemmar.length;i++)
		{
			m.delete(medlemmar[i]);
		}
		for(int i=0;i<arrangemang.length;i++)
		{
			a.delete(arrangemang[i]);
		}
		f.delete(forening);
	}

}
