package com.qa.rest.TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Utilities.RandomUtilities;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PutCallTest extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	String employeeName = RandomUtilities.employeeName();
	String employeeSalary = RandomUtilities.employeeSalary();
	String employeeAge = RandomUtilities.employeeAge();
	
	@BeforeClass
	public void putCallTest()
	{
		Log.info("*******Put Call Test Case Execution is Started*******");
		
		TestBase.initialization();
		
		RestAssured.baseURI = property.getProperty("PutCall");
		
		httpRequest = RestAssured.given();
		
		org.json.simple.JSONObject requestJson = new org.json.simple.JSONObject();
		requestJson.put("name", employeeName);
		requestJson.put("salary", employeeSalary);
		requestJson.put("age", employeeAge);
		
		httpRequest.header("Content-Type", "application/json");
		
		httpRequest.body(requestJson.toJSONString());
		
		response = httpRequest.request(Method.PUT, property.getProperty("PutCallParameter"));
	}
	
	@Test(priority=1)
	public void validateResponseBody()
	{		
		String responseBody = response.getBody().asString();
		System.out.println("The Response Body is " +responseBody);
		
		Assert.assertEquals(responseBody.contains(employeeName), true);
		Assert.assertEquals(responseBody.contains(employeeSalary), true);
		Assert.assertEquals(responseBody.contains(employeeAge), true);
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
			System.out.println(header.getName()+ "	"+header.getValue());
		}
		Log.info("-----Printed Headers in 2 Ways-----");
		
		//To Print Individual Header and can be Validate as Well.
		String contentLength = response.getHeader("Content-Length");
		System.out.println("The Content Length of Header is ::: " +contentLength);
		
		String contentType = response.getHeader("Content-Type");
		System.out.println("The Content Type of Header is ::: " +contentType);
		Log.info("-----Printed Headers Individually-----");
	}
	
	@AfterClass
	public void tearDown()
	{
		Log.info("*******Put Call Test Case Execution is Completed*******");
	}
}
