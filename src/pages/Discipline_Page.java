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
import utilities.Log;
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
	static By dd_selectActionNo = By.xpath("//label[@for='{0}']");
	static By dd_select = By.xpath("//span[text()='{0}']");
	static By link_employee = By.xpath("//td[text()='{0}']/../td[8]");


    public static String ownerName,employeeName,caseNo,description,status,action,dueDate,createdBy,createdOn;
  //  public static WebDriverWait wait = new WebDriverWait(driver, 30);	

    public static void storeUserInArray(int iTestData,String screenshotPath) throws Exception{		

		int totalElementNo = driver.findElements(list_employee).size();
		Log.info("The total no of employee in the page is: " + totalElementNo);
		List<WebElement> webelement_empName = driver.findElements(list_employee);
		Log.info("All employeeName are stored in the WebElement");
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		Log.info("All employeeName are stored in the Array");
		employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Log.info("The employeeName selected by random no is:" + employeeName);
		ownerName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Log.info("The ownerName selected by random no is:" + ownerName);
		Utils.screenShot(screenshotPath + "\\EmployeeList.jpg", driver);
		Log.info("Screen shot is  taken for Employee List ");
		Reporter.log("storeUserInArray method execution is completed",true);
	
}
    
    public static void addDesciplinaryRecord() throws Exception{		

		driver.switchTo().frame(0);
		Log.info("Switched the Frame");
		Thread.sleep(3000);
	//	wait.until(ExpectedConditions.presenceOfElementLocated(btn_Add ));
		driver.findElement(btn_Add ).click();
		Log.info("Click action is performed on Add button");
		Reporter.log("addDesciplinaryRecord method execution is completed",true);
	
}
    
    public static void setDesciplinaryRecord(int iTestData) throws Exception{		

		WebElement webelement_empname = driver.findElement(txtbx_EmployeeName);
		webelement_empname.sendKeys(employeeName);
		webelement_empname.sendKeys(Keys.DOWN);
		webelement_empname.sendKeys(Keys.ENTER);
		Log.info("The value "+employeeName+" is entered as employeeName in the text-box");
		caseNo = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_DeciplinaryCases);
		Log.info("The caseNo read from excel is:" + caseNo);
		driver.findElement(txtbx_CaseNo).sendKeys(caseNo);
		Log.info("The value "+ caseNo+" is entered as caseName in the text-box");
		description = ExcelConfig.getCellData(iTestData, Constant.col_Desciption,Constant.sheet_DeciplinaryCases);
		Log.info("The description read from excel is:" + description);		
		driver.findElement(txtbx_CaseDesc).sendKeys(description);
		Log.info("The value "+description +" is entered as description in the text-box");
		driver.findElement(btn_Save).click();
		Log.info("Click action is performed on Save button");
		driver.findElement(btn_Action).click();
		Log.info("Click action is performed on Take Disciplinary Action button");
		Reporter.log("setDesciplinaryRecord method execution is completed",true);
}
    
    public static void setDesciplinaryAction(int iTestData,String screenshotPath) throws Exception{		

		action = ExcelConfig.getCellData(iTestData, Constant.col_DisciplinaryAction,Constant.sheet_DeciplinaryCases);
		Log.info("The action read from excel is: " + action);
		String actionNo[] = action.split(" ");
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.presenceOfElementLocated(CommonMethod.formatLocator(dd_selectActionNo, actionNo[0])));
		driver.findElement(CommonMethod.formatLocator(dd_selectActionNo, actionNo[0])).click();
		Log.info("The value "+actionNo[0]+ " is selected as deciplinary action");
		driver.findElement(btn_Select ).click();
		Log.info("Click action is performed on Select button");
		dueDate = ExcelConfig.getCellData(iTestData, Constant.col_DueDate, Constant.sheet_DeciplinaryCases);
		Log.info("The dueDate read from excel is: " + dueDate);
		driver.findElement(click_CreateDate).click();
		Log.info("The value "+dueDate+ " is selected as due date from calender");
		CommonMethod.date_HRM(dueDate, driver, 1);
		WebElement webelement_owner = driver.findElement(txtbx_OwnerName );
		webelement_owner.sendKeys(ownerName);
		webelement_owner.sendKeys(Keys.DOWN);
		webelement_owner.sendKeys(Keys.ENTER);
		Log.info("The value "+ownerName+"is entered as ownerName in the text-box");
		status = ExcelConfig.getCellData(iTestData, Constant.col_ActionStatus, Constant.sheet_DeciplinaryCases);
		Log.info("The status read from excel is" + status);
		driver.findElement(dd_Status).click();
		driver.findElement(CommonMethod.formatLocator(dd_select, status)).click();
		Log.info("The value "+status+" is selected as status in the dropdown");		
		Utils.screenShot(screenshotPath + "\\DisciplinaryCase.jpg", driver);
		Log.info("Screen shot is  taken for Desciplinary case");
		driver.findElement(btn_Save).click();
		Log.info("Click action is performed on Save button");
		Reporter.log("setDesciplinaryAction method execution is completed",true);
}
    
    public static void verifyDesciplinaryRec(int iTestData) throws Exception{	
    	
    	Thread.sleep(3000);
		driver.findElement(click_Fillter).click();
		WebElement webelement_filter=driver.findElement(txtbx_EmpnameSrch);
		webelement_filter.sendKeys(employeeName);
		webelement_filter.sendKeys(Keys.ARROW_DOWN);
		webelement_filter.sendKeys(Keys.ENTER);
		driver.findElement(btn_Search).click();
		Log.info("click action is performed for search button");
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
		Log.info("Click action is performed on view Link");

		String validation_Status = driver.findElement(txtbx_Status).getText();
		CommonMethod.verifyData(status, validation_Status);    	
		Reporter.log("verifyDesciplinaryRec method execution is completed",true);
    }
    
    public static void setCloseStatus(int iTestData,String screenshotPath) throws Exception{	
		// TO VIEW THE ACTION STATUS AND COMPLETE IF ITS NOT COMPLETED

		driver.findElement(click_ViewAction).click();
		Log.info("Click action is performed on Item Link");
		driver.findElement(btn_Deciplinary).click();
		Log.info("Click action is performed on View Disciplinary case button");
		Thread.sleep(2000);
		driver.findElement(btn_CloseCase).click();
		Log.info("Click action is performed on close case button");
		Thread.sleep(2000);
	
		// VERIFY CLOSE STATUS FOR THE CASE
		
		String validation_StatusClose = driver.findElement(CommonMethod.formatLocator(link_employee, employeeName))
				.getText();
		CommonMethod.verifyData("Close", validation_StatusClose);

		Utils.screenShot(screenshotPath + "\\CaseStatus.jpg", driver);
		Log.info("Screen shot is  taken for Case Status");
		Reporter.log("setCloseStatus method execution is completed",true);
    }
}