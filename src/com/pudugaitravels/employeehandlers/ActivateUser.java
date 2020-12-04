package com.pudugaitravels.employeehandlers;

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
import com.pudugaitravels.db.EmployeeManager;



public class ActivateUser extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name="";
	private String activRes="";
	
	
	
	
	final static Logger logger = LoggerFactory.getLogger(ActivateUser.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in ActivateUser Post method");
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			HttpSession session = req.getSession(false);
			if(session == null)
			{
				
				logger.info("Session expired when user clicked ActivateUser button from admin page");
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
					name = obj.getAsJsonPrimitive("name").getAsString();
					activRes = obj.getAsJsonPrimitive("activRes").getAsString();
					
					logger.info("name "+name);
					logger.info("activRes "+activRes);
					
					
				}
				String propPath = getServletContext().getRealPath("props/db.properties");
				EmployeeManager empManager = new EmployeeManager();
				int activatedRes = empManager.activateUser(propPath,name,activRes);
				logger.info("activatedRes :"+activatedRes);
				JsonObject respJson = new JsonObject();
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				if(activatedRes == 1)
				{
					if(activRes.equalsIgnoreCase("false"))
					{
						respJson.addProperty("actEmpMsg","User is active");
					}
					else
					{
						respJson.addProperty("actEmpMsg","User is inactive");
					}
					respJson.addProperty("name",name);
					respJson.addProperty("activRes",activRes);
				}
				else
				{
		
					respJson.addProperty("actEmpMsg","Db Error");
		
				}
				out.print(respJson.toString());
				out.flush();

			}
		}
		catch(Exception e)
		{
			logger.error("Exception in ActivateUser",e);
		}
		finally
		{
			
		}
	}

}
