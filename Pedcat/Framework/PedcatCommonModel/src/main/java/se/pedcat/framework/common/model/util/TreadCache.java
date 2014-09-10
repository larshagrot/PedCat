/*
 * MapCache.java
 *
 * Created on den 4 april 2002, 09:32
 */

package se.pedcat.framework.common.model.util;
import java.util.*;


// TODO: Auto-generated Javadoc
/**
 * The Class TreadCache.
 *
 * @author  larh
 * @version
 */
abstract public class TreadCache
{
    
    /** The free list. */
    private List freeList = new ArrayList();
    
    /** The object map. */
    private Map  objectMap = new HashMap();
    
    /**
     * Creates new MapCache.
     */
    public TreadCache()
    {
    }
    
    /**
     * Flush.
     */
    public void  flush()
    {
        synchronized(freeList)
        {
            freeList.clear();
        }
    }
    
    /**
     * Gets the.
     *
     * @param create the create
     * @return the object
     */
    public Object get(boolean create)
    {
        Object object = null;
        String name = getName();
        synchronized(objectMap)
        {
            object =  objectMap.get(name);
        }
        
        if (object==null && create)
        {
            object = getNew();
            synchronized(objectMap)
            {
                objectMap.put(name,object);
            }
        }
        return object;
    }
    
    /**
     * Gets the new.
     *
     * @return the new
     */
    private Object getNew()
    {
        synchronized(freeList)
        {
            if (!freeList.isEmpty())
            {
                return freeList.remove(0);
            }
            else
            {
                return createNew();
            }
        }
    }
    
    /**
     * Creates the new.
     *
     * @return the object
     */
    abstract public Object createNew();
    
    /**
     * Release.
     */
    public void release()
    {
        Object object = null;
        synchronized(objectMap)
        {
            object =  objectMap.remove(getName());
        }
        if (object!=null)
        {
            try
            {
                java.lang.reflect.Method method = object.getClass().getMethod("clear",new Class[0]);
                method.invoke(object,new Object[0]);
                synchronized(freeList)
                {
                    freeList.add(object);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public static String getName()
    {
        return Thread.currentThread().getName();
    }
    
    /**
     * Free.
     *
     * @return the int
     */
    public int free()
    {
        return this.freeList.size();
    }
    
    /**
     * Used.
     *
     * @return the int
     */
    public int used()
    {
        return this.objectMap.size();
    }
    
    /**
     * Gets the statistics.
     *
     * @return the statistics
     */
    public String getStatistics()
    {
        StringBuffer sb=new StringBuffer();
        try
        {
            if (used()>0)
            {
                sb.append("OCCUPIED\n");
                Set set = this.objectMap.keySet();
                Iterator i = set.iterator();
                while (i.hasNext())
                {
                    String key = (String) i.next();
                    Object object = this.objectMap.get(key);
                    try
                    {
                        //java.lang.reflect.Method method = object.getClass().getMethod("size",Class<>null);
                        //sb.append("Thread " + key +  " " + method.invoke(object,new Object[0])+ "\n");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        sb.append("EXCEPTION  " + object.getClass().getName()+  " " + e.getMessage() + "\n");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sb.append("EXCEPTION " + e.getMessage());
        }
        return sb.toString();
    }
    
    
    
}
