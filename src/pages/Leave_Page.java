package pages;

import java.io.IOException;

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
	
	public static WebDriverWait wait = new WebDriverWait(driver, 30);	
	public static String employeeName,leaveDateFrom,leaveDateTo;
	
	public static void SetLeaveData(int iTestData) throws Exception{
		// CLICKING FOR APPLY LEAVE FORM AND APPLY
		employeeName=driver.findElement(By.xpath("//span[@id='account-name']")).getText();
		Reporter.log("The leave is applying for the employee :" + employeeName,true);
		String leaveType = ExcelConfig.getCellData(iTestData, Constant.col_leaveType, Constant.sheet_ApplyLeaveCases);
		Reporter.log("The leaveType read from excel is:" + leaveType,true);
		String leaveDesc = ExcelConfig.getCellData(iTestData, Constant.col_leaveDesc, Constant.sheet_ApplyLeaveCases);
		Reporter.log("The leaveDesc read from excel is:" + leaveDesc,true);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_LeaveType));
		driver.findElement(dd_LeaveType).click();
		driver.findElement(By.xpath("//span[text()='" + leaveType + "']")).click();
		Reporter.log("The value "+ leaveType+" is selected as leaveType in the drop down",true);
		driver.findElement(txtbx_Desc).sendKeys(leaveDesc);
		Reporter.log("The value "+ leaveDesc+" is entered as leaveDesc in the text-box",true);
		
		// From date
		leaveDateFrom = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateFrom,Constant.sheet_ApplyLeaveCases);
		Reporter.log("The leaveDateFrom read from excel is:" + leaveDateFrom,true);
		Thread.sleep(3000);
		driver.findElement(click_DateFrom).click();
		Thread.sleep(5000);
		Reporter.log("Click action is performed on the Calender for From Date",true);

		CommonMethod.date_HRM(leaveDateFrom, driver, 1);

		// Select To Date
		leaveDateTo = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateTo,Constant.sheet_ApplyLeaveCases);
		Reporter.log("The leaveDateTo read from excel is:" + leaveDateTo,true);
		Thread.sleep(3000);
		driver.findElement(click_DateTo).click();
		Thread.sleep(5000);
		Reporter.log("Click action is performed on the Calender for To Date",true);
		CommonMethod.date_HRM(leaveDateTo, driver, 2);	
		driver.findElement(btn_Save).click();
		Reporter.log("Click action is performed on the Save Button",true);

	}

	public static void warningHanddling(String screenshotPath) throws Exception{
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
		Utils.screenShot(screenshotPath + "\\AppliedLeave.jpg", driver);
		Reporter.log("Screen shot is  taken for Applied Leave",true);
	}
	
	public static void approveLeave(String screenshotPath) throws Exception{
		Thread.sleep(5000);
		WebElement webelement_empname = driver.findElement(txtbx_employee);
		webelement_empname.sendKeys(employeeName);
	//	webelement_empname.sendKeys(Keys.DOWN);
	//	webelement_empname.sendKeys(Keys.ENTER);
		Reporter.log("The employeeName "+employeeName+" is enterted for search",true);
		Thread.sleep(3000);
		driver.findElement(btn_Search).click();
		Reporter.log("Click is performed on the button Search",true);
		
		try {
			WebElement userfoundmsg = driver.findElement(msg_Record);
			if (userfoundmsg.isDisplayed()) {
				Reporter.log("The Leave record is not found message displayed", true);
				CommonMethod.reason = "The Leave record  is not found message displayed";
				Assert.assertTrue(false, "The Leave record  is not found message displayed");
			}
		} catch (Exception user) {
			driver.findElement(dd_aproveLeave).click();
			Thread.sleep(3000);
			driver.findElement(dd_text).click();
			Reporter.log("The approve is selected from the dropdown",true);
			driver.findElement(By.xpath("//button[text()='Save']"));
			Reporter.log("Click is performed on the button Save",true);
			Utils.screenShot(screenshotPath + "\\ApprovedLeave.jpg", driver);
			Reporter.log("Screen shot is  taken for Leave approval",true);

		}
		
			}
	
	public static void verifyLeaveApproval(String screenshotPath) throws IOException, Exception{	
		
		try {
		WebElement webelement_All = driver.findElement(chckbx_All);
		WebElement webelement_Scheduled = driver.findElement(chckbx_Scheduled);
		if (webelement_All.isEnabled()) {			
			webelement_All.click();
			webelement_Scheduled.click();
			Reporter.log("The Scheduled option is Enabled with Single click on All", true);

		} else {

			webelement_All.click();
			webelement_All.click();
			webelement_Scheduled.click();
			Reporter.log("The Scheduled option is Enabled with double click on ALL", true);
		}
	} catch (Exception e) {
		Reporter.log("All Enabled option is not available", true);	}		
		
		// From date
		Thread.sleep(3000);
		driver.findElement(click_DateFrom).click();
		Thread.sleep(5000);
		Reporter.log("Click action is performed on the Calender for From Date",true);
		CommonMethod.date_HRM(leaveDateFrom, driver, 1);

		// Select To Date
		Thread.sleep(3000);
		driver.findElement(click_DateTo).click();
		Thread.sleep(5000);
		Reporter.log("Click action is performed on the Calender for To Date",true);
		
		String date[] = leaveDateTo.split("/");
		// Select Year
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[2]")).click();
		Thread.sleep(2000);
		WebElement element_year=driver.findElement(By.xpath("//span[text()='"+ date[2] +"']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element_year);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_year);
		System.out.println("The Year selected from calender is:" + date[2]);
		// Select Month
		
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='"+ date[0] +"']")).click();
		System.out.println("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(By.xpath("(//table[@class='picker__table'])[2]/tbody/tr")).size();
		int cols = driver.findElements(By.xpath("(//table[@class='picker__table'])[2]/tbody/tr[1]/td")).size();
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
		Reporter.log("Click is performed on the button Search",true);
		
		try {
			WebElement userfoundmsg = driver.findElement(msg_Record);
			if (userfoundmsg.isDisplayed()) {
				Reporter.log("The Leave record is not found message displayed", true);
				CommonMethod.reason = "The Leave record  is not found message displayed";
				Assert.assertTrue(false, "The Leave record  is not found message displayed");
			}
		} catch (Exception user) {
			Utils.screenShot(screenshotPath + "\\VerifyApproved.jpg", driver);
			Reporter.log("Screen shot is  taken for verifying the leave",true);
		}
		

	}
	
	public static void checkLeavebalance(){
	
		
	}
	
}
