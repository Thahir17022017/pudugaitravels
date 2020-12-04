package com.pudugaitravels.journaluihandler;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.db.LedgerManager;

public class DeleteJournal extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DeleteJournal.class);
	private String deleteId;
	private String trType;
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			logger.info("Running in DeleteJournal Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				logger.info("Session expired when user clicked DeleteJournal button from admin page");
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
					deleteId = obj.getAsJsonPrimitive("deleteId").getAsString();
					trType = obj.getAsJsonPrimitive("trType").getAsString();
					
					logger.info("deleteId "+deleteId);
					logger.info("trType "+trType);
					
		
				}

				
				
				JsonObject respJson = new JsonObject();
				PrintWriter out = resp.getWriter();
				String propPath = getServletContext().getRealPath("props/db.properties");
				JournalManager journalMgr = new JournalManager();
				LedgerManager ledgMgr = new LedgerManager();
				int cnt = journalMgr.delJnl(propPath,deleteId, trType);
				ledgMgr.delLedger(propPath,deleteId);
				JsonArray creditArray = new JsonArray();
				JsonArray debitArray = new JsonArray();
				if(trType.equalsIgnoreCase("debit"))
				{
					List<String> creditIds=journalMgr.getCreditIds();
					for(String creditId:creditIds)
					{
						creditArray.add(creditId);
					
					}
				}
				else if(trType.equalsIgnoreCase("credit"))
				{
					List<String> debitIds=journalMgr.getDebitIds();
					for(String debitId:debitIds)
					{
						debitArray.add(debitId);
					
					}
				}
				if(cnt==0)
				{
					respJson.addProperty("delJournalMsg","Delete journal failure");
				}
				else
				{
					respJson.addProperty("delJournalMsg","Delete journal success");
					respJson.addProperty("deleteId",deleteId);
					respJson.addProperty("trType",trType);
					if(trType.equalsIgnoreCase("debit"))
					{
						respJson.add("creditIds",creditArray);
					}
					else if(trType.equalsIgnoreCase("credit"))
					{
						respJson.add("debitIds",debitArray);
					}
					logger.info("data has been stored");
				}
				out.print(respJson.toString());	
				logger.info("data went out");
				out.flush();				

			}
		}
		catch(Exception e)
		{
			logger.error("Exception in DeleteJournal",e);
		}
		finally
		{
			deleteId="";
			trType="";
			
			
		}
	}
	

}
