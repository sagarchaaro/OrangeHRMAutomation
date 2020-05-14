package pages;

import org.openqa.selenium.By;

public class PIM_Page {
	By txtbx_firstName=By.xpath("//input[@id='firstName']");
	By txtbx_middleName=By.xpath("//input[@id='middleName']");
	By txtbx_lastName=By.xpath("//input[@id='lastName']");
	By dd_location=By.xpath("(//input[@class='select-dropdown'])[2]");
	By btn_next=By.xpath("//a[@id='systemUserSaveBtn']");
	By dd_bloodGroup=By.xpath("(//input[@class='select-dropdown'])[3]");
	By txtbx_hobbies=By.xpath("//input[@id='5']");
	By btn_next2=By.xpath("//button[@class='btn waves-effect waves-light right']");
	By dd_region=By.xpath("(//input[@class='select-dropdown'])[6]");
	By dd_FTE=By.xpath("(//input[@class='select-dropdown'])[7]");
	By dd_temp_Department=By.xpath("(//input[@class='select-dropdown'])[8]");
	By btn_save=By.xpath("//button[@class='btn waves-effect waves-light right']");
}
