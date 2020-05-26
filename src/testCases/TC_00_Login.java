package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import utilities.ExcelConfig;
import utilities.Utils;

public class TC_00_Login {
	public static String timestamp, screenshotPath, browser, reason,url, excelPath;
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
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		System.out.println("Excel sheet is set");
		driver = Utils.openBrowser(CommonMethod.yamlData, "Chrome");
		new BaseClass(driver);	
		
		Login_Page.loginPageVerify();			
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{			
		
		System.out.println("Before Method");	
		
	}
	@Test (dataProviderClass=CommonMethod.class, dataProvider="Login")
	public void loginValidation(String userName, String password, String testID) throws Exception{
		
		System.out.println("Username from data provider is: "+userName);
		System.out.println("Password from data provider is:"+password);
		System.out.println("TestCase Id from data provider is:"+testID);
		
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);		
				
		Login_Page.login(userName, password);
		
		Thread.sleep(3000);			
		
		Home_Page.verifyDashboard(screenshotPath);

		CommonMethod.logoutJaveExecuter(driver);
				
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		System.out.println("After Method");
		
		if(result.getStatus() == ITestResult.SUCCESS){
			
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases,true);
	
		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}

    }
	@AfterClass
	public void afterClass() throws Exception{
		
		driver.quit();
		Reporter.log("After Class,Execution completed,true");
	}
}
	