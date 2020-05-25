package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Utils;

public class Discipline_Page extends BaseClass{
	
	public Discipline_Page(WebDriver driver) {
		super(driver);
	}
	
	static By list_employee = By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]");
	static By btn_Add = By.xpath("//i[text()='add']");
	static By txtbx_EmployeeName = By.xpath("//input[@id='addCase_employeeName_empName']");
	static By txtbx_CaseNo = By.xpath("//input[@id='addCase_caseName']");
	static By txtbx_CaseDesc = By.xpath("//textarea[@id='addCase_description']");
	static By btn_Save = By.xpath("//a[text()='Save']");
	static By btn_Action = By.xpath("//a[text()='Take Disciplinary Action']");
	static By btn_Select = By.xpath("//a[text()='Select']");
	static By click_CreateDate = By.xpath("//input[@id='createDate']");
	static By txtbx_OwnerName = By.xpath("//input[@id='defaultAction_owner_empName']");
	static By dd_Status = By.xpath("//label[text()='Status']//parent::div//child::input");
	static By click_Fillter = By.xpath("//i[text()='ohrm_filter']");
	static By txtbx_EmpnameSrch = By.xpath("//input[@id='DisciplinaryCaseSearch_empName_empName']");
	static By btn_Search = By.xpath("//a[@id='searchBtn']");
	static By txtbx_NameVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[2]");
	static By txtbx_CaseVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[3]");
	static By txtbx_DescVal = By.xpath("//table[@id='resultTable']/tbody/tr/td[4]");
	static By txtbx_CreatedBy = By.xpath("//table[@id='resultTable']/tbody/tr/td[5]");
	static By txtbx_CreatedOn = By.xpath("//table[@id='resultTable']/tbody/tr/td[6]");
	static By click_ViewLink = By.xpath("//table[@id='resultTable']/tbody/tr/td[7]/a");
	static By txtbx_Status = By.xpath("//table[@id='resultTable']/tbody/tr/td[5]");
	static By click_ViewAction = By.xpath("//table[@id='resultTable']/tbody/tr/td/a");
	static By btn_Deciplinary = By.xpath("//a[text()='View Disciplinary Case']");
	static By btn_CloseCase = By.xpath("//a[text()='Close Case']");


    public static String ownerName,employeeName,caseNo,description,status,action,dueDate,createdBy,createdOn;
    public static WebDriverWait wait = new WebDriverWait(driver, 30);	

    public static void storeUserInArray(int iTestData,String screenshotPath) throws Exception{		

		int totalElementNo = driver.findElements(list_employee).size();
		Reporter.log("The total no of employee in the page is: " + totalElementNo,true);
		List<WebElement> webelement_empName = driver.findElements(list_employee);
		Reporter.log("All employeeName are stored in the WebElement",true);
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		Reporter.log("All employeeName are stored in the Array",true);
		employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The employeeName selected by random no is:" + employeeName,true);
		ownerName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The ownerName selected by random no is:" + ownerName,true);
		Utils.screenShot(screenshotPath + "\\EmployeeList.jpg", driver);
		Reporter.log("Screen shot is  taken for Employee List ",true);
	
}
    
    public static void addDesciplinaryRecord() throws Exception{		

		driver.switchTo().frame(0);
		Reporter.log("Switched the Frame",true);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(btn_Add ));
		driver.findElement(btn_Add ).click();
		Reporter.log("Click action is performed on Add button",true);
	
}
    
    public static void setDesciplinaryRecord(int iTestData) throws Exception{		

		WebElement webelement_empname = driver.findElement(txtbx_EmployeeName);
		webelement_empname.sendKeys(employeeName);
		webelement_empname.sendKeys(Keys.DOWN);
		webelement_empname.sendKeys(Keys.ENTER);
		Reporter.log("The value "+employeeName+" is entered as employeeName in the text-box",true);
		caseNo = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_DeciplinaryCases);
		Reporter.log("The caseNo read from excel is:" + caseNo,true);
		driver.findElement(txtbx_CaseNo).sendKeys(caseNo);
		Reporter.log("The value "+ caseNo+" is entered as caseName in the text-box",true);
		description = ExcelConfig.getCellData(iTestData, Constant.col_Desciption,Constant.sheet_DeciplinaryCases);
		Reporter.log("The description read from excel is:" + description,true);		
		driver.findElement(txtbx_CaseDesc).sendKeys(description);
		Reporter.log("The value "+description +" is entered as description in the text-box",true);
		driver.findElement(btn_Save).click();
		Reporter.log("Click action is performed on Save button",true);
		driver.findElement(btn_Action).click();
		Reporter.log("Click action is performed on Take Disciplinary Action button",true);
	
}
    
    public static void setDesciplinaryAction(int iTestData,String screenshotPath) throws Exception{		

		action = ExcelConfig.getCellData(iTestData, Constant.col_DisciplinaryAction,Constant.sheet_DeciplinaryCases);
		Reporter.log("The action read from excel is: " + action,true);
		String actionNo[] = action.split(" ");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@for='" + actionNo[0] + "']")));
		driver.findElement(By.xpath("//label[@for='" + actionNo[0] + "']")).click();
		Reporter.log("The value "+actionNo[0]+ " is selected as deciplinary action",true);
		driver.findElement(btn_Select ).click();
		Reporter.log("Click action is performed on Select button",true);
		dueDate = ExcelConfig.getCellData(iTestData, Constant.col_DueDate, Constant.sheet_DeciplinaryCases);
		Reporter.log("The dueDate read from excel is: " + dueDate,true);
		driver.findElement(click_CreateDate).click();
		Reporter.log("The value "+dueDate+ " is selected as due date from calender");
		CommonMethod.date_HRM(dueDate, driver, 1);
		WebElement webelement_owner = driver.findElement(txtbx_OwnerName );
		webelement_owner.sendKeys(ownerName);
		webelement_owner.sendKeys(Keys.DOWN);
		webelement_owner.sendKeys(Keys.ENTER);
		Reporter.log("The value "+ownerName+"is entered as ownerName in the text-box",true);
		status = ExcelConfig.getCellData(iTestData, Constant.col_ActionStatus, Constant.sheet_DeciplinaryCases);
		Reporter.log("The status read from excel is" + status,true);
		driver.findElement(dd_Status).click();
		driver.findElement(By.xpath("//span[text()='" + status + "']")).click();
		Reporter.log("The value "+status+" is selected as status in the dropdown",true);		
		Utils.screenShot(screenshotPath + "\\DisciplinaryCase.jpg", driver);
		Reporter.log("Screen shot is  taken for Desciplinary case",true);
		driver.findElement(btn_Save).click();
		Reporter.log("Click action is performed on Save button",true);
}
    
    public static void verifyDesciplinaryRec(int iTestData) throws Exception{	
    	

		driver.findElement(click_Fillter).click();
		WebElement webelement_filter=driver.findElement(txtbx_EmpnameSrch);
		webelement_filter.sendKeys(employeeName);
		webelement_filter.sendKeys(Keys.ARROW_DOWN);
		webelement_filter.sendKeys(Keys.ENTER);
		driver.findElement(btn_Search).click();
		Reporter.log("click action is performed for search button",true);
		Thread.sleep(3000);

		String validation_name = driver.findElement(txtbx_NameVal).getText();
		CommonMethod.verifyData(employeeName, validation_name);

		String validation_case = driver.findElement(txtbx_CaseVal).getText();
		CommonMethod.verifyData(caseNo, validation_case);

		String validation_description = driver.findElement(txtbx_DescVal)
				.getText();
		CommonMethod.verifyData(description, validation_description);

		createdBy = driver.findElement(txtbx_CreatedBy).getText();
		createdOn = driver.findElement(txtbx_CreatedOn).getText();

		driver.findElement(click_ViewLink).click();
		Reporter.log("Click action is performed on view Link",true);

		String validation_Status = driver.findElement(txtbx_Status).getText();
		CommonMethod.verifyData(status, validation_Status);    	
    }
    
    public static void setCloseStatus(int iTestData,String screenshotPath) throws Exception{	
		// TO VIEW THE ACTION STATUS AND COMPLETE IF ITS NOT COMPLETED

		driver.findElement(click_ViewAction).click();
		Reporter.log("Click action is performed on Item Link",true);
		driver.findElement(btn_Deciplinary).click();
		Reporter.log("Click action is performed on View Disciplinary case button",true);
		driver.findElement(btn_CloseCase).click();
		Reporter.log("Click action is performed on close case button",true);
	
		// VERIFY CLOSE STATUS FOR THE CASE
		
		String validation_StatusClose = driver.findElement(By.xpath("//td[text()='" + employeeName + "']/../td[8]"))
				.getText();
		CommonMethod.verifyData("Close", validation_StatusClose);

		Utils.screenShot(screenshotPath + "\\CaseStatus.jpg", driver);
		Reporter.log("Screen shot is  taken for Case Status",true);
    }
}