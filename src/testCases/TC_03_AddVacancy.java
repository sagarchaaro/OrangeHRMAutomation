package testCases;

import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_03_AddVacancy {
	
	public static String timestamp, screenshotPath, browser, vacancy_Name;
	public static Properties prop;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites(){
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.screenshotPath + CommonMethod.testCaseID + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@BeforeMethod()
	public void browserLaunch() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println("The Data read from Properties file.");		
		prop = CommonMethod.propertilesRead(CommonMethod.projectpath + "\\test-resources\\TestInfo.properties");
		System.out.println("The Testcase id executing is :"+CommonMethod.testCaseID);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.pathExcel);
		iTestCase = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(CommonMethod.testCaseID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		System.out.println("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(prop, browser);
	}
	@Test
	public void addVacancy() throws Exception {
		System.out.println("The Execution started for TC_03_AddVacancy");
		// LOAD AND READ THE PROPERTIES FILE


		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddVacancyCases);
		System.out.println("The userName read from excel is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddVacancyCases);
		System.out.println("The password read from excel is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		System.out.println("The value "+userName+" is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		System.out.println("The value "+password+" is entered as Password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click action is performed on log in button ");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\Add_Vacancy.jpg", driver);
			System.out.println("Screen shot is  taken for Dashboard ");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.pathExcel);
			System.out.println("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.pathExcel);
			System.out.println("Dashboard is not available is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			throw new Exception();
		}

		driver.findElement(By.xpath("//span[text()='Recruitment']")).click();
		System.out.println("Click action is performed on Recruitment in the Menu bar");
		Thread.sleep(10000);

		int size = driver.findElements(By.tagName("iframe")).size();
		System.out.println("Number of iframes are : " + size);

		// driver.switchTo().frame(0);
		// driver.switchTo().frame("noncoreIframe");
		WebElement element_iframe = driver.findElement(By.xpath("//iframe[@id='noncoreIframe']"));
		driver.switchTo().frame(element_iframe);

		System.out.println("Switched into iframe");
		driver.findElement(By.xpath("(//i[text()='add'])[1]")).click();
		System.out.println("Click action is performed on Add or import vacancy button");

		driver.findElement(By.xpath("(//i[text()='add'])[2]")).click();
		System.out.println("Click action is performed on Add or import vacancy to Add");

		String vacancy_Name = "Testing_" + RandomStringUtils.randomAlphabetic(6);
		driver.findElement(By.id("addJobVacancy_name")).sendKeys(vacancy_Name);
		System.out.println("The value "+ vacancy_Name+" is entered as vacancy_Name in the text-box");

		driver.findElement(By.id("textarea_addJobVacancy_jobTitle")).click();
		String vacancy_JobTitle = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_JobTitle,
				Constant.sheet_AddVacancyCases);
		System.out.println("The vacancy_JobTitle read from excel is : " + vacancy_JobTitle);
		driver.findElement(By.xpath("//div[@id='textarea_addJobVacancy_jobTitle']")).click();
		driver.findElement(By.xpath("(//p[text()='" + vacancy_JobTitle.trim() + "'])[1]")).click();
		System.out.println("The value "+ vacancy_JobTitle+" is selected as vacancy_JobTitle in the dropdown");

		driver.findElement(By.id("textarea_addJobVacancy_location")).click();
		String vacancy_location = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_location,
				Constant.sheet_AddVacancyCases);
		System.out.println("The vacancy_location read from excel is : " + vacancy_location);
		driver.findElement(By.xpath("(//p[contains(text(),'" + vacancy_location.trim() + "')])[1]")).click();
		System.out.println("The value "+ vacancy_location+" is selected as vacancy_location in the dropdown");

		driver.findElement(By.id("textarea_addJobVacancy_sub_unit")).click();
		String subUnit = ExcelConfig.getCellData(iTestData, Constant.col_subUnit, Constant.sheet_AddVacancyCases);
		System.out.println("The subUnit read from excel is : " + subUnit);
		driver.findElement(By.xpath("(//p[text()='" + subUnit + "'])[1]")).click();
		System.out.println("The value "+ subUnit+" is selected as subUnit in the dropdown");

		driver.findElement(By.id("textarea_addJobVacancy_hiringManagers")).click();
		String hiringManagers = ExcelConfig.getCellData(iTestData, Constant.col_HiringManagers,
				Constant.sheet_AddVacancyCases);
		System.out.println("The hiringManagers read from excel is : " + hiringManagers);
		driver.findElement(By.xpath("//p[text()='" + hiringManagers + "']")).click();
		System.out.println("The value "+ hiringManagers+" is selected as hiringManagers in the dropdown");

		String noOfPositions = ExcelConfig.getCellData(iTestData, Constant.col_NoOfPositions,
				Constant.sheet_AddVacancyCases);
		System.out.println("The noOfPositions read from excel is : " + noOfPositions);
		driver.findElement(By.id("addJobVacancy_noOfPositions")).sendKeys(noOfPositions);
		System.out.println("The value "+ noOfPositions+" is entered as noOfPositions in the text-box");

		driver.findElement(By.id("saveVacancy")).click();
		System.out.println("Click action is performed on Save Button");
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//i[@class='material-icons mdi-navigation-menu']")).click();

		driver.findElement(By.linkText("Home")).click();
		System.out.println("Click action is performed on Home link");

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
		
		
	}
	@AfterMethod
	public void tearDown() throws Exception{
		driver.quit();
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,CommonMethod.pathExcel);
		System.out.println("The value "+vacancy_Name+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_Vacancy_name+" in the "+Constant.sheet_AddVacancyCases);
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
		System.out.println("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.pathExcel);
		System.out.println("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases);
		System.out.println("The file are closed");
	}

}
