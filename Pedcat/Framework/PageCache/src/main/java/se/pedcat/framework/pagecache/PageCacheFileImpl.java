/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.List;


/**
 * The Class PageCacheFileImpl.
 *
 * @param <KeyType> the generic type
 * @param <RowType> the generic type
 */
public class PageCacheFileImpl<KeyType,RowType> extends PageCacheDefaultImpl<KeyType,RowType> {

	
    /** The dos data. */
    private RandomAccessFile dosData = null;
    
    /** The dos index. */
    private RandomAccessFile dosIndex = null;
    
    /** The temp. */
    private File temp = null;
    
    /** The temp2. */
    private File temp2 = null;
    
    /** The antal sidor. */
    private int antalSidor = 0;
    
    /** The antal rader. */
    private int antalRader = 0;
    
    /** The delta. */
    private long delta = 0;
	
	/**
	 * Instantiates a new page cache file impl.
	 *
	 * @param rader the rader
	 */
	public PageCacheFileImpl(KeyType[] rader) {
		super();
	    try {
            temp = File.createTempFile("index",".dat");
            temp.deleteOnExit();
            dosIndex = new RandomAccessFile(temp.getAbsolutePath(),"rw");
            
            temp2 = File.createTempFile("data",".dat");
            temp2.deleteOnExit();
            dosData = new RandomAccessFile(temp2.getAbsolutePath(),"rw");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(temp.getAbsolutePath());
        this.antalRader = rader.length;
		paginate(rader);
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#getRader()
	 */
	@Override
	public KeyType[] getRader() {
		
	    return  null;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#getCurrentPageRader()
	 */
	@Override
	public List<KeyType> getCurrentPageRader() 
	{
	    try {
	        //System.out.println("Getting " + this.getCurrentPage());
	        dosIndex.seek((this.getCurrentPage()-1) *delta);
        
    	    int page = dosIndex.readInt();
    	    int antal = dosIndex.readInt();
    	    long start = dosIndex.readLong();
    	    long size = dosIndex.readLong();
    	    dosData.seek(start);
    	    byte[] buffer = new byte[(int)size];
    	    dosData.read(buffer);
    	    
    	    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer));
    	    return (List<KeyType>) ois.readObject();
	    } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return null;
	  
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#addPage(java.lang.Integer, java.util.List)
	 */
	@Override
	public void addPage(Integer page, List<KeyType> lista) 
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	    ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(lista);
            long start = dosData.getFilePointer();
            dosData.write(baos.toByteArray());
            long stop = dosData.getFilePointer();
            long index =  dosIndex.getFilePointer();
     
            dosIndex.writeInt(page);
            dosIndex.writeInt(lista.size());
            dosIndex.writeLong(start);
            dosIndex.writeLong(stop-start);
            this.delta = dosIndex.getFilePointer() - index;
            
            this.antalSidor++;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	}
	
	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCache#size()
	 */
	public int size()
	{
		return this.antalRader;
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#clearPagination()
	 */
	@Override
	public void clearPagination() {
		
	}

	/* (non-Javadoc)
	 * @see se.pedcat.framework.pagecache.PageCacheDefaultImpl#getAntalSidor()
	 */
	@Override
	public int getAntalSidor() {
	    return this.antalSidor;
	}

    /* (non-Javadoc)
     * @see se.pedcat.framework.pagecache.PageCache#clear()
     */
    @Override
    public void clear() {
        this.temp.delete();
        this.temp2.delete();
    }

	
}
