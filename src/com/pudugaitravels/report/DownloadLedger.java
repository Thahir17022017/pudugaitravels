package com.pudugaitravels.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pudugaitravels.db.LedgerManager;
import com.pudugaitravels.pojo.Ledger;

public class DownloadLedger extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DownloadLedger.class);
	private String fromLedgerDate;
	private String toLedgerDate;
	private String ledgerAcc;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in DownloadLedger Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				
				logger.info("Session expired when user clicked DownloadLedger button from admin page");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				
				String jsonString = req.getHeader("detailType");
				logger.info("jsonString  :"+jsonString);
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(jsonString.toString());
				if(element instanceof JsonObject)
				{
					JsonObject obj = (JsonObject)element;
					logger.info("JsonObject  after conversion :"+obj);
					fromLedgerDate = obj.getAsJsonPrimitive("fromLedgerDate").getAsString();
					toLedgerDate = obj.getAsJsonPrimitive("toLedgerDate").getAsString();
					ledgerAcc = obj.getAsJsonPrimitive("ledgerAcc").getAsString();
					logger.info("fromLedgerDate "+fromLedgerDate);
					logger.info("toLedgerDate "+toLedgerDate);
					logger.info("ledgerAcc "+ledgerAcc);
				}
				
				String[] splitDates1= fromLedgerDate.split("-");
				String convFromLedgDate = splitDates1[2]+"-"+splitDates1[1]+"-"+splitDates1[0];
				logger.info("reverse fromLedgerDate "+convFromLedgDate);
				
				String[] splitDates2= toLedgerDate.split("-");
				String convToLedgDate = splitDates2[2]+"-"+splitDates2[1]+"-"+splitDates2[0];
				logger.info("reverse toLedgerDate "+convToLedgDate);
				
				String propPath = getServletContext().getRealPath("props/db.properties");
				LedgerManager ledgerManager = new LedgerManager();
				StringBuffer sb = ledgerManager.downloadLedger(propPath,convFromLedgDate,convToLedgDate,ledgerAcc);
				
				resp.setContentType("application/octet-stream");
				resp.setHeader("Content-Disposition",
				"attachment;filename=Ledger_"+ledgerAcc+"_"+fromLedgerDate+"_"+toLedgerDate+".csv");
				resp.setHeader("resultfor", "downloadfile");
				
				InputStream in = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));
				ServletOutputStream out = resp.getOutputStream();

				byte[] outputByte = new byte[4096];
				//copy binary contect to output stream
				while(in.read(outputByte, 0, 4096) != -1)
				{
					out.write(outputByte, 0, 4096);
				}
				in.close();
				out.flush();
				out.close();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in DownloadLedger",e);
		}
		finally {
			fromLedgerDate="";
			toLedgerDate="";
			ledgerAcc="";
		}
	}
	
	
}
