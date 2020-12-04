package com.pudugaitravels.ratesuihandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

public class CheckCumRatesHourly extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(CheckCumRatesHourly.class);
	private String currCode="";
	private String formatted = "";
	private String req_result = "";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			logger.info("Running in CheckCumRatesHourly Get method");
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
				String propPath = getServletContext().getRealPath("props/db.properties");
				String configPath = getServletContext().getRealPath("props/config.properties");
				PropertyManager propManager = new PropertyManager();
				propManager.loadProps(configPath);
				String apiKey = propManager.readSpecProp("com.pudugaitravels.pi");
				RateManager rateManager = new RateManager();
				currCode = rateManager.getCurrencyCode(propPath,countryName);
				Map<String,String> currCodes = rateManager.getAllCurrCodes(propPath);
				logger.info("currCode  :"+currCode);
				List<CumulativeCurrency> ctryResult = new ArrayList<CumulativeCurrency>();
				
				
				
					// Setting URL
					String url_str = "https://v3.exchangerate-api.com/bulk/"+apiKey+"/"+currCode;

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
					if(jsonobj.get("timestamp")!=null)
					{
						Long time_stamp = jsonobj.get("timestamp").getAsLong();
						Date date = new Date(time_stamp * 1000L);
						DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
						formatted  = format.format(date);
					}
					if(req_result.equals("success"))
					{
						
						
						JsonObject rates = jsonobj.get("rates").getAsJsonObject();
						for(Map.Entry<String,String> entry:currCodes.entrySet())
						{
							if(rates.get(entry.getKey())!=null)
							{
								String recCurrValue = rates.get(entry.getKey()).getAsString();
								logger.info(entry.getValue()+" : "+recCurrValue+" "+entry.getKey());
								CumulativeCurrency cumCurrVal = new CumulativeCurrency(entry.getValue(),recCurrValue+" "+entry.getKey());
								ctryResult.add(cumCurrVal);
							
								
							}
							
						}
						Collections.sort(ctryResult);
						req.setAttribute("ctryList",ctryResult);
						req.setAttribute("from",currCode);
						req.setAttribute("timestamp",formatted);
						req.setAttribute("result",req_result);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrlist.jsp");
						rd.forward(req, resp);
						
					}
					if(req_result.equals("failed"))
					{
						req.setAttribute("from",currCode);
						req.setAttribute("timestamp",formatted);
						req.setAttribute("result",req_result);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrlist.jsp");
						rd.forward(req, resp);
					}
					if(req_result.equals("error"))
					{
						req.setAttribute("from",currCode);
//						req.setAttribute("timestamp",formatted);
						req.setAttribute("result",req_result);
						resp.setHeader("resultfor", "thirdRow");
						RequestDispatcher rd = req.getRequestDispatcher("jsps/showcurrlist.jsp");
						rd.forward(req, resp);
					}
					
				
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in CheckCumRatesHourly",e);
			req.setAttribute("from",currCode);
			req.setAttribute("timestamp",formatted);
			req.setAttribute("result",req_result);
			resp.setHeader("resultfor", "thirdRow");
			RequestDispatcher rd = req.getRequestDispatcher("showcurrlist/showcurrlist.jsp");
			rd.forward(req, resp);
		}
		finally {
			
			currCode = "";
			formatted = "";
			req_result = "";
		}
		
	}

}
