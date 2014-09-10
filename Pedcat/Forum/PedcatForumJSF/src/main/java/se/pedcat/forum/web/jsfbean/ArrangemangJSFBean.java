package se.pedcat.forum.web.jsfbean;

import java.util.Arrays;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import se.pedcat.forum.model.Arrangemang;
import se.pedcat.forum.model.Forening;
import se.pedcat.forum.model.Arrangemang.ArrangemangKey;
import se.pedcat.forum.service.ArrangemangService;
import se.pedcat.forum.service.ArrangemangServiceImpl;

/**
 * @author laha
 * 
 */
public class ArrangemangJSFBean  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient ArrangemangService theArrangemangService = null;

	private Arrangemang currentArrangemang;
	private Forening currentForening;
	
	private String currentArrangemangId;
	private String currentForeningId;
	
	
	/**
	 * Returnerar currentArrangemang
	 * 
	 * @return the currentArrangemang
	 */
	public Arrangemang getCurrentArrangemang() {
		return this.currentArrangemang;
	}

	/**
	 * Sätter currentArrangemang
	 * 
	 * @param currentArrangemang
	 *            the currentArrangemang to set
	 */
	public void setCurrentArrangemang(Arrangemang currentArrangemang) {
		this.currentArrangemang = currentArrangemang;
	}

	
	
	
	public Arrangemang[] getArrangemang() {
		Arrangemang[] messages = new Arrangemang[0];
		try {
			messages = this.getArrangemangService().findAll();
			Arrays.sort(messages);
		} catch (Exception e) {
			addMessage(e);
		}
		return messages;
	}

	public boolean isNew() {
		return this.currentArrangemang != null
				&& this.currentArrangemang.isNew();
	}

	public String cancel() {

		this.currentArrangemang = null;
		return "success";
	}

	public String save() {
		try {
			
			
			this.currentArrangemang = this.getArrangemangService().save(
					this.currentArrangemang);

		} catch (Exception e) {
			addMessage(e);
		}
		this.currentArrangemang = null;
		return "success";
	}

	
	public String delete() {
		try {
			if (this.currentArrangemangId != null) {
				this.getArrangemangService().delete(
						new ArrangemangKey(this.currentArrangemangId));
				this.currentArrangemang = null;
				this.currentArrangemangId = null;
			}
		} catch (Exception e) {
			addMessage(e);
		}
		return "success";
	}

	/**
	 * @return
	 */
	public String getIp() {

		Object o = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (o instanceof HttpServletRequest) {
			return ((HttpServletRequest) o).getRemoteAddr();
		} else {
			return "";
		}

	}

	public String nytt() {
		this.currentArrangemang = new Arrangemang();
		return "success";
	}

	public String update() {
		try {
			System.out.println("OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK " + this.currentArrangemangId);
			this.currentArrangemang = this.getArrangemangService().findByKey(
					new Arrangemang.ArrangemangKey(this.currentArrangemangId));
			this.currentArrangemangId = null;
			System.out.println("OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK " + this.currentArrangemang!=null);
		} catch (Exception e) {
			e.printStackTrace();
			
			addMessage(e);
		}
		return "success";
	}

	/**
	 * @param e
	 */
	private void addMessage(Exception e) {
		// TODO Auto-generated method stub

	}

	public boolean isHasArrangemang() {
		return this.currentArrangemang != null;
	}

	public ArrangemangService getArrangemangService() {
		if (this.theArrangemangService == null) {
			this.theArrangemangService = new ArrangemangServiceImpl();
		}
		return this.theArrangemangService;
	}
	

	
	/**
	 *  Returnerar currentForening
	 *
	 * @return the currentForening
	 */
	public Forening getCurrentForening() {
		return this.currentForening;
	}

	/**
	 * Sätter currentForening
	 * @param currentForening the currentForening to set
	 */
	public void setCurrentForening(Forening currentForening) {
		this.currentForening = currentForening;
	}

	/**
	 *  Returnerar currentArrangemangId
	 *
	 * @return the currentArrangemangId
	 */
	public String getCurrentArrangemangId() {
		return this.currentArrangemangId;
	}

	/**
	 * Sätter currentArrangemangId
	 * @param currentArrangemangId the currentArrangemangId to set
	 */
	public void setCurrentArrangemangId(String currentArrangemangId) {
		this.currentArrangemangId = currentArrangemangId;
	}

	/**
	 *  Returnerar currentForeningId
	 *
	 * @return the currentForeningId
	 */
	public String getCurrentForeningId() {
		return this.currentForeningId;
	}

	/**
	 * Sätter currentForeningId
	 * @param currentForeningId the currentForeningId to set
	 */
	public void setCurrentForeningId(String currentForeningId) {
		this.currentForeningId = currentForeningId;
	}

	/**
	 * @return
	 */
	public String getUserId() {

		Object o = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (o instanceof HttpServletRequest) {
			return ((HttpServletRequest) o).getRemoteUser();
		} else {
			return "";
		}

	}

	
	public boolean isLoggedIn()
	{
		return true;
		
	}
	
	
}
