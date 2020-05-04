package testCases;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_04_EditEmployee {
	public static void main(String[] args) throws InterruptedException, Exception {

		System.out.println("The Execution started for TC_04_EditEmployee");

		// LOAD AND READ THE PROPERTIES FILE

		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		Properties prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.testCaseID);

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,
				Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,
				Constant.sheet_EditEmployeeCases);
		System.out.println("The row no for of test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD

		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.openBrowser(prop, iBrowser);
		String timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		// LOGIN TO THE DASHBOARD
		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_EditEmployeeCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_EditEmployeeCases);
		System.out.println("The password read from excel is : " + password);

		// user name
		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		// Password
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as Password in the text-box");
		// Login Click
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		// CLICK ON THE LIST OF EMPLOYEE AND EDIT THE 1ST RECORD

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		System.out.println("Click action is performed on PLM in the Menu bar");
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='left-menu-title'][text()='Employee List']")));
		// click on list of employee
		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Employee List']")).click();
		System.out.println("Click action is performed on Employee list in the Menu bar");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='nowrap cursor-pointer']")));
		// Search employee
		driver.findElement(By.xpath("//td[@class='nowrap cursor-pointer']")).click();
		System.out.println("Click action is performed on first Link of Employee list");
		String employeeName=driver.findElement(By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[3]")).getText();
		System.out.println("Edit employee activity is performed  for the employee :"+employeeName);
		ExcelConfig.setCellData(employeeName, iTestData, Constant.col_EditEmployeeName, Constant.sheet_EditEmployeeCases,
				CommonMethod.pathExcel);
		System.out.println("The value "+employeeName+" is written as EditEmployeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EditEmployeeName
				+" in the "+Constant.sheet_EditEmployeeCases);
		
		String employeeID=driver.findElement(By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[2]")).getText();
		System.out.println("Edit employee activity is performed  for the employee ID :"+employeeID);
		ExcelConfig.setCellData(employeeID, iTestData, Constant.col_EditEmployeeID, Constant.sheet_EditEmployeeCases,
				CommonMethod.pathExcel);
		System.out.println("The value "+employeeID+" is written as EditEmployeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EditEmployeeID
				+" in the "+Constant.sheet_EditEmployeeCases);
		
		// PERSONAL DETAIL TO UPDATE

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_EditEmployeeCases);
		System.out.println("The lastName read from excel is : " + lastName);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		System.out.println("The value "+ lastName+" is entered as lastName in the text-box");
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]")).click();
		System.out.println("Click action is performed on calender for DOB");
		String dateOfBirthFomat1 = ExcelConfig.getCellData(iTestData, Constant.col_DateOfBirth,
				Constant.sheet_EditEmployeeCases);
		CommonMethod.date_HRM(dateOfBirthFomat1, driver, 1);

		driver.findElement(By.xpath("(//div[@class='select-wrapper initialized']/input)[2]")).click();
		String nationalty = ExcelConfig.getCellData(iTestData, Constant.col_Nationality,
				Constant.sheet_EditEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + nationalty + "']")).click();
		System.out.println("The value "+ nationalty+" is selected as nationalty from dropdown");
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[1]")).click();
		System.out.println("Click action is performed on save button for the Personal detail update");
		try {
			WebElement webelement_EEO = driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']/..//child::input"));
			webelement_EEO.click();
			driver.findElement(By.xpath("//span[text()='Asian']")).click();
			System.out.println("Click action is performed on EEO option ");
		} catch (Exception user) {
			System.out.println("EEO Race and Ethnicity is not required");
		}

		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				System.out.println("Successfully dispaly message is verified for Personal detail update");

			}
		} catch (Exception user) {
			System.out.println("Successfully dispaly message is not verified for Personal detail update");
		}

		// IMPORTANT DETAIL TO UPDATE

		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_BloodGroup,
				Constant.sheet_EditEmployeeCases);
		// String bloodGroup=sh.getRow(i).getCell(6).getStringCellValue();
		driver.findElement(By.xpath("//label[text()='Blood Group']//parent::div//child::input")).click();
		driver.findElement(By.xpath("//span[text()='" + bloodGroup + "']")).click();
		System.out.println("Change the Blood Group");
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[2]")).click();
		System.out.println("Click action is performed on save button for the important detail update");
		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				System.out.println("Successfully dispaly message is verified for important detail update");

			}
		} catch (Exception user) {
			System.out.println("Successfully dispaly message is not verified for important detail update");
		}

		// PREFERENCES DETAIL TO UPDATE

		String hubby1 = ExcelConfig.getCellData(iTestData, Constant.col_HubbyFirst, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_First = driver.findElement(By.xpath("//label[@for='" + hubby1 + "']"));
		if (Hubby_First.isSelected()) {
			System.out.println("Hubby is already selected as" + hubby1);
		} else {
			Hubby_First.click();
			System.out.println("Hubby is selected as " + hubby1);
		}
		String hubby2 = ExcelConfig.getCellData(iTestData, Constant.col_HubbySecond, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_Second = driver.findElement(By.xpath("//label[@for='" + hubby2 + "']"));
		if (Hubby_Second.isSelected()) {
			System.out.println("Hubby is already selected as " + hubby2);
		} else {
			Hubby_Second.click();
			System.out.println("Hubby is selected as " + hubby2);
		}
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[3]")).submit();
		System.out.println("Enter the save for the Preferences Detail update");
		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				System.out.println("Successfully dispaly message is verified for Preferences detail update");

			}
		} catch (Exception user) {
			System.out.println("Successfully dispaly message is not verified for Preferences detail update");
		}

		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();		
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.pathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.pathExcel);
		System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
				+" in the "+Constant.sheet_TestCases);
		System.out.println("The file are closed");
	}

}