package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import frameworkScripts.Constant;
import utilities.ExcelConfig;

public class PIM_Page extends BaseClass {

	public PIM_Page(WebDriver driver) {
		super(driver);

	}

	static By txtbx_firstName = By.xpath("//input[@id='firstName']");
	static By txtbx_middleName = By.xpath("//input[@id='middleName']");
	static By txtbx_lastName = By.xpath("//input[@id='lastName']");
	static By dd_location = By.xpath("(//input[@class='select-dropdown'])[2]");
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
	
	public static String employeeName, employeeID, firstName, middleName, lastName;
	
	

	public static void setEmployeePersonalData(int iTestData) throws Exception{
		// Enter name
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(txtbx_firstName));

		firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The firstName read from excel is : " + firstName, true);

		driver.findElement(txtbx_firstName).sendKeys(firstName);
		Reporter.log("The value " + firstName + " is entered as firstName in the text-box", true);

		middleName = ExcelConfig.getCellData(iTestData, Constant.col_middleName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The middleName read from excel is : " + middleName, true);

		driver.findElement(txtbx_middleName).sendKeys(middleName);
		Reporter.log("The value " + middleName + " is entered as middleName in the text-box", true);

		lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The lastName read from excel is : " + lastName, true);

		driver.findElement(txtbx_lastName).sendKeys(lastName);
		Reporter.log("The value " + lastName + " is entered as lastName in the text-box", true);

		// enter the location
		driver.findElement(dd_location).click();
		String location = ExcelConfig.getCellData(iTestData, Constant.col_location, Constant.sheet_AddEmployeeCases);
		Reporter.log("The location read from excel is : " + location, true);

		driver.findElement(By.xpath("//span[contains(text(),'" + location + "')]")).click();
		Reporter.log("The value " + location + " is selected as location in the dropdown", true);
		Thread.sleep(2000);
		// click next button
		WebElement element_next = driver.findElement(btn_next_PersonalDetails);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element_next);
		Reporter.log("Click action is performed on Next button", true);
	}
	
	public static void setEmployeeImportantData(int iTestData) throws Exception{
		
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_bloodGroup));
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[3]")).click();
		String bloodgroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup, Constant.sheet_AddEmployeeCases);
		Reporter.log("The bloodgroup read from excel is : " + bloodgroup,true);
		
		driver.findElement(By.xpath("//span[text()='" + bloodgroup + "']")).click();
		Reporter.log("The value "+ bloodgroup+" is selected as bloodgroup in the dropdown",true);

		// select hobbies
		String hobby = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		Reporter.log("The hobby read from excel is : " + hobby,true);
		
		driver.findElement(txtbx_hobbies).sendKeys(hobby);
		Reporter.log("The value "+ hobby+" is entered as hobby in the text-box",true);

		// next button
		driver.findElement(btn_next_ImportantDetails).click();
		Reporter.log("Clicked action is performed on Next button",true);
	}
	
	
	public static void setEmployeeData(int iTestData) throws Exception{
		driver.findElement(dd_region).click();

		String region = ExcelConfig.getCellData(iTestData, Constant.col_Region, Constant.sheet_AddEmployeeCases);
		Reporter.log("The Region read from excel is : " + region,true);
		
		driver.findElement(By.xpath("//span[text()='" + region + "']")).click();
		Reporter.log("The value "+ region+" is selected as Region in the dropdown",true);

		driver.findElement(dd_FTE).click();
		String FTE = ExcelConfig.getCellData(iTestData, Constant.col_FTE, Constant.sheet_AddEmployeeCases);
		Reporter.log("The FTE read from excel is : " + FTE,true);		
		
		driver.findElement(By.xpath("//span[text()='" + FTE + "']")).click();
		Reporter.log("The value "+ FTE+" is selected as FTE in the dropdown",true);

		driver.findElement(dd_temp_Department).click();
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.col_Temp_Department, Constant.sheet_AddEmployeeCases);

		Reporter.log("The Temp_Department read from excel is : " + temp_Department);
		driver.findElement(By.xpath("//span[text()='" + temp_Department + "']")).click();
		Reporter.log("The value "+ temp_Department+" is selected as Temp_Department in the dropdown",true);

		driver.findElement(btn_save).click();
		Reporter.log("Click action is performed Save button",true);
	}
	
	public static void verifyEmployeeData() throws Exception{
		
		driver.findElement(Home_Page.link_EmployeeList).click();
		employeeName = firstName.concat(" " + middleName).concat(" " + lastName);
		employeeID = driver.findElement(By.xpath("//td[text()='"+employeeName+" ']/../td[2]")).getText();
		
	}
	
}
