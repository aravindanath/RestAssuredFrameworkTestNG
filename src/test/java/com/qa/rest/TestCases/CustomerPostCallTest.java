package com.qa.rest.TestCases;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Utilities.RandomUtilities;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CustomerPostCallTest extends TestBase
{
	public static HashMap map = new HashMap();
	
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeClass
	public void createCustomerTest()
	{
		Log.info("*******Customer Post Call Test Case Execution is Started*******");
		
		TestBase.initialization();
		
		//Define Base URL.
		RestAssured.baseURI = property.getProperty("CustomerPostCall");
		
		//Define Http Request.
		httpRequest = RestAssured.given();
		
		//Create Json Object with all the Fields.
		//Rest Assured does not provide any Utility to Create Json Object.
		//To do that we need to use 3rd Party API <<org.json>>.
		//Each time we need to pass unique Values in Json PayLoad else we get User already Exists.
		org.json.simple.JSONObject requestJson = new org.json.simple.JSONObject();
		requestJson.put("FirstName", RandomUtilities.firstName());
		requestJson.put("LastName", RandomUtilities.lastName());
		requestJson.put("UserName", RandomUtilities.userName());
		requestJson.put("Password", RandomUtilities.password());		
		requestJson.put("Email", RandomUtilities.emailID());
		
		//Add Header.
		httpRequest.header("Content-Type", "application/json");

		//Add Json PayLoad to the Body of the Request.
		httpRequest.body(requestJson.toJSONString());
		
		//Post Request and Get Response by Hitting API with Path Parameter Or Query Parameter.
		//Auth Key has to be Passed, if there is Any.
		//Service URL and Path Parameter Or Query Parameter can be called as URI.
		response = httpRequest.request(Method.POST, property.getProperty("CustomerPostCallParameter"));
	}
	
	@Test(priority=1)
	public void validateResponseBody()
	{
		//Converting Response Object to String >> asString() is same is toString().
		//asString() is provided by RestAssured.
		String responseBody = response.getBody().asString();
		System.out.println("The Response Body is " +responseBody);
		Assert.assertEquals(responseBody.contains("OPERATION_SUCCESS"), true);
		Log.info("-----Response Body is Printed and Validated-----");
	}
		
	@Test(priority=2)
	public void validateStatusCode()
	{
		int statusCode = response.getStatusCode();

		//To Get Key Value of Json Body by using Json Path.
		//Created an Object of JsonPath as Locally to put Conditions.
		JsonPath jsonPathValue = response.jsonPath();
		if(response.getStatusCode() == TestUtility.RESPONSE_CODE_201)
		{
			System.out.println("The Status Code is ::: " +statusCode);
			Assert.assertEquals(statusCode, TestUtility.RESPONSE_CODE_201);
			Log.info("-----Status Code is Printed and Validated-----");
			
			String successCode = jsonPathValue.get("SuccessCode");
			System.out.println("The Value of Success Code is ::: " +successCode);
			Assert.assertEquals(successCode, "OPERATION_SUCCESS");
			
			String message = jsonPathValue.get("Message");
			System.out.println("The Value of Message is ::: " +message);
			Assert.assertEquals(message, "Operation completed successfully");
			Log.info("-----Success Code and Success Message is Printed and Validated-----");
		}
		else if(response.getStatusCode() == TestUtility.RESPONSE_CODE_200)
		{
			System.out.println("The Status Code is ::: " +statusCode);
			Assert.assertEquals(statusCode, TestUtility.RESPONSE_CODE_200);
			Log.info("-----Status Code is Printed and Validated-----");
						
			String fauldId = jsonPathValue.get("FaultId");
			System.out.println("The Value of Fauld Id is ::: " +fauldId);
			Assert.assertEquals(fauldId, "User already exists");
			
			String faultMessage = jsonPathValue.get("fault");
			System.out.println("The Value of Fault Message is ::: " +faultMessage);
			Assert.assertEquals(faultMessage, "FAULT_USER_ALREADY_EXISTS");
			Log.info("-----Fault Id and Fault Message is Printed and Validated-----");
		}
	}
	
	@Test(priority=3)
	public void printStatusLine()
	{
		System.out.println("The Status Line is ::: " +response.statusLine());
		Log.info("-----Status Line is Printed-----");
	}
	
	@Test(priority=4)
	public void printAllHeaders()
	{
		System.out.println("::::::::::::::::::Headers:::::::::::::::::::::");
		Headers headers = response.getHeaders();
		System.out.println(headers);
		Log.info("-----Printed All Headers-----");
						
		String contentLength = response.getHeader("Content-Length");
		System.out.println("The Content Length of Header is ::: " +contentLength);
						
		String contentType = response.getHeader("Content-Type");
		System.out.println("The Content Type of Header is ::: " +contentType);
		Log.info("-----Printed Indivdual Headers-----");
	}
	
	@AfterClass
	public void tearDown()
	{
		Log.info("*******Customer Post Call Test Case Execution is Completed*******");
	}
}

