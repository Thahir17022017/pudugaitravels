package com.pudugaitravels.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pudugaitravels.prop.PropertyManager;

public class DbHelper {
	
private static Connection conn;

private static PropertyManager propManager = new PropertyManager();
	
final static Logger logger = LoggerFactory.getLogger(DbHelper.class);


		public DbHelper()
		{
			
		}


	public static Connection DbConnector(String path)
	{
		try
		{
			
			propManager.loadProps(path);
			String dbDriver = propManager.readSpecProp("com.pudugaitravels.dbdriver");
			logger.info("dbDriver"+dbDriver);
			String dbUrl = propManager.readSpecProp("com.pudugaitravels.url");
			logger.info("dbUrl"+dbUrl);
			String dbUser = propManager.readSpecProp("com.pudugaitravels.dbuser");
			logger.info("dbUser"+dbUser);
			String dbPwd = propManager.readSpecProp("com.pudugaitravels.dbpwd");
			logger.info("dbPwd"+dbPwd);
			
			Class.forName(dbDriver); //uncomment for mysql
			//Class.forName("org.sqlite.jdbc");
			conn=DriverManager.getConnection(dbUrl,dbUser,dbPwd); //uncomment for mysql
//			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//			conn=DriverManager.getConnection("jdbc:derby:db/store;user=Basheer;password=12345!");
//			conn.setSchema("APP");
//			if(conn!=null)
//			{
//				logger.info("Connection success");
//			}
			
//			Statement stmt=conn.createStatement();  
//			ResultSet rs=stmt.executeQuery("select * from comments");  
//			while(rs.next())  
//			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
//			conn.close();  
			
		}
		catch(Exception e)
		{
			
			logger.error("Exception in getting db connection",e);
		}
		return conn;
	}

	

	
	public static boolean DbConnectorClose(Connection conn)
	{
		
		try
		{
			if(conn!=null)
			{
				conn.close();
				return true;
			}
			
			//Class.forName("org.sqlite.jdbc");
			
			
		}
		catch(Exception e)
		{
			logger.error("Exception in closing db connection",e);
		}
		return false;
	}
	
	public static void resultSetClose(ResultSet rs)
	{
		try {
		if(rs!=null)
    	{
    		rs.close();
    	}
		}
		catch(Exception e)
		{
			logger.error("Exception in closing db result set",e);
		}
	}
	
	public static void stmtClose(PreparedStatement pstmt)
	{
		try {
		if(pstmt!=null)
    	{
    		pstmt.close();
    	}
		}
		catch(Exception e)
		{
			logger.error("Exception in closing db statement",e);
		}
	}

}