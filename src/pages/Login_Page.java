package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;

public class Login_Page extends BaseClass{
	
	public Login_Page(WebDriver driver) {
		super(driver);
		
	}
	static By txtbx_userID = By.id("txtUsername");
	static By txtbx_password = By.id("txtPassword");
	static By btn_login = By.xpath("//input[@id='btnLogin']");
	
	public static void loginPageVerify(){
		String title = driver.getTitle();
		CommonMethod.verifyData(title, "OrangeHRM");
	}
	
	public static void login(int iTestData) throws Exception{
				
		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddEmployeeCases);
		Reporter.log("The password read from excel is : " + password,true);

		driver.findElement(txtbx_userID).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(txtbx_password).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		driver.findElement(btn_login).submit();
		Reporter.log("Click action is performed on Login button",true);
		
	}
	
	
}
