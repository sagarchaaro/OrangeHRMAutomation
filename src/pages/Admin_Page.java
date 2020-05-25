package pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
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
	
	static By chckbx_changePassword = By.xpath("//label[@for='changepassword']");
	static By txtbx_changePassword = By.xpath("(//div[@id='modal1']/form//child::input)[8]");
	static By txtbx_confirmPassword = By.xpath("(//div[@id='modal1']/form//child::input)[9]");
	static By btn_save2 = By.id("systemUserSaveBtn");
	
	//Added for TC_06 test case
	static By txtbx_UserPage = By.xpath("//tbody[@ng-if='!listData.staticBody']/tr/td[4]/ng-include/span");
	static By btn_Add = By.xpath("//i[text()='add']");
	static By txtbx_EmployeeName = By.xpath("//input[@id='selectedEmployee_value']");
	static By txtbx_UserName = By.xpath("//input[@id='user_name']");
	static By dd_AdminRole = By.xpath("//div[@id='adminrole_inputfileddiv']/div/input");
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
	
	public void demo(){
		driver.findElement(btn_save).click();
	}
	
	public static String empFirstName,userName,newPassword,employeeName;
	public static WebDriverWait wait = new WebDriverWait(driver, 30);	
	
	public static void storeUserInArray(int iTestData) throws Exception{		

		int totalElementNo = driver.findElements(txtbx_UserPage).size();
		Reporter.log("The total no of employee in the page is: " + totalElementNo,true);
		List<WebElement> webelement_empName = driver.findElements(txtbx_UserPage);
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The employeeName selected by random no is:" + employeeName,true);
		String empNameSearch[] = employeeName.split(" ");
		empFirstName = empNameSearch[0];
		driver.findElement(btn_Add).click();
		Reporter.log("Click action is performed on add button",true);
		
	}
	
	public static void SetNewUser(int iTestData) throws Exception{
		
		WebElement webelement1 = driver.findElement(txtbx_EmployeeName);
		webelement1.sendKeys(empFirstName);
		Thread.sleep(3000);
		webelement1.sendKeys(Keys.DOWN);
		webelement1.sendKeys(Keys.ENTER);
		Reporter.log("The Employee name entered for search is: " + empFirstName);				
		String randomAlphabet = RandomGenerator.randomAlphabet(4);
		userName = empFirstName.concat(randomAlphabet);
		driver.findElement(txtbx_UserName).sendKeys(userName);
		Reporter.log("The value "+ userName+" is entered as userName in the text-box",true);

		String adminRole = ExcelConfig.getCellData(iTestData, Constant.col_AdminRole, Constant.sheet_AddUserCases);
		Reporter.log("The adminRole read from excel is : " + adminRole,true);

		driver.findElement(dd_AdminRole).click();
		driver.findElement(By.xpath("//span[text()='" + adminRole + "']")).click();
		Reporter.log("The value "+ adminRole+" is entered as adminRole in the drop down",true);
		driver.findElement(dd_Status).click();
		driver.findElement(dd_StatusEnable).click();
		Reporter.log("Admin Role is Enabled ",true);
		newPassword = ExcelConfig.getCellData(iTestData, Constant.col_UserPassword, Constant.sheet_AddUserCases);
		Reporter.log("The password read from excel is : " + newPassword,true);
		driver.findElement(txtbx_Password ).sendKeys(newPassword);
		Reporter.log("The value "+ newPassword+" is entered as password in the text-box",true);
		driver.findElement(txtbx_PasswordConfirm).sendKeys(newPassword);
		Reporter.log("The value "+ newPassword+" is entered as Confirm password in the text-box",true);
		driver.findElement(btn_Save).click();
		Reporter.log("Click action is performed on Save button",true);
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(btn_SaveRegion ));
				driver.findElement(btn_SaveRegion ).click();
		Reporter.log("Click action is performed on Save button is for region",true);

	}
	
	public static void verifyUser(int iTestData,String screenshotPath) throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(click_Search ));
		driver.findElement(click_Search ).click();
		Reporter.log("Click action is performed on Filter",true);
		driver.findElement(txtbx_UserFilter ).sendKeys(userName);
		Reporter.log("The value "+ userName+" is entered as userName in the Search text-box",true);
		driver.findElement(btn_Search).click();
		Reporter.log("Click action is performed on serched button",true);

		try {
			WebElement userfoundmsg = driver.findElement(msg_Record );
			if (userfoundmsg.isDisplayed()) {
				Reporter.log("The User is not found message displayed",true);
				CommonMethod.reason="The User is not found message displayed";
				Assert.assertTrue(false, "The User is not found message displayed");
			}
		} catch (Exception user) {
			Utils.screenShot(screenshotPath + "\\OrangeHRMUser.jpg", driver);
			Reporter.log("User detail page is found for the user"+userName,true);
		}
		
	}
	
	public static void vrifyUserlogin(String screenshotPath) throws Exception{	
		String username1_validation = driver.findElement(By.xpath("//span[@id='account-name']")).getText();
		Utils.screenShot(screenshotPath + "\\" + userName + "_Login.jpg", driver);
	    CommonMethod.verifyData(employeeName, username1_validation);
}
}
