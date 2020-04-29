package testCases;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_07_DisciplinaryCase {

	// CLASS VARIABLE DECLARATION

	public static void main(String[] args) throws Exception {

		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Project Path is:"+CommonMethod.projectpath);
		Properties prop = CommonMethod.PropertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_DeciplinaryCases);
		System.out.println("The row no for of test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD
		String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.OpenBrowser(CommonMethod.Url, driverPath, iBrowser);
		String timestamp = Utils.TimeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.Validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_DeciplinaryCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_DeciplinaryCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println(userName+"is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println(password+"is entered as Password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on Login button");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.ScreenShot(screenshotPath + "\\OrangeHRMLogin.jpg", driver);
			System.out.println("Dashboard is avilable, Screen Print taken");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.PathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.PathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);

			throw new Exception();
		}

		// GOTO THE EMPLOYEELIST AND SET ALL EMPLOYEE NAME INTO THE ARRY TO
		// SELECT EMP NAME AND OWNER NAME

		driver.findElement(By.xpath("//html/body/div/div/div/div/div[2]/div/div/div[4]/ul/li[2]/a/span[2]")).click();
		System.out.println("Click action is performed on PLM in the Menu bar");
		driver.findElement(By.xpath("//span[text()='Employee List']")).click();
		System.out.println(" Click action is performed on Employee list in the Menu bar");
		int totalElementNo = driver.findElements(By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]")).size();
		System.out.println("The total no of employee in the page is: " + totalElementNo);
		List<WebElement> webelement_empName = driver.findElements(By.xpath("//table[@id='employeeListTable']/tbody/tr/td[3]"));
		String[] empNameArray = Utils.dataIntoArray(webelement_empName, totalElementNo);
		String employeeName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		System.out.println("The employeeName selected by random no is " + employeeName);
		String ownerName = Utils.selectWithRandomIndex(totalElementNo, empNameArray);
		System.out.println("The ownerName selected by random no is " + ownerName);
		Utils.ScreenShot(screenshotPath + "\\EmployeeList.jpg", driver);
		System.out.println("Screen shot is  taken for Employee List ");

		// THE BLOCK IS TO CREATE DECIPLINARY RECORD

		driver.findElement(By.xpath("//span[text()='Discipline']")).click();
		System.out.println("Click action is performed on Discipline in the Menu bar");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Disciplinary Cases']")));
		driver.findElement(By.xpath("//span[text()='Discipline']/..//following::a/span[text()='Disciplinary Cases']")).click();
		// span[contains(text(),'Disciplinary Cases')]
		System.out.println("Click action is performed on Disciplinary Cases in the Menu bar");
		driver.switchTo().frame(0);
		System.out.println("Switched the Frame");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[text()='add']")));
		driver.findElement(By.xpath("//i[text()='add']")).click();
		System.out.println("Click action is performed on Add button");
		WebElement webelement_empname = driver.findElement(By.xpath("//input[@id='addCase_employeeName_empName']"));
		webelement_empname.sendKeys(employeeName);
		webelement_empname.sendKeys(Keys.DOWN);
		webelement_empname.sendKeys(Keys.ENTER);
		System.out.println(employeeName+"is entered as employeeName in the text-box");
		String caseNo = ExcelConfig.getCellData(iTestData, Constant.col_caseName, Constant.sheet_DeciplinaryCases);
		System.out.println("The caseNo read from excel is " + caseNo);
		// String caseNo=sht.getRow(R1).getCell(4).getStringCellValue();
		driver.findElement(By.xpath("//input[@id='addCase_caseName']")).sendKeys(caseNo);
		System.out.println( caseNo+"is entered as caseName in the text-box");
		String description = ExcelConfig.getCellData(iTestData, Constant.col_Desciption,Constant.sheet_DeciplinaryCases);
		System.out.println(description +"is entered as description in the text-box");
		driver.findElement(By.xpath("//textarea[@id='addCase_description']")).sendKeys(description);
		System.out.println("Data entered for description " + description);
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		driver.findElement(By.xpath("//a[text()='Take Disciplinary Action']")).click();
		System.out.println("Click action is performed on Take Disciplinary Action button");
		String action = ExcelConfig.getCellData(iTestData, Constant.col_DisciplinaryAction,Constant.sheet_DeciplinaryCases);
		System.out.println("The value of action is: " + action);
		// String action=sht.getRow(R1).getCell(11).getStringCellValue();
		String actionNo[] = action.split(" ");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@for='" + actionNo[0] + "']")));
		driver.findElement(By.xpath("//label[@for='" + actionNo[0] + "']")).click();
		System.out.println("Select deciplinary action as " + actionNo[0]);
		driver.findElement(By.xpath("//a[text()='Select']")).click();
		System.out.println("Select button is clicked");
		String dueDate = ExcelConfig.getCellData(iTestData, Constant.col_DueDate, Constant.sheet_DeciplinaryCases);
		System.out.println("The value of dueDate is: " + dueDate);
		// String dueDate=sht.getRow(R1).getCell(9).getStringCellValue();
		driver.findElement(By.xpath("//input[@id='createDate']")).click();
		System.out.println("The due date is clicked");
		CommonMethod.Date_HRM(dueDate, driver, 1);
		WebElement webelement_owner = driver.findElement(By.xpath("//input[@id='defaultAction_owner_empName']"));
		webelement_owner.sendKeys(ownerName);
		webelement_owner.sendKeys(Keys.DOWN);
		webelement_owner.sendKeys(Keys.ENTER);
		System.out.println("the Owner name is entered");
		String status = ExcelConfig.getCellData(iTestData, Constant.col_ActionStatus, Constant.sheet_DeciplinaryCases);
		System.out.println("The value of status is" + status);
		driver.findElement(By.xpath("//label[text()='Status']//parent::div//child::input")).click();
		driver.findElement(By.xpath("//span[text()='" + status + "']")).click();
		// String filename1=screenshotPath+"\\DisciplinaryCase.jpg";
		Utils.ScreenShot(screenshotPath + "\\DisciplinaryCase.jpg", driver);
		System.out.println("Deciplinary case is created, Screen Print taken");
		driver.findElement(By.xpath("//a[text()='Save']")).click();
		System.out.println("Save button is clicked");

		driver.findElement(By.xpath("//span[text()='Disciplinary Cases']")).click();
		System.out.println("Disciplinary Cases is clicked");
		
		driver.findElement(By.xpath("//i[text()='ohrm_filter']")).click();
		WebElement element=driver.findElement(By.xpath("//input[@id='DisciplinaryCaseSearch_empName_empName']"));
		element.sendKeys(employeeName);
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//a[@id='searchBtn']")).click();
		System.out.println("click is performed for search button");
		Thread.sleep(3000);


		// THE BLOCK IS VALIDATING THE DICIPLINARY CREATED RECORD

		String validation_name = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[2]")).getText();
		CommonMethod.Validation(employeeName, validation_name, iTestCase);

		String validation_case = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[3]")).getText();
		CommonMethod.Validation(caseNo, validation_case, iTestCase);

		String validation_description = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[4]"))
				.getText();
		CommonMethod.Validation(description, validation_description, iTestCase);

		String CreatedBy = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[5]")).getText();
		String CreatedOn = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[6]")).getText();

		driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[7]/a")).click();
		System.out.println("The view is clicked");

		String validation_Status = driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td[5]")).getText();
		CommonMethod.Validation(status, validation_Status, iTestCase);

		// TO VIEW THE ACTION STATUS AND COMPLETE IF ITS NOT COMPLETED

		driver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr/td/a")).click();
		System.out.println("The Item is clicked");
		driver.findElement(By.xpath("//a[text()='View Disciplinary Case']")).click();
		System.out.println("The View Disciplinary case button is clicked");
		driver.findElement(By.xpath("//a[text()='Close Case']")).click();
		System.out.println("The close case button is clicked");
	/*	String status_check = driver.findElement(By.xpath("//input[@class='select-dropdown']")).getAttribute("value");
		if (status_check.equalsIgnoreCase("Completed")) {
			driver.findElement(By.xpath("//a[text()='View Disciplinary Case']")).click();
			driver.switchTo().frame(0);
			System.out.println("The View Disciplinary case button is clicked");
			driver.findElement(By.xpath("//a[text()='Close Case']")).click();
			System.out.println("The close case button is clicked");

			/*driver.findElement(By.xpath("//a[@id='btnCancel']")).click();
			System.out.println("The cancel button is clicked");
		} else {*/
			
			/*driver.switchTo().frame("noncoreIframe");
			driver.findElement(By.xpath("//a[@id='btnEdit']")).click();
			System.out.println("The Edit button is clicked");
			/*
			 * driver.findElement(By.xpath("//input[@class='select-dropdown']")).click();
			 * driver.findElement(By.xpath("//span[text()='Completed']")).click();
			 * System.out.println("The status is selected to completed");
			 		
			driver.findElement(By.xpath("//a[@id='btnSave']")).click();
			System.out.println("The Save button is clicked");*/
			

		//}

		// CLOSE THE STATUS FOR THE CASE

		
		String validation_StatusClose = driver.findElement(By.xpath("//td[text()='" + employeeName + "']/../td[8]"))
				.getText();
		CommonMethod.Validation("Close", validation_StatusClose, iTestCase);

		// String filename2=screenshotPath+"\\CaseStatus.jpg";
		Utils.ScreenShot(screenshotPath + "\\CaseStatus.jpg", driver);

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();

		// WRITE THE DATA IN THE EXCEL FILE.

		ExcelConfig.setCellData(employeeName, iTestData, Constant.col_EmpName, Constant.sheet_DeciplinaryCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData(ownerName, iTestData, Constant.col_OwnerName, Constant.sheet_DeciplinaryCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData(CreatedBy, iTestData, Constant.col_CreatedBy, Constant.sheet_DeciplinaryCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData(CreatedOn, iTestData, Constant.col_CreatedOn, Constant.sheet_DeciplinaryCases,
				CommonMethod.PathExcel);

		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
				CommonMethod.PathExcel);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,
				Constant.sheet_TestCases, CommonMethod.PathExcel);

		System.out.println("The file are closed");

	}

}
