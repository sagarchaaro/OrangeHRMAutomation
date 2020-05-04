package testCases;

import java.awt.Robot;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_08_TravelRequest {

	public static void main(String[] args) throws Exception {
		System.out.println("The Execution started for TC_08_TravelRequest");
		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		Properties prop = CommonMethod
				.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.testCaseID);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TravelRequestCases);
		System.out.println("The row no for test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD
		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.openBrowser(prop, iBrowser);
		String timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_TravelRequestCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_TravelRequestCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			// String filename=screenshotPath+"\\OrangeHRMLogin_.jpg";
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.pathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.pathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			throw new Exception();
		}

		// CHANGING USER PASSWORD
		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		System.out.println("Click action is performed on Admin in the Menu bar");

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		System.out.println("Click action is performed on User Management in the Menu bar");

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		System.out.println("Click action is performed on Users in the Menu bar");

		Thread.sleep(10000);
		List<WebElement> employeeName = driver
				.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		System.out.println("All EmployeeName are stored in the WebElement");
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		System.out.println("All EmployeeName are stored in the Array");
		String employee_Name = Utils.selectWithRandomIndex(50, empName);
		System.out.println("The EmployeeName is selected by random no is :" + employee_Name);
		driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"
				+ employee_Name + "']/../../../td[8]")).click();
		System.out.println("Click action is performed on Edit Link");

		Utils.screenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		System.out.println("Screen shot is  taken for Travel Request ");

		driver.findElement(By.xpath("//label[@for='changepassword']")).click();
		System.out.println("Click action is performed  on change password checkbox");

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(password);
		System.out.println("The value "+ password+" is entered as password in the text-box");

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(password);
		System.out.println("The value "+ password+" is entered as Confirm password in the text-box");

		driver.findElement(By.id("systemUserSaveBtn")).click();
		System.out.println("Click action is performed on Save button");
		Thread.sleep(5000);

		CommonMethod.logoutJaveExecuter(driver);

		// LOGGING INTO USER PROFILE
		driver.findElement(By.id("txtUsername")).sendKeys(employee_Name);
		System.out.println("The value "+ employee_Name+" is entered as employee_Name in the text-box");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+ password+" is entered as password in the text-box");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Click action is performed on Login button for the employee login");

		Utils.screenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		System.out.println("Screen shot is  taken for the employee login ");
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Click action is performed on Expense in the Menu bar");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Click action is performed on Travel Requests in the Menu bar");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Click action is performed on My Travel Requests in the Menu bar");

		Thread.sleep(5000);
		driver.switchTo().frame(0);

		driver.findElement(By.xpath("//i[text()='add']")).click();
		System.out.println("Click action is performed on Add button");
		// driver.switchTo().defaultContent();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#estimateAddForEmployee div div div div input")).click();
		List<WebElement> currencyName = driver
				.findElements(By.cssSelector("#estimateAddForEmployee div div div div ul li span"));
		System.out.println("All CurrencyName are stored in the WebElement");
		String[] cur_Name = Utils.dataIntoArray(currencyName, 160);
		System.out.println("All CurrencyName are stored in the Array");
		String currency_Name = Utils.selectWithRandomIndex(160, cur_Name);
		System.out.println("The CurrencyName is selected by random no is :" + currency_Name);
		//driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/input")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/ul/li/span[text()='"+ currency_Name + "']")).click();
		System.out.println("Click action is performed on Currency option");

		driver.findElement(By.xpath("//a[text()='Next']")).click();
		System.out.println("Click action is performed on Next button");

		driver.findElement(By.xpath("(//button[text()='Add'])[1]")).click();
		System.out.println("Click action is performed  on Add button");

		String main_Destination = ExcelConfig.getCellData(iTestData, Constant.col_Main_Destination,
				Constant.sheet_TravelRequestCases);
		System.out.println("The Main_Destination read from excel is : " + main_Destination);
		driver.findElement(By.xpath("//input[@name='TravelInformation[main_destination]']")).sendKeys(main_Destination);
		System.out.println("The value "+ main_Destination+" is entered as Main_Destination in the text-box");

		String fromDate = ExcelConfig.getCellData(iTestData, Constant.col_From_Date, Constant.sheet_TravelRequestCases);
		System.out.println("The FromDate read from excel is : " + fromDate);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']")).click();
		WebElement element_FromDate = driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_FromDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_FromDate);
		
		System.out.println("Click action is performed on calender for FromDate");
		Thread.sleep(10000);
		CommonMethod.date_HRM_08(fromDate, driver, 1);
		
		Thread.sleep(10000);
		String toDate = ExcelConfig.getCellData(iTestData, Constant.col_To_Date, Constant.sheet_TravelRequestCases);
		System.out.println("The ToDate read from excel is : " + toDate);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']")).click();
		WebElement element_ToDate =driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_ToDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_ToDate);

		System.out.println("Click action is performed on calender for ToDate");
		Thread.sleep(10000);
		CommonMethod.date_HRM_08(toDate, driver, 2);

		String dest_Address = RandomStringUtils.randomAlphabetic(6);
		System.out.println("The Dest_Address is selected by random Util is :" + dest_Address);
		driver.findElement(By.name("TravelInformation[destination_address]")).sendKeys(dest_Address);
		System.out.println("The value "+ dest_Address+" is entered as Dest_Address in the text-box");
		driver.findElement(By.xpath("(//a[text()='Save'])[3]")).click();
		System.out.println("Click action is performed on Save button");

		driver.findElement(By.xpath("(//button[text()='Add'])[2]")).click();
		System.out.println("Click action is performed on Add");

		driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
		String expense_Type = ExcelConfig.getCellData(iTestData, Constant.col_Expense_Type,
				Constant.sheet_TravelRequestCases);
		System.out.println("The Expense_Type read from excel is : " + expense_Type);
		driver.findElement(By.xpath("(//input[@class='select-dropdown']/../ul/li/span)[text()='" + expense_Type + "']")).click();
		
		System.out.println("The value "+ expense_Type+" is selected as Expense_Type in the dropdown");

		driver.findElement(By.xpath("//input[@class='select-dropdown valid']")).click();
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);

		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		String amount = RandomStringUtils.randomNumeric(6);
		System.out.println("The amount is selected by random Util is :" + amount);
		driver.findElement(By.xpath("//input[@name='estimation[amount]']")).sendKeys(amount);
		System.out.println("The value "+ amount+" is entered as Amount in the text-box");

		driver.findElement(By.xpath("(//input[@class='select-dropdown valid'])[2]")).click();
		String paid_By = ExcelConfig.getCellData(iTestData, Constant.col_Paid_By, Constant.sheet_TravelRequestCases);
		System.out.println("The paid_By read from excel is : " + paid_By);
		driver.findElement(
				By.xpath("((//input[@class='select-dropdown valid'])[2]/../ul/li/span)[text()='" + paid_By + "']"))
				.click();
		System.out.println("The value "+ paid_By+" is selected as paid_By in the dropdown");

		driver.findElement(By.xpath("(//a[text()='Save'])[2]")).click();
		System.out.println("Click action is performed on Save button");

		Utils.screenShot(CommonMethod.screenshotPath, driver);
		System.out.println("Screen shot is taken for Employee List ");

		driver.findElement(By.xpath("//a[text()='Submit']")).click();
		System.out.println("Click action is performed on Submit button");

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Click action is performed on OK button");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Click action is performed on My Travel Requests in the Menu bar");

		String requestID = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td a"))
				.getText();

		String requestStatus = driver
				.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td:nth-of-type(3)")).getText();

		Utils.screenShot(screenshotPath + "\\RequestID.jpg", driver);
		System.out.println("Screen shot is taken for Travel Request Id ");

		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as password in the text-box");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Click action is performed on Login button for Admin");

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Click action is performed on Expenses in the Menu bar");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Click action is performed on Travel Requests in the Menu bar");

		driver.findElement(By.xpath("//a[@id='menu_expense_viewEmployeeEstimateRequest']/span[2]")).click();
		System.out.println("Click action is performed on Employee Travel Requests in the Menu bar");

		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		System.out.println("Click action is performed on Search button");

		driver.findElement(By.xpath("//input[@class='select-dropdown valid']")).click();
		driver.findElement(
				By.xpath("//input[@class='select-dropdown valid']/../ul/li/span[text()='" + requestStatus + "']"))
				.click();
		System.out.println("The value "+ requestStatus+" is selected as RequestStatus in the dropdown");

		driver.findElement(By.xpath("//a[text()='Search']")).click();
		System.out.println("Click action is performed on Search button");

		driver.findElement(
				By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/a[text()='" + requestID + "']")).click();
		System.out.println("Clicked on Request ID link");

		driver.findElement(By.xpath("//a[text()='Approve']")).click();
		System.out.println("Click action is performed  on Approve button");

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Click action is performed on OK button");

		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(employee_Name);
		System.out.println("The value "+employee_Name+" is entered as userName in the text-box");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as password in the text-box");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Click action is performed on Login button for Employee");

		Utils.screenShot(screenshotPath + "\\OrangeHRMEmplopyeeLogin.jpg", driver);
		System.out.println("Screen shot is taken for Employee Login ");

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Click action is performed on Expenses in the Menu bar");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Click action is performed on Travel Requests in the Menu bar");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Click action is performed on My Travel Requests in the Menu bar");

		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		System.out.println("Screen shot is taken for Aproved Travel Request ");

		CommonMethod.logoutJaveExecuter(driver);

		driver.quit();

		ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_Employee_Name, Constant.sheet_TravelRequestCases,
				CommonMethod.pathExcel);
		System.out.println("The value "+employee_Name+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_Employee_Name
				+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(currency_Name, iTestData, Constant.col_Currency_Name, Constant.sheet_TravelRequestCases,
				CommonMethod.pathExcel);
		System.out.println("The value "+currency_Name+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Currency_Name
				+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(dest_Address, iTestData, Constant.col_Destination_Address,
				Constant.sheet_TravelRequestCases, CommonMethod.pathExcel);
		System.out.println("The value "+dest_Address+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Destination_Address
				+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(requestStatus, iTestData, Constant.col_Request_Status,
				Constant.sheet_TravelRequestCases, CommonMethod.pathExcel);
		System.out.println("The value "+requestStatus+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_Status
				+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(requestID, iTestData, Constant.col_Request_ID, Constant.sheet_TravelRequestCases,
				CommonMethod.pathExcel);
		System.out.println("The value "+requestID+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_ID
				+" in the "+Constant.sheet_TravelRequestCases);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_NewPhoneNo, Constant.sheet_TestCases,
				CommonMethod.pathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.pathExcel);
		System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
				+" in the "+Constant.sheet_TestCases);

		System.out.println("The file are closed");

	}
}
