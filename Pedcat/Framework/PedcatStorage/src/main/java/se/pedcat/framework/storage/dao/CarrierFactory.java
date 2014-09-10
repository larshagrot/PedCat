/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.storage.dao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.util.MapCache;
import se.pedcat.framework.common.model.util.ModelException;
import se.pedcat.framework.common.model.util.ModelTrace;
import se.pedcat.framework.common.model.util.SetCache;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.FrameworkException;
import se.pedcat.framework.common.util.FrameworkMessage;
import se.pedcat.framework.common.util.Log;
import se.pedcat.framework.common.util.Trace;


/**
 * Is supposed to do nearly ALL direct calls to the database
 * Concrete FrameworkDAO implementations shall be registred with (as example below)
 * <code>public static void registerDAO(Class keyClass, Class crudClass, Class queryClass, Class daoClass)</code>
 * 
 * *.
 *
 * @author Lars Hagrot
 */
public abstract class CarrierFactory   implements java.io.Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	static Logger logger = LoggerFactory
	.getLogger(CarrierFactory.class);

    /** maps query classes and infohomes. */
    private static Map queryMap = new java.util.HashMap();
    
    /** maps Carrier classes and infohomes. */
    private static Map carrierMap = new java.util.HashMap();
    
    /** maps key classes and infohomes. */
    private static Map keyMap = new java.util.HashMap();
    
    /** Maps keys to carriers. */
    private static Map keyCarrierMap = new java.util.HashMap();
    
    /** Maps query types to cached queries. */
    private static Map<String,Map<String,Map<? extends ModelQuery,? extends ModelQuery>>> queriesMapMapMap = new java.util.HashMap<String,Map<String,Map<? extends ModelQuery,? extends ModelQuery>>>();
    //private static Map<String,Map<? extends ModelQuery,? extends ModelQuery>> queriesMapMapMap = new java.util.HashMap<String,Map<? extends ModelQuery,? extends ModelQuery>>();
    
    /** Maps query types to cached queries. */
    private static Map<String,Map<String,Map<? extends ModelQuery,? extends ModelQuery>>> queriesCountMapMapMap = new java.util.HashMap<String,Map<String,Map<? extends ModelQuery,? extends ModelQuery>>>();
    //private static Map<String,Map<? extends ModelQuery,? extends ModelQuery>> queriesCountMapMapMap = new java.util.HashMap<String,Map<? extends ModelQuery,? extends ModelQuery>>();
    
    /** Maps carrier type to query type queries. */
    private static Map carrierQueryMap = new java.util.HashMap();
    
    /** Maps query type to carrier type. */
    private static Map queryCarrierMap = new java.util.HashMap();
    
    /** Holds carriers created or updated in current transaction. */
    private static MapCache transactionMapCache          = new MapCache();
    
    /** Holds carriers deleted in current transaction. */
    private static MapCache transactionDeleteMapCache    = new MapCache();
    
    /** FrameworkDAO objects cached by type. */
    private static Map cachedClassDAOMap      = new java.util.HashMap();
    
    /** The update set cache. */
    private static SetCache updateSetCache =  new SetCache();
    
    /** The cached queries. */
    protected static int cachedQueries = 0;
    
    /** The new queries. */
    protected static int newQueries = 0;
    
    /** The cached objects. */
    protected static int cachedObjects = 0;
    
    /** The returned objects. */
    protected static int returnedObjects = 0;
    
    static
    {
        /** Bind keys, carriers, queries and DAOs 
         *  Commented because we do not want dependency of concrete classes here
         * */
    	/*registerDAO(Ansvarig.AnsvarigKey.class,Ansvarig.class,Ansvarig.AnsvarigQuery.class,AnsvarigDAO.class);
    	registerDAO(Jobbstatus.JobbstatusKey.class,Jobbstatus.class,Jobbstatus.JobbstatusQuery.class,JobbstatusDAO.class);
    	registerDAO(Jobbtyp.JobbtypKey.class,Jobbtyp.class,Jobbtyp.JobbtypQuery.class,JobbtypDAO.class);
    	registerDAO(Jobb.JobbKey.class,Jobb.class,Jobb.JobbQuery.class,JobbDAO.class);
    	registerDAO(Vardform.VardformKey.class,Vardform.class,Vardform.VardformQuery.class,VardformDAO.class);
    	registerDAO(Atgard.AtgardKey.class,Atgard.class,Atgard.AtgardQuery.class,AtgardDAO.class);
    	registerDAO(Huvuddiagnos.HuvuddiagnosKey.class,Huvuddiagnos.class,Huvuddiagnos.HuvuddiagnosQuery.class,HuvuddiagnosDAO.class);
    	registerDAO(Bidiagnos.BidiagnosKey.class,Bidiagnos.class,Bidiagnos.BidiagnosQuery.class,BidiagnosDAO.class);
    	registerDAO(PatientrelateradKontakt.PatientrelateradKontaktKey.class,PatientrelateradKontakt.class,PatientrelateradKontakt.PatientrelateradKontaktQuery.class,PatientrelateradKontaktDAO.class);
    	
    	registerDAO(Vardenhet.VardenhetKey.class,Vardenhet.class,Vardenhet.VardenhetQuery.class,VardenhetDAO.class);
    	registerDAO(Journalforare.JournalforareKey.class,Journalforare.class,Journalforare.JournalforareQuery.class,JournalforareDAO.class);
    	registerDAO(Leveransobjekt.Key.class,Leveransobjekt.class,Leveransobjekt.LeveransobjektQuery.class,LeveransobjektDAO.class);
        registerDAO(Inleverans.Key.class,Inleverans.class,Inleverans.InleveransQuery.class,InleveransDAO.class);
        registerDAO(Organisation.OrganisationKey.class,Organisation.class,Organisation.OrganisationQuery.class,OrganisationDAO.class);
        registerDAO(Journalpost.Key.class,Journalpost.class,JournalpostQuery.class,JournalpostDAO.class);
        registerDAO(Journalpostinnehall.Key.class,Journalpostinnehall.class,JournalpostinnehallQuery.class,JournalpostinnehallDAO.class);
        registerDAO(User.UserKey.class,User.class,User.UserQuery.class,UserDAO.class);
        registerDAO(UserRoll.Key.class,UserRoll.class,UserRoll.UserRollQuery.class,UserRollDAO.class);
        registerDAO(Arkivtyp.Key.class,Arkivtyp.class,Arkivtyp.ArkivtypQuery.class,ArkivtypDAO.class);
        registerDAO(Status.Key.class,Status.class,Status.StatusQuery.class,StatusDAO.class);
        registerDAO(Innehallstyp.Key.class,Innehallstyp.class,Innehallstyp.InnehallstypQuery.class,InnehallstypDAO.class);
        registerDAO(Informationstyp.InformationstypKey.class,Informationstyp.class,Informationstyp.InformationstypQuery.class,InformationstypDAO.class);
        registerDAO(Sokrubrik.SokrubrikKey.class,Sokrubrik.class,Sokrubrik.SokrubrikQuery.class,SokrubrikDAO.class);
        registerDAO(Aktivitetstyp.AktivitetstypKey.class,Aktivitetstyp.class,Aktivitetstyp.AktivitetstypQuery.class,AktivitetstypDAO.class);
        registerDAO(Klinik.KlinikKey.class,Klinik.class,Klinik.KlinikQuery.class,KlinikDAO.class);
        registerDAO(Befattning.BefattningKey.class,Befattning.class,Befattning.BefattningQuery.class,BefattningDAO.class);
        registerDAO(Inrattning.InrattningKey.class,Inrattning.class,Inrattning.InrattningQuery.class,InrattningDAO.class);
        registerDAO(Traffbild.Key.class,Traffbild.class,TraffbildQuery.class,TraffbildDAO.class);
        registerDAO(Patientinfo.Key.class,Patientinfo.class,PatientinfoQuery.class,PatientinfoDAO.class);
        registerDAO(Leveransfil.LeveransfilKey.class,Leveransfil.class,Leveransfil.LeveransfilQuery.class,LeveransfilDAO.class);
        registerDAO(Kallsystem.KallsystemKey.class,Kallsystem.class,Kallsystem.KallsystemQuery.class,KallsystemDAO.class);
        registerDAO(Kallsystemversion.KallsystemversionKey.class,Kallsystemversion.class,Kallsystemversion.KallsystemversionQuery.class,KallsystemversionDAO.class);
        registerDAO(Exportprogramversion.ExportprogramversionKey.class,Exportprogramversion.class,Exportprogramversion.ExportprogramversionQuery.class,ExportprogramversionDAO.class);
        registerDAO(Exportprogram.ExportprogramKey.class,Exportprogram.class,Exportprogram.ExportprogramQuery.class,ExportprogramDAO.class);
        registerDAO(Foretag.ForetagKey.class,Foretag.class,Foretag.ForetagQuery.class,ForetagDAOImpl.class);
        registerDAO(Rapport.RapportKey.class,Rapport.class,Rapport.RapportQuery.class,RapportSPDAO.class);
        registerDAO(Konto.KontoKey.class,Konto.class,Konto.KontoQuery.class,KontoDAO.class);
        registerDAO(Rapporttyp.RapporttypKey.class,Rapporttyp.class,Rapporttyp.RapporttypQuery.class,RapporttypDAO.class);
        registerDAO(Handlingstyp.HandlingstypKey.class,Handlingstyp.class,Handlingstyp.HandlingstypQuery.class,HandlingstypDAO.class);
        
        registerDAO(Verifikationstyp.VerifikationstypKey.class,Verifikationstyp.class,Verifikationstyp.VerifikationstypQuery.class,VerifikationstypDAO.class);
        registerDAO(Koddeltyp.KoddeltypKey.class,Koddeltyp.class,Koddeltyp.KoddeltypQuery.class,KoddeltypDAO.class);
        registerDAO(Koddel.KoddelKey.class,Koddel.class,Koddel.KoddelQuery.class,KoddelDAO.class);
        registerDAO(Koddelsdefinition.KoddelsdefinitionKey.class,Koddelsdefinition.class,Koddelsdefinition.KoddelsdefinitionQuery.class,KoddelsdefinitionDAO.class);
        registerDAO(XMLSchemainnehall.XMLSchemainnehallKey.class,XMLSchemainnehall.class,XMLSchemainnehall.XMLSchemainnehallQuery.class,XMLSchemainnehallDAO.class);
        registerDAO(XSLTStylesheetinnehall.XSLTStylesheetinnehallKey.class,XSLTStylesheetinnehall.class,XSLTStylesheetinnehall.XSLTStylesheetinnehallQuery.class,XSLTStylesheetinnehallDAO.class);
        registerDAO(SchemaStylesheetKombination.SchemaStylesheetKombinationKey.class,SchemaStylesheetKombination.class,SchemaStylesheetKombination.SchemaStylesheetKombinationQuery.class,SchemaStylesheetKombinationDAO.class);
        registerDAO(Nyckelordstyp.NyckelordstypKey.class,Nyckelordstyp.class,Nyckelordstyp.NyckelordstypQuery.class,NyckelordstypDAO.class);
        registerDAO(Nyckelordstyptyp.NyckelordstyptypKey.class,Nyckelordstyptyp.class,Nyckelordstyptyp.NyckelordstyptypQuery.class,NyckelordstyptypDAO.class);
        registerDAO(Nyckelordsdatatyp.NyckelordsdatatypKey.class,Nyckelordsdatatyp.class,Nyckelordsdatatyp.NyckelordsdatatypQuery.class,NyckelordsdatatypDAO.class);
        registerDAO(Loggposttyp.LoggposttypKey.class,Loggposttyp.class,Loggposttyp.LoggposttypQuery.class,LoggposttypDAO.class);
        registerDAO(Loggpost.LoggpostKey.class,Loggpost.class,Loggpost.LoggpostQuery.class,LoggpostDAO.class);
        registerDAO(Loggpostinnehall.LoggpostinnehallKey.class,Loggpostinnehall.class,Loggpostinnehall.LoggpostinnehallQuery.class,LoggpostinnehallDAO.class);
        registerDAO(LoggpostNyckelord.LoggpostNyckelordKey.class,LoggpostNyckelord.class,LoggpostNyckelord.LoggpostNyckelordQuery.class,LoggpostNyckelordDAO.class);
        registerDAO(Nyckelord.NyckelordKey.class,Nyckelord.class,Nyckelord.NyckelordQuery.class,NyckelordDAO.class);
        registerDAO(Datatyp.DatatypKey.class,Datatyp.class,Datatyp.DatatypQuery.class,DatatypDAO.class);
        */
        
    }
    
    
    
    /**
     * Constructor.
     *
     * @roseuid 3AD5325A03D6
     */
    protected CarrierFactory()
    {
    }
    
    /**
     * Instantiates a FrameworkDAO implementation.
     *
     * @param daoClass the dao class
     * @return the framework dao
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     */
    public static FrameworkDAO createDAO(Class daoClass)
    throws java.lang.InstantiationException,java.lang.IllegalAccessException
    {
        return (FrameworkDAO) daoClass.newInstance();
    }
    
    /**
     * returns an instance of a FrameworkDAO Impl.
     *
     * @param daoClass the dao class
     * @param connection the connection
     * @return the dAO
     */
    public static FrameworkDAO getDAO(Class daoClass,Connection connection)
    {
        FrameworkDAO dao = null;
        try
        {
            if (daoClass!=null)
            {
	        	if (connection==null)
	            {
	                dao = (FrameworkDAO) cachedClassDAOMap.get(daoClass.getName());
	                if (dao==null)
	                {
	                    dao = createDAO(daoClass);
	                    cachedClassDAOMap.put(daoClass.getName(),dao);
	                }
	            }
	            else
	            {
	                dao = createDAO(daoClass);
	                dao.setConnection(connection);
	            }
            }
            else
            {
            	logger.error("daoClass == null");
            }
        }
        catch (Exception e)
        {
            throw new ModelException("Could not get/create FrameworkDAO implementation for " + daoClass.getName(),null);
        }
        return dao;
    }
    
    
    
    
    /**
     * Looks up home on primary key type.
     *
     * @param key primary key
     * @param connection the connection
     * @return FrameworkDAO
     * @roseuid 3AB4755E02D1
     */
    public static FrameworkDAO getDAO(Key key,Connection connection)
    {
        Class daoClass = (Class) keyMap.get(key.getClass().getName());
        if (daoClass==null)
        {
        	logger.error("DAOClass not found " + key.getClass().getName());
        	throw new FrameworkException("DAOClass not found " + key.getClass().getName());
        	
        }
        FrameworkDAO dao =   getDAO(daoClass,connection);
        if (dao==null)
        {
        	logger.error("DAOClass could not be found for " + key.getClass().getName());
        }
        return dao;
    }
    
    /**
     * Looks up home on primary key type.
     *
     * @param key primary key
     * @return FrameworkDAO
     */
    public static FrameworkDAO getDAO(Key key)
    {
        return getDAO(key,null);
    }
    
    /**
     * Gets the dAO.
     *
     * @param carrier the carrier
     * @param connection the connection
     * @return FrameworkDAO
     * @roseuid 3AD53CA70373
     */
    public static FrameworkDAO getDAO(Carrier carrier,Connection connection)
    {
        Class daoClass = (Class) carrierMap.get(carrier.getClass().getName());
        if (daoClass==null)
        {
        	logger.error("DAOClass not found " + carrier.getClass().getName());
        	return null;
        }
        return getDAO(daoClass,connection);
    }
    
    /**
     * Gets the dAO.
     *
     * @param carrier the carrier
     * @return the dAO
     */
    public static FrameworkDAO getDAO(Carrier carrier)
    {
        return getDAO(carrier,null);
    }
    
    
    /**
     * Looks up home on query type.
     *
     * @param query the query
     * @param connection the connection
     * @return the dAO
     * @roseuid 3AD5325A03E0
     */
    public static FrameworkDAO getDAO(ModelQuery query,Connection connection)
    {
        Class daoClass = (Class) queryMap.get(query.getClass().getName());
        if (daoClass==null)
        {
        	logger.error("DAOClass not found " + query.getClass().getName());
        	return null;
        }
        return getDAO(daoClass,connection);
    }
    
    
    
    /**
     * Gets the dAO.
     *
     * @param query the query
     * @return the dAO
     */
    public static FrameworkDAO getDAO(ModelQuery query)
    {
        return getDAO(query,null);
    }
    
    
    
    /**
     * Register dao.
     *
     * @param keyClass the key class
     * @param carrierClass the carrier class
     * @param queryClass the query class
     * @param daoClass the dao class
     * @roseuid 3AD5325B000D
     */
    protected static void registerDAO(Class keyClass, Class carrierClass, Class queryClass, Class daoClass)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"registerDAO",isVerbose());
        trace.logMessage(keyClass!=null?keyClass.getName():"null");
        trace.logMessage(carrierClass.getName());
        trace.logMessage(queryClass.getName());
        trace.logMessage(daoClass.getName());
        if (keyClass!=null)
        {
            keyMap.put(keyClass.getName(),daoClass);
            keyCarrierMap.put(keyClass.getName(),carrierClass);
        }
        carrierMap.put(carrierClass.getName(),daoClass);
        queryMap.put(queryClass.getName(),daoClass);
        carrierQueryMap.put(carrierClass.getName(),queryClass);
        queryCarrierMap.put(queryClass.getName(),carrierClass);
    }
    
    
    
    
    /**
     * Parses a query and returns matching objects.
     *
     * @param query the query
     * @return array with objects
     * @roseuid 3AD5325B0070
     */
    public static int countByQuery(ModelQuery query) 
    {
        ModelTrace trace = ModelTrace.startTrace(logger,(Object)null,CarrierFactory.class,"countByQuery("+query.getClass().getName(),isVerbose(),false);
        //trace.doAssert(query!=null,"Can not find by NULL query",R7EArkivMessage.CANNOT_FIND_BY_NULL_QUERY);
        ModelQuery cachedQuery = null;
        int count = 0;
        try
        {
            //trace.dumpProperties(query);
            boolean isUpdating = isUpdating(query);
            trace.logMessage("isUpdating " + isUpdating);
            FrameworkDAO dao = getDAO(query);
            if (dao.isQueryCaching() && !isUpdating)
            {
                cachedQuery = getCachedQuery(dao,query);
                if (cachedQuery!=null)
                {
                    trace.logMessage("Getting cached result from cache ");
                    count = cachedQuery.getCountResult();
                }
            }
            else
            {
                trace.logMessage("Not cached or is updating....");
            }
            if (cachedQuery==null)
            {
                trace.logMessage("Have to query database...");
                count =  dao.countByQuery(query);
                newQueries++;
                if (dao.isQueryCaching())
                {
                    trace.logMessage("Caching result...");
                    setCachedResult(dao,query,count);
                    trace.logMessage("Stored in carrier query cache...");
                }
            }
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could no query for carriers",FrameworkMessage.FATALT,e,query);
        }
        finally
        {
            trace.end();
        }
        return count;
    }
    
    
    /**
     * Gets the cached query.
     *
     * @param dao the dao
     * @param query the query
     * @return the cached query
     */
    public static ModelQuery getCachedQuery(FrameworkDAO dao,ModelQuery query)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"getCachedQuery",isVerbose());
        
        Map   queriesMap =  getQueriesMap(dao,query.getClass().getName(),false);
        if (queriesMap!=null && queriesMap.size()>0)
        {
            trace.logMessage("Searching amongst " + queriesMap.size());
            ModelQuery cachedQuery = (ModelQuery) queriesMap.get(query);
            if (cachedQuery!=null)
            {
                
                if (!cachedQuery.isExpired())
                {
                    if (!isUpdating(cachedQuery))
                    {
                        trace.logMessage("Returned cached result for " + cachedQuery.getClass().getName());
                        return cachedQuery;
                    }
                    else
                    {
                        trace.logMessage("Updating in call can not use cached query");
                    }
                }
                else
                {
                    trace.logMessage("ModelQuery timeout for " + cachedQuery.getClass().getName());
                }
            }
            else
            {
                trace.logMessage("Did not find cached query for " + query.getClass().getName());
                trace.dumpProperties(query);
            }
        }
        return null;
    }
    
    /**
     * Gets the queries map.
     *
     * @param dao the dao
     * @param name the name
     * @param create the create
     * @return the queries map
     */
    private static Map getQueriesMap(FrameworkDAO dao, String name, boolean create) {
    	
    	if (dao!=null)
    	{
    		Map<String,Map<? extends ModelQuery,? extends ModelQuery>> queriesMapMap = queriesMapMapMap.get(dao.getConnectionPoolName());
    		if (queriesMapMap==null)
    		{
    			queriesMapMap = new HashMap<String,Map<? extends ModelQuery,? extends ModelQuery>>();
    			queriesMapMapMap.put(dao.getConnectionPoolName(),queriesMapMap);
    		}
    		Map map =  queriesMapMap.get(name);
    		if (map==null && create)
    		{
    			map = new HashMap();
    			queriesMapMap.put(name,map);
    		}
    		return map;
    		
    	}
    	else
    	{
    		return null;
    	}
	}
    
    /**
     * Gets the queries maps.
     *
     * @param name the name
     * @return the queries maps
     */
    private static Map[] getQueriesMaps(String name) {
    	List<Map> mapList = new ArrayList<Map>();
    	if (name!=null)
    	{
    		for(Map map:queriesMapMapMap.values())
    		{
    			Map qmap = (Map) map.get(name);
    			if (qmap!=null)
    			{
    				mapList.add(qmap);
    			}
    		}
    	}
    	return mapList.toArray(new Map[0]);
    		
	}
    
    
    /**
     * Gets the queries count map.
     *
     * @param dao the dao
     * @param name the name
     * @param create the create
     * @return the queries count map
     */
    private static Map getQueriesCountMap(FrameworkDAO dao, String name, boolean create) {
    	
    	if (dao!=null)
    	{
    		Map<String,Map<? extends ModelQuery,? extends ModelQuery>> queriesMapMap = queriesCountMapMapMap.get(dao.getConnectionPoolName());
    		if (queriesMapMap==null)
    		{
    			queriesMapMap = new HashMap<String,Map<? extends ModelQuery,? extends ModelQuery>>();
    			queriesCountMapMapMap.put(dao.getConnectionPoolName(),queriesMapMap);
    		}
    		Map map =    queriesMapMap.get(name);
    		if (map==null && create)
    		{
    			map = new HashMap();
    			queriesMapMap.put(name,map);
    		}
    		return map;
    	}
    	else
    	{
    		return null;
    	}
	}

	/**
	 * Gets the cached query count.
	 *
	 * @param dao the dao
	 * @param query the query
	 * @return the cached query count
	 */
	public static ModelQuery getCachedQueryCount(FrameworkDAO dao,ModelQuery query)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"getCachedQuery",isVerbose());
        
        Map   queriesMap =   getQueriesCountMap(dao,query.getClass().getName(),false);
        if (queriesMap!=null && queriesMap.size()>0)
        {
            trace.logMessage("Searching amongst " + queriesMap.size());
            ModelQuery cachedQuery = (ModelQuery) queriesMap.get(query);
            if (cachedQuery!=null)
            {
                
                if (!cachedQuery.isExpired())
                {
                    if (!isUpdating(cachedQuery))
                    {
                        trace.logMessage("Returned cached result for " + cachedQuery.getClass().getName());
                        return cachedQuery;
                    }
                    else
                    {
                        trace.logMessage("Updating in call can not use cached query");
                    }
                }
                else
                {
                    trace.logMessage("ModelQuery timeout for " + cachedQuery.getClass().getName());
                }
            }
            else
            {
                trace.logMessage("Did not find cached query for " + query.getClass().getName());
                trace.dumpProperties(query);
            }
        }
        return null;
    }
    
    
    /**
     * Gets the cached result.
     *
     * @param dao the dao
     * @param query the query
     * @return the cached result
     */
    public static Carrier[] getCachedResult(FrameworkDAO dao,ModelQuery query)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"getCachedResult",isVerbose());
        
        ModelQuery cachedQuery = getCachedQuery(dao,query);
        if (cachedQuery!=null)
        {
            return (Carrier[]) cachedQuery.getResult();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Sets the cached result.
     *
     * @param dao the dao
     * @param query the query
     * @param carriers the carriers
     */
    public static void setCachedResult(FrameworkDAO dao,ModelQuery query,Carrier[] carriers)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"setCachedResult",isVerbose());
        trace.dumpProperties(query);
        query.setTimestamp(System.currentTimeMillis());
        query.setResult(carriers);
        query.setCountResult(carriers!=null?carriers.length:0);
        Map   queriesMap = getQueriesMap(dao,query.getClass().getName(),true);
        queriesMap.put(query,query);
    }
    
    /**
     * Gets the cached result count.
     *
     * @param dao the dao
     * @param query the query
     * @return the cached result count
     */
   public static Integer getCachedResultCount(FrameworkDAO dao,ModelQuery query)
   {
       Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"getCachedResult",isVerbose());
       
       ModelQuery cachedQuery = getCachedQueryCount(dao,query);
       if (cachedQuery!=null)
       {
           return new Integer(cachedQuery.getCountResult()); 
       }
       else
       {
           return null;
       }
   }
   
   /**
    * Sets the cached result count.
    *
    * @param dao the dao
    * @param query the query
    */
   public static void setCachedResultCount(FrameworkDAO dao,ModelQuery query)
   {
       Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"setCachedResult",isVerbose());
       trace.dumpProperties(query);
       query.setTimestamp(System.currentTimeMillis());
       
       Map   queriesMap = getQueriesCountMap(dao, query.getClass().getName(),true);
       
       queriesMap.put(query,query);
   }
    
    /**
     * Sets the cached result.
     *
     * @param dao the dao
     * @param query the query
     * @param count the count
     */
    public static void setCachedResult(FrameworkDAO dao,ModelQuery query, int count)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"setCachedResult",isVerbose());
        trace.dumpProperties(query);
        query.setTimestamp(System.currentTimeMillis());
        query.setResult(null);
        query.setCountResult(count);
        Map   queriesMap = getQueriesMap(dao, query.getClass().getName(),true);
        queriesMap.put(query,query);
    }
    
    
    /**
     * Parses a query and returns matching objects.
     *
     * @param query special query instance which imply type of domain value objects
     * @param connection to be used
     * @return array with objects
     * @roseuid 3AD5325B0070
     */
    public static Carrier[] findByQuery(ModelQuery query,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"findByQuery",isVerbose());
        Carrier[] carriers = null;
        try
        {
            FrameworkDAO dao = getDAO(query,connection);
            carriers = dao.findByQuery(query);
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not query for carriers",FrameworkMessage.FATALT,e);
        }
        finally
        {
            trace.end();
        }
        return carriers;
    }
    
    
    
    /**
     * Finds object by primary key
     * Caller is responsible for connection handling.
     *
     * @param key key object
     * @param connection  open connection
     * @return Carrier found object
     */
    public static Carrier findByPrimaryKey(Key key,Connection connection) 
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"findByPrimaryKey(Key,Connection)",isVerbose());
        Carrier carrier = null;
        try
        {
            
            FrameworkDAO dao = getDAO(key,connection);
            
            carrier =  dao.findByPrimaryKey(key);
        }
        catch (Exception e)
        {
            trace.throwFrameworkException("Could not query for carriers",FrameworkMessage.FATALT,e);
        }
        finally
        {
            trace.end();
        }
        return carrier;
    }
    
    /**
     * Checks if is verbose.
     *
     * @return true, if is verbose
     */
    protected static boolean isVerbose()
    {
        return logger.isTraceEnabled();
    }
    
    /**
     * Flushes all query caches and query caches.
     */
    public static void flushQueryCaches()
    {
        queriesMapMapMap.clear();
        queriesCountMapMapMap.clear();
    }
    
    /**
     * Flushes all caches and query caches.
     */
    public static void flushAllCaches()
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"flushAllCaches",isVerbose());
        FrameworkDAOImpl.flushAllCaches();
        queriesMapMapMap.clear();
        queriesCountMapMapMap.clear();
        flush();
    }
    
    /**
     * Flushes cache and query cache for a certain type of carrier.
     *
     * @param classObject the class object
     */
    public static void flushCache(Class classObject)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"flushCache("+ classObject.getName() + ")",isVerbose());
        try
        {
            Class daoClass = (Class) carrierMap.get(classObject.getName());
            FrameworkDAO dao = (FrameworkDAO) getDAO(daoClass,null);
            if (dao!=null && dao.isCaching())
            {
                dao.flushCache();
            }
            if (dao!=null && dao.isQueryCaching())
            {
                Class queryClass =  (Class) carrierQueryMap.get(classObject.getName());
                if (queryClass!=null)
                {
                    Map<? extends ModelQuery,? extends ModelQuery> map = getQueriesMap(dao,queryClass.getName(),false);
                    if (map!=null)
                    {
                        trace.logMessage("Removing " + map.size() + " queries from querycache");
                        Collection<? extends ModelQuery> i = map.values();
                        map.clear();
                        for(ModelQuery mq:i)
                        {
                        	mq.clear();
                        }
                        
                    }
                }
                            }
            if (dao!=null && dao.isCaching())
            {
            	dao.flushCache();
            }
        }
        finally
        {
            trace.end();
        }
    }
    
    /**
     * Flushes query cache for a certain type of carrier.
     *
     * @param classObject the class object
     */
    public static void flushQueryCache(Class<?> classObject)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"flushQueryCache("+ classObject.getName() + ")",isVerbose());
        try
        {
            Class<?> daoClass = (Class) carrierMap.get(classObject.getName());
            FrameworkDAO<?,?> dao = (FrameworkDAO<?,?>) getDAO(daoClass,null);
            
            if (dao!=null && dao.isQueryCaching())
            {
                Class<?> queryClass =  (Class<?>) carrierQueryMap.get(classObject.getName());
                if (queryClass!=null)
                {
                    Map<?,?>[] maps = getQueriesMaps(queryClass.getName());
                    if (maps!=null)
                    {
                        
                        for(Map<?,?> map:maps)
                        {	
                        	trace.logMessage("Removing " + map.size() + " queries from querycache " + classObject.getName() );
                        	map.clear();
                        }
                    }
                }
                if (dao.isCaching())
                {
                	dao.flushCache();
                }
                
            }
            else
            {
            	if (dao==null)
            	{
            		trace.logError("No dao found for " + classObject.getName());
            	}
            }
           
        }
        finally
        {
            trace.end();
        }
    }
    
    
	/**
     * Flushes query cache for a certain type of carrier.
     *
     * @param classObjectName the class object name
     */
    public static void flushQueryCache(String classObjectName)
    {
        Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"flushQueryCache("+ classObjectName + ")",isVerbose());
        try
        {
            Class daoClass = (Class) carrierMap.get(classObjectName);
            FrameworkDAO dao = (FrameworkDAO) getDAO(daoClass,null);
            
            if (dao!=null && dao.isQueryCaching())
            {
                Class queryClass =  (Class) carrierQueryMap.get(classObjectName);
                if (queryClass!=null)
                {
                    Map map = getQueriesMap(dao,queryClass.getName(),false);
                    if (map!=null)
                    {
                        trace.logMessage("Removing " + map.size() + " queries from querycache");
                        map.clear();
                        
                    }
                }
                
            }
            else
            {
            	if (dao==null)
            	{
            		trace.logError("No dao found for " + classObjectName);
            	}
            }
           
        }
        finally
        {
            trace.end();
        }
    }
    
    
    /**
     * Objects in transaction cache.
     *
     * @return the int
     */
    public static int objectsInTransactionCache()
    {
        int count = 0;
        Map map = transactionMapCache.getMap(false);
        if (map!=null)
        {
            count += map.size();
        }
        map = transactionDeleteMapCache.getMap(false);
        if (map!=null)
        {
            count += map.size();
        }
        return count;
    }
    
    /**
     * Returns carrier from inserted and/or updated carriers.
     *
     * @param key the key
     * @return the from transaction cache
     */
    public static Carrier getFromTransactionCache(Key key)
    {
    	Carrier carrier = null;
    
    	if (isUseTransactionCache())
    	{
	        Map map = transactionMapCache.getMap(false);
	    	   
	        if (map!=null)
	        {
	            carrier= (Carrier)  map.get(key);
	        }
    	}
        
        return carrier;
    }
    
    /**
     * Checks if is use transaction cache.
     *
     * @return true, if is use transaction cache
     */
	private static boolean isUseTransactionCache() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Returns cache for inserted, deleted and/or updated carriers.
	 *
	 * @param create the create
	 * @return the transaction cache
	 */
    public static Map getTransactionCache(boolean create)
    {
        return transactionMapCache.getMap(create);
    }
    
    /**
     * Returns cache for deleted carriers.
     *
     * @param create the create
     * @return the transaction delete cache
     */
    public static Map getTransactionDeleteCache(boolean create)
    {
        return transactionDeleteMapCache.getMap(create);
    }
    
    /**
     * Flushes cache for inserted, deleted and/or updated carriers.
     */
    public static void initializeTransactionCache()
    {
        transactionMapCache.release();
        transactionDeleteMapCache.release();
    }
    
    /**
     * Sync transaction cache.
     *
     * @param commit the commit
     */
    public static void syncTransactionCache(boolean commit)
    {
        if (!isUseTransactionCache())
        {
        	return;
    	}
    	Trace trace = Trace.startTrace1(logger,CarrierFactory.class,"syncTransactionCache("+commit +")",isVerbose());
        try
        {
            
            // Store classes involved in this transaction to make it possible to flush query cache
            Set carrierSet = new HashSet();
            
            // Get created and updated carriers in this transaction
            Map updatedMap = getTransactionCache(false);
            
            if (updatedMap!=null && updatedMap.size()>0)
            {
                trace.logMessage("committing updates and inserts" + updatedMap.size());
                Iterator iterator = updatedMap.values().iterator();
                while (iterator.hasNext())
                {
                    Carrier carrier = (Carrier)iterator.next();
                    FrameworkDAO dao =  CarrierFactory.getDAO(carrier);
                    if (dao!=null && dao.isCaching())
                    {
                        if (commit)
                        {
                            dao.storeInCache(carrier);
                        }
                        else
                        {
                            dao.removeFromCache(carrier.getKey());
                        }
                    }
                    carrierSet.add(carrier.getClass());
                    
                }
                updatedMap.clear();
            }
            
            Map deletedMap = getTransactionDeleteCache(false);
            if (deletedMap !=null && deletedMap.size()>0)
            {
                trace.logMessage("committing deletions " + deletedMap.size());
                Iterator iterator = deletedMap.values().iterator();
                while (iterator.hasNext())
                {
                    KeyImpl keyImpl = (KeyImpl)iterator.next();
                    FrameworkDAO dao =  CarrierFactory.getDAO(keyImpl);
                    if (dao!=null && dao.isCaching())
                    {
                        dao.removeFromCache(keyImpl);
                    }
                    Class carrierClass = (Class) keyCarrierMap.get(keyImpl.getClass().getName());
                    if (carrierClass!=null)
                    {
                        carrierSet.add(carrierClass);
                    }
                }
                deletedMap.clear();
            }
            Iterator carrierSetIterator = carrierSet.iterator();
            while (carrierSetIterator.hasNext())
            {
                Class carrierClass = (Class) carrierSetIterator.next();
                trace.logMessage("Flushing querycache for " + carrierClass.getName());
                flushQueryCache(carrierClass);
            }
        }
        finally
        {
            trace.end();
        }
    }
    
    /**
     * Flushes cache for inserted, deleted and/or updated carriers.
     */
    public static void rollbackTransactionCache()
    {
        syncTransactionCache(false);
        initializeTransactionCache();
    }
    
    /**
     * Flushes cache for inserted, deleted and/or updated carriers.
     */
    public static void commitTransactionCache()
    {
        syncTransactionCache(true);
        initializeTransactionCache();
    }
    
    /**
     * Add to  cache for inserted and/or updated carriers.
     *
     * @param carrier the carrier
     * @param dao the dao
     */
    public static void addToTransactionCache(Carrier carrier, FrameworkDAO dao)
    {
    	if  (isUseTransactionCache())
    	{	
	        if (dao.isCaching())
	        {
	            Map map = getTransactionCache(false);
	            map.put(carrier.getKey(),carrier);
	        }
	        if (dao.isQueryCaching())
	        {
	            setUpdating(carrier.getClass());
	        }
    	}
    }
    
    /**
     * Add to  cache for inserted and/or updated carriers.
     *
     * @param carriers the carriers
     * @param dao the dao
     */
    public static void addToTransactionCache(Carrier[] carriers, FrameworkDAO dao)
    {
    	if  (isUseTransactionCache())
    	{
	    	if (dao.isCaching() )
	        {
	            Map map = getTransactionCache(true);
	            for(int index=0;index<carriers.length;index++)
	            {
	                map.put(carriers[index].getKey(),carriers[index]);
	            }
	        }
	        if (dao.isQueryCaching() && carriers!=null && carriers.length>0)
	        {
	            setUpdating(carriers[0].getClass());
	        }
    	}
    }
    
    
    
    /**
     * Add to cache with deleted carriers in this transaction
     * Removes carrier if it exist in cache for inserted and/or updated carriers.
     *
     * @param carrierKeys the carrier keys
     * @param dao the dao
     */
    public static void addToTransactionDeleteCache(Key[] carrierKeys, FrameworkDAO dao)
    {
        if (dao.isCaching())
        {
            Map map = getTransactionDeleteCache(true);
            for(int index=0;index<carrierKeys.length;index++)
            {
                map.put(carrierKeys[index],carrierKeys[index]);
            }
            map = getTransactionCache(false);
            if (map!=null)
            {
                for(int index=0;index<carrierKeys.length;index++)
                {
                    map.remove(carrierKeys[index]);
                }
            }
        }
        if (dao.isQueryCaching() && carrierKeys!=null && carrierKeys.length>0)
        {
            Class carrierClass = (Class) keyCarrierMap.get(carrierKeys[0].getClass().getName());
            setUpdating(carrierClass);
        }
    }
    
    /**
     * Collect statistics.
     *
     * @return the statistics
     */
    public static String getStatistics()
    {
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            Set set = carrierMap.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext())
            {
                String name = (String) iterator.next();
                Class daoClass = (Class)carrierMap.get(name);
                FrameworkDAO dao = getDAO(daoClass,null);
                if(dao==null)
                {
                	dao = (FrameworkDAO) daoClass.newInstance();
                }
                Map map = dao.getCache();
                if (map!=null)
                {
                    stringBuffer.append(name + ":" + map.size() + "\n");
                }
                else
                {
                    stringBuffer.append(name + ":NO CACHE YET\n");
                }
            }
            stringBuffer.append("*************\n");
            Set querySet = queriesMapMapMap.keySet();
            Iterator queryIterator = querySet.iterator();
            while (queryIterator.hasNext())
            {
                String  key  = (String) queryIterator.next();
                Map     map     = (Map) queriesMapMapMap.get(key);
                stringBuffer.append(key + ":" + map.size() + "\n");
            }
            stringBuffer.append("*************\n");
            
            
            stringBuffer.append("CarrierFactory.transactionMapCache       used " + transactionMapCache.used() +" free" + transactionMapCache.free() +"\n");
            stringBuffer.append("CarrierFactory.transactionDeleteMapCache used " + transactionDeleteMapCache.used() +" free" + transactionDeleteMapCache.free() +"\n");
            stringBuffer.append("CarrierFactory.updateSetCache            used " + updateSetCache.used() +" free" + updateSetCache.free() +"\n");
            stringBuffer.append("New queries not found in cached       " + newQueries + "\n");
            stringBuffer.append("Found and used cached queries         " + cachedQueries + "\n");
            stringBuffer.append("Returned cached query carrier objects " + cachedObjects + "\n");
            stringBuffer.append("Returned carrier objects              " + returnedObjects + "\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    
    /**
     * Flush.
     */
    public static void  flush()
    {
        transactionMapCache.flush();
        transactionDeleteMapCache.flush();
        updateSetCache.flush();
    }
    
    /**
     * Collect info about all carriers in cache.
     *
     * @return the string
     */
    public static String dumpCaches()
    {
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            Set set = carrierMap.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext())
            {
                String name = (String) iterator.next();
                Class daoClass = (Class)carrierMap.get(name);
                FrameworkDAO dao = (FrameworkDAO) daoClass.newInstance();
                Map map = dao.getCache();
                if (map!=null)
                {
                    stringBuffer.append(name + ":" + map.size() + "\n");
                    Collection carrierCollection = map.values();
                    SortedSet sortedSet = new TreeSet(carrierCollection);
                    Iterator sortedIterator = sortedSet.iterator();
                    while (sortedIterator.hasNext())
                    {
                        Carrier carrier = null;
                        try
                        {
                            carrier = (Carrier) sortedIterator.next();
                            stringBuffer.append(carrier.getObjectId() + "\t");
                            java.util.Date date = carrier.getAndradDatum();
                            if (date!=null)
                            {
                                stringBuffer.append(formatter.format(date));
                            }
                            else
                            {
                                stringBuffer.append("NULL");
                            }
                            stringBuffer.append("\n");
                        }
                        catch (Exception e)
                        {
                            if (carrier!=null)
                            {
                                Log.logMessage(logger,"dumpCaches",CommonUtility.dumpFields(carrier));
                            }
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    stringBuffer.append("No cache for " + name + "\n");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    
    /**
     * Collect info about all carriers in cache.
     *
     * @param carrierClass  type of carrier
     * @return the string
     */
    public static String dumpCache(Class carrierClass)
    {
        StringBuffer stringBuffer = new StringBuffer();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            
            String name =  carrierClass.getName();
            Class daoClass = (Class)carrierMap.get(name);
            FrameworkDAO dao = (FrameworkDAO) daoClass.newInstance();
            Map map = dao.getCache();
            stringBuffer.append(name + ":" + map.size() + "\n");
            Collection carrierCollection = map.values();
            SortedSet sortedSet = new TreeSet(carrierCollection);
            Iterator sortedIterator = sortedSet.iterator();
            while (sortedIterator.hasNext())
            {
                Carrier carrier = (Carrier) sortedIterator.next();
                stringBuffer.append(carrier.getObjectId() + "\t");
                stringBuffer.append(formatter.format(carrier.getAndradDatum()));
                stringBuffer.append("\n");
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    
    
    /**
     * Start trace.
     *
     * @param method the method
     * @param verbose the verbose
     * @return the trace
     */
    protected static Trace startTrace(String method,boolean verbose)
    {
        return Trace.startTrace1(logger,CarrierFactory.class,method,verbose);
    }
    
    
    /**
     * Sets the updating.
     *
     * @param carrierClass the new updating
     */
    public static void setUpdating(Class carrierClass)
    {
       if (false)
       {
	       Set set = getUpdateSet(true);
	       set.add(carrierClass);
    	}
    }
    
    /**
     * Checks if is updating.
     *
     * @param carrierClass the carrier class
     * @return true, if is updating
     */
    public static boolean isUpdating(Class carrierClass)
    {
        boolean rc = false;
        Set set = getUpdateSet(false);
        if (set!=null)
        {
            rc = set.contains(carrierClass);
        }
        return rc;
    }
    
    /**
     * Checks if is updating.
     *
     * @param query the query
     * @return true, if is updating
     */
    public static boolean isUpdating(ModelQuery query)
    {
        Class carrierClass = (Class) queryCarrierMap.get(query.getClass().getName());
        return isUpdating(carrierClass);
    }
    
    
    /**
     * Clear is updating.
     */
    public static void clearIsUpdating()
    {
        updateSetCache.release();
    }
    
    /**
     * Gets the update set.
     *
     * @param create the create
     * @return the update set
     */
    public static Set getUpdateSet(boolean create)
    {
        return updateSetCache.getSet(create);
    }
	
}

