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

//CLASS VARIABLE DECLARATION

public class TC_01_AddEmployee {

	public static void main(String[] args) throws Exception {

		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println(CommonMethod.projectpath);
		Properties prop = CommonMethod.PropertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		// Properties
		// prop=CommonMethod.PropertilesRead("C:\\Users\\aneesh\\eclipse-workspace\\SeleniumAutomation\\src\\Test-Resources\\TestInfo.properties.properties");
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
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

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddEmployeeCases);
		System.out.println("The value of userName is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddEmployeeCases);
		System.out.println("The value of password is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click on log in button ");
		Thread.sleep(3000);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//li[contains(text(),'Dashboard')]")), "Dashboard"));
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Dashboard is avilable, Screen Print taken");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			System.out.println(user.getMessage());
			user.printStackTrace();
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.PathExcel);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.PathExcel);
			throw new Exception();
		}

		// PIM Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		System.out.println("Clicked on PIM");

		// Add employee click
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		System.out.println("Clicked on Add Employee");
		// Enter name
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstName']")));

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		System.out.println("Entered First Name");

		String middleName = ExcelConfig.getCellData(iTestData, Constant.col_middleName,
				Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		System.out.println("Entered Middle Name");

		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		System.out.println("Entered Last Name");

		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		String location = ExcelConfig.getCellData(iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[contains(text(),'" + location + "')]")).click();
		
		Thread.sleep(2000);
		// click next button
		WebElement element_next=driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element_next);
		System.out.println("clicked on Next button");
		Thread.sleep(3000);
		// select blood group
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@class='select-dropdown'])[3]")));
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[3]")).click();
		String bloodgroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup,
				Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + bloodgroup + "']")).click();
		System.out.println("Entered blood group");

		// select hobbies
		String hobby = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobby);
		System.out.println("Entered hobbies");

		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Clicked on Next button");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[6]")).click();
		String Region = ExcelConfig.getCellData(iTestData, Constant.Region, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + Region + "']")).click();
		System.out.println("Entered Region");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[7]")).click();
		String FTE = ExcelConfig.getCellData(iTestData, Constant.FTE, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + FTE + "']")).click();
		System.out.println("Entered FTE");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[8]")).click();
		String Temp_Department = ExcelConfig.getCellData(iTestData, Constant.Temp_Department,
				Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + Temp_Department + "']")).click();
		System.out.println("Entered Temporary Department");

		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Entered Save");
		Thread.sleep(3000);
		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);

		System.out.println("The file are closed");

	}

}
