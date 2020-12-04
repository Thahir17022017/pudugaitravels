package com.pudugaitravels.email;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.prop.PropertyManager;



public class EmailManager {
	
	final static Logger logger = LoggerFactory.getLogger(EmailManager.class);
	
	private PropertyManager propManager = new PropertyManager();
	private String path;
	
	
	public EmailManager(String path)
	{
		this.path = path;
	}
	
	
	public boolean sendEmail(String name,String email,String phone,String subject,String msg)
	{
		
		logger.info("----inside send email ----- ");
		
		logger.info(" User name value : "+name);
		
		
		logger.info(" User email value : "+email);
		
		
		logger.info(" User phone value : "+phone);
		
		
		logger.info(" User subject value : "+subject);
		
		
		logger.info(" User message value : "+msg);
		
		
		
		
		logger.info("----inside send email ----- ");
		
		
		propManager.loadProps(path);
		// Recipient's email ID needs to be mentioned.
	      String to = propManager.readSpecProp("com.pudugaitravels.toaddr");;//change accordingly
	      logger.info("to addr "+to);

	      // Sender's email ID needs to be mentioned
	      final String from = propManager.readSpecProp("com.pudugaitravels.fromaddr");//change accordingly
	      logger.info("from addr "+from);
	      final String username = propManager.readSpecProp("com.pudugaitravels.username");//change accordingly
	      logger.info("username "+username);
	      final String password = propManager.readSpecProp("com.pudugaitravels.password");//change accordingly
	      logger.info("password "+password);

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = propManager.readSpecProp("com.pudugaitravels.hostname");
	      logger.info("host "+host);
	      
	      String port = propManager.readSpecProp("com.pudugaitravels.port");
	      logger.info("port "+port);
	      
	      
	      String ssl = propManager.readSpecProp("com.pudugaitravels.ssl");
	      logger.info("ssl "+ssl);
	      
	      String tls = propManager.readSpecProp("com.pudugaitravels.tls");
	      logger.info("tls "+tls);
	      
	      String auth = propManager.readSpecProp("com.pudugaitravels.auth");
	      logger.info("auth "+auth);
	      
	      Properties props = new Properties();
//	      props.put("mail.smtp.auth", auth);
//	      props.put("mail.smtp.starttls.enable", tls);
//	      props.put("mail.smtp.socketFactory.class",ssl);   
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", port);

	      // Get the Session object.
	      Session emailSession = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(emailSession);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(subject+" from PudugaiTravels.com");

	         // Now set the actual message
	         message.setText("Enquiry Details from PudugaiTravels.com \n\n"+
	        		 " Name : "+name+"\n\n"+
	        		 " Phone : "+phone+"\n\n"+
	        		 " Email : "+email+"\n\n"+
	        		 " Message : "+msg+"\n\n");	

	         // Send message
	         Transport.send(message);

	         logger.info("Sent message successfully....");
	         return true;

	      } catch (MessagingException e) {
	    	  logger.error("email message failure....",e);
	    	  return false;
	      }
	}


	public void sendLoggedInfo(String subject) {
		// TODO Auto-generated method stub
		logger.info("----inside send email ----- ");
		
		
		
		logger.info(" User subject value : "+subject);
		
		
		
		
		
		
		logger.info("----inside send email ----- ");
		
		
		propManager.loadProps(path);
		// Recipient's email ID needs to be mentioned.
	      String to = propManager.readSpecProp("com.pudugaitravels.toaddr");;//change accordingly
	      logger.info("to addr "+to);

	      // Sender's email ID needs to be mentioned
	      final String from = propManager.readSpecProp("com.pudugaitravels.fromaddr");//change accordingly
	      logger.info("from addr "+from);
	      final String username = propManager.readSpecProp("com.pudugaitravels.username");//change accordingly
	      logger.info("username "+username);
	      final String password = propManager.readSpecProp("com.pudugaitravels.password");//change accordingly
	      logger.info("password "+password);

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = propManager.readSpecProp("com.pudugaitravels.hostname");
	      logger.info("host "+host);
	      
	      String port = propManager.readSpecProp("com.pudugaitravels.port");
	      logger.info("port "+port);
	      
	      
	      String ssl = propManager.readSpecProp("com.pudugaitravels.ssl");
	      logger.info("ssl "+ssl);
	      
	      String tls = propManager.readSpecProp("com.pudugaitravels.tls");
	      logger.info("tls "+tls);
	      
	      String auth = propManager.readSpecProp("com.pudugaitravels.auth");
	      logger.info("auth "+auth);
	      
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", auth);
	      props.put("mail.smtp.starttls.enable", tls);
	      props.put("mail.smtp.socketFactory.class",ssl);   
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", port);

	      // Get the Session object.
	      Session emailSession = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(emailSession);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject(subject);

//	         // Now set the actual message
	         
	         message.setText(subject);
//	         message.setText("Enquiry Details from PudugaiTravels.com \n\n"+
//	        		 " Name : "+name+"\n\n"+
//	        		 " Phone : "+phone+"\n\n"+
//	        		 " Email : "+email+"\n\n"+
//	        		 " Message : "+msg+"\n\n");	

	         // Send message
	         Transport.send(message);

	         logger.info("Sent message successfully....");
	         

	      } catch (MessagingException e) {
	    	  logger.error("email message failure....",e);
	    	  
	      }
		
	}

}