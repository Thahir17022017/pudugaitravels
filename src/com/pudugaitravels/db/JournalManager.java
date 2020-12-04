package com.pudugaitravels.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.pojo.ActJournal;
import com.pudugaitravels.pojo.Journal;

public class JournalManager {
	
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Connection conn2;
	private PreparedStatement pstmt2;
	private ResultSet rs2;
	private Connection conn3;
	private PreparedStatement pstmt3;
	private Connection conn4;
	private PreparedStatement pstmt4;
	private List<String> creditIds;
	private List<String> debitIds;
	private ResultSet rs3;
	private ResultSet rs4;
	private Connection conn0;
	private PreparedStatement pstmt0;
	private ResultSet rs0;
	
	
	final static Logger logger = LoggerFactory.getLogger(JournalManager.class);

	public List<String> getCreditIds() {
		return creditIds;
	}

	public List<String> getDebitIds() {
		return debitIds;
	}

	
	
	public JournalManager()
	{
		
	}

	public String insCrJnl(String path,String date,String journalDesc,String journalAcc,String amount,String rate,String dbCrAmt)
	{
		int cnt=0;
		String creditId="";
		try {
			String insertJournal1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT(JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_CREDIT_AMOUNT) VALUES(?,?,?,?,?,?)";
			String insertJournal2 = "SELECT MAX(CREDIT_ID) AS CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT";
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertJournal1);
			pstmt.setString(1,date);
			pstmt.setString(2,journalDesc);
			pstmt.setString(3,journalAcc);
			pstmt.setString(4,amount);
			pstmt.setString(5,rate);
			pstmt.setString(6,dbCrAmt);
			cnt = pstmt.executeUpdate();
			logger.info("count vl is : "+cnt);
			conn.commit();
			if(cnt==0)
			{
				return "";
			}
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(insertJournal2);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				creditId = rs2.getString("CREDIT_ID");
			}
			conn2.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insert credit journal",e1);
			}
			logger.error("Exception in insert credit journal",e);
		}
		finally {
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return creditId;
	}
	
	
	public String insCrJnlRl(String path,String date,String journalDesc,String journalAcc,String amount,String rate,String dbCrAmt,String dbCrId)
	{
		int cnt=0;
		int drCnt=0;
		String creditId="";
		try {
			String insertJournal0 = "SELECT DISTINCT COUNT(DEBIT_ID) AS DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE DEBIT_ID=?";
			String insertJournal1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT(JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_CREDIT_AMOUNT) VALUES(?,?,?,?,?,?)";
			String insertJournal2 = "SELECT MAX(CREDIT_ID) AS CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT";
			String insertJournal3 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL(DEBIT_ID,CREDIT_ID) values(?,?)";
			
			synchronized(this)
			{
			conn0 = DbHelper.DbConnector(path);
			conn0.setAutoCommit(false);
			pstmt0 = conn0.prepareStatement(insertJournal0);
			pstmt0.setString(1,dbCrId);
			rs0 = pstmt0.executeQuery();
			while(rs0.next())
			{
				drCnt = Integer.parseInt(rs0.getString("DEBIT_ID"));
			}
			conn0.commit();
			logger.info("drCnt is : "+drCnt);
			if(drCnt==0)
			{
				return "Not a valid debit id";
			}
			
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertJournal1);
			pstmt.setString(1,date);
			pstmt.setString(2,journalDesc);
			pstmt.setString(3,journalAcc);
			pstmt.setString(4,amount);
			pstmt.setString(5,rate);
			pstmt.setString(6,dbCrAmt);
			cnt = pstmt.executeUpdate();
			logger.info("count vl is : "+cnt);
			conn.commit();
			if(cnt==0)
			{
				return "";
			}
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(insertJournal2);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				creditId = rs2.getString("CREDIT_ID");
			}
			conn2.commit();
			
			cnt=0;
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(insertJournal3);
			pstmt3.setString(1,dbCrId);
			pstmt3.setString(2,creditId);
			logger.info("-----------credit rel-------------");
			logger.info("debit id",dbCrId);
			logger.info("credit id",creditId);
			cnt = pstmt3.executeUpdate();
			conn3.commit();
			if(cnt==0)
			{
				return "";
			}
			}
			
		}
		catch(Exception e)
		{
			try {
				if(conn0!=null)
				{
					conn0.rollback();
				}
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insert credit journal rel",e1);
			}
			logger.error("Exception in insert credit journal rel",e);
		}
		finally {
			DbHelper.resultSetClose(rs0);
			DbHelper.stmtClose(pstmt0);
			DbHelper.DbConnectorClose(conn0);
			
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
			
		}
		return creditId;
	}

	public String insDrJnl(String path,String date,String journalDesc,String journalAcc,String amount,String rate,String dbCrAmt)
	{
		int cnt=0;
		String debitId="";
		try {
			String insertJournal1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT(JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT) VALUES(?,?,?,?,?,?)";
			String insertJournal2 = "SELECT MAX(DEBIT_ID) AS DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertJournal1);
			pstmt.setString(1,date);
			pstmt.setString(2,journalDesc);
			pstmt.setString(3,journalAcc);
			pstmt.setString(4,amount);
			pstmt.setString(5,rate);
			pstmt.setString(6,dbCrAmt);
			cnt = pstmt.executeUpdate();
			logger.info("count vl is : "+cnt);
			conn.commit();
			if(cnt==0)
			{
				return "";
			}
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(insertJournal2);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				debitId = rs2.getString("DEBIT_ID");
			}
			conn2.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insert debit journal",e1);
			}
			logger.error("Exception in insert debit journal",e);
		}
		finally {
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return debitId;
	}
	
	public String insDrJnlRel(String path,String date,String journalDesc,String journalAcc,String amount,String rate,String dbCrAmt,String dbCrId)
	{
		int cnt=0;
		String debitId="";
		try {
			String insertJournal1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT(JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT) VALUES(?,?,?,?,?,?)";
			String insertJournal2 = "SELECT MAX(DEBIT_ID) AS DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT";
			String insertJournal3 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL(DEBIT_ID,CREDIT_ID) values(?,?)";
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertJournal1);
			pstmt.setString(1,date);
			pstmt.setString(2,journalDesc);
			pstmt.setString(3,journalAcc);
			pstmt.setString(4,amount);
			pstmt.setString(5,rate);
			pstmt.setString(6,dbCrAmt);
			cnt = pstmt.executeUpdate();
			logger.info("count vl is : "+cnt);
			conn.commit();
			if(cnt==0)
			{
				return "";
			}
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(insertJournal2);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				debitId = rs2.getString("DEBIT_ID");
			}
			conn2.commit();
			
			cnt=0;
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(insertJournal3);
			pstmt3.setString(1,debitId);
			pstmt3.setString(2,dbCrId);
			logger.info("-----------debit rel-------------");
			logger.info("debit id",debitId);
			logger.info("credit id",dbCrId);
			cnt = pstmt3.executeUpdate();
			conn3.commit();
			if(cnt==0)
			{
				return "";
			}
			}
			
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insert debit journal rel",e1);
			}
			logger.error("Exception in insert debit journal rel",e);
		}
		finally {
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return debitId;
	}
	
	public int delJnl(String path,String id,String trType)
	{
			creditIds = new ArrayList<String>();
			debitIds = new ArrayList<String>();
			int cnt=0;
			
			String delJournal1 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL where DEBIT_ID=?";
			String delJournal2 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL where DEBIT_ID=?";
			String delJournal3 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE DEBIT_ID=?";
			
			String delJournal4 = "SELECT DISTINCT DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL where CREDIT_ID=?";
			String delJournal5 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL where CREDIT_ID=?";
			String delJournal6 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT WHERE CREDIT_ID=?";
			
			if(trType.equalsIgnoreCase("debit"))
			{
				try
				{
						synchronized(this)
						{
						conn = DbHelper.DbConnector(path);
						conn.setAutoCommit(false);
						pstmt = conn.prepareStatement(delJournal1);
						pstmt.setString(1,id);
						rs = pstmt.executeQuery();
						while(rs.next())
						{
							creditIds.add(rs.getString("CREDIT_ID"));
						}
						conn.commit();

						if(creditIds.size()>0)
						{
								conn2 = DbHelper.DbConnector(path);
								conn2.setAutoCommit(false);
								pstmt2 = conn2.prepareStatement(delJournal2);
								pstmt2.setString(1,id);
								cnt=cnt+pstmt2.executeUpdate();
								conn2.commit();
								
								conn3 = DbHelper.DbConnector(path);
								conn3.setAutoCommit(false);
								pstmt3 = conn3.prepareStatement(delJournal6);
								for(String creditId:creditIds)
								{
									pstmt3.setString(1,creditId);
									cnt=cnt+pstmt3.executeUpdate();
								}
								conn3.commit();
						}
						conn4 = DbHelper.DbConnector(path);
						conn4.setAutoCommit(false);
						pstmt4 = conn4.prepareStatement(delJournal3);
						pstmt4.setString(1,id);
						cnt=cnt+pstmt4.executeUpdate();
						conn4.commit();
						}
				}
				catch(Exception e)
				{
					try {
						if(conn!=null)
						{
							conn.rollback();
						}
						if(conn2!=null)
						{
							conn2.rollback();
						}
						if(conn3!=null)
						{
							conn3.rollback();
						}
						if(conn4!=null)
						{
							conn4.rollback();
						}
					}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						logger.error("Exception in rolling back in delete journal",e1);
					}
					logger.error("Exception in delete journal",e);
				}
				finally
				{
					DbHelper.stmtClose(pstmt4);
					DbHelper.DbConnectorClose(conn4);
					DbHelper.stmtClose(pstmt3);
					DbHelper.DbConnectorClose(conn3);
					DbHelper.stmtClose(pstmt2);
					DbHelper.DbConnectorClose(conn2);
					DbHelper.resultSetClose(rs);
					DbHelper.stmtClose(pstmt);
					DbHelper.DbConnectorClose(conn);
					
				}
			}
			if(trType.equalsIgnoreCase("credit"))
			{
				try
				{
					    synchronized(this)
					    {
						conn = DbHelper.DbConnector(path);
						conn.setAutoCommit(false);
						pstmt = conn.prepareStatement(delJournal4);
						pstmt.setString(1,id);
						rs = pstmt.executeQuery();
						while(rs.next())
						{
							debitIds.add(rs.getString("DEBIT_ID"));
						}
						conn.commit();

						if(debitIds.size()>0)
						{
								conn2 = DbHelper.DbConnector(path);
								conn2.setAutoCommit(false);
								pstmt2 = conn2.prepareStatement(delJournal5);
								pstmt2.setString(1,id);
								cnt=cnt+pstmt2.executeUpdate();
								conn2.commit();
								
								conn3 = DbHelper.DbConnector(path);
								conn3.setAutoCommit(false);
								pstmt3 = conn3.prepareStatement(delJournal3);
								for(String debitId:debitIds)
								{
									pstmt3.setString(1,debitId);
									cnt=cnt+pstmt3.executeUpdate();
								}
								conn3.commit();
						}
						conn4 = DbHelper.DbConnector(path);
						conn4.setAutoCommit(false);
						pstmt4 = conn4.prepareStatement(delJournal6);
						pstmt4.setString(1,id);
						cnt=cnt+pstmt4.executeUpdate();
						conn4.commit();
					    }
				}
				catch(Exception e)
				{
					try {
						if(conn!=null)
						{
							conn.rollback();
						}
						if(conn2!=null)
						{
							conn2.rollback();
						}
						if(conn3!=null)
						{
							conn3.rollback();
						}
						if(conn4!=null)
						{
							conn4.rollback();
						}
					}
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						logger.error("Exception in rolling back in delete journal",e1);
					}
					logger.error("Exception in delete journal",e);
				}
				finally
				{
					DbHelper.stmtClose(pstmt4);
					DbHelper.DbConnectorClose(conn4);
					DbHelper.stmtClose(pstmt3);
					DbHelper.DbConnectorClose(conn3);
					DbHelper.stmtClose(pstmt2);
					DbHelper.DbConnectorClose(conn2);
					DbHelper.resultSetClose(rs);
					DbHelper.stmtClose(pstmt);
					DbHelper.DbConnectorClose(conn);
				}
				
			}
			return cnt;

	}

	public int updJnl(String path,String date, String journalDesc, String journalAcc, String drcrType, String amount, String rate,
			String dbCrAmt, String updId) {
		// TODO Auto-generated method stub
		int cnt = 0;
		try 
		{
			String updateJournal1 = "UPDATE pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT SET JOURNAL_DATE=?,JOURNAL_DESCRIPTION=?,JOURNAL_ACCOUNT=?,JOURNAL_AMOUNT=?,JOURNAL_RATE=?,JOURNAL_DEBIT_AMOUNT=? WHERE DEBIT_ID=?";
			String updateJournal2 = "UPDATE pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT SET JOURNAL_DATE=?,JOURNAL_DESCRIPTION=?,JOURNAL_ACCOUNT=?,JOURNAL_AMOUNT=?,JOURNAL_RATE=?,JOURNAL_CREDIT_AMOUNT=? WHERE CREDIT_ID=?";
			
			
			if(drcrType.equalsIgnoreCase("debit"))
			{
				synchronized(this){
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(updateJournal1);
				pstmt.setString(1,date);
				pstmt.setString(2,journalDesc);
				pstmt.setString(3,journalAcc);
				pstmt.setString(4,amount);
				pstmt.setString(5,rate);
				pstmt.setString(6,dbCrAmt);
				pstmt.setString(7,updId);
				cnt = pstmt.executeUpdate();
				conn.commit();
				}
			}
			else if(drcrType.equalsIgnoreCase("credit"))
			{
				synchronized(this){
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(updateJournal2);
				pstmt.setString(1,date);
				pstmt.setString(2,journalDesc);
				pstmt.setString(3,journalAcc);
				pstmt.setString(4,amount);
				pstmt.setString(5,rate);
				pstmt.setString(6,dbCrAmt);
				pstmt.setString(7,updId);
				cnt = pstmt.executeUpdate();
				conn.commit();
				}
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
				logger.error("Exception in rolling back in update journal",e1);
			}
			logger.error("Exception in update journal",e);
		}
		finally
		{
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return cnt;
		
	}

	public List<Journal> readJnl(String path) {
		// TODO Auto-generated method stub
		List<Journal> journals = new ArrayList<Journal>();
		List<String> debitIds = new ArrayList<String>();
		List<String> creditIds = new ArrayList<String>();
		try
		{
			LocalDate localDate = LocalDate.now();
			logger.info("localDate : "+localDate.toString());
			localDate = localDate.minus(10,ChronoUnit.DAYS);
			logger.info("10 less localDate : "+localDate.toString());
			String readJnl1 = "SELECT DISTINCT DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE JOURNAL_DATE >= "+localDate.toString();
			String readJnl2 = "SELECT DISTINCT DEBIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE DEBIT_ID=?";
			String readJnl3 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL WHERE DEBIT_ID=?";
			String readJnl4 = "SELECT DISTINCT CREDIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT WHERE CREDIT_ID=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(readJnl1);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				debitIds.add(rs.getString("DEBIT_ID"));
			}
			conn.commit();
			logger.info("debitIds : "+debitIds);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(readJnl2);
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(readJnl3);
			
			conn4 = DbHelper.DbConnector(path);
			conn4.setAutoCommit(false);
			pstmt4 = conn4.prepareStatement(readJnl4);
			
			for(String debitId:debitIds)
			{
				logger.info("debitId "+debitId);
				pstmt2.setString(1,debitId);
				rs2 = pstmt2.executeQuery();
				while(rs2.next())
				{
					String[] indDates = rs2.getString("JOURNAL_DATE").split("-");
					String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
					logger.info("converted date "+date);
					journals.add(new Journal(rs2.getString("DEBIT_ID"),date,rs2.getString("JOURNAL_DESCRIPTION"),
							rs2.getString("JOURNAL_ACCOUNT"),rs2.getString("JOURNAL_DEBIT_AMOUNT"),"","Debit"));
				}
				
				pstmt3.setString(1,debitId);
				rs3 = pstmt3.executeQuery();
				while(rs3.next())
				{
					creditIds.add(rs3.getString("CREDIT_ID"));
					
				}
				logger.info("creditIds "+creditIds);
				if(creditIds.size()>0)
				{
					for(String creditId:creditIds)
					{
						logger.info("creditId "+creditId);
						pstmt4.setString(1,creditId);
						rs4 = pstmt4.executeQuery();
						while(rs4.next())
						{
							String[] indDates = rs4.getString("JOURNAL_DATE").split("-");
							String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
							logger.info("converted date "+date);
							journals.add(new Journal(rs4.getString("CREDIT_ID"),date,rs4.getString("JOURNAL_DESCRIPTION"),
									rs4.getString("JOURNAL_ACCOUNT"),"",rs4.getString("JOURNAL_CREDIT_AMOUNT"),"Credit"));
						}
					}
				}
				creditIds.clear();
				
				
			}
			conn4.commit();
			conn3.commit();
			conn2.commit();
			conn.commit();
			logger.info("debit journals"+journals);
			}
			
			
		}
		catch(Exception e)
		{
			try {
				if(conn4!=null)
				{
					conn4.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn!=null)
				{
					conn.rollback();
				}
				
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in read journal",e1);
			}
			logger.error("Exception in read journal",e);
		}
		finally
		{
			DbHelper.resultSetClose(rs4);
			DbHelper.stmtClose(pstmt4);
			DbHelper.DbConnectorClose(conn4);
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return journals;
	}

	public List<Journal> searchJnl(String path,String date, String accType) {
		// TODO Auto-generated method stub
		List<Journal> journals = new ArrayList<Journal>();
		List<String> debitIds = new ArrayList<String>();
		List<String> creditIds = new ArrayList<String>();
		try {
				
			String readJnl1 = "SELECT DISTINCT DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE JOURNAL_DATE>=?";
			String readJnl1_2 = "SELECT DISTINCT DEBIT_ID FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE JOURNAL_DATE>=? AND JOURNAL_ACCOUNT=?";
			String readJnl2 = "SELECT DISTINCT DEBIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE DEBIT_ID=?";
			String readJnl3 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL WHERE DEBIT_ID=?";
			String readJnl4 = "SELECT DISTINCT CREDIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT WHERE CREDIT_ID=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			if(accType.trim().equalsIgnoreCase(""))
			{
				pstmt = conn.prepareStatement(readJnl1);
				pstmt.setString(1,date);
				logger.info("searching for date records ");
			}
			else if(!accType.trim().equalsIgnoreCase(""))
			{
				pstmt = conn.prepareStatement(readJnl1_2);
				pstmt.setString(1,date);
				pstmt.setString(2,accType);
				logger.info("searching for date plus acctype records ");
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				debitIds.add(rs.getString("DEBIT_ID"));
			}
			conn.commit();
			logger.info("debitIds : "+debitIds);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(readJnl2);
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(readJnl3);
			
			conn4 = DbHelper.DbConnector(path);
			conn4.setAutoCommit(false);
			pstmt4 = conn4.prepareStatement(readJnl4);
			
			for(String debitId:debitIds)
			{
				logger.info("debitId "+debitId);
				pstmt2.setString(1,debitId);
				rs2 = pstmt2.executeQuery();
				while(rs2.next())
				{
					String[] indDates = rs2.getString("JOURNAL_DATE").split("-");
					String date2 = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
					logger.info("converted date "+date2);
					journals.add(new Journal(rs2.getString("DEBIT_ID"),date2,rs2.getString("JOURNAL_DESCRIPTION"),
							rs2.getString("JOURNAL_ACCOUNT"),rs2.getString("JOURNAL_DEBIT_AMOUNT"),"","Debit"));
				}
				
				pstmt3.setString(1,debitId);
				rs3 = pstmt3.executeQuery();
				while(rs3.next())
				{
					creditIds.add(rs3.getString("CREDIT_ID"));
					
				}
				logger.info("creditIds "+creditIds);
				if(creditIds.size()>0)
				{
					for(String creditId:creditIds)
					{
						logger.info("creditId "+creditId);
						pstmt4.setString(1,creditId);
						rs4 = pstmt4.executeQuery();
						while(rs4.next())
						{
							String[] indDates = rs4.getString("JOURNAL_DATE").split("-");
							String date2 = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
							logger.info("converted date "+date2);
							journals.add(new Journal(rs4.getString("CREDIT_ID"),date2,rs4.getString("JOURNAL_DESCRIPTION"),
									rs4.getString("JOURNAL_ACCOUNT"),"",rs4.getString("JOURNAL_CREDIT_AMOUNT"),"Credit"));
						}
					}
				}
				creditIds.clear();
				
				
			}
			conn4.commit();
			conn3.commit();
			conn2.commit();
			conn.commit();
			logger.info("debit journals"+journals);
			
			}
		}
		catch(Exception e)
		{
			try {
				if(conn4!=null)
				{
					conn4.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn!=null)
				{
					conn.rollback();
				}
				
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in searchJnl",e1);
			}
			logger.error("Exception in searchJnl",e);
		}
		finally
		{
			DbHelper.resultSetClose(rs4);
			DbHelper.stmtClose(pstmt4);
			DbHelper.DbConnectorClose(conn4);
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return journals;
		
	}

	public List<ActJournal> loadJnl(String path,String id) {
		// TODO Auto-generated method stub
		List<ActJournal> entries = new ArrayList<ActJournal>();
		List<String> creditIds = new ArrayList<String>();
		try
		{
			String loadJournal1 = "SELECT DISTINCT DEBIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE DEBIT_ID=?";
			String loadJournal2 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL WHERE DEBIT_ID=?";
			String loadJournal3 = "SELECT DISTINCT CREDIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT WHERE CREDIT_ID=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(loadJournal1);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String[] indDates = rs.getString("JOURNAL_DATE").split("-");
				String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
				logger.info("converted date "+date);
				entries.add(new ActJournal(rs.getString("DEBIT_ID"),"",date,rs.getString("JOURNAL_DESCRIPTION"),
						rs.getString("JOURNAL_ACCOUNT"), rs.getString("JOURNAL_AMOUNT"), rs.getString("JOURNAL_RATE"),rs.getString("JOURNAL_DEBIT_AMOUNT"),"","Debit"));
			}
			conn.commit();
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(loadJournal2);
			pstmt2.setString(1,id);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				creditIds.add(rs2.getString("CREDIT_ID"));
				
			}
			conn2.commit();
			
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(loadJournal3);
			if(creditIds.size()>0)
			{
				for(String creditId:creditIds)
				{
					logger.info("creditId "+creditId);
					pstmt3.setString(1,creditId);
					rs3 = pstmt3.executeQuery();
					while(rs3.next())
					{
						String[] indDates = rs3.getString("JOURNAL_DATE").split("-");
						String date2 = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
						logger.info("converted date "+date2);
						entries.add(new ActJournal(rs3.getString("CREDIT_ID"),id,date2,rs3.getString("JOURNAL_DESCRIPTION"),
								rs3.getString("JOURNAL_ACCOUNT"), rs3.getString("JOURNAL_AMOUNT"), rs3.getString("JOURNAL_RATE"),"",rs3.getString("JOURNAL_CREDIT_AMOUNT"),"Credit"));
					}
					
				}
			}
			conn3.commit();
			}
			
		}
		catch(Exception e)
		{
			try {
				
				if(conn3!=null)
				{
					conn3.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn!=null)
				{
					conn.rollback();
				}
				
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in loadJnl",e1);
			}
			logger.error("Exception in loadJnl",e);
		}
		finally
		{
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return entries;
	}

	public StringBuffer dwnldJnl(String path,String searchDate, String searchAcc) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		List<String> creditIds = new ArrayList<String>();
		sb.append("JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT,JOURNAL_CREDIT_AMOUNT\n");
		try {
			String searchJournal0 = "SELECT DISTINCT DEBIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE JOURNAL_DATE >= ?";
			String searchJournal1 = "SELECT DISTINCT DEBIT_ID,JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT WHERE JOURNAL_DATE >= ? and JOURNAL_ACCOUNT=?";
			String searchJournal2 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL WHERE DEBIT_ID=?";
			String searchJournal3 = "SELECT JOURNAL_DATE,JOURNAL_DESCRIPTION,JOURNAL_ACCOUNT,JOURNAL_AMOUNT,JOURNAL_RATE,JOURNAL_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT WHERE CREDIT_ID=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchJournal2);
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(searchJournal3);
			
			
			if(!searchDate.equalsIgnoreCase("") && searchAcc.equalsIgnoreCase(""))
			{
				pstmt = conn.prepareStatement(searchJournal0);
				pstmt.setString(1,searchDate);
				
			}
			else if(!searchDate.equalsIgnoreCase("") && !searchAcc.equalsIgnoreCase(""))
			{
				pstmt = conn.prepareStatement(searchJournal1);
				pstmt.setString(1,searchDate);
				pstmt.setString(2,searchAcc);
			}
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String[] indDates = rs.getString("JOURNAL_DATE").split("-");
				String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
				logger.info("converted date "+date);
				sb.append(date+","+rs.getString("JOURNAL_DESCRIPTION")+","+rs.getString("JOURNAL_ACCOUNT")+","
				           +rs.getString("JOURNAL_AMOUNT")+","+rs.getString("JOURNAL_RATE")+","+
						   rs.getString("JOURNAL_DEBIT_AMOUNT")+","+""+"\n");
				
				pstmt2.setString(1,rs.getString("DEBIT_ID"));
				rs2 = pstmt2.executeQuery();
				while(rs2.next())
				{
					
					pstmt3.setString(1,rs2.getString("CREDIT_ID"));
					rs3 = pstmt3.executeQuery();
					while(rs3.next())
					{
						String[] indDates2 = rs3.getString("JOURNAL_DATE").split("-");
						String date2 = indDates2[2]+"-"+indDates2[1]+"-"+indDates2[0];
						logger.info("converted date2 "+date2);
						sb.append(date2+","+rs3.getString("JOURNAL_DESCRIPTION")+","+rs3.getString("JOURNAL_ACCOUNT")+","
						           +rs3.getString("JOURNAL_AMOUNT")+","+rs3.getString("JOURNAL_RATE")+","+
								   ""+","+rs3.getString("JOURNAL_CREDIT_AMOUNT")+"\n");
					}
					conn3.commit();
					
				}
				conn2.commit();
				
			}
			conn.commit();
			logger.info("records "+sb.toString());
			}
	
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
							
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in downloadJournal",e1);
			}
			logger.error("Exception in downloadJournal",e);
			
		}
		finally
		{
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return sb;
	}

	public void cleanSweep(String path) {
		// TODO Auto-generated method stub
		try {
			String searchJournal0 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_DEBIT_CREDIT_REL";
			String searchJournal1 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_CREDIT";
			String searchJournal2 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_JOURNAL_DEBIT";
			
			
			synchronized(this)
			{
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(searchJournal0);
				pstmt.executeUpdate();
				conn.commit();
				
				conn2 = DbHelper.DbConnector(path);
				conn2.setAutoCommit(false);
				pstmt2 = conn2.prepareStatement(searchJournal1);
				pstmt2.executeUpdate();
				conn2.commit();
				
				conn3 = DbHelper.DbConnector(path);
				conn3.setAutoCommit(false);
				pstmt3 = conn3.prepareStatement(searchJournal2);
				pstmt3.executeUpdate();
				conn3.commit();
			}
			
	
		}
		catch(Exception e)
		{
			try {
				if(conn!=null)
				{
					conn.rollback();
				}
				if(conn2!=null)
				{
					conn2.rollback();
				}
				if(conn3!=null)
				{
					conn3.rollback();
				}
							
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in cleanSweep",e1);
			}
			logger.error("Exception in cleanSweep",e);
			
		}
		finally
		{
			
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
	}

	
	
	
	

}
