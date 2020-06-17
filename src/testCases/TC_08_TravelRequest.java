package testCases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import pages.Admin_Page;
import pages.BaseClass;
import pages.Expense_Page;
import pages.Home_Page;
import pages.Login_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.Log;

public class TC_08_TravelRequest {
	
	public static String timestamp, url, screenshotPath, browser, excelPath, vacancy_Name, employee_Name, currency_Name, dest_Address, requestStatus, requestID,reason;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath, true);
		Log.info("The Project Path is:"+CommonMethod.projectpath );
		
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		screenshotPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.projectpath+ screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
			
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("The Testcase id executing is :"+testID );
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase );
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TravelRequestCases);
		Log.info("The row no for test Data is : " + iTestData );
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + browser );
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	
	@Test
	public void travelRequest() throws Exception {
		Reporter.log("The Execution started for TC_08_TravelRequest",true );
		Log.info("The Execution started for TC_08_TravelRequest" );
		// LOAD AND READ THE PROPERTIES FILE
		
		// LOGIN AND DASHBOARD VALDATION

		Login_Page.loginPageVerify();
		
		Login_Page.login(iTestData,Constant.sheet_TravelRequestCases);
		
		// CHANGING USER PASSWORD
		Home_Page.navigateMenu("Admin", "User Management", "Users");
		
		employee_Name = Admin_Page.changePassword(iTestData);

		CommonMethod.logoutJaveExecuter(driver);

		// LOGGING INTO USER PROFILE
		Login_Page.loginWithNewUser(employee_Name);

		Utils.screenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		Log.info("Screen shot is  taken for the employee login " );
		Thread.sleep(2000);
		
		Home_Page.navigateMenu("Expense", "Travel Requests", "My Travel Requests");
		
		Expense_Page.currencyDetails();

		Expense_Page.travelInformation(iTestData);
		
		Expense_Page.travelRequestEstimates(iTestData);
		
		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		Log.info("Screen shot is taken for Employee List " );
		
		Expense_Page.submitRequestAndGettingRequestID();

		CommonMethod.logoutJaveExecuter(driver);

		Login_Page.login(iTestData,Constant.sheet_TravelRequestCases);
		
		Home_Page.navigateMenu("Expense", "Travel Requests", "Employee Travel Requests");
		
		Expense_Page.approveRequest();
		Thread.sleep(1000);
		CommonMethod.logoutJaveExecuter(driver);

		Login_Page.loginWithNewUser(employee_Name);

		Utils.screenShot(screenshotPath + "\\OrangeHRMEmplopyeeLogin.jpg", driver);
		Log.info("Screen shot is taken for Employee Login " );
		
		Home_Page.navigateMenu("Expense", "Travel Requests", "My Travel Requests");

		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		Log.info("Screen shot is taken for Approved Travel Request " );
		Thread.sleep(3000);
		
		CommonMethod.logoutJaveExecuter(driver);
		
		}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		
		// ENTERING IN EXCEL SHEET
		
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_Employee_Name, Constant.sheet_TravelRequestCases,excelPath);
		Log.info("The value "+employee_Name+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_Employee_Name+" in the "+Constant.sheet_TravelRequestCases );
		ExcelConfig.setCellData(currency_Name, iTestData, Constant.col_Currency_Name, Constant.sheet_TravelRequestCases,excelPath);
		Log.info("The value "+currency_Name+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Currency_Name+" in the "+Constant.sheet_TravelRequestCases );
		ExcelConfig.setCellData(dest_Address, iTestData, Constant.col_Destination_Address,Constant.sheet_TravelRequestCases, excelPath);
		Log.info("The value "+dest_Address+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Destination_Address+" in the "+Constant.sheet_TravelRequestCases );
		ExcelConfig.setCellData(requestStatus, iTestData, Constant.col_Request_Status,Constant.sheet_TravelRequestCases, excelPath);
		Log.info("The value "+requestStatus+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_Status+" in the "+Constant.sheet_TravelRequestCases );
		ExcelConfig.setCellData(requestID, iTestData, Constant.col_Request_ID, Constant.sheet_TravelRequestCases,excelPath);
		Log.info("The value "+requestID+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_ID+" in the "+Constant.sheet_TravelRequestCases );

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_NewPhoneNo, Constant.sheet_TestCases,excelPath);
		Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, excelPath);
		Log.info("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases );
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+reason );
		}
		
		driver.quit();
		Reporter.log("TestCase execution is completed",true);
		Log.endTestCase();
	}

}
