package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home_Page extends BaseClass{
	
	public Home_Page(WebDriver driver) {
		super(driver);
		
	}
	
	By link_PIM =By.xpath("//span[text()='PIM']");
	By link_AddEmployee = By.xpath("//span[text()='Add Employee']");	
	By link_admin = By.xpath("//span[@class='left-menu-title'][text()='Admin']");
	By link_organization = By.xpath("//span[@class='left-menu-title'][text()='Organization']");
	By link_location = By.xpath("//span[@class='left-menu-title'][text()='Locations']");
	By link_recruitment = By.xpath("//span[text()='Recruitment']");
	By link_userManagement = By.xpath("//span[text()='User Management']");
	By link_users = By.xpath("//span[text()='Users']");
	By link_expense = By.xpath("//span[text()='Expense']");
	By link_travelRequests = By.xpath("//span[text()='Travel Requests']");
	By link_myTravelRequests = By.xpath("//span[text()='My Travel Requests']");
	By link_employeeTravelRequests = By.xpath("//a[@id='menu_expense_viewEmployeeEstimateRequest']/span[2]");
	//Updated for TC_04,Tc_05 and TC_06 and TC_08
	By link_EmployeeList = By.xpath("//span[@class='left-menu-title'][text()='Employee List']");
	By link_Leave = By.xpath("//span[text()='Leave']");
	By link_LeaveApply = By.xpath("//span[text()='Apply']");
	By link_UserMange = By.xpath("//span[text()='User Management']");
	By link_Users = By.xpath("//span[text()='Users']");
	By link_Decipline = By.xpath("//span[text()='Discipline']");
	By link_DeciplineCases = By.xpath("//span[text()='Discipline']/..//following::a/span[text()='Disciplinary Cases']");
	
	
}
