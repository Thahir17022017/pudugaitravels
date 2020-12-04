package com.pudugaitravels.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
import com.pudugaitravels.db.JournalManager;


public class DownloadJournal extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(DownloadJournal.class);
	private String searchAcc;
	private String searchDate;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		try
		{
			logger.info("Running in DownloadJournal Post method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			
			if(session == null)
			{
				
				logger.info("Session expired when user clicked DownloadJournal button from admin page");
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
					searchAcc = obj.getAsJsonPrimitive("searchAcc").getAsString();
					searchDate = obj.getAsJsonPrimitive("searchDate").getAsString();
					
					logger.info("searchDate "+searchDate);
					logger.info("searchAcc "+searchAcc);
				}
				
				String[] splitDates1= searchDate.split("-");
				String revSearchDate = splitDates1[2]+"-"+splitDates1[1]+"-"+splitDates1[0];
				logger.info("reverse searchDate "+revSearchDate);
				
				
				
				String propPath = getServletContext().getRealPath("props/db.properties");
				JournalManager ledgerManager = new JournalManager();
				StringBuffer sb = ledgerManager.dwnldJnl(propPath,revSearchDate,searchAcc);
				
				resp.setContentType("application/octet-stream");
				if(!searchDate.equalsIgnoreCase("") && !searchAcc.equalsIgnoreCase(""))
				{
					resp.setHeader("Content-Disposition",
							"attachment;filename=Journal_"+searchDate+"_"+searchAcc+".csv");
				}
				else if(!searchDate.equalsIgnoreCase("") && searchAcc.equalsIgnoreCase(""))
				{
					resp.setHeader("Content-Disposition",
							"attachment;filename=Journal_"+searchDate+".csv");
				}
				
				resp.setHeader("resultfor", "downloadJnlfile");
				
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
			logger.error("Exception in DownloadJournal",e);
		}
		finally {
			searchDate="";
			searchAcc="";
		}
	}
}
