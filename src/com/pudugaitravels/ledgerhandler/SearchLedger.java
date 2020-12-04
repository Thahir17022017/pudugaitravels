package com.pudugaitravels.ledgerhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
import com.pudugaitravels.db.LedgerManager;
import com.pudugaitravels.pojo.Journal;
import com.pudugaitravels.pojo.Ledger;

public class SearchLedger  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(SearchLedger.class);
	private String fromLedgerDate="";
	private String oldFromLedgerDate="";
	private String oldToLedgerDate="";
	private String toLedgerDate="";
	private String ledgerAcc="";
	
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in SearchLedger Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				
				logger.info("Session expired when user clicked SearchLedger button from admin page");
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
					fromLedgerDate = obj.getAsJsonPrimitive("fromLedgerDate").getAsString();
					toLedgerDate = obj.getAsJsonPrimitive("toLedgerDate").getAsString();
					ledgerAcc = obj.getAsJsonPrimitive("ledgerAcc").getAsString();
					logger.info("fromLedgerDate "+fromLedgerDate);
					logger.info("toLedgerDate "+toLedgerDate);
					logger.info("ledgerAcc "+ledgerAcc);
				
		
				}
				String[] splitDates1= fromLedgerDate.split("-");
				oldFromLedgerDate=fromLedgerDate;
				fromLedgerDate = splitDates1[2]+"-"+splitDates1[1]+"-"+splitDates1[0];
				logger.info("reverse fromLedgerDate "+fromLedgerDate);
				
				String[] splitDates2= toLedgerDate.split("-");
				oldToLedgerDate=toLedgerDate;
				toLedgerDate = splitDates2[2]+"-"+splitDates2[1]+"-"+splitDates2[0];
				logger.info("reverse toLedgerDate "+toLedgerDate);
				
				String propPath = getServletContext().getRealPath("props/db.properties");
				JsonArray respJsonArr = new JsonArray();
				JsonObject respJson = new JsonObject();
				PrintWriter out = resp.getWriter();
				
				LedgerManager ledgerManager = new LedgerManager();
				List<Ledger> records = ledgerManager.searchLedger(propPath,fromLedgerDate,toLedgerDate,ledgerAcc);
				String  ledgAccBalance = ledgerManager.calcLedgBal(propPath,fromLedgerDate,toLedgerDate,ledgerAcc);
				
				
				
				if(records.size()==0)
				{
					respJson.addProperty("searchLedgerMsg","No records found");
				}
				if(records.size()>0)
				{
					respJson.addProperty("searchLedgerMsg","Records found");
					for(Ledger entry:records)
					{
						
						respJsonArr.add(entry.getLedgerDate()+
								":"+entry.getLedgerDescription()+
								":"+entry.getLedgerDrAmt()+
								":"+entry.getLedgerCrAmt()+
								":"+entry.getTrType());
						
						
					}
					respJson.add("search_results",respJsonArr);
					logger.error("records packaged properly"+respJsonArr);
				}
				
				respJson.addProperty("ledgerAcc",ledgerAcc);
				respJson.addProperty("fromLedgerDate",oldFromLedgerDate);
				respJson.addProperty("toLedgerDate",oldToLedgerDate);
				respJson.addProperty("ledgerAccBal",ledgAccBalance);
				
				out.print(respJson.toString());	
				logger.info("data went out");
				out.flush();

			}
		}
		catch(Exception e)
		{
			logger.error("Exception in SearchLedger",e);
		}
		finally
		{
			fromLedgerDate="";
			toLedgerDate="";
			ledgerAcc="";
			oldFromLedgerDate="";
			oldToLedgerDate="";
		}
	}

}
