package com.pudugaitravels.adminhandlers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntialLoginHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(IntialLoginHandler.class);
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");

			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
			rd.include(req, resp);
		}
		catch(Exception e)
		{
			logger.error("Exception in IntialLoginHandler GET Method",e);
		}
	}
	
	

}
