package testCases;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.RandomGenerator;

public class TC_03_AddVacancy {
	
	public static String timestamp, screenshotPath, browser, vacancy_Name, reason;
	public static Map<String, String> yaml;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		yaml = CommonMethod.yamlFileRead(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
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
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddVacancyCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(yaml, browser);
	}
	@Test
	public void addVacancy() throws Exception {
		Reporter.log("The Execution started for TC_03_AddVacancy",true);
		// LOAD AND READ THE PROPERTIES FILE


		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddVacancyCases);
		Reporter.log("The userName read from excel is : " + userName,true);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddVacancyCases);
		Reporter.log("The password read from excel is : " + password,true);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		Reporter.log("The value "+userName+" is entered as userName in the text-box",true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value "+password+" is entered as Password in the text-box",true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on log in button ",true);

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.screenShot(screenshotPath + "\\Add_Vacancy.jpg", driver);
			Reporter.log("Screen shot is  taken for Dashboard ",true);

		} catch (Exception user) {
			Reporter.log("Dashboard is not available, Test case is failed",true);
			reason="Dashboard is not available";		
			Assert.assertTrue(false, "Dashboard is not available, Test case is failed");
		}

		driver.findElement(By.xpath("//span[text()='Recruitment']")).click();
		Reporter.log("Click action is performed on Recruitment in the Menu bar",true);
		Thread.sleep(10000);

		int size = driver.findElements(By.tagName("iframe")).size();
		Reporter.log("Number of iframes are : " + size,true);

		// driver.switchTo().frame(0);
		// driver.switchTo().frame("noncoreIframe");
		WebElement element_iframe = driver.findElement(By.xpath("//iframe[@id='noncoreIframe']"));
		driver.switchTo().frame(element_iframe);

		Reporter.log("Switched into iframe",true);
		driver.findElement(By.xpath("(//i[text()='add'])[1]")).click();
		Reporter.log("Click action is performed on Add or import vacancy button",true);

		driver.findElement(By.xpath("(//i[text()='add'])[2]")).click();
		Reporter.log("Click action is performed on Add or import vacancy to Add",true);

		String vacancy_Name = "Testing_" + RandomGenerator.randomAlphabetic(6);
		driver.findElement(By.id("addJobVacancy_name")).sendKeys(vacancy_Name);
		Reporter.log("The value "+ vacancy_Name+" is entered as vacancy_Name in the text-box",true);

		//driver.findElement(By.id("textarea_addJobVacancy_jobTitle")).click();
		String vacancy_JobTitle = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_JobTitle,Constant.sheet_AddVacancyCases);
		Reporter.log("The vacancy_JobTitle read from excel is : " + vacancy_JobTitle,true);
		driver.findElement(By.xpath("//div[@id='textarea_addJobVacancy_jobTitle']")).click();
		driver.findElement(By.xpath("(//p[text()='" + vacancy_JobTitle.trim() + "'])[1]")).click();
		Reporter.log("The value "+ vacancy_JobTitle+" is selected as vacancy_JobTitle in the dropdown",true);

		driver.findElement(By.id("textarea_addJobVacancy_location")).click();
		String vacancy_location = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_location,Constant.sheet_AddVacancyCases);
		Reporter.log("The vacancy_location read from excel is : " + vacancy_location,true);
		driver.findElement(By.xpath("(//p[contains(text(),'" + vacancy_location.trim() + "')])[1]")).click();
		Reporter.log("The value "+ vacancy_location+" is selected as vacancy_location in the dropdown",true);

		driver.findElement(By.id("textarea_addJobVacancy_sub_unit")).click();
		String subUnit = ExcelConfig.getCellData(iTestData, Constant.col_subUnit, Constant.sheet_AddVacancyCases);
		Reporter.log("The subUnit read from excel is : " + subUnit,true);
		driver.findElement(By.xpath("(//p[text()='" + subUnit + "'])[1]")).click();
		Reporter.log("The value "+ subUnit+" is selected as subUnit in the dropdown",true);

		driver.findElement(By.id("textarea_addJobVacancy_hiringManagers")).click();
		String hiringManagers = ExcelConfig.getCellData(iTestData, Constant.col_HiringManagers,Constant.sheet_AddVacancyCases);
		Reporter.log("The hiringManagers read from excel is : " + hiringManagers,true);
		driver.findElement(By.xpath("//p[text()='" + hiringManagers + "']")).click();
		Reporter.log("The value "+ hiringManagers+" is selected as hiringManagers in the dropdown",true);

		String noOfPositions = ExcelConfig.getCellData(iTestData, Constant.col_NoOfPositions,Constant.sheet_AddVacancyCases);
		Reporter.log("The noOfPositions read from excel is : " + noOfPositions,true);
		driver.findElement(By.id("addJobVacancy_noOfPositions")).sendKeys(noOfPositions);
		Reporter.log("The value "+ noOfPositions+" is entered as noOfPositions in the text-box",true);

		driver.findElement(By.id("saveVacancy")).click();
		Reporter.log("Click action is performed on Save Button",true);
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//i[@class='material-icons mdi-navigation-menu']")).click();

		driver.findElement(By.linkText("Home")).click();
		Reporter.log("Click action is performed on Home link",true);

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
		
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		driver.quit();
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,CommonMethod.pathExcel);
		Reporter.log("The value "+vacancy_Name+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_Vacancy_name+" in the "+Constant.sheet_AddVacancyCases,true);
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.pathExcel);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.pathExcel);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, CommonMethod.pathExcel);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
		
		Reporter.log("TestCase execution is completed",true);
		
	}

}
