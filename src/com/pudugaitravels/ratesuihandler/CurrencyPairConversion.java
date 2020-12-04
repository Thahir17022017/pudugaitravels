package com.pudugaitravels.ratesuihandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.pudugaitravels.db.RateManager;
import com.pudugaitravels.pojo.CumulativeCurrency;
import com.pudugaitravels.prop.PropertyManager;

public class CurrencyPairConversion extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(CurrencyPairConversion.class);
	private String srcCurrCode="";
	private String destCurrCode="";

	private String req_result = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			logger.info("Running in CurrencyPairConversion Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked on CheckCumRatesHourly rate button in admin page ");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
				String countryName = req.getHeader("detailType");
				logger.info("Country Name :"+countryName);
				String[] ctryPairs = countryName.split(":");
				String propPath = getServletContext().getRealPath("props/db.properties");
				String configPath = getServletContext().getRealPath("props/config.properties");
				PropertyManager propManager = new PropertyManager();
				propManager.loadProps(configPath);
				String apiKey = propManager.readSpecProp("com.pudugaitravels.pi");
				RateManager rateManager = new RateManager();
				srcCurrCode = rateManager.getCurrencyCode(propPath,ctryPairs[0]);
				destCurrCode = rateManager.getCurrencyCode(propPath,ctryPairs[1]);
				
				logger.info("srcCurrCode  :"+srcCurrCode);
				logger.info("destCurrCode  :"+destCurrCode);
				
				
				
				
				
					// Setting URL
					String url_str = "https://v3.exchangerate-api.com/pair/"+apiKey+"/"+srcCurrCode+"/"+destCurrCode;

					// Making Request
					URL url = new URL(url_str);
					HttpURLConnection request = (HttpURLConnection) url.openConnection();
					request.connect();

					// Convert to JSON
					JsonParser jp = new JsonParser();
					JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
					JsonObject jsonobj = root.getAsJsonObject();

					// Accessing object
					req_result  = jsonobj.get("result").getAsString();
					logger.info("Response from End Point :"+jsonobj.toString());
					
					if(req_result.equals("success"))
					{
						
						
						String fromCurr = jsonobj.get("from").getAsString();
						String toCurr = jsonobj.get("to").getAsString();
						String rate = jsonobj.get("rate").getAsString();
						
						req.setAttribute("result",req_result);
						req.setAttribute("from",fromCurr);
						req.setAttribute("to",toCurr);
						req.setAttribute("rate",rate);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrpairlist.jsp");
						rd.forward(req, resp);
						
					}
					if(req_result.equals("failed"))
					{
						
						req.setAttribute("result",req_result);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrpairlist.jsp");
						rd.forward(req, resp);
					}
					if(req_result.equals("error"))
					{
						
						req.setAttribute("result",req_result);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrpairlist.jsp");
						rd.forward(req, resp);
					}
					
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in CurrencyPairConversion",e);
			req.setAttribute("result",req_result);
			resp.setHeader("resultfor", "thirdRow");
			RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrpairlist.jsp");
			rd.forward(req, resp);
		}
		finally {
			
			req_result = "";
			srcCurrCode = "";
			destCurrCode = "";
		}
		
	}

}
