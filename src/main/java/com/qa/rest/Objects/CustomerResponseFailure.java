package com.qa.rest.Objects;

public class CustomerResponseFailure 
{
	//POJO Objects - [Plain Old Java Object].
	public String FaultId;
	public String fault;
	
	//Response we are getting after hitting API Successfully for which data is already created.
	//{
	//	   "FaultId": "User already exists",
	//	   "fault": "FAULT_USER_ALREADY_EXISTS"
	//}
}
