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
		System.out.println("The Execution started for TC_01_AddEmployee");
		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Data read from Properties file.");		
		Properties prop = CommonMethod.PropertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.TestCaseID);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		System.out.println("The row no for test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestData, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD

		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.OpenBrowser(prop, iBrowser);
		String timestamp = Utils.TimeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.Validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddEmployeeCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddEmployeeCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as Password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");
		Thread.sleep(3000);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//li[contains(text(),'Dashboard')]")), "Dashboard"));
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			System.out.println(user.getMessage());
			user.printStackTrace();
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.PathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.PathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			throw new Exception();
		}

		// PIM Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		System.out.println("Click action is performed on PLM in the Menu bar");

		// Add employee click
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		System.out.println("Click action is performed on Add Employee in the Menu bar");
		// Enter name
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstName']")));

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		System.out.println("The firstName read from excel is : " + firstName);
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		System.out.println("The value "+ firstName+" is entered as firstName in the text-box");

		String middleName = ExcelConfig.getCellData(iTestData, Constant.col_middleName,
				Constant.sheet_AddEmployeeCases);
		System.out.println("The middleName read from excel is : " + middleName);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		System.out.println("The value "+ middleName+" is entered as middleName in the text-box");
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		System.out.println("The lastName read from excel is : " + lastName);
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		System.out.println("The value "+ lastName+" is entered as lastName in the text-box");

		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		String location = ExcelConfig.getCellData(iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases);
		System.out.println("The location read from excel is : " + location);
		driver.findElement(By.xpath("//span[contains(text(),'" + location + "')]")).click();
		System.out.println("The value "+ location+" is selected as location in the dropdown");
		Thread.sleep(2000);
		// click next button
		WebElement element_next=driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element_next);
		System.out.println("Click action is performed on Next button");
		Thread.sleep(3000);
		// select blood group
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@class='select-dropdown'])[3]")));
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[3]")).click();
		String bloodgroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup,
				Constant.sheet_AddEmployeeCases);
		System.out.println("The bloodgroup read from excel is : " + bloodgroup);
		driver.findElement(By.xpath("//span[text()='" + bloodgroup + "']")).click();
		System.out.println("The value "+ bloodgroup+" is selected as bloodgroup in the dropdown");

		// select hobbies
		String hobby = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		System.out.println("The hobby read from excel is : " + hobby);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobby);
		System.out.println("The value "+ hobby+" is entered as hobby in the text-box");

		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Clicked action is performed on Next button");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[6]")).click();
		String Region = ExcelConfig.getCellData(iTestData, Constant.Region, Constant.sheet_AddEmployeeCases);
		System.out.println("The Region read from excel is : " + Region);
		driver.findElement(By.xpath("//span[text()='" + Region + "']")).click();
		System.out.println("The value "+ Region+" is selected as Region in the dropdown");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[7]")).click();
		String FTE = ExcelConfig.getCellData(iTestData, Constant.FTE, Constant.sheet_AddEmployeeCases);
		System.out.println("The FTE read from excel is : " + FTE);		
		driver.findElement(By.xpath("//span[text()='" + FTE + "']")).click();
		System.out.println("The value "+ FTE+" is selected as FTE in the dropdown");

		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[8]")).click();
		String Temp_Department = ExcelConfig.getCellData(iTestData, Constant.Temp_Department,
				Constant.sheet_AddEmployeeCases);
		System.out.println("The Temp_Department read from excel is : " + Temp_Department);
		driver.findElement(By.xpath("//span[text()='" + Temp_Department + "']")).click();
		System.out.println("The value "+ Temp_Department+" is selected as Temp_Department in the dropdown");

		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Click action is performed Save button");
		Thread.sleep(3000);
		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.PathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);

		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);
		System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
				+" in the "+Constant.sheet_TestCases);

		System.out.println("The file are closed");

	}

}
