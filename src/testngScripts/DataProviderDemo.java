package testngScripts;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderDemo {
	
	@Test (dataProviderClass=DataProviderDemo1.class, dataProvider="Login")
	public void testDemo(String userName, String password){
		System.out.println("Username is: "+userName);
		System.out.println("Password is:"+password);
	}
	
	
}
