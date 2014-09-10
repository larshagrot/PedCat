/**
 * 
 */
package se.pedcat.forum.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author laha
 *
 */
public class AddUser {

	
	public String login()
	{
		String id = "";
		try {
			URL url = new URL("http://www.sikskidor.se/jahia/administration?do=processlogin&login_username=root&login_password=sikskidor");
			
			HttpURLConnection c;
		
			c = (HttpURLConnection) url.openConnection();

			c.setDoOutput(false);
			c.setDoInput(true);
			
			c.connect();
			
			Map<String,List<String>> map = c.getHeaderFields();
			
			for(String key:map.keySet())
			{
				System.out.print(key);
				for(String value:map.get(key))
				{
					if (value.contains("JSESSIONID"))
					{
						id =  value.substring(value.indexOf("=")+1,value.indexOf(";"));
					}
					System.out.println(":" + value);
				}
				System.out.println();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id);
		return id;
		
	}
	
	public void addUser(String id,String userName,String fornamn,String efternamn,String pwd,String email)
	{
		
		try {
			URL url = new URL("http://www.sikskidor.se/jahia/administration;jsessionid="+ id +"?do=users&sub=processCreate&username="+userName+"&manage-user-property#firstname="+ fornamn +"&manage-user-property#lastname="+ efternamn +"&manage-user-property#email="+ email+ "&passwd="+ userName + "&passwdconfirm="+ userName + "&actionType=save&manage-user-property#preferredLanguage=engelska");
			
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			
			c.setDoOutput(false);
			c.setDoInput(true);
			
			c.connect();
			
			InputStream is = c.getInputStream();
			byte[] buffer = new byte[2000];
			int n = is.read(buffer);
			while (n>0)
			{
				System.out.println(new String(buffer,0,n));
				n = is.read(buffer);
			}
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args)
	{
		//String id = new AddUser().login();
		new AddUser().addUser("6B4EDFEE254E6D5B75B3A96C3750EECD", "test55", "fornamn", "efternamn", "testarpwd","lars.hagrot@signifikant.se");
		
	}
}
