package com.pudugaitravels.adminhandlers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pudugaitravels.db.EmployeeManager;
import com.pudugaitravels.employeehandlers.ReadEmployee;

public class HomePageHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(HomePageHandler.class);
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			logger.info("Running in HomePageHandler Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user logged to HomePageHandler");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				String loggedUser = req.getHeader("detailType");
				logger.info("In Request loggedUser :" + loggedUser);
				String propPath = getServletContext().getRealPath("props/db.properties");
				EmployeeManager empManager = new EmployeeManager();
				String userRole = empManager.checkUserRole(propPath,loggedUser);
				logger.info("User role  : "+userRole);
				if(userRole.equals("Admin"))
				{
					resp.setHeader("resultfor", "myContainer");
					RequestDispatcher rd = req.getRequestDispatcher("jsps/admin_home.jsp");
					rd.forward(req, resp);
				}
				if(userRole.equals("Employee"))
				{
					resp.setHeader("resultfor", "myContainer");
					RequestDispatcher rd = req.getRequestDispatcher("jsps/emp_home.jsp");
					rd.forward(req, resp);
				}
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in HomePageHandler GET Method",e);
			
		}
		finally
		{
			
		}
	}

}
