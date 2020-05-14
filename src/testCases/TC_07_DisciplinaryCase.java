package testCases;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_07_DisciplinaryCase {

	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		// LOAD AND READ THE PROPERTIES FILE
		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
	
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
		
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_DeciplinaryCases);
		Reporter.log("The row no for of test Data is : " + iTestData,true);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + iBrowser,true);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(prop, iBrowser);		
				
	}


	@Test
	public  void disciplinaryCase() throws InterruptedException, Exception {
		Reporter.log("The Execution started for TC_07_DisciplinaryCase",true);
				
		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_DeciplinaryCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_DeciplinaryCases);
		Reporter.log("The password read from excel is : " + password,true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button",true);

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\OrangeHRMLogin.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ");
			
		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case is failed",true);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases,true);

			throw new Exception();
		}

		// GOTO THE EMPLOYEELIST AND SET ALL EMPLOYEE NAME INTO THE ARRY TO
		// SELECT EMP NAME AND OWNER NAME

		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		Reporter.log("Click action is performed on PLM in the Menu bar",true);
		driver.findElement(By.xpath("//span[text()='Employee List']")).click();
		Reporter.log("Click action is performed on Employee list in the Menu bar",true);
		int totalElementNo = driver.findElements(By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]")).size();
		Reporter.log("The total no of employee in the page is: " + totalElementNo,true);
		List<WebElement> webelement_empName = driver.findElements(By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]"));
		Reporter.log("All employeeName are stored in the WebElement",true);
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		Reporter.log("All employeeName are stored in the Array",true);
		String employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The employeeName selected by random no is:" + employeeName,true);
		String ownerName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		Reporter.log("The ownerName selected by random no is:" + ownerName,true);
		Utils.screenShot(screenshotPath + "\\EmployeeList.jpg", driver);
		Reporter.log("Screen shot is  taken for Employee List ",true);

		// THE BLOCK IS TO CREATE DECIPLINARY RECORD

		driver.findElement(By.xpath("//span[text()='Discipline']")).click();
		Reporter.log("Click action is performed on Discipline in the Menu bar",true);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Disciplinary Cases']")));
		driver.findElement(By.xpath("//span[text()='Discipline']/..//following::a/span[text()='Disciplinary Cases']")).click();
		// span[contains(text(),'Disciplinary Cases')]
		Reporter.log("Click action is performed on Disciplinary Cases in the Menu bar",true);
		driver.switchTo().frame(0);
		Reporter.log("Switched the Frame",true);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[text()='add']")));
		driver.findElement(By.xpath("//i[text()='add']")).click();
		Reporter.log("Click action is performed on Add button",true);
		WebElement webelement_empname = driver.findElement(By.xpath("//input[@id='addCase_employeeName_empName']"));
		webelement_empname.sendKeys(employeeName);
		webelement_empname.sendKeys(Keys.DOWN);
		webelement_empname.sendKeys(Keys.ENTER);
		Reporter.log("The value "+employeeName+" is entered as employeeName in the text-box",true);
		String caseNo = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_DeciplinaryCases);
		Reporter.log("The caseNo read from excel is:" + caseNo,true);
		// String caseNo=sht.getRow(R1).getCell(4).getStringCellValue();
		driver.findElement(By.xpath("//input[@id='addCase_caseName']")).sendKeys(caseNo);
		Reporter.log("The value "+ caseNo+" is entered as caseName in the text-box",true);
		String description = ExcelConfig.getCellData(iTestData, Constant.col_Desciption,Constant.sheet_DeciplinaryCases);
		Reporter.log("The description read from excel is:" + description,true);		
		driver.findElement(By.xpath("//textarea[@id='addCase_description']")).sendKeys(description);
		Reporter.log("The value "+description +" is entered as description in the text-box",true);
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		driver.findElement(By.xpath("//a[text()='Take Disciplinary Action']")).click();
		Reporter.log("Click action is performed on Take Disciplinary Action button",true);
		String action = ExcelConfig.getCellData(iTestData, Constant.col_DisciplinaryAction,Constant.sheet_DeciplinaryCases);
		Reporter.log("The action read from excel is: " + action,true);
		// String action=sht.getRow(R1).getCell(11).getStringCellValue();
		String actionNo[] = action.split(" ");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@for='" + actionNo[0] + "']")));
		driver.findElement(By.xpath("//label[@for='" + actionNo[0] + "']")).click();
		Reporter.log("The value "+actionNo[0]+ " is selected as deciplinary action",true);
		driver.findElement(By.xpath("//a[text()='Select']")).click();
		Reporter.log("Click action is performed on Select button",true);
		String dueDate = ExcelConfig.getCellData(iTestData, Constant.col_DueDate, Constant.sheet_DeciplinaryCases);
		Reporter.log("The dueDate read from excel is: " + dueDate,true);
		// String dueDate=sht.getRow(R1).getCell(9).getStringCellValue();
		driver.findElement(By.xpath("//input[@id='createDate']")).click();
		Reporter.log("The value "+dueDate+ " is selected as due date from calender");
		CommonMethod.date_HRM(dueDate, driver, 1);
		WebElement webelement_owner = driver.findElement(By.xpath("//input[@id='defaultAction_owner_empName']"));
		webelement_owner.sendKeys(ownerName);
		webelement_owner.sendKeys(Keys.DOWN);
		webelement_owner.sendKeys(Keys.ENTER);
		Reporter.log("The value "+ownerName+"is entered as ownerName in the text-box",true);
		String status = ExcelConfig.getCellData(iTestData, Constant.col_ActionStatus, Constant.sheet_DeciplinaryCases);
		Reporter.log("The status read from excel is" + status,true);
		driver.findElement(By.xpath("//label[text()='Status']//parent::div//child::input")).click();
		driver.findElement(By.xpath("//span[text()='" + status + "']")).click();
		Reporter.log("The value "+status+" is selected as status in the dropdown",true);		
		Utils.screenShot(screenshotPath + "\\DisciplinaryCase.jpg", driver);
		Reporter.log("Screen shot is  taken for Desciplinary case",true);
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		Reporter.log("Click action is performed on Save button",true);

		driver.findElement(By.xpath("//span[text()='Disciplinary Cases']")).click();
		Reporter.log("Click action is performed on Disciplinary Cases in the Menu bar",true);
		
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		WebElement webelement_filter=driver.findElement(By.xpath("//input[@id='DisciplinaryCaseSearch_empName_empName']"));
		webelement_filter.sendKeys(employeeName);
		webelement_filter.sendKeys(Keys.ARROW_DOWN);
		webelement_filter.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//a[@id='searchBtn']")).click();
		Reporter.log("click action is performed for search button",true);
		Thread.sleep(3000);


		// THE BLOCK IS VALIDATING THE DICIPLINARY CREATED RECORD

		String validation_name = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[2]")).getText();
		CommonMethod.validation(employeeName, validation_name, iTestCase);

		String validation_case = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[3]")).getText();
		CommonMethod.validation(caseNo, validation_case, iTestCase);

		String validation_description = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[4]"))
				.getText();
		CommonMethod.validation(description, validation_description, iTestCase);

		String createdBy = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[5]")).getText();
		String createdOn = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[6]")).getText();

		driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[7]/a")).click();
		Reporter.log("Click action is performed on view Link",true);

		String validation_Status = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[5]")).getText();
		CommonMethod.validation(status, validation_Status, iTestCase);

		// TO VIEW THE ACTION STATUS AND COMPLETE IF ITS NOT COMPLETED

		driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td/a")).click();
		Reporter.log("Click action is performed on Item Link",true);
		driver.findElement(By.xpath("//a[text()='View Disciplinary Case']")).click();
		Reporter.log("Click action is performed on View Disciplinary case button",true);
		driver.findElement(By.xpath("//a[text()='Close Case']")).click();
		Reporter.log("Click action is performed on close case button",true);
	
		// VERIFY CLOSE STATUS FOR THE CASE
		
		String validation_StatusClose = driver.findElement(By.xpath("//td[text()='" + employeeName + "']/../td[8]"))
				.getText();
		CommonMethod.validation("Close", validation_StatusClose, iTestCase);

		Utils.screenShot(screenshotPath + "\\CaseStatus.jpg", driver);
		Reporter.log("Screen shot is  taken for Case Status",true);

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		// WRITE THE DATA IN THE EXCEL FILE.

		ExcelConfig.setCellData(employeeName, iTestData, Constant.col_EmpName, Constant.sheet_DeciplinaryCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+employeeName+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EmpName
				+" in the "+Constant.sheet_DeciplinaryCases,true);

		ExcelConfig.setCellData(ownerName, iTestData, Constant.col_OwnerName, Constant.sheet_DeciplinaryCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+ownerName+" is written as ownerName against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName
				+" in the "+Constant.sheet_DeciplinaryCases,true);

		ExcelConfig.setCellData(createdBy, iTestData, Constant.col_CreatedBy, Constant.sheet_DeciplinaryCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+createdBy+" is written as CreatedBy against to RowNumber "+iTestData +", column Number " +Constant.col_CreatedBy
				+" in the "+Constant.sheet_DeciplinaryCases,true);

		ExcelConfig.setCellData(createdOn, iTestData, Constant.col_CreatedOn, Constant.sheet_DeciplinaryCases,
				CommonMethod.pathExcel);
		Reporter.log("The value "+createdOn+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_CreatedOn
				+" in the "+Constant.sheet_DeciplinaryCases,true);


	}
	
	@AfterMethod
	public void afterMethod() throws Exception{

		driver.quit();


		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.pathExcel);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases,true);

		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
				+" in the "+Constant.sheet_TestCases,true);


		Reporter.log("The file are closed",true);

	}

}
