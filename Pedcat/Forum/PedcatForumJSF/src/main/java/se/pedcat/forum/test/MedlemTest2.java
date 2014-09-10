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
public class MedlemTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		MedlemService m = new MedlemServiceImpl();
		System.out.println(m.findAll().length);
	}

}
