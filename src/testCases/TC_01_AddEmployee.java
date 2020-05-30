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
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import pages.PIM_Page;
import utilities.Utils;
import utilities.ExcelConfig;


public class TC_01_AddEmployee {
	
	public static String timestamp, url, excelPath, screenshotPath, browser;
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
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}

	@Test
	public void addEmployee() throws Exception {
		Reporter.log("The Execution started for TC_01_AddEmployee",true);
		
		Login_Page.loginPageVerify();
		
		Login_Page.login(iTestData,Constant.sheet_AddEmployeeCases);
		
		Home_Page.navigateMenu("PIM", "Add Employee");
		
		PIM_Page.setEmployeePersonalData(iTestData);		
		
		PIM_Page.setEmployeeImportantData(iTestData);
		
		PIM_Page.setEmployeeData(iTestData);
		
		PIM_Page.verifyEmployeeData();
		
		CommonMethod.logoutJaveExecuter(driver);
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	
		
		if(result.getStatus() == ITestResult.SUCCESS){
			ExcelConfig.setCellData(PIM_Page.employeeName, iTestData, Constant.col_employeeName, Constant.sheet_AddEmployeeCases,excelPath);
			Reporter.log("The value "+PIM_Page.employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeName +" in the "+Constant.sheet_AddEmployeeCases,true);
			
			ExcelConfig.setCellData(PIM_Page.employeeID, iTestData, Constant.col_employeeID, Constant.sheet_AddEmployeeCases, excelPath);
			Reporter.log("The value "+PIM_Page.employeeID+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeID +" in the "+Constant.sheet_AddEmployeeCases,true);

			ExcelConfig.setCellData(PIM_Page.location, iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases,excelPath);
			Reporter.log("The value "+PIM_Page.location+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_location +" in the "+Constant.sheet_AddEmployeeCases,true);
			
		
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases, excelPath);
			Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases,true);
			
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases, excelPath);
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
