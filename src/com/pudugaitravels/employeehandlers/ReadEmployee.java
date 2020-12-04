package com.pudugaitravels.employeehandlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pudugaitravels.db.EmployeeManager;
import com.pudugaitravels.pojo.Employee;



public class ReadEmployee extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(ReadEmployee.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			logger.info("Running in ReadEmployee Get method");
			HttpSession session = req.getSession(false);
			// Set to expire far in the past.
			resp.setHeader("Expires", "0");
	
			// Set standard HTTP/1.1 no-cache headers.
			resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	
			// Set standard HTTP/1.0 no-cache header.
			resp.setHeader("Pragma", "no-cache");
			if(session == null)
			{
				
				logger.info("Session expired when user clicked ReadEmployee button from admin page");
				RequestDispatcher rd = req.getRequestDispatcher("adminlogin/adminlogin.html");
				rd.include(req, resp);
				
			}
			else
			{
//				// Set to expire far in the past.
//				resp.setHeader("Expires", "0");
//		
//				// Set standard HTTP/1.1 no-cache headers.
//				resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
//		
//				// Set standard HTTP/1.0 no-cache header.
//				resp.setHeader("Pragma", "no-cache");
				
				String propPath = getServletContext().getRealPath("props/db.properties");
				EmployeeManager empManager = new EmployeeManager();
				List<Employee> names= empManager.readEmployees(propPath);
				logger.info("names"+names);
//				JsonObject respJson = new JsonObject();
//				JsonArray  respNamesArr = new JsonArray();
//				
//				for(String name:names)
//				{
//					respNamesArr.add(name);
//					
//				}
//				logger.info("name values "+respNamesArr.toString());
//				respJson.add("names",respNamesArr);
				resp.setHeader("resultfor", "myContainer");
				req.setAttribute("list",names);
				RequestDispatcher rd = req.getRequestDispatcher("jsps/employee_config.jsp");
				rd.forward(req, resp);
				
//				PrintWriter out = resp.getWriter();
//				resp.setContentType("application/json");
//				resp.setCharacterEncoding("UTF-8");
//				out.print(respJson.toString());
//				out.flush();
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in ReadEmployee",e);
		}
		finally
		{
			
		}
	}

}
