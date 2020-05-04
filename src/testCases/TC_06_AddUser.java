package testCases;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_06_AddUser {
	public static void main(String[] args) throws Exception {
		
		System.out.println("The Execution started for TC_06_AddUser");
		// LOAD AND READ THE PROPERTIES FILE

		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		Properties prop = CommonMethod
				.PropertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_AddUserCases);
		System.out.println("The row no for test Data is : " + iTestData);
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

		String userNameLogin = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddUserCases);
		System.out.println("The userName read from excel is : " + userNameLogin);
		String passwordLogin = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddUserCases);
		System.out.println("The userName read from excel is : " + passwordLogin);

		driver.findElement(By.id("txtUsername")).sendKeys(userNameLogin);
		System.out.println("The value "+userNameLogin+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(passwordLogin);
		System.out.println("The value "+passwordLogin+" is entered as userName in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case id failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.PathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.PathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			throw new Exception();
		}

		// CLICK FOR USER AMENDMENT

		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		System.out.println("Click action is performed on Admin in the Menu bar");
		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		System.out.println("Click action is performed on User Management in the Menu bar");
		driver.findElement(By.xpath("//span[text()='Users']")).click();
		System.out.println("Click action is performed on Users in the Menu bar");

		// STORING ALL THE EMPLOYEE NAME IN THE PAGE

		int totalElementNo = driver.findElements(By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span")).size();
		System.out.println("The total no of employee in the page is: " + totalElementNo);
		List<WebElement> webelement_empName = driver
				.findElements(By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span"));
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		String employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		System.out.println("The employeeName selected by random no is:" + employeeName);
		String empNameSearch[] = employeeName.split(" ");

		driver.findElement(By.xpath("//i[text()='add']")).click();
		System.out.println("Click action is performed on add button");

		WebElement webelement1 = driver.findElement(By.xpath("//input[@id='selectedEmployee_value']"));
		webelement1.sendKeys(empNameSearch[0]);
		Thread.sleep(3000);
		webelement1.sendKeys(Keys.DOWN);
		webelement1.sendKeys(Keys.ENTER);
		System.out.println("The Employee name entered for search is: " + empNameSearch[0]);

		String randomAlphabet = RandomStringUtils.randomAlphabetic(4);
		String userName = empNameSearch[0].concat(randomAlphabet);
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys(userName);
		System.out.println("The value "+ userName+" is entered as userName in the text-box");
		ExcelConfig.setCellData(userName, iTestData, Constant.col_UserID, Constant.sheet_AddUserCases,
				CommonMethod.PathExcel);
		System.out.println("The value "+userName+" is written as userName against to RowNumber "+iTestData +", column Number " +Constant.col_UserID
				+" in the "+Constant.sheet_AddUserCases);
		String adminRole = ExcelConfig.getCellData(iTestData, Constant.col_AdminRole, Constant.sheet_AddUserCases);
		System.out.println("The adminRole read from excel is : " + adminRole);

		driver.findElement(By.xpath("//div[@id='adminrole_inputfileddiv']/div/input")).click();
		driver.findElement(By.xpath("//span[text()='" + adminRole + "']")).click();
		System.out.println("The value "+ adminRole+" is entered as adminRole in the drop down");
		driver.findElement(By.xpath("//div[@id='status_inputfileddiv']/div/input")).click();
		driver.findElement(By.xpath("//div[@id='status_inputfileddiv']/div/ul/li/span[text()='Enabled']")).click();
		System.out.println("Admin Role is Enabled ");
		String password1 = ExcelConfig.getCellData(iTestData, Constant.col_UserPassword, Constant.sheet_AddUserCases);
		System.out.println("The password read from excel is : " + password1);

		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password1);
		System.out.println("The value "+ password1+" is entered as password in the text-box");
		driver.findElement(By.xpath("//input[@id='confirmpassword']")).sendKeys(password1);
		System.out.println("The value "+ password1+" is entered as Confirm password in the text-box");
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		System.out.println("Click action is performed on Save button");
		Thread.sleep(3000);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='modal-footer']/a[text()='Save']")));


		driver.findElement(By.xpath("//div[@class='modal-footer']/a[text()='Save']")).click();
		System.out.println("Click action is performed on Save button is for region");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[text()='ohrm_filter']")));
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		System.out.println("Click action is performed on Filter");
		driver.findElement(By.xpath("//input[@id='systemuser_uname_filter']")).sendKeys(userName);
		System.out.println("The value "+ userName+" is entered as userName in the Search text-box");
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		System.out.println("Click action is performed on serched button");

		try {
			WebElement userfoundmsg = driver.findElement(By.xpath("//div[text()='No Records Found']"));
			if (userfoundmsg.isDisplayed()) {
				System.out.println("The User is not found message displayed");
			}
		} catch (Exception user) {
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMUser.jpg", driver);
			System.out.println("User detail page is found for the user"+userName);
		}

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
		System.out.println("The login will be done and validated for new user");

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password1);
		System.out.println("The value "+password1+" is entered as password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed  on log in button for new user");
		String username1_validation = driver.findElement(By.xpath("//span[@id='account-name']")).getText();
		Utils.ScreenShot(screenshotPath + "\\" + userName + "_Login.jpg", driver);
		CommonMethod.Validation(employeeName, username1_validation, iTestCase);

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();

		System.out.println("Click action is performed on Logoout button for New User");

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
