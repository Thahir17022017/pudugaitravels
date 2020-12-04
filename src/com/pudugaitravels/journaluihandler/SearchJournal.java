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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.pojo.Journal;

public class SearchJournal extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SearchJournal.class);
	private String date="";
	private String accType="";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in SearchJournal Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked SearchJournal button from admin page");
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
					date = obj.getAsJsonPrimitive("searchDate").getAsString();
					accType = obj.getAsJsonPrimitive("searchAcc").getAsString();
					
					logger.info("date "+date);
					logger.info("accType "+accType);
					
		
				}
//				resp.setHeader("resultfor", "myContainer");
//				req.setAttribute("list",names);
				String[] splitDates= date.split("-");
				date = splitDates[2]+"-"+splitDates[1]+"-"+splitDates[0];
				logger.info("reverse date "+date);
				
				JsonObject respJson = new JsonObject();
				JsonArray respJsonArr = new JsonArray();
				PrintWriter out = resp.getWriter();
				String propPath = getServletContext().getRealPath("props/db.properties");
				
				JournalManager journalMgr = new JournalManager();
				List<Journal> result = journalMgr.searchJnl(propPath,date,accType);
				if(result.size()==0)
				{
					respJson.addProperty("searchJournalMsg","No records found");
				}
				if(result.size()>0)
				{
					respJson.addProperty("searchJournalMsg","Records found");
					for(Journal entry:result)
					{
						
						respJsonArr.add(entry.getJournalId()+
								":"+entry.getJournalDt()+
								":"+entry.getJournalDesc()+
								":"+entry.getJournalAcc()+
								":"+entry.getJournalDrAmt()+
								":"+entry.getJournalCrAmt()+
								":"+entry.getJournalDrCrType());
						
						
					}
					respJson.add("search_results",respJsonArr);
					
				}
				out.print(respJson.toString());	
				logger.info("data went out");
				out.flush();
//								

			}
		}
		catch(Exception e)
		{
			logger.error("Exception in SearchJournal",e);
		}
		finally
		{
			date="";
			accType="";
		}
		
	}

}
