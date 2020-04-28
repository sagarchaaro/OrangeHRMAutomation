package testCases;

import java.awt.Robot;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_08_TravelRequest {

	public static void main(String[] args) throws Exception {

		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println(CommonMethod.projectpath);
		Properties prop = CommonMethod
				.PropertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TravelRequestCases);
		System.out.println("The row no of test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD
		String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.OpenBrowser(CommonMethod.Url, driverPath, iBrowser);
		String timestamp = Utils.TimeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.Validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_TravelRequestCases);
		System.out.println("The value of userName is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_TravelRequestCases);
		System.out.println("The value of password is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click on log in button ");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			// String filename=screenshotPath+"\\OrangeHRMLogin_.jpg";
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Dashboard is avilable, Screen Print taken");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.PathExcel);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.PathExcel);
			throw new Exception();
		}

		Utils.ScreenShot(screenshotPath + "\\Travel_Request.jpg", driver);

		// CHANGING USER PASSWORD
		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		System.out.println("Clicked on Admin");

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		System.out.println("Clikced on User Management");

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		System.out.println("Clicked on Users");

		Thread.sleep(10000);
		List<WebElement> EmployeeName = driver
				.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		String[] EmpName = Utils.dataIntoArray(EmployeeName, 50);
		String Employee_Name = Utils.selectWithRandomIndex(50, EmpName);

		driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"
				+ Employee_Name + "']/../../../td[8]")).click();
		System.out.println("Clicked on Edit");

		Utils.ScreenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		System.out.println("Screenshot taken");

		driver.findElement(By.xpath("//label[@for='changepassword']")).click();
		System.out.println("Clicked on change password checkbox");

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(password);
		System.out.println("Entered password");

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(password);
		System.out.println("Enetred confirm password");

		// WebElement element2 = driver.findElement(By.id("systemUserSaveBtn"));
		// ((JavascriptExecutor)
		// driver).executeScript("arguments[0].scrollIntoView(true);",
		// element2);
		// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
		// element2);
		driver.findElement(By.id("systemUserSaveBtn")).click();
		System.out.println("Clicked on Save");
		Thread.sleep(5000);

		CommonMethod.logoutJaveExecuter(driver);

		// LOGGING INTO USER PROFILE
		driver.findElement(By.id("txtUsername")).sendKeys(Employee_Name);
		System.out.println("Entered user name");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("Entered password");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Clicked on Login button");

		Utils.ScreenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		System.out.println("Screenshot taken");

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Clicked on Expenses");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Clicked on Travel request");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Clicked on My Travel Requests");

		Thread.sleep(5000);
		driver.switchTo().frame(0);

		driver.findElement(By.xpath("//i[text()='add']")).click();
		System.out.println("Clicked on Add");
		// driver.switchTo().defaultContent();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#estimateAddForEmployee div div div div input")).click();
		List<WebElement> CurrencyName = driver
				.findElements(By.cssSelector("#estimateAddForEmployee div div div div ul li span"));
		String[] Cur_Name = Utils.dataIntoArray(CurrencyName, 160);
		String Currency_Name = Utils.selectWithRandomIndex(160, Cur_Name);
		System.out.println(Currency_Name);
		//driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/input")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/ul/li/span[text()='"+ Currency_Name + "']")).click();
		System.out.println("Selected Currency");

		driver.findElement(By.xpath("//a[text()='Next']")).click();
		System.out.println("Selected Next");

		driver.findElement(By.xpath("(//button[text()='Add'])[1]")).click();
		System.out.println("Selected on Add");

		String Main_Destination = ExcelConfig.getCellData(iTestData, Constant.col_Main_Destination,
				Constant.sheet_TravelRequestCases);
		driver.findElement(By.xpath("//input[@name='TravelInformation[main_destination]']")).sendKeys(Main_Destination);
		System.out.println("Entered Main Destination");

		String FromDate = ExcelConfig.getCellData(iTestData, Constant.col_From_Date, Constant.sheet_TravelRequestCases);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']")).click();
		System.out.println(FromDate);
		Thread.sleep(10000);
		CommonMethod.Date_HRM_08(FromDate, driver, 1);
		
		Thread.sleep(10000);
		String ToDate = ExcelConfig.getCellData(iTestData, Constant.col_To_Date, Constant.sheet_TravelRequestCases);
		System.out.println(ToDate);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']")).click();
		Thread.sleep(10000);
		CommonMethod.Date_HRM_08(ToDate, driver, 2);

		String Dest_Address = RandomStringUtils.randomAlphabetic(6);
		driver.findElement(By.name("TravelInformation[destination_address]")).sendKeys(Dest_Address);

		driver.findElement(By.xpath("(//a[text()='Save'])[3]")).click();
		System.out.println("Clicked on Save button");

		driver.findElement(By.xpath("(//button[text()='Add'])[2]")).click();
		System.out.println("Clicked on Add");

		driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
		String Expense_Type = ExcelConfig.getCellData(iTestData, Constant.col_Expense_Type,
				Constant.sheet_TravelRequestCases);
		driver.findElement(By.xpath("(//input[@class='select-dropdown']/../ul/li/span)[text()='" + Expense_Type + "']"))
				.click();
		System.out.println("Clicked on Expense Type");

		driver.findElement(By.xpath("//input[@class='select-dropdown valid']")).click();
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);

		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);

		String Amount = RandomStringUtils.randomNumeric(6);
		driver.findElement(By.xpath("//input[@name='estimation[amount]']")).sendKeys(Amount);
		System.out.println("Entered Amount");

		driver.findElement(By.xpath("(//input[@class='select-dropdown valid'])[2]")).click();
		String Paid_By = ExcelConfig.getCellData(iTestData, Constant.col_Paid_By, Constant.sheet_TravelRequestCases);
		driver.findElement(
				By.xpath("((//input[@class='select-dropdown valid'])[2]/../ul/li/span)[text()='" + Paid_By + "']"))
				.click();
		System.out.println("Clicked on Paid By value");

		driver.findElement(By.xpath("(//a[text()='Save'])[2]")).click();
		System.out.println("Clicked on Save");

		Utils.ScreenShot(CommonMethod.screenshotPath, driver);
		System.out.println("Screenshot taken");

		driver.findElement(By.xpath("//a[text()='Submit']")).click();
		System.out.println("Clicked on Submit");

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Clicked on OK");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Clicked on My Travel Requests");

		String RequestID = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td a"))
				.getText();

		String RequestStatus = driver
				.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td:nth-of-type(3)")).getText();

		Utils.ScreenShot(screenshotPath + "\\RequestID.jpg", driver);
		System.out.println("Screenshot taken");

		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("Entered user name");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("Entered password");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Clicked on Login button");

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Clicked on Expenses");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Clicked on Travel request");

		driver.findElement(By.xpath("//a[@id='menu_expense_viewEmployeeEstimateRequest']/span[2]")).click();
		System.out.println("Clicked on My Travel Requests");

		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		System.out.println("Clicked on Search");

		driver.findElement(By.xpath("//input[@class='select-dropdown valid']")).click();
		driver.findElement(
				By.xpath("//input[@class='select-dropdown valid']/../ul/li/span[text()='" + RequestStatus + "']"))
				.click();
		System.out.println("Clicked on Request Status");

		driver.findElement(By.xpath("//a[text()='Search']")).click();
		System.out.println("Clicked on Search");

		driver.findElement(
				By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/a[text()='" + RequestID + "']")).click();
		System.out.println("Clicked on Request ID");

		driver.findElement(By.xpath("//a[text()='Approve']")).click();
		System.out.println("Clicked on Approve");

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Clicked on OK");

		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(Employee_Name);
		System.out.println("Entered user name");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("Entered password");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		System.out.println("Clicked on Login button");

		Utils.ScreenShot(screenshotPath + "\\OrangeHRMEmplopyeeLogin.jpg", driver);
		System.out.println("Screenshot taken");

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		System.out.println("Clicked on Expenses");

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		System.out.println("Clicked on Travel request");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Clicked on My Travel Requests");

		Utils.ScreenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		System.out.println("Screenshot taken");

		CommonMethod.logoutJaveExecuter(driver);

		driver.quit();

		ExcelConfig.setCellData(Employee_Name, iTestData, Constant.col_Employee_Name, Constant.sheet_TravelRequestCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData(Currency_Name, iTestData, Constant.col_Currency_Name, Constant.sheet_TravelRequestCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData(Dest_Address, iTestData, Constant.col_Destination_Address,
				Constant.sheet_TravelRequestCases, CommonMethod.PathExcel);
		ExcelConfig.setCellData(RequestStatus, iTestData, Constant.col_Request_Status,
				Constant.sheet_TravelRequestCases, CommonMethod.PathExcel);
		ExcelConfig.setCellData(RequestID, iTestData, Constant.col_Request_ID, Constant.sheet_TravelRequestCases,
				CommonMethod.PathExcel);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_NewPhoneNo, Constant.sheet_TestCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);

		System.out.println("The file are closed");

	}
}
