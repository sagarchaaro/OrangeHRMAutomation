package pages;

import org.openqa.selenium.By;

public class Admin_Page {
	By dd_country = By.xpath("(//input[@class='select-dropdown'])[2]");
	By txtbx_location = By.xpath("//input[@id='name']");
	By txtbx_phone = By.xpath("//input[@id='phone']");
	By chckbx_EEO = By.xpath("//input[@id='eeo_applicable']");
	By btn_save = By.xpath("//a[contains(text(),'Save')]");
	
	By chckbx_changePassword = By.xpath("//label[@for='changepassword']");
	By txtbx_changePassword = By.xpath("(//div[@id='modal1']/form//child::input)[8]");
	By txtbx_confirmPassword = By.xpath("(//div[@id='modal1']/form//child::input)[9]");
	By btn_save2 = By.id("systemUserSaveBtn");
	
}
