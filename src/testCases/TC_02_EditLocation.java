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
import pages.Admin_Page;
import pages.BaseClass;
import pages.HRM_TestCase_Method;
import pages.Home_Page;
import pages.Login_Page;

import utilities.Utils;
import utilities.ExcelConfig;
import utilities.ExtentManage;
import utilities.Log;
import utilities.Suite;

public class TC_02_EditLocation extends Suite{
	
	public static String timestamp, screenshotPath, excelPath, browser, employeeName, reason, testName;
	public static int iTestCase, iTestData ;
	public static WebDriver driver;
	static ExtentTest logger;
	
	@BeforeClass
	public void execute_Prerequisites() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath, true);
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
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_EditUserCases);
		Log.info("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	@Test
	public void editUser() throws Exception {
		Reporter.log("The Execution started for TC_02_EditUser",true);
		Log.info("The Execution started for TC_02_EditUser");
		// WEBDRIVER AND TIMESTAMP METHOD

		Login_Page.login(iTestData,Constant.sheet_EditUserCases);
		Log.info("Logged into OrangeHRM Application");
		logger.log(Status.INFO, "Logged into OrangeHRM Application");

		Utils.screenShot(screenshotPath + "\\Edit_User.jpg", driver);
		
		Home_Page.navigateMenu("Admin", "Organization", "Locations");
		Log.info("Navigated to locations");
		logger.log(Status.INFO, "Navigated to locations");

		Admin_Page.getLocationData();
		Log.info("Edited location details");
		logger.log(Status.INFO, "Edited location details");

		Thread.sleep(3000);

		Admin_Page.validateLocation();
		Log.info("Edited location details are validated");
		logger.log(Status.INFO, "Edited location details are validated");

		employeeName = HRM_TestCase_Method.AddEmployee(Admin_Page.newLocationName, driver);
		Log.info("New employee details are added");
		logger.log(Status.INFO, "New employee details are added");

		Thread.sleep(10000);
		Home_Page.navigateMenu("Employee List");
		Log.info("Navigated to main menu");
		logger.log(Status.INFO, "Navigated to main menu");
		
		Admin_Page.verifyLocationInEmployeeList(employeeName);
		Log.info("Location is verified in Employee List");
		logger.log(Status.INFO, "Location is verified in Employee List");

		CommonMethod.logoutJaveExecuter(driver);
		Log.info("Loggedout from the OrangeHRM application");
		logger.log(Status.INFO, "Loggedout from the OrangeHRM application");
		
		
			}

	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	
	
	if(result.getStatus() == ITestResult.SUCCESS) {
	int rowCount=ExcelConfig.getRowUsed(Constant.sheet_AddEmployeeCases);
	boolean found=false;
	for(int row=1;row<rowCount;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_location, Constant.sheet_AddEmployeeCases);
		if(Admin_Page.existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(Admin_Page.newLocationName, row, Constant.col_location, Constant.sheet_AddEmployeeCases, excelPath);
			Log.info(Admin_Page.existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet");
			found=true;
		}
	}
	
	if(!found){
		Log.info(Admin_Page.existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location );
	}
	
	
	int rowCount_TC_03=ExcelConfig.getRowUsed(Constant.sheet_AddVacancyCases);
	boolean found_03=false;
	for(int row=1;row<rowCount_TC_03;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases);
		if(Admin_Page.existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(Admin_Page.newLocationName, row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases, excelPath);
			Log.info(Admin_Page.existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet" );
			found_03=true;
		}
	}
	
	if(!found_03){
		Log.info(Admin_Page.existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location );
	}
	// WRITE THE DATA IN THE EXCEL FILE.
	ExcelConfig.setCellData(Admin_Page.existingLocationName, iTestData, Constant.col_ExistingLocationName, Constant.sheet_EditUserCases,excelPath);
	Log.info("The value "+Admin_Page.existingLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases );
	
	ExcelConfig.setCellData(Admin_Page.newLocationName, iTestData, Constant.col_NewLocationName, Constant.sheet_EditUserCases,excelPath);
	Log.info("The value "+Admin_Page.newLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases );

	ExcelConfig.setCellData(employeeName, iTestData, Constant.col_NewEmployeee, Constant.sheet_EditUserCases,excelPath);
	Log.info("The value "+employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases );
	
	ExcelConfig.setCellData(Admin_Page.phoneNumber, iTestData, Constant.col_NewPhoneNo, Constant.sheet_EditUserCases,excelPath);
	Log.info("The value "+Admin_Page.phoneNumber+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName	+" in the "+Constant.sheet_EditUserCases );

	
	ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);

	
	Log.info("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status	+" in the "+Constant.sheet_TestCases );
	ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
	Log.info("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases );

	Log.info("The file are closed" );
	logger.log(Status.PASS, "Testcase " +testName+ " is passed");
	}
	else if(result.getStatus() ==ITestResult.FAILURE){
		Utils.screenShot(screenshotPath + "\\Fail.jpg", driver);
		ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Log.info("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
		ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
		Log.info(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases );
		
		logger.log(Status.FAIL, "Testcase " +testName+ " is failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath + "\\Fail.jpg").build());
		
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
