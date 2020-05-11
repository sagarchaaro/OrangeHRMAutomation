package testCases;



import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

//CLASS VARIABLE DECLARATION

public class TC_01_AddEmployee {
	
	public static String timestamp, screenshotPath, browser, employeeName, employeeID;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);	
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		Reporter.log("The Testcase id executing is :"+CommonMethod.testCaseID,true);
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{
			
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(prop, browser);
	}

	@Test
	public void addEmployee() throws Exception {
		Reporter.log("The Execution started for TC_01_AddEmployee",true);
		// LOAD AND READ THE PROPERTIES FILE

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddEmployeeCases);
		Reporter.log("The password read from excel is : " + password,true);

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
			Reporter.log(user.getMessage(),true);
			user.printStackTrace();
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			throw new Exception();
		}

		// PIM Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		Reporter.log("Click action is performed on PLM in the Menu bar",true);

		// Add employee click
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		Reporter.log("Click action is performed on Add Employee in the Menu bar",true);
		// Enter name
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstName']")));

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The firstName read from excel is : " + firstName,true);
		
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		Reporter.log("The value "+ firstName+" is entered as firstName in the text-box",true);

		String middleName = ExcelConfig.getCellData(iTestData, Constant.col_middleName,
				Constant.sheet_AddEmployeeCases);
		Reporter.log("The middleName read from excel is : " + middleName,true);
		
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		Reporter.log("The value "+ middleName+" is entered as middleName in the text-box",true);
		
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The lastName read from excel is : " + lastName,true);
		
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		Reporter.log("The value "+ lastName+" is entered as lastName in the text-box",true);

		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		String location = ExcelConfig.getCellData(iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases);
		Reporter.log("The location read from excel is : " + location,true);
		
		driver.findElement(By.xpath("//span[contains(text(),'" + location + "')]")).click();
		Reporter.log("The value "+ location+" is selected as location in the dropdown",true);
		Thread.sleep(2000);
		// click next button
		WebElement element_next=driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element_next);
		Reporter.log("Click action is performed on Next button",true);
		Thread.sleep(3000);
		// select blood group
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@class='select-dropdown'])[3]")));
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[3]")).click();
		String bloodgroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup,
				Constant.sheet_AddEmployeeCases);
		Reporter.log("The bloodgroup read from excel is : " + bloodgroup,true);
		
		driver.findElement(By.xpath("//span[text()='" + bloodgroup + "']")).click();
		Reporter.log("The value "+ bloodgroup+" is selected as bloodgroup in the dropdown",true);

		// select hobbies
		String hobby = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		Reporter.log("The hobby read from excel is : " + hobby,true);
		
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobby);
		Reporter.log("The value "+ hobby+" is entered as hobby in the text-box",true);

		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Reporter.log("Clicked action is performed on Next button",true);

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[6]")).click();

		String region = ExcelConfig.getCellData(iTestData, Constant.col_Region, Constant.sheet_AddEmployeeCases);
		Reporter.log("The Region read from excel is : " + region,true);
		
		driver.findElement(By.xpath("//span[text()='" + region + "']")).click();
		Reporter.log("The value "+ region+" is selected as Region in the dropdown",true);

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[7]")).click();
		String FTE = ExcelConfig.getCellData(iTestData, Constant.col_FTE, Constant.sheet_AddEmployeeCases);
		Reporter.log("The FTE read from excel is : " + FTE,true);		
		
		driver.findElement(By.xpath("//span[text()='" + FTE + "']")).click();
		Reporter.log("The value "+ FTE+" is selected as FTE in the dropdown",true);

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[8]")).click();
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.col_Temp_Department,
				Constant.sheet_AddEmployeeCases);

		Reporter.log("The Temp_Department read from excel is : " + temp_Department);
		driver.findElement(By.xpath("//span[text()='" + temp_Department + "']")).click();
		Reporter.log("The value "+ temp_Department+" is selected as Temp_Department in the dropdown",true);

		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Reporter.log("Click action is performed Save button",true);
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[text()='Employee List']")).click();
		employeeName = firstName.concat(" " + middleName).concat(" " + lastName);
		employeeID = driver.findElement(By.xpath("//td[text()='"+employeeName+" ']/../td[2]")).getText();
		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
	}
	
	@AfterMethod
	public void tearDown() throws Exception{
		driver.quit();
		
		ExcelConfig.setCellData(employeeName, iTestData, Constant.col_employeeName, Constant.sheet_AddEmployeeCases,CommonMethod.pathExcel);
		Reporter.log("The value "+employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeName +" in the "+Constant.sheet_AddEmployeeCases,true);
		
		ExcelConfig.setCellData(employeeID, iTestData, Constant.col_employeeID, Constant.sheet_AddEmployeeCases,CommonMethod.pathExcel);
		Reporter.log("The value "+employeeID+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_employeeID +" in the "+Constant.sheet_AddEmployeeCases,true);
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases,true);

		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases,true);

		Reporter.log("The file are closed",true);
	}

}
