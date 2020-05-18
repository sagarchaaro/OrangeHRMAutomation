package frameworkScripts;

import java.util.List;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.ExcelConfig;
import utilities.RandomGenerator;
import utilities.Utils;

public class AddLoginData {
		public static String timestamp, screenshotPath, browser, reason, employee_Name, newPassword;
		public static Map<String, String> yaml;
		public static int iTestCase, iTestData;
		public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception {
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:" + CommonMethod.projectpath, true);
		yaml = CommonMethod.yamlFileRead(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
	}

	@Parameters({ "testID" })
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception {

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		Reporter.log("The Testcase id executing is :" + testID, true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase, true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_Login);
		Reporter.log("The row no for test Data is : " + iTestData, true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser, true);
		driver = Utils.openBrowser(yaml, browser);
	}

	@Test
	public void addLogin()throws Exception {
		// LOAD AND READ THE PROPERTIES FILE

		// LOGIN AND DASHBOARD VALDATION
		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_Login);
		Reporter.log("The userName read from excel is : " + userName, true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_Login);
		Reporter.log("The password read from excel is : " + password, true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value " + userName + " is entered as userName in the text-box", true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value " + password + " is entered as password in the text-box", true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button", true);
		// CHANGING USER PASSWORD

		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		Reporter.log("Click action is performed on Admin in the Menu bar", true);

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		Reporter.log("Click action is performed on User Management in the Menu bar", true);

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		Reporter.log("Click action is performed on Users in the Menu bar", true);

		Thread.sleep(5000);
		List<WebElement> employeeName = driver
				.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		Reporter.log("All EmployeeName are stored in the WebElement", true);
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		Reporter.log("All EmployeeName are stored in the Array", true);
		employee_Name = Utils.selectWithRandomIndex(50, empName);
		Reporter.log("The EmployeeName is selected by random no is :" + employee_Name, true);
		driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"
				+ employee_Name + "']/../../../td[8]")).click();
		Reporter.log("Click action is performed on Edit Link", true);

		driver.findElement(By.xpath("//label[@for='changepassword']")).click();
		Reporter.log("Click action is performed  on change password checkbox", true);
		
		newPassword = RandomGenerator.randomAlphaNumeric(8);
		Reporter.log("New password is :"+newPassword);

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(newPassword);
		Reporter.log("The value " + newPassword + " is entered as password in the text-box", true);

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(newPassword);
		Reporter.log("The value " + newPassword + " is entered as Confirm password in the text-box", true);

		driver.findElement(By.id("systemUserSaveBtn")).click();
		Reporter.log("Click action is performed on Save button", true);
		Thread.sleep(5000);

		CommonMethod.logoutJaveExecuter(driver);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		driver.quit();
		// ENTERING IN EXCEL SHEET
		
		if(result.getStatus() == ITestResult.SUCCESS){
			int rowCount=ExcelConfig.getRowUsed(Constant.sheet_Login);
			boolean found=false;
			for(int row=1;row<rowCount;row++){
				String employeeName=ExcelConfig.getCellData(row, Constant.col_NewUserName, Constant.sheet_Login);
				if(employee_Name.equalsIgnoreCase(employeeName)){
					ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_NewUserName, Constant.sheet_Login,CommonMethod.pathExcel);
					Reporter.log("The value "+employee_Name+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewUserName +" in the "+Constant.sheet_Login,true);
					found=true;
				}
			}
			
			if(!found){
				Reporter.log(employee_Name+ " is not found in Add Employee sheet against to the column "+Constant.col_NewUserName,true);
			}
			
			
			ExcelConfig.setCellData(newPassword, iTestData, Constant.col_NewPassword, Constant.sheet_Login,CommonMethod.pathExcel);
			Reporter.log("The value "+newPassword+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewPassword +" in the "+Constant.sheet_Login,true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
		
		Reporter.log("TestCase execution is completed",true);

	}

}

