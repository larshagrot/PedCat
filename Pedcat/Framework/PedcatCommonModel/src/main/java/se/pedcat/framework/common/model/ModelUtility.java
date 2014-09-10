/**
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.pedcat.framework.common.model.util.ModelException;
import se.pedcat.framework.common.util.CommonUtility;





// TODO: Auto-generated Javadoc
/**
 * The Class ModelUtility.
 */
public class ModelUtility extends CommonUtility{

	/** The Constant BUFFERT_SIZE. */
	public static final int BUFFERT_SIZE = 2048; 
	
	/** Ett nulldatum. */
	public static final String NULLDATE = "nulldate";
	/** Ett nulldatum. */
	public static final String NULLDATE_2 = "Saknas";
	/** Ett nulldatum. */
	public static final String NULLDATE_3 = "null";
	/** Ett nulldate 1753-02-01. */
	public static final String NULLDATE_4 = "1753-02-01";

    /** The date time format. */
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /** The date time format2. */
    private static SimpleDateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    /** The date format. */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /** The date format2. */
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
    
    /** The date time minute format. */
    private static SimpleDateFormat dateTimeMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    /** The time format. */
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
	

	
	
	/**
	 * Creates the map.
	 *
	 * @param carriers the carriers
	 * @return the map
	 */
	public static Map<Key,Carrier> createMap(Carrier[] carriers)
    {
		Map<Key,Carrier> map = new HashMap<Key,Carrier>();
		for(Carrier carrier:carriers)
		{
			map.put(carrier.getKey(), carrier);
		}
    	return map;
    }

	/**
	 * Adds the to map.
	 *
	 * @param map the map
	 * @param carriers the carriers
	 */
	public static void addToMap(Map<Key,Carrier> map, Carrier[] carriers) {
		// TODO Auto-generated method stub
		for(Carrier carrier:carriers)
		{
			map.put(carrier.getKey(), carrier);
		}
	}

     /**
      * Formatera datum tid.
      *
      * @param datum the datum
      * @return the string
      */
     public static String formateraDatumTid(Date datum) {
        if (datum!=null)
        {
            return dateTimeFormat.format(datum);
        }
        else
        {
            return "";
        }
    }

	

	/**
	 * From string2 date.
	 *
	 * @param date the date
	 * @return the date
	 */
	public static Date fromString2Date(String date) {

		if (date==null)
		{
			return null;
		}
		else
		try
		{
			return dateTimeFormat.parse(date);
		}
		catch (Exception e)
		{
			try
			{
				return dateFormat.parse(date);
			}
			catch (Exception e2)
			{
				try
				{
					return dateFormat2.parse(date);
				}
				catch (Exception e3)
				{
					try
					{
						return dateTimeMinuteFormat.parse(date);
					}
					catch (Exception e4) {}
				}	
			}
		}
		
		
		return null;
	}
	
	/**
	 * Tar ett datum med klockslag och returnerar datumdelen.
	 * 
	 * 
	 * @param dateTime
	 *            ett datum med klockslag.
	 * @return datumdelen.
	 */
	public static String stripKlockslag(final String dateTime)
	{
		String datum = "";
		if (isValidDate(dateTime))
		{
			try
			{
				Date date = null;
				try
				{
					date = ModelUtility.dateTimeFormat.parse(dateTime);
				}
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					// logger.warn(e.getMessage());
					try
					{
						date = ModelUtility.dateTimeFormat.parse(dateTime);
					}
					catch (ParseException e2)
					{
						date = ModelUtility.dateFormat.parse(dateTime);
					}
				}
				catch (Exception e)
				{
					if (1==0)
					{
						System.err.println("Got exception " + e.getMessage());
					}
				}
				
				if (date != null)
				{
					datum = ModelUtility.dateFormat.format(date);
				}
			}
			catch (ParseException e)
			{
				datum = "";
			}
		}
		return datum;
	}

	/**
	 * Tar ett datum med klockslag och returnerar klockslagsdelen.
	 * 
	 * 
	 * @param dateTime
	 *            ett datum+klockslag
	 * @return klockslag
	 */
	
	public static String stripDatum(final String dateTime)
	{
		String klockslag = "";
		if (isValidDate(dateTime))
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try
			{
				Date date = format.parse(dateTime);
				format = new SimpleDateFormat("HH:mm");
				klockslag = format.format(date);
			}
			catch (ParseException e)
			{
				klockslag = "";
			}
		}
		return klockslag;
	}

	/**
	 * Simpel liten hjälpmetod som testar en sträng.
	 * 
	 * @param string
	 *            en text
	 * @return true om den är tom.
	 */
	public static boolean isEmpty(final String string)
	{
		return string == null || string.trim().length() == 0 || "NULL".equalsIgnoreCase(string);
	}
	
	/**
	 * Simpel liten hjälpmetod som testar ett object.
	 *
	 * @param object the object
	 * @return true om den är null
	 */
	public static boolean isEmpty(final Object object)
	{
		return object == null;
	}
	
	/**
	 * Checks if is not empty.
	 *
	 * @param object the object
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(final Object object)
	{
		return !isEmpty(object);
	}

	/**
	 * Simpel liten hjälpmetod som testar sträng.
	 * 
	 * @param string
	 *            en sträng som ska undersökas
	 * @return true om den inte är tom
	 */
	public static boolean isNotEmpty(final String string)
	{
		return !isEmpty(string);
	}

	/**
	 * Simpel hjälpmetod som testar en array.
	 * 
	 * @param array
	 *            en array
	 * @return true om den är tom.
	 */
	public static boolean isEmpty(final Object[] array)
	{
	
		return !isNotEmpty(array);
	}

	/**
	 * Simpel hjälpmetod som testar en array.
	 * 
	 * @param array
	 *            en array
	 * @return true om den inte är tom
	 */
	public static boolean isNotEmpty(final Object[] array)
	{
	
		return array != null && array.length > 0;
	}
	
	/**
	 * Checks if is valid date.
	 *
	 * @param dateTime ett datum i strängform
	 * @return true om det inte är tomt eller ser ut som ett NULL-värde
	 */
	public static boolean isValidDate(final String dateTime)
	{
		return !isNullDate(dateTime);
	}


	/**
	 * Checks if is not empty.
	 *
	 * @param tidpunkt the tidpunkt
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(Date tidpunkt) {
		// TODO Auto-generated method stub
		return tidpunkt!=null;
	}

	/**
	 * Strip klockslag.
	 *
	 * @param tidpunkt the tidpunkt
	 * @return the string
	 */
	public static String stripKlockslag(Date tidpunkt) {
		// TODO Auto-generated method stub
		return dateFormat.format(tidpunkt);
	}

	/**
	 * From date2 string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String fromDate2String(Date date) {
		// TODO Auto-generated method stub
		if (date!=null)
		{
			return dateFormat.format(date);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * yyyy-MM-dd HH:mm.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String fromDate2StringDateTimeMinuteFormat(Date date) {
		if (date == null) return null;
		return dateTimeMinuteFormat.format(date);	
	}
		
	
	/**
	 * From date2 string2.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String fromDate2String2(Date date) {
		// TODO Auto-generated method stub
		if (date!=null)
		{
			return dateTimeFormat2.format(date);
		}
		else
		{
			return null;
		}
	}
		
	/**
	 * Strip sekunder.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String stripSekunder(Date date)
	{
		return dateTimeMinuteFormat.format(date);
	}

	
	/**
	 * Checks if is null fk.
	 *
	 * @param value the value
	 * @return true, if is null fk
	 */
	public static boolean isNullFk(int value)
	{
		return value <1;
	}
	
	/**
	 * Checks if is null fk.
	 *
	 * @param value the value
	 * @return true, if is null fk
	 */
	public static boolean isNullFk(Integer value)
	{
		return value==null ||  isNullFk(value.intValue());
	}
	
	/**
	 * Checks if is not empty and has wild card.
	 *
	 * @param value the value
	 * @return true, if is not empty and has wild card
	 */
	public static boolean isNotEmptyAndHasWildCard(final String value)
	{
		return isNotEmpty(value) && value.indexOf('*')!=-1;
	}
	
	/**
	 * Checks if is not empty and has not wild card.
	 *
	 * @param value the value
	 * @return true, if is not empty and has not wild card
	 */
	public static boolean isNotEmptyAndHasNotWildCard(final String value)
	{
		return isNotEmpty(value) && value.indexOf('*')==-1;
	}
	

	/**
	 * Convert2 double.
	 *
	 * @param value the value
	 * @return the double
	 */
	public static Double convert2Double(String value)
	{
		Double returned = null;
		if (value!=null)
		{
			try
			{
				returned = Double.valueOf(value.replace(',','.'));
			}
			catch (Exception e)
			{
			
			}
		}
		return returned;
	}
	
	
	/**
	 * Checks if is true.
	 *
	 * @param value the value
	 * @return true, if is true
	 */
	public static boolean isTrue(Boolean value) {
		// TODO Auto-generated method stub
		return value !=null && value.booleanValue();
	}

	/**
	 * Adds the to list in map.
	 *
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param value the value
	 */
	public static void addToListInMap(Map theMap,Integer key1,Object value )
	{
		List list = (List)  theMap.get(key1);
		if (list==null)
		{
			list = new ArrayList();
			theMap.put(key1, list);
		}
		list.add(value);
	}

	/**
	 * Concatenate.
	 *
	 * @param prefix the prefix
	 * @param integers the integers
	 * @param separator the separator
	 * @param suffix the suffix
	 * @return the string
	 */
	public static String concatenate(String prefix, Integer[] integers,
			String separator, String suffix) {
		StringBuffer sb = new StringBuffer();
		if (prefix!=null)
		{
			sb.append(prefix);
		}
		if (integers!=null)
		{
			for(int i=0;i<integers.length;i++)
			{
				sb.append(integers[i]);
				if (i<(integers.length-1))
				{
					sb.append(separator);
				}
			}
		}
		if (suffix!=null)
		{
			sb.append(suffix);
		}
		
		return sb.toString();
	}

	/**
	 * Concatenate.
	 *
	 * @param prefix the prefix
	 * @param values the values
	 * @param separator the separator
	 * @param suffix the suffix
	 * @return the string
	 */
	public static String concatenate(Object prefix, String[] values,
			String separator, Object suffix) {
		
		return ModelUtility.concatenate(prefix, values, separator, suffix,false);
	}
	
	
	/**
	 * Concatenate.
	 *
	 * @param prefix the prefix
	 * @param values the values
	 * @param separator the separator
	 * @param suffix the suffix
	 * @param replaceWildcards the replace wildcards
	 * @return the string
	 */
	public static String concatenate(Object prefix, String[] values,
			String separator, Object suffix,boolean replaceWildcards) {
		StringBuffer sb = new StringBuffer();
		if (prefix!=null)
		{
			sb.append(prefix);
		}
		if (values!=null)
		{
			for(int i=0;i<values.length;i++)
			{
				if (replaceWildcards && ModelUtility.isNotEmptyAndHasWildCard(values[i]))
				{
					values[i] = values[i].replace('*', '%').replaceAll("_", "*_").replace('?','_'); 
				}
				sb.append(values[i]);
				if (i<(values.length-1))
				{
					sb.append(separator);
				}
			}
		}
		if (suffix!=null)
		{
			sb.append(suffix);
		}
		
		return sb.toString();
	}
	

    /**
     * Parses the exception.
     *
     * @param exception the exception
     * @param classAndMethod the class and method
     * @return the string
     */
    public static String parseException(Throwable exception, String classAndMethod) {
        StringBuffer stringBuffer = new StringBuffer();
        if (exception != null) {
            Exception ne = null;
            ModelException pe = ModelUtility.getModelException(exception);
            if (pe != null) {
                stringBuffer.append("\n*****************  PORTAL FRAMEWORK EXCEPTION PARSED ***********\n");
                stringBuffer.append("\n PARSED IN " + classAndMethod + "\n");
                stringBuffer.append("\nMessage:" + pe.getMessage());
                Carrier carrier = pe.getCarrier();
                if (carrier != null) {
                    stringBuffer.append("\nCarrier fields:\n" + CommonUtility.dumpFields(carrier));
                    stringBuffer.append("\nCarrier props :\n" + CommonUtility.dumpProperties(carrier));
                }
                Carrier[] carriers = pe.getCarriers();
                if (carriers != null) {
                    stringBuffer.append("\nCarrier fields:\n" + CommonUtility.dumpFields(carriers));
                    stringBuffer.append("\nCarrier props :\n" + CommonUtility.dumpProperties(carriers));
                }
                Key key = pe.getKey();
                if (key != null) {
                    stringBuffer.append("\nKey props :" + key.toString());
                }
                ModelQuery query = pe.getQuery();
                if (query != null) {
                    stringBuffer.append("\nQuery props :\n" + CommonUtility.dumpProperties(query));
                }
                stringBuffer.append("\nThrownby      :" + pe.getThrownBy());
                stringBuffer.append("\nCode      :" + pe.getMessageKey());
                stringBuffer.append("\nProdexMessage      :" + pe.getR7EArkivMessage());
                StringWriter sw = new StringWriter();
                PrintWriter printWriter = new PrintWriter(sw);
                pe.printStackTrace(printWriter);
                stringBuffer.append("\nSTACKTRACE\n");
                stringBuffer.append(sw.toString());
                ne = (Exception) pe.getCause();
            }

            if (ne == null) {
                ne = getNestedException(exception);
            }
            if (ne != null) {
                stringBuffer.append("\nNested Message:" + ne.getMessage());
                StringWriter sw = new StringWriter();
                PrintWriter printWriter = new PrintWriter(sw);
                ne.printStackTrace(printWriter);
                stringBuffer.append("\nSTACKTRACE\n");
                stringBuffer.append(sw.toString());
                if (ne instanceof java.sql.SQLException) {
                    stringBuffer.append(formatSQLException((java.sql.SQLException) ne));
                }
            }
            stringBuffer.append("\nThrown Message:" + exception.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            exception.printStackTrace(printWriter);
            stringBuffer.append("\nSTACKTRACE\n");
            stringBuffer.append(sw.toString());
            stringBuffer.append("\n******************************* END *************************\n");

        }
        return stringBuffer.toString();

    }

    /**
     * This method formats an SQLException to a more readible message.
     *
     * @param e the SQLException to format
     * @return String
     */
    public static String formatSQLException(SQLException e) {
        StringBuffer message = new java.lang.StringBuffer(400);
        message.append("SQL Exception:");
        if (e != null) {

            message.append("Message:" + e.getMessage());
            message.append("LocalizedMessage:" + e.getLocalizedMessage());
            message.append("Type:" + e.getClass().getName());
            message.append("ErrorCode:" + e.getErrorCode());
            message.append("SQLState:" + e.getSQLState());
            message.append("toString:" + e.toString());
        }
        return message.toString();
    }

    
	/**
	 * Gets the model exception.
	 *
	 * @param exception the exception
	 * @return the model exception
	 */
	private static ModelException getModelException(
			Throwable exception) {

		Throwable t = exception;
		
		while (t!=null && ! (t instanceof ModelException ) && t.getCause()!=null)
		{
			exception = t;
			t = t.getCause();
		}
		if (t == null)
		{
			t = exception;
		}
		return t instanceof ModelException?(ModelException)t:null;
	}

	
	
	/**
	 * Checks if is null date.
	 *
	 * @param datum ett datum som ska testas
	 * @return true om nulldate
	 */
	public static boolean isNullDate(final String datum)
	{
		return datum == null || datum.trim().length() == 0 || datum.indexOf(NULLDATE) != -1 || datum.indexOf(NULLDATE_2) != -1
				|| datum.indexOf(NULLDATE_3) != -1 || datum.indexOf(NULLDATE_4) != -1;
	
	}

	/**
	 * Adds the to set in map.
	 *
	 * @param setMap the set map
	 * @param key the key
	 * @param value the value
	 */
	public static void addToSetInMap(
			Map<String, Set<String>> setMap,
			String key, String value) {
		
		Set set = (Set)  setMap.get(key);
		if (set==null)
		{
			set = new HashSet();
			setMap.put(key, set);
		}
		set.add(value);
		
	}

}
