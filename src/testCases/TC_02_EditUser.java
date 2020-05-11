package testCases;

import java.util.Properties;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HRM_TestCase_Method;

import java.util.List;
import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;
import utilities.RandomGenerator;

public class TC_02_EditUser {
	
	public static String timestamp, screenshotPath, browser, existingLocationName, newLocationName, employeeName, phoneNumber;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Data read from Properties file."+CommonMethod.projectpath,true);
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		Reporter.log("The Testcase id executing is :"+CommonMethod.testCaseID,true);
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{
				
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(prop, browser);
	}
	@Test
	public void editUser() throws Exception {
		Reporter.log("The Execution started for TC_02_EditUser",true);
		// WEBDRIVER AND TIMESTAMP METHOD


		WebDriverWait wait = new WebDriverWait(driver, 30);
		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_EditUserCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_EditUserCases);
		Reporter.log("The password read from excel is : " + password,true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ",true);

		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case is failed",true);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			throw new Exception();
		}

		Utils.screenShot(screenshotPath + "\\Edit_User.jpg", driver);

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Admin']")).click();
		Reporter.log("Click action is performed on Admin in the Menu bar",true);

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Organization']")).click();
		Reporter.log("Click action is performed on Organization in the Menu bar",true);

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Locations']")).click();
		Reporter.log("Click action is performed on Locations in the Menu bar",true);

		List<WebElement> element_location = driver.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include"));
		Reporter.log("All location are stored in the WebElement",true);
		String[] locationArray = Utils.dataIntoArray(element_location, 16);
		Reporter.log("All location are stored in the Array",true);
		String existingLocationName = Utils.selectWithRandomIndex(16, locationArray);
		Reporter.log("The location is selected by random no is :" + existingLocationName,true);
		driver.findElement(By.xpath("//span[text()='" + existingLocationName + "']//ancestor ::tr/td[8]/i")).click();
		Reporter.log(" Click action is performed on Edit button",true);
		// Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@class='select-dropdown'])[2]")));

		WebElement element_country = driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]"));
		String country = element_country.getAttribute("value");
		Reporter.log(" The Country name read from webpage is:" + country,true);
		Thread.sleep(2000);		

		driver.findElement(By.xpath("//input[@id='name']")).clear();
		Reporter.log("The Country name is cleared in the webpage",true);
		String randomAlphabet = RandomStringUtils.randomAlphabetic(6);
		String newLocationName = country.concat(randomAlphabet);
		Reporter.log("The new location  generated is:"+newLocationName,true);
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys(newLocationName);
		Reporter.log("The value "+ newLocationName+" is entered as location in the text-box",true);
		driver.findElement(By.xpath("//input[@id='phone']")).clear();
		Reporter.log("The phone number is cleared in the webpage",true);

		phoneNumber = RandomGenerator.randomNumeric(10);
		Reporter.log("The Random number generated is:"+phoneNumber,true);
		driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("+91 " + phoneNumber);
		Reporter.log("The value "+ phoneNumber+" is entered as phone no in the text-box",true);

		try{
			WebElement webelement_EeoEnable = driver.findElement(By.xpath("//input[@id='eeo_applicable']"));
			if (webelement_EeoEnable.isEnabled()) {
				Reporter.log("The EEO is already Enabled ",true);

			} else {

				webelement_EeoEnable.click();
				Reporter.log("Click action is performed for EEO is Enabled option",true);
			}
		}catch(Exception e){
			Reporter.log("EEO Enabled option is not available",true);
		}


		driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
		Reporter.log("Click action is performed on Save button",true);

		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[@class='tooltipped circle']")).click();
		Reporter.log("Click action is performed on Filter button",true);
		driver.findElement(By.xpath("//input[@id='location_name_filter']")).sendKeys(newLocationName);
		Reporter.log("The locaion name entered is:" + newLocationName,true);
		driver.findElement(By.xpath("//a[@class='modal-action modal-close waves-effect btn primary-btn']")).click();
		Reporter.log("Click action is performed on Search button",true);
		Thread.sleep(5000);

		String validate_location = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span")).getText();
		Reporter.log(" The location name for Validation is : " + validate_location,true);
		CommonMethod.validation(validate_location, newLocationName, iTestCase);

		String validate_PhoneNo = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[5]/ng-include/span")).getText();
		Reporter.log(" The phone no for Validation is : " + validate_PhoneNo,true);
		CommonMethod.validation(validate_PhoneNo, "+91 " + phoneNumber, iTestCase);

		/*
		 * CALLING THE mETHOD TO ADD THE EMPLOYEE from line no 157 to 177 can be
		 * commented and test TC_01_AddEmpoloyee can run with this data, we need
		 * to check
		 */

		employeeName = HRM_TestCase_Method.AddEmployee(newLocationName, driver);

		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Employee List']")));
		WebElement element_emp=driver.findElement(By.xpath("//span[text()='Employee List']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element_emp);
		Reporter.log("Click action is performed on Employee List in the Menu bar",true);
		//driver.findElement(By.xpath("//span[text()='Employee List']")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.id("employee_name_quick_filter_employee_list_value")).sendKeys(employeeName);
		Reporter.log("The value "+ employeeName+" is entered as search test in the text-box",true);
		Thread.sleep(3000);
		driver.findElement(By.id("quick_search_icon")).click();
		Reporter.log("Click action is performed on Search button",true);

		Thread.sleep(3000);
		String validate_locationName = driver.findElement(By.xpath("(//table[@id='employeeListTable']/tbody/tr/td[8])[1]")).getText();

		Reporter.log(" The location name for Validation is : " + validate_locationName,true);

		CommonMethod.validation(validate_locationName, newLocationName, iTestCase);

		CommonMethod.logoutJaveExecuter(driver);
		
		
			}

	@AfterMethod
	public void tearDown() throws Exception{
	driver.quit();
	int rowCount=ExcelConfig.getRowUsed(Constant.sheet_AddEmployeeCases);
	boolean found=false;
	for(int row=1;row<rowCount;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_location, Constant.sheet_AddEmployeeCases);
		if(existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(newLocationName, row, Constant.col_location, Constant.sheet_AddEmployeeCases, CommonMethod.pathExcel);
			Reporter.log(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet",true);
			found=true;
		}
	}
	
	if(!found){
		Reporter.log(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location,true);
	}
	
	
	int rowCount_TC_03=ExcelConfig.getRowUsed(Constant.sheet_AddVacancyCases);
	boolean found_03=false;
	for(int row=1;row<rowCount_TC_03;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases);
		if(existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(newLocationName, row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases, CommonMethod.pathExcel);
			Reporter.log(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet",true);
			found_03=true;
		}
	}
	
	if(!found_03){
		Reporter.log(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location,true);
	}
	// WRITE THE DATA IN THE EXCEL FILE.
	ExcelConfig.setCellData(existingLocationName, iTestData, Constant.col_ExistingLocationName, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	Reporter.log("The value "+existingLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);
	
	ExcelConfig.setCellData(newLocationName, iTestData, Constant.col_NewLocationName, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	Reporter.log("The value "+newLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);

	ExcelConfig.setCellData(employeeName, iTestData, Constant.col_NewEmployeee, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	Reporter.log("The value "+employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);
	
	ExcelConfig.setCellData(phoneNumber, iTestData, Constant.col_NewPhoneNo, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	Reporter.log("The value "+phoneNumber+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName	+" in the "+Constant.sheet_EditUserCases,true);

	
	ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);

	
	Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status	+" in the "+Constant.sheet_TestCases,true);
	ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
	Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases,true);

	Reporter.log("The file are closed",true);
	}

}
