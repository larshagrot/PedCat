/**
 * 
 */
package se.pedcat.forum.model;

import java.util.Date;

import se.pedcat.forum.model.ForumMessage.ForumMessageKey;
import se.pedcat.framework.common.model.GenericCarrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;
import se.pedcat.framework.common.model.ModelUtility;


/**
 * @author laha
 *
 */
public class Arrangemang extends GenericCarrier  implements Comparable<Arrangemang>
{
	public static class ArrangemangKey extends KeyImpl{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ArrangemangKey(String objectId) {
			super(objectId);
			// TODO Auto-generated constructor stub
		}
		
	}
	public static class ArrangemangQuery extends ModelQuery
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	}
	
	public Key getKey()
	{
		return new ArrangemangKey(this.getObjectId());
	}

	
	private String namn;
	private String beskrivning;
	private String kontaktperson;
	private String telefon;
	private String epost;
	private String anmalan;
	private String plats;
	private Date start;
	private Date datum;
	private Date senast;
	
	/**
	 *  Returnerar startAsString
	 *
	 * @return the startAsString
	 */
	public String getStartAsString() {
		return ModelUtility.fromDate2String(this.start);
	}




	/**
	 * Sätter startAsString
	 * @param startAsString the startAsString to set
	 */
	public void setStartAsString(String startAsString) {
		this.start = ModelUtility.fromString2Date(startAsString);
	}




	/**
	 *  Returnerar datumAsString
	 *
	 * @return the datumAsString
	 */
	public String getDatumAsString() {
		return ModelUtility.fromDate2String(this.datum);
	}




	/**
	 * Sätter datumAsString
	 * @param datumAsString the datumAsString to set
	 */
	public void setDatumAsString(String datumAsString) {
		this.datum = ModelUtility.fromString2Date(datumAsString);
	}




	/**
	 *  Returnerar senastAsString
	 *
	 * @return the senastAsString
	 */
	public String getSenastAsString() {
		return ModelUtility.fromDate2String(this.senast);
	}




	/**
	 * Sätter senastAsString
	 * @param senastAsString the senastAsString to set
	 */
	public void setSenastAsString(String senastAsString) {
		this.start = ModelUtility.fromString2Date(senastAsString);
	}

	
	private String url;
	private String foreningId;
		
	
	/**
	 *  Returnerar start
	 *
	 * @return the start
	 */
	public Date getStart() {
		return this.start;
	}


	
	
	/**
	 *  Returnerar datum
	 *
	 * @return the datum
	 */
	public Date getDatum() {
		return this.datum;
	}
	/**
	 * Sätter datum
	 * @param datum the datum to set
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	/**
	 *  Returnerar senast
	 *
	 * @return the senast
	 */
	public Date getSenast() {
		return this.senast;
	}
	/**
	 * Sätter senast
	 * @param senast the senast to set
	 */
	public void setSenast(Date senast) {
		this.senast = senast;
	}
	/**
	 * Sätter start
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}
	
	
	
	
	/**
	 *  Returnerar beskrivning
	 *
	 * @return the beskrivning
	 */
	public String getBeskrivning() {
		return this.beskrivning;
	}
	/**
	 * Sätter beskrivning
	 * @param beskrivning the beskrivning to set
	 */
	public void setBeskrivning(String beskrivning) {
		this.beskrivning = beskrivning;
	}
	/**
	 *  Returnerar foreningId
	 *
	 * @return the foreningId
	 */
	public String getForeningId() {
		return this.foreningId;
	}
	/**
	 * Sätter foreningId
	 * @param foreningId the foreningId to set
	 */
	public void setForeningId(String foreningId) {
		this.foreningId = foreningId;
	}
	/**
	 *  Returnerar namn
	 *
	 * @return the namn
	 */
	public String getNamn() {
		return this.namn;
	}
	/**
	 * Sätter namn
	 * @param namn the namn to set
	 */
	public void setNamn(String namn) {
		this.namn = namn;
	}
	/**
	 *  Returnerar kontaktperson
	 *
	 * @return the kontaktperson
	 */
	public String getKontaktperson() {
		return this.kontaktperson;
	}
	/**
	 * Sätter kontaktperson
	 * @param kontaktperson the kontaktperson to set
	 */
	public void setKontaktperson(String kontaktperson) {
		this.kontaktperson = kontaktperson;
	}
	/**
	 *  Returnerar telefon
	 *
	 * @return the telefon
	 */
	public String getTelefon() {
		return this.telefon;
	}
	/**
	 * Sätter telefon
	 * @param telefon the telefon to set
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	/**
	 *  Returnerar epost
	 *
	 * @return the epost
	 */
	public String getEpost() {
		return this.epost;
	}
	/**
	 * Sätter epost
	 * @param epost the epost to set
	 */
	public void setEpost(String epost) {
		this.epost = epost;
	}
	/**
	 *  Returnerar anmalan
	 *
	 * @return the anmalan
	 */
	public String getAnmalan() {
		return this.anmalan;
	}
	/**
	 * Sätter anmalan
	 * @param anmalan the anmalan to set
	 */
	public void setAnmalan(String anmalan) {
		this.anmalan = anmalan;
	}
	/**
	 *  Returnerar plats
	 *
	 * @return the plats
	 */
	public String getPlats() {
		return this.plats;
	}
	/**
	 * Sätter plats
	 * @param plats the plats to set
	 */
	public void setPlats(String plats) {
		this.plats = plats;
	}
	/**
	 *  Returnerar url
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}
	/**
	 * Sätter url
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}




	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Arrangemang o) {
		try
		{
			return this.getDatum().compareTo(o.getDatum());
		}
		catch (Exception e)
		{
			return 0;
		}
	
	}
	
}
