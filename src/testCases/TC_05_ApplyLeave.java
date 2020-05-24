package testCases;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import utilities.Utils;
import utilities.ExcelConfig;

public class TC_05_ApplyLeave {
		//CLASS VARIABLE DECLARATION
		public static String timestamp, screenshotPath, iBrowser,reason,url, excelPath;
		public static Map<String, String> yaml;
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
			iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_ApplyLeaveCases);
			Reporter.log("The row no for of test Data is : " + iTestData,true);
			iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
			Reporter.log("The Browser for the excecution is : " + iBrowser,true);

			// WEBDRIVER AND TIMESTAMP METHOD				
			driver = Utils.openBrowser(CommonMethod.yamlData,iBrowser);	
			new BaseClass(driver);
		}


		@Test
		public  void applyLeave() throws InterruptedException, Exception {
			WebDriverWait wait = new WebDriverWait(driver, 30);	
			Reporter.log("The Execution started for TC_05_AplyLeave",true);

			Login_Page.login(iTestData);	
			
			Home_Page.verifyDashboard(screenshotPath);

			// CLICKING FOR APPLY LEAVE FORM AND APPLY

			String leaveType = ExcelConfig.getCellData(iTestData, Constant.col_leaveType, Constant.sheet_ApplyLeaveCases);
			Reporter.log("The leaveType read from excel is:" + leaveType,true);
			String leaveDesc = ExcelConfig.getCellData(iTestData, Constant.col_leaveDesc, Constant.sheet_ApplyLeaveCases);
			Reporter.log("The leaveDesc read from excel is:" + leaveDesc,true);
			driver.findElement(By.xpath("//span[text()='Leave']")).click();
			Reporter.log("Click action is performed on Leave in the Menu bar",true);
			driver.findElement(By.xpath("//span[text()='Apply']")).click();
			Reporter.log("Click action is performed on Apply in the Menu bar",true);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select-dropdown']")));
			driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
			driver.findElement(By.xpath("//span[text()='" + leaveType + "']")).click();
			Reporter.log("The value "+ leaveType+" is selected as leaveType in the drop down",true);
			driver.findElement(By.xpath("//div[@class='input-field col s12 m12 l12']/textarea")).sendKeys(leaveDesc);
			Reporter.log("The value "+ leaveDesc+" is entered as leaveDesc in the text-box",true);
			// From date
			String leaveDateFrom = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateFrom,
				Constant.sheet_ApplyLeaveCases);
			Reporter.log("The leaveDateFrom read from excel is:" + leaveDateFrom,true);
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]")).click();
			Thread.sleep(5000);
			Reporter.log("Click action is performed on the Calender for From Date",true);

			CommonMethod.date_HRM(leaveDateFrom, driver, 1);

			// Select To Date
			String leaveDateTo = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateTo,
				Constant.sheet_ApplyLeaveCases);
			Reporter.log("The leaveDateTo read from excel is:" + leaveDateTo,true);
			driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[2]")).click();
			Thread.sleep(10000);
			Reporter.log("Click action is performed on the Calender for To Date",true);
			CommonMethod.date_HRM(leaveDateTo, driver, 2);

		
			driver.findElement(By.xpath("//button[@class='btn waves-effect waves-green']")).click();
			Reporter.log("Click action is performed on the Save Button",true);

			// INSUFFICENT LEAVE WARNNING PAGE

			try {
				WebElement element = driver.findElement(By.xpath("//div[@class='modal-heading']/h4"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
				Reporter.log("Found the xpath");

				if (element.isDisplayed()) {
					WebElement element1 = driver
						.findElement(By.xpath("//a[@class='modal-action waves-effect btn primary-btn']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);

					Reporter.log("Click action is performed on Ok button for Insufficent leave Warnning",true);
				}

			} catch (Exception user) {
			Reporter.log(" There is no warnning for Insufficent Balance,Leave applied",true);
			}

			// OVERLAPPING LEAVE REQUEST PAGE

			try {
				WebElement element2 = driver.findElement(By.xpath("//div[@class='modal-heading']/h4"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);

				if (element2.isDisplayed()) {
					WebElement element3 = driver.findElement(By.xpath("//a[@class='modal-action waves-effect btn']"));
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element3);
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", element3);

					Reporter.log("Leave Overlapping,Click action is performed on Close button",true);
				}
			} catch (Exception user) {
				Reporter.log(" There is no warnning for Overlapping  ,Leave applied",true);
			}

			try {
				if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
					Reporter.log("Successfully Leave applied message is verified",true);
				}

			} catch (Exception user) {
				Reporter.log("Leave is not applied Successfully",true);
			}

			// LOGOUT AND CLOSING THE BROWSER.
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[text()='keyboard_arrow_down']")));
			driver.findElement(By.xpath("//i[text()='keyboard_arrow_down']")).click();
			driver.findElement(By.id("logoutLink")).click();
		}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{

		driver.quit();
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases,true);

		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases,excelPath);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}		
		
		Reporter.log("TestCase execution is completed",true);
	}

}
