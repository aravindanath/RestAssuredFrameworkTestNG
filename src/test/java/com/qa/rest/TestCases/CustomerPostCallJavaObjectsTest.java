package com.qa.rest.TestCases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Objects.CustomerResponseFailure;
import com.qa.rest.Objects.CustomerResponseSuccess;
import com.qa.rest.Utilities.RandomUtilities;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CustomerPostCallJavaObjectsTest extends TestBase
{
	//Here We are using POJO Objects Concept to Validate Response Body.
	
	public static HashMap map = new HashMap();
	
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeClass
	public void createCustomerTest()
	{
		Log.info("*******Customer Post Call Java Objects Test Case Execution is Started*******");
		
		TestBase.initialization();

		RestAssured.baseURI = property.getProperty("CustomerPostCall");
		
		httpRequest = RestAssured.given();
		
		org.json.simple.JSONObject requestJson = new org.json.simple.JSONObject();
		requestJson.put("FirstName", RandomUtilities.firstName());
		requestJson.put("LastName", RandomUtilities.lastName());
		requestJson.put("UserName", RandomUtilities.userName());
		requestJson.put("Password", RandomUtilities.password());		
		requestJson.put("Email", RandomUtilities.emailID());
		
		httpRequest.header("Content-Type", "application/json");
		
		httpRequest.body(requestJson.toJSONString());
		
		response = httpRequest.request(Method.POST, property.getProperty("CustomerPostCallParameter"));
	}
	
	@Test(priority=1)
	public void printResponseBody()
	{
		String responseBody = response.getBody().asString();
		System.out.println("The Response Body is " +responseBody);
		Log.info("-----Response Body is Printed-----");
	}
	
	@Test(priority=2)
	public void printStatusCode()
	{
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is ::: " +statusCode);
		Log.info("-----Status Code is Printed-----");
	}
	
	@Test(priority=3)
	public void printStatusLine()
	{
		System.out.println("The Status Line is ::: " +response.statusLine());
		Log.info("-----Status Line is Printed-----");
	}			
	
	@Test(priority=4)
	public void validateResponseBody()
	{
		//De-Serialization - as() method Converting the Json Response into CustomerResponse Class.
		//Here as() method is Converting the Json Response into Java Objects.
		if(response.statusCode() == TestUtility.RESPONSE_CODE_201)
		{
			
			CustomerResponseSuccess customerResponse = response.as(CustomerResponseSuccess.class);
			
			//Printing in Console - Since we are converting Json Response into Java Objects.
			//We can use it anywhere it is Required and Putting Assertions.
			System.out.println("The Success Code is ::: " +customerResponse.SuccessCode);
			Assert.assertEquals("OPERATION_SUCCESS", customerResponse.SuccessCode);
			
			System.out.println("The Success Message is ::: " +customerResponse.Message);
			Assert.assertEquals("Operation completed successfully", customerResponse.Message);
			Log.info("-----Success Code and Success Message is Printed and Validated-----");
		}
		else if(response.statusCode() == TestUtility.RESPONSE_CODE_200)
		{
			CustomerResponseFailure customerResponse = response.as(CustomerResponseFailure.class);
		
			//Printing in Console - Since we are converting Json Response into Java Objects.
			//We can use it anywhere it is Required and Putting Assertions.
			System.out.println("The Fault Id is ::: " +customerResponse.FaultId);
			Assert.assertEquals("User already exists", customerResponse.FaultId);
			
			System.out.println("The Fault Message is ::: " +customerResponse.fault);
			Assert.assertEquals("FAULT_USER_ALREADY_EXISTS", customerResponse.fault);
			Log.info("-----Fault Id and Fault Message is Printed and Validated-----");
		}
	}
	
	@AfterClass
	public void tearDown()
	{
		Log.info("*******Customer Post Call Java Objects Test Case Execution is Completed*******");
	}
}
