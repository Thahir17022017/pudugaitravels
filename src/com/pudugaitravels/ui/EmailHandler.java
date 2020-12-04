package com.pudugaitravels.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pudugaitravels.db.DbHelper;
import com.pudugaitravels.email.EmailManager;



public class EmailHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(EmailHandler.class);
	private String name = "";
	private String phNumber = "";
	private String email = "";
	private String subject = "";
	private String message = "";
	private String token = "";
	private Boolean captchaRes = false;
	private double score = 0.0;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			
			logger.info("Received the request from enquiry form");
			StringBuilder jsonBuff = new StringBuilder();
			String line=null;
			BufferedReader readData = req.getReader();
			while((line=readData.readLine())!=null)
			{
				jsonBuff.append(line);
			}
//			DbHelper helper = new DbHelper();
//			helper.DbConnector();
			
			logger.info("Request JSON string :" + jsonBuff.toString());
			JsonParser parser = new JsonParser();
			JsonElement  element = parser.parse(jsonBuff.toString());
			
			if(element.isJsonObject())
			{
				logger.info("Json object : "+element.getAsJsonObject().toString());
				JsonObject obj = element.getAsJsonObject();
				logger.info("After Json object conversion : "+element.getAsJsonObject().toString());
				name = obj.getAsJsonPrimitive("name").getAsString();
				phNumber = obj.getAsJsonPrimitive("phNumber").getAsString();
				email = obj.getAsJsonPrimitive("email").getAsString();
				subject = obj.getAsJsonPrimitive("subject").getAsString();
				message = obj.getAsJsonPrimitive("message").getAsString();
				token = obj.getAsJsonPrimitive("token").getAsString();
				
				
				logger.info("name "+name);
				logger.info("phNumber "+phNumber);
				logger.info("email "+email);
				logger.info("subject "+subject);
				logger.info("message "+message);
				logger.info("token "+token);
				
			}
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			nvps.add(new BasicNameValuePair("secret", "6LcBoZ0UAAAAAN3uW1IfgaAlSYveI05Tp4Ua9CF0"));
			nvps.add(new BasicNameValuePair("response", token));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);
			try {
			    System.out.println(response2.getStatusLine());
			    HttpEntity entity2 = response2.getEntity();
			    InputStream respStream = entity2.getContent();
			    String result = IOUtils.toString(respStream, StandardCharsets.UTF_8);
			    System.out.println("Result from Google "+result);
			    JsonParser jsonParser = new JsonParser();
			    JsonObject jo = (JsonObject)jsonParser.parse(result);
			    System.out.println("After converting result from google"+jo.get("success"));
			    captchaRes = jo.get("success").getAsBoolean();
			    if(captchaRes==true)
			    {
			    	score = jo.get("score").getAsDouble();
			    }	
			    // do something useful with the response body
			    // and ensure it is fully consumed
			    EntityUtils.consume(entity2);
			} finally {
			    response2.close();
			}
			if(captchaRes==true && score>0.5)
			{
				String propPath = getServletContext().getRealPath("props/config.properties");
				EmailManager emailManager = new EmailManager(propPath);
				boolean emailResult = emailManager.sendEmail(name, email, phNumber, subject,message);
				if(emailResult==true)
				{
					JsonObject respErrJson = new JsonObject();
					respErrJson.addProperty("successMessage","Message sent successfully");
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					out.print(respErrJson.toString());
					out.flush();
				}
				if(emailResult==false)
				{
					JsonObject respErrJson = new JsonObject();
					respErrJson.addProperty("emailErrMessage","Failed to send message! Retry after sometime");
					PrintWriter out = resp.getWriter();
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					out.print(respErrJson.toString());
					out.flush();
				}
				
			}
			else
			{
				JsonObject respErrJson = new JsonObject();
				respErrJson.addProperty("errorMessage","Captcha Error!!! Re-Enter form again");
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(respErrJson.toString());
				out.flush();
			}
			
		}
		catch(Exception e)
		{
			logger.error("Exception in EmailHandler",e);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req,resp);
	}

}
