package com.pudugaitravels.ratesuihandler;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.db.RateManager;

public class ShowCurrencyConversionUi extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(ShowCurrencyConversionUi.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in ShowCurrencyConversionUi Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked on CheckCumRatesHourly rate button in admin page ");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				String propPath = getServletContext().getRealPath("props/db.properties");
				RateManager rateManager = new RateManager();
				List<String> readNames = rateManager.readCountryNames(propPath);
				req.setAttribute("CountryList",readNames);
				resp.setHeader("resultfor", "secondRow");
				RequestDispatcher rd = req.getRequestDispatcher("jsps/todays_currencypair.jsp");
				rd.forward(req, resp);
			}
		}
		catch(Exception e)
		{
			
			logger.error("Exception in  ShowCurrencyConversionUi"+e);
		}
		finally
		{
			
		}
	}
}
