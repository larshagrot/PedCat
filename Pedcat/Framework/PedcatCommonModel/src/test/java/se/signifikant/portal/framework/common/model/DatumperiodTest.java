package se.pedcat.framework.common.model;
import org.junit.Test;

import se.pedcat.framework.common.model.Datumperiod;

import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author laha
 *
 */
public class DatumperiodTest {

	@Test
	public void testDatumperiod()
	{
		String testperiod1 =  "2010-01-01--2010-02-01";
		String testperiod2 =  "--2010-02-01";
		String testperiod3 =  "2010-01-01--";
		String testperiod4 =  "--";
		
		Datumperiod period1 = new Datumperiod(testperiod1);
		Datumperiod period2 = new Datumperiod(testperiod2);
		Datumperiod period3 = new Datumperiod(testperiod3);
		Datumperiod period4 = new Datumperiod(testperiod4);
		
		System.out.println(period1);
		System.out.println(period2);
		System.out.println(period3);
		System.out.println(period4);
		
	}
}
