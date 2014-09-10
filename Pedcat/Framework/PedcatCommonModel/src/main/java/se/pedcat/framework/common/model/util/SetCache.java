/*
 * SetCache.java
 *
 * Created on den 4 april 2002, 09:32
 */

package se.pedcat.framework.common.model.util;
import java.util.*;


// TODO: Auto-generated Javadoc
/**
 * The Class SetCache.
 *
 * @author  larh
 * @version
 */
public class SetCache extends TreadCache
{
    
    /**
     * Creates new SetCache.
     */
    public SetCache()
    {
    }
    
    /**
     * Gets the sets the.
     *
     * @param create the create
     * @return the sets the
     */
    public Set getSet(boolean create)
    {
        return  (Set)  super.get(create);
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.util.TreadCache#createNew()
     */
    public Object createNew()
    {
        return new HashSet();
    }
    
    
    
    
    /**
     * The Class SetCacheTest.
     */
    public static class SetCacheTest extends Thread
    {
        
        /** The set cache. */
        static SetCache setCache = new SetCache();
        
        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        public void run()
        {
            while (true)
            {
                //System.out.println(setCache.getName());
                long time = System.currentTimeMillis();
                for(int j=0;j<100;j++)
                {
                    Set set = setCache.getSet(true);
                    for(int i=0;i<100;i++)
                    {
                        set.add("test" + i);
                    }
                    setCache.release();
                    set = setCache.getSet(false);
                    setCache.release();
                }
                //System.out.println(setCache.getName() + " " + (System.currentTimeMillis()-time)/1000.);
                //System.out.println(setCache.free() + " " + setCache.used());
                synchronized(this)
                {
                    try
                    {
                        this.wait();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    
    
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
        try
        {
            SetCacheTest[] m = new SetCacheTest[100];
            for(int i=0;i<m.length;i++)
            {
                m[i] = new SetCacheTest();
                m[i].start();
            }
            
            while (true)
            {
                Thread.sleep(5000);
                for(int i=0;i<m.length;i++)
                {
                    synchronized(m[i])
                    {
                        m[i].notify();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
}
