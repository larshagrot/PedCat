package se.pedcat.forum.web.jsfbean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import se.pedcat.forum.model.Arrangemang;
import se.pedcat.forum.model.Forening;
import se.pedcat.forum.model.Funktionar;
import se.pedcat.forum.model.Medlem;
import se.pedcat.forum.model.Medlem.MedlemKey;
import se.pedcat.forum.service.ArrangemangService;
import se.pedcat.forum.service.ArrangemangServiceImpl;
import se.pedcat.forum.service.FunktionarService;
import se.pedcat.forum.service.FunktionarServiceImpl;
import se.pedcat.forum.service.MedlemService;
import se.pedcat.forum.service.MedlemServiceImpl;
import se.pedcat.framework.common.util.CommonUtility;

/**
 * @author laha
 * 
 */
public class MedlemJSFBean extends SIKBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient MedlemService theMedlemService = null;

	private Medlem currentMedlem;
	private Forening currentForening;
	
	private String currentMedlemId;
	private String currentForeningId;
	private String currentFunktionarId;
	/**
	 *  Returnerar currentFunktionarId
	 *
	 * @return the currentFunktionarId
	 */
	public String getCurrentFunktionarId() {
		return this.currentFunktionarId;
	}

	/**
	 * Sätter currentFunktionarId
	 * @param currentFunktionarId the currentFunktionarId to set
	 */
	public void setCurrentFunktionarId(String currentFunktionarId) {
		this.currentFunktionarId = currentFunktionarId;
	}

	private String funktion;
	
	/**
	 *  Returnerar funktion
	 *
	 * @return the funktion
	 */
	public String getFunktion() {
		return this.funktion;
	}

	/**
	 * Sätter funktion
	 * @param funktion the funktion to set
	 */
	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	private String choosenArrangemangId;
	/**
	 *  Returnerar choosenArrangemangId
	 *
	 * @return the choosenArrangemangId
	 */
	public String getChoosenArrangemangId() {
		return this.choosenArrangemangId;
	}

	/**
	 * Sätter choosenArrangemangId
	 * @param choosenArrangemangId the choosenArrangemangId to set
	 */
	public void setChoosenArrangemangId(String choosenArrangemangId) {
		this.choosenArrangemangId = choosenArrangemangId;
	}

	private ArrangemangService theArrangemangService;
	private FunktionarService theFunktionarService;
	
	
	public String addFunktionar()
	{
		Funktionar funktionar = new Funktionar();
		
		funktionar.setMedlemId(this.currentMedlem.getObjectId());
		funktionar.setArrangemangId(this.choosenArrangemangId);
		funktionar.setFunktion(this.funktion);
		funktionar = this.getFunktionarService().create(funktionar);
		
		return "success";
	}
	
	public String updateFunktionar()
	{
		/*Funktionar funktionar = new Funktionar();
		 * 
		 *
		
		funktionar.setMedlemId(this.currentMedlem.getObjectId());
		funktionar.setArrangemangId(this.choosenArrangemangId);
		funktionar.setFunktion(this.funktion);
		funktionar = this.getFunktionarService().create(funktionar);
		*/
		return "success";
	}
	
	public String deleteFunktionar()
	{
		this.getFunktionarService().delete(new Funktionar.FunktionarKey(this.currentFunktionarId));
		return "success";
	}

	public Funktionar[] getFunktionarerLista()
	{
		List<Funktionar> lista = new ArrayList<Funktionar>();
		try
		{
		
		Funktionar.FunktionarQuery q = new Funktionar.FunktionarQuery();
		q.setMedlemId(this.currentMedlem.getObjectId());
		
		Funktionar[] f = this.getFunktionarService().findByQuery(q);
		for(Funktionar ff:f)
		{
			Arrangemang aa = this.getArrangemangService().findByKey(new Arrangemang.ArrangemangKey(ff.getArrangemangId()));
			ff.setArrangemang(aa);
			lista.add(ff);
		}
		}
		catch(Exception e)
		{
			this.addMessage(e);
		}
		return lista.toArray(new Funktionar[0]);
	}

	
	public List<SelectItem> getArrangemangsLista()
	{
		List<SelectItem> lista = new ArrayList<SelectItem>();
		Arrangemang[] a = this.getArrangemangService().findAll();
		for(Arrangemang aa:a)
		{
			lista.add(new SelectItem(aa.getObjectId(),aa.getDatumAsString() + " " + aa.getNamn()));
		}
		return lista;
	}
	
	
	/**
	 * Returnerar currentMedlem
	 * 
	 * @return the currentMedlem
	 */
	public Medlem getCurrentMedlem() {
		return this.currentMedlem;
	}

	/**
	 * Sätter currentMedlem
	 * 
	 * @param currentMedlem
	 *            the currentMedlem to set
	 */
	public void setCurrentMedlem(Medlem currentMedlem) {
		this.currentMedlem = currentMedlem;
	}

	
	
	
	public Medlem[] getMedlemmar() {
		Medlem[] medlemmar = new Medlem[0];
		try {
			medlemmar = this.getMedlemService().findAll();
			if (medlemmar!=null)
			{
				for(Medlem  medlem:medlemmar)
				{
					System.out.println(medlem.getClass().getName());
				}
				Arrays.sort(medlemmar);
			}
		} catch (Exception e) {
			addMessage(e);
		}
		return medlemmar;
	}

	public boolean isNew() {
		return this.currentMedlem != null
				&& this.currentMedlem.isNew();
	}

	public String cancel() {

		this.currentMedlem = null;
		return "success";
	}

	public String save() {
		try {
			
			
			this.currentMedlem = this.getMedlemService().save(
					this.currentMedlem);

		} catch (Exception e) {
			addMessage(e);
		}
		this.currentMedlem = null;
		return "success";
	}

	
	public String delete() {
		try {
			if (this.currentMedlemId != null) {
				this.getMedlemService().delete(
						new MedlemKey(this.currentMedlemId));
				this.currentMedlem = null;
				this.currentMedlemId = null;
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
		this.currentMedlem = new Medlem();
		return "success";
	}

	public String update() {
		try {
			System.out.println("OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK " + this.currentMedlemId);
			this.currentMedlem = this.getMedlemService().findByKey(
					new Medlem.MedlemKey(this.currentMedlemId));
			this.currentMedlemId = null;
			System.out.println("OKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOKOK " + this.currentMedlem!=null);
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

		System.err.println(e.getMessage());
		e.printStackTrace();
	}

	public boolean isHasMedlem() {
		return this.currentMedlem != null;
	}

	public MedlemService getMedlemService() {
		if (this.theMedlemService == null) {
			this.theMedlemService = new MedlemServiceImpl();
		}
		return this.theMedlemService;
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
	 *  Returnerar currentMedlemId
	 *
	 * @return the currentMedlemId
	 */
	public String getCurrentMedlemId() {
		return this.currentMedlemId;
	}

	/**
	 * Sätter currentMedlemId
	 * @param currentMedlemId the currentMedlemId to set
	 */
	public void setCurrentMedlemId(String currentMedlemId) {
		this.currentMedlemId = currentMedlemId;
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

	public ArrangemangService getArrangemangService() {
		if (this.theArrangemangService == null) {
			this.theArrangemangService = new ArrangemangServiceImpl();
		}
		return this.theArrangemangService;
	}

	public FunktionarService getFunktionarService() {
		if (this.theFunktionarService == null) {
			this.theFunktionarService = new FunktionarServiceImpl();
		}
		return this.theFunktionarService;
	}

}
