package com.pudugaitravels.balancesheet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.db.EmployeeManager;
import com.pudugaitravels.db.LedgerManager;
import com.pudugaitravels.pojo.TrialBal;

public class Income extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = LoggerFactory.getLogger(Income.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in Income Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				
				logger.info("Session expired when user clicked Income button from admin page");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				String propPath = getServletContext().getRealPath("props/db.properties");
				String name = (String)session.getAttribute("name");
				logger.info("name of the employee "+name);
				EmployeeManager empManager = new EmployeeManager();
				String userRole = empManager.checkUserRole(propPath,name);
				logger.info("user role "+userRole);
				if(userRole.equalsIgnoreCase("Admin"))
				{
					LedgerManager ledgManager = new LedgerManager();
					ledgManager.calcIncome(propPath);
					List<TrialBal> income = ledgManager.getInc();
					logger.info("final income \n"+income);
					List<TrialBal> expenses = ledgManager.getExp();
					logger.info("final expenses \n"+expenses);
					String profitLoss = "Profit";
					double result = ledgManager.calcProfitLoss(income, expenses);
					
					if(result>0)
					{
						profitLoss = "Profit";
						logger.info("Profit is set");
					}
					else if(result<0)
					{
						profitLoss = "Loss";
					}
					
					req.setAttribute("income",income);
					req.setAttribute("expenses",expenses);
					req.setAttribute("date",LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-YYYY")));
					req.setAttribute("incomeval",String.format("%.2f",result));
					req.setAttribute("profitLoss",profitLoss.toUpperCase());
					
								
					resp.setHeader("resultfor", "myContainer");
					RequestDispatcher rd = req.getRequestDispatcher("jsps/income.jsp");
					rd.forward(req, resp);
				}
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in Income",e);
		}
		finally
		{
			
		}
	}
}
