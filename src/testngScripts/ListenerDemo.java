package testngScripts;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerDemo implements ITestListener{

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
