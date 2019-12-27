package com.qa.rest.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.rest.BaseClass.TestBase;

public class TestUtility extends TestBase
{
	//1.
	//Common Http Status Codes.
	public final static int RESPONSE_CODE_200 = 200;
	public final static int RESPONSE_CODE_201 = 201;
	public final static int RESPONSE_CODE_204 = 204;
	public final static int RESPONSE_CODE_400 = 400;
	public final static int RESPONSE_CODE_401 = 401;
	
	//2.
	//Data Provider Utility.
	//Below Function is used for getting Data from Excel.	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/src/main/java/com/qa/rest/TestData/RestAssuredData.xlsx";
	static Workbook book;
	static Sheet sheet;
	
	public static Object[][] getDataFromSheet(String sheetName)
	{
		FileInputStream file = null;
		try 
		{
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		try
		{
			book = WorkbookFactory.create(file);
		} 
		catch (InvalidFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) 
		{
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++)
			{
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}
	
	//3.
	//To Append Date with Extent Report.
	public static String getSystemDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyy_HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	//4.
	//Set Date For Log4J.
	public static void setDateForLog4J() 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("_ddMMyyy_hhmmss");
		System.setProperty("current_date", dateFormat.format(new Date()));
		PropertyConfigurator.configure("./src/main/resources/log4j.properties");
	}
}