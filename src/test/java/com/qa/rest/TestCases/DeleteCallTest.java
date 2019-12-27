package com.qa.rest.TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeleteCallTest extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeClass
	public void deleteCallTest()
	{
		Log.info("*******Delete Call Test Case Execution is Started*******");
		
		TestBase.initialization();

		RestAssured.baseURI = property.getProperty("DeleteCall");
		
		httpRequest = RestAssured.given();
		
		response = httpRequest.request(Method.DELETE, property.getProperty("DeleteCallParameter"));
	}
	
	@Test(priority=1)
	public void validateStatusCode()
	{
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is ::: " +statusCode);
		Assert.assertEquals(statusCode, TestUtility.RESPONSE_CODE_204);
		Log.info("-----Status Code is Printed and Validated-----");
	}
	
	@Test(priority=2)
	public void printHeaders()
	{
		Headers headers = response.getHeaders();
		Log.info("-----Headers-----");
		System.out.println(headers);
		Log.info("-----Printed All Headers-----");
		
		String connection = response.getHeader("Connection");
		System.out.println("The Connection of Header is ::: " +connection);
				
		String server = response.getHeader("Server");
		System.out.println("The Server of Header is ::: " +server);
		Log.info("-----Printed Indivdual Headers-----");
	}
	
	@AfterClass
	public void tearDown()
	{
		Log.info("*******Delete Call Test Case Execution is Completed*******");
	}
}
