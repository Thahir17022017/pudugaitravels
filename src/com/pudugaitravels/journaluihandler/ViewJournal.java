package com.pudugaitravels.journaluihandler;

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

import com.pudugaitravels.db.AccountManager;
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.pojo.Journal;

public class ViewJournal extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(ViewJournal.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
				logger.info("Running in ViewJournal Post method");
				HttpSession session = req.getSession(false);
				// Set to expire far in the past.
				resp.setHeader("Expires", "0");
		
				// Set standard HTTP/1.1 no-cache headers.
				resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		
				// Set standard HTTP/1.0 no-cache header.
				resp.setHeader("Pragma", "no-cache");
				if(session == null)
				{
					
					logger.info("Session expired when user clicked ViewJournal button from admin page");
					RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
					rd.include(req, resp);
					
				}
				else
				{
					resp.setHeader("resultfor", "loadJnlRow");
					String propPath = getServletContext().getRealPath("props/db.properties");
					AccountManager empManager = new AccountManager();
					List<String> names= empManager.readAccounts(propPath);
					logger.info("names"+names);
					req.setAttribute("list",names);
					
					JournalManager jnrManager = new JournalManager();
					List<Journal> journals = jnrManager.readJnl(propPath);
					req.setAttribute("journals",journals);
					RequestDispatcher rd = req.getRequestDispatcher("jsps/view_journal.jsp");
					rd.forward(req, resp);
				}
		}
		catch(Exception e)
		{
			logger.error("Exception in ViewJournal",e);
		}
		finally
		{
			
		}
	}
}
