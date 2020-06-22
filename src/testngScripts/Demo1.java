package testngScripts;

import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Demo1 {
	@Test
	public void test2(){
		Reporter.log("Core Java", true);
	}
	
	@Test 
	public void test3(){
		Reporter.log("Advanced Java", true);
	}
	
	@Test
	public void test4(){
		Reporter.log("Java ME", true);
	}
	
	@BeforeSuite
	public void beforeSuite(){
		Reporter.log("BeforeSuite method", true);
	}
	
	@AfterSuite
	public void afterSuite(){
		Reporter.log("AfterSuite method", true);
	}
}
