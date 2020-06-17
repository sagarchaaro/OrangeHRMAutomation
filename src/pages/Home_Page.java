package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import utilities.Log;
import utilities.Utils;

public class Home_Page extends BaseClass {

	public Home_Page(WebDriver driver) {
		super(driver);

	}

	static By txt_Dashboard = By.xpath("//li[contains(text(),'Dashboard')]");
	static By link_admin = By.xpath("//span[@class='left-menu-title'][text()='Admin']");
	static By link_organization = By.xpath("//span[@class='left-menu-title'][text()='Organization']");
	static By link_location = By.xpath("//span[@class='left-menu-title'][text()='Locations']");
	static By link_recruitment = By.xpath("//span[text()='Recruitment']");
	static By link_userManagement = By.xpath("//span[text()='User Management']");
	static By link_users = By.xpath("//span[text()='Users']");
	static By link_expense = By.xpath("//span[text()='Expense']");
	static By link_travelRequests = By.xpath("//span[text()='Travel Requests']");
	static By link_myTravelRequests = By.xpath("//span[text()='My Travel Requests']");
	static By link_employeeTravelRequests = By.xpath("//a[@id='menu_expense_viewEmployeeEstimateRequest']/span[2]");
	static By link_EmployeeList = By.xpath("//span[@class='left-menu-title'][text()='Employee List']");
	static By link_Leave = By.xpath("//span[text()='Leave']");
	static By link_LeaveApply = By.xpath("//span[text()='Apply']");
	static By link_UserMange = By.xpath("//span[text()='User Management']");
	static By link_Users = By.xpath("//span[text()='Users']");
	static By link_Decipline = By.xpath("//span[text()='Discipline']");
	static By link_DeciplineCases = By.xpath("//span[text()='Discipline']/..//following::a/span[text()='Disciplinary Cases']");

	public static void verifyDashboard(String screenshotPath) throws Exception {
		Thread.sleep(3000);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(txt_Dashboard), "Dashboard"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin_.jpg", driver);
			Log.info("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			Log.info("Dashboard is not available, Test case is failed");
			CommonMethod.reason = "Dashboard is not available";
			Assert.assertTrue(false, "Dashboard is not available, Test case is failed");
		}
		Reporter.log("verifyDashboard method execution is completed",true);
	}
	
	public static void navigateMenu(String mainHeader) {
		driver.findElement(By.xpath("//span[text()='" + mainHeader + "']")).click();
		Log.info("Click action is performed on " + mainHeader + " in the Menu bar");
	}

	public static void navigateMenu(String mainHeader, String subHeader) {
		// PIM Click
		driver.findElement(By.xpath("//span[text()='" + mainHeader + "']")).click();
		Log.info("Click action is performed on " + mainHeader + " in the Menu bar");

		// Add employee click
		driver.findElement(By.xpath("//span[text()='" + subHeader + "']")).click();
		Log.info("Click action is performed on " + subHeader + " in the Menu bar");
	}

	public static void navigateMenu(String mainHeader, String subHeader, String branch) {

		driver.findElement(By.xpath("//span[text()='" + mainHeader + "']")).click();
		Log.info("Click action is performed on " + mainHeader + " in the Menu bar");

		driver.findElement(By.xpath("//span[text()='" + subHeader + "']")).click();
		Log.info("Click action is performed on " + subHeader + " in the Menu bar");

		driver.findElement(By.xpath("//span[text()='" + branch + "']")).click();
		Log.info("Click action is performed on " + branch + " in the Menu bar");
	}
	public static void navigateDesciplinary() {
		driver.findElement(link_Decipline).click();
		Log.info("Click action is performed on Discipline in the Menu bar");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Disciplinary Cases']")));
		driver.findElement(link_DeciplineCases).click();
		// span[contains(text(),'Disciplinary Cases')]
		Log.info("Click action is performed on Disciplinary Cases in the Menu bar");		// PIM Click
		
	}
}
