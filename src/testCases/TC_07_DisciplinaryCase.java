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
import pages.Discipline_Page;
import pages.Home_Page;
import pages.Login_Page;
import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;

public class TC_07_DisciplinaryCase extends Suite{

	//CLASS VARIABLE DECLARATION
	public static String timestamp, screenshotPath, iBrowser,url, excelPath, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	static ExtentTest logger;

	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Log.info("The Project Path is:"+CommonMethod.projectpath);
	
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
		Log.info("The Testcase id executing is :"+testID);
		Reporter.log("The Testcase id executing is :"+testID);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_DeciplinaryCases);
		Log.info("The row no for of test Data is : " + iTestData);
		iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD				
		driver = Utils.openBrowser(CommonMethod.yamlData, iBrowser);
		new BaseClass(driver);
				
	}


	@Test
	public  void disciplinaryCase() throws InterruptedException, Exception {
		Log.info("The Execution started for TC_07_DisciplinaryCase");				
	
		Login_Page.login(iTestData,Constant.sheet_DeciplinaryCases);	
		Log.info("Logged into OrangeHRM Application");
		logger.log(Status.INFO, "Logged into OrangeHRM Application");
		Reporter.log("Logged into OrangeHRM Application", true);
		
		Home_Page.verifyDashboard(reportPath);
		Log.info("Dashboard is verified");
		logger.log(Status.INFO, "Dashboard is verified");
		Reporter.log("Dashboard is verified", true);
		
		Home_Page.navigateMenu("PIM", "Employee List");
		Log.info("Navigated to the Employee List window");
		logger.log(Status.INFO, "Navigated to the Employee List window");
		Reporter.log("Navigated to the Employee List window", true);
		
		Discipline_Page.storeUserInArray(iTestData, reportPath);
		Log.info("Users data stored in the array");
		logger.log(Status.INFO, "Users data stored in the array");
		Reporter.log("Users data stored in the array", true);
		
	    Home_Page.navigateDesciplinary();
		Log.info("Navigated to the Disciplinary window");
		logger.log(Status.INFO, "Navigated to the Disciplinary window");
		Reporter.log("Navigated to the Disciplinary window", true);

		Discipline_Page.addDesciplinaryRecord();
		Log.info("Navigated to the Disciplinary record creation");
		logger.log(Status.INFO, "Navigated to the Disciplinary record creation");
		Reporter.log("Navigated to the Disciplinary record creation", true);


		Discipline_Page.setDesciplinaryRecord(iTestData);
		Log.info("Disciplinary record is created");
		logger.log(Status.INFO, "Disciplinary record is created");
		Reporter.log("Disciplinary record is created", true);
		
		Discipline_Page.setDesciplinaryAction(iTestData, reportPath);
		Log.info("Disciplinary record action is selected");
		logger.log(Status.INFO, "Disciplinary record action is selected");
		Reporter.log("Disciplinary record action is selected", true);

		Home_Page.navigateDesciplinary();	
		Log.info("Navigated to the Disciplinary window");
		logger.log(Status.INFO, "Navigated to the Disciplinary window");
		Reporter.log("Navigated to the Disciplinary window", true);
		
		Discipline_Page.verifyDesciplinaryRec(iTestData); 
		Log.info("Disciplinary record creation is verified ");
		logger.log(Status.INFO, "Disciplinary record creation is verified ");
		Reporter.log("Disciplinary record creation is verified ", true);

		Discipline_Page.setCloseStatus(iTestData, reportPath);
		Log.info("Disciplinary record close status is verified ");
		logger.log(Status.INFO, "Disciplinary record close status is verified ");
		Reporter.log("Disciplinary record close status is verified ", true);
		
		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Loggedout from the OrangeHRM application");
		logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
		Reporter.log("Loggedout from the OrangeHRM application", true);
	
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception{



		if(result.getStatus() == ITestResult.SUCCESS){
			ExcelConfig.setCellData(Discipline_Page.employeeName, iTestData, Constant.col_EmpName, Constant.sheet_DeciplinaryCases,excelPath);
			
			Log.info("The value "+Discipline_Page.employeeName+" is written as employeeName against to RowNumber "+iTestData +", column Number " +Constant.col_EmpName
					+" in the "+Constant.sheet_DeciplinaryCases);

			ExcelConfig.setCellData(Discipline_Page.ownerName, iTestData, Constant.col_OwnerName, Constant.sheet_DeciplinaryCases,excelPath);
				
			Log.info("The value "+Discipline_Page.ownerName+" is written as ownerName against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName
					+" in the "+Constant.sheet_DeciplinaryCases);

			ExcelConfig.setCellData(Discipline_Page.createdBy, iTestData, Constant.col_CreatedBy, Constant.sheet_DeciplinaryCases,excelPath);
			
			Log.info("The value "+Discipline_Page.createdBy+" is written as CreatedBy against to RowNumber "+iTestData +", column Number " +Constant.col_CreatedBy
					+" in the "+Constant.sheet_DeciplinaryCases);

			ExcelConfig.setCellData(Discipline_Page.createdOn, iTestData, Constant.col_CreatedOn, Constant.sheet_DeciplinaryCases,excelPath);
			
			Log.info("The value "+Discipline_Page.createdOn+" is written as CreatedOn against to RowNumber "+iTestData +", column Number " +Constant.col_CreatedOn
					+" in the "+Constant.sheet_DeciplinaryCases);			
		
			ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			
			Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
				+" in the "+Constant.sheet_TestCases);
			logger.log(Status.PASS, "Testcase " +testName+ " is passed");
			Reporter.log( "Testcase " +testName+ " is passed",true);
		
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Utils.screenShot(reportPath + "\\_Fail.jpg", driver);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
			Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData(CommonMethod.reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
			Log.info(CommonMethod.reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases);
			logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(reportPath + "\\Fail.jpg").build());
			Reporter.log( "Testcase " +testName+ " is failed",true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+CommonMethod.reason);
			logger.log(Status.SKIP, "Testcase " +testName+ " is skipped");
			Reporter.log("Testcase " +testName+ " is skipped",true);
		}
		
		driver.quit();
		Log.endTestCase();
		report.flush();

	}

}
