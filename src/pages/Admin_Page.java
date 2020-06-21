package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import frameworkScripts.CommonMethod;
import org.testng.Assert;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.RandomGenerator;
import utilities.Utils;

public class Admin_Page extends BaseClass {

	public Admin_Page(WebDriver driver) {
		super(driver);
	}

	static By dd_country = By.xpath("(//input[@class='select-dropdown'])[2]");
	static By txtbx_location = By.xpath("//input[@id='name']");
	static By txtbx_phone = By.xpath("//input[@id='phone']");
	static By chckbx_EEO = By.xpath("//input[@id='eeo_applicable']");
	static By btn_save = By.xpath("//a[contains(text(),'Save')]");

	static By chckbx_changePassword = By.xpath("//label[@for='changepassword']");
	static By txtbx_changePassword = By.xpath("(//div[@id='modal1']/form//child::input)[8]");
	static By txtbx_confirmPassword = By.xpath("(//div[@id='modal1']/form//child::input)[9]");
	static By btn_save2 = By.id("systemUserSaveBtn");
	static By link_location = By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include");
	static By link_editLocation = By.xpath("//span[text()='{0}']//ancestor ::tr/td[8]/i");

	// Added for TC_06 test case
	static By txtbx_UserPage = By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span");
	static By btn_Add = By.xpath("//i[text()='add']");
	static By txtbx_EmployeeName = By.xpath("//input[@id='selectedEmployee_value']");
	static By txtbx_UserName = By.xpath("//input[@id='user_name']");
	static By dd_AdminRole = By.xpath("//div[@id='adminrole_inputfileddiv']/div/input");
	static By dd_select = By.xpath("//span[text()='{0}']");
	static By dd_Status = By.xpath("//div[@id='status_inputfileddiv']/div/input");
	static By dd_StatusEnable = By.xpath("//div[@id='status_inputfileddiv']/div/ul/li/span[text()='Enabled']");
	static By txtbx_Password = By.xpath("//input[@id='password']");
	static By txtbx_PasswordConfirm = By.xpath("//input[@id='confirmpassword']");
	static By btn_Save = By.xpath("//a[@id='systemUserSaveBtn']");
	static By btn_SaveRegion = By.xpath("//div[@class='modal-footer']/a[text()='Save']");
	static By click_Search = By.xpath("//i[text()='ohrm_filter']");
	static By txtbx_UserFilter = By.xpath("//input[@id='systemuser_uname_filter']");
	static By btn_Search = By.xpath("//a[text()='Search']");
	static By msg_Record = By.xpath("//div[text()='No Records Found']");
	static By link_userName = By.xpath("//span[@id='account-name']");
	static By link_employeeName = By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='{0}']/../../../td[8]");

	public void demo() {
		driver.findElement(btn_save).click();
	}

	public static String phoneNumber, newLocationName, password, existingLocationName;
	public static String empFirstName, userName, newPassword, employeeName;
	public static WebDriverWait wait = new WebDriverWait(driver, 30);

	public static void getLocationData() throws Exception {
		
		List<WebElement> element_location = driver.findElements(link_location);
		Log.info("All location are stored in the WebElement" );
		Thread.sleep(5000);
		String[] locationArray = Utils.dataIntoArray(element_location, 17);
		Log.info("All location are stored in the Array" );
		existingLocationName = Utils.selectWithRandomIndex(17, locationArray);
		Log.info("The location is selected by random no is :" + existingLocationName );
		driver.findElement(CommonMethod.formatLocator(link_editLocation,existingLocationName)).click();
		Log.info(" Click action is performed on Edit button" );
		// Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_country));

		WebElement element_country = driver.findElement(dd_country);
		String country = element_country.getAttribute("value");
		Log.info(" The Country name read from webpage is:" + country );
		Thread.sleep(2000);

		driver.findElement(txtbx_location).clear();
		Log.info("The Country name is cleared in the webpage" );
		String randomAlphabet = RandomGenerator.randomAlphabetic(6);
		newLocationName = country.concat(randomAlphabet);
		Log.info("The new location  generated is:" + newLocationName );
		driver.findElement(txtbx_location).sendKeys(newLocationName);
		Log.info("The value " + newLocationName + " is entered as location in the text-box" );
		driver.findElement(txtbx_phone).clear();
		Log.info("The phone number is cleared in the webpage" );

		phoneNumber = RandomGenerator.randomNumeric(10);
		Log.info("The Random number generated is:" + phoneNumber );
		driver.findElement(txtbx_phone).sendKeys("+91 " + phoneNumber);
		Log.info("The value " + phoneNumber + " is entered as phone no in the text-box" );

		try {
			WebElement webelement_EeoEnable = driver.findElement(chckbx_EEO);
			if (webelement_EeoEnable.isEnabled()) {
				Log.info("The EEO is already Enabled " );

			} else {

				webelement_EeoEnable.click();
				Log.info("Click action is performed for EEO is Enabled option" );
			}
		} catch (Exception e) {
			Log.info("EEO Enabled option is not available" );
		}

		driver.findElement(btn_save).click();
		Log.info("Click action is performed on Save button" );
	}

	public static void validateLocation() throws Exception {
		driver.findElement(By.xpath("//a[@class='tooltipped circle']")).click();
		Log.info("Click action is performed on Filter button" );
		driver.findElement(By.xpath("//input[@id='location_name_filter']")).sendKeys(newLocationName);
		Log.info("The locaion name entered is:" + newLocationName );
		driver.findElement(By.xpath("//a[@class='modal-action modal-close waves-effect btn primary-btn']")).click();
		Log.info("Click action is performed on Search button" );
		Thread.sleep(5000);

		String validate_location = driver
				.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span")).getText();
		Log.info(" The location name for Validation is : " + validate_location );
		CommonMethod.verifyData(newLocationName, validate_location);

		String validate_PhoneNo = driver
				.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[5]/ng-include/span")).getText();
		Log.info(" The phone no for Validation is : " + validate_PhoneNo );
		CommonMethod.verifyData("+91 " + phoneNumber, validate_PhoneNo);
		
	}

	public static void verifyLocationInEmployeeList(String employeeName) throws Exception {
		Thread.sleep(5000);
		driver.findElement(By.id("employee_name_quick_filter_employee_list_value")).sendKeys(employeeName);
		Log.info("The value " + employeeName + " is entered as search test in the text-box" );
		Thread.sleep(3000);
		driver.findElement(By.id("quick_search_icon")).click();
		Log.info("Click action is performed on Search button" );

		Thread.sleep(3000);
		String validate_locationName = driver
				.findElement(By.xpath("(//table[@id='employeeListTable']/tbody/tr/td[8])[1]")).getText();

		Log.info(" The location name for Validation is : " + validate_locationName );

		CommonMethod.verifyData(newLocationName, validate_locationName);
	}

	public static void storeUserInArray(int iTestData) throws Exception {

		int totalElementNo = driver.findElements(txtbx_UserPage).size();
		Log.info("The total no of employee in the page is: " + totalElementNo );
		List<WebElement> webelement_empName = driver.findElements(txtbx_UserPage);
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Log.info("The employeeName selected by random no is:" + employeeName );
		String empNameSearch[] = employeeName.split(" ");
		empFirstName = empNameSearch[0];
		driver.findElement(btn_Add).click();
		Log.info("Click action is performed on add button" );

	}

	public static void SetNewUser(int iTestData) throws Exception {

		WebElement webelement1 = driver.findElement(txtbx_EmployeeName);
		webelement1.sendKeys(empFirstName);
		Thread.sleep(3000);
		webelement1.sendKeys(Keys.DOWN);
		webelement1.sendKeys(Keys.ENTER);
		Log.info("The Employee name entered for search is: " + empFirstName);
		String randomAlphabet = RandomGenerator.randomAlphabet(4);
		userName = empFirstName.concat(randomAlphabet);
		driver.findElement(txtbx_UserName).sendKeys(userName);
		Log.info("The value " + userName + " is entered as userName in the text-box" );

		String adminRole = ExcelConfig.getCellData(iTestData, Constant.col_AdminRole, Constant.sheet_AddUserCases);
		Log.info("The adminRole read from excel is : " + adminRole );

		driver.findElement(dd_AdminRole).click();
		driver.findElement(CommonMethod.formatLocator(dd_select, adminRole)).click();
		Log.info("The value " + adminRole + " is entered as adminRole in the drop down" );
		driver.findElement(dd_Status).click();
		driver.findElement(dd_StatusEnable).click();
		Log.info("Admin Role is Enabled " );
		newPassword = ExcelConfig.getCellData(iTestData, Constant.col_UserPassword, Constant.sheet_AddUserCases);
		Log.info("The password read from excel is : " + newPassword );
		driver.findElement(txtbx_Password).sendKeys(newPassword);
		Log.info("The value " + newPassword + " is entered as password in the text-box" );
		driver.findElement(txtbx_PasswordConfirm).sendKeys(newPassword);
		Log.info("The value " + newPassword + " is entered as Confirm password in the text-box" );
		driver.findElement(btn_Save).click();
		Log.info("Click action is performed on Save button" );
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(btn_SaveRegion));
		driver.findElement(btn_SaveRegion).click();
		Log.info("Click action is performed on Save button is for region" );

	}

	public static void verifyUser(int iTestData, String screenshotPath) throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(click_Search));
		driver.findElement(click_Search).click();
		Log.info("Click action is performed on Filter" );
		driver.findElement(txtbx_UserFilter).sendKeys(userName);
		Log.info("The value " + userName + " is entered as userName in the Search text-box" );
		driver.findElement(btn_Search).click();
		Log.info("Click action is performed on serched button" );

		try {
			WebElement userfoundmsg = driver.findElement(msg_Record);
			if (userfoundmsg.isDisplayed()) {
				Log.info("The User is not found message displayed" );
				CommonMethod.reason = "The User is not found message displayed";
				Assert.assertTrue(false, "The User is not found message displayed");
			}
		} catch (Exception user) {
			Utils.screenShot(screenshotPath + "\\OrangeHRMUser.jpg", driver);
			Log.info("User detail page is found for the user" + userName );
		}

	}

	public static void vrifyUserlogin(String screenshotPath) throws Exception {
		String username1_validation = driver.findElement(link_userName).getText();
		Utils.screenShot(screenshotPath + "\\" + userName + "_Login.jpg", driver);
		CommonMethod.verifyData(employeeName, username1_validation);
	}
	
	public static String changePassword(int iTestData) throws Exception{
		Thread.sleep(5000);
		List<WebElement> employeeName = driver.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		Log.info("All EmployeeName are stored in the WebElement" );
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		Log.info("All EmployeeName are stored in the Array" );
		String employee_Name = Utils.selectWithRandomIndex(50, empName);
		Log.info("The EmployeeName is selected by random no is :" + employee_Name );
		driver.findElement(CommonMethod.formatLocator(link_employeeName, employee_Name)).click();
		Log.info("Click action is performed on Edit Link" );

		driver.findElement(By.xpath("//label[@for='changepassword']")).click();
		Log.info("Click action is performed  on change password checkbox" );
		
		password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_TravelRequestCases);

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(password);
		Log.info("The value "+ password+" is entered as password in the text-box" );

		driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(password);
		Log.info("The value "+ password+" is entered as Confirm password in the text-box" );

		driver.findElement(By.id("systemUserSaveBtn")).click();
		Log.info("Click action is performed on Save button" );
		Thread.sleep(5000);
		return employee_Name;
	}

}
