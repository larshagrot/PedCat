/*
 * MapCache.java
 *
 * Created on den 4 april 2002, 09:32
 */

package se.pedcat.framework.common.model.util;
import java.util.*;


// TODO: Auto-generated Javadoc
/**
 * The Class MapCache.
 *
 * @author  larh
 * @version
 */
public class MapCache extends TreadCache
{
    
    /**
     * Creates new MapCache.
     */
    public MapCache()
    {
    }
    
    /**
     * Gets the map.
     *
     * @param create the create
     * @return the map
     */
    public Map getMap(boolean create)
    {
        return  (Map)  super.get(create);
    }
    
    /* (non-Javadoc)
     * @see se.pedcat.framework.common.model.util.TreadCache#createNew()
     */
    public Object createNew()
    {
        return new HashMap();
    }
    
    
    
    
    /**
     * The Class MapCacheTest.
     */
    public static class MapCacheTest extends Thread
    {
        
        /** The map cache. */
        static MapCache mapCache = new MapCache();
        
        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        public void run()
        {
            while (true)
            {
                System.out.println(mapCache.getName());
                long time = System.currentTimeMillis();
                for(int j=0;j<100;j++)
                {
                    Map map = mapCache.getMap(true);
                    for(int i=0;i<100;i++)
                    {
                        map.put(""+i,"test" + i);
                    }
                    mapCache.release();
                    map = mapCache.getMap(false);
                    mapCache.release();
                }
                System.out.println(mapCache.getName() + " " + (System.currentTimeMillis()-time)/1000.);
                System.out.println(mapCache.free() + " " + mapCache.used());
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
            MapCacheTest[] m = new MapCacheTest[100];
            for(int i=0;i<m.length;i++)
            {
                m[i] = new MapCacheTest();
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
