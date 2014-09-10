/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.config;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.FrameworkResourceBundle;




// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkConfiguration.
 */
public class FrameworkConfiguration implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(FrameworkConfiguration.class);
	
	/** The Constant FILE_1. */
	private static final String FILE_1 = "/config/portal_framework_config.xml";
	
	/** The Constant FILE_2. */
	private static final String FILE_2 = "config/portal_framework_config.xml";
	
	/** The Constant FILE_3. */
	private static final String FILE_3 = "portal_framework_config.xml";
	
	/** The Constant ENVIRONMENTS. */
	private static final String ENVIRONMENTS = "environments";
	
	/** The Constant ENVIRONMENT. */
	private static final String ENVIRONMENT = "environment";
	
	/** The Constant PROPERTIES. */
	private static final String PROPERTIES = "properties";
	
	/** The Constant PROPERTY. */
	private static final String PROPERTY = "property";

	/** The Constant FRAMEWORK_PROPERTY_FILE_PATH. */
	private static final String FRAMEWORK_PROPERTY_FILE_PATH = "se.pedcat.framework.config.file.path";

	/** The properties. */
	private static Properties properties = new Properties();

	/** The config map map. */
	private static Map<String, Map<String, Map<String, Object>>> configMapMap = 
		new HashMap<String, Map<String, Map<String, Object>>>();
	
	/** The config map. */
	private static Map<String, Map<String, Object>> configMap = 
		new HashMap<String, Map<String, Object>>();
	
	/** The default config. */
	private static String[]  defaultConfig = 
		new String[]{"defaultSettings"};
	
	/** The environment list. */
	private static List<String> environmentList = 
		new ArrayList<String>(Arrays.asList(System.getProperty("se.pedcat.framework.environment")!=null?System.getProperty("se.pedcat.framework.environment").split(","):"defaultSetting".split(","))); //System.getProperty("se.r7earkiv.environment")!=null?System.getProperty("se.r7earkiv.environment").split(","):defaultConfig;
	
	/** The instance. */
	private static FrameworkConfiguration instance = 
		new FrameworkConfiguration();
	
	/**
	 * Instantiates a new framework configuration.
	 */
	private FrameworkConfiguration() 
	{
		try {
			for(String env:environmentList)
			{
				logger.info("ENVIRONMENT:" + env);
			}
		
			String propertyFilePath = System.getProperty(FRAMEWORK_PROPERTY_FILE_PATH);

			if (propertyFilePath!=null)
			{
				File file = new File(propertyFilePath);
				if (file.exists())
				{
					this.loadXmlProperties(new FileInputStream(propertyFilePath));
				}
			}
			
			if (!loadXmlProperties(FILE_1))
			{
				if (!loadXmlProperties(FILE_2))
				{
					if (!loadXmlProperties(FILE_3))
					{
						
					}
				}
			}
			String[] fileNames = this.getProperty("message.property.files","se.pedcat.framework.common.messages.messages").split(",");
			for(String fileName:fileNames)
			{
				FrameworkResourceBundle.addBundle(fileName);
			}
			
		} catch (Exception e) {
			logger.error("Could not initialize ", e);
			
		}
	}
	
	/**
	 * Instantiates a new framework configuration.
	 *
	 * @param environments the environments
	 */
	protected FrameworkConfiguration(String[] environments) 
	{
		for(String environment:environments)
		{
			if (!environmentList.contains(environment))
			{
				environmentList.add(environment);
			}
		}
	
	}

	/**
	 * Gets the single instance of FrameworkConfiguration.
	 *
	 * @return single instance of FrameworkConfiguration
	 */
	public static FrameworkConfiguration getInstance() {
		return instance;
	}

	/**
	 * Load xml properties.
	 *
	 * @param property_xml_file the property_xml_file
	 * @return true, if successful
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean loadXmlProperties(final String property_xml_file)
			throws ParserConfigurationException, SAXException, IOException {
		logger.info("Laddar konfigurationsfilen " + property_xml_file);
		InputStream in = FrameworkConfiguration.class.getClassLoader().getResourceAsStream(property_xml_file);
		if (in != null) {
			if (this.loadXmlProperties(in))
			{
				logger.info("Loaded " + property_xml_file);
				return true;
			}
			else
			{
				return false;
			}
			
		} else {
			logger.warn("Kan inte hitta konfigurationsfilen "
					+ property_xml_file);
			return false;
		}

	}

	/**
	 * Load xml properties.
	 *
	 * @param in the in
	 * @return true, if successful
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public  boolean loadXmlProperties(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub 
		
		if (in!=null)
		{
			SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			EArkivConfigurationHandler handler = new EArkivConfigurationHandler(); 
			parser.parse(in,handler);
			return true;
		}
		else
		{
			logger.warn("Kan inte ladda konfiguration! (InputStream == null)");
			return false;
		}
	}

	/**
	 * Load properties.
	 *
	 * @param resource the resource
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadProperties(String resource) throws IOException {

		InputStream is = FrameworkConfiguration.class.getClassLoader()
				.getResourceAsStream(resource);
		if (is != null) {
			logger.info("Laddar konfigurationsfilen " + resource);
			properties.load(is);
		} else {
			logger.warn("Kan inte hitta konfigurationsfilen " + resource);
		}
	}

	/**
	 * Ladda in properties.
	 *
	 * @param properties the properties
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadProperties(Properties properties) throws IOException {
		properties.putAll(properties);
	}

	/**
	 * Gets the boolean property.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the boolean property
	 */
	public boolean getBooleanProperty(String key, boolean defaultValue) {
		return Boolean.valueOf(getProperty(key, "" + defaultValue))
				.booleanValue();
	}

	/**
	 * Gets the int property.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the int property
	 */
	public int getIntProperty(String key, int defaultValue) {
		return Integer.parseInt(getProperty(key, "" + defaultValue));
	}
	
	/**
	 * Gets the long property.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the long property
	 */
	public long getLongProperty(String key, long defaultValue) {
		return Long.parseLong(getProperty(key, "" + defaultValue));
	}


	/**
	 * Parsar konfigurationsfil.
	 *
	 * @author laha
	 */
	static class EArkivConfigurationHandler extends DefaultHandler {
		
		/** The current environment. */
		private String currentEnvironment = null;
		
		/** The current properties. */
		private String currentProperties = null;

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
		 */
		public void endDocument() throws SAXException {
			super.endDocument();
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
		 */
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			super.endElement(uri, localName, name);
			if (name.equals(ENVIRONMENT)) {
				currentEnvironment = null;
			}
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
		 */
		public void startDocument() throws SAXException {
			
			super.startDocument();

		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String localName,
				String elementName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, elementName, attributes);

			//logger.info(elementName);
			if (elementName.equals(ENVIRONMENTS)) {
				defaultConfig = attributes.getValue("default").split(",");
			} else if (elementName.equals(ENVIRONMENT)) {
				currentEnvironment = attributes.getValue("name");
			} else if (elementName.equals(PROPERTIES)) {
				currentProperties = attributes.getValue("name");
			} else if (elementName.equals(PROPERTY)) {
				String name = attributes.getValue("name");
				String value = attributes.getValue("value");
				CommonUtility.addToMapInMapInMap(configMapMap,
						currentEnvironment, currentProperties, name, value);
				CommonUtility.addToMapInMap(configMap, currentEnvironment,
						name, value);
				logger.info(currentEnvironment + ":" + name + ":" + value);
			}
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the property
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public String getProperty(String key) {
		if (logger.isTraceEnabled())
		{
			logger.trace("Searching for " + key);
		}
		String value = null;
		if (!environmentList.isEmpty())
		{
			for(String env:environmentList)
			{
				Map map = (Map) configMap.get(env);
				if (map != null) {
					value = (String) map.get(key);
					if (value!=null)
					{
						break;
					}
				}
			}
		}
		if (value == null) {
			Map map = (Map) configMap.get("defaultSettings");
			if (map != null) {
				value = (String) map.get(key);
			}
		}
		if (value == null) {
			value = properties.getProperty(key);
		}
		if (value == null) {
			value = System.getProperty(key);
		}
		if (logger.isTraceEnabled())
		{
			logger.trace("Found " + value);
		}
		
		return value;
	}

	/**
	 * Sets the environment.
	 *
	 * @param prodEnvironment the new environment
	 */
	public void setEnvironment(String[] prodEnvironment) {
		// TODO Auto-generated method stub
		
		environmentList.addAll(Arrays.asList(prodEnvironment));
	}

	/**
	 * Gets the all properties dump.
	 *
	 * @return the all properties dump
	 */
	public String getAllPropertiesDump() {
		// TODO Auto-generated method stub
		Map<String, Object> defaultMap = null;
		Map<String, Object> envMap = null;
		StringBuffer sb = new StringBuffer();

		if (environmentList.isEmpty())
		{
			environmentList.addAll(Arrays.asList(defaultConfig));
		}
		
		logger.trace("Env " + environmentList.toString());
		
			for(String env:environmentList)
			{
				envMap = configMap.get(env);
				logger.trace("envMap " + (envMap != null));
				if (envMap != null) {
					Iterator<String> iter = envMap.keySet().iterator();
					while (iter.hasNext()) {
						String key = iter.next();
						sb.append(key + ":" + envMap.get(key));
						sb.append("\r\n");
					}
				}
			}
		
		Map<String, Object> dmap = configMap.get("defaultSettings");
		
		if (dmap != null) {
			Iterator<String> diter = dmap.keySet().iterator();
			while (diter.hasNext()) {
				String key = diter.next();

				if (envMap != null && envMap.containsKey(key)) {

				} else {
					sb.append(key + ":" + dmap.get(key));
					sb.append("\r\n");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Gets the properties.
	 *
	 * @param propertiesName the properties name
	 * @return the properties
	 */
	public Properties getProperties(String propertiesName) {

		Properties properties = new Properties();
		Map<String, Map<String, Object>> map = configMapMap
				.get("defaultSettings");
		if (map != null) {
			Map<String, Object> map2 = map.get(propertiesName);
			if (map2 != null) {
				properties.putAll(map2);
			}
		}
		for(String env:environmentList)
		{
			map = configMapMap.get(env);
			if (map != null) {
				Map<String, Object> map2 = map.get(propertiesName);
				if (map2 != null) {
					properties.putAll(map2);
				}
			}
		}
		return properties;
	}

	/**
	 * Adds the property.
	 *
	 * @param name the name
	 * @param value the value
	 */
	protected void addProperty(String name, String value) {
		properties.put(name, value);
	}

}
