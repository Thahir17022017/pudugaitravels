package com.pudugaitravels.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RateManager {
	

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection conn2;
	private PreparedStatement pstmt2;
	
	final static Logger logger = LoggerFactory.getLogger(RateManager.class);
	
	public RateManager()
	{
		
	}
	
	public List<String> readCountryNames(String path)
	{
		List<String> names = new ArrayList<String>();
		try {
			String readCountry = "SELECT DISTINCT CTRY_NAME FROM pudugaitr_pudugaitravels.TCOLKP_CTRY_CODES";
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readCountry);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				names.add(rs.getString("CTRY_NAME"));
			}
			conn.commit();
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in readCountryNames",e1);
			}
			logger.error("Exception in readCountryNames",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		return names;
	}

	public String getCurrencyCode(String path,String countryName) {
		// TODO Auto-generated method stub
		
		String currCode = "";
		try {
			String readCountryCode = "SELECT DISTINCT CURR_CODE FROM pudugaitr_pudugaitravels.TCOLKP_CTRY_CODES WHERE CTRY_NAME=?";
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readCountryCode);
			pstmt.setString(1,countryName);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				currCode = rs.getString("CURR_CODE");
			}
			conn.commit();
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in getCurrencyCode",e1);
			}
			logger.error("Exception in getCurrencyCode",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return currCode;
	}

	public Map<String,String> getAllCurrCodes(String path) {
		// TODO Auto-generated method stub
		Map<String,String> currCodes = new HashMap<String,String>();
		try {
			String readCountryCode = "SELECT DISTINCT CURR_CODE,CTRY_NAME FROM pudugaitr_pudugaitravels.TCOLKP_CTRY_CODES";
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(readCountryCode);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				currCodes.put(rs.getString("CURR_CODE"),rs.getString("CTRY_NAME"));
			}
			conn.commit();
		}
		catch(Exception e)
		{
			try {
				conn.rollback();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in getAllCurrCodes",e1);
			}
			logger.error("Exception in getAllCurrCodes",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return currCodes;
	}
}
