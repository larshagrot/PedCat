/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import se.pedcat.framework.common.config.FrameworkConfiguration;


// TODO: Auto-generated Javadoc
/**
 * The Class CommonUtility.
 */
public abstract class CommonUtility {
	
	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(CommonUtility.class);
	
	/** The start. */
	private static long start = System.currentTimeMillis();
	
	/** The buildinformation. */
	private static String buildinformation = null;

	/** The property method map map. */
	private static Map<String, Map<String, PropertyDescriptor>> propertyMethodMapMap = new HashMap<String, Map<String, PropertyDescriptor>>();
	
	/** The BUFFERSIZE. */
	private static int BUFFERSIZE = FrameworkConfiguration.getInstance().getIntProperty("se.pedcat.framework.upload.buffer.size", 8196);


	/**
	 * Instantiates a new common utility.
	 */
	protected CommonUtility() {

	}

	/**
	 * Equals.
	 *
	 * @param value1 the value1
	 * @param value2 the value2
	 * @return true, if successful
	 */
	public static boolean equals(String value1, String value2) {
		// TODO Auto-generated method stub
		return value1 == null && value2 == null
				|| (value1 != null && value2 != null && value1.equals(value2));
	}

	/**
	 * Equals.
	 *
	 * @param value1 the value1
	 * @param value2 the value2
	 * @return true, if successful
	 */
	public static boolean equals(Integer value1, Integer value2) {
		// TODO Auto-generated method stub
		return value1 == null && value2 == null
				|| (value1 != null && value2 != null && value1.equals(value2));
	}

	/**
	 * Gets the value.
	 *
	 * @param property the property
	 * @param target the target
	 * @return the value
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws IntrospectionException the introspection exception
	 */
	public static Object getValue(String property, Object target)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, IntrospectionException {
		// TODO Auto-generated method stub
		try {
			Method method = getReadMethod(property, target);
			if (method != null) {
				return method.invoke(target, new Object[0]);
			} else {
				throw new FrameworkException(
						"Could not find read method for property " + property);
			}
		} catch (FrameworkException e) {
			throw e;
		} catch (Exception e) {
			throw new FrameworkException("Could read value for property "
					+ property, e);
		}
	}

	/**
	 * Gets the read method.
	 *
	 * @param property the property
	 * @param target the target
	 * @return the read method
	 * @throws IntrospectionException the introspection exception
	 */
	private static Method getReadMethod(String property, Object target)
			throws IntrospectionException {
		// TODO Auto-generated method stub
		Map<String, PropertyDescriptor> map = propertyMethodMapMap.get(target
				.getClass().getName());
		if (map == null) {
			BeanInfo bi = Introspector.getBeanInfo(target.getClass());
			PropertyDescriptor[] pds = bi.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				// logger.info("Adding " + pds[i].getName());
				CommonUtility.addToMapInMap(propertyMethodMapMap, target
						.getClass().getName(), pds[i].getName(), pds[i]);
			}
		}
		Map<String, PropertyDescriptor> targetMap = propertyMethodMapMap
				.get(target.getClass().getName());
		if (targetMap != null) {
			PropertyDescriptor pd = (PropertyDescriptor) targetMap
					.get(property);
			if (pd != null) {
				return pd.getReadMethod();
			}
		}
		return null;
	}

	/**
	 * Adds the to map in map.
	 *
	 * @param mapMap the map map
	 * @param name the name
	 * @param name2 the name2
	 * @param propertyDescriptor the property descriptor
	 */
	private static void addToMapInMap(
			Map<String, Map<String, PropertyDescriptor>> mapMap, String name,
			String name2, PropertyDescriptor propertyDescriptor) {
		Map<String, PropertyDescriptor> map = mapMap.get(name);
		if (map == null) {
			map = new HashMap<String, PropertyDescriptor>();
			mapMap.put(name, map);
		}
		map.put(name2, propertyDescriptor);

	}

	/**
	 * Adds the to list in map.
	 *
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param value the value
	 */
	public static void addToListInMap(Map theMap, String key1, Object value) {
		List list = (List) theMap.get(key1);
		if (list == null) {
			list = new ArrayList();
			theMap.put(key1, list);
		}
		list.add(value);
	}

	/**
	 * Adds the to list in map.
	 *
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param value the value
	 */
	public static void addToListInMap(Map theMap, Integer key1, Object value) {
		List list = (List) theMap.get(key1);
		if (list == null) {
			list = new ArrayList();
			theMap.put(key1, list);
		}
		list.add(value);
	}

	/**
	 * Adds the to map in map.
	 *
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param key2 the key2
	 * @param value the value
	 */
	public static void addToMapInMap(Map<String, Map<String, Object>> theMap,
			String key1, String key2, Object value) {
		Map<String, Object> map = theMap.get(key1);
		if (map == null) {
			map = new HashMap<String, Object>();
			theMap.put(key1, map);
		}
		map.put(key2, value);
	}

	
	
	/**
	 * Adds the to map in map generic.
	 *
	 * @param <Type> the generic type
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param key2 the key2
	 * @param value the value
	 */
	public static <Type> void  addToMapInMapGeneric(
			Map<Integer, Map<Integer, Type>> theMap,
			Integer key1, 
			Integer key2, 
			Type value) {
		Map<Integer, Type> map = theMap.get(key1);
		if (map == null) 
		{
			map = new HashMap<Integer, Type>();
			theMap.put(key1, map);
		}
		map.put(key2, value);
	}

	
	/**
	 * Adds the to map in map.
	 *
	 * @param theMap the the map
	 * @param key1 the key1
	 * @param key2 the key2
	 * @param value the value
	 */
	public static void addToMapInMap(Map<Integer, Map<Integer, Object>> theMap,
			Integer key1, Integer key2, Object value) {
		Map<Integer, Object> map = theMap.get(key1);
		if (map == null) {
			map = new HashMap<Integer, Object>();
			theMap.put(key1, map);
		}
		map.put(key2, value);
	}

	/**
	 * Gets the elapsed.
	 *
	 * @return the elapsed
	 */
	public static String getElapsed() {
		return "" + (System.currentTimeMillis() - start) / 1000.;
	}

	/**
	 * Hjälpmetod för att bygga strukturer med mappar i mappar i en map.
	 *
	 * @param configMap the config map
	 * @param key1 för second map
	 * @param key2 the key2
	 * @param name the name
	 * @param value the value
	 */

	public static void addToMapInMapInMap(
			Map<String, Map<String, Map<String, Object>>> configMap,
			String key1, String key2, String name, Object value) {
		Map<String, Map<String, Object>> map = configMap.get(key1);
		if (map == null) {
			map = new HashMap<String, Map<String, Object>>();
			configMap.put(key1, map);
		}
		addToMapInMap(map, key2, name, value);
	}

	/**
	 * formats a string with all public fields as a name-value pair table.
	 *
	 * @param from the from
	 * @return the string
	 */
	public static String dumpFields(Object from) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			if (from != null) {
				Class fromClassObject = from.getClass();
				stringBuffer.append("\n" + fromClassObject.getName() + "\n");
				Field[] fields = fromClassObject.getFields();
				for (int index = 0; index < fields.length; index++) {
					Object fieldObject = fields[index].get(from);
					stringBuffer.append(fields[index].getName()
							+ "\t=\t"
							+ (fieldObject != null ? fieldObject.toString()
									: "null") + "\n");
				}
			} else {
				stringBuffer.append("object == null in dumpFields");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	/**
	 * Dump properties.
	 *
	 * @param from the from
	 * @return the string
	 */
	public static String dumpProperties(Object[] from) {
		String rc = "";
		for (Object object : from) {
			rc += dumpProperties(object);
		}
		return rc;
	}

	/**
	 * Dump properties.
	 *
	 * @param from the from
	 * @return the string
	 */
	public static String dumpProperties(Object from) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			if (from != null) {
				Class fromClassObject = from.getClass();
				stringBuffer.append("\n" + fromClassObject.getName() + "\n");
				BeanInfo beanInfo = java.beans.Introspector
						.getBeanInfo(fromClassObject);
				PropertyDescriptor[] propertyDescriptors = beanInfo
						.getPropertyDescriptors();

				for (int index = 0; index < propertyDescriptors.length; index++) {
					Method method = propertyDescriptors[index].getReadMethod();
					if (method != null) {
						try {
							stringBuffer
									.append(method.getName()
											+ "\t=\t"
											+ method
													.invoke(from, new Object[0])
											+ "\n");
						} catch (Exception e) {
							stringBuffer.append(method.getName() + "\t=\t"
									+ e.getMessage() + "\n");
						}
					}
				}
			} else {
				stringBuffer.append("object == null in dumpProperties");
			}
		} catch (Exception e) {
			e.printStackTrace();
			stringBuffer.append("Exceptio when trying to dump "
					+ e.getMessage());
		}
		return stringBuffer.toString();
	}

	/**
	 * Dump fields.
	 *
	 * @param objects the objects
	 * @return the string
	 */
	public static String dumpFields(Object[] objects) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			if (objects != null && objects.length > 0 && objects[0] != null) {
				Class fromClassObject = objects[0].getClass();
				Field[] fields = fromClassObject.getFields();
				stringBuffer.append("\n" + fromClassObject.getName() + "\n");
				for (int findex = 0; findex < fields.length; findex++) {
					stringBuffer.append(fields[findex].getName() + "\t");
				}
				stringBuffer.append("\n");
				for (int oindex = 0; oindex < objects.length; oindex++) {
					Object object = objects[oindex];
					for (int findex = 0; findex < fields.length; findex++) {
						Object fieldObject = fields[findex].get(object);
						stringBuffer.append((fieldObject != null ? fieldObject
								.toString() : "null")
								+ "\t");
					}
					stringBuffer.append("\n");
				}
			} else {
				stringBuffer.append("object == null in dumpFields");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	/**
	 * Compares all public fields from on object to another Objects has to be of
	 * equal types otherwise they are unequal.
	 *
	 * @param object1 the object1
	 * @param object2 the object2
	 * @return true if equal false if unequal
	 */
	public static boolean equalFields(Object object1, Object object2) {
		boolean rc = true;
		try {
			if (object1 != object2) {
				if (object1 == null || object2 == null
						|| !object1.getClass().equals(object2.getClass())) {
					rc = false;
				} else {
					Class fromClassObject = object1.getClass();
					Field[] fields = fromClassObject.getFields();
					for (int index = 0; index < fields.length && rc; index++) {
						Object field1 = fields[index].get(object1);
						Object field2 = fields[index].get(object2);
						rc = (field1 == null && field2 == null)
								|| ((field1 != null && field2 != null) && field1
										.equals(field2));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}

	/**
	 * Copy all public fields from on object to another Objects has to be of
	 * equal types.
	 *
	 * @param from source
	 * @param to sink
	 */
	public static void copyFields(Object from, Object to) {
		try {
			if (from != null && to != null) {
				if (from != to) {
					if (from.getClass().equals(to.getClass())) {
						Class fromClassObject = from.getClass();
						Field[] fields = fromClassObject.getFields();
						for (int index = 0; index < fields.length; index++) {
							if (!Modifier.isFinal(fields[index].getModifiers())) {
								fields[index].set(to, fields[index].get(from));
							}
						}
					} else {
						// What?
					}
				}
			} else {
				// What?
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Compare fields.
	 *
	 * @param from the from
	 * @param to the to
	 * @param cmpEqualFields the cmp equal fields
	 * @return the field[]
	 */
	public static Field[] compareFields(Object from, Object to,
			boolean cmpEqualFields) {
		Collection differentFields = new ArrayList();
		Collection equalFields = new ArrayList();
		try {

			if (from != null && to != null) {
				if (from != to) {
					if (from.getClass().equals(to.getClass())) {
						Class fromClassObject = from.getClass();
						Field[] fields = fromClassObject.getFields();
						for (int index = 0; index < fields.length; index++) {
							Object fromField = fields[index].get(from);
							Object toField = fields[index].get(to);
							if ((fromField == null && toField == null)
									|| ((fromField != null && toField != null) && (fromField
											.equals(toField)))) {
								equalFields.add(fields[index]);
							} else {
								differentFields.add(fields[index]);
							}
						}
					} else {

					}
				}
			} else {
				// What?
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cmpEqualFields) {
			return (Field[]) equalFields.toArray(new Field[0]);
		} else {
			return (Field[]) differentFields.toArray(new Field[0]);
		}
	}

	/**
	 * Strip if to long.
	 *
	 * @param value ett värde som ska undersökas
	 * @param maxlength max antal tecken
	 * @param replaceChars tecken som ska in i stället om det var för långt.
	 * @return samma text men eventuellt strippad
	 */
	public static String stripIfToLong(final String value, final int maxlength,
			final String replaceChars) {
		final int extra = 3;
		String returnedValue = value;
		if (returnedValue != null
				&& (returnedValue.length() - extra) > maxlength) {
			returnedValue = value.substring(0, maxlength) + replaceChars;
		}
		return returnedValue;
	}

	/**
	 * Här är en mekanism som splittar upp en text så att den inte innehåller
	 * för långa ord eller sammanhängande text.
	 * 
	 * 
	 * @param text
	 *            en text som ska undersökas.
	 * @param maxLength
	 *            maximal ordlängd/sammanhängande text utan white space.
	 * @return samma text men eventuellt med lite blanktecken inspränga
	 */
	public static String ensureMaxWordLength(final String text,
			final int maxLength) {
		String strReturn = text;
		if (text != null && text.trim().length() > 0
				&& !text.trim().equals("N/A")) {
			strReturn = "";
			String[] words = text.split("\\s+"); // Separated by
			// "whitespace"

			for (int i = 0; i < words.length; i++) {
				String word = words[i];

				while (word.length() > maxLength) {
					String start = word.substring(0, maxLength);
					String rest = word.substring(maxLength, word.length());

					strReturn += " " + start;
					word = rest;

					if (word.length() <= maxLength) {
						strReturn += " " + rest;
						word = "";
					}
				}

				strReturn += " " + word;
			}
		} else {
			return "";
		}

		return strReturn;
	}

	/**
	 * Returnernar presentation av minnestillstånd.
	 * 
	 * 
	 * @return minnesstatus som en sträng.
	 */
	public static String getMemoryStatus() {
		return "Memory total\t"
				+ Runtime.getRuntime().totalMemory()
				+ "\nfree\t\t"
				+ Runtime.getRuntime().freeMemory()
				+ "\nUsed\t\t"
				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
						.freeMemory()) + "\n";
	}

	/**
	 * Hjälpmetod som administrerar hantering av en Map med sorterade
	 * collections.
	 * 
	 * @param theMap
	 *            en Map.
	 * @param theKey
	 *            en nyckel.
	 * @param theValue
	 *            ett värde som ska lagras.
	 * @param theComparator
	 *            sortering.
	 */
	public static void addToTreeInMap(final Map theMap, final String theKey,
			final Object theValue, final Comparator theComparator) {
		Collection theCollection = (Collection) theMap.get(theKey);
		if (theCollection == null) {
			theCollection = new java.util.TreeSet(theComparator);
			theMap.put(theKey, theCollection);
		}
		theCollection.add(theValue);
	}

	/**
	 * Liten hjälpmetod som plockar ut stacktrace och returnerar denna som
	 * sträng.
	 * 
	 * 
	 * @param e
	 *            ett exeception
	 * @return stacktrace
	 */
	public static String stacktrace2string(final Exception e) {

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		return sw.toString();
	}

	/**
	 * Parses the line.
	 *
	 * @param string the string
	 * @param separator the separator
	 * @return the string[]
	 */
	public static String[] parseLine(String string, char separator) {
		// TODO Auto-generated method stub
		return string != null ? string.split("" + separator) : new String[0];
	}

	/**
	 * Gets the buildproperties.
	 *
	 * @return the buildproperties
	 */
	public static Properties getBuildproperties() {
		InputStream is = CommonUtility.class
				.getResourceAsStream("/build.number");
		Properties properties = new Properties();
		if (is != null) {
			try {
				properties.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * Gets the buildinformation.
	 *
	 * @return the buildinformation
	 */
	public static String getBuildinformation() {
		if (buildinformation == null) {
			Properties properties = CommonUtility.getBuildproperties();
			StringBuffer sb = new StringBuffer();
			sb.append("build " + properties.getProperty("build.number"));
			sb.append(" " + properties.getProperty("build.date"));
			buildinformation = sb.toString();
		}
		return buildinformation;

	}

	/**
	 * Slår ihop heltal i en array.
	 *
	 * @param prefix prefix för resultatet
	 * @param integers the integers
	 * @param separator avskiljare
	 * @param suffix suffix för resultatet
	 * @return the string
	 */

	public static String concatenate(String prefix, Integer[] integers,
			String separator, String suffix) {
		StringBuffer sb = new StringBuffer();
		if (prefix != null) {
			sb.append(prefix);
		}
		if (integers != null) {
			for (int i = 0; i < integers.length; i++) {
				sb.append(integers[i]);
				if (i < (integers.length - 1)) {
					sb.append(separator);
				}
			}
		}
		if (suffix != null) {
			sb.append(suffix);
		}

		return sb.toString();
	}

	/**
	 * Slår ihop strängar i en array.
	 *
	 * @param prefix prefix för resultatet
	 * @param values värden som ska slås ihop
	 * @param separator avskiljare
	 * @param suffix suffix för resultatet
	 * @return the string
	 */
	public static String concatenate(Object prefix, String[] values,
			String separator, Object suffix) {
		StringBuffer sb = new StringBuffer();
		if (prefix != null) {
			sb.append(prefix);
		}
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				sb.append(values[i]);
				if (i < (values.length - 1)) {
					sb.append(separator);
				}
			}
		}
		if (suffix != null) {
			sb.append(suffix);
		}

		return sb.toString();
	}

	/**
	 * Gets the nested exception.
	 *
	 * @param exception the exception
	 * @return the nested exception
	 */
	public static Exception getNestedException(Throwable exception) {
		logger.error("Real exception--->",exception);
		Throwable e = exception;
		try {
			while (e != null && e.getCause() != null) {
				exception = e;
				e = e.getCause();
			}
			if (e == null) {
				e = exception;
			}
		} catch (Exception e2) {
			return (Exception) exception;
		}
		if (e instanceof  Exception)
			return (Exception) e;
		else
		{
			e.printStackTrace();
			logger.error("Nested",e);
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Gets the nested exception.
	 *
	 * @param exception the exception
	 * @return the nested exception
	 */
	public static Exception getNestedException(Exception exception) {
		logger.error("Real exception--->",exception);
		Exception e = exception;
		try {
			while (e != null && e.getCause() != null) {
				exception = e;
				if (e.getCause() instanceof Exception)
				{
					e = (Exception) e.getCause();
				}
				else
				if (e.getCause() instanceof Throwable)	
				{
					return getNestedException(e.getCause());
				}
					
			}
			if (e == null) {
				e = exception;
			}
		} catch (Exception e2) {
			return (Exception) exception;
		}
		if (e instanceof  Exception)
			return (Exception) e;
		else
		{
			e.printStackTrace();
			logger.error("Nested",e);
			throw new RuntimeException(e);
		}
		
	}
	
	
	

	/**
	 * Gets the nested exception.
	 *
	 * @param exception the exception
	 * @return the nested exception
	 */
	public static Throwable getNestedException(Error exception) {
		Throwable t = exception;
		Throwable e = exception;
		while (e != null && e.getCause() != null) {
			t = e;
			e = e.getCause();
		}
		if (e == null) {
			e = t;
		}
		return e;
	}

	/**
	 * Ensure framework exception.
	 *
	 * @param t the t
	 */
	public static void ensureFrameworkException(Throwable t) {
		ensureFrameworkException(null, t, null);
	}

	/**
	 * Ensure framework exception.
	 *
	 * @param message the message
	 * @param t the t
	 * @param messageKey the message key
	 */
	public static void ensureFrameworkException(String message, Throwable t,
			String messageKey) {
		t = getNestedException(t);
		if (t instanceof FrameworkException) {
			throw (FrameworkException) t;
		} else {
			throw new FrameworkException(message, t, messageKey);
		}
	}

	/**
	 * Test encoding.
	 *
	 * @param b the b
	 * @param csnam the csnam
	 * @return true, if successful
	 */
	public static boolean testEncoding(byte[] b, String csnam) {
		CharsetDecoder cd = Charset.availableCharsets().get(csnam).newDecoder();
		try {
			System.out.println(b.length);
			cd.decode(ByteBuffer.wrap(b));

		} catch (CharacterCodingException e) {
			return false;
		}
		return true;
	}

	/**
	 * File as bytes.
	 *
	 * @param path the path
	 * @return the byte[]
	 */
	public static byte[] fileAsBytes(String path) {
		ByteArrayOutputStream baoss = new ByteArrayOutputStream();
		InputStream iss = null;
		byte[] buffer = new byte[BUFFERSIZE];
		try {
			// iss = new FileInputStream("WebContent/WEB-INF/test/3.xsl");
			iss = new FileInputStream(path);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		try {

			int n = iss.read(buffer);
			while (n > 0) {
				baoss.write(buffer, 0, n);
				n = iss.read(buffer);
			}

		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		return baoss.toByteArray();
	}

	/**
	 * Ensure encoding.
	 *
	 * @param bytes the bytes
	 * @return the byte[]
	 */
	public static byte[] ensureEncoding(byte[] bytes) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(bytes)));

			String line = r.readLine();

			if (line.indexOf("encoding=\"UTF-8\"") != -1
					&& !testEncoding(bytes, "UTF-8")) {

				String text = new String(bytes, "ISO-8859-1");
				text = text.replaceFirst("encoding=\"UTF-8\"",
						"encoding=\"ISO-8859-1\"");
				return text.getBytes("ISO-8859-1");
			} else {

				return bytes;
			}
		} catch (Exception e) {
			return bytes;
		}

	}
	
	/**
	 * Move.
	 *
	 * @param filePath the file path
	 * @param newFile the new file
	 * @param deleteSource the delete source
	 * @throws Exception the exception
	 */
	public static void move(String filePath, File newFile,boolean deleteSource) throws Exception 
	{
		try
		{
			try
			{
				if (newFile.exists())
				{
					logger.info("Deleting target because it exists!");
					newFile.delete();
					logger.info("Deleted? " + !newFile.exists());
				}
				
			}
			catch(Exception e)
			{
				logger.error("",e);
			}
			logger.info("Renaming " + filePath);
			new File(filePath).renameTo(newFile);
			if (newFile.exists())
			{
				logger.info("New name " + newFile.getAbsolutePath());
			}
		}
		catch (Exception e)
		{
			logger.info("Error occured doing ordinary copying...");
			OutputStream out = new FileOutputStream(newFile);
			InputStream is = new FileInputStream(filePath);
			byte[] buffer = new byte[BUFFERSIZE];
			int nBytes = is.read(buffer);
			while (nBytes > 0) {
				out.write(buffer, 0, nBytes);
				nBytes = is.read(buffer);
			}
			out.flush();
			out.close();
			is.close();
			
			if (deleteSource)
			{
				new File(filePath).delete();
			}
		}
	}

	/**
	 * Gets the from map in map generic.
	 *
	 * @param <Type> the generic type
	 * @param mainMap the main map
	 * @param firstKey the first key
	 * @param secondKey the second key
	 * @param remove the remove
	 * @return the from map in map generic
	 */
	public static  <Type> Type getFromMapInMapGeneric (
			Map<Integer, Map<Integer, Type>> mainMap,
			Integer firstKey, 
			Integer secondKey,
			boolean remove){
		Map<Integer, Type> subMap = mainMap.get(firstKey);
		if (subMap!=null)
		{
			if (remove)
			{
				return subMap.remove(secondKey);
			}
			else
			{
				return subMap.get(secondKey);
			}
		}
		else
		{
			return null;
		}
	}


}
