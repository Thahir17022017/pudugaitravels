package com.pudugaitravels.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountManager {
	
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection conn2;
	private PreparedStatement pstmt2;
	
	final static Logger logger = LoggerFactory.getLogger(AccountManager.class);
	
	public AccountManager()
	{
	
	}

	public List<String> readAccounts(String path)
	{
		List<String> names = new ArrayList<String>();
		try {
			synchronized(this)
			{
				String readAccounts = "SELECT DISTINCT ACCOUNT_NAME,ACCOUNT_TYPE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT";
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				
				pstmt = conn.prepareStatement(readAccounts);
				
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					names.add(rs.getString("ACCOUNT_NAME")+" - "+rs.getString("ACCOUNT_TYPE"));
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
				logger.error("Exception in rolling back in readAccounts",e1);
			}
			logger.error("Exception in readAccounts",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		return names;
	}

	public int checkIfAccIsUnique(String path,String account) {
		int res = 0;
		try {
			String readAccounts = "select count(ACCOUNT_NAME) AS ACC_CNT from pudugaitr_pudugaitravels.TCOLKP_ACCOUNT where ACCOUNT_NAME=?";
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			synchronized(this)
			{
				pstmt = conn.prepareStatement(readAccounts);
				pstmt.setString(1,account);
				rs = pstmt.executeQuery();
				while(rs.next())
				{
					res = Integer.parseInt(rs.getString("ACC_CNT"));
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
				logger.error("Exception in rolling back in checkIfAccIsUnique",e1);
			}
			logger.error("Exception in checkIfAccIsUnique",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		
		return res;
	}

	public int insertNewAccount(String path,String account,String accountType) {
		// TODO Auto-generated method stub
		int res = 0;
	
		try {
			String addAccount= "INSERT INTO pudugaitr_pudugaitravels.TCOLKP_ACCOUNT(ACCOUNT_NAME,ACCOUNT_TYPE) VALUES(?,?)";
			
			
			synchronized(this)
			{
				conn2 = DbHelper.DbConnector(path);
				conn2.setAutoCommit(false);
				pstmt2 = conn2.prepareStatement(addAccount);
				pstmt2.setString(1,account);
				pstmt2.setString(2,accountType);
				res = pstmt2.executeUpdate();
				conn2.commit();
			}
			
			
		}
		catch(Exception e)
		{
			try {
				conn2.rollback();
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insertNewAccount",e1);
			}
			logger.error("Exception in insertNewAccount",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
			
		}
		
		return res;
	}

	public int updateAccount(String path,String user, String oldAccName) {
		// TODO Auto-generated method stub
		int res = 0;
		
		try {
			String updateAccount= "UPDATE pudugaitr_pudugaitravels.TCOLKP_ACCOUNT SET ACCOUNT_NAME=? where ACCOUNT_NAME=?";
			
			
			synchronized(this)
			{
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(updateAccount);
			pstmt2.setString(1,user);
			pstmt2.setString(2,oldAccName);
			res = pstmt2.executeUpdate();
			conn2.commit();
			}
			
			
		}
		catch(Exception e)
		{
			try {
				conn2.rollback();
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in updateAccount",e1);
			}
			logger.error("Exception in updateAccount",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
			
		}
		
		return res;
	}

	public int deleteAccount(String path,String user) {
		// TODO Auto-generated method stub
		int res = 0;
		
		try {
			String deleteAccount= "DELETE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT WHERE ACCOUNT_NAME=?";
			
			
			synchronized(this)
			{
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(deleteAccount);
			pstmt2.setString(1,user);
			
			res = pstmt2.executeUpdate();
			conn2.commit();
			}
			
			
		}
		catch(Exception e)
		{
			try {
				conn2.rollback();
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in deleteAccount",e1);
			}
			logger.error("Exception in deleteAccount",e);
		}
		finally {
//			DbHelper.resultSetClose(rs);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
			
		}
		
		return res;
	}

	public List<String> getAccounts(String path) {
		// TODO Auto-generated method stub
		List<String> accountNames = new ArrayList<String>();
		try {
		String getAccounts = "SELECT DISTINCT ACCOUNT_NAME,ACCOUNT_TYPE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT";
		synchronized(this) {
		conn = DbHelper.DbConnector(path);
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(getAccounts);
		rs = pstmt.executeQuery();
		while(rs.next())
		{
			if(rs.getString("ACCOUNT_TYPE").toUpperCase().equals("ASSETS"))
			{
				accountNames.add(rs.getString("ACCOUNT_NAME").toUpperCase()+" - "+rs.getString("ACCOUNT_TYPE").toUpperCase());
			}
			if(rs.getString("ACCOUNT_TYPE").toUpperCase().equals("LIABILITIES"))
			{
				accountNames.add(rs.getString("ACCOUNT_NAME").toUpperCase()+" - "+rs.getString("ACCOUNT_TYPE").toUpperCase());
			}
			if(rs.getString("ACCOUNT_TYPE").toUpperCase().equals("CAPITAL") || rs.getString("ACCOUNT_TYPE").toUpperCase().equals("INCOME") || rs.getString("ACCOUNT_TYPE").toUpperCase().equals("WITHDRAWAL") || rs.getString("ACCOUNT_TYPE").toUpperCase().equals("EXPENSE"))
			{
				accountNames.add(rs.getString("ACCOUNT_NAME").toUpperCase()+" - "+rs.getString("ACCOUNT_TYPE").toUpperCase());
			}
	
		}
		conn.commit();
		logger.info("Account names" +accountNames);
		}
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in getAccounts",e1);
			}
			logger.error("Exception in getAccounts",e);
		}
		finally {
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return accountNames;
	}

	public void cleanSweep(String path) {
		// TODO Auto-generated method stub
		try {
			String delAccounts = "DELETE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT";
			synchronized(this)
			{
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(delAccounts);
				pstmt.executeUpdate();
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
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in cleanSweep",e1);
			}
			logger.error("Exception in cleanSweep",e);
		}
		finally {
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
	}

}
