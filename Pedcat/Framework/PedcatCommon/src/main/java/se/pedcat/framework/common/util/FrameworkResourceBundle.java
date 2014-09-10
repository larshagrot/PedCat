/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class FrameworkResourceBundle.
 */
public class FrameworkResourceBundle extends java.util.ResourceBundle 
{
	
	/** The bundles. */
	private static Map<String, ResourceBundle> bundles = new HashMap<String, ResourceBundle>();
	
	static
	{
		FrameworkResourceBundle.addBundle("se.pedcat.framework.utskrift.message.utskrift");
		FrameworkResourceBundle.addBundle("se.pedcat.framework.common.message.common");
        FrameworkResourceBundle.addBundle("se.pedcat.framework.bokning.message.bokning");        
        FrameworkResourceBundle.addBundle("se.pedcat.framework.verifieringsprotokoll.message.verifieringsprotokoll");
        FrameworkResourceBundle.addBundle("se.pedcat.framework.leveransvalidering.message.leveransvalidering");
	}
	
	/**
	 * Adds the bundle.
	 *
	 * @param file the file
	 */
	public static void addBundle(String file)
	{
		try
		{
		    if (!containsBundle(file)) {
		        bundles.put(file, ResourceBundle.getBundle(file));
		    }
		}
		catch(Exception e)
		{
			
		}
		
		
	}
	
	/**
	 * Adds the bundle.
	 *
	 * @param bundle the bundle
	 */
	public static void addBundle(ResourceBundle bundle)
	{
        try
        {
            if (!containsBundle(bundle.toString())) {
                bundles.put(bundle.toString(), bundle);
            }
        }
        catch(Exception e)
        {
            
        }
	}
	
	/**
	 * Instantiates a new framework resource bundle.
	 */
	public FrameworkResourceBundle()
	{

        String env = System.getProperty("se.r7earkiv.environment");
		try
		{	
 
            
            if (env !=null) {
                if (env.startsWith("sllearkiv"))
                {
                    addBundle("se.earkiv.common.messages.earkiv_messages");
                }
                if (env.startsWith("ts") || env.indexOf("tsearkiv")!=-1)
                {
                    addBundle("tsearkiv");      
                }
                else
                if (env.indexOf("r7earkiv")!=-1)
                {
                    addBundle("r7earkiv");      
                }
            }
            

	
			addBundle("se.r7earkiv.common.messages.r7earkiv_journaler_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_resursadministration_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_ekonomi_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_dokument_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_loggar_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_diarie_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_inleverans_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_flytt_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_verifieringsprotokoll_messages");
			addBundle("se.r7earkiv.common.messages.r7earkiv_messages");
			
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Kollar om en specifik properties-fil redan är laddad.
	 *
	 * @param aBundle the a bundle
	 * @return true, if successful
	 */
	private static boolean containsBundle(String aBundle) {
	    
        if (bundles.keySet().contains(aBundle)) {
            return true;
        }
        return false;
  
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	@Override
	public Object handleGetObject(String key) 
	{
		//Kolla först efter kundspecifika texter 
	    //(properties-filer för kundspecifika texter ska heta xxx_branded.properties)
        for(String bundleName : bundles.keySet())
        {
            if (bundleName.contains("branded")) {
                ResourceBundle bundle = bundles.get(bundleName);
                if (bundle.containsKey(key))
                {
                    return bundle.getString(key);
                }
            }
        }

        for(String bundleName : bundles.keySet())
        {
            ResourceBundle bundle = bundles.get(bundleName);
            if (bundle.containsKey(key))
            {
                return bundle.getString(key);
            }
        }
		System.out.println("Not found " + key);
		return null;
	
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#handleKeySet()
	 */
	@Override
	protected Set<String> handleKeySet() {
		// TODO Auto-generated method stub
		return super.handleKeySet();
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#getKeys()
	 */
	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		Vector<String> v = new Vector<String>();
		for(String bundleName : bundles.keySet())
		{
	        ResourceBundle bundle = bundles.get(bundleName);
			v.addAll(bundle.keySet());
		}
		
		return v.elements();
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#containsKey(java.lang.String)
	 */
	@Override
	public boolean containsKey(String key) {
	    
		for(String bundleName : bundles.keySet())
		{
		    ResourceBundle bundle = bundles.get(bundleName);
			if (bundle.containsKey(key))
			{
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#keySet()
	 */
	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		Set<String> v = new HashSet<String>();
        for(String bundleName : bundles.keySet())
        {
            ResourceBundle bundle = bundles.get(bundleName);
			v.addAll(bundle.keySet());
		}
		
		return v;
	}

	
}
