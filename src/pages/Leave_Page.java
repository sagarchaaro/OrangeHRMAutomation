package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.Utils;

public class Leave_Page extends BaseClass{
	
	public Leave_Page(WebDriver driver) {
		super(driver);
		
	}
	static By dd_LeaveType = By.xpath("//input[@class='select-dropdown']");
	static By txtbx_Desc = By.xpath("//div[@class='input-field col s12 m12 l12']/textarea");
	static By click_DateFrom = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]");
	static By click_DateTo = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[2]");
	static By btn_Save = By.xpath("//button[@class='btn waves-effect waves-green']");
	static By msg_Warnning = By.xpath("//div[@class='modal-heading']/h4");
	static By btn_Close = By.xpath("//a[@class='modal-action waves-effect btn']");
	static By btn_Ok = By.xpath("//a[@class='modal-action waves-effect btn primary-btn']");
	static By msg_Success = By.xpath("//div[@class='toast toast-success']");
	static By txtbx_employee = By.xpath("//input[@id='selectedEmployee_value']");
	static By btn_Search = By.xpath("//button[text()='Search']");
	static By dd_aproveLeave = By.xpath("//div[@class='select-wrapper ng-pristine ng-untouched ng-valid ng-empty']");
	static By dd_text = By.xpath("//span[text()='Approve']");
	static By chckbx_All = By.xpath("//label[@for='statusAll']");
	static By chckbx_Scheduled = By.xpath("//label[@for='scheduled']");
	static By msg_Record = By.xpath("//div[text()='No Records Found']");
	static By click_LeaveBalance = By.xpath("//a[text()='Check Leave Balance']");
	static By list_leave = By.xpath("//table[@class='highlight bordered']/tbody/tr/td[3]/ng-include/span");
	static By btn_closeBalance = By.xpath("//a[text()='Close']");
	static By link_Menu=By.xpath("//span[text()='{0}']");
	static By text_employee = By.xpath("//span[@id='account-name']");
	static By btn_SaveApprove = By.xpath("//button[text()='Save']");
	static By link_year = By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[2]");
	static By dd_select=By.xpath("//span[text()='{0}']");
	static By link_month = By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[1]");
	static By size_row = By.xpath("(//table[@class='picker__table'])[2]/tbody/tr");
	static By size_cols = By.xpath("(//table[@class='picker__table'])[2]/tbody/tr[1]/td");
	static By txt_calDate = By.xpath("(//table[@class='picker__table'])[{0}]/tbody/tr[{0}]/td[{0}]/div");
	
	
	public static WebDriverWait wait = new WebDriverWait(driver, 30);	
	public static String employeeName,leaveDateFrom,leaveDateTo;
	
	public static void SetLeaveData(int iTestData) throws Exception{
		// CLICKING FOR APPLY LEAVE FORM AND APPLY
		employeeName=driver.findElement(text_employee).getText();
		Log.info("The leave is applying for the employee :" + employeeName);
		String leaveType = ExcelConfig.getCellData(iTestData, Constant.col_leaveType, Constant.sheet_ApplyLeaveCases);
		Log.info("The leaveType read from excel is:" + leaveType);
		String leaveDesc = ExcelConfig.getCellData(iTestData, Constant.col_leaveDesc, Constant.sheet_ApplyLeaveCases);
		Log.info("The leaveDesc read from excel is:" + leaveDesc);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_LeaveType));
		driver.findElement(dd_LeaveType).click();
		driver.findElement(CommonMethod.formatLocator(link_Menu, leaveType)).click();
		Log.info("The value "+ leaveType+" is selected as leaveType in the drop down");
		driver.findElement(txtbx_Desc).sendKeys(leaveDesc);
		Log.info("The value "+ leaveDesc+" is entered as leaveDesc in the text-box");
		
		// From date
		leaveDateFrom = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateFrom,Constant.sheet_ApplyLeaveCases);
		Log.info("The leaveDateFrom read from excel is:" + leaveDateFrom);
		Thread.sleep(3000);
		driver.findElement(click_DateFrom).click();
		Thread.sleep(5000);
		Log.info("Click action is performed on the Calender for From Date");

		CommonMethod.date_HRM(leaveDateFrom, driver, 1);

		// Select To Date
		leaveDateTo = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateTo,Constant.sheet_ApplyLeaveCases);
		Log.info("The leaveDateTo read from excel is:" + leaveDateTo);
		Thread.sleep(3000);
		driver.findElement(click_DateTo).click();
		Thread.sleep(5000);
		Log.info("Click action is performed on the Calender for To Date");
		CommonMethod.date_HRM(leaveDateTo, driver, 2);	
		driver.findElement(btn_Save).click();
		Log.info("Click action is performed on the Save Button");
		Reporter.log("SetLeaveData method execution is completed",true);
	}

	public static void warningHanddling(String screenshotPath) throws Exception{
		// INSUFFICENT LEAVE WARNNING PAGE

		try {
			WebElement element = driver.findElement(msg_Warnning);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			Log.info("Found the xpath");

			if (element.isDisplayed()) {
				WebElement element1 = driver.findElement(btn_Ok);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);

				Log.info("Click action is performed on Ok button for Insufficent leave Warnning");
			}

		} catch (Exception user) {
		Log.info(" There is no warnning for Insufficent Balance,Leave applied");
		}

		// OVERLAPPING LEAVE REQUEST PAGE

		try {
			WebElement element2 = driver.findElement(msg_Warnning);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element2);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);

			if (element2.isDisplayed()) {
				WebElement element3 = driver.findElement(btn_Close);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element3);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element3);

				Log.info("Leave Overlapping,Click action is performed on Close button");
			}
		} catch (Exception user) {
			Log.info(" There is no warnning for Overlapping  ,Leave applied");
		}

		try {
			if (driver.findElement(msg_Success).isDisplayed()) {
				Log.info("Successfully Leave applied message is verified");
			}

		} catch (Exception user) {
			Log.info("Leave is not applied Successfully");
		}
		Utils.screenShot(screenshotPath + "\\AppliedLeave.jpg", driver);
		Log.info("Screen shot is  taken for Applied Leave");
		Reporter.log("warningHanddling method execution is completed",true);
	}
	
	public static void approveLeave(String screenshotPath) throws Exception{
		Thread.sleep(5000);
		WebElement webelement_empname = driver.findElement(txtbx_employee);
		webelement_empname.sendKeys(employeeName);
	//	webelement_empname.sendKeys(Keys.DOWN);
	//	webelement_empname.sendKeys(Keys.ENTER);
		Log.info("The employeeName "+employeeName+" is enterted for search");
		Thread.sleep(3000);
		driver.findElement(btn_Search).click();
		Log.info("Click is performed on the button Search");
		
		try {
			WebElement userfoundmsg = driver.findElement(msg_Record);
			if (userfoundmsg.isDisplayed()) {
				Log.info("The Leave record is not found message displayed");
				CommonMethod.reason = "The Leave record  is not found message displayed";
				Assert.assertTrue(false, "The Leave record  is not found message displayed");
			}
		} catch (Exception user) {
			driver.findElement(dd_aproveLeave).click();
			Log.info("Click is performed on drop down to approve");
			Thread.sleep(3000);
			driver.findElement(dd_text).click();
			Log.info("The approve is selected from the dropdown");
			Thread.sleep(2000);
			driver.findElement(btn_SaveApprove).click();
			Log.info("Click is performed on the button Save");
			Utils.screenShot(screenshotPath + "\\ApprovedLeave.jpg", driver);
			Log.info("Screen shot is  taken for Leave approval");

		}
		
			}
	
	public static void verifyLeaveApproval(String screenshotPath) throws IOException, Exception{	
		
		try {
		WebElement webelement_All = driver.findElement(chckbx_All);
		WebElement webelement_Scheduled = driver.findElement(chckbx_Scheduled);
		if (webelement_All.isEnabled()) {			
			webelement_All.click();
			webelement_Scheduled.click();
			Log.info("The Scheduled option is Enabled with Single click on All");

		} else {

			webelement_All.click();
			webelement_All.click();
			webelement_Scheduled.click();
			Log.info("The Scheduled option is Enabled with double click on ALL");
		}
	} catch (Exception e) {
		Log.info("All Enabled option is not available");	}		
		
		// From date
		Thread.sleep(3000);
		driver.findElement(click_DateFrom).click();
		Thread.sleep(5000);
		Log.info("Click action is performed on the Calender for From Date");
		CommonMethod.date_HRM(leaveDateFrom, driver, 1);

		// Select To Date
		Thread.sleep(3000);
		driver.findElement(click_DateTo).click();
		Thread.sleep(5000);
		Log.info("Click action is performed on the Calender for To Date");
		
		String date[] = leaveDateTo.split("/");
		// Select Year
		driver.findElement(link_year).click();
		Thread.sleep(2000);
		WebElement element_year=driver.findElement(CommonMethod.formatLocator(dd_select, date[2]));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_year);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_year);
		System.out.println("The Year selected from calender is:" + date[2]);
		// Select Month
		
		driver.findElement(link_month).click();
		Thread.sleep(3000);
/*		WebElement element_month=driver.findElement(CommonMethod.formatLocator(dd_select, date[0]));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_month);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_month);*/
		driver.findElement(CommonMethod.formatLocator(dd_select, date[0])).click();
		System.out.println("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(size_row).size();
		int cols = driver.findElements(size_cols).size();
		System.out.println("The no of cols is:" + cols + ", The no of rows is:" + rows);
		System.out.println("date[1]" + date[1]);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
		for (int colsNo = 1; colsNo <= cols; colsNo++) {

				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table'])[2]/tbody/tr[" + rowNo + "]/td[" + colsNo + "]/div")).getText();
				System.out.println("calDate]" +calDate);
				if (calDate.equalsIgnoreCase(date[1])) {
					driver.findElement(By.xpath("(//table[@class='picker__table'])[2]/tbody/tr["+ rowNo + "]/td[" + colsNo + "]/div")).click();
					System.out.println("The Date is selected from calender is:" + leaveDateTo);
					break rows;
				}

			}
		}

	//	CommonMethod.date_HRM(leaveDateTo, driver, 2);
		driver.findElement(btn_Search);
		Log.info("Click is performed on the button Search");
		
		try {
			WebElement userfoundmsg = driver.findElement(msg_Record);
			if (userfoundmsg.isDisplayed()) {
				Log.info("The Leave record is not found message displayed");
				CommonMethod.reason = "The Leave record  is not found message displayed";
				Assert.assertTrue(false, "The Leave record  is not found message displayed");
			}
		} catch (Exception user) {
			Utils.screenShot(screenshotPath + "\\VerifyApproved.jpg", driver);
			Log.info("Screen shot is  taken for verifying the leave");
		}
		
		Reporter.log("approveLeave method execution is completed",true);
	}
	
	public static void checkLeavebalance(){
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(click_LeaveBalance));
		driver.findElement(click_LeaveBalance).click();
		
		int totalElementNo = driver.findElements(list_leave).size();
		Log.info("The total no of leave bracket in the page is: " + totalElementNo);
		List<WebElement> webelement_LeaveMonth = driver.findElements(list_leave);
		Log.info("All Leave Bracket are stored in the WebElement");
		String[] leaveMonthArray = Utils.dataIntoArray(webelement_LeaveMonth, totalElementNo);
		Log.info("All Leave Bracket are stored in the Array");
		
		String leaveDate_input[]=leaveDateTo.split("/");
		
		for (int row=1;row<totalElementNo;row++) {			
			
			String leavedate_page[] = leaveMonthArray[row].split(" ");			
			
			if (leavedate_page[2].equalsIgnoreCase(leaveDate_input[0])) {
				WebElement leaveBalance=driver.findElement
				(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[3]/ng-include/span[text()='"+leaveMonthArray[row]+"']"));
			    String noOfLeave=leaveBalance.getText();
			    Log.info("No of Leave available for the period is:"+noOfLeave);
			    break;
			}
		}
		driver.findElement(btn_closeBalance);
		Reporter.log("checkLeavebalance method execution is completed",true);
		
	}
	
	
}
