/**
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The Class PageListinfo.
 *
 * @param <KeyType> the generic type
 * @param <RowType> the generic type
 * @author laha
 */
public class PageListinfo<KeyType,RowType> implements java.io.Serializable{
    
    
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The antal rader per sida. */
    private int antalRaderPerSida = 30;
    
    /** The current page. */
    private int currentPage = 1;
    
    /** The antal sidor som visas. */
    private int antalSidorSomVisas = 9;
    
    /** The antal sidor. */
    private int antalSidor;
    
    /** The antal rader. */
    private int antalRader;
    
    /** The antal poster. */
    private int antalPoster;
    
    /** The objektbeteckning. */
    private String objektbeteckning;
    
    /** The array. */
    private RowType[] array;
    
    /** The page cache ticket. */
    private PageCacheTicket pageCacheTicket; 
    
    /**
     * Gets the antal rader per sida.
     *
     * @return the antalRaderPerSida
     */
    public int getAntalRaderPerSida() {
        return antalRaderPerSida;
    }
    
    /**
     * Sets the antal rader per sida.
     *
     * @param antalRaderPerSida the antalRaderPerSida to set
     */
    public void setAntalRaderPerSida(int antalRaderPerSida) {
        this.antalRaderPerSida = antalRaderPerSida;
    }
    
    /**
     * Gets the current page.
     *
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }
    
    /**
     * Sets the current page.
     *
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    /**
     * Gets the antal sidor som visas.
     *
     * @return the antalSidorSomVisas
     */
    public int getAntalSidorSomVisas() {
        return antalSidorSomVisas;
    }
    
    /**
     * Sets the antal sidor som visas.
     *
     * @param antalSidorSomVisas the antalSidorSomVisas to set
     */
    public void setAntalSidorSomVisas(int antalSidorSomVisas) {
        this.antalSidorSomVisas = antalSidorSomVisas;
    }
    
    /**
     * Gets the antal sidor.
     *
     * @return the antalSidor
     */
    public int getAntalSidor() {
        return antalSidor;
    }
    
    /**
     * Sets the antal sidor.
     *
     * @param antalSidor the antalSidor to set
     */
    public void setAntalSidor(int antalSidor) {
        this.antalSidor = antalSidor;
    }
    
    /**
     * Gets the array.
     *
     * @return the array
     */
    public RowType[] getArray() {
        return array;
    }
    
    /**
     * Sets the array.
     *
     * @param array the array to set
     */
    public void setArray(RowType[] array) {
        this.array = array;
    }

	/**
	 * Returnerar pageCacheTicket.
	 *
	 * @return the pageCacheTicket
	 */
	public PageCacheTicket getPageCacheTicket() {
		return this.pageCacheTicket;
	}

	/**
	 * Sätter pageCacheTicket.
	 *
	 * @param pageCacheTicket the pageCacheTicket to set
	 */
	public void setPageCacheTicket(PageCacheTicket pageCacheTicket) {
		this.pageCacheTicket = pageCacheTicket;
	}

	/**
	 * Returnerar antalRader.
	 *
	 * @return the antalRader
	 */
	public int getAntalRader() {
		return this.antalRader;
	}

	/**
	 * Sätter antalRader.
	 *
	 * @param antalRader the antalRader to set
	 */
	public void setAntalRader(int antalRader) {
		this.antalRader = antalRader;
	}

	/**
	 * Gets the first navigate page.
	 *
	 * @return the first navigate page
	 */
	public int getFirstNavigatePage() {
		// TODO Auto-generated method stub
		int first = (this.antalSidorSomVisas/2) < this.getCurrentPage()? this.getCurrentPage() - (this.antalSidorSomVisas/2):1;
		return first;
	}

	/**
	 * Gets the sidnummer.
	 *
	 * @return the sidnummer
	 */
	public Integer[] getSidnummer() {
		List<Integer> list =  new ArrayList<Integer>();
		for(int i=1;i<=this.antalSidor;i++)
		{
			list.add(new Integer(i));
		}
		return list.toArray(new Integer[0]);
	}
    
	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix()
	{
		return this.getAntalSidor()<=this.antalSidorSomVisas || (this.getCurrentPage()-this.antalSidorSomVisas/2)<=0?"":"...";
	}
	
	/**
	 * Gets the suffix.
	 *
	 * @return the suffix
	 */
	public String getSuffix()
	{
		return this.getAntalSidor()<=this.antalSidorSomVisas || (this.getCurrentPage()+this.antalSidorSomVisas/2)>=this.getAntalSidor()?"":"...";
	}
	
	
	/**
	 * Gets the last navigate page.
	 *
	 * @return the last navigate page
	 */
	public int getLastNavigatePage()
	{
		int last =  this.getCurrentPage() + (this.antalSidorSomVisas/2) < this.getAntalSidor()? this.getCurrentPage() +(this.antalSidorSomVisas/2):this.getAntalSidor();
		return last;
	}
	
	/**
	 * Gets the first page index.
	 *
	 * @return the first page index
	 */
	public int getFirstPageIndex() {
		// TODO Auto-generated method stub
		return (this.getCurrentPage() - 1) * this.antalRaderPerSida + 1;
	}
	
	/**
	 * Gets the last page index.
	 *
	 * @return the last page index
	 */
	public int getLastPageIndex() {
		// TODO Auto-generated method stub
		int last =  (this.getCurrentPage() - 1) * this.antalRaderPerSida + this.antalRaderPerSida;
		if (last>this.getAntalPoster())
		{
			last =  this.getAntalPoster(); 
		}
		return last;
	}

	/**
	 * Returnerar antalPoster.
	 *
	 * @return the antalPoster
	 */
	public int getAntalPoster() {
		return this.antalPoster;
	}

	/**
	 * Sätter antalPoster.
	 *
	 * @param antalPoster the antalPoster to set
	 */
	public void setAntalPoster(int antalPoster) {
		this.antalPoster = antalPoster;
	}

	/**
	 * Returnerar objektbeteckning.
	 *
	 * @return the objektbeteckning
	 */
	public String getObjektbeteckning() {
		return this.objektbeteckning;
	}

	/**
	 * Sätter objektbeteckning.
	 *
	 * @param objektbeteckning the objektbeteckning to set
	 */
	public void setObjektbeteckning(String objektbeteckning) {
		this.objektbeteckning = objektbeteckning;
	}
	
	/**
	 * Gets the middle sidnummer.
	 *
	 * @return the middle sidnummer
	 */
	public Integer[] getMiddleSidnummer()
	{
		if (this.getAntalSidor()<=this.antalSidorSomVisas )
		{
			List<Integer> list = new ArrayList<Integer>();
			for(int i=1;i<=this.getAntalSidor();i++)
			{
				list.add(i);
			}
			
			return list.toArray(new Integer[0]);
		}
		else
		{
			List<Integer> sidnummer = new ArrayList<Integer>();
			int left  = (this.getCurrentPage() - (this.antalSidorSomVisas/2));
			int right = this.getCurrentPage() + (this.antalSidorSomVisas/2);
			if ((right-left)<this.antalSidorSomVisas && (right-left)< this.getAntalSidor())
			{
				if (this.getCurrentPage()-(this.antalSidorSomVisas/2)<0)
				{
					right += -( this.getCurrentPage()-(this.antalSidorSomVisas/2)) + 1;
				}
				if (this.getCurrentPage()+(this.antalSidorSomVisas/2)>this.getAntalSidor())
				{
					left += -( this.getCurrentPage()+(this.antalSidorSomVisas/2)-this.getAntalSidor());
				}
			}
				
			if (right>this.getAntalSidor())
			{
				right = this.getAntalSidor();
			}
			if (left<1)
			{
				left =1;
			}
			
			for(int i=1;i<=this.getAntalSidor();i++)
			{
				if ( i >= left &&  i  <= right)
				{
					sidnummer.add(i);
				}
			}
			
			return sidnummer.toArray(new Integer[0]);
		}
	}
	
	/**
	 * Gets the current page rader.
	 *
	 * @return the current page rader
	 */
	public List<RowType> getCurrentPageRader()
	{
		return Arrays.asList(this.getArray());
	}


}
