package com.qa.rest.TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.rest.BaseClass.TestBase;
import com.qa.rest.Utilities.TestUtility;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherDataGetCallTest extends TestBase
{
	RequestSpecification httpRequest;
	Response response;
	
	@BeforeMethod
	public void setUp()
	{
		Log.info("*******Weather Data Get Call Test Case Execution is Started*******");
		
		TestBase.initialization();
	}
	
	String sheetName = "WeatherData";
	
	@DataProvider
	public Object[][] getData()
	{
		Object testdata[][] = TestUtility.getDataFromSheet(sheetName);
		return testdata;
	}
	
	@Test(priority=1, dataProvider="getData")
	public void getWeatherDataWithCityNameTest(String City, String HttpMethod, String Temperature, 
				String Humidity, String WeatherDescription, String WindSpeed, String WindDirectionDegree)
	{
		RestAssured.baseURI = property.getProperty("WeatherDataServiceUrl");
		
		httpRequest = RestAssured.given();
		
		response = httpRequest.request(Method.GET, "/"+City);
		
		String responseBody = response.getBody().asString();
		System.out.println("The Response Body is " +responseBody);
		Assert.assertEquals(responseBody.contains("City"), true);
		Log.info("-----Response Body is Printed and Validated-----");
		
		int statusCode = response.getStatusCode();
		System.out.println("The Status Code is ::: " +statusCode);
		Assert.assertEquals(statusCode, TestUtility.RESPONSE_CODE_200);
		Log.info("-----Status Code is Printed and Validated-----");
		
		System.out.println("The Status Line is ::: " +response.statusLine());
		Log.info("-----Status Line is Printed-----");
		
		Headers headers = response.getHeaders();
		Log.info("-----Printing all Headers-----");
		System.out.println(headers);
		Log.info("-----Printed All Headers-----");
		
		//To Print Individual Header and can be Validate as Well.
		String contentLength = response.getHeader("Content-Length");
		System.out.println("The Content Length of Header is ::: " +contentLength);
		
		String contentType = response.getHeader("Content-Type");
		System.out.println("The Content Type of Header is ::: " +contentType);
		Log.info("-----Printed Individual Headers-----");
		
		//To Get Key Value of Response Body Or Json Body by using Json Path.
		Log.info("-----Printing Each Value of Response Body Using Json Path-----");
		JsonPath jsonPathValue = response.jsonPath();
		
		String cityValue = jsonPathValue.get("City");
		System.out.println("The Value of City is ::: " +cityValue);
		Assert.assertEquals(cityValue, City);
		
		String temp = jsonPathValue.get("Temperature");
		System.out.println("The Value of Temperature is ::: " +temp);
		Assert.assertEquals(temp, Temperature);
		
		String humidity = jsonPathValue.get("Humidity");
		System.out.println("The Value of Humidity is ::: " +humidity);
		Assert.assertEquals(humidity, Humidity);
		
		String weatherDescription = jsonPathValue.get("WeatherDescription");
		System.out.println("The Value of WeatherDescription is ::: " +weatherDescription);
		Assert.assertEquals(weatherDescription, WeatherDescription);
		
		String windSpeed = jsonPathValue.get("WindSpeed");
		System.out.println("The Value of WindSpeed is ::: " +windSpeed);
		Assert.assertEquals(windSpeed, WindSpeed);
		
		String windDirectionDegree = jsonPathValue.get("WindDirectionDegree");
		System.out.println("The Value of WindDirectionDegree is ::: " +windDirectionDegree);
		Assert.assertEquals(windDirectionDegree, WindDirectionDegree);
		Log.info("-----Printed Each Value of Response Body Using Json Path-----");
	}
	
	@AfterMethod
	public void tearDown()
	{
		Log.info("*******Weather Data Get Call Test Case Execution is Completed*******");
	}
}
