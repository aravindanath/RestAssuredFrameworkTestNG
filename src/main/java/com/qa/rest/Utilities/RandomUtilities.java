package com.qa.rest.Utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtilities
{
	public static String employeeName()
	{
		String generatedString  = RandomStringUtils.randomAlphabetic(2);
		return("Niru" +generatedString);
	}
	
	public static String employeeSalary()
	{
		String generatedString = RandomStringUtils.randomNumeric(5);
		return(generatedString);
	}
	
	public static String employeeAge()
	{
		String generatedString = RandomStringUtils.randomNumeric(2);
		return(generatedString);
	}
	
	public static String firstName()
	{
		String generatedString = RandomStringUtils.randomNumeric(2);
		return("Tom" +generatedString);
	}
	
	public static String lastName()
	{
		String generatedString = RandomStringUtils.randomNumeric(2);
		return("Peter" +generatedString);
	}
	
	public static String userName()
	{
		String generatedString = RandomStringUtils.randomNumeric(2);
		return("TomPeter" +generatedString);
	}
	
	public static String password()
	{
		String generatedString = RandomStringUtils.randomNumeric(4);
		return("Tester" +generatedString);
	}
	
	public static String emailID()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(8);
		return(generatedString+"Gmail.Com");
	}
}
