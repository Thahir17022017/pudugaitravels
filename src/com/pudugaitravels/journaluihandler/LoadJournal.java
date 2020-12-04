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
import com.pudugaitravels.pojo.ActJournal;



public class LoadJournal extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(LoadJournal.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			logger.info("Running in LoadJournal Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked on LoadJournal button in admin page ");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				String id = req.getHeader("detailType");
				logger.info("id to be loaded"+id);
				String propPath = getServletContext().getRealPath("props/db.properties");
				JournalManager journalMgr = new JournalManager();
				AccountManager accManager = new AccountManager();
				List<String> names= accManager.readAccounts(propPath);
				logger.info("names"+names);
				req.setAttribute("list",names);
				List<ActJournal> journals = journalMgr.loadJnl(propPath,id);
				resp.setHeader("resultfor", "loadJnlRow");
				req.setAttribute("journals",journals);
				RequestDispatcher rd = req.getRequestDispatcher("jsps/add_journal.jsp");
				rd.forward(req, resp);
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in LoadJournal",e);
		}
		finally
		{
			
		}
	}
}
