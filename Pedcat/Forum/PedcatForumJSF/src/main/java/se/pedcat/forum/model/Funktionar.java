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
public class Funktionar extends GenericCarrier 
{
	public static class FunktionarKey extends KeyImpl{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FunktionarKey(String objectId) {
			super(objectId);
			// TODO Auto-generated constructor stub
		}
		
	}
	public static class FunktionarQuery extends ModelQuery
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
					+ ((this.arrangemangId == null) ? 0 : this.arrangemangId
							.hashCode());
			result = prime * result
					+ ((this.medlemId == null) ? 0 : this.medlemId.hashCode());
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
			FunktionarQuery other = (FunktionarQuery) obj;
			if (this.arrangemangId == null) {
				if (other.arrangemangId != null) {
					return false;
				}
			} else if (!this.arrangemangId.equals(other.arrangemangId)) {
				return false;
			}
			if (this.medlemId == null) {
				if (other.medlemId != null) {
					return false;
				}
			} else if (!this.medlemId.equals(other.medlemId)) {
				return false;
			}
			return true;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String medlemId;
		private String arrangemangId;

		/**
		 *  Returnerar arrangemangId
		 *
		 * @return the arrangemangId
		 */
		public String getArrangemangId() {
			return this.arrangemangId;
		}

		/**
		 * Sätter arrangemangId
		 * @param arrangemangId the arrangemangId to set
		 */
		public void setArrangemangId(String arrangemangId) {
			this.arrangemangId = arrangemangId;
		}

		/**
		 *  Returnerar medlemId
		 *
		 * @return the medlemId
		 */
		public String getMedlemId() {
			return this.medlemId;
		}

		/**
		 * Sätter medlemId
		 * @param medlemId the medlemId to set
		 */
		public void setMedlemId(String medlemId) {
			this.medlemId = medlemId;
		} 
		
	}
	
	public Key getKey()
	{
		return new FunktionarKey(this.getObjectId());
	}

	
	private String medlemId;
	private Medlem  medlem;
	private String arrangemangId;
	private Arrangemang  arrangemang;
	private String  funktion;
	/**
	 *  Returnerar medlemId
	 *
	 * @return the medlemId
	 */
	public String getMedlemId() {
		return this.medlemId;
	}
	/**
	 * Sätter medlemId
	 * @param medlemId the medlemId to set
	 */
	public void setMedlemId(String medlemId) {
		this.medlemId = medlemId;
	}
	/**
	 *  Returnerar medlem
	 *
	 * @return the medlem
	 */
	public Medlem getMedlem() {
		return this.medlem;
	}
	/**
	 * Sätter medlem
	 * @param medlem the medlem to set
	 */
	public void setMedlem(Medlem medlem) {
		this.medlem = medlem;
	}
	/**
	 *  Returnerar arrangemangId
	 *
	 * @return the arrangemangId
	 */
	public String getArrangemangId() {
		return this.arrangemangId;
	}
	/**
	 * Sätter arrangemangId
	 * @param arrangemangId the arrangemangId to set
	 */
	public void setArrangemangId(String arrangemangId) {
		this.arrangemangId = arrangemangId;
	}
	/**
	 *  Returnerar arrangemang
	 *
	 * @return the arrangemang
	 */
	public Arrangemang getArrangemang() {
		return this.arrangemang;
	}
	/**
	 * Sätter arrangemang
	 * @param arrangemang the arrangemang to set
	 */
	public void setArrangemang(Arrangemang arrangemang) {
		this.arrangemang = arrangemang;
	}
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
}
