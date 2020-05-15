package pages;

import org.openqa.selenium.By;

public class PIM_Page {
	By txtbx_firstName=By.xpath("//input[@id='firstName']");
	By txtbx_middleName=By.xpath("//input[@id='middleName']");
	By txtbx_lastName=By.xpath("//input[@id='lastName']");
	By dd_location=By.xpath("(//input[@class='select-dropdown'])[2]");
	By btn_next=By.xpath("//a[@id='systemUserSaveBtn']");
	By dd_bloodGroup=By.xpath("(//input[@class='select-dropdown'])[3]");
	//By dd_bloodGroup = By.xpath("//label[text()='Blood Group']//parent::div//child::input");
	By txtbx_hobbies=By.xpath("//input[@id='5']");
	By btn_next2=By.xpath("//button[@class='btn waves-effect waves-light right']");
	By dd_region=By.xpath("(//input[@class='select-dropdown'])[6]");
	By dd_FTE=By.xpath("(//input[@class='select-dropdown'])[7]");
	By dd_temp_Department=By.xpath("(//input[@class='select-dropdown'])[8]");
	By btn_save=By.xpath("//button[@class='btn waves-effect waves-light right']");
	//For TC_04_EditEmployee
	By link_FistEmployee = By.xpath("//td[@class='nowrap cursor-pointer']");
	By txtbx_EmployeeName = By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[3]");
	By txtbx_EmployeeId = By.xpath("//table[@id='employeeListTable']/tbody/tr[1]/td[2]");
	By click_DateDOB = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]");
	By dd_nationality = By.xpath("(//div[@class='select-wrapper initialized']/input)[2]");
	By btn_savePersonalDtl=By.xpath("(//button[@class=' btn waves-effect waves-green '])[1]");
	By click_EEO = By.xpath("//label[text()='EEO Race and Ethnicity']/..//child::input");
	By btn_saveImportantDtl = By.xpath("(//button[@class=' btn waves-effect waves-green '])[2]");
	By btn_savePreferenceDtl = By.xpath("(//button[@class=' btn waves-effect waves-green '])[3]");	
	By msg_sucess = By.xpath("//div[@class='toast toast-success']");
	By txtbx_EmployeeList = By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]");
	
	
}
