package com.pudugaitravels.adminhandlers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.email.EmailManager;



public class LogoutHandler extends HttpServlet{
	
	

	private static final long serialVersionUID = 1L;
	private String meridian = "";
	final static Logger logger = LoggerFactory.getLogger(LogoutHandler.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
			doGet(req, resp);
			
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		
       
		try 
		{
			logger.info("Running in LogoutHandler Get method");
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			HttpSession session = req.getSession();
			String name = (String) session.getAttribute("name");
			session.invalidate();
			
			Date date = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
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
			if(name!=null)
			{
				emailHandler.sendLoggedInfo("User : "+name+" logged out from the System at "+cal.get(Calendar.HOUR_OF_DAY)+" : "+cal.get(Calendar.MINUTE)+" "+meridian);
			}
			
			
			RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
			rd.include(req, resp);
			
	
		}
		catch(Exception e)
		{
			logger.error("Exception in Logouthandler",e);
		}
		finally
		{
			meridian="";
		}
	
	}
}
