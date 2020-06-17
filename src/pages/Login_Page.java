package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Log;


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
	
	public static String password;
	
	public static void login(int iTestData, String sheetName) throws Exception{
		
		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName,sheetName);
		Log.info("The userName read from excel is : " + userName);
		password = ExcelConfig.getCellData(iTestData, Constant.col_Password,sheetName);
		Log.info("The password read from excel is : " + password);

		driver.findElement(txtbx_userID).sendKeys(userName);
		Log.info("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(txtbx_password).sendKeys(password);
		Log.info("The value "+password+" is entered as Password in the text-box");
		driver.findElement(btn_login).submit();
		Log.info("Click action is performed on Login button");
		
		Log.info("login method execution is completed");
		Reporter.log("login method execution is completed",true);
		
	}
	
	public static void login(String userName, String password){	
		driver.findElement(txtbx_userID).sendKeys(userName);
		Log.info("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(txtbx_password).sendKeys(password);
		Log.info("The value "+password+" is entered as Password in the text-box");
		driver.findElement(btn_login).submit();
		Log.info("Click action is performed on Login button");
		Reporter.log("login method execution is completed",true);
	}
	
	public static void loginWithNewUser(String employee_Name) {
		driver.findElement(By.id("txtUsername")).sendKeys(employee_Name);
		Log.info("The value "+ employee_Name+" is entered as employee_Name in the text-box");

		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Log.info("The value "+ password+" is entered as password in the text-box");

		driver.findElement(By.xpath("//input[@id='btnLogin']")).click();
		Log.info("Click action is performed on Login button for the employee login");
		Reporter.log("login method execution is completed",true);
		
	}
}
