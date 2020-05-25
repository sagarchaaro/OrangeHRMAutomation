package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import utilities.RandomGenerator;
import utilities.Utils;

public class Admin_Page extends BaseClass{
	
	public Admin_Page(WebDriver driver) {
		super(driver);
	}

	static By dd_country = By.xpath("(//input[@class='select-dropdown'])[2]");
	static By txtbx_location = By.xpath("//input[@id='name']");
	static By txtbx_phone = By.xpath("//input[@id='phone']");
	static By chckbx_EEO = By.xpath("//input[@id='eeo_applicable']");
	static By btn_save = By.xpath("//a[contains(text(),'Save')]");
	
	By chckbx_changePassword = By.xpath("//label[@for='changepassword']");
	By txtbx_changePassword = By.xpath("(//div[@id='modal1']/form//child::input)[8]");
	By txtbx_confirmPassword = By.xpath("(//div[@id='modal1']/form//child::input)[9]");
	By btn_save2 = By.id("systemUserSaveBtn");
	
	//Added for TC_06 test case
	By txtbx_EmployeePage = By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span");
	By btn_Add = By.xpath("//i[text()='add']");
	By txtbx_EmployeeName = By.xpath("//input[@id='selectedEmployee_value']");
	By txtbx_UserName = By.xpath("//input[@id='user_name']");
	By dd_AdminRole = By.xpath("//div[@id='adminrole_inputfileddiv']/div/input");
	By dd_Status = By.xpath("//div[@id='status_inputfileddiv']/div/input");
	By dd_StatusEnable = By.xpath("//div[@id='status_inputfileddiv']/div/ul/li/span[text()='Enabled']");
	By txtbx_Password = By.xpath("//input[@id='password']");
	By txtbx_PasswordConfirm = By.xpath("//input[@id='confirmpassword']");
	By btn_Save = By.xpath("//a[@id='systemUserSaveBtn']");	
	By btn_SaveRegion = By.xpath("//div[@class='modal-footer']/a[text()='Save']");
	By click_Search = By.xpath("//i[text()='ohrm_filter']");
	By txtbx_UserFilter = By.xpath("//input[@id='systemuser_uname_filter']");
	By btn_Search = By.xpath("//a[text()='Search']");
	By msg_Record = By.xpath("//div[text()='No Records Found']");
	
	public void demo(){
		driver.findElement(btn_save).click();
	}
	
	public static String phoneNumber, newLocationName;
	
	public static void getLocationData() throws Exception {
		List<WebElement> element_location = driver.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include"));
		Reporter.log("All location are stored in the WebElement",true);
		String[] locationArray = Utils.dataIntoArray(element_location, 16);
		Reporter.log("All location are stored in the Array",true);
		String existingLocationName = Utils.selectWithRandomIndex(16, locationArray);
		Reporter.log("The location is selected by random no is :" + existingLocationName,true);
		driver.findElement(By.xpath("//span[text()='" + existingLocationName + "']//ancestor ::tr/td[8]/i")).click();
		Reporter.log(" Click action is performed on Edit button",true);
		// Thread.sleep(5000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(dd_country));

		WebElement element_country = driver.findElement(dd_country);
		String country = element_country.getAttribute("value");
		Reporter.log(" The Country name read from webpage is:" + country,true);
		Thread.sleep(2000);		

		driver.findElement(txtbx_location).clear();
		Reporter.log("The Country name is cleared in the webpage",true);
		String randomAlphabet = RandomGenerator.randomAlphabetic(6);
		newLocationName = country.concat(randomAlphabet);
		Reporter.log("The new location  generated is:"+newLocationName,true);
		driver.findElement(txtbx_location).sendKeys(newLocationName);
		Reporter.log("The value "+ newLocationName+" is entered as location in the text-box",true);
		driver.findElement(txtbx_phone).clear();
		Reporter.log("The phone number is cleared in the webpage",true);

		phoneNumber = RandomGenerator.randomNumeric(10);
		Reporter.log("The Random number generated is:"+phoneNumber,true);
		driver.findElement(txtbx_phone).sendKeys("+91 " + phoneNumber);
		Reporter.log("The value "+ phoneNumber+" is entered as phone no in the text-box",true);

		try{
			WebElement webelement_EeoEnable = driver.findElement(chckbx_EEO);
			if (webelement_EeoEnable.isEnabled()) {
				Reporter.log("The EEO is already Enabled ",true);

			} else {

				webelement_EeoEnable.click();
				Reporter.log("Click action is performed for EEO is Enabled option",true);
			}
		}catch(Exception e){
			Reporter.log("EEO Enabled option is not available",true);
		}


		driver.findElement(btn_save).click();
		Reporter.log("Click action is performed on Save button",true);
	}
	
	public static void validateLocation() throws Exception{
		driver.findElement(By.xpath("//a[@class='tooltipped circle']")).click();
		Reporter.log("Click action is performed on Filter button",true);
		driver.findElement(By.xpath("//input[@id='location_name_filter']")).sendKeys(newLocationName);
		Reporter.log("The locaion name entered is:" + newLocationName,true);
		driver.findElement(By.xpath("//a[@class='modal-action modal-close waves-effect btn primary-btn']")).click();
		Reporter.log("Click action is performed on Search button",true);
		Thread.sleep(5000);

		String validate_location = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span")).getText();
		Reporter.log(" The location name for Validation is : " + validate_location,true);
		CommonMethod.verifyData(newLocationName, validate_location);

		String validate_PhoneNo = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[5]/ng-include/span")).getText();
		Reporter.log(" The phone no for Validation is : " + validate_PhoneNo,true);
		CommonMethod.verifyData("+91 " + phoneNumber, validate_PhoneNo);
	}
	
	public static void verifyLocationInEmployeeList(String employeeName) throws Exception {
		Thread.sleep(5000);
		driver.findElement(By.id("employee_name_quick_filter_employee_list_value")).sendKeys(employeeName);
		Reporter.log("The value "+ employeeName+" is entered as search test in the text-box",true);
		Thread.sleep(3000);
		driver.findElement(By.id("quick_search_icon")).click();
		Reporter.log("Click action is performed on Search button",true);

		Thread.sleep(3000);
		String validate_locationName = driver.findElement(By.xpath("(//table[@id='employeeListTable']/tbody/tr/td[8])[1]")).getText();

		Reporter.log(" The location name for Validation is : " + validate_locationName,true);

		CommonMethod.verifyData(newLocationName, validate_locationName);
	}
}
