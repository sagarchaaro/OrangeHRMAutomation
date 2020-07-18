package testCases;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import pages.BaseClass;
import pages.Home_Page;
import pages.Login_Page;
import pages.Recruitment_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;


public class TC_03_AddVacancy extends Suite{
	
	public static String timestamp, screenshotPath, excelPath, browser, vacancy_Name, reason, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	static ExtentTest logger;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath, true);
		Log.info("The Project Path is:"+CommonMethod.projectpath );
		
	}
	
	@Parameters({"testID"})
	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception{
		testName=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1)+"_"+testID;
		logger=ExtentManage.getExtentTest(report, testName);
		Log.startTestCase(testName);
				
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("The Testcase id executing is :"+testID );
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase );
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddVacancyCases);
		Log.info("The row no for test Data is : " + iTestData );
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + browser );
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	@Test
	public void addVacancy() throws Exception {
		Reporter.log("The Execution started for TC_03_AddVacancy",true);
		Log.info("The Execution started for TC_03_AddVacancy" );
		// LOAD AND READ THE PROPERTIES FILE


		// LOGIN AND DASHBOARD VALDATION
		Login_Page.loginPageVerify();
		Log.info("Title of the OrangeHRM Application is verified");
		logger.log(Status.INFO, "Title of the OrangeHRM Application is verified");
		
		Login_Page.login(iTestData,Constant.sheet_AddVacancyCases);
		Log.info("Logged into OrangeHRM Application");
		logger.log(Status.INFO, "Logged into OrangeHRM Application");
		
		Home_Page.navigateMenu("Recruitment");
		Log.info("Navigated to Recruitment page");
		logger.log(Status.INFO, "Navigated to Recruitment page");
		
		Recruitment_Page.navigateIntoFrames();
		Log.info("Navigated to frames");
		logger.log(Status.INFO, "Navigated to frames");
		
		Recruitment_Page.vacancyDetails(iTestData);
		Log.info("Entered vacancy details");
		logger.log(Status.INFO, "Entered vacancy details");
		
		Recruitment_Page.verifyJobAndGoToMenu();
		Log.info("Verified new vacancy details");
		logger.log(Status.INFO, "Verified new vacancy details");
		
		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Logged out from OrangeHRM Application");
		logger.log(Status.INFO, "Logged out from OrangeHRM Application");
		
		
		
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		if(result.getStatus() == ITestResult.SUCCESS){
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,excelPath);
		Log.info("The value "+vacancy_Name+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_Vacancy_name+" in the "+Constant.sheet_AddVacancyCases );
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, excelPath);
		Log.info("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments+" in the "+Constant.sheet_TestCases );
		
		logger.log(Status.PASS, "Testcase " +testName+ " is passed");
		
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(reportPath +"\\screenshot"+ "\\Fail_"+testName+".jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
			ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
			
			logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(reportPath + "\\Fail_"+testName+".jpg").build());
			
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+reason );
			logger.log(Status.SKIP, "Testcase " +testName+ " is skipped");
		}
		
		driver.quit();
		Reporter.log("TestCase execution is completed",true);
		Log.endTestCase();
		report.flush();
	}

}
