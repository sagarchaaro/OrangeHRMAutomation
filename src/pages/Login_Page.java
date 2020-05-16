package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login_Page extends BaseClass{
	
	public Login_Page(WebDriver driver) {
		super(driver);
		
	}
	By txtbx_userID = By.id("txtUsername");
	By txtbx_password = By.id("txtPassword");
	By btn_login = By.xpath("//input[@id='btnLogin']");
	
}
