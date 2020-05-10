package testCases;


import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_08_TravelRequest {
	
	public static String timestamp, screenshotPath, browser, vacancy_Name, employee_Name, currency_Name, dest_Address, requestStatus, requestID;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites(){
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Data read from Properties file.");		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.testCaseID);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		System.out.println("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(prop, browser);
	}
	@Test
	public void travelRequest(String[] args) throws Exception {
		System.out.println("The Execution started for TC_08_TravelRequest");
		// LOAD AND READ THE PROPERTIES FILE
		
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
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/ul/li/span[text()='"+ currency_Name + "']")).click();
		driver.findElement(By.xpath("//span[text()='"+ currency_Name + "']")).click();
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
		Thread.sleep(5000);
		CommonMethod.date_HRM_08(fromDate, driver, 1);
		
		Thread.sleep(5000);
		String toDate = ExcelConfig.getCellData(iTestData, Constant.col_To_Date, Constant.sheet_TravelRequestCases);
		System.out.println("The ToDate read from excel is : " + toDate);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']")).click();
		WebElement element_ToDate =driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_ToDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_ToDate);

		System.out.println("Click action is performed on calender for ToDate");
		Thread.sleep(5000);
		String date[] = toDate.split("/");
		// Select Year
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='"+date[2]+"'])[2]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_year);
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_year);
		System.out.println("The Year selected from calender is:" + date[2]);
		// Select Month
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='" + date[0] + "'])[2]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_month);
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_month);
		System.out.println("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(By.xpath("(//table[@class='picker__table'])[3]/tbody/tr")).size();
		int cols = driver.findElements(By.xpath("(//table[@class='picker__table'])[3]/tbody/tr[1]/td")).size();
		System.out.println("The no of cols is:" + cols + ", The no of rows is:" + rows);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
			System.out.println("entered row for loop");
		for (int colsNo = 1; colsNo <= cols; colsNo++) {
			System.out.println("entered column for loop");
				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr[" + rowNo + "]/td[" + colsNo + "]/div)[3]")).getText();
				System.out.println(calDate);
				if (calDate.equalsIgnoreCase(date[1])) {
					System.out.println("entered if loop");
					driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr["+ rowNo + "]/td[" + colsNo + "]/div)[3]")).click();
					System.out.println("The Date is selected from calender is:" + toDate);
					break rows;
				}

			}
		}


		//CommonMethod.date_HRM_08(toDate, driver, 2);

		String dest_Address = RandomStringUtils.randomAlphabetic(6);
		System.out.println("The Dest_Address is selected by random Util is :" + dest_Address);
		driver.findElement(By.name("TravelInformation[destination_address]")).sendKeys(dest_Address);
		System.out.println("The value "+ dest_Address+" is entered as Dest_Address in the text-box");
		driver.findElement(By.xpath("(//a[text()='Save'])[3]")).click();
		System.out.println("Click action is performed on Save button");

		driver.findElement(By.xpath("(//button[text()='Add'])[2]")).click();
		System.out.println("Click action is performed on Add");

		driver.findElement(By.xpath("//form[@id='selectionForm']/div/div/div/input")).click();
		String expense_Type = ExcelConfig.getCellData(iTestData, Constant.col_Expense_Type,
				Constant.sheet_TravelRequestCases);
		System.out.println("The Expense_Type read from excel is : " + expense_Type);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='"+expense_Type+"']")).click();
		
		
		System.out.println("The value "+ expense_Type+" is selected as Expense_Type in the dropdown");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		System.out.println("Currency paid in is clicked");
		driver.findElement(By.xpath("//span[text()='"+currency_Name+"']")).click();
		/*
		 * Robot r = new Robot(); r.keyPress(KeyEvent.VK_DOWN);
		 * r.keyRelease(KeyEvent.VK_DOWN);
		 * 
		 * Thread.sleep(2000); r.keyPress(KeyEvent.VK_ENTER);
		 * r.keyRelease(KeyEvent.VK_ENTER);
		 */
		System.out.println("Selected currency");
		
		String amount = RandomStringUtils.randomNumeric(6);
		System.out.println("The amount is selected by random Util is :" + amount);
		driver.findElement(By.xpath("//input[@name='estimation[amount]']")).sendKeys(amount);
		System.out.println("The value "+ amount+" is entered as Amount in the text-box");
		driver.findElement(By.xpath("//select[@id='estimation_paid_by_id']/../input")).click();
		String paid_By = ExcelConfig.getCellData(iTestData, Constant.col_Paid_By, Constant.sheet_TravelRequestCases);
		System.out.println("The paid_By read from excel is : " + paid_By);
		driver.findElement(
				By.xpath("//span[text()='" + paid_By + "']"))
				.click();
		System.out.println("The value "+ paid_By+" is selected as paid_By in the dropdown");
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//a[text()='Save'])[2]")).click();
		System.out.println("Click action is performed on Save button");

		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		System.out.println("Screen shot is taken for Employee List ");

		driver.findElement(By.xpath("//a[text()='Submit']")).click();
		System.out.println("Click action is performed on Submit button");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Click action is performed on OK button");

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		System.out.println("Click action is performed on My Travel Requests in the Menu bar");

		String requestID = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td a")).getText();
		System.out.println("Request ID is "+requestID);
		String requestStatus = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td:nth-of-type(3)")).getText();
		System.out.println("Request status is "+requestStatus);
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
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		System.out.println("Click action is performed on Search button");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='estimateSearchForEmployee']/div/div/div/div/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Pending Supervisor Approval']")).click();
		System.out.println("The value Pending Supervisor Approval is selected as RequestStatus in the dropdown");

		driver.findElement(By.xpath("//a[text()='Search']")).click();
		System.out.println("Click action is performed on Search button");

		//driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/a[text()='" + requestID + "']")).click();
		//System.out.println("Clicked on Request ID link");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='"+requestID+"']")).click();
		driver.findElement(By.xpath("//a[text()='Approve']")).click();
		System.out.println("Click action is performed  on Approve button");

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		System.out.println("Click action is performed on OK button");
		Thread.sleep(1000);
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
		System.out.println("Screen shot is taken for Approved Travel Request ");
		Thread.sleep(2000);
		//CommonMethod.logoutJaveExecuter(driver);
		WebElement element_Logout = driver.findElement(By.xpath("//*[@id='account-job']/i"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_Logout);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_Logout);
		Thread.sleep(3000);
		driver.findElement(By.id("logoutLink")).click();
		System.out.println("Click action is performed on Logout button");
		
		}
	
	@AfterMethod
	public void tearDown() throws Exception{
		driver.quit();
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,CommonMethod.pathExcel);
		System.out.println("The value "+vacancy_Name+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_Vacancy_name+" in the "+Constant.sheet_AddVacancyCases);
		
		ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_Employee_Name, Constant.sheet_TravelRequestCases,CommonMethod.pathExcel);
		System.out.println("The value "+employee_Name+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_Employee_Name+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(currency_Name, iTestData, Constant.col_Currency_Name, Constant.sheet_TravelRequestCases,CommonMethod.pathExcel);
		System.out.println("The value "+currency_Name+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Currency_Name+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(dest_Address, iTestData, Constant.col_Destination_Address,Constant.sheet_TravelRequestCases, CommonMethod.pathExcel);
		System.out.println("The value "+dest_Address+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Destination_Address+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(requestStatus, iTestData, Constant.col_Request_Status,Constant.sheet_TravelRequestCases, CommonMethod.pathExcel);
		System.out.println("The value "+requestStatus+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_Status+" in the "+Constant.sheet_TravelRequestCases);
		ExcelConfig.setCellData(requestID, iTestData, Constant.col_Request_ID, Constant.sheet_TravelRequestCases,CommonMethod.pathExcel);
		System.out.println("The value "+requestID+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_ID+" in the "+Constant.sheet_TravelRequestCases);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_NewPhoneNo, Constant.sheet_TestCases,CommonMethod.pathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.pathExcel);
		System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases);

		System.out.println("The file are closed");

	}

}
