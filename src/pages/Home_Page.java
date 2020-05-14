package pages;

import org.openqa.selenium.By;

public class Home_Page {
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
	
}
