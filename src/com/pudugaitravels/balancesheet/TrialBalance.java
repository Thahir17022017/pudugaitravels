package com.pudugaitravels.balancesheet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.pudugaitravels.db.LedgerManager;
import com.pudugaitravels.pojo.TrialBal;


public class TrialBalance extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = LoggerFactory.getLogger(TrialBalance.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in TrialBalance Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				
				logger.info("Session expired when user clicked TrialBalance button from admin page");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{

				String propPath = getServletContext().getRealPath("props/db.properties");
				LedgerManager ledgManager = new LedgerManager();
				ledgManager.calcAllLedgBal(propPath);
				List<TrialBal> assets = ledgManager.getAssets();
				logger.info("final assets \n"+assets);
				List<TrialBal> liabilities = ledgManager.getLiabilities();
				logger.info("final liabilities \n"+liabilities);
				List<TrialBal> capital = ledgManager.getCapital();
				logger.info("final capital \n"+capital);
				List<TrialBal> drawings = ledgManager.getDrawings();
				logger.info("final drawings \n"+drawings);
				List<TrialBal> income = ledgManager.getIncome();
				logger.info("final income \n"+income);
				List<TrialBal> expenses = ledgManager.getExpenses();
				logger.info("final expenses \n"+expenses);
				
				double totalDebits = ledgManager.calcTotalDebits(assets,drawings,expenses);
				double totalCredits = ledgManager.calcTotalCredits(liabilities,capital,income);
				
				req.setAttribute("assets",assets);
				req.setAttribute("liabilities",liabilities);
				req.setAttribute("capital",capital);
				req.setAttribute("drawings",drawings);
				req.setAttribute("income",income);
				req.setAttribute("expenses",expenses);
				req.setAttribute("date",LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-YYYY")));
				req.setAttribute("totalDebits",String.format("%.2f",totalDebits));
				req.setAttribute("totalCredits",String.format("%.2f",totalCredits));
				
							
				resp.setHeader("resultfor", "myContainer");
				RequestDispatcher rd = req.getRequestDispatcher("jsps/trialbalance.jsp");
				rd.forward(req, resp);
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in TrialBalance",e);
		}
		finally
		{
			
		}
	}
	

	
}
