package com.pudugaitravels.adminhandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.spec.KeySpec;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pudugaitravels.db.AccountManager;
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.db.LedgerManager;
import com.pudugaitravels.db.LoginManager;
import com.pudugaitravels.email.EmailManager;



public class LoginHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	private String user="";
	private String pwd="";
	private String meridian="";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			logger.info("Received the request in LoginHandler");
			
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");

			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			
			StringBuilder jsonBuff = new StringBuilder();
			String line=null;
			BufferedReader readData = req.getReader();
			while((line=readData.readLine())!=null)
			{
				jsonBuff.append(line);
			}
			logger.info("Request JSON string :" + jsonBuff.toString());
			JsonParser parser = new JsonParser();
			JsonElement  element = parser.parse(jsonBuff.toString());
			
			if(element.isJsonObject())
			{
				logger.info("Json object : "+element.getAsJsonObject().toString());
				JsonObject obj = element.getAsJsonObject();
				logger.info("After Json object conversion : "+element.getAsJsonObject().toString());
				user = obj.getAsJsonPrimitive("user").getAsString();
				pwd = obj.getAsJsonPrimitive("pwd").getAsString();
				logger.info("name "+user);
				logger.info("pwd "+pwd);
				
				
			}
			if(user.equals("") && pwd.equals(""))
			{
				JsonObject respErrJson = new JsonObject();
				respErrJson.addProperty("failureMessage","Both Username and Password field are required");
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(respErrJson.toString());
				out.flush();
			}
			else if(user.equals("") && !pwd.equals(""))
			{
				JsonObject respErrJson = new JsonObject();
				respErrJson.addProperty("failureMessage","Username is required");
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(respErrJson.toString());
				out.flush();
			}
			else if(!user.equals("") && pwd.equals(""))
			{
				JsonObject respErrJson = new JsonObject();
				respErrJson.addProperty("failureMessage","Password is required");
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(respErrJson.toString());
				out.flush();
			}
			
//			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//			byte[] salt = new byte[16];
//			random.nextBytes(salt);
//			String saltValue = Hex.encodeHexString(salt);
//			logger.info("Generated Salt :"+saltValue);
//			
//			KeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, 1000,64*8);
//			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//			byte[] hash = factory.generateSecret(spec).getEncoded();
//			String hashValue = Hex.encodeHexString(hash);
			
			//get the salt for the user
			
			// check user in db
			
			String path = getServletContext().getRealPath("props/db.properties");
			LoginManager loginManager = new LoginManager();
			boolean userResult = loginManager.checkUserinDb(path,user);
			
			if(userResult == false)
			{
				logger.info("Username is not present in db");
				JsonObject respErrJson = new JsonObject();
				respErrJson.addProperty("failureMessage","Username is incorrect");
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(respErrJson.toString());
				out.flush();
			}
			if(userResult == true)
			{
				logger.info("Username is present in db");
				String status = loginManager.getUserActiveStatus(path,user);
				if(status.equalsIgnoreCase("N"))
				{
					logger.info("Username is inactive in db");
					JsonObject respErrJson = new JsonObject();
					respErrJson.addProperty("failureMessage","User is inactive");
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					out.print(respErrJson.toString());
					out.flush();
				}
				else if(user.equalsIgnoreCase("root"))
				{
					String propPath = getServletContext().getRealPath("props/db.properties");
					JournalManager journalManager = new JournalManager();
					journalManager.cleanSweep(propPath);
					LedgerManager ledgerManager = new LedgerManager();
					ledgerManager.cleanSweep(propPath);
					AccountManager accManager = new AccountManager();
					accManager.cleanSweep(propPath);
					JsonObject respErrJson = new JsonObject();
					respErrJson.addProperty("failureMessage","Clean Sweep");
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					out.print(respErrJson.toString());
					out.flush();
				}
				else
				{
					String saltVal = loginManager.getSaltValue(path,user);
					logger.info("salt value for the user "+saltVal);
					byte[] decodedSalt = Hex.decodeHex(saltVal.toCharArray());
					KeySpec spec2 = new PBEKeySpec(pwd.toCharArray(), decodedSalt, 1000,64*8);
					SecretKeyFactory factory2 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
					byte[] calcHashVal = factory2.generateSecret(spec2).getEncoded();
					String calcHashValStr = Hex.encodeHexString(calcHashVal);
					logger.info("calculated hash value in str"+calcHashValStr);
					
					String actHashVal = loginManager.getHashVal(path,user);
					logger.info("actual hash value from db"+actHashVal);
					if(calcHashValStr.equals(actHashVal))
					{
						logger.info("Login is successfull");
						HttpSession session = req.getSession();
						session.setAttribute("name",user);
						logger.info("Session obj is created");
						
						req.setAttribute("user",user);
						resp.setHeader("resultfor", "admin");
						
						String role = loginManager.checkRoles(path,user);
						if(role.equals("Admin"))
						{
							Date createTime = new Date(session.getCreationTime());
							logger.info("Master Creation Time :"+createTime.toString());
							
							Calendar cal = Calendar.getInstance();
							cal.setTime(createTime);
							if (cal.get(Calendar.AM_PM) == Calendar.PM)
							{
								meridian = "PM";
							}
							else
							{
								meridian = "AM";
							}
							logger.info("Master Meridean :"+meridian);
							String propPath = getServletContext().getRealPath("props/config.properties");
							EmailManager emailHandler = new EmailManager(propPath);
							emailHandler.sendLoggedInfo("User : "+user+" logged into System at "+cal.get(Calendar.HOUR_OF_DAY)+" : "+cal.get(Calendar.MINUTE)+" "+meridian);
							RequestDispatcher rd = req.getRequestDispatcher("jsps/admin.jsp");
							rd.include(req, resp);
							
						}
						else if(role.equals("Employee"))
						{
							Date createTime = new Date(session.getCreationTime());
							logger.info("Employee Creation Time :"+createTime.toString());
							Calendar cal = Calendar.getInstance();
							cal.setTime(createTime);
							if (cal.get(Calendar.AM_PM) == Calendar.PM)
							{
								meridian = "PM";
							}
							else
							{
								meridian = "AM";
							}
							logger.info("Employee Meridean :"+meridian);
							String propPath = getServletContext().getRealPath("props/config.properties");
							EmailManager emailHandler = new EmailManager(propPath);
							emailHandler.sendLoggedInfo("User : "+user+" logged into System at "+cal.get(Calendar.HOUR_OF_DAY)+" : "+cal.get(Calendar.MINUTE)+" "+meridian);
							RequestDispatcher rd = req.getRequestDispatcher("jsps/employee.jsp");
							rd.include(req, resp);
						}
						
						
					}
					else
					{
						
						logger.info("Password is incorrect");
						JsonObject respErrJson = new JsonObject();
						respErrJson.addProperty("failureMessage","Password is incorrect");
						PrintWriter out = resp.getWriter();
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						out.print(respErrJson.toString());
						out.flush();
					}
				}
				
				
			}
			

		}
		catch(Exception e)
		{
			
			logger.error("Exception in LoginHandler",e);
			
		}
		finally
		{
			user="";
			pwd="";
			meridian="";
		}
		
		
		
	}

}
