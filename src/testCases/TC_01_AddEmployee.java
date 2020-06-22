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
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import pages.PIM_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;


public class TC_01_AddEmployee extends Suite{
	
	public static String timestamp, url, excelPath, screenshotPath, browser, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	static ExtentTest logger;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath, true);
		Log.info("The Project Path is:"+CommonMethod.projectpath);
		
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
			
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.
		testName=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1)+"_"+testID;
		
		logger=ExtentManage.getExtentTest(report, testName);
		
		
		Log.startTestCase(testName);
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("The Testcase id executing is :"+testID);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Log.info("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}

	@Test
	public void addEmployee() throws Exception {
		Log.info("The Execution started for TC_01_AddEmployee");
		
		Login_Page.loginPageVerify();
		Log.info("Title of the OrangeHRM Application is verified");
		logger.log(Status.INFO, "Title of the OrangeHRM Application is verified");
		
		Login_Page.login(iTestData,Constant.sheet_AddEmployeeCases);
		Log.info("Logged into OrangeHRM Application");
		logger.log(Status.INFO, "Logged into OrangeHRM Application");
		
		Home_Page.navigateMenu("PIM", "Add Employee");
		Log.info("Navigated to the AddEmployee window");
		logger.log(Status.INFO, "Navigated to the AddEmployee window");
		
		PIM_Page.setEmployeePersonalData(iTestData);	
		Log.info("Entered Personal Data of a Employee");
		logger.log(Status.INFO, "Entered Personal Data of a Employee");
		
		PIM_Page.setEmployeeImportantData(iTestData);
		Log.info("Entered Important Data of a Employee");
		logger.log(Status.INFO, "Entered Important Data of a Employee");
		
		PIM_Page.setEmployeeData(iTestData);
		Log.info("Entered Preferred Data of a Employee");
		logger.log(Status.INFO, "Entered Preferred Data of a Employee");
		
		PIM_Page.verifyEmployeeData();
		Log.info("AddEmployee Data is verified in the Employee List");
		logger.log(Status.INFO, "AddEmployee Data is verified in the Employee List");
		
		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Loggedout from the OrangeHRM application");
		logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	
		
		if(result.getStatus() == ITestResult.SUCCESS){
			ExcelConfig.setCellData(PIM_Page.employeeName, iTestData, Constant.col_employeeName, Constant.sheet_AddEmployeeCases,excelPath);
			Log.info("The value "+PIM_Page.employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeName +" in the "+Constant.sheet_AddEmployeeCases);
			
			ExcelConfig.setCellData(PIM_Page.employeeID, iTestData, Constant.col_employeeID, Constant.sheet_AddEmployeeCases, excelPath);
			Log.info("The value "+PIM_Page.employeeID+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeID +" in the "+Constant.sheet_AddEmployeeCases);

			ExcelConfig.setCellData(PIM_Page.location, iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases,excelPath);
			Log.info("The value "+PIM_Page.location+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_location +" in the "+Constant.sheet_AddEmployeeCases);
			
		
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases, excelPath);
			Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases);
			logger.log(Status.PASS, "Testcase " +testName+ " is passed");
			
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases, excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			
			ExcelConfig.setCellData(CommonMethod.reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(CommonMethod.reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			
			logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath + "\\Fail.jpg").build());
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+CommonMethod.reason);
			logger.log(Status.SKIP, "Testcase " +testName+ " is skipped");
		}
		
		driver.quit();
		Log.endTestCase();
		report.flush();
	}

}
