package testCases;

import org.openqa.selenium.WebDriver;
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
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import pages.Recruitment_Page;
import utilities.Utils;
import utilities.ExcelConfig;


public class TC_03_AddVacancy {
	
	public static String timestamp, screenshotPath, excelPath, browser, vacancy_Name, reason;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		screenshotPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.projectpath+ screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
		
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
				
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Reporter.log("The Testcase id executing is :"+testID,true);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddVacancyCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	@Test
	public void addVacancy() throws Exception {
		Reporter.log("The Execution started for TC_03_AddVacancy",true);
		// LOAD AND READ THE PROPERTIES FILE


		// LOGIN AND DASHBOARD VALDATION
		Login_Page.loginPageVerify();
		
		Login_Page.login(iTestData,Constant.sheet_AddVacancyCases);
		
		Home_Page.navigateMenu("Recruitment");
		
		Recruitment_Page.navigateIntoFrames();
		
		Recruitment_Page.vacancyDetails(iTestData);
		
		Recruitment_Page.verifyJobAndGoToMenu();

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		
		
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,excelPath);
		Reporter.log("The value "+vacancy_Name+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_Vacancy_name+" in the "+Constant.sheet_AddVacancyCases,true);
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, excelPath);
		Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(screenshotPath + "\\Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
		
		driver.quit();
		Reporter.log("TestCase execution is completed",true);
		
	}

}
