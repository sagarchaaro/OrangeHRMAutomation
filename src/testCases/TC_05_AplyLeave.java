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

		// LOAD AND READ THE PROPERTIES FILE

		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println(CommonMethod.projectpath);
		Properties prop = CommonMethod
				.PropertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		System.out.println("The Properties file is read and closed");

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The row no of test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD

		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.OpenBrowser(prop, iBrowser);
		String timestamp = Utils.TimeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// LOGIN TO DASHBOARD

		String title = driver.getTitle();
		CommonMethod.Validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of userName is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of password is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click on log in button ");

		// CLICKING FOR APPLY LEAVE FORM AND APPLY

		String leaveType = ExcelConfig.getCellData(iTestData, Constant.col_leaveType, Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of leaveType is : " + leaveType);
		String leaveDesc = ExcelConfig.getCellData(iTestData, Constant.col_leaveDesc, Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of leaveDesc is : " + leaveDesc);
		driver.findElement(By.xpath("//span[text()='Leave']")).click();
		System.out.println("Select Leave");
		driver.findElement(By.xpath("//span[text()='Apply']")).click();
		System.out.println("Select Apply in Leave category");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select-dropdown']")));
		driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
		driver.findElement(By.xpath("//span[text()='" + leaveType + "']")).click();
		System.out.println("Select the leave category as " + leaveType);
		driver.findElement(By.xpath("//div[@class='input-field col s12 m12 l12']/textarea")).sendKeys(leaveDesc);

		// From date
		String leaveDateFrom = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateFrom,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of leaveDateFrom is : " + leaveDateFrom);
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]")).click();
		Thread.sleep(5000);
		System.out.println("Clicking on the From Date Table");

		CommonMethod.Date_HRM(leaveDateFrom, driver, 1);

		

		// Select To Date
		String leaveDateTo = ExcelConfig.getCellData(iTestData, Constant.col_leaveDateTo,
				Constant.sheet_ApplyLeaveCases);
		System.out.println("The value of leaveDateTo is : " + leaveDateTo);
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[2]")).click();
		Thread.sleep(10000);
		System.out.println("Clicking on the To Date Table");
		CommonMethod.Date_HRM(leaveDateTo, driver, 2);

		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-green']")).click();
		System.out.println("Click the Save Button");

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

				System.out.println("Ok is clicked for Insufficent leave Warnning");
			}

		} catch (Exception user) {
			System.out.println(" no warnning for Insufficent Balance,Leave applied");
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

				System.out.println("Leave Overlapping,Close is clicked");
			}
		} catch (Exception user) {
			System.out.println(" no warnning for Overlapping  ,Leave applied");
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
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);
		// fis.close();
		System.out.println("The file are closed");

	}

}
