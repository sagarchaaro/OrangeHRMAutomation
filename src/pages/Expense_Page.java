package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.RandomGenerator;
import utilities.Utils;

public class Expense_Page extends BaseClass{
	
	public Expense_Page(WebDriver driver) {
		super(driver);
	}
	
	static By dd_currency = By.cssSelector("#estimateAddForEmployee div div div div input");
	static By btn_next = By.xpath("//a[text()='Next']");
	static By btn_add = By.xpath("(//button[text()='Add'])[1]");
	static By txtbx_mainDestination = By.xpath("//input[@name='TravelInformation[main_destination]']");
	static By txtbx_fromDate = By.xpath("//input[@name='TravelInformation[travel_period_from]']");
	static By txtbx_toDate = By.xpath("//input[@name='TravelInformation[travel_period_to]']");
	static By txtbx_destinationAddress = By.name("TravelInformation[destination_address]");
	static By btn_save = By.xpath("(//a[text()='Save'])[3]");
	static By btn_addRequestEstimates = By.xpath("(//button[text()='Add'])[2]");
	static By dd_expense = By.xpath("//form[@id='selectionForm']/div/div/div/input");
	static By dd_currencyName = By.xpath("//select[@id='estimation_currency_id']/../input");
	static By txtbx_amount = By.xpath("//input[@name='estimation[amount]']");
	static By dd_paidBy = By.xpath("//select[@id='estimation_paid_by_id']/../input");
	static By btn_saveRequestEstimates = By.xpath("(//a[text()='Save'])[2]");
	static By btn_submit = By.xpath("//a[text()='Submit']");
	static By btn_OK = By.xpath("//a[text()='OK']");
	static By btn_search = By.xpath("//i[text()='ohrm_filter']");
	static By txtbx_search = By.xpath("//form[@id='estimateSearchForEmployee']/div/div/div/div/input");
	static By btn_approve = By.xpath("//a[text()='Approve']");
	static By btn_Ok = By.xpath("//a[text()='OK']");
	
	public static String currency_Name, requestID;
	
	public static void currencyDetails() throws Exception {
		Thread.sleep(5000);
		driver.switchTo().frame(0);

		driver.findElement(By.xpath("//i[text()='add']")).click();
		Reporter.log("Click action is performed on Add button",true);
		// driver.switchTo().defaultContent();

		Thread.sleep(3000);

		driver.findElement(dd_currency).click();
		List<WebElement> currencyName = driver.findElements(By.cssSelector("#estimateAddForEmployee div div div div ul li span"));
		Reporter.log("All CurrencyName are stored in the WebElement",true);
		String[] cur_Name = Utils.dataIntoArray(currencyName, 160);
		Reporter.log("All CurrencyName are stored in the Array",true);
		currency_Name = Utils.selectWithRandomIndex(160, cur_Name);
		Reporter.log("The CurrencyName is selected by random no is :" + currency_Name,true);
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//span[text()='"+ currency_Name + "']")).click();
		Reporter.log("Click action is performed on Currency option",true);

		driver.findElement(btn_next).click();
		Reporter.log("Click action is performed on Next button",true);
	}

	public static void travelInformation(int iTestData) throws Exception{
		// TODO Auto-generated method stub
		driver.findElement(btn_add).click();
		Reporter.log("Click action is performed  on Add button",true);

		String main_Destination = ExcelConfig.getCellData(iTestData, Constant.col_Main_Destination,Constant.sheet_TravelRequestCases);
		Reporter.log("The Main_Destination read from excel is : " + main_Destination,true);
		driver.findElement(txtbx_mainDestination).sendKeys(main_Destination);
		Reporter.log("The value "+ main_Destination+" is entered as Main_Destination in the text-box",true);

		String fromDate = ExcelConfig.getCellData(iTestData, Constant.col_From_Date, Constant.sheet_TravelRequestCases);
		Reporter.log("The FromDate read from excel is : " + fromDate,true);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@name='TravelInformation[travel_period_from]']")).click();
		WebElement element_FromDate = driver.findElement(txtbx_fromDate);
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
		WebElement element_ToDate =driver.findElement(txtbx_toDate);
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
		driver.findElement(txtbx_destinationAddress).sendKeys(dest_Address);
		Reporter.log("The value "+ dest_Address+" is entered as Dest_Address in the text-box",true);
		driver.findElement(btn_save).click();
		Reporter.log("Click action is performed on Save button",true);

	}
	
	public static void travelRequestEstimates(int iTestData) throws Exception{
		driver.findElement(btn_addRequestEstimates).click();
		Reporter.log("Click action is performed on Add",true);

		driver.findElement(dd_expense).click();
		String expense_Type = ExcelConfig.getCellData(iTestData, Constant.col_Expense_Type,Constant.sheet_TravelRequestCases);
		Reporter.log("The Expense_Type read from excel is : " + expense_Type,true);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='"+expense_Type+"']")).click();
		
		
		Reporter.log("The value "+ expense_Type+" is selected as Expense_Type in the dropdown",true);
		Thread.sleep(2000);
		driver.findElement(dd_currencyName).click();
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
		driver.findElement(txtbx_amount).sendKeys(amount);
		Reporter.log("The value "+ amount+" is entered as Amount in the text-box",true);
		driver.findElement(dd_paidBy).click();
		String paid_By = ExcelConfig.getCellData(iTestData, Constant.col_Paid_By, Constant.sheet_TravelRequestCases);
		Reporter.log("The paid_By read from excel is : " + paid_By,true);
		driver.findElement(By.xpath("//span[text()='" + paid_By + "']")).click();
		Reporter.log("The value "+ paid_By+" is selected as paid_By in the dropdown",true);
		Thread.sleep(2000);
		driver.findElement(btn_saveRequestEstimates).click();
		Reporter.log("Click action is performed on Save button",true);

	}
	
	public static void submitRequestAndGettingRequestID() throws Exception {
		driver.findElement(btn_submit).click();
		Reporter.log("Click action is performed on Submit button",true);
		Thread.sleep(2000);
		driver.findElement(btn_OK).click();
		Reporter.log("Click action is performed on OK button",true);

		driver.findElement(By.xpath("//span[text()='My Travel Requests']")).click();
		Reporter.log("Click action is performed on My Travel Requests in the Menu bar",true);

		requestID = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td a")).getText();
		Reporter.log("Request ID is "+requestID,true);
		String requestStatus = driver.findElement(By.cssSelector(".highlight.bordered tbody tr:nth-child(1) td:nth-of-type(3)")).getText();
		Reporter.log("Request status is "+requestStatus,true);
	}
	
	public static void approveRequest() throws Exception{
		Thread.sleep(5000);
		driver.switchTo().frame(0);
		driver.findElement(btn_search).click();
		Reporter.log("Click action is performed on Search button",true);
		Thread.sleep(2000);
		driver.findElement(txtbx_search).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Pending Supervisor Approval']")).click();
		Reporter.log("The value Pending Supervisor Approval is selected as RequestStatus in the dropdown",true);

		driver.findElement(By.xpath("//a[text()='Search']")).click();
		Reporter.log("Click action is performed on Search button",true);

		//driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/a[text()='" + requestID + "']")).click();
		//Reporter.log("Clicked on Request ID link");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='"+requestID+"']")).click();
		driver.findElement(btn_approve).click();
		Reporter.log("Click action is performed  on Approve button",true);

		driver.findElement(btn_Ok).click();
		Reporter.log("Click action is performed on OK button",true);
	}

}
