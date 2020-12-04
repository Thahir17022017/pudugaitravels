package com.pudugaitravels.accounthandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.pudugaitravels.db.AccountManager;
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.db.LedgerManager;

public class CloseAccounts extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate="";
	
	
	final static Logger logger = LoggerFactory.getLogger(CloseAccounts.class);


	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in CloseAccounts Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked CloseAccounts button from admin page");
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
					fromDate = obj.getAsJsonPrimitive("closeAccDate").getAsString();
					logger.info("fromDate "+fromDate);
					
				}
				String propPath = getServletContext().getRealPath("props/db.properties");
				AccountManager accManager = new AccountManager();
				List<String> accounts = accManager.getAccounts(propPath);
				
				JsonObject respJson = new JsonObject();
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				if(accounts.size() == 0)
				{
					
					respJson.addProperty("closeAccMsg","No accounts are present for closure");
					
				}
				else
				{
					String[] splitDates= fromDate.split("-");
					String date = splitDates[2]+"-"+splitDates[1]+"-"+splitDates[0];
					LedgerManager ledgManager = new LedgerManager();
					int cnt = ledgManager.deleteLedgEntries(propPath,accounts,date);
					
					
					if(cnt == 0)
					{
						respJson.addProperty("closeAccMsg","Error in Ledger Delete");
					}
					else
					{
						respJson.addProperty("closeAccMsg","Accounts are closed");
					}
					
					
				}
				out.print(respJson.toString());
				out.flush();
				
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in CloseAccounts",e);
		}
		finally
		{
			fromDate="";
		}
	}

}
