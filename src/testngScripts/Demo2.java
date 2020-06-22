package testngScripts;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Demo2 {
	@BeforeTest
	public void beforeTest(){
		Reporter.log("BeforeTest method", true);
	}
	
	@AfterTest
	public void afterTest(){
		Reporter.log("AfterTest method", true);
	}
	
	@Test
	public void countryName(){
		Reporter.log("Country - India", true);
	}
	
	@Test
	public void continent(){
		Reporter.log("Continent - Asia", true);
	}
	
	@BeforeMethod
	public void beforeMethod(){
		Reporter.log("BeforeMethod method - Demo2", true);
	}
	
	@AfterMethod
	public void afterMethod(){
		Reporter.log("AfterMethod method - Demo2", true);
	}
	
	@BeforeClass
	public void beforeClass(){
		Reporter.log("BeforClass method - Demo2", true);
	}
	
	@AfterClass
	public void afterClass(){
		Reporter.log("AfterClass method - Demo2", true);
	}
}
