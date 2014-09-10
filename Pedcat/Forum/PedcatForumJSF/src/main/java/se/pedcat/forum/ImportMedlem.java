/**
 * 
 */
package se.pedcat.forum;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import se.pedcat.forum.model.Medlem;
import se.pedcat.forum.service.MedlemService;
import se.pedcat.forum.service.MedlemServiceImpl;

/**
 * @author laha
 *
 */
public class ImportMedlem {

	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String fileLocation = "xml/medlemslista_2010.xml";
		try {
			
			MedlemService medlemService = new MedlemServiceImpl();
			
			
			FileInputStream fileInputStream;
			
			fileInputStream = new FileInputStream(fileLocation);
			XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance().createXMLStreamReader(fileInputStream);
			boolean isData = false;
			boolean isNew = false;
			boolean isTable = false;
			boolean isFinished = false;
			boolean isFirst = true;
			Medlem currentMedlem = null;
			int row = 0;
			int index=1;
			StringBuffer sb = new StringBuffer();
			while (xmlStreamReader.hasNext() && !isFinished)
			{
				
				switch (xmlStreamReader.next())
				{
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					isTable = xmlStreamReader.getName().getLocalPart().equals("Table");
					
					isNew = xmlStreamReader.getName().getLocalPart().equals("Row");
					isData = xmlStreamReader.getName().getLocalPart().equals("Data");
					if (isNew)
					{
						System.out.println("=========================");
						index = 0;
						row++;
						currentMedlem = new Medlem();
					}
					else
					{
						if (isData)
						{
							sb.setLength(0);
							index++;
						}
					}
						
					break;
				case XMLStreamConstants.END_ELEMENT:
					
					isData = xmlStreamReader.getName().getLocalPart().equals("Data");
					isTable = xmlStreamReader.getName().getLocalPart().equals("Table");
					if (isData)
					{
						System.out.println(index + ":" + sb.toString() + " "+ ( row==1?"*":""));
						isData = false;
						if (row>1)
						{
							switch (index)
							{
							case 1:
								currentMedlem.setFornamn(sb.toString());
								break;
							case 2:
								currentMedlem.setEfternamn(sb.toString());
								break;
							case 3:
								currentMedlem.setMedlemsnummer(sb.toString());
								break;	
							case 4:
								currentMedlem.setPersonnummer(convertDate(sb.toString()));
								break;	
							case 5:
								currentMedlem.setEpost(sb.toString());
								
								medlemService.ensure(currentMedlem);
								break;	
							}
						}
					}
					if (isTable)
					{
						isFinished = true;
					}
					
					break;
				case XMLStreamConstants.CHARACTERS:
					if (isData)
					{
						String text = xmlStreamReader.getText();
						sb.append(text);
					}
					break;
			
				}
				
				
				
				
				
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

	}

	/**
	 * @param string
	 * @return
	 */
	private static String convertDate(String string) {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		
		try
		{
			Date date = sdf2.parse(string);
			return sdf3.format(date);
		}
		catch (Exception e)
		{
			string = string.replace('T', ' ');
			Date date = null;
			try {
				date = sdf.parse(string);
				return sdf3.format(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return "-";
	}

}
