/*
 * Utility.java
 *
 * Created on November 27, 2001, 2:06 PM
 */
package se.pedcat.framework.storage.util;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.beans.*;
import java.lang.reflect.*;
import java.text.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.model.Carrier;
import se.pedcat.framework.common.model.Datumperiod;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.ModelUtility;
import se.pedcat.framework.common.model.util.ModelException;
import se.pedcat.framework.common.util.CommonUtility;
import se.pedcat.framework.common.util.Log;
import se.pedcat.framework.common.util.Trace;
import se.pedcat.framework.storage.dao.CarrierFactory;
import se.pedcat.framework.storage.dao.FrameworkDAOImpl;
import se.pedcat.framework.storage.dao.StorageConnectionPool;
import se.pedcat.framework.storage.service.GenericCarrierServiceImpl;


import java.rmi.*;
import java.io.*;
import java.math.BigDecimal;

import javax.ejb.EJBException;
import se.pedcat.framework.common.model.NumeriskPeriod;

/**
 * This is a general utility class
 * It is supposed to ONLY HAVE STATIC CLASS METHODS
 *
 *<!--
 * $Revision: 31 $
 * $Log: /prodex-utv/u1.2.2/framework/Utility.java $
 * 
 * 31    03-09-15 15:42 Ulf
 * 
 * 30    02-04-25 16:35 Larh
 *
 *
 *
 *-->
 * @author  larh
 * @version
 */
public class DAOUtility extends ModelUtility
{
	private static  Logger logger = LoggerFactory
	.getLogger(FrameworkDAOImpl.class);

	
    private final static String JDBC_SOURCE_NAME = "source1";

    /** Creates new Utility
     *  This should never happen....
     *
     *
     */
    protected DAOUtility() {
    }

    public static Connection getConnection(String name) throws java.sql.SQLException, ClassNotFoundException {
        return StorageConnectionPool.getInstance(name).getConnection();
    }

    public static void releaseConnection(String name,Connection connection) throws java.sql.SQLException {
        StorageConnectionPool.getInstance(name).releaseConnection(connection);
    }

    
    /**
     * This method clean up after the execution of a database query.
     * It closes the result set, statement and connection.
     *
     * @param rs resultset to close
     * @param statement statement to close
     * @param connection connection to close
     * @throws SystemException could not clean up something wrong
     */
    public static void cleanUp(String name, ResultSet rs, java.sql.Statement statement, Connection connection) throws java.sql.SQLException {
        Trace trace = Trace.startTrace1(logger,DAOUtility.class, "cleanUp", isVerbose());
        try {
            if (rs != null) {
                try {
                    trace.logMessage("Closing resultset..");
                    rs.close();
                } catch (java.sql.SQLException e) {
                // Dont care
                }
            }

            if (statement != null) {
                try {
                    trace.logMessage("Closing statement..");
                    statement.close();
                } catch (java.sql.SQLException e) {
                // Dont care
                }
            }

            if (connection != null) {
                try {
                    trace.logMessage("Closing connection..");
                    DAOUtility.releaseConnection(name,connection);
                } catch (java.sql.SQLException e) {
                    // Care!!
                    trace.logException(e);
                    throw e;
                }
            }
        } finally {
            trace.end();
        }
    }

    /**
     * Instances a java.util.Vector and adds objects from array
     *
     */
    public static Vector createVector(Object[] objects) {
        Vector vector = new java.util.Vector();
        if (objects != null) {
            for (int index = 0; index < objects.length; index++) {
                vector.add(objects[index]);
            }
        }
        return vector;
    }

    /**
     * Makes a call string from a name and a number of placeholders
     * Example:  if parameters are  "myProc" and 4 the returned String will be  "{ call myProc(?,?,?,?) }"
     *
     */
    public static String generateProcedureCallString(String procedureName, int numPlaceHolders) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("{ call ");
        stringBuffer.append(procedureName);
        stringBuffer.append(generatePlaceHolders(numPlaceHolders));
        stringBuffer.append(" }");
        return stringBuffer.toString();
    }

    /**
     * Makes a call string from a name and a number of placeholders
     * Example:  if parameters are  "myProc" and 4 the returned String will be  "{ call myProc(?,?,?,?) }"
     *
     */
    public static String generateProcedureCallString(String packageName, String procedureName, int numPlaceHolders) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("{ call ");
        if (packageName!=null)
        {
	        stringBuffer.append(packageName);
	        stringBuffer.append('.');
        }
        stringBuffer.append(procedureName);
        stringBuffer.append(generatePlaceHolders(numPlaceHolders));
        stringBuffer.append(" }");
        return stringBuffer.toString();
    }

    /**
     * Makes a call string from a name and a number of placeholders
     * Example:  if parameters are  "myFunc" and 4 the returned String will be  "{ ? = call myFunc(?,?,?,?) }"
     *
     */
    public static String generateFunctionCallString(String functionName, int numPlaceHolders) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("{ ? = call ");
        stringBuffer.append(functionName);
        stringBuffer.append(generatePlaceHolders(numPlaceHolders));
        stringBuffer.append(" }");
        return stringBuffer.toString();
    }

    /**
     * Makes a call string from a name and a number of placeholders
     * Example:  if parameters are  "myFunc" and 4 the returned String will be  "{ ? = call myFunc(?,?,?,?) }"
     *
     */
    public static String generateFunctionCallString(String packageName, String functionName, int numPlaceHolders) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("{ ? = call ");
        if (packageName!=null)
        {
	        stringBuffer.append(packageName);
	        stringBuffer.append('.');
        }
        stringBuffer.append(functionName);
        stringBuffer.append(generatePlaceHolders(numPlaceHolders));
        stringBuffer.append(" }");
        return stringBuffer.toString();
    }

    public static String generatePlaceHolders(int numPlaceHolders) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("(");
        for (int index = 0; index < numPlaceHolders; index++) {
            if (index < numPlaceHolders - 1) {
                stringBuffer.append("?,");
            } else {
                stringBuffer.append("?");
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public static java.util.Date fromTimestampToDate(java.sql.Timestamp timestamp) {
        if (timestamp != null) {
            return new java.util.Date(timestamp.getTime());
        } else {
            return null;
        }

    }

    public static java.util.Date fromTimestampToDate(ResultSet resultSet, String name) throws java.sql.SQLException {
        Timestamp timestamp = resultSet.getTimestamp(name);
        if (timestamp != null) {
            return new java.util.Date(timestamp.getTime());
        } else {
            return null;
        }

    }

    public static java.util.Date fromSQLDateToDate(java.sql.Date date) {
        if (date != null) {
            return new java.util.Date(date.getTime());
        } else {
            return null;
        }

    }

    public static java.sql.Timestamp fromDateToTimestamp(java.util.Date date) throws java.sql.SQLException {
        if (date != null) {
            return new java.sql.Timestamp(date.getTime());
        } else {
            return null;
        }
    }

    public static void setForeignKeyParameter(CallableStatement statement, int index, int objectId) throws java.sql.SQLException {
        if (objectId > 0) {
            statement.setInt(index, objectId);
        } else {
            //statement.setNull(index,oracle.jdbc.driver.OracleTypes.NUMBER);
            statement.setNull(index, java.sql.Types.INTEGER);
        }
    }

    
    public static void setDateParameter2(PreparedStatement statement, int index, java.util.Date date) throws java.sql.SQLException {
        if (date != null) {
            statement.setDate(index, DAOUtility.fromDateToSQLDate(date));
        } else {
            statement.setNull(index, java.sql.Types.DATE);
        }
    }
    
    private static java.sql.Date fromDateToSQLDate(java.util.Date date) {
		// TODO Auto-generated method stub
		return new java.sql.Date(date.getTime());
	}
    private static Calendar calendar = new GregorianCalendar();
    static 
    {
    	calendar.set(1753,1,1);
    }
    private static java.util.Date lowest = calendar.getTime(); 
	public static void setDateParameter(PreparedStatement statement, int index, java.util.Date date) throws java.sql.SQLException {
        if (date != null) {
        	if (date.before(lowest))
        	{
        		logger.error("Date is before 1753 " + date);
        		
        		date = calendar.getTime();
        		logger.error("Date is set to  1753 " + date);
        		
        	}
        	statement.setTimestamp(index, DAOUtility.fromDateToTimestamp(date));
        } else {
            statement.setNull(index, java.sql.Types.DATE);
        }
    }

    public static java.util.Date stringToDate(String strDate) throws java.text.ParseException {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        return formatter.parse(strDate);

    }

    public static void setStringToDateParameter(CallableStatement statement, int index, java.lang.String strDate) throws java.sql.SQLException, java.text.ParseException {

        if (strDate != null) {
            statement.setTimestamp(index, DAOUtility.fromDateToTimestamp(stringToDate(strDate)));
        } else {
            statement.setNull(index, java.sql.Types.DATE);
        }
    }

    /**
     * Dumps all properties and returns them in a string
     *
     * @param object the object to dump properties from
     * @return a string with name - value pairs
     * @roseuid 39D1EA9A010E
     */
    public static String toString(Object object) {

        StringBuffer stringBuffer = new StringBuffer(500);
        try {
            // Get info
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
            PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();
            // For all
            for (int i = 0; i < pd.length; i++) {
                // If its a propertydescriptor
                if (pd[i].getClass().equals(PropertyDescriptor.class)) {
                    // Get read method
                    Method method = pd[i].getReadMethod();
                    if (method != null) {
                        //Invoke
                        Object returnObject;
                        try {
                            returnObject = method.invoke(object, new Object[0]);
                        } catch (InvocationTargetException e) {
                            // Log.error(object.getClass().getName(),"Could not do toString",e);
                            returnObject = "<error: invocation>";
                        } catch (IllegalAccessException e) {
                            Log.logException(logger,object.getClass().getName(), "Could not do toString", e);
                            returnObject = "<error: access>";
                        }
                        // append name and value
                        stringBuffer.append(pd[i].getName() + ":" + (returnObject != null ? returnObject.toString() : "null"));
                        stringBuffer.append("\n");
                    }
                }
            }
        } catch (IntrospectionException e) {
            Log.logException(logger,object.getClass().getName(), "Could not do toString", e);

        }

        return stringBuffer.toString();
    }

    public static void registerCursorOutParameter(int index, CallableStatement statement) throws java.sql.SQLException {
    //statement.registerOutParameter(index,oracle.jdbc.driver.OracleTypes.CURSOR);
    }

    /**
     * Dumps column names on the Log
     *
     */
    public static void dumpColumns(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int count = resultSetMetaData.getColumnCount();
        for (int index = 1; index <= count; index++) {
            Log.logMessage(logger,"buildColumns", resultSetMetaData.getColumnName(index));
        }
    }

    private static boolean isVerbose() {
        return false;
    }

    public static int getNextSequence(String name,String sequenceName) throws SQLException, ClassNotFoundException {
        Connection connection = DAOUtility.getConnection(name);
        java.sql.Statement stmt = null;
        ResultSet results = null;
        int nextOid = 0;
        try {
            stmt = connection.createStatement();
            results = stmt.executeQuery("SELECT " + sequenceName + ".NEXTVAL from dual");
            while (results.next()) {
                nextOid = results.getInt(1);
            }
        } finally {
            DAOUtility.cleanUp(name,results, stmt, connection);
        }
        return nextOid;
    }

    public static Map createCarrierMap(Carrier[] carriers) {
        Map map = new HashMap();
        for (int index = 0; index < carriers.length; index++) {
            map.put(carriers[index].getKey(), carriers[index]);
        }
        return map;
    }

    public static String dateToString(ResultSet resultSet, String columnName) throws java.sql.SQLException {
        java.sql.Timestamp currentDate = resultSet.getTimestamp(columnName);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentDate);
        return dateString;
    }


    public static ModelException getModelException(Throwable exception) {

        Throwable e = exception;
        while (e != null && !(e instanceof ModelException) && (e instanceof RemoteException || e instanceof EJBException)) {
            if (e instanceof RemoteException) {
                RemoteException re = (RemoteException) e;
                e = (Exception) re.detail;
            }
            if (e instanceof EJBException) {
                EJBException ee = (EJBException) e;
                e = (Exception) ee.getCausedByException();
            }
        }
        if (!(e instanceof ModelException)) {
            e = null;
        }
        return (ModelException) e;

    }

    public static void dumpConnection(String name) throws java.sql.SQLException {
        Trace trace = Trace.startTrace1(logger,String.class, "dumpConnection", true);
        Connection c = null;
        try {
            c = getConnection(name);
            trace.logMessage("Autocommit " + c.getAutoCommit());
            trace.logMessage("Catalog " + c.getCatalog());
            trace.logMessage("TransactionIsolation " + c.getTransactionIsolation());
        } catch (Exception e) {
            releaseConnection(name,c);
        }
    }

    public static void commitSoFar(String name) throws java.sql.SQLException {
        Connection c = null;
        try {
            c = getConnection(name);
            c.commit();
        } catch (Exception e) {


            releaseConnection(name,c);
        }
    }

    public static int measureSize(Object object) throws java.io.IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        return bos.size();
    }

    public static String parseException(Throwable exception, String classAndMethod) {
        StringBuffer stringBuffer = new StringBuffer();
        if (exception != null) {
            Exception ne = null;
            ModelException pe = DAOUtility.getModelException(exception);
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
                ne = DAOUtility.getNestedException(exception);
            }
            if (ne != null) {
                stringBuffer.append("\nNested Message:" + ne.getMessage());
                StringWriter sw = new StringWriter();
                PrintWriter printWriter = new PrintWriter(sw);
                ne.printStackTrace(printWriter);
                stringBuffer.append("\nSTACKTRACE\n");
                stringBuffer.append(sw.toString());
                if (ne instanceof java.sql.SQLException) {
                    stringBuffer.append(DAOUtility.formatSQLException((java.sql.SQLException) ne));
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

    public static String convertDirectoryName(String dir) {
        Log.logMessage(logger,"convertDirectoryName", dir);
        if (File.separatorChar == '\\') {
            dir = dir.replace('/', '\\');
        } else {
            dir = dir.replace('\\', '/');
        }
        if (File.separatorChar == '\\' && dir.length() > 0 && dir.charAt(0) == '\\') {
            dir = "c:" + dir;
        }

        Log.logMessage(logger,"convertDirectoryName", dir);
        return dir;
    }

    /**
     * Parses a String and returns the parts as a String array
     *
     * @param line String to be parsed
     * @param delimeter character that identifies parts
     * @return Stringarray with parts
     */
    public static String[] parseLine(String line, char delimeter) {
        // An array for the different fields
        List list = new java.util.LinkedList();

        if (line != null) {
            // We will parse the line from the beginning
            int start = 0;
            int end = 0;

            int length = line.length();


            // Parse the line
            int aIndex = 0;
            for (int index = 0; index < length; index++) {
                // If we find a tab
                if (line.charAt(index) == delimeter) {
                    // Pick up the field
                    String subString = line.substring(start, index);
                    list.add(subString);
                    // And continue
                    start = index + 1;
                }
            }


            // Take care of the last one (the rest of the line)
            if (start < length) {
                list.add(line.substring(start));
            }
        }
        return (String[]) list.toArray(new String[0]);
    }

    public static void startThread(Runnable runnable) {
        Thread t = new Thread(runnable);
        t.start();
    }

    public static String getStatistics() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("***************** Statistics " + new java.util.Date().toString() + "**************************\n");
        stringBuffer.append(CarrierFactory.getStatistics());
        stringBuffer.append("********************************** Queues *********************************************************\n");
        //stringBuffer.append(ProdexQueue.getStatistics());
        stringBuffer.append("********************************** DAO ************************************************************\n");
        stringBuffer.append(FrameworkDAOImpl.getStatistics());
        stringBuffer.append("********************************** Sessions ***********************************************************\n");
        //stringBuffer.append(SessionFactory.getStatistics());
        stringBuffer.append("********************************** Memory **************************************************\n");
        stringBuffer.append(getMemoryStatus());
        stringBuffer.append("**********************************************************************************************\n");
        //stringBuffer.append("Not finalized session beans " + DefaultSessionBeanImpl.getCount() + "\n");
        stringBuffer.append("Not finalized Trace objects " + Trace.getCount() + "\n");
        stringBuffer.append("Not finalized Carrier objects " + Carrier.getCount() + "\n");
        stringBuffer.append("Not finalized KeyImpl objects " + KeyImpl.getCount() + "\n");
        //stringBuffer.append("Not finalized Container objects " + ejbeans.Container.getCount() + "\n");
        stringBuffer.append("Not finalized DAOImpl objects " + FrameworkDAOImpl.getCount() + "\n");
        // stringBuffer.append("Not finalized Sender objects " + Sender.getCount() + "\n");
        //stringBuffer.append("Not finalized ProdexQueue objects " + ProdexQueue.getCount() + "\n");
        stringBuffer.append("Not finalized ModelQuery objects " + ModelQuery.getCount() + "\n");
        //stringBuffer.append("Sent messages " + Sender.getSentMessages() + "\n");
        stringBuffer.append("Not finalized GenericCarrierService objects " + GenericCarrierServiceImpl.count() + "\n");
        
        stringBuffer.append("**********************************************************************************************\n");

        return stringBuffer.toString();
    }

    public static String getMemoryStatus() {
        return "Memory total\t" + format(Runtime.getRuntime().totalMemory()) + "\nfree\t\t" + format(Runtime.getRuntime().freeMemory()) + "\nUsed\t\t" + format(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "\n";
    }

    private static String format(long number) {
        int pos = 12;
        StringBuffer s = new StringBuffer();
        s.append(number);
        while (s.length() < pos) {
            s.insert(0, ' ');
        }
        return s.toString();
    }

    public static void afterCompletion(boolean commit) {
        Trace trace = Trace.startTrace1(logger,DAOUtility.class, "afterCompletion", false);
        try {
            if (commit) {
                trace.logMessage("Commiting transactionCache");
                // OK! Transaction committed
                // Transfer created, updated carriers to "Common chache"
                CarrierFactory.commitTransactionCache();

                trace.logMessage("Commiting messageCache");
            // Transfer messages to real message queue
            //ProdexQueue.commitMessageCache();
            } else {
                // OK! Transaction rolled back
                // Flush created, updated carriers to "Common chache"
                CarrierFactory.rollbackTransactionCache();
            // Flush messages DONT SEND THEM
            //ProdexQueue.rollbackMessageCache();
            }
        } finally {
            trace.logMessage("Clear updating...");
            // Clear update states
            CarrierFactory.clearIsUpdating();

            trace.logMessage("Flush readcache");
            // Flush cache with read carriers within this transacation
            FrameworkDAOImpl.flushReadCache();

            trace.logMessage("Flush log");
            // Flush log for this transaction
            Log.flushLog();

            trace.logMessage("Flush carrier service map..");
        // Flush cached services
        //SessionFactory.flushCarrierServiceMap();
        }

    }

    public static boolean isUnix() {
        return File.separatorChar == '/';
    }

    public static boolean isPc() {
        return File.separatorChar == '\\';
    }

    public static boolean isServer() {
        String serverType = System.getProperty("prodex.server_type");
        if (serverType == null || !serverType.equals("client")) {
            return true;
        } else {
            return false;
        }

    }

    public static void setStringOrNull(CallableStatement statement, int index, String value) throws SQLException {
        // TODO Auto-generated method stub
        if (ModelUtility.isEmpty(value) || value.equals("-1")) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, value);
        }
    }
    
    public static void setStringOrExcplicitNull(CallableStatement statement, int index, String value) throws SQLException {
        // TODO Auto-generated method stub
        if (value==null) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, value);
        }
    }
    
    public static void setWildcardStringOrNull(CallableStatement statement, int index, String value) throws SQLException {
    	if (ModelUtility.isEmpty(value))
    	{
    		statement.setNull(index++, Types.VARCHAR);
    	}
    	else
    		if (ModelUtility.isNotEmptyAndHasWildCard(value))
    		{
    			statement.setString(index++,DAOUtility.replaceWildcards(value));
    		}
    		else
    		{
    			statement.setString(index++,value);
    		}
    	
    }
    
    
    

    /**
     * 
     * Concatenates strings in a list and separates them with separator.
     * 
     * @param list
     * @param separator
     * @return
     */
    public static String concatenate(List list, String separator, String quote, String prefix, String suffix) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer(prefix);
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            sb.append(quote);
            sb.append(iter.next());
            sb.append(quote);
            if (iter.hasNext()) {
                sb.append(separator);
            }
        }
        sb.append(suffix);


        return sb.toString();
    }

    public static void prepareParameterTable(Connection connection,long timestamp, String[] leveransIdn, String[] valdaSokrubriker, String[] valdaBefattningar,String[] valdaInrattningar, String[] valdaKliniker) throws SQLException {
        PreparedStatement insert = connection.prepareStatement("insert into TB_TraffbildTemp (id,leveransobjektId,sokrubrikCode,befattningCode,inrattningCode,klinikCode) values (@@spid,?,?,?,?,?)");
        List<String> leveransIdList = new ArrayList<String>();
        List<String> sokrubrikCodesList = new ArrayList<String>();
        List<String> befattningCodesList = new ArrayList<String>();
        List<String> klinikCodesList = new ArrayList<String>();
        List<String> inrattningCodesList = new ArrayList<String>();

        if (leveransIdn != null) {
            leveransIdList.addAll(Arrays.asList(leveransIdn));
        }

        if (valdaSokrubriker != null) {
            sokrubrikCodesList.addAll(Arrays.asList(valdaSokrubriker));
        }

        if (valdaBefattningar != null) {
            befattningCodesList.addAll(Arrays.asList(valdaBefattningar));
        }
        if (valdaKliniker != null) {
            klinikCodesList.addAll(Arrays.asList(valdaKliniker));
        }
        if (valdaInrattningar != null) {
            inrattningCodesList.addAll(Arrays.asList(valdaInrattningar));
        }

        logger.info("LEVERANS:" + leveransIdList.toString());
        logger.info("SOKRUBRIK:" + sokrubrikCodesList.toString());
        logger.info("KLINIK:" +klinikCodesList.toString());
        logger.info("INRATTNING:" +inrattningCodesList.toString());

        String nextLeveransobjektId = !leveransIdList.isEmpty() ? leveransIdList.remove(0) : null;
        String nextSokrubrikCode = !sokrubrikCodesList.isEmpty() ? sokrubrikCodesList.remove(0) : null;
        String nextKlinikCode = !klinikCodesList.isEmpty() ? klinikCodesList.remove(0) : null;
        String nextInrattningCode = !inrattningCodesList.isEmpty() ? inrattningCodesList.remove(0) : null;
        String nextBefattningCode = !befattningCodesList.isEmpty() ? befattningCodesList.remove(0) : null;

        while (nextInrattningCode!=null || nextLeveransobjektId != null || nextSokrubrikCode != null || nextKlinikCode != null || nextBefattningCode != null) {
        	
            int index2 = 1;
            
            setStringOrNull(insert, index2++, nextLeveransobjektId);
            setStringOrNull(insert, index2++, nextSokrubrikCode);
            setStringOrNull(insert, index2++, nextBefattningCode);
            setStringOrNull(insert, index2++, nextInrattningCode);
            setStringOrNull(insert, index2++, nextKlinikCode);
            insert.execute();
            insert.clearParameters();
            nextLeveransobjektId = !leveransIdList.isEmpty() ? leveransIdList.remove(0) : null;
            nextSokrubrikCode = !sokrubrikCodesList.isEmpty() ? sokrubrikCodesList.remove(0) : null;
            nextInrattningCode = !inrattningCodesList.isEmpty() ? inrattningCodesList.remove(0) : null;
            nextKlinikCode = !klinikCodesList.isEmpty() ? klinikCodesList.remove(0) : null;
            nextBefattningCode = !befattningCodesList.isEmpty() ? befattningCodesList.remove(0) : null;

        }
    }
    
    public static void setStringOrNull(PreparedStatement statement, int index,
			String value) throws SQLException {
		// TODO Auto-generated method stub
		if (value == null) {
			statement.setNull(index, Types.VARCHAR);
		} else {
			statement.setString(index, value);
		}
	}

    public static void setIntOrNull(PreparedStatement statement,int index,Integer value) throws SQLException 
    {
        if (value == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value.intValue());
        }
    }
    
    public static void setIntFrom(PreparedStatement statement,int index,NumeriskPeriod value) throws SQLException 
    {
        logger.debug("DAOUtility.setIntTom(" + value + ")"); 
        if (value == null || value.getStart() == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value.getStart().intValue());
        }
    }
    
    public static void setIntTom(PreparedStatement statement,int index,NumeriskPeriod value) throws SQLException 
    {
        logger.debug("DAOUtility.setIntTom(" + value + ")"); 
        int i = statement.getParameterMetaData().getParameterCount();

        if (value == null || value.getEnd() == null) {
            statement.setNull(index, Types.INTEGER);
        } else {
            statement.setInt(index, value.getEnd().intValue());
        }
    }
	
	public static void setIntOrNullFk(PreparedStatement statement,int index,Integer value) throws SQLException 
	{
		if (value == null || value.intValue()<1) {
			statement.setNull(index, Types.INTEGER);
		} else {
			statement.setInt(index, value.intValue());
		}
	}
	
	public static void setIntOrNullFk(PreparedStatement statement,int index,int value) throws SQLException 
	{
		if (value <1) {
			statement.setNull(index, Types.INTEGER);
		} else {
			statement.setInt(index, value);
		}
	}

	public static void setShortOrNull(CallableStatement statement, int index,
			Short value) throws SQLException {
		if (value == null) {
			statement.setNull(index, Types.SMALLINT);
		} else {
			statement.setShort(index, value.shortValue());
		}
		
	}

	public static String replaceWildcards(String value) {
		// TODO Auto-generated method stub
		return value!=null?value.replace('*', '%').replaceAll("_", "*_").replace('?','_'):value;
	}

    public static void setNumericOrNull(CallableStatement statement, int index,
            Double value) throws SQLException 
    {
        if (ModelUtility.isEmpty(value))
        {
            statement.setNull(index,Types.NUMERIC);
        }
        else
        {
            statement.setDouble(index,value.doubleValue());
        }
        
    }

    public static void setNumericFrom(CallableStatement statement, int index,
            NumeriskPeriod value) throws SQLException 
    {
        if (value == null || value.getStart() == null)
        {
            statement.setNull(index,Types.NUMERIC);
        }
        else
        {
            statement.setDouble(index,value.getStart().doubleValue());
        }
        
    }

    public static void setNumericTom(CallableStatement statement, int index,
            NumeriskPeriod value) throws SQLException 
    {
        if (value == null || value.getEnd() == null)
        {
            statement.setNull(index,Types.NUMERIC);
        }
        else
        {
            statement.setDouble(index,value.getEnd().doubleValue());
        }
        
    }
	
	static class DumpStatistics implements Runnable
    {
        public void run()
        {
            while(true)
            {
                try
                {
                    Thread.sleep(10000);
                    logMessage("DumpStatistics",DAOUtility.getStatistics());
                    Thread.sleep(60*60*1000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    static public void startDump()
    {
        try
        {
            //if (!Configuration.getMandatoryProperty(Configuration.SERVER_TYPE_KEY).equals("testclient"))
            {
                //Utility.startThread(new DumpStatistics());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

	public static void logMessage(String string, String statistics) {
		
		
	}

	/**
	 * @param statement
	 * @param index
	 * @param registreringsdatumPeriod
	 * @throws SQLException 
	 */
	public static void setDateParameterFrom(CallableStatement statement,
			int index, Datumperiod datumPeriod) throws SQLException {
		if (datumPeriod!=null)
		{
			setDateParameter2(statement,index,datumPeriod.getStart());
		}
		else
		{
			setDateParameter2(statement,index,null);
		}
	}
	public static void setDateParameterTom(CallableStatement statement,
			int index, Datumperiod datumPeriod) throws SQLException {
		if (datumPeriod!=null)
		{
			setDateParameter2(statement,index,datumPeriod.getEnd());
		}
		else
		{
			setDateParameter2(statement,index,null);
		}
		
	}
	
}

