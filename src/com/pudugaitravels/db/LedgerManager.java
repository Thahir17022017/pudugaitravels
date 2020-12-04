package com.pudugaitravels.db;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.pojo.ActJournal;
import com.pudugaitravels.pojo.Journal;
import com.pudugaitravels.pojo.Ledger;
import com.pudugaitravels.pojo.TrialBal;

public class LedgerManager {
	
	
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
	private Connection conn1;
	private PreparedStatement pstmt1;
	private ResultSet rs1;
	private List<TrialBal> assets;
	private List<TrialBal> liabilities;
	private List<TrialBal> capital;
	private List<TrialBal> drawings;
	private List<TrialBal> income;
	private List<TrialBal> expenses;
	private List<TrialBal> inc;
	private List<TrialBal> exp;
	
	
	final static Logger logger = LoggerFactory.getLogger(LedgerManager.class);
	
	
	public List<TrialBal> getInc() {
		return inc;
	}

	public List<TrialBal> getExp() {
		return exp;
	}
	
	
	public List<TrialBal> getAssets() {
		return assets;
	}

	public List<TrialBal> getLiabilities() {
		return liabilities;
	}

	public List<TrialBal> getCapital() {
		return capital;
	}

	public List<TrialBal> getDrawings() {
		return drawings;
	}

	public List<TrialBal> getIncome() {
		return income;
	}

	public List<TrialBal> getExpenses() {
		return expenses;
	}

	public List<String> getCreditIds() {
		return creditIds;
	}

	public List<String> getDebitIds() {
		return debitIds;
	}

	
	
	public LedgerManager()
	{
		
	}

	
	
	public void insDrLedger(String path,String debitId,String date,String journalDesc,String journalAcc,String amount,String rate,String dbCrAmt)
	{
		
		
		try {
			String insertJournal1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT(DEBIT_ID,LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_ACCOUNT,LEDGER_AMOUNT,LEDGER_RATE,LEDGER_DEBIT_AMOUNT) VALUES(?,?,?,?,?,?,?)";
			synchronized(this){
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertJournal1);
			pstmt.setString(1,debitId);
			pstmt.setString(2,date);
			pstmt.setString(3,journalDesc);
			pstmt.setString(4,journalAcc);
			pstmt.setString(5,amount);
			pstmt.setString(6,rate);
			pstmt.setString(7,dbCrAmt);
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
			
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insDrLedger ",e1);
			}
			logger.error("Exception in insDrLedger ",e);
		}
		finally {
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
	}
	
	
	public void insCrLedger(String path,String creditId,String debitId, String date, String journalDesc, String journalAcc, String amount,
			String rate, String dbCrAmt) {
		// TODO Auto-generated method stub
		try {
			String insertLedger1 = "INSERT INTO pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT(CREDIT_ID,DEBIT_ID,LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_ACCOUNT,LEDGER_AMOUNT,LEDGER_RATE,LEDGER_CREDIT_AMOUNT) VALUES(?,?,?,?,?,?,?,?)";
			
			synchronized(this){
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertLedger1);
			pstmt.setString(1,creditId);
			pstmt.setString(2,debitId);
			pstmt.setString(3,date);
			pstmt.setString(4,journalDesc);
			pstmt.setString(5,journalAcc);
			pstmt.setString(6,amount);
			pstmt.setString(7,rate);
			pstmt.setString(8,dbCrAmt);
			
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
			
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in insCrLedger",e1);
			}
			logger.error("Exception in insCrLedger",e);
		}
		finally {
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
	}

	public void delLedger(String path,String deleteId) {
		// TODO Auto-generated method stub
		List<String> creditIds = new ArrayList<String>();
		try {
			String deleteLedger1 = "SELECT DISTINCT CREDIT_ID FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE DEBIT_ID=? ";
			String deleteLedger2 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE CREDIT_ID=?";
			String deleteLedger3 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE DEBIT_ID=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(deleteLedger1);
			pstmt.setString(1,deleteId);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				creditIds.add(rs.getString("CREDIT_ID"));
			}
			conn.commit();
			
			if(creditIds.size()>0)
			{
				conn2 = DbHelper.DbConnector(path);
				conn2.setAutoCommit(false);
				
				pstmt2 = conn2.prepareStatement(deleteLedger2);
				for(String creditId:creditIds)
				{
					pstmt2.setString(1,creditId);
					pstmt2.executeUpdate();
				}
				conn2.commit();
				
				
			}
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			
			pstmt3 = conn3.prepareStatement(deleteLedger3);
			pstmt3.setString(1,deleteId);
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
				logger.error("Exception in rolling back in delLedger",e1);
			}
			logger.error("Exception in delLedger",e);
		}
		finally {
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
	}

	public void updLedger(String path,String date, String journalDesc, String journalAcc, String drcrType, String amount,
			String rate, String dbCrAmt, String updId) {
		// TODO Auto-generated method stub
		try 
		{
			String updateLedger1 = "UPDATE pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT SET LEDGER_DATE=?,LEDGER_DESCRIPTION=?,LEDGER_ACCOUNT=?,LEDGER_AMOUNT=?,LEDGER_RATE=?,LEDGER_DEBIT_AMOUNT=? WHERE DEBIT_ID=?";
			String updateLedger2 = "UPDATE pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT SET LEDGER_DATE=?,LEDGER_DESCRIPTION=?,LEDGER_ACCOUNT=?,LEDGER_AMOUNT=?,LEDGER_RATE=?,LEDGER_CREDIT_AMOUNT=? WHERE CREDIT_ID=?";
			
			
			if(drcrType.equalsIgnoreCase("debit"))
			{
				synchronized(this) {
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(updateLedger1);
				pstmt.setString(1,date);
				pstmt.setString(2,journalDesc);
				pstmt.setString(3,journalAcc);
				pstmt.setString(4,amount);
				pstmt.setString(5,rate);
				pstmt.setString(6,dbCrAmt);
				pstmt.setString(7,updId);
				pstmt.executeUpdate();
				conn.commit();
				}
			}
			else if(drcrType.equalsIgnoreCase("credit"))
			{
				synchronized(this) {
				conn = DbHelper.DbConnector(path);
				conn.setAutoCommit(false);
				pstmt = conn.prepareStatement(updateLedger2);
				pstmt.setString(1,date);
				pstmt.setString(2,journalDesc);
				pstmt.setString(3,journalAcc);
				pstmt.setString(4,amount);
				pstmt.setString(5,rate);
				pstmt.setString(6,dbCrAmt);
				pstmt.setString(7,updId);
				pstmt.executeUpdate();
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
				logger.error("Exception in rolling back in updLedger",e1);
			}
			logger.error("Exception in updLedger",e);
		}
		finally
		{
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
	}

	public List<Ledger> searchLedger(String path,String fromLedgerDate, String toLedgerDate, String ledgerAcc) {
		// TODO Auto-generated method stub
		List<Ledger> records = new ArrayList<Ledger>();
		try {
			
			String searchLedger1 = "SELECT DISTINCT DEBIT_ID,LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			String searchLedger2 = "SELECT DISTINCT LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			
			synchronized(this)
			{
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(searchLedger1);
			pstmt.setString(1,fromLedgerDate);
			pstmt.setString(2,toLedgerDate);
			pstmt.setString(3,ledgerAcc);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String[] indDates = rs.getString("LEDGER_DATE").split("-");
				String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
				logger.info("converted date "+date);
				records.add(new Ledger(date,rs.getString("LEDGER_DESCRIPTION"),rs.getString("LEDGER_DEBIT_AMOUNT"),"","Debit"));
			}
			conn.commit();
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchLedger2);
			pstmt2.setString(1,fromLedgerDate);
			pstmt2.setString(2,toLedgerDate);
			pstmt2.setString(3,ledgerAcc);
//			pstmt2.setString(4,rs.getString("DEBIT_ID"));
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				String[] indDates2 = rs2.getString("LEDGER_DATE").split("-");
				String date2 = indDates2[2]+"-"+indDates2[1]+"-"+indDates2[0];
				logger.info("converted date2 "+date2);
				records.add(new Ledger(date2,rs2.getString("LEDGER_DESCRIPTION"),"",rs2.getString("LEDGER_CREDIT_AMOUNT"),"Credit"));
			}
			conn2.commit();
			logger.info("records "+records);
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
				logger.error("Exception in rolling back in searchLedger",e1);
			}
			logger.error("Exception in searchLedger",e);
		}
		finally {
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		return records;
	}

	public String calcLedgBal(String path,String fromLedgerDate, String toLedgerDate, String ledgerAcc) {
		// TODO Auto-generated method stub
		
		double dbAmt=0.00,crAmt=0.00,bal=0.00;
		String normalBal="";
		String finalBal="";
		try {
			
			String searchLedger1 = "SELECT LEDGER_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			String searchLedger2 = "SELECT LEDGER_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			String searchLedger3 = "SELECT DISTINCT NORMAL_BAL FROM pudugaitr_pudugaitravels.TCOLKP_NORMAL_BAL WHERE ACCOUNTING_ELEMENT=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(searchLedger1);
			pstmt.setString(1,fromLedgerDate);
			pstmt.setString(2,toLedgerDate);
			pstmt.setString(3,ledgerAcc);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				dbAmt=dbAmt+rs.getDouble("LEDGER_DEBIT_AMOUNT");
				
			}
			conn.commit();
			logger.info("Total Debit for "+ledgerAcc+" : "+dbAmt);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchLedger2);
			pstmt2.setString(1,fromLedgerDate);
			pstmt2.setString(2,toLedgerDate);
			pstmt2.setString(3,ledgerAcc);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				
				crAmt=crAmt+rs2.getDouble("LEDGER_CREDIT_AMOUNT");
				
			}
			
			conn2.commit();
			logger.info("Total Credit for "+ledgerAcc+" : "+crAmt);
			
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(searchLedger3);
			String[] splitAccs= ledgerAcc.split("-");
			String accName = splitAccs[1].trim().toLowerCase();
			logger.info("accName "+accName);
			pstmt3.setString(1,accName);
			rs3 = pstmt3.executeQuery();
			
			while(rs3.next())
			{
				normalBal = rs3.getString("NORMAL_BAL");
			}
			conn3.commit();
			logger.info("normalBal for "+ledgerAcc+" : "+normalBal);
			
//			DecimalFormat df2 = new DecimalFormat(".##");
//			df2.setRoundingMode(RoundingMode.UP);
			}
			
			
			if(dbAmt>crAmt && normalBal.equalsIgnoreCase("debit"))
			{
				bal = dbAmt - crAmt;
				finalBal = String.format("%.2f",bal)+" Dr";
				logger.info("Bal amount when debit>credit and has debit balance "+finalBal);
			}
			if(dbAmt==crAmt && normalBal.equalsIgnoreCase("debit"))
			{
				bal = dbAmt - crAmt;
//				finalBal = df2.format(bal)+" Dr";
				finalBal = String.format("%.2f",bal)+" Dr";
				logger.info("Bal amount when debit=credit and has debit balance "+finalBal);
			}
			if(crAmt>dbAmt && normalBal.equalsIgnoreCase("debit"))
			{
				bal = dbAmt - crAmt;
//				finalBal = df2.format(bal)+" Cr";
				finalBal = String.format("%.2f",bal)+" Cr";
				logger.info("Bal amount when credit>debit and has debit balance "+finalBal);
			}
			
			
			if(crAmt>dbAmt && normalBal.equalsIgnoreCase("credit"))
			{
				bal = crAmt - dbAmt;
//				finalBal = df2.format(bal)+" Cr";
				finalBal = String.format("%.2f",bal)+" Cr";
				logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
			}
			if(crAmt==dbAmt && normalBal.equalsIgnoreCase("credit"))
			{
				bal = crAmt - dbAmt;
//				finalBal = df2.format(bal)+" Cr";
				finalBal = String.format("%.2f",bal)+" Cr";
				logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
			}
			if(dbAmt>crAmt && normalBal.equalsIgnoreCase("credit"))
			{
				bal = crAmt - dbAmt;
//				finalBal = df2.format(bal)+" Dr";
				finalBal = String.format("%.2f",bal)+" Cr";
				logger.info("Bal amount when debit>credit and has credit balance "+finalBal);
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
				logger.error("Exception in rolling back in calcLedgBal",e1);
			}
			logger.error("Exception in calcLedgBal",e);
		}
		finally {
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
		return finalBal;	
		
		
	}

	public StringBuffer downloadLedger(String path,String convFromLedgDate, String convToLedgDate, String ledgerAcc) 
	{
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("DATE,LEDGER_DESCRIPTION,LEDGER_DEBIT_AMOUNT,LEDGER_CREDIT_AMOUNT\n");
		try {
			String searchLedger1 = "SELECT DISTINCT DEBIT_ID,LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			String searchLedger2 = "SELECT DISTINCT LEDGER_DATE,LEDGER_DESCRIPTION,LEDGER_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_DATE >= ? and LEDGER_DATE <= ? and LEDGER_ACCOUNT=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(searchLedger1);
			pstmt.setString(1,convFromLedgDate);
			pstmt.setString(2,convToLedgDate);
			pstmt.setString(3,ledgerAcc);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String[] indDates = rs.getString("LEDGER_DATE").split("-");
				String date = indDates[2]+"-"+indDates[1]+"-"+indDates[0];
				logger.info("converted date "+date);
				sb.append(date+","+rs.getString("LEDGER_DESCRIPTION")+","+rs.getString("LEDGER_DEBIT_AMOUNT")+","+""+"\n");
			}
			conn.commit();
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchLedger2);
			pstmt2.setString(1,convFromLedgDate);
			pstmt2.setString(2,convToLedgDate);
			pstmt2.setString(3,ledgerAcc);
			rs2 = pstmt2.executeQuery();
			while(rs2.next())
			{
				String[] indDates2 = rs2.getString("LEDGER_DATE").split("-");
				String date2 = indDates2[2]+"-"+indDates2[1]+"-"+indDates2[0];
				logger.info("converted date2 "+date2);
				sb.append(date2+","+rs2.getString("LEDGER_DESCRIPTION")+","+""+","+rs2.getString("LEDGER_CREDIT_AMOUNT")+"\n");
			}
			conn2.commit();
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
							
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception in rolling back in downloadLedger",e1);
			}
			logger.error("Exception in downloadLedger",e);
			
		}
		finally
		{
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		return sb;
		
	}
	
	public  void calcAllLedgBal(String path) {
		// TODO Auto-generated method stub
		
		
		List<String> accountNames = new ArrayList<String>();
		
		assets = new ArrayList<TrialBal>();
		liabilities = new ArrayList<TrialBal>();
		capital = new ArrayList<TrialBal>();
		drawings = new ArrayList<TrialBal>();
		income = new ArrayList<TrialBal>();
		expenses = new ArrayList<TrialBal>();
		
		
		try {
			
			String searchLedger0 = "SELECT DISTINCT ACCOUNT_NAME,ACCOUNT_TYPE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT";
			String searchLedger1 = "SELECT LEDGER_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_ACCOUNT=?";
			String searchLedger2 = "SELECT LEDGER_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_ACCOUNT=?";
			String searchLedger3 = "SELECT DISTINCT NORMAL_BAL FROM pudugaitr_pudugaitravels.TCOLKP_NORMAL_BAL WHERE ACCOUNTING_ELEMENT=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(searchLedger0);
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
			
			
			conn1 = DbHelper.DbConnector(path);
			conn1.setAutoCommit(false);
			pstmt1 = conn1.prepareStatement(searchLedger1);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchLedger2);
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(searchLedger3);
			
			for(String ledgerAcc:accountNames)
			{
				double dbAmt=0.00,crAmt=0.00,bal=0.00;
				String normalBal="";
				String finalBal="";
				
				pstmt1.setString(1,ledgerAcc);
				rs1 = pstmt1.executeQuery();
				while(rs1.next())
				{
					dbAmt=dbAmt+rs1.getDouble("LEDGER_DEBIT_AMOUNT");
					
				}
				conn1.commit();
				logger.info("Total Debit for "+ledgerAcc+" : "+dbAmt);
				
				
				pstmt2.setString(1,ledgerAcc);
				rs2 = pstmt2.executeQuery();
				while(rs2.next())
				{
					crAmt=crAmt+rs2.getDouble("LEDGER_CREDIT_AMOUNT");
				}
				conn2.commit();
				logger.info("Total Credit for "+ledgerAcc+" : "+crAmt);
				
				
				String[] splitAccs= ledgerAcc.split("-");
				String accName = splitAccs[1].trim().toLowerCase();
				logger.info("accName "+accName);
				pstmt3.setString(1,accName);
				rs3 = pstmt3.executeQuery();
				
				while(rs3.next())
				{
					normalBal = rs3.getString("NORMAL_BAL");
				}
				conn3.commit();
				logger.info("normalBal for "+ledgerAcc+" : "+normalBal);
				
//				DecimalFormat df2 = new DecimalFormat(".##");
//				df2.setRoundingMode(RoundingMode.UP);
				
				
				if(dbAmt>crAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit>credit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				
				
				if(dbAmt==crAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit=credit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				if(crAmt>dbAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				
				
				if(crAmt>dbAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				
				if(crAmt==dbAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				if(dbAmt>crAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit>credit and has credit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("assets"))
					{
						assets.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("Asset Acc balances : "+assets);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("liabilities"))
					{
						liabilities.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("liabilities Acc balances : "+liabilities);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("capital"))
					{
						capital.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("capital Acc balances : "+capital);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("withdrawal"))
					{
						drawings.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("drawings Acc balances : "+drawings);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						income.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+income);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						expenses.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+expenses);
					}
				}
				
				
				
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
				if(conn1!=null)
				{
					conn1.rollback();
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
				logger.error("Exception in rolling back in calcLedgBal",e1);
			}
			logger.error("Exception in calcLedgBal",e);
		}
		finally {
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs1);
			DbHelper.stmtClose(pstmt1);
			DbHelper.DbConnectorClose(conn1);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
		
		
		
	}

	public double calcTotalDebits(List<TrialBal> assets2, List<TrialBal> drawings2, List<TrialBal> expenses2) {
		// TODO Auto-generated method stub
		double totalDebits=0.00;
		try 
		{
			
			for(TrialBal t:assets2)
			{
				totalDebits  = totalDebits+Double.valueOf(t.getDebitBal());
			}
			for(TrialBal t:drawings2)
			{
				totalDebits  = totalDebits+Double.valueOf(t.getDebitBal());
			}
			for(TrialBal t:expenses2)
			{
				totalDebits  = totalDebits+Double.valueOf(t.getDebitBal());
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in calcTotalDebits",e);
		}
		return totalDebits;
		
	}

	public double calcTotalCredits(List<TrialBal> liabilities2, List<TrialBal> capital2, List<TrialBal> income2) {
		// TODO Auto-generated method stub
		double totalCredits=0.00;
		try 
		{
			
			for(TrialBal t:liabilities2)
			{
				totalCredits  = totalCredits+Double.valueOf(t.getCreditBal());
			}
			for(TrialBal t:capital2)
			{
				totalCredits  = totalCredits+Double.valueOf(t.getCreditBal());
			}
			for(TrialBal t:income2)
			{
				totalCredits  = totalCredits+Double.valueOf(t.getCreditBal());
			}
		}
		catch(Exception e)
		{
			logger.error("Exception in calcTotalCredits",e);
		}
		return totalCredits;
	}

	public void calcIncome(String path) {
		// TODO Auto-generated method stub
		List<String> accountNames = new ArrayList<String>();
		
		inc = new ArrayList<TrialBal>();
		exp = new ArrayList<TrialBal>();
		
		try {
			
			String searchLedger0 = "SELECT DISTINCT ACCOUNT_NAME,ACCOUNT_TYPE FROM pudugaitr_pudugaitravels.TCOLKP_ACCOUNT";
			String searchLedger1 = "SELECT LEDGER_DEBIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_ACCOUNT=?";
			String searchLedger2 = "SELECT LEDGER_CREDIT_AMOUNT FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_ACCOUNT=?";
			String searchLedger3 = "SELECT DISTINCT NORMAL_BAL FROM pudugaitr_pudugaitravels.TCOLKP_NORMAL_BAL WHERE ACCOUNTING_ELEMENT=?";
			
			synchronized(this) {
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(searchLedger0);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				
				if(rs.getString("ACCOUNT_TYPE").toUpperCase().equals("INCOME") || rs.getString("ACCOUNT_TYPE").toUpperCase().equals("EXPENSE"))
				{
					accountNames.add(rs.getString("ACCOUNT_NAME").toUpperCase()+" - "+rs.getString("ACCOUNT_TYPE").toUpperCase());
				}
		
			}
			conn.commit();
			logger.info("Account names" +accountNames);
			
			
			conn1 = DbHelper.DbConnector(path);
			conn1.setAutoCommit(false);
			pstmt1 = conn1.prepareStatement(searchLedger1);
			
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(searchLedger2);
			
			conn3 = DbHelper.DbConnector(path);
			conn3.setAutoCommit(false);
			pstmt3 = conn3.prepareStatement(searchLedger3);
			
			for(String ledgerAcc:accountNames)
			{
				double dbAmt=0.00,crAmt=0.00,bal=0.00;
				String normalBal="";
				String finalBal="";
				
				pstmt1.setString(1,ledgerAcc);
				rs1 = pstmt1.executeQuery();
				while(rs1.next())
				{
					dbAmt=dbAmt+rs1.getDouble("LEDGER_DEBIT_AMOUNT");
					
				}
				conn1.commit();
				logger.info("Total Debit for "+ledgerAcc+" : "+dbAmt);
				
				
				pstmt2.setString(1,ledgerAcc);
				rs2 = pstmt2.executeQuery();
				while(rs2.next())
				{
					crAmt=crAmt+rs2.getDouble("LEDGER_CREDIT_AMOUNT");
				}
				conn2.commit();
				logger.info("Total Credit for "+ledgerAcc+" : "+crAmt);
				
				
				String[] splitAccs= ledgerAcc.split("-");
				String accName = splitAccs[1].trim().toLowerCase();
				logger.info("accName "+accName);
				pstmt3.setString(1,accName);
				rs3 = pstmt3.executeQuery();
				
				while(rs3.next())
				{
					normalBal = rs3.getString("NORMAL_BAL");
				}
				conn3.commit();
				logger.info("normalBal for "+ledgerAcc+" : "+normalBal);
				
//				DecimalFormat df2 = new DecimalFormat(".##");
//				df2.setRoundingMode(RoundingMode.UP);
				
				
				if(dbAmt>crAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit>credit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				
				
				if(dbAmt==crAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit=credit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				if(crAmt>dbAmt && normalBal.equalsIgnoreCase("debit"))
				{
					bal = dbAmt - crAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit and has debit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				
				
				if(crAmt>dbAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				
				if(crAmt==dbAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when credit>debit ad has credit bal "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,"",String.valueOf(finalBal)));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				if(dbAmt>crAmt && normalBal.equalsIgnoreCase("credit"))
				{
					bal = crAmt - dbAmt;
					finalBal = String.format("%.2f",bal);
					logger.info("Bal amount when debit>credit and has credit balance "+finalBal);
					if(splitAccs[1].trim().equalsIgnoreCase("income"))
					{
						inc.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("income Acc balances : "+inc);
					}
					if(splitAccs[1].trim().equalsIgnoreCase("expense"))
					{
						exp.add(new TrialBal(ledgerAcc,String.valueOf(finalBal),""));
						logger.info("expenses Acc balances : "+exp);
					}
				}
				
				
				
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
				if(conn1!=null)
				{
					conn1.rollback();
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
				logger.error("Exception in rolling back in calcProfitLoss",e1);
			}
			logger.error("Exception in calcProfitLoss",e);
		}
		finally {
			DbHelper.resultSetClose(rs3);
			DbHelper.stmtClose(pstmt3);
			DbHelper.DbConnectorClose(conn3);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			DbHelper.resultSetClose(rs1);
			DbHelper.stmtClose(pstmt1);
			DbHelper.DbConnectorClose(conn1);
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
		}
	}
	
	public double calcProfitLoss(List<TrialBal> revenues, List<TrialBal> expenses) {
		// TODO Auto-generated method stub
		double totalCredits=0.00;
		double totalDebits=0.00;
		double result=0.00;
		try 
		{
			
			for(TrialBal t:revenues)
			{
				totalCredits  = totalCredits+Double.valueOf(t.getCreditBal());
			}
			for(TrialBal t:expenses)
			{
				totalDebits  = totalDebits+Double.valueOf(t.getDebitBal());
			}
			result=totalCredits-totalDebits;
			
		}
		catch(Exception e)
		{
			logger.error("Exception in calcTotalDebits",e);
		}
		return result;
		
	}

	public int deleteLedgEntries(String path,List<String> accounts, String fromDate) {
		// TODO Auto-generated method stub
		int cnt=0;
		try {
			String deleteLedger1 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT WHERE LEDGER_ACCOUNT=? AND LEDGER_DATE>=?";
			String deleteLedger2 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT WHERE LEDGER_ACCOUNT=? AND LEDGER_DATE>=?";
			
			synchronized(this) {
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(deleteLedger2);
			for(String account:accounts)
			{
				pstmt2.setString(1,account);
				pstmt2.setString(2,fromDate);
				cnt=cnt+pstmt2.executeUpdate();
			}
			conn2.commit();
			
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(deleteLedger1);
			for(String account:accounts)
			{
				pstmt.setString(1,account);
				pstmt.setString(2,fromDate);
				cnt=cnt+pstmt.executeUpdate();
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
				if(conn2!=null)
				{
					conn2.rollback();
				}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("Exception in rolling back in deleteLedgEntries",e1);
		}
		    logger.error("Exception in deleteLedgEntries",e);
		}
		finally {
			
			DbHelper.resultSetClose(rs);
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
			DbHelper.resultSetClose(rs2);
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
		}
		return cnt;
	}

	public void cleanSweep(String path) {
		// TODO Auto-generated method stub
		try {
			String deleteLedger1 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_CREDIT";
			String deleteLedger2 = "DELETE FROM pudugaitr_pudugaitravels.TCOODS_LEDGER_DEBIT";
			
			
			synchronized(this) {
			conn2 = DbHelper.DbConnector(path);
			conn2.setAutoCommit(false);
			pstmt2 = conn2.prepareStatement(deleteLedger1);
			pstmt2.executeUpdate();
			conn2.commit();
			
			conn = DbHelper.DbConnector(path);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(deleteLedger2);
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
				if(conn2!=null)
				{
					conn2.rollback();
				}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error("Exception in rolling back in cleanSweep",e1);
		}
		    logger.error("Exception in cleanSweep",e);
		}
		finally {
			
			
			DbHelper.stmtClose(pstmt);
			DbHelper.DbConnectorClose(conn);
			
			DbHelper.stmtClose(pstmt2);
			DbHelper.DbConnectorClose(conn2);
			
		}
		
	}
}
