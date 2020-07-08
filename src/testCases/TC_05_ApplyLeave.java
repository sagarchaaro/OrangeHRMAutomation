package testCases;

import java.util.Map;

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
import pages.BaseClass;
import pages.Home_Page;
import pages.Leave_Page;
import pages.Login_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;

public class TC_05_ApplyLeave extends Suite{
		//CLASS VARIABLE DECLARATION
		public static String timestamp, screenshotPath, iBrowser,url, excelPath, testName;
		public static Map<String, String> yaml;
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
			Reporter.log("The Testcase id executing is :"+testID);
			Log.info("The Testcase id executing is :"+testID);
			iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
			Log.info("The row no for Test Case is : " + iTestCase);
			iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_ApplyLeaveCases);
			Log.info("The row no for of test Data is : " + iTestData);
			iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
			Log.info("The Browser for the excecution is : " + iBrowser);

			// WEBDRIVER AND TIMESTAMP METHOD				
			driver = Utils.openBrowser(CommonMethod.yamlData,iBrowser);	
			new BaseClass(driver);
		}


		@Test
		public  void applyLeave() throws InterruptedException, Exception {
			
			Log.info("The Execution started for TC_05_AplyLeave");
			Reporter.log("The Execution started for TC_05_AplyLeave");

			Login_Page.login(iTestData, Constant.sheet_ApplyLeaveCases);	
			Log.info("Logged into OrangeHRM Application");
			logger.log(Status.INFO, "Logged into OrangeHRM Application");
			Reporter.log("Logged into OrangeHRM Application", true);
			
			Home_Page.verifyDashboard(reportPath);
			Log.info("Dashboard is verified");
			logger.log(Status.INFO, "Dashboard is verified");
			Reporter.log("Dashboard is verified", true);
			
			
			Home_Page.navigateMenu("Leave", "Apply");
			Log.info("Navigated to the Leave apply window");
			logger.log(Status.INFO, "Navigated to the Leave apply window");
			Reporter.log("Navigated to the Leave apply window", true);

			Leave_Page.SetLeaveData(iTestData);
			Log.info("Entered the Leave data");
			logger.log(Status.INFO, "Entered the Leave data");
			Reporter.log("Entered the Leave data", true);
			
			
			Leave_Page.warningHanddling(reportPath);
			Log.info("All warnning are handdled");
			logger.log(Status.INFO, "All warnning are handdled");
			Reporter.log("All warnning are handdled", true);
			
			CommonMethod.logoutJaveExecuter(driver);
			Log.info("Loggedout from the OrangeHRM application");
			logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
			Reporter.log("Loggedout from the OrangeHRM application", true);
			
			
			
			Login_Page.login(CommonMethod.yamlData.get("userName"), CommonMethod.yamlData.get("password"));
			Log.info("Logged into OrangeHRM Application as Admin");
			logger.log(Status.INFO, "Logged into OrangeHRM Application as Admin");
			Reporter.log("Logged into OrangeHRM Application as Admin", true);
			
			
			
			Home_Page.navigateMenu("Leave", "Leave List");
			Log.info("Navigated to the Leave List window");
			logger.log(Status.INFO, "Navigated to the Leave List window");
			Reporter.log("Navigated to the Leave List window", true);
			
			Leave_Page.approveLeave(reportPath);
			Log.info("Leave is approved");
			logger.log(Status.INFO, "Leave is approved");
			Reporter.log("Leave is approved", true);
			
			CommonMethod.logoutJaveExecuter(driver);
			Log.info("Loggedout from the OrangeHRM application");
			logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
			Reporter.log("Loggedout from the OrangeHRM application", true);
			
			Login_Page.login(iTestData,Constant.sheet_ApplyLeaveCases);
			Log.info("Logged into OrangeHRM Application");
			logger.log(Status.INFO, "Logged into OrangeHRM Application");
			Reporter.log("Logged into OrangeHRM Application", true);
			
			Home_Page.navigateMenu("Leave", "My Leave");
			Log.info("Navigated to the My Leave List window");
			logger.log(Status.INFO, "Navigated to the My Leave List window");
			Reporter.log("Navigated to the My Leave List window", true);
			
			Leave_Page.verifyLeaveApproval(reportPath);
			Log.info("Verified the approved Leave");
			logger.log(Status.INFO, "Verified the approved Leave");
			Reporter.log("Verified the approved Leave", true);
			
			CommonMethod.logoutJaveExecuter(driver);
			Log.info("Loggedout from the OrangeHRM application");
			logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
			Reporter.log("Loggedout from the OrangeHRM application", true);
			
			// LOGOUT AND CLOSING THE BROWSER.
		/*	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[text()='keyboard_arrow_down']")));
			driver.findElement(By.xpath("//i[text()='keyboard_arrow_down']")).click();
			driver.findElement(By.id("logoutLink")).click();*/
		}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{

		
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		logger.log(Status.PASS, "Testcase " +testName+ " is passed");
		Reporter.log( "Testcase " +testName+ " is passed",true);

		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(reportPath + "\\Fail_"+testName+".jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData(CommonMethod.reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases,excelPath);
			Log.info(CommonMethod.reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);

			logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(reportPath + "\\Fail_"+testName+".jpg").build());
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
