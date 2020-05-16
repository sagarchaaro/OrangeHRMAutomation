package pages;

import org.openqa.selenium.By;

public class Discipline_Page {
	By btn_Add = By.xpath("//i[text()='add']");
	By txtbx_EmployeeName = By.xpath("//input[@id='addCase_employeeName_empName']");
	By txtbx_CaseNo = By.xpath("//input[@id='addCase_caseName']");
	By txtbx_CaseDesc = By.xpath("//textarea[@id='addCase_description']");
	By btn_Save = By.xpath("//a[text()='Save']");
	By btn_Action = By.xpath("//a[text()='Take Disciplinary Action']");
	By btn_Select = By.xpath("//a[text()='Select']");
	By click_CreateDate = By.xpath("//input[@id='createDate']");
	By txtbx_OwnerName = By.xpath("//input[@id='defaultAction_owner_empName']");
	By dd_Status = By.xpath("//label[text()='Status']//parent::div//child::input");
	By click_Fillter = By.xpath("//i[text()='ohrm_filter']");
	By txtbx_EmpnameSrch = By.xpath("//input[@id='DisciplinaryCaseSearch_empName_empName']");
	By btn_Search = By.xpath("//a[@id='searchBtn']");
	By txtbx_NameVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[2]");
	By txtbx_CaseVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[3]");
	By txtbx_DescVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[4]");
	By txtbx_CreatedBy = By.xpath("//table[@id='resultTable']/tbody/tr/td[5]");
	By txtbx_CreatedOn = By.xpath("//table[@id='resultTable']/tbody/tr/td[6]");
	By click_ViewLink = By.xpath("//table[@id='resultTable']/tbody/tr/td[7]/a");
	By txtbx_Status = By.xpath("//table[@id='resultTable']/tbody/tr/td[5]");
	By click_ViewAction = By.xpath("//table[@id='resultTable']/tbody/tr/td/a");
	By btn_Deciplinary = By.xpath("//a[text()='View Disciplinary Case']");
	By btn_CloseCase = By.xpath("//a[text()='Close Case']");
}
