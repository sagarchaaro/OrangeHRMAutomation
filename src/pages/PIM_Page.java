package pages;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import testCases.TC_04_EditEmployee;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.RandomGenerator;
import utilities.Utils;

public class PIM_Page extends BaseClass {

	public PIM_Page(WebDriver driver) {
		super(driver);

	}

	static By txtbx_firstName = By.xpath("//input[@id='firstName']");
	static By txtbx_middleName = By.xpath("//input[@id='middleName']");
	static By txtbx_lastName = By.xpath("//input[@id='lastName']");
	static By dd_location = By.xpath("(//input[@class='select-dropdown'])[2]");
	static By dd_location_options = By.xpath("//div[@class='select-wrapper initialized']/ul/li[@class='']");
	static By btn_next_PersonalDetails = By.xpath("//a[@id='systemUserSaveBtn']");
	static By dd_bloodGroup = By.xpath("(//input[@class='select-dropdown'])[3]");
	static By txtbx_hobbies = By.xpath("//input[@id='5']");
	static By btn_next_ImportantDetails = By.xpath("//button[@class='btn waves-effect waves-light right']");
	static By dd_region = By.xpath("(//input[@class='select-dropdown'])[6]");
	static By dd_FTE = By.xpath("(//input[@class='select-dropdown'])[7]");
	static By dd_temp_Department = By.xpath("(//input[@class='select-dropdown'])[8]");
	static By btn_save = By.xpath("//button[@class='btn waves-effect waves-light right']");
	static By link_FistEmployee = By.xpath("//td[@class='nowrap cursor-pointer']");
	static By txtbx_EmployeeName = By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[3]");
	static By txtbx_EmployeeId = By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[2]");
	static By click_DateDOB = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]");
	static By dd_nationality = By.xpath("(//div[@class='select-wrapper initialized']/input)[2]");
	static By btn_savePersonalDtl = By.xpath("(//button[@class=' btn waves-effect waves-green '])[1]");
	static By click_EEO = By.xpath("//label[text()='EEO Race and Ethnicity']/..//child::input");
	static By btn_saveImportantDtl = By.xpath("(//button[@class=' btn waves-effect waves-green '])[2]");
	static By btn_savePreferenceDtl = By.xpath("(//button[@class=' btn waves-effect waves-green '])[3]");
	static By msg_sucess = By.xpath("//div[@class='toast toast-success']");
	static By txtbx_EmployeeList = By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]");
	static By dd_menucontains=By.xpath("//span[contains(text(),'{0}')]");
	static By dd_menu=By.xpath("//span[text()='{0}']");
	static By link_VerifySearch = By.id("employee_name_quick_filter_employee_list_value");
	static By element_EmployeeDetails = By.xpath("//table[@id='employeeListTable']/tbody/tr/td[2]"); 
	static By link_hobby = By.xpath("//label[@for='{0}']");
	static By dd_bloodGroupEdit = By.xpath("//label[text()='Blood Group']//parent::div//child::input");
	
	public static String employeeName, employeeID, firstName, middleName, lastName, location;
	public static WebDriverWait wait = new WebDriverWait(driver, 30);	
	

	public static void setEmployeePersonalData(int iTestData) throws Exception{
		// Enter name
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(txtbx_firstName));

		firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		Log.info("The firstName read from excel is : " + firstName );

		driver.findElement(txtbx_firstName).sendKeys(firstName);
		Log.info("The value " + firstName + " is entered as firstName in the text-box" );

		middleName = ExcelConfig.getCellData(iTestData, Constant.col_middleName, Constant.sheet_AddEmployeeCases);
		Log.info("The middleName read from excel is : " + middleName );

		driver.findElement(txtbx_middleName).sendKeys(middleName);
		Log.info("The value " + middleName + " is entered as middleName in the text-box" );

		lastName = RandomGenerator.randomAlphabetic(4);
		Log.info("The lastName is : " + lastName );

		driver.findElement(txtbx_lastName).sendKeys(lastName);
		Log.info("The value " + lastName + " is entered as lastName in the text-box" );

		// enter the location
		driver.findElement(dd_location).click();
		Thread.sleep(5000);
		int totalElementNo=driver.findElements(dd_location_options).size();
		Log.info("The total no of location in the dropdown is: " + totalElementNo );
		List<WebElement> webelement_location = driver.findElements(dd_location_options);
		Log.info("All locations are stored in the WebElement" );
		String[] locationsArray = Utils.dataIntoArray(webelement_location, totalElementNo);
		Log.info("All locations are stored in the Array" );
		location = Utils.selectWithRandomIndex(totalElementNo, locationsArray).trim();
		Log.info("The location selected by random no is:" + location );
	//	String location = ExcelConfig.getCellData(iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases);
	//	Log.info("The location read from excel is : " + location );

		driver.findElement(CommonMethod.formatLocator(dd_menucontains, location)).click();
		Log.info("The value " + location + " is selected as location in the dropdown" );
		Thread.sleep(2000);
		// click next button
		WebElement element_next = driver.findElement(btn_next_PersonalDetails);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_next);
		Log.info("Click action is performed on Next button" );
	}
	
	public static void setEmployeeImportantData(int iTestData) throws Exception{
		
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_bloodGroup));
		driver.findElement(dd_bloodGroup).click();
		Log.info("Click action is performed on Blood Group drop-down");
		String bloodgroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup, Constant.sheet_AddEmployeeCases);
		Log.info("The bloodgroup read from excel is : " + bloodgroup );
		
		driver.findElement(CommonMethod.formatLocator(dd_menu, bloodgroup)).click();
		Log.info("The value "+ bloodgroup+" is selected as bloodgroup in the dropdown" );

		// select hobbies
		String hobby = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		Log.info("The hobby read from excel is : " + hobby );
		
		driver.findElement(txtbx_hobbies).sendKeys(hobby);
		Log.info("The value "+ hobby+" is entered as hobby in the text-box" );

		// next button
		driver.findElement(btn_next_ImportantDetails).click();
		Log.info("Clicked action is performed on Next button" );
	}
	
	
	public static void setEmployeeData(int iTestData) throws Exception{
		driver.findElement(dd_region).click();

		String region = ExcelConfig.getCellData(iTestData, Constant.col_Region, Constant.sheet_AddEmployeeCases);
		Log.info("The Region read from excel is : " + region );
		
		driver.findElement(CommonMethod.formatLocator(dd_menu, region)).click();
		Log.info("The value "+ region+" is selected as Region in the dropdown" );

		driver.findElement(dd_FTE).click();
		String FTE = ExcelConfig.getCellData(iTestData, Constant.col_FTE, Constant.sheet_AddEmployeeCases);
		Log.info("The FTE read from excel is : " + FTE );		
		
		driver.findElement(CommonMethod.formatLocator(dd_menu, FTE)).click();
		Log.info("The value "+ FTE+" is selected as FTE in the dropdown" );

		driver.findElement(dd_temp_Department).click();
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.col_Temp_Department, Constant.sheet_AddEmployeeCases);

		Log.info("The Temp_Department read from excel is : " + temp_Department);
		driver.findElement(CommonMethod.formatLocator(dd_menu, temp_Department)).click();
		Log.info("The value "+ temp_Department+" is selected as Temp_Department in the dropdown" );

		driver.findElement(btn_save).click();
		Log.info("Click action is performed Save button" );
		Thread.sleep(10000);
	}
		
	public static void verifyEmployeeData() throws Exception{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(Home_Page.link_EmployeeList));
		driver.findElement(Home_Page.link_EmployeeList).click();
		employeeName = firstName.concat(" " + middleName).concat(" " + lastName);
		driver.findElement(link_VerifySearch).sendKeys(employeeName);
		employeeID = driver.findElement(element_EmployeeDetails).getText();
		
		
	}
	
	public static void employeeToUpdate(){
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(link_FistEmployee));
		// Search employee
		driver.findElement(link_FistEmployee).click();
		Log.info("Click action is performed on first Link of Employee list" );
		TC_04_EditEmployee.employeeName=driver.findElement(txtbx_EmployeeName).getText();
		Log.info("Edit employee activity is performed  for the employee :"+employeeName );
		TC_04_EditEmployee.employeeID=driver.findElement(txtbx_EmployeeId).getText();
		Log.info("Edit employee activity is performed  for the employee ID :"+employeeID );		
		
	}
	
	public static void editPersonalDtl(int iTestData) throws Exception{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastName")));
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_EditEmployeeCases);
		Log.info("The lastName read from excel is : " + lastName );
		driver.findElement(txtbx_lastName).clear();
		driver.findElement(txtbx_lastName).sendKeys(lastName);
		Log.info("The value "+ lastName+" is entered as lastName in the text-box" );
		driver.findElement(click_DateDOB).click();
		Log.info("Click action is performed on calender for DOB" );
		String dateOfBirthFomat1 = ExcelConfig.getCellData(iTestData, Constant.col_DateOfBirth,Constant.sheet_EditEmployeeCases);
		CommonMethod.date_HRM(dateOfBirthFomat1, driver, 1);
		driver.findElement(dd_nationality).click();
		String nationalty = ExcelConfig.getCellData(iTestData, Constant.col_Nationality,Constant.sheet_EditEmployeeCases);
		driver.findElement(CommonMethod.formatLocator(dd_menu, nationalty)).click();
		Log.info("The value "+ nationalty+" is selected as nationalty from dropdown" );
		driver.findElement(btn_savePersonalDtl).click();
		Log.info("Click action is performed on save button for the Personal detail update" );	
		try {
			WebElement webelement_EEO = driver.findElement(click_EEO);
			webelement_EEO.click();
			driver.findElement(CommonMethod.formatLocator(dd_menu, "Asian")).click();
			Log.info("Click action is performed on EEO option " );
		} catch (Exception user) {
			Log.info("EEO Race and Ethnicity is not required" );
		}

		try {
			if (driver.findElement(msg_sucess).isDisplayed()) {
				Log.info("Successfully dispaly message is verified for Personal detail update" );

			}
		} catch (Exception user) {
			Log.info("Successfully dispaly message is not verified for Personal detail update" );
		}
		
	}
	public static void editImportantDtl(int iTestData) throws Exception{
		
		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_BloodGroup,Constant.sheet_EditEmployeeCases);
		// String bloodGroup=sh.getRow(i).getCell(6).getStringCellValue();
		driver.findElement(dd_bloodGroupEdit).click();
		driver.findElement(CommonMethod.formatLocator(dd_menu, bloodGroup)).click();
		Log.info("The value "+ bloodGroup+" is selected as bloodGroup from dropdown" );
		driver.findElement(btn_saveImportantDtl).click();
		Log.info("Click action is performed on save button for the important detail update" );
		try {
			if (driver.findElement(msg_sucess).isDisplayed()) {
				Log.info("Successfully dispaly message is verified for important detail update" );

			}
		} catch (Exception user) {
			Log.info("Successfully dispaly message is not verified for important detail update" );
		}
		
	}
	
	public static void editPreferencesDtl(int iTestData) throws Exception{
		
		String hubby1 = ExcelConfig.getCellData(iTestData, Constant.col_HubbyFirst, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_First = driver.findElement(CommonMethod.formatLocator(link_hobby, hubby1));
		if (Hubby_First.isSelected()) {
			Log.info("Hubby is already selected as" + hubby1 );
		} else {
			Hubby_First.click();
			Log.info("Hubby is selected as " + hubby1 );
		}
		String hubby2 = ExcelConfig.getCellData(iTestData, Constant.col_HubbySecond, Constant.sheet_EditEmployeeCases);
		WebElement Hubby_Second = driver.findElement(CommonMethod.formatLocator(link_hobby, hubby2));
		if (Hubby_Second.isSelected()) {
			Log.info("Hubby is already selected as " + hubby2 );
		} else {
			Hubby_Second.click();
			Log.info("Hubby is selected as " + hubby2 );
		}
		driver.findElement(btn_savePreferenceDtl).submit();
		Log.info("Enter the save for the Preferences Detail update" );
		try {
			if (driver.findElement(msg_sucess).isDisplayed()) {
				Log.info("Successfully dispaly message is verified for Preferences detail update" );

			}
		} catch (Exception user) {
			Log.info("Successfully dispaly message is not verified for Preferences detail update" );
		}
		
	}
	
}
