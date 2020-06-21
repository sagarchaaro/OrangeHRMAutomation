package testCases;

import org.apache.log4j.xml.DOMConfigurator;
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
import utilities.Log;
import utilities.Utils;

public class TC_00_Login {
	public static String timestamp, screenshotPath, browser, reason,url, excelPath, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Log.info("The Project Path is:"+CommonMethod.projectpath);
		
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		screenshotPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.projectpath+ screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("Excel sheet is set");
		driver = Utils.openBrowser(CommonMethod.yamlData, "Chrome");
		new BaseClass(driver);	
		
		Login_Page.loginPageVerify();			
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{			
		
		Log.info("Before Method");	
		
	}
	@Test (dataProviderClass=CommonMethod.class, dataProvider="Login")
	public void loginValidation(String userName, String password, String testID) throws Exception{
		testName=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1)+"_"+testID;
		Log.startTestCase(testName);	
		Log.info("Username from data provider is: "+userName);
		Log.info("Password from data provider is:"+password);
		Log.info("TestCase Id from data provider is:"+testID);
		
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);		
				
		Login_Page.login(userName, password);
		
		Thread.sleep(3000);			
		
		Home_Page.verifyDashboard(screenshotPath);

		CommonMethod.logoutJaveExecuter(driver);
				
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		Log.info("After Method");
		
		if(result.getStatus() == ITestResult.SUCCESS){
			
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases);
	
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\_Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+reason);
		}

    }
	@AfterClass
	public void afterClass() throws Exception{
		
		driver.quit();
		Reporter.log("After Class,Execution completed",true);
		Log.endTestCase();
	}
}
	