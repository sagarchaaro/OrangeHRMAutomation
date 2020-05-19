package testCases;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import pages.BaseClass;
import utilities.ExcelConfig;
import utilities.Utils;

public class TC_00_Login {
	public static String timestamp, screenshotPath, browser, reason;
	public static Map<String, String> yaml;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);	
		yaml = CommonMethod.yamlFileRead(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		System.out.println("Excel sheet is set");
		driver = Utils.openBrowser(yaml, "Chrome");
		new BaseClass(driver);
		
		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{			
		
		System.out.println("Before Method");	
		
	}
	@Test (dataProviderClass=CommonMethod.class, dataProvider="Login")
	public void loginValidation(String userName, String password, String testID) throws Exception{
		System.out.println("Username is: "+userName);
		System.out.println("Password is:"+password);
		System.out.println("TestCase Id is:"+testID);
		
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);	
		

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);
		Thread.sleep(3000);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//li[contains(text(),'Dashboard')]")), "Dashboard"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ",true);

		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case is failed",true);
			reason="Dashboard is not available";		
			Assert.assertTrue(false, "Dashboard is not available, Test case is failed");
		}
	// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
				
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		System.out.println("After Method");
		
		if(result.getStatus() == ITestResult.SUCCESS){
			
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases,true);
			
			ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
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
	