/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package  se.pedcat.framework.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


// TODO: Auto-generated Javadoc
/**
 * Bekvämlighetsmetoder för datumhantering.
 */
public final class DateUtil {
  
	/** The Constant df1. */
	private static final DateFormat df1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	/** The Constant df2. */
	private static final DateFormat df2 = new SimpleDateFormat("yyyyMMdd HH:mm");
	
	/** The Constant df3. */
	private static final DateFormat df3 = new SimpleDateFormat("yy-MM-dd HH:mm");
	
	/** The Constant df4. */
	private static final DateFormat df4 = new SimpleDateFormat("yyMMdd HH:mm");
	
	/** The Constant df5. */
	private static final DateFormat df5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/** The Constant df6. */
	private static final DateFormat df6 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	/** The Constant df7. */
	private static final DateFormat df7 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	/** The Constant df8. */
	private static final DateFormat df8 = new SimpleDateFormat("yyMMdd HH:mm:ss");
	
	/** The Constant df9. */
	private static final DateFormat df9 = new SimpleDateFormat("yyyy-MM-dd HH");
	
	/** The Constant df10. */
	private static final DateFormat df10 = new SimpleDateFormat("yyyyMMdd HH");
	
	/** The Constant df11. */
	private static final DateFormat df11 = new SimpleDateFormat("yy-MM-dd HH");
	
	/** The Constant df12. */
	private static final DateFormat df12 = new SimpleDateFormat("yyMMdd HH");
	
	/** The Constant df13. */
	private static final DateFormat df13 = new SimpleDateFormat("yyyy-MM-dd");
	
	/** The Constant df14. */
	private static final DateFormat df14 = new SimpleDateFormat("yyyyMMdd");
	
	/** The Constant df15. */
	private static final DateFormat df15 = new SimpleDateFormat("yy-MM-dd");
	
	/** The Constant df16. */
	private static final DateFormat df16 = new SimpleDateFormat("yyMMdd");
	
	/** The Constant dateTimeFormats. */
	private static final DateFormat[] dateTimeFormats = {df1, df2, df3, df4, df5, df6, df7, df8, df9, df10, df11, df12};
	
	/** The Constant dateFormats. */
	private static final DateFormat[] dateFormats = {df13, df14, df15, df16};

	
	static {
		for (DateFormat df : dateTimeFormats) {
			df.setLenient(false);
		}
		for (DateFormat df : dateFormats) {
			df.setLenient(false);
		}		
	}
	
 /**
  * Returnerar samma datum som argumentet fast med klockslag 00:00:00.
  *
  * @param date the date
  * @return the date after midnight
  */
  public static Date getDateAfterMidnight(final Date date) {
  	Calendar cal = GregorianCalendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);

    return cal.getTime();
  }
  
  /**
   * Returnerar samma datum som argumentet fast med klockslag 23:59:59.
   *
   * @param date the date
   * @return the date before midnight
   */
   public static Date getDateBeforeMidnight(final Date date) {
   	Calendar cal = GregorianCalendar.getInstance();
     cal.setTime(date);
     cal.set(Calendar.HOUR_OF_DAY, 23);
     cal.set(Calendar.MINUTE, 59);
     cal.set(Calendar.SECOND, 59);

     return cal.getTime();
   }

  /**
   * Returnerar den första dagen i den månad som argumentet representerar (klockan 00:00:00).
   *
   * @param date the date
   * @return the first day of month
   */
  public static Date getFirstDayOfMonth(Date date) {
	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(getDateAfterMidnight(date));
	cal.set(Calendar.DAY_OF_MONTH, 1);
	return cal.getTime();
  }
  
  /**
   * Returnerar den sista dagen i den månad som argumentet representerar (klockan 00:00:00).
   *
   * @param date the date
   * @return the last day of month
   */
  public static Date getLastDayOfMonth(Date date) {
	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(getDateAfterMidnight(date));
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	return cal.getTime();
  }
  
	/**
	 * String to date.
	 *
	 * @param value the value
	 * @return the date
	 */
	public static Date stringToDate(String value) {
		
		boolean dateOk = false;
		Date date = null;
		for (DateFormat df : dateTimeFormats) {
			date = toDate(value.toString(), df);
			if (date != null) return date;
		} 
		for (DateFormat sdf : dateFormats) {
			date = toDate(value.toString(), sdf);
			if (date != null) return date;
		} 
		
		throw new IllegalArgumentException("Ogiltig datumsträng " + value);
	}
	
	/**
	 * To date.
	 *
	 * @param dateString the date string
	 * @param sdf the sdf
	 * @return the date
	 */
	private static Date toDate(String dateString, DateFormat sdf) {
		try {
			Date aDate = sdf.parse(dateString); 
			return aDate;
		}
		catch (ParseException e) {
			return null;
		}
	}

	  /** The format. */
  	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	  
  	/**
  	 * Gets the date time as string.
  	 *
  	 * @param aDate the a date
  	 * @return the date time as string
  	 */
  	public static  String getDateTimeAsString(Date aDate)
	  {
		  try
		  {
			  return format.format(aDate);
		  }
		  catch (Exception e)
		  {
			  return "";
		  }
	  }

	
}
