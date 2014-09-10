package se.pedcat.framework.storage.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class AntIsqlTask extends Task 
{
	private List<Inputfile> inputfilesList = new ArrayList<Inputfile>();
	private String user;
	private String password;
	private String database;
	private String server;
	private String inputfiles;
	private String dir;
	
	public void init() throws BuildException
	{
		super.init();
		
	}
	
	public void initFileList()
	{
		if (this.inputfiles!=null)
		{
			try {
				BufferedReader br = new BufferedReader(new FileReader(this.inputfiles));
				String directory = this.dir!=null?this.dir+"/":"";
				String fileName = br.readLine();
				while (fileName !=null)
				{
					if (fileName.startsWith("#"))
					{
						
					}
					else
					{
						Inputfile inputfile = this.createInputfile();
						inputfile.setFile(directory+fileName);
						log(inputfile.getFile());
					}
					fileName = br.readLine();
					
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new BuildException(e);
			}
		}
	}
	
	
	public void execute() throws BuildException 
	{
		initFileList();
		try {
			
			Iterator<Inputfile> iterator = this.inputfilesList.iterator();
			log("Number of inputfiles:"+ inputfilesList.size());
			
			while (iterator.hasNext())
			{
				String command = this.buildCommand(iterator.next());
				log(command);
				Process p = Runtime.getRuntime().exec(command);
				
				InputStream is = p.getInputStream();
				InputStream err = p.getErrorStream();
				
				InputStreamReader r = new InputStreamReader(is);
				
				BufferedReader br = new BufferedReader(r); 
				
				String line  = br.readLine();
				while (line!=null)
				{
					log(line);
					line  = br.readLine();
				}
				
				r = new InputStreamReader(err);
				
				br = new BufferedReader(r); 
				
				line  = br.readLine();
				while (line!=null)
				{
					log(line);
					line  = br.readLine();
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new BuildException(e);
		}
		
	}



	private String buildCommand(Inputfile file) throws BuildException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		sb.append("isql ");
		sb.append("-U"+this.getUser() + " ");
		sb.append("-P"+this.getPassword() + " ");
		sb.append("-D "+this.getDatabase() + " ");
		sb.append("-i "+file.getFile() + " ");
		
		return sb.toString();
	}



	


	/**
	 * @return the user
	 */
	public String getUser() {
		return this.user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}



	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}



	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * @return the server
	 */
	public String getServer() {
		return this.server;
	}



	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
	
	public Inputfile createInputfile()
	{
		Inputfile inputfile = new Inputfile();
		this.inputfilesList.add(inputfile);
		return inputfile;
	}
	
	public class Inputfile 
	{
		private String file;
		public Inputfile()
		{
			
		}
		/**
		 * @return the file
		 */
		public String getFile() {
			return this.file;
		}
		/**
		 * @param file the file to set
		 */
		public void setFile(String file) {
			this.file = file;
		}
		
	}

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return this.database;
	}



	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the inputfiles
	 */
	public String getInputfiles() {
		return this.inputfiles;
	}

	/**
	 * @param inputfiles the inputfiles to set
	 */
	public void setInputfiles(String inputfiles) {
		this.inputfiles = inputfiles;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return this.dir;
	}

	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

}
