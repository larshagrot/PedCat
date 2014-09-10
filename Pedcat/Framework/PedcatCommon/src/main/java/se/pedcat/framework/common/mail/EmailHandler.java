/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.common.mail;


import java.net.*;
import java.io.*;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.pedcat.framework.common.config.FrameworkConfiguration;
import se.pedcat.framework.common.util.CommonUtility;




// TODO: Auto-generated Javadoc
/**
 * Handles sending e-mail by offering the world a static method
 * "sendMessage".
 *
 * @author author ?, paba
 * @version      1.0 ?
 * @version      2.0 paba, added sendMultiPartMessage
 * $Log: /DEV/src/externweb/Bestalla/General/src/se/enea/djurskydd/general/EmailHandler.java $
 * 
 * 2     05-01-07 9:58 Larh
 **/
public class EmailHandler
{
    
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(EmailHandler.class);
    
    /** The debug. */
    private  boolean debug = logger.isDebugEnabled();
    
    /** The instance. */
    private static EmailHandler instance;
    
    
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {
    	
    	System.setProperty("use.mail.user" ,"true" );
		System.setProperty("use.mail.password" ,"true" );
		System.setProperty("use.mail.port" ,"true" );
		System.setProperty("use.mail.ssl" ,"true" );
		System.setProperty("mail.user" ,"lars.hagrot@gmail.com" );
		System.setProperty("mail.password" ,"trall1414" );
		System.setProperty("mail.smtp.port" ,"465" );
    	EmailHandler.getInstance().sendHtmlMail("smtp.gmail.com", "tjabba@lars.se", "lars.hagrot@gmail.com", "test", "<pre>HEJ</pre>");
    	
    }

    
    
    /**
     * Gets the single instance of EmailHandler.
     *
     * @return single instance of EmailHandler
     */
    public static EmailHandler getInstance()
    {
    	if (instance==null)
    	{
    		instance =  new EmailHandler(); 
    	}
    	return instance;
    }
    
    /**
     * The Class AsyncMailSender.
     */
    private class AsyncMailSender implements java.lang.Runnable
    {
        
        /** The message. */
        private Message message;
        
        /**
         * Instantiates a new async mail sender.
         *
         * @param message the message
         */
        public AsyncMailSender(Message message)
        {
            this.message = message;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run()
        {
            try
            {
                logger.trace("Sending.....");
                Transport.send(message);
                logger.trace("Sent");
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * This is the method responsible for establishing a communications
     * path to the mail server and for sending SMTP commands.
     * <p>
     * Step 1:  Create a new socket object using the name of the mail
     *          server and the port number.<br>
     * Step 2:  Create a printstream object based on the new socket.<br>
     * Step 3:  Send the mail server the appropriate SMTP commands
     *          using the printstream.<br>
     * Step 4:  Capture and display status messages return from the
     *          mail server. See getReply() method
     *          below.<br>
     * Step 5:  Release the connection to the server.<br>
     * Step 6:  Close the printstream and the socket.
     *
     * @param host_name Internet host name of the mail server
     * @param sender_name Sender's name (email adress)
     * @param recipient_name Recipient's email adress
     * @param subject E-mail subject
     * @param message_body The message itself
     * @return The response from the mail server.
     **/
    public String sendMessage(String host_name,
    String sender_name,
    String recipient_name,
    String subject,
    String message_body)
    {
        
        
        EmailHandler emh = new EmailHandler();
        StringBuffer server_response = new StringBuffer();
        
        if (debug) logger.trace( "host_name: " + host_name);
        if (debug) logger.trace( "sender_name: " + sender_name);
        if (debug) logger.trace( "recipient_name: " + recipient_name);
        if (debug) logger.trace("subject: " + subject);
        if (debug) logger.trace("message_body: " + message_body);
        
        try
        {
            //Step 1
            // Sendmail port will typically be port #25.
            Socket outgoing = new Socket(host_name, 25);
            
            //Step 2
            PrintWriter ps = new PrintWriter(outgoing.getOutputStream(), true);
            
            //Steps 3 & 4
            ps.println("HELO " + host_name);
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace( "emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            ps.println("MAIL FROM: " + sender_name);
            if (debug) logger.trace( "MAIL FROM: " + sender_name);
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace( "emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            ps.println("RCPT TO: " + recipient_name);
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace("emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            ps.println("DATA");
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace( "(emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            ps.println("Subject: "  + subject + "\n");
            ps.println(message_body + "\n" + "." + "\n");
            
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace( "(emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            //Step 5
            ps.println("QUIT");
            //            server_response.append(emh.getReply(outgoing)+"\n");
            if (debug) logger.trace( "(emh.getReply(outgoing): " + emh.getReply(outgoing));
            
            //Step 6
            ps.close();
            outgoing.close();
        }
        catch (Exception e)
        {
            logger.error( "Exception: " + e.getMessage(),e);
            server_response.append(e.getMessage()+"\n");
        }
        return server_response.toString();
    }
    
    /**
     * This method will capture status message returned by the mail server
     * and return them.
     * Step 1:  Create an InputStream for the socket object created above that gets passed
     *          into this method.
     * Step 2:  Read a line of the InputStream and return the line as a string for display.
     *
     * @param incoming The SMTP socket
     * @return The SMTP-server reply
     **/
    public String getReply(Socket incoming)
    {
        
        try
        {
            //Step 1
            BufferedReader myDIS = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            
            //Step 2
            String the_message = myDIS.readLine();
            return the_message;
        }
        catch (IOException e)
        {
        	logger.error( "Exception: " + e.getMessage(), e);
            return "ERROR";
        }
    }
    
    /**
     * Sends a mail with text and a attached html page.
     *
     * @param host_name mailserver
     * @param sender e-mail adress to the sender of the e-mail
     * @param recipient e-mail adress to the reciever of the email
     * @param subject subject of the email
     * @param HtmlPart HTML formatted string
     * @return the string
     */
    public String sendHtmlMail(String host_name,
    String sender,
    String recipient,
    String subject,
    String HtmlPart)
    {
        return sendHtmlMail(  host_name,
        sender,
        recipient,
        subject,
        HtmlPart,
        null,
        null);
    }
    
    /**
     * Send html mail.
     *
     * @param host_name the host_name
     * @param sender the sender
     * @param recipient the recipient
     * @param subject the subject
     * @param HtmlPart the html part
     * @param ccRecipients the cc recipients
     * @param attachmentFiles the attachment files
     * @return the string
     */
    public String sendHtmlMail(String host_name,
    String sender,
    String recipient,
    String subject,
    String HtmlPart,
    String[] ccRecipients,
    File[] attachmentFiles)
    {
        return sendHtmlMail(  host_name,
        sender,
        recipient,
        subject,
        HtmlPart,
        null,
        null,
        false);
    }
    
    /**
     * Sends a mail with text and a attached html page.
     *
     * @param host_name mailserver
     * @param sender e-mail adress to the sender of the e-mail
     * @param recipient e-mail adress to the reciever of the email
     * @param subject subject of the email
     * @param HtmlPart HTML formatted string
     * @param ccRecipients an array with addresses to cc
     * @param attachmentFiles the attachment files
     * @param async the async
     * @return the string
     */
    public String sendHtmlMail(String host_name,
    String sender,
    String recipient,
    String subject,
    String HtmlPart,
    String[] ccRecipients,
    File[] attachmentFiles,
    boolean async)
    {
        
        logger.trace("sendHtmlMail---->");
        Session mailSession = null;
        Address replyAddress = null;
        Address[] recipientAddresses = null;
        Address   fromAddress = null;
        Address[] ccAddresses = null;
        Address[] replyAddresses = new Address[1];
        
        
        final Properties props = new Properties();
        props.setProperty("mail.host", host_name);
        props.setProperty("mail.smtp.host", host_name);
        
        props.setProperty("mail.transport.protocol", "smtp");
        
        
        javax.mail.Authenticator au = null; 
        
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.user", false) && FrameworkConfiguration.getInstance().getProperty("mail.user")!=null)
        {
        	props.setProperty("mail.user", FrameworkConfiguration.getInstance().getProperty("mail.user"));
        
	        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.password", false) && FrameworkConfiguration.getInstance().getProperty("mail.password")!=null)
	        {
	        	props.setProperty("mail.password", FrameworkConfiguration.getInstance().getProperty("mail.password"));
	        	au = new javax.mail.Authenticator(){
	
	    			@Override
	    			protected PasswordAuthentication getPasswordAuthentication() {
	    				
	    				return new PasswordAuthentication(props.getProperty("mail.user"),props.getProperty("mail.password"));
	    			}};
	        }
        }
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.port", false) && FrameworkConfiguration.getInstance().getProperty("mail.smtp.port")!=null)
        {
        	props.setProperty("mail.smtp.port", FrameworkConfiguration.getInstance().getProperty("mail.smtp.port"));
        }
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.ssl", false) && FrameworkConfiguration.getInstance().getProperty("mail.smtp.port")!=null)
        {
        	props.setProperty("mail.smtp.port", FrameworkConfiguration.getInstance().getProperty("mail.smtp.port"));
        	props.put("mail.smtp.socketFactory.port", FrameworkConfiguration.getInstance().getProperty("mail.smtp.port"));
    		props.put("mail.smtp.socketFactory.class",
    				"javax.net.ssl.SSLSocketFactory");
    		props.put("mail.smtp.auth", "true");
    		props.put("mail.smtp.port", FrameworkConfiguration.getInstance().getProperty("mail.smtp.port"));
        }
        
                
        mailSession = Session.getInstance(props, au);
        
        try
        {
            // Check for sendlist....
            String[] addresses = CommonUtility.parseLine(recipient,',');
            recipientAddresses = new Address[addresses.length];
            for(int i=0;i<addresses.length;i++)
            {
                recipientAddresses[i] = new InternetAddress(addresses[i]);
            }
            fromAddress = new InternetAddress(sender);
            replyAddress = new InternetAddress(sender);
            replyAddresses[0] = replyAddress;
            if (ccRecipients!=null && ccRecipients.length>0)
            {
                ccAddresses = new Address[ccRecipients.length];
                for(int index=0;index<ccAddresses.length;index++)
                {
                    ccAddresses[index] = new InternetAddress(ccRecipients[index]);
                }
            }
        }
        catch (AddressException ae)
        {
            logger.error("AddressException: " + ae.getMessage(),ae);
        }
        
        MimeMessage message = new MimeMessage(mailSession);
        try
        {
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            if (ccAddresses != null)
            {
                message.setRecipients(Message.RecipientType.CC, ccAddresses);
            }
            message.setFrom(fromAddress);
            message.setSubject(subject);
            message.setReplyTo(replyAddresses);
            
            // construct HTML message
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(HtmlPart, "text/html")));
            
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp);
            
            for(int i=0;attachmentFiles!=null  && i< attachmentFiles.length;i++)
            {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                DataSource source1 = new FileDataSource(attachmentFiles[i]);
                DataSource source2 = new ByteArrayDataSource(source1.getInputStream(),"application/pdf");
                mimeBodyPart.setDataHandler(new DataHandler(source2));
                mimeBodyPart.setFileName(attachmentFiles[i].getName());
                
                
                
                mp.addBodyPart(mimeBodyPart);
            }
            
            // add the Multipart to the message
            message.setContent(mp);
            message.setHeader("X-Mailer", "msgsend");
            message.setSentDate(new Date());
            //Transport trans = mailSession.getTransport("smtp");
            if (async)
            {
                Thread thread = new Thread(new EmailHandler.AsyncMailSender(message));
                thread.start();
            }
            else
            {
                Transport.send(message);
            }
        }
        catch (Exception me)
        {
        	logger.error("MessagingException: " + me.getMessage(),me);
        }
        
        return "";
    }
    
    /**
     * Sends a mail with text and a attached html page.
     *
     * @param host_name mailserver
     * @param sender e-mail adress to the sender of the e-mail
     * @param recipient e-mail adress to the reciever of the email
     * @param subject subject of the email
     * @param messageText mail body text
     * @param htmlFileName filename without .html (ex: thefile)
     * @param HtmlPart HTML formatted string
     * @return the string
     */
    public String sendAttachedHtmlFile(String host_name,
    String sender,
    String recipient,
    String subject,
    String messageText,
    String htmlFileName,
    String HtmlPart)
    {
        
        logger.trace("sendAttachedHtmlFile ----->");
        
        Session mailSession = null;
        Address replyAddress = null;
        Address recipientAddress = null;
        Address fromAddress = null;
        Address ccAddress = null;
        Address[] replyAddresses = new Address[1];
        
        Properties props = new Properties();
        props.setProperty("mail.host", host_name);
        props.setProperty("mail.smtp.host", host_name);
        
        props.setProperty("mail.transport.protocol", "smtp");
        
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.user", false) && FrameworkConfiguration.getInstance().getProperty("mail.user")!=null)
        {
        	props.setProperty("mail.user", FrameworkConfiguration.getInstance().getProperty("mail.user"));
        }
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.password", false) && FrameworkConfiguration.getInstance().getProperty("mail.password")!=null)
        {
        	props.setProperty("mail.password", FrameworkConfiguration.getInstance().getProperty("mail.password"));
        }
        if (FrameworkConfiguration.getInstance().getBooleanProperty("use.mail.port", false) && FrameworkConfiguration.getInstance().getProperty("mail.smtp.port")!=null)
        {
        	props.setProperty("mail.smtp.port", FrameworkConfiguration.getInstance().getProperty("mail.smtp.port"));
        }
        
        mailSession = Session.getInstance(props, null);
        
        try
        {
            recipientAddress = new InternetAddress(recipient);
            fromAddress = new InternetAddress(sender);
            replyAddress = new InternetAddress(sender);
            replyAddresses[0] = replyAddress;
        }
        catch (AddressException ae)
        {
        		logger.error("AddressException: ",ae);
        }
        
        MimeMessage message = new MimeMessage(mailSession);
        try
        {
            message.setRecipient(Message.RecipientType.TO, recipientAddress);
            if (ccAddress != null)
            {
                message.setRecipient(Message.RecipientType.CC, ccAddress);
            }
            message.setFrom(fromAddress);
            message.setSubject(subject);
            message.setReplyTo(replyAddresses);
            
            // construct multipart message
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(messageText,"iso-8859-1");
            
            // build and add attachment HTML
            MimeBodyPart mbp2 = new MimeBodyPart();
            mbp2.setFileName(htmlFileName + ".html");
            mbp2.setDataHandler(new DataHandler(new ByteArrayDataSource(HtmlPart, "text/html")));
            
            // create the Multipart and its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);
            
            // add the Multipart to the message
            message.setContent(mp);
            message.setHeader("X-Mailer", "msgsend");
            message.setSentDate(new Date());
            
            Transport.send(message);
        }
        catch (MessagingException me)
        {
            logger.error("MessagingException",me);
        }
        
        return "";
    }
    
    /**
     * Run.
     */
    public void run()
    {
    }
    
}


/**
 * This class is taken from MailFormServlet :
 *  maybe it could be declared public in MailFormServlet
 *  and deleted from here, then do some testing!!!
 */
class ByteArrayDataSource implements DataSource
{
    private final Logger logger = LoggerFactory.getLogger(EmailHandler.class);
    
    private byte[] data; // data
    private String type; // content-type
    
    /* Create a datasource from an input stream */
    ByteArrayDataSource(InputStream is, String type)
    {
        logger.trace("ByteArrayDataSource---->");
        this.type = type;
        try
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1)
                os.write(ch);
            data = os.toByteArray();
        }
        catch (IOException ioex)
        {
            logger.error("IOException",ioex);
        }
    }
    
    /* Create a datasource from a byte array */
    ByteArrayDataSource(byte[] data, String type)
    {
        this.data = data;
        this.type = type;
    }
    
    /* Create a datasource from a String */
    ByteArrayDataSource(String data, String type)
    {
        logger.trace("ByteArrayDataSource-----");
        try
        {
            // Assumption that the string contains only ascii
            // characters ! Else just pass in a charset into this
            // constructor and use it in getBytes()
            this.data = data.getBytes("iso-8859-1");
        }
        catch (UnsupportedEncodingException uex)
        {
            logger.error("uex",uex);
        }
        
        this.type = type;
    }
    
    public InputStream getInputStream() throws IOException
    {
        if (data == null)
            throw new IOException("no data");
        return new ByteArrayInputStream(data);
    }
    public OutputStream getOutputStream() throws IOException
    {
        throw new IOException("cannot do this");
    }
    public String getContentType()
    {
        return type;
    }
    public String getName()
    {
        return "dummy";

    }
    
    
}



