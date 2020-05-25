package testCases;


import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.RandomGenerator;

public class TC_08_TravelRequest {
	
	public static String timestamp, screenshotPath, browser, excelPath, vacancy_Name, employee_Name, currency_Name, dest_Address, requestStatus, requestID,reason;
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
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	
	@Test
	public void travelRequest(String[] args) throws Exception {
		Reporter.log("The Execution started for TC_08_TravelRequest",true);
		// LOAD AND READ THE PROPERTIES FILE
		
		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.verifyData(title, "OrangeHRM");

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_TravelRequestCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_TravelRequestCases);
		Reporter.log("The password read from excel is : " + password,true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			// String filename=screenshotPath+"\\OrangeHRMLogin_.jpg";
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ",true);

		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case is failed",true);
			reason="Dashboard is not available";		
			Assert.assertTrue(false, "Dashboard is not available, Test case is failed");
		}

		// CHANGING USER PASSWORD
		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		Reporter.log("Click action is performed on Admin in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		Reporter.log("Click action is performed on User Management in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		Reporter.log("Click action is performed on Users in the Menu bar",true);

		Thread.sleep(5000);
		List<WebElement> employeeName = driver.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		Reporter.log("All EmployeeName are stored in the WebElement",true);
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		Reporter.log("All EmployeeName are stored in the Array",true);
		String employee_Name = Utils.selectWithRandomIndex(50, empName);
		Reporter.log("The EmployeeName is selected by random no is :" + employee_Name,true);
		driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"+ employee_Name + "']/../../../td[8]")).click();
		Reporter.log("Click action is performed on Edit Link",true);

		Utils.screenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		Reporter.log("Screen shot is  taken for Travel Request ",true);

		driver.findElement(By.xpath("//label[@for='changepassword']")).click();
		Reporter.log("Click action is performed  on change password checkbox",true);

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(password);
		Reporter.log("The value "+ password+" is entered as password in the text-box",true);

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(password);
		Reporter.log("The value "+ password+" is entered as Confirm password in the text-box",true);

		driver.findElement(By.id("systemUserSaveBtn")).click();
		Reporter.log("Click action is performed on Save button",true);
		Thread.sleep(5000);

		CommonMethod.logoutJaveExecuter(driver);

		// LOGGING INTO USER PROFILE
		driver.findElement(By.id("txtUsername")).sendKeys(employee_Name);
		Reporter.log("The value "+ employee_Name+" is entered as employee_Name in the text-box",true);

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+ password+" is entered as password in the text-box",true);

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		Reporter.log("Click action is performed on Login button for the employee login",true);

		Utils.screenShot(screenshotPath + "\\Travel_Request.jpg", driver);
		Reporter.log("Screen shot is  taken for the employee login ",true);
		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		Reporter.log("Click action is performed on Expense in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		Reporter.log("Click action is performed on Travel Requests in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		Reporter.log("Click action is performed on My Travel Requests in the Menu bar",true);

		Thread.sleep(5000);
		driver.switchTo().frame(0);

		driver.findElement(By.xpath("//i[text()='add']")).click();
		Reporter.log("Click action is performed on Add button",true);
		// driver.switchTo().defaultContent();

		Thread.sleep(3000);

		driver.findElement(By.cssSelector("#estimateAddForEmployee div div div div input")).click();
		List<WebElement> currencyName = driver.findElements(By.cssSelector("#estimateAddForEmployee div div div div ul li span"));
		Reporter.log("All CurrencyName are stored in the WebElement",true);
		String[] cur_Name = Utils.dataIntoArray(currencyName, 160);
		Reporter.log("All CurrencyName are stored in the Array",true);
		String currency_Name = Utils.selectWithRandomIndex(160, cur_Name);
		Reporter.log("The CurrencyName is selected by random no is :" + currency_Name,true);
		//driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/input")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//form[@id='estimateAddForEmployee']/child::div/div/div/div/ul/li/span[text()='"+ currency_Name + "']")).click();
		driver.findElement(By.xpath("//span[text()='"+ currency_Name + "']")).click();
		Reporter.log("Click action is performed on Currency option",true);

		driver.findElement(By.xpath("//a[text()='Next']")).click();
		Reporter.log("Click action is performed on Next button",true);

		driver.findElement(By.xpath("(//button[text()='Add'])[1]")).click();
		Reporter.log("Click action is performed  on Add button",true);

		String main_Destination = ExcelConfig.getCellData(iTestData, Constant.col_Main_Destination,Constant.sheet_TravelRequestCases);
		Reporter.log("The Main_Destination read from excel is : " + main_Destination,true);
		driver.findElement(By.xpath("//input[@name='TravelInformation[main_destination]']")).sendKeys(main_Destination);
		Reporter.log("The value "+ main_Destination+" is entered as Main_Destination in the text-box",true);

		String fromDate = ExcelConfig.getCellData(iTestData, Constant.col_From_Date, Constant.sheet_TravelRequestCases);
		Reporter.log("The FromDate read from excel is : " + fromDate,true);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']")).click();
		WebElement element_FromDate = driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_FromDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_FromDate);
		
		Reporter.log("Click action is performed on calender for FromDate",true);
		Thread.sleep(5000);
		CommonMethod.date_HRM_08(fromDate, driver, 1);
		
		Thread.sleep(5000);
		String toDate = ExcelConfig.getCellData(iTestData, Constant.col_To_Date, Constant.sheet_TravelRequestCases);
		Reporter.log("The ToDate read from excel is : " + toDate,true);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']")).click();
		WebElement element_ToDate =driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_to]']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_ToDate);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_ToDate);

		Reporter.log("Click action is performed on calender for ToDate",true);
		Thread.sleep(5000);
		String date[] = toDate.split("/");
		// Select Year
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='"+date[2]+"'])[2]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_year);
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_year);
		Reporter.log("The Year selected from calender is:" + date[2],true);
		// Select Month
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[2]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='" + date[0] + "'])[2]")).click();
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_month);
		//((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_month);
		Reporter.log("The Month selected from calender is:" + date[0],true);
		// Select Date
		int rows = driver.findElements(By.xpath("(//table[@class='picker__table'])[3]/tbody/tr")).size();
		int cols = driver.findElements(By.xpath("(//table[@class='picker__table'])[3]/tbody/tr[1]/td")).size();
		Reporter.log("The no of cols is:" + cols + ", The no of rows is:" + rows,true);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
			Reporter.log("entered row for loop",true);
		for (int colsNo = 1; colsNo <= cols; colsNo++) {
			Reporter.log("entered column for loop",true);
				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr[" + rowNo + "]/td[" + colsNo + "]/div)[3]")).getText();
				Reporter.log(calDate,true);
				if (calDate.equalsIgnoreCase(date[1])) {
					Reporter.log("entered if loop",true);
					driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr["+ rowNo + "]/td[" + colsNo + "]/div)[3]")).click();
					Reporter.log("The Date is selected from calender is:" + toDate,true);
					break rows;
				}

			}
		}


		//CommonMethod.date_HRM_08(toDate, driver, 2);

		String dest_Address = RandomGenerator.randomAlphabetic(6);
		Reporter.log("The Dest_Address is selected by random Util is :" + dest_Address,true);
		driver.findElement(By.name("TravelInformation[destination_address]")).sendKeys(dest_Address);
		Reporter.log("The value "+ dest_Address+" is entered as Dest_Address in the text-box",true);
		driver.findElement(By.xpath("(//a[text()='Save'])[3]")).click();
		Reporter.log("Click action is performed on Save button",true);

		driver.findElement(By.xpath("(//button[text()='Add'])[2]")).click();
		Reporter.log("Click action is performed on Add",true);

		driver.findElement(By.xpath("//form[@id='selectionForm']/div/div/div/input")).click();
		String expense_Type = ExcelConfig.getCellData(iTestData, Constant.col_Expense_Type,Constant.sheet_TravelRequestCases);
		Reporter.log("The Expense_Type read from excel is : " + expense_Type,true);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='"+expense_Type+"']")).click();
		
		
		Reporter.log("The value "+ expense_Type+" is selected as Expense_Type in the dropdown",true);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		Reporter.log("Currency paid in is clicked",true);
		driver.findElement(By.xpath("//span[text()='"+currency_Name+"']")).click();
		/*
		 * Robot r = new Robot(); r.keyPress(KeyEvent.VK_DOWN);
		 * r.keyRelease(KeyEvent.VK_DOWN);
		 * 
		 * Thread.sleep(2000); r.keyPress(KeyEvent.VK_ENTER);
		 * r.keyRelease(KeyEvent.VK_ENTER);
		 */
		Reporter.log("Selected currency",true);
		
		String amount = RandomGenerator.randomNumeric(6);
		Reporter.log("The amount is selected by random Util is :" + amount,true);
		driver.findElement(By.xpath("//input[@name='estimation[amount]']")).sendKeys(amount);
		Reporter.log("The value "+ amount+" is entered as Amount in the text-box",true);
		driver.findElement(By.xpath("//select[@id='estimation_paid_by_id']/../input")).click();
		String paid_By = ExcelConfig.getCellData(iTestData, Constant.col_Paid_By, Constant.sheet_TravelRequestCases);
		Reporter.log("The paid_By read from excel is : " + paid_By,true);
		driver.findElement(By.xpath("//span[text()='" + paid_By + "']")).click();
		Reporter.log("The value "+ paid_By+" is selected as paid_By in the dropdown",true);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//a[text()='Save'])[2]")).click();
		Reporter.log("Click action is performed on Save button",true);

		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		Reporter.log("Screen shot is taken for Employee List ",true);

		driver.findElement(By.xpath("//a[text()='Submit']")).click();
		Reporter.log("Click action is performed on Submit button",true);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='OK']")).click();
		Reporter.log("Click action is performed on OK button",true);

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		Reporter.log("Click action is performed on My Travel Requests in the Menu bar",true);

		String requestID = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td a")).getText();
		Reporter.log("Request ID is "+requestID,true);
		String requestStatus = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td:nth-of-type(3)")).getText();
		Reporter.log("Request status is "+requestStatus,true);
		Utils.screenShot(screenshotPath + "\\RequestID.jpg", driver);
		Reporter.log("Screen shot is taken for Travel Request Id ",true);

		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as password in the text-box",true);

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		Reporter.log("Click action is performed on Login button for Admin",true);

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		Reporter.log("Click action is performed on Expenses in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		Reporter.log("Click action is performed on Travel Requests in the Menu bar",true);

		driver.findElement(By.xpath("//a[@id='menu_expense_viewEmployeeEstimateRequest']/span[2]")).click();
		Reporter.log("Click action is performed on Employee Travel Requests in the Menu bar",true);
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		Reporter.log("Click action is performed on Search button",true);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='estimateSearchForEmployee']/div/div/div/div/input")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Pending Supervisor Approval']")).click();
		Reporter.log("The value Pending Supervisor Approval is selected as RequestStatus in the dropdown",true);

		driver.findElement(By.xpath("//a[text()='Search']")).click();
		Reporter.log("Click action is performed on Search button",true);

		//driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/a[text()='" + requestID + "']")).click();
		//Reporter.log("Clicked on Request ID link");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='"+requestID+"']")).click();
		driver.findElement(By.xpath("//a[text()='Approve']")).click();
		Reporter.log("Click action is performed  on Approve button",true);

		driver.findElement(By.xpath("//a[text()='OK']")).click();
		Reporter.log("Click action is performed on OK button",true);
		Thread.sleep(1000);
		CommonMethod.logoutJaveExecuter(driver);

		driver.findElement(By.id("txtUsername")).sendKeys(employee_Name);
		Reporter.log("The value "+employee_Name+" is entered as userName in the text-box",true);

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as password in the text-box",true);

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		Reporter.log("Click action is performed on Login button for Employee",true);

		Utils.screenShot(screenshotPath + "\\OrangeHRMEmplopyeeLogin.jpg", driver);
		Reporter.log("Screen shot is taken for Employee Login ",true);

		driver.findElement(By.xpath("//span[text()='Expense']")).click();
		Reporter.log("Click action is performed on Expenses in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='Travel Requests']")).click();
		Reporter.log("Click action is performed on Travel Requests in the Menu bar",true);

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		Reporter.log("Click action is performed on My Travel Requests in the Menu bar",true);

		Utils.screenShot(screenshotPath + "\\TravelRequest.jpg", driver);
		Reporter.log("Screen shot is taken for Approved Travel Request ",true);
		Thread.sleep(2000);
		//CommonMethod.logoutJaveExecuter(driver);
		WebElement element_Logout = driver.findElement(By.xpath("//*[@id='account-job']/i"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_Logout);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_Logout);
		Thread.sleep(3000);
		driver.findElement(By.id("logoutLink")).click();
		Reporter.log("Click action is performed on Logout button",true);
		
		}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		driver.quit();
		// ENTERING IN EXCEL SHEET
		
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_Employee_Name, Constant.sheet_TravelRequestCases,excelPath);
		Reporter.log("The value "+employee_Name+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_Employee_Name+" in the "+Constant.sheet_TravelRequestCases,true);
		ExcelConfig.setCellData(currency_Name, iTestData, Constant.col_Currency_Name, Constant.sheet_TravelRequestCases,excelPath);
		Reporter.log("The value "+currency_Name+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Currency_Name+" in the "+Constant.sheet_TravelRequestCases,true);
		ExcelConfig.setCellData(dest_Address, iTestData, Constant.col_Destination_Address,Constant.sheet_TravelRequestCases, excelPath);
		Reporter.log("The value "+dest_Address+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Destination_Address+" in the "+Constant.sheet_TravelRequestCases,true);
		ExcelConfig.setCellData(requestStatus, iTestData, Constant.col_Request_Status,Constant.sheet_TravelRequestCases, excelPath);
		Reporter.log("The value "+requestStatus+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_Status+" in the "+Constant.sheet_TravelRequestCases,true);
		ExcelConfig.setCellData(requestID, iTestData, Constant.col_Request_ID, Constant.sheet_TravelRequestCases,excelPath);
		Reporter.log("The value "+requestID+" is written as currency_Name against to RowNumber "+iTestData +", column Number " +Constant.col_Request_ID+" in the "+Constant.sheet_TravelRequestCases,true);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_NewPhoneNo, Constant.sheet_TestCases,excelPath);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, excelPath);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
		
		Reporter.log("TestCase execution is completed",true);

	}

}
