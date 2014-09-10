/**
 * 
 */
package se.pedcat.forum.model;

import se.pedcat.forum.model.ForumMessage.ForumMessageKey;
import se.pedcat.framework.common.model.GenericCarrier;
import se.pedcat.framework.common.model.Key;
import se.pedcat.framework.common.model.KeyImpl;
import se.pedcat.framework.common.model.ModelQuery;



/**
 * @author laha
 *
 */
public class Medlem extends GenericCarrier {

	public static class MedlemKey extends KeyImpl{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MedlemKey(String objectId) {
			super(objectId);
			// TODO Auto-generated constructor stub
		}
		
	}
	public static class MedlemQuery extends ModelQuery
	{

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime
					* result
					+ ((this.medlemsnummer == null) ? 0 : this.medlemsnummer
							.hashCode());
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			MedlemQuery other = (MedlemQuery) obj;
			if (this.medlemsnummer == null) {
				if (other.medlemsnummer != null) {
					return false;
				}
			} else if (!this.medlemsnummer.equals(other.medlemsnummer)) {
				return false;
			}
			return true;
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String medlemsnummer;
		/**
		 *  Returnerar medlemsnummer
		 *
		 * @return the medlemsnummer
		 */
		public String getMedlemsnummer() {
			return this.medlemsnummer;
		}
		/**
		 * Sätter medlemsnummer
		 * @param medlemsnummer the medlemsnummer to set
		 */
		public void setMedlemsnummer(String medlemsnummer) {
			this.medlemsnummer = medlemsnummer;
		}
		
	}
	
	public Key getKey()
	{
		return new MedlemKey(this.getObjectId());
	}

	
	private String fornamn;
	private String efternamn;
	private String personnummer;
	private String medlemsnummer;
	private String epost;
	private String telefonnummer;
	private String mobilnummer;
	private String adress;
	private String postnummer;
	private String ort;
	
	private String foreningId;
	
	
	/**
	 *  Returnerar personnummer
	 *
	 * @return the personnummer
	 */
	public String getPersonnummer() {
		return this.personnummer;
	}
	/**
	 * Sätter personnummer
	 * @param personnummer the personnummer to set
	 */
	public void setPersonnummer(String personnummer) {
		this.personnummer = personnummer;
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
	 *  Returnerar fornamn
	 *
	 * @return the fornamn
	 */
	public String getFornamn() {
		return this.fornamn;
	}
	/**
	 * Sätter fornamn
	 * @param fornamn the fornamn to set
	 */
	public void setFornamn(String fornamn) {
		this.fornamn = fornamn;
	}
	/**
	 *  Returnerar efternamn
	 *
	 * @return the efternamn
	 */
	public String getEfternamn() {
		return this.efternamn;
	}
	/**
	 * Sätter efternamn
	 * @param efternamn the efternamn to set
	 */
	public void setEfternamn(String efternamn) {
		this.efternamn = efternamn;
	}
	/**
	 *  Returnerar medlemsnummer
	 *
	 * @return the medlemsnummer
	 */
	public String getMedlemsnummer() {
		return this.medlemsnummer;
	}
	/**
	 * Sätter medlemsnummer
	 * @param medlemsnummer the medlemsnummer to set
	 */
	public void setMedlemsnummer(String medlemsnummer) {
		this.medlemsnummer = medlemsnummer;
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
	 *  Returnerar telefonnummer
	 *
	 * @return the telefonnummer
	 */
	public String getTelefonnummer() {
		return this.telefonnummer;
	}
	/**
	 * Sätter telefonnummer
	 * @param telefonnummer the telefonnummer to set
	 */
	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	/**
	 *  Returnerar adress
	 *
	 * @return the adress
	 */
	public String getAdress() {
		return this.adress;
	}
	/**
	 * Sätter adress
	 * @param adress the adress to set
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}
	/**
	 *  Returnerar postnummer
	 *
	 * @return the postnummer
	 */
	public String getPostnummer() {
		return this.postnummer;
	}
	/**
	 * Sätter postnummer
	 * @param postnummer the postnummer to set
	 */
	public void setPostnummer(String postnummer) {
		this.postnummer = postnummer;
	}
	/**
	 *  Returnerar ort
	 *
	 * @return the ort
	 */
	public String getOrt() {
		return this.ort;
	}
	/**
	 * Sätter ort
	 * @param ort the ort to set
	 */
	public void setOrt(String ort) {
		this.ort = ort;
	}
	/**
	 *  Returnerar mobilnummer
	 *
	 * @return the mobilnummer
	 */
	public String getMobilnummer() {
		return this.mobilnummer;
	}
	/**
	 * Sätter mobilnummer
	 * @param mobilnummer the mobilnummer to set
	 */
	public void setMobilnummer(String mobilnummer) {
		this.mobilnummer = mobilnummer;
	}
	
}
