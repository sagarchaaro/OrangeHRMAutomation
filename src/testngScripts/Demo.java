package testngScripts;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Demo {
	@Test (priority=1)
	public void test(){
		Reporter.log("Hello World ..!!!", true);
		Reporter.log("This is test method", true);
	}
	
	@Test (priority=-1)
	public void test1(){
		Reporter.log("This is Test1", true);
	}
	
	@Parameters({"username", "password"})
	@Test (priority=5)
	public void add(@Optional("Sagar") String un, @Optional("sagar123") String pwd){
		int x=5, y=10;
		int z=x+y;
		Reporter.log("Addition of numbers is :"+z, true);
		Reporter.log("UserName is :"+un, true);
		Reporter.log("Password is :"+pwd, true);
	}
	
	@Test
	public void sub(){
		int x=5, y=10;
		int z=x-y;
		Reporter.log("Subtraction of numbers is :"+z, true);
	}
	
	@Test (priority=3, enabled=true)
	public void mul(){
		//throw new SkipException("Multiplication method is skipped");
		int x=5, y=10;
		int z=x*y;
		Reporter.log("Multiplication of numbers is :"+z, true);
		
	}
	
	@Test (priority=4)
	public void div(){
		int x=5, y=10;
		int z=x/y;
		Reporter.log("Division of numbers is :"+z, true);
	}
	
	@BeforeClass
	public void beforeClass(){
		Reporter.log("Before Class method", true);
	}
	
	@AfterClass
	public void afterClass(){
		Reporter.log("After Class method", true);
	}
	
	@BeforeMethod
	public void beforeMethod(){
		Reporter.log("BeforeMethod method", true);
	}
	
	@AfterMethod
	public void afterMethod(){
		Reporter.log("AfterMethod method", true);
	}
	
	@BeforeSuite
	public void beforeSuite(){
		Reporter.log("BeforeSuite method -Demo class", true);
	}
}
