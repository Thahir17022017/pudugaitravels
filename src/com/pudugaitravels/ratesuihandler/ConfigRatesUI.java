package com.pudugaitravels.ratesuihandler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.db.EmployeeManager;



public class ConfigRatesUI extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(ConfigRatesUI.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		try {
			logger.info("Running in ConfigRatesUI Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked on Exchange rate option in admin page ");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				
				
					resp.setHeader("resultfor", "myContainer");
					RequestDispatcher rd = req.getRequestDispatcher("jsps/rates_config.jsp");
					rd.forward(req, resp);
				
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in ConfigRatesUI",e);
			
		}
		finally
		{
			
		}
	}

}
