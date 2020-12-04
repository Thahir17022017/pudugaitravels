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



public class UpdateEmployee extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user="";
	private String pwd="";
	private String empType="";
	
	
	
	final static Logger logger = LoggerFactory.getLogger(UpdateEmployee.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in UpdateEmployee Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked UpdateEmployee button from admin page");
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
					pwd = obj.getAsJsonPrimitive("pwd").getAsString();
					empType = obj.getAsJsonPrimitive("empType").getAsString();
					logger.info("name "+user);
					logger.info("pwd "+pwd);
					logger.info("empType "+empType);
					
					
				}
				String propPath = getServletContext().getRealPath("props/db.properties");
				EmployeeManager empManager = new EmployeeManager();
				String userRole = empManager.checkUserRole(propPath,user);
				logger.info("userRole :"+userRole);
				JsonObject respJson = new JsonObject();
				if(userRole.equals(""))
				{
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					respJson.addProperty("updEmpMsg","Db Error");
					out.print(respJson.toString());
					out.flush();
				}
				else if(userRole.equals("Admin") && empType.equals("Employee"))
				{
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					respJson.addProperty("updEmpMsg","User is an Admin");
					out.print(respJson.toString());
					out.flush();
				}
				else if(userRole.equals("Employee") && empType.equals("Admin"))
				{
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					respJson.addProperty("updEmpMsg","User is an Employee");
					out.print(respJson.toString());
					out.flush();
				}
				else
				{
					SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
				    byte[] salt = new byte[16];
				    sr.nextBytes(salt);
				    
				    
					KeySpec spec = new PBEKeySpec(pwd.toCharArray(), salt, 1000,64*8);
					SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
					byte[] calcHashVal = factory.generateSecret(spec).getEncoded();
					
					String calcHashValStr = Hex.encodeHexString(calcHashVal);
					String calcSaltStr=Hex.encodeHexString(salt);
					logger.info("calculated hash value in str :"+calcHashValStr);
					logger.info("calculated salt value in str :"+calcSaltStr);
					
					int res = empManager.updateNewEmployee(propPath,user,calcSaltStr,calcHashValStr);
					logger.info("res after update :"+res);
					if(res==0)
					{
						PrintWriter out = resp.getWriter();
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						respJson.addProperty("updEmpMsg","Db Error in update");
						out.print(respJson.toString());
						out.flush();
					}
					else if(res == 1)
					{
						
						   String sessionName = (String)session.getAttribute("name");
						   if(user.equals(sessionName))
						   {
							   session.invalidate();
							   resp.setHeader("resultfor", "bodyContainer");
							   RequestDispatcher rd = req.getRequestDispatcher("jsps/adminlogin2.jsp");
							   rd.forward(req, resp);
						   }
						   else
						   {
							   	PrintWriter out = resp.getWriter();
								resp.setContentType("application/json");
								resp.setCharacterEncoding("UTF-8");
								respJson.addProperty("updEmpMsg","Employee Updated");
								out.print(respJson.toString());
								out.flush();
						   }
						   
						
							
						
						
					}
					
					
					
				}
//				JsonObject respJson = new JsonObject();
//				
//				
//				
//				PrintWriter out = resp.getWriter();
//				resp.setContentType("application/json");
//				resp.setCharacterEncoding("UTF-8");
//				out.print(respJson.toString());
//				out.flush();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in UpdateEmployee",e);
		}
		finally
		{
			
		}
	}
	
	

}
