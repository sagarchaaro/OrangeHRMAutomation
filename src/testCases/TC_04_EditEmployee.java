package testCases;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class TC_04_EditEmployee {
	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser,reason;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		
		// LOAD AND READ THE PROPERTIES FILE
		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");
		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
	
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
		

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,
						Constant.sheet_EditEmployeeCases);
		Reporter.log("The row no for of test Data is : " + iTestData,true);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + iBrowser,true);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(prop, iBrowser);	
		new BaseClass(driver);
				
	}


	@Test
	public  void editEmployee() throws InterruptedException, Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);		
		Reporter.log("The Execution started for TC_04_EditEmployee",true);					
		// LOGIN TO THE DASHBOARD
		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_EditEmployeeCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_EditEmployeeCases);
		Reporter.log("The password read from excel is : " + password,true);
		
		// user name
		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		// Password
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		// Login Click
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);

		// CLICK ON THE LIST OF EMPLOYEE AND EDIT THE 1ST RECORD

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		Reporter.log("Click action is performed on PLM in the Menu bar",true);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[@class='left-menu-title'][text()='Employee List']")));
		// click on list of employee
		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Employee List']")).click();
		Reporter.log("Click action is performed on Employee list in the Menu bar",true);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='nowrap cursor-pointer']")));
		// Search employee
		driver.findElement(By.xpath("//td[@class='nowrap cursor-pointer']")).click();
		Reporter.log("Click action is performed on first Link of Employee list",true);
		String employeeName=driver.findElement(By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[3]")).getText();
		Reporter.log("Edit employee activity is performed  for the employee :"+employeeName,true);
		ExcelConfig.setCellData(employeeName, iTestData, Constant.col_EditEmployeeName, Constant.sheet_EditEmployeeCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+employeeName+" is written as EditEmployeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EditEmployeeName
				+" in the "+Constant.sheet_EditEmployeeCases,true);
		
		String employeeID=driver.findElement(By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[2]")).getText();
		Reporter.log("Edit employee activity is performed  for the employee ID :"+employeeID,true);
		ExcelConfig.setCellData(employeeID, iTestData, Constant.col_EditEmployeeID, Constant.sheet_EditEmployeeCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+employeeID+" is written as EditEmployeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EditEmployeeID
				+" in the "+Constant.sheet_EditEmployeeCases,true);
		
		// PERSONAL DETAIL TO UPDATE

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_EditEmployeeCases);
		Reporter.log("The lastName read from excel is : " + lastName,true);
		driver.findElement(By.id("lastName")).clear();
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		Reporter.log("The value "+ lastName+" is entered as lastName in the text-box",true);
		driver.findElement(By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]")).click();
		Reporter.log("Click action is performed on calender for DOB",true);
		String dateOfBirthFomat1 = ExcelConfig.getCellData(iTestData, Constant.col_DateOfBirth,
				Constant.sheet_EditEmployeeCases);
		CommonMethod.date_HRM(dateOfBirthFomat1, driver, 1);

		driver.findElement(By.xpath("(//div[@class='select-wrapper initialized']/input)[2]")).click();
		String nationalty = ExcelConfig.getCellData(iTestData, Constant.col_Nationality,
				Constant.sheet_EditEmployeeCases);
		driver.findElement(By.xpath("//span[text()='" + nationalty + "']")).click();
		Reporter.log("The value "+ nationalty+" is selected as nationalty from dropdown",true);
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[1]")).click();
		Reporter.log("Click action is performed on save button for the Personal detail update",true);
		try {
			WebElement webelement_EEO = driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']/..//child::input"));
			webelement_EEO.click();
			driver.findElement(By.xpath("//span[text()='Asian']")).click();
			Reporter.log("Click action is performed on EEO option ",true);
		} catch (Exception user) {
			Reporter.log("EEO Race and Ethnicity is not required",true);
		}

		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				Reporter.log("Successfully dispaly message is verified for Personal detail update",true);

			}
		} catch (Exception user) {
			Reporter.log("Successfully dispaly message is not verified for Personal detail update",true);
		}

		// IMPORTANT DETAIL TO UPDATE

		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_BloodGroup,
				Constant.sheet_EditEmployeeCases);
		// String bloodGroup=sh.getRow(i).getCell(6).getStringCellValue();
		driver.findElement(By.xpath("//label[text()='Blood Group']//parent::div//child::input")).click();
		driver.findElement(By.xpath("//span[text()='" + bloodGroup + "']")).click();
		Reporter.log("The value "+ bloodGroup+" is selected as bloodGroup from dropdown",true);
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[2]")).click();
		Reporter.log("Click action is performed on save button for the important detail update",true);
		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				Reporter.log("Successfully dispaly message is verified for important detail update",true);

			}
		} catch (Exception user) {
			Reporter.log("Successfully dispaly message is not verified for important detail update",true);
		}

		// PREFERENCES DETAIL TO UPDATE

		String hubby1 = ExcelConfig.getCellData(iTestData, Constant.col_HubbyFirst, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_First = driver.findElement(By.xpath("//label[@for='" + hubby1 + "']"));
		if (Hubby_First.isSelected()) {
			Reporter.log("Hubby is already selected as" + hubby1,true);
		} else {
			Hubby_First.click();
			Reporter.log("Hubby is selected as " + hubby1,true);
		}
		String hubby2 = ExcelConfig.getCellData(iTestData, Constant.col_HubbySecond, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_Second = driver.findElement(By.xpath("//label[@for='" + hubby2 + "']"));
		if (Hubby_Second.isSelected()) {
			Reporter.log("Hubby is already selected as " + hubby2,true);
		} else {
			Hubby_Second.click();
			Reporter.log("Hubby is selected as " + hubby2,true);
		}
		driver.findElement(By.xpath("(//button[@class=' btn waves-effect waves-green '])[3]")).submit();
		Reporter.log("Enter the save for the Preferences Detail update",true);
		try {
			if (driver.findElement(By.xpath("//div[@class='toast toast-success']")).isDisplayed()) {
				Reporter.log("Successfully dispaly message is verified for Preferences detail update",true);

			}
		} catch (Exception user) {
			Reporter.log("Successfully dispaly message is not verified for Preferences detail update",true);
		}

		CommonMethod.logoutJaveExecuter(driver);
	}
		
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{

		driver.quit();		
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status +" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments	+" in the "+Constant.sheet_TestCases);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Reporter.log("Testcase is failed with the reason as :"+reason,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
	
		Reporter.log("TestCase execution is completed",true);
	}

}