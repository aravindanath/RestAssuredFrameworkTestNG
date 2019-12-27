package com.qa.rest.AuthTestCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BasicAuthenticationTest extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeClass
	public void basicAuthentication()
	{
		Log.info("*******Basic Authentication Test Case Execution is Started*******");
		
		TestBase.initialization();
		
		RestAssured.baseURI = property.getProperty("BasicAuth");
				
		//############### Code to Handle Basic Authentication ################
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName("ToolsQA");
		authScheme.setPassword("TestPassword");
		RestAssured.authentication = authScheme;
						
		httpRequest = RestAssured.given();
				
		response = httpRequest.request(Method.GET, property.getProperty("Parameter"));		
	}
	
	@Test(priority=1)
	public void validateResponseBody()
	{
		String responseBody = response.getBody().asString();
		System.out.println("The Response Body is " +responseBody);
		Assert.assertEquals(responseBody.contains("OPERATION_SUCCESS"), true);
		Log.info("-----Response Body is Printed and Validated-----");
	}
		
	@Test(priority=2)
	public void validateStatusCode()
	{
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is ::: " +statusCode);
		Assert.assertEquals(statusCode, TestUtility.RESPONSE_CODE_200);
		Log.info("-----Status Code is Printed and Validated-----");
	}
	
	@Test(priority=3)
	public void printStatusLine()
	{
		System.out.println("The Status Line is ::: " +response.statusLine());
		Log.info("-----Status Line is Printed-----");
	}
		
	@Test(priority=4)
	public void printHeaders()
	{
		Log.info("-----Printing Headers in First Way-----");
		Headers headers = response.getHeaders();
		System.out.println(headers);
		
		Log.info("-----Printing Headers in Second Way-----");
		for(Header header : headers)
		{
			System.out.println(header.getName()+ "	" +header.getValue());
		}
		Log.info("-----Printed Headers in 2 Ways-----");
		
		//To Print Individual Header and can be Validated as Well.
		String contentLength = response.getHeader("Content-Length");
		System.out.println("The Content Length of Header is ::: " +contentLength);
		
		String contentType = response.getHeader("Content-Type");
		System.out.println("The Content Type of Header is ::: " +contentType);
		Log.info("-----Printed Headers Individually-----");
	}
		
	@Test(priority=5)
	public void validateResponseBodyValues()
	{
		Log.info("-----Printing Each Value of Response Body-----");
		JsonPath jsonPathValue = response.jsonPath();
	    	
	    String faultId = jsonPathValue.get("FaultId");
		System.out.println("The Fault Id Value is ::: " +faultId);
		Assert.assertEquals(faultId, "OPERATION_SUCCESS");
		
		String fault = jsonPathValue.get("Fault");
		System.out.println("The Fault Message Value is ::: " +fault);
		Assert.assertEquals(fault, "Operation completed successfully");
		Log.info("-----Printed Each Value of Response Body and Validated-----");
	}
	
	@AfterClass
	public void tearDown()
	{
		Log.info("*******Basic Authentication Test Case Execution is Completed*******");
	}
}