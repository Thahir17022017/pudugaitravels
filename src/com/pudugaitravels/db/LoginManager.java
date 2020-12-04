package com.pudugaitravels.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginManager {
	
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	final static Logger logger = LoggerFactory.getLogger(LoginManager.class);
	
	public LoginManager()
	{
		
	}

	public String checkUser(String username,String password)
	{
		return "";
	}

	public boolean checkUserinDb(String path,String user) {
		// TODO Auto-generated method stub
		String actUserName="";
		try {
			synchronized(this)
			{
				String readUser = "SELECT DISTINCT USER_NAME FROM pudugaitr_pudugaitravels.TCOLKP_USER where USER_NAME=?";
				logger.info("check user in db");
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				
				pstmt = conn.prepareStatement(readUser);
				pstmt.setString(1,user);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					actUserName = rs.getString("USER_NAME");
				}
				conn.commit();
			}
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in Login manager",e1);
			}
			logger.error("Exception in Login manager",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		if(actUserName=="")
		{
			return false;
		}
		
		return true;
		
	}

	public String getSaltValue(String path,String user) {
		// TODO Auto-generated method stub
		String saltVal = "";
		try {
			
			String readSalt = "SELECT DISTINCT USER_SALT FROM pudugaitr_pudugaitravels.TCOLKP_USER where USER_NAME=?";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readSalt);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				saltVal = rs.getString("USER_SALT");
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
				logger.error("Exception in rolling back in getSaltValue",e1);
			}
			logger.error("Exception in getSaltValue",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return saltVal;
		
		
	}

	public String getHashVal(String path,String user) {
		// TODO Auto-generated method stub
		String hashVal = "";
		try {
			String readHash = "SELECT DISTINCT USER_PWD FROM pudugaitr_pudugaitravels.TCOLKP_USER where USER_NAME=?";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readHash);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				hashVal = rs.getString("USER_PWD");
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
				logger.error("Exception in rolling back in getHashVal",e1);
			}
			logger.error("Exception in getHashVal",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return hashVal;
	}

	public String checkRoles(String path,String user) {
		// TODO Auto-generated method stub
		String roleVal = "";
		try {
			String readRole = "SELECT DISTINCT USER_ROLES FROM pudugaitr_pudugaitravels.TCOLKP_USER_ROLES where USER_NAME=?";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readRole);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				roleVal = rs.getString("USER_ROLES");
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
				logger.error("Exception in rolling back in checkRoles",e1);
			}
			logger.error("Exception in checkRoles",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return roleVal;
	}

	public String getUserActiveStatus(String path,String user) {
		// TODO Auto-generated method stub
		String activeStatus = "";
		try {
			String checkActiveStatus = "SELECT DISTINCT ACTIVE_FLG FROM pudugaitr_pudugaitravels.TCOLKP_USER where USER_NAME=?";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(checkActiveStatus);
			pstmt.setString(1,user);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				activeStatus = rs.getString("ACTIVE_FLG");
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
				logger.error("Exception in rolling back in getUserActiveStatusr",e1);
			}
			logger.error("Exception in getUserActiveStatus of Login manager",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return activeStatus;
	}
}
