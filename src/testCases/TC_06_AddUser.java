package testCases;

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
import pages.Home_Page;
import pages.Login_Page;
import utilities.Utils;
import utilities.ExcelConfig;

public class TC_06_AddUser {
	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser,url, excelPath;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		
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
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddUserCases);
		Reporter.log("The row no for of test Data is : " + iTestData,true);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + iBrowser,true);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(CommonMethod.yamlData,iBrowser);		
		new BaseClass(driver);
	}


	 @Test
	 public  void addUser() throws InterruptedException, Exception {		 
			
		Reporter.log("The Execution started for TC_06_AddUser",true);

		Login_Page.login(iTestData,Constant.sheet_AddUserCases);	
		
		Home_Page.verifyDashboard(screenshotPath);
		
		Home_Page.navigateMenu("Admin", "User Management","Users");

		Admin_Page.storeUserInArray(iTestData);
		
		Admin_Page.SetNewUser(iTestData);
		
		Admin_Page.verifyUser(iTestData, screenshotPath);
		
		CommonMethod.logoutJaveExecuter(driver);
		
		Reporter.log("The login will be done and validated for new user",true);
		
		Login_Page.login(Admin_Page.userName, Admin_Page.newPassword);	
		
		Admin_Page.vrifyUserlogin(screenshotPath);
		
		CommonMethod.logoutJaveExecuter(driver);
	 }
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{

	
		Reporter.log("Click action is performed on Logoout button for New User",true);
		
		if(result.getStatus() == ITestResult.SUCCESS){
		
		ExcelConfig.setCellData(Admin_Page.userName, iTestData, Constant.col_UserID, Constant.sheet_AddUserCases,excelPath);

		Reporter.log("The value "+Admin_Page.userName+" is written as userName against to RowNumber "+iTestData +", column Number " +Constant.col_UserID
				+" in the "+Constant.sheet_AddUserCases,true);
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases,true);
		
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\_Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(CommonMethod.reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Reporter.log(CommonMethod.reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+CommonMethod.reason,true);
		}
		
		driver.quit();
		Reporter.log("TestCase execution is completed",true);

	}

}
