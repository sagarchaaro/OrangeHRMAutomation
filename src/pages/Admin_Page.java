package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Admin_Page extends BaseClass{
	
	public Admin_Page(WebDriver driver) {
		super(driver);
	}

	By dd_country = By.xpath("(//input[@class='select-dropdown'])[2]");
	By txtbx_location = By.xpath("//input[@id='name']");
	By txtbx_phone = By.xpath("//input[@id='phone']");
	By chckbx_EEO = By.xpath("//input[@id='eeo_applicable']");
	By btn_save = By.xpath("//a[contains(text(),'Save')]");
	
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
}
