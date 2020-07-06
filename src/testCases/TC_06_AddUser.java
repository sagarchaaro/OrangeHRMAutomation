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

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import pages.Admin_Page;
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;

public class TC_06_AddUser extends Suite{
	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser,url, excelPath, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	static ExtentTest logger;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Log.info("The Project Path is:"+CommonMethod.projectpath);		
				
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
		testName=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1)+"_"+testID;
		logger=ExtentManage.getExtentTest(report, testName);
		Log.startTestCase(testName);	
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("The Testcase id executing is :"+testID);
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddUserCases);
		Log.info("The row no for of test Data is : " + iTestData);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(CommonMethod.yamlData,iBrowser);		
		new BaseClass(driver);
	}


	 @Test
	 public  void addUser() throws InterruptedException, Exception {		 
			
		Log.info("The Execution started for TC_06_AddUser");

		Login_Page.login(iTestData,Constant.sheet_AddUserCases);	
		Log.info("Logged into OrangeHRM Application");
		logger.log(Status.INFO, "Logged into OrangeHRM Application");
		Reporter.log("Logged into OrangeHRM Application", true);
		
		Home_Page.verifyDashboard(screenshotPath);
		Log.info("Dashboard is verified");
		logger.log(Status.INFO, "Dashboard is verified");
		Reporter.log("Dashboard is verified", true);
		
		Home_Page.navigateMenu("Admin", "User Management","Users");
		Log.info("Navigated to the User Management window");
		logger.log(Status.INFO, "Navigated to the User Management window");
		Reporter.log("Navigated to the User Management window", true);

		Admin_Page.storeUserInArray(iTestData);
		Log.info("Users data stored in the array");
		logger.log(Status.INFO, "Users data stored in the array");
		Reporter.log("Users data stored in the array", true);
		
		Admin_Page.SetNewUser(iTestData);
		Log.info("New user creation is completed ");
		logger.log(Status.INFO, "New user creation is completed ");
		Reporter.log("New user creation is completed", true);
		
		Admin_Page.verifyUser(iTestData, screenshotPath);
		Log.info("New user creation is verified ");
		logger.log(Status.INFO, "New user creation is verified ");
		Reporter.log("New user creation is verified", true);
		
		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Loggedout from the OrangeHRM application");
		logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
		Reporter.log("Loggedout from the OrangeHRM application", true);
		
		Log.info("The login will be done and validated for new user");
		
		Login_Page.login(Admin_Page.userName, Admin_Page.newPassword);	
		Log.info("Logged into OrangeHRM Application for New user");
		logger.log(Status.INFO, "Logged into OrangeHRM Application for New user");
		Reporter.log("Logged into OrangeHRM Application for New user", true);
		
		Admin_Page.vrifyUserlogin(screenshotPath);
		Log.info("New user logging is verified ");
		logger.log(Status.INFO, "New user logging is verified ");
		Reporter.log("New user logging is verified", true);
		
		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Loggedout from the OrangeHRM application");
		logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
		Reporter.log("Loggedout from the OrangeHRM application", true);
	 }
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{

	
		Log.info("Click action is performed on Logoout button for New User");
		
		if(result.getStatus() == ITestResult.SUCCESS){
		
		ExcelConfig.setCellData(Admin_Page.userName, iTestData, Constant.col_UserID, Constant.sheet_AddUserCases,excelPath);

		Log.info("The value "+Admin_Page.userName+" is written as userName against to RowNumber "+iTestData +", column Number " +Constant.col_UserID
				+" in the "+Constant.sheet_AddUserCases);
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		
		Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		logger.log(Status.PASS, "Testcase " +testName+ " is passed");
		Reporter.log( "Testcase " +testName+ " is passed",true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\_Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData(CommonMethod.reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(CommonMethod.reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(reportPath + "\\Fail.jpg").build());
			Reporter.log( "Testcase " +testName+ " is failed",true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+CommonMethod.reason);
			logger.log(Status.SKIP, "Testcase " +testName+ " is skipped");
			Reporter.log("Testcase " +testName+ " is skipped",true);
		}
		
		driver.quit();
		Log.endTestCase();
		report.flush();
	}

}
