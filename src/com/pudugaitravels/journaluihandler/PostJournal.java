package com.pudugaitravels.journaluihandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
import com.pudugaitravels.db.JournalManager;
import com.pudugaitravels.db.LedgerManager;

public class PostJournal extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(PostJournal.class);
	private String date="";
	private String journalDesc="";
	private String journalAcc="";
	private String drcrType="";
	private String amount="";
	private String rate="";
	private String dbCrAmt="";
	private String dbCrId="";
	private String oldDate="";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in PostJournal Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked PostJournal button from admin page");
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
					date = obj.getAsJsonPrimitive("date").getAsString();
					journalDesc = obj.getAsJsonPrimitive("journalDesc").getAsString();
					journalAcc = obj.getAsJsonPrimitive("journalAcc").getAsString();
					drcrType = obj.getAsJsonPrimitive("drcrType").getAsString();
					amount = obj.getAsJsonPrimitive("amount").getAsString();
					rate = obj.getAsJsonPrimitive("rate").getAsString();
					dbCrAmt = obj.getAsJsonPrimitive("dbCrAmt").getAsString();
					dbCrId = obj.getAsJsonPrimitive("dbCrId").getAsString();
					logger.info("date "+date);
					logger.info("journalDesc "+journalDesc);
					logger.info("journalAcc "+journalAcc);
					logger.info("drcrType "+drcrType);
					logger.info("amount "+amount);
					logger.info("rate "+rate);
					logger.info("dbCrAmt "+dbCrAmt);
					logger.info("dbCrId "+dbCrId);
		
				}
//				resp.setHeader("resultfor", "myContainer");
//				req.setAttribute("list",names);
				
				String[] splitDates= date.split("-");
				oldDate=date;
				date = splitDates[2]+"-"+splitDates[1]+"-"+splitDates[0];
				logger.info("reverse date "+date);
				JsonObject respJson = new JsonObject();
				PrintWriter out = resp.getWriter();
				String propPath = getServletContext().getRealPath("props/db.properties");
				JournalManager journalMgr = new JournalManager();
				LedgerManager ledgMgr = new LedgerManager();
				if((drcrType.equalsIgnoreCase("credit")) && (dbCrId.equalsIgnoreCase("")))
				{
					// do credit entry
					logger.info("normal credit entry");
					String journalId = journalMgr.insCrJnl(propPath,date, journalDesc, journalAcc,  amount, rate, dbCrAmt);
					
					
					if(journalId.equalsIgnoreCase(""))
					{
						respJson.addProperty("postJournalMsg","Insert credit journal failure");
					}
					else
					{
						respJson.addProperty("postJournalMsg","Insert credit journal success");
						respJson.addProperty("date",oldDate);
						respJson.addProperty("journalDesc",journalDesc);
						respJson.addProperty("journalAcc",journalAcc);
						respJson.addProperty("drcrType",drcrType);
						respJson.addProperty("amount",amount);
						respJson.addProperty("rate",rate);
						respJson.addProperty("dbCrAmt",dbCrAmt);
						respJson.addProperty("journalId",journalId);
						logger.info("credit data inserted properly ");
					}

					
				}
				else if((drcrType.equalsIgnoreCase("credit")) && (!dbCrId.equalsIgnoreCase("")))
				{
					logger.info("credit entry with relation");
					String journalId = journalMgr.insCrJnlRl(propPath,date, journalDesc, journalAcc,  amount, rate, dbCrAmt,dbCrId);
					if(!journalId.equalsIgnoreCase("")) {
						ledgMgr.insCrLedger(propPath,journalId,dbCrId,date, journalDesc, journalAcc, amount, rate, dbCrAmt);
					}
					
					if(journalId.equalsIgnoreCase("Not a valid debit id"))
					{
						respJson.addProperty("postJournalMsg","Not a valid debit id");
					}
					else if(journalId.equalsIgnoreCase(""))
					{
						respJson.addProperty("postJournalMsg","Insert credit journal failure");
					}
					else
					{
						respJson.addProperty("postJournalMsg","Insert credit journal success");
						respJson.addProperty("date",oldDate);
						respJson.addProperty("journalDesc",journalDesc);
						respJson.addProperty("journalAcc",journalAcc);
						respJson.addProperty("drcrType",drcrType);
						respJson.addProperty("amount",amount);
						respJson.addProperty("rate",rate);
						respJson.addProperty("dbCrId", dbCrId);
						respJson.addProperty("dbCrAmt",dbCrAmt);
						respJson.addProperty("journalId",journalId);
						logger.info("credit data inserted properly ");
					}
				}
				else if((drcrType.equalsIgnoreCase("debit")) && (dbCrId.equalsIgnoreCase("")))
				{
					// do credit entry
					logger.info("normal debit entry");
					String journalId = journalMgr.insDrJnl(propPath,date, journalDesc, journalAcc,  amount, rate, dbCrAmt);
					if(!journalId.equalsIgnoreCase(""))
					{
						ledgMgr.insDrLedger(propPath,journalId,date, journalDesc, journalAcc,  amount, rate, dbCrAmt);
					}
					
					if(journalId.equalsIgnoreCase(""))
					{
						respJson.addProperty("postJournalMsg","Insert debit journal failure");
					}
					else
					{
						respJson.addProperty("postJournalMsg","Insert debit journal success");
						respJson.addProperty("date",oldDate);
						respJson.addProperty("journalDesc",journalDesc);
						respJson.addProperty("journalAcc",journalAcc);
						respJson.addProperty("drcrType",drcrType);
						respJson.addProperty("amount",amount);
						respJson.addProperty("rate",rate);
						respJson.addProperty("dbCrAmt",dbCrAmt);
						respJson.addProperty("journalId",journalId);
						logger.info("debit data inserted properly ");
					}
				}
				else if((drcrType.equalsIgnoreCase("debit")) && (!dbCrId.equalsIgnoreCase("")))
				{
					logger.info("debit entry with relation");
					String journalId = journalMgr.insDrJnlRel(propPath,date, journalDesc, journalAcc,  amount, rate, dbCrAmt,dbCrId);
					if(journalId.equalsIgnoreCase(""))
					{
						respJson.addProperty("postJournalMsg","Insert debit journal failure");
					}
					else
					{
						respJson.addProperty("postJournalMsg","Insert debit journal success");
						respJson.addProperty("date",oldDate);
						respJson.addProperty("journalDesc",journalDesc);
						respJson.addProperty("journalAcc",journalAcc);
						respJson.addProperty("drcrType",drcrType);
						respJson.addProperty("amount",amount);
						respJson.addProperty("rate",rate);
						respJson.addProperty("dbCrId", dbCrId);
						respJson.addProperty("dbCrAmt",dbCrAmt);
						respJson.addProperty("journalId",journalId);
						logger.info("credit data inserted properly ");
					}
				}
				out.print(respJson.toString());	
				logger.info("data went out");
				out.flush();
//								

			}
		}
		catch(Exception e)
		{
			logger.error("Exception in PostJournal",e);
		}
		finally
		{
			date="";
			journalDesc="";
			journalAcc="";
			drcrType="";
			amount="";
			rate="";
			dbCrAmt="";
			dbCrId="";
			oldDate="";
		}
		
	}

}
