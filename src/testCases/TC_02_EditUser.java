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

public class TC_02_EditUser {
	
	public static String timestamp, screenshotPath, browser, existingLocationName, newLocationName, employeeName_New, randomInt;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites(){
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Data read from Properties file.");		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.testCaseID);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		System.out.println("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(prop, browser);
	}
	@Test
	public void editUser() throws Exception {
		System.out.println("The Execution started for TC_02_EditUser");
		// WEBDRIVER AND TIMESTAMP METHOD


		WebDriverWait wait = new WebDriverWait(driver, 30);
		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_EditUserCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_EditUserCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as Password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			System.out.println("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.pathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.pathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			throw new Exception();
		}

		Utils.screenShot(screenshotPath + "\\Edit_User.jpg", driver);

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Admin']")).click();
		System.out.println("Click action is performed on Admin in the Menu bar");

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Organization']")).click();
		System.out.println("Click action is performed on Organization in the Menu bar");

		driver.findElement(By.xpath("//span[@class='left-menu-title'][text()='Locations']")).click();
		System.out.println("Click action is performed on Locations in the Menu bar");

		List<WebElement> element_location = driver.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include"));
		System.out.println("All location are stored in the WebElement");
		String[] locationArray = Utils.dataIntoArray(element_location, 16);
		System.out.println("All location are stored in the Array");
		String existingLocationName = Utils.selectWithRandomIndex(16, locationArray);
		System.out.println("The location is selected by random no is :" + existingLocationName);
		driver.findElement(By.xpath("//span[text()='" + existingLocationName + "']//ancestor ::tr/td[8]/i")).click();
		System.out.println(" Click action is performed on Edit button");
		// Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@class='select-dropdown'])[2]")));

		WebElement element_country = driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]"));
		String country = element_country.getAttribute("value");
		System.out.println(" The Country name read from webpage is:" + country);
		Thread.sleep(2000);		

		driver.findElement(By.xpath("//input[@id='name']")).clear();
		System.out.println("The Country name is cleared in the webpage");
		String randomAlphabet = RandomStringUtils.randomAlphabetic(6);
		String newLocationName = country.concat(randomAlphabet);
		System.out.println("The new location  generated is:"+newLocationName);
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys(newLocationName);
		System.out.println("The value "+ newLocationName+" is entered as location in the text-box");
		driver.findElement(By.xpath("//input[@id='phone']")).clear();
		System.out.println("The phone number is cleared in the webpage");

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10);
		System.out.println("The Random number generated is:"+randomInt);
		driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("+91 " + randomInt);
		System.out.println("The value "+ randomInt+" is entered as phone no in the text-box");

		try{
			WebElement webelement_EeoEnable = driver.findElement(By.xpath("//input[@id='eeo_applicable']"));
			if (webelement_EeoEnable.isEnabled()) {
				System.out.println("The EEO is already Enabled ");

			} else {

				webelement_EeoEnable.click();
				System.out.println("Click action is performed for EEO is Enabled option");
			}
		}catch(Exception e){
			System.out.println("EEO Enabled option is not available");
		}


		driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
		System.out.println("Click action is performed on Save button");

		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[@class='tooltipped circle']")).click();
		System.out.println("Click action is performed on Filter button");
		driver.findElement(By.xpath("//input[@id='location_name_filter']")).sendKeys(newLocationName);
		System.out.println("The locaion name entered is:" + newLocationName);
		driver.findElement(By.xpath("//a[@class='modal-action modal-close waves-effect btn primary-btn']")).click();
		System.out.println("Click action is performed on Search button");
		Thread.sleep(5000);

		String validate_location = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span")).getText();
		System.out.println(" The location name for Validation is : " + validate_location);
		CommonMethod.validation(validate_location, newLocationName, iTestCase);

		String validate_PhoneNo = driver
				.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[5]/ng-include/span")).getText();
		System.out.println(" The phone no for Validation is : " + validate_PhoneNo);
		CommonMethod.validation(validate_PhoneNo, "+91 " + randomInt, iTestCase);

		/*
		 * CALLING THE mETHOD TO ADD THE EMPLOYEE from line no 157 to 177 can be
		 * commented and test TC_01_AddEmpoloyee can run with this data, we need
		 * to check
		 */

		String employeeName_New = HRM_TestCase_Method.AddEmployee(newLocationName, driver);

		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Employee List']")));
		WebElement element_emp=driver.findElement(By.xpath("//span[text()='Employee List']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element_emp);
		System.out.println("Click action is performed on Employee List in the Menu bar");
		//driver.findElement(By.xpath("//span[text()='Employee List']")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.id("employee_name_quick_filter_employee_list_value")).sendKeys(employeeName_New);
		System.out.println("The value "+ employeeName_New+" is entered as search test in the text-box");
		Thread.sleep(3000);
		driver.findElement(By.id("quick_search_icon")).click();
		System.out.println("Click action is performed on Search button");

		Thread.sleep(3000);
		String validate_locationName = driver
				.findElement(By.xpath("(//table[@id='employeeListTable']/tbody/tr/td[8])[1]")).getText();

		System.out.println(" The location name for Validation is : " + validate_locationName);

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
			System.out.println(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet");
			found=true;
		}
	}
	
	if(!found){
		System.out.println(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location);
	}
	
	
	int rowCount_TC_03=ExcelConfig.getRowUsed(Constant.sheet_AddVacancyCases);
	boolean found_03=false;
	for(int row=1;row<rowCount_TC_03;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases);
		if(existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(newLocationName, row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases, CommonMethod.pathExcel);
			System.out.println(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet");
			found_03=true;
		}
	}
	
	if(!found_03){
		System.out.println(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location);
	}
	// WRITE THE DATA IN THE EXCEL FILE.
	ExcelConfig.setCellData(existingLocationName, iTestData, Constant.col_ExistingLocationName, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	ExcelConfig.setCellData(newLocationName, iTestData, Constant.col_NewLocationName, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	System.out.println("The value "+newLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases);

	ExcelConfig.setCellData(employeeName_New, iTestData, Constant.col_OwnerName, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	ExcelConfig.setCellData("+91 " + randomInt, iTestData, Constant.col_NewPhoneNo, Constant.sheet_EditUserCases,CommonMethod.pathExcel);
	System.out.println("The value "+randomInt+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName	+" in the "+Constant.sheet_EditUserCases);

	
	ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);

	
	System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status	+" in the "+Constant.sheet_TestCases);
	ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
	System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases);

	System.out.println("The file are closed");
	}

}
