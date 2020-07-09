package testngScripts;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Demo11 implements ITestListener{
	@Test
	public void test1(){
		System.out.println("This is the passed scenrio - test1");
	}
	
	@Test
	public void test2(){
		
		System.out.println("This is the passed scenrio - test2");
		method1();
	}
	
	@Test
	public void test3(){
		System.out.println("This is the Failed scenrio - test3");
		Assert.assertTrue(false);
	}
	
	@Test
	public void test4(){
		System.out.println("This is the Failed scenrio - test4");
		Assert.assertTrue(false);
	}
	
	@Test
	public void test5(){
		System.out.println("This is the Skipped scenrio - test5");
		throw new SkipException("This is the Skipped scenrio - test5");
	}
	
	@Test
	public void test6(){
		System.out.println("This is the Skipped scenrio - test6");
		throw new SkipException("This is the Skipped scenrio - test6");
	}
	
	public static void method1(){
		System.out.println("This is the method1");
	}
	
	public void onTestStart(ITestResult result) {
		System.out.println("This is the ListernerDemo - onTestStart");
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("This is the ListernerDemo - onTestSuccess");
		
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("This is the ListernerDemo - onTestFailure");
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		System.out.println("This is the ListenerDemo - onStart");
		
	}

	public void onFinish(ITestContext context) {
		System.out.println("This is the ListenerDemo - onFinish");
		
	}


}
