package com.pudugaitravels.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.pojo.Employee;

public class EmployeeManager {
	
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection conn2;
	private PreparedStatement pstmt2;
	
	final static Logger logger = LoggerFactory.getLogger(EmployeeManager.class);
	
	public EmployeeManager()
	{
		
	}

	public List<Employee> readEmployees(String path)
	{
		List<Employee> names = new ArrayList<Employee>();
		try {
			String readEmployees = "SELECT DISTINCT USER_NAME,ACTIVE_FLG FROM pudugaitr_pudugaitravels.TCOLKP_USER";
			synchronized (this) {
					
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				
				pstmt = conn.prepareStatement(readEmployees);
				
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					names.add(new Employee(rs.getString("USER_NAME"),rs.getString("ACTIVE_FLG")));
				}
				conn.commit();
			}
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in Read Employees",e1);
			}
			logger.error("Exception in Read Employees",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		return names;
	}

	public int checkIfEmpIsUnique(String path,String user) {
		// TODO Auto-generated method stub
		
		int res = 0;
		try {
			String readEmployees = "select count(USER_ID) AS USRCNT from pudugaitr_pudugaitravels.TCOLKP_USER where USER_NAME=?";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readEmployees);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				res = Integer.parseInt(rs.getString("USRCNT"));
			}
			conn.commit();
			}
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in checkIfEmpIsUnique",e1);
			}
			logger.error("Exception in checkIfEmpIsUnique",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		
		return res;
	}

	public int insertNewEmployee(String path,String user, String calcSaltStr, String calcHashValStr,String roles) {
		// TODO Auto-generated method stub
		int res = 0;
		int cnt = 0;
		try {
			String addEmployee1= "INSERT INTO pudugaitr_pudugaitravels.TCOLKP_USER_ROLES(USER_NAME,USER_ROLES) VALUES(?,?)";
			String addEmployee2 = "INSERT INTO pudugaitr_pudugaitravels.TCOLKP_USER(USER_NAME,USER_SALT,USER_PWD) VALUES(?,?,?);";
			
			synchronized (this) {
				
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(addEmployee2);
			pstmt2.setString(1,user);
			pstmt2.setString(2,calcSaltStr);
			pstmt2.setString(3,calcHashValStr);
			cnt = pstmt2.executeUpdate();
			conn2.commit();
			
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(addEmployee1);
			pstmt.setString(1,user);
			pstmt.setString(2,roles);
			res = pstmt.executeUpdate();
			conn.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				conn2.rollback();
				conn.rollback();
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insertNewEmployee",e1);
			}
			logger.error("Exception in insertNewEmployee",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		res=res+cnt;
		return res;
	}
	
	public String checkUserRole(String path,String user) 
	{
		String userRole="";
		
		try {
			String readUserRole = "Select DISTINCT USER_ROLES from pudugaitr_pudugaitravels.TCOLKP_USER_ROLES where USER_NAME=?";
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readUserRole);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				userRole = rs.getString("USER_ROLES");
			}
			conn.commit();
			}
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in checkUserRole",e1);
			}
			logger.error("Exception in checkUserRole",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		
		return userRole;
	}

	public int updateNewEmployee(String path,String user, String calcSaltStr, String calcHashValStr) {
		// TODO Auto-generated method stub
		int res = 0;
		try {
			
			String updateEmployee = "UPDATE pudugaitr_pudugaitravels.TCOLKP_USER SET USER_SALT=?,USER_PWD=? WHERE USER_NAME=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(updateEmployee);
			pstmt.setString(1,calcSaltStr);
			pstmt.setString(2,calcHashValStr);
			pstmt.setString(3,user);
			res = pstmt.executeUpdate();
			conn.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				
				conn.rollback();
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in updateNewEmployee",e1);
			}
			logger.error("Exception in updateNewEmployee",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			
			
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return res;
	}

	public int deleteEmployee(String path,String user) {
		// TODO Auto-generated method stub
		int res = 0;
		int cnt = 0;
		try {
			String deleteEmployee1 = "DELETE FROM pudugaitr_pudugaitravels.TCOLKP_USER_ROLES WHERE USER_NAME=?";
			String deleteEmployee2 = "DELETE FROM pudugaitr_pudugaitravels.TCOLKP_USER WHERE USER_NAME=?";
			
			synchronized (this){
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(deleteEmployee1);
			pstmt.setString(1,user);
			res = pstmt.executeUpdate();
			conn.commit();
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(deleteEmployee2);
			pstmt2.setString(1,user);
			cnt = pstmt2.executeUpdate();
			conn2.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				
				conn.rollback();
				conn2.rollback();
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in deleteEmployee",e1);
			}
			logger.error("Exception in deleteEmployee",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
		}
		res=res+cnt;
		return res;
	}

	public int activateUser(String path,String name, String activRes) {
		// TODO Auto-generated method stub
		int res = 0;
		
		try {
			String activateEmployee = "UPDATE pudugaitr_pudugaitravels.TCOLKP_USER SET ACTIVE_FLG=? WHERE USER_NAME=?";
			
			synchronized (this){			
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(activateEmployee);
			if(activRes.equalsIgnoreCase("false"))
			{
				pstmt.setString(1,"Y");
			}
			else if(activRes.equalsIgnoreCase("true"))
			{
				pstmt.setString(1,"N");
			}
			pstmt.setString(2,name);
			res = pstmt.executeUpdate();
			conn.commit();
			}
			
			
		}
		catch(Exception e)
		{
			try {
				
				conn.rollback();
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in activateUser",e1);
			}
			logger.error("Exception in activateUser",e);
		}
		finally {

			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
			
			
		}
		
		return res;
	}

	
}
