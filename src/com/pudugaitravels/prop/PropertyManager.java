package com.pudugaitravels.prop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class PropertyManager {
	
	private Properties readProps;
	final static Logger logger = LoggerFactory.getLogger(PropertyManager.class);
	
	public void loadProps(String path)
	{
		FileInputStream fis =null;
		try {
		readProps = new Properties();
		fis = new FileInputStream(path);
		readProps.load(fis);
		}
		catch(Exception e)
		{
			logger.error("Exception in reading  property file",e);
		}
		finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception in closing  property file",e);
			}
		}
		
		
	}
	
	
	
	public String readUserCount()
	{
		 return readProps.getProperty("com.msjexports.usercount");
	}
	
	public String readSpecProp(String name)
	{
		return readProps.getProperty(name);
	}
	
}