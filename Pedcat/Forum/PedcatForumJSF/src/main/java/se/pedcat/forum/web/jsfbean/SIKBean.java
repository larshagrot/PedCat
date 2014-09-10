/**
 * 
 */
package se.pedcat.forum.web.jsfbean;

import javax.faces.context.FacesContext;

/**
 * @author laha
 *
 */
public class SIKBean {

	
	private static final String ADMIN = "admin";

	public boolean isAdmin()
	{
		return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(ADMIN);
	}
}
