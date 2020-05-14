package testCases;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_06_AddUser {
	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		// LOAD AND READ THE PROPERTIES FILE
		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
			
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
				
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,	Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_AddUserCases);
		Reporter.log("The row no for of test Data is : " + iTestData,true);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + iBrowser,true);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(prop, iBrowser);				
	}


	 @Test
	 public  void addUser() throws InterruptedException, Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);	
		Reporter.log("The Execution started for TC_06_AddUser",true);

		// LOGIN TO DASHBOARD

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userNameLogin = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddUserCases);
		Reporter.log("The userName read from excel is : " + userNameLogin,true);
		String passwordLogin = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddUserCases);
		Reporter.log("The userName read from excel is : " + passwordLogin,true);

		driver.findElement(By.id("txtUsername")).sendKeys(userNameLogin);
		Reporter.log("The value "+userNameLogin+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(passwordLogin);
		Reporter.log("The value "+passwordLogin+" is entered as userName in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case id failed",true);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases,true);
			throw new Exception();
		}

		// CLICK FOR USER AMENDMENT

		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		Reporter.log("Click action is performed on Admin in the Menu bar",true);
		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		Reporter.log("Click action is performed on User Management in the Menu bar",true);
		driver.findElement(By.xpath("//span[text()='Users']")).click();
		Reporter.log("Click action is performed on Users in the Menu bar",true);

		// STORING ALL THE EMPLOYEE NAME IN THE PAGE

		int totalElementNo = driver.findElements(By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span")).size();
		Reporter.log("The total no of employee in the page is: " + totalElementNo,true);
		List<WebElement> webelement_empName = driver
				.findElements(By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span"));
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		String employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The employeeName selected by random no is:" + employeeName,true);
		String empNameSearch[] = employeeName.split(" ");

		driver.findElement(By.xpath("//i[text()='add']")).click();
		Reporter.log("Click action is performed on add button",true);

		WebElement webelement1 = driver.findElement(By.xpath("//input[@id='selectedEmployee_value']"));
		webelement1.sendKeys(empNameSearch[0]);
		Thread.sleep(3000);
		webelement1.sendKeys(Keys.DOWN);
		webelement1.sendKeys(Keys.ENTER);
		Reporter.log("The Employee name entered for search is: " + empNameSearch[0]);

		String randomAlphabet = RandomStringUtils.randomAlphabetic(4);
		String userName = empNameSearch[0].concat(randomAlphabet);
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys(userName);
		Reporter.log("The value "+ userName+" is entered as userName in the text-box",true);
		ExcelConfig.setCellData(userName, iTestData, Constant.col_UserID, Constant.sheet_AddUserCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+userName+" is written as userName against to RowNumber "+iTestData +", column Number " +Constant.col_UserID
				+" in the "+Constant.sheet_AddUserCases,true);
		String adminRole = ExcelConfig.getCellData(iTestData, Constant.col_AdminRole, Constant.sheet_AddUserCases);
		Reporter.log("The adminRole read from excel is : " + adminRole,true);

		driver.findElement(By.xpath("//div[@id='adminrole_inputfileddiv']/div/input")).click();
		driver.findElement(By.xpath("//span[text()='" + adminRole + "']")).click();
		Reporter.log("The value "+ adminRole+" is entered as adminRole in the drop down",true);
		driver.findElement(By.xpath("//div[@id='status_inputfileddiv']/div/input")).click();
		driver.findElement(By.xpath("//div[@id='status_inputfileddiv']/div/ul/li/span[text()='Enabled']")).click();
		Reporter.log("Admin Role is Enabled ",true);
		String password1 = ExcelConfig.getCellData(iTestData, Constant.col_UserPassword, Constant.sheet_AddUserCases);
		Reporter.log("The password read from excel is : " + password1,true);

		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password1);
		Reporter.log("The value "+ password1+" is entered as password in the text-box",true);
		driver.findElement(By.xpath("//input[@id='confirmpassword']")).sendKeys(password1);
		Reporter.log("The value "+ password1+" is entered as Confirm password in the text-box",true);
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		Reporter.log("Click action is performed on Save button",true);
		Thread.sleep(3000);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='modal-footer']/a[text()='Save']")));


		driver.findElement(By.xpath("//div[@class='modal-footer']/a[text()='Save']")).click();
		Reporter.log("Click action is performed on Save button is for region",true);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[text()='ohrm_filter']")));
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		Reporter.log("Click action is performed on Filter",true);
		driver.findElement(By.xpath("//input[@id='systemuser_uname_filter']")).sendKeys(userName);
		Reporter.log("The value "+ userName+" is entered as userName in the Search text-box",true);
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		Reporter.log("Click action is performed on serched button",true);

		try {
			WebElement userfoundmsg = driver.findElement(By.xpath("//div[text()='No Records Found']"));
			if (userfoundmsg.isDisplayed()) {
				Reporter.log("The User is not found message displayed",true);
			}
		} catch (Exception user) {
			Utils.screenShot(screenshotPath + "\\OrangeHRMUser.jpg", driver);
			Reporter.log("User detail page is found for the user"+userName,true);
		}

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
		Reporter.log("The login will be done and validated for new user",true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password1);
		Reporter.log("The value "+password1+" is entered as password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed  on log in button for new user",true);
		String username1_validation = driver.findElement(By.xpath("//span[@id='account-name']")).getText();
		Utils.screenShot(screenshotPath + "\\" + userName + "_Login.jpg", driver);
		CommonMethod.validation(employeeName, username1_validation, iTestCase);

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
	 }
	@AfterMethod
	public void afterMethod() throws Exception{

		driver.quit();

		Reporter.log("Click action is performed on Logoout button for New User",true);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.pathExcel);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
				+" in the "+Constant.sheet_TestCases,true);
		Reporter.log("The file are closed",true);

	}

}
