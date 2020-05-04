package testCases;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_05_AplyLeave {
	public static void main(String[] args) throws Exception {

		// CLASS VARIABLE DECLARATION
		System.out.println("The Execution started for TC_05_AplyLeave");
		// LOAD AND READ THE PROPERTIES FILE

		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		Properties prop = CommonMethod
				.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		System.out.println("The Testcase id executing is :"+CommonMethod.TestCaseID);

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The row no for test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD

		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.openBrowser(prop, iBrowser);
		String timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// LOGIN TO DASHBOARD

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_ApplyLeaveCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_ApplyLeaveCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as Password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		// CLICKING FOR APPLY LEAVE FORM AND APPLY

		String leaveType = ExcelConfig.getCellData(iTestData, Constant.col_leaveType, Constant.sheet_ApplyLeaveCases);
		System.out.println("The leaveType read from excel is:" + leaveType);
		String leaveDesc = ExcelConfig.getCellData(iTestData, Constant.col_leaveDesc, Constant.sheet_ApplyLeaveCases);
		System.out.println("The leaveDesc read from excel is:" + leaveDesc);
		driver.findElement(By.xpath("//span[text()='Leave']")).click();
		System.out.println("Click action is performed on Leave in the Menu bar");
		driver.findElement(By.xpath("//span[text()='Apply']")).click();
		System.out.println("Click action is performed on Apply in the Menu bar");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select-dropdown']")));
		driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
		driver.findElement(By.xpath("//span[text()='" + leaveType + "']")).click();
		System.out.println("The value "+ leaveType+" is selected as leaveType in the drop down");
		driver.findElement(By.xpath("//div[@class='input-field col s12 m12 l12']/textarea")).sendKeys(leaveDesc);
		System.out.println("The value "+ leaveDesc+" is entered as leaveDesc in the text-box");
		// From date
		String leaveDateFrom = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateFrom,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The leaveDateFrom read from excel is:" + leaveDateFrom);
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]")).click();
		Thread.sleep(5000);
		System.out.println("Click action is performed on the Calender for From Date");

		CommonMethod.date_HRM(leaveDateFrom, driver, 1);

		// Select To Date
		String leaveDateTo = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateTo,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The leaveDateTo read from excel is:" + leaveDateTo);
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[2]")).click();
		Thread.sleep(10000);
		System.out.println("Click action is performed on the Calender for To Date");
		CommonMethod.date_HRM(leaveDateTo, driver, 2);

		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-green']")).click();
		System.out.println("Click action is performed on the Save Button");

		// INSUFFICENT LEAVE WARNNING PAGE

		try {
			WebElement element = driver.findElement(By.xpath("//div[@class='modal-heading']/h4"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			System.out.println("Found the xpath");

			if (element.isDisplayed()) {
				WebElement element1 = driver
						.findElement(By.xpath("//a[@class='modal-action waves-effect btn primary-btn']"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element1);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);

				System.out.println("Click action is performed on Ok button for Insufficent leave Warnning");
			}

		} catch (Exception user) {
			System.out.println(" There is no warnning for Insufficent Balance,Leave applied");
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

				System.out.println("Leave Overlapping,Click action is performed on Close button");
			}
		} catch (Exception user) {
			System.out.println(" There is no warnning for Overlapping  ,Leave applied");
		}

		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				System.out.println("Successfully Leave applied message is verified");
			}

		} catch (Exception user) {
			System.out.println("Leave is not applied Successfully");
		}

		// LOGOUT AND CLOSING THE BROWSER.
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[text()='keyboard_arrow_down']")));
		driver.findElement(By.xpath("//i[text()='keyboard_arrow_down']")).click();
		driver.findElement(By.id("logoutLink")).click();
		driver.quit();
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.PathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		System.out.println("The file are closed");

	}

}
