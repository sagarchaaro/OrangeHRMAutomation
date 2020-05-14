package pages;

import org.openqa.selenium.By;

public class Expense_Page {
	By dd_currency = By.cssSelector("#estimateAddForEmployee div div div div input");
	By btn_next = By.xpath("//a[text()='Next']");
	By btn_add = By.xpath("(//button[text()='Add'])[1]");
	By txtbx_mainDestination = By.xpath("//input[@name='TravelInformation[main_destination]']");
	By txtbx_fromDate = By.xpath("//input[@name='TravelInformation[travel_period_from]']");
	By txtbx_toDate = By.xpath("//input[@name='TravelInformation[travel_period_to]']");
	By txtbx_destinationAddress = By.name("TravelInformation[destination_address]");
	By btn_save = By.xpath("(//a[text()='Save'])[3]");
	By btn_add2 = By.xpath("(//button[text()='Add'])[2]");
	By dd_expense = By.xpath("//form[@id='selectionForm']/div/div/div/input");
	By dd_currencyName = By.xpath("(//input[@class='select-dropdown'])[2]");
	By txtbx_amount = By.xpath("//input[@name='estimation[amount]']");
	By dd_paidBy = By.xpath("//select[@id='estimation_paid_by_id']/../input");
	By btn_save2 = By.xpath("(//a[text()='Save'])[2]");
	By btn_submit = By.xpath("//a[text()='Submit']");
	By btn_OK = By.xpath("//a[text()='OK']");
	By btn_search = By.xpath("//i[text()='ohrm_filter']");
	By txtbx_search = By.xpath("//form[@id='estimateSearchForEmployee']/div/div/div/div/input");
	By btn_approve = By.xpath("//a[text()='Approve']");
	By btn_Ok = By.xpath("//a[text()='OK']");
	
	
}
