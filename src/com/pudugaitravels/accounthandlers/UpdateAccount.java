package com.pudugaitravels.accounthandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

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



public class UpdateAccount extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user="";
	private String accType="";
	private String oldAccName="";
	
	
	
	
	final static Logger logger = LoggerFactory.getLogger(UpdateAccount.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in UpdateAccount Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked UpdateAccount button from admin page");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				
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
					oldAccName = obj.getAsJsonPrimitive("oldUser").getAsString();
					accType = obj.getAsJsonPrimitive("accType").getAsString();
					logger.info("name "+user);
					logger.info("oldAccName "+oldAccName);
					logger.info("accType "+accType);
				
				}
				String propPath = getServletContext().getRealPath("props/db.properties");
				AccountManager accManager = new AccountManager();
				
				JsonObject respJson = new JsonObject();
				int res = accManager.updateAccount(propPath,user,oldAccName);
				logger.info("res after update account:"+res);
				if(res==0)
				{
						PrintWriter out = resp.getWriter();
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						respJson.addProperty("updAccMsg","Db Error in update");
						out.print(respJson.toString());
						out.flush();
				}
				else
				{	
							   	PrintWriter out = resp.getWriter();
								resp.setContentType("application/json");
								resp.setCharacterEncoding("UTF-8");
								respJson.addProperty("updAccMsg","Employee Updated");
								respJson.addProperty("oldAccName",oldAccName+" - "+accType);
								respJson.addProperty("newAccName",user+" - "+accType);
								out.print(respJson.toString());
								out.flush();
				}
						   
						
							
						
						
			}
					

			
		}
		catch(Exception e)
		{
			logger.error("Exception in UpdateAccount",e);
		}
		finally
		{
			user="";
			oldAccName="";
			accType="";
		}
	}
	
	

}
