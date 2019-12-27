package com.qa.rest.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;

import com.qa.rest.Utilities.TestUtility;

public class TestBase 
{
	public static Properties property;
	public static Logger Log;
	
	public TestBase()
	{
		Log = Logger.getLogger(this.getClass());
	}
	
	@BeforeTest
	public void setLog4J()
	{
		TestUtility.setDateForLog4J();
	}
	
	public static void initialization()
	{
		try
		{
			property = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/qa/rest/Configuration/Configuration.properties");
			property.load(ip);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
