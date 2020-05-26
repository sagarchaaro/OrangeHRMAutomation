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
import pages.Admin_Page;
import pages.BaseClass;
import pages.HRM_TestCase_Method;
import pages.Home_Page;
import pages.Login_Page;

import utilities.Utils;
import utilities.ExcelConfig;

public class TC_02_EditLocation {
	
	public static String timestamp, screenshotPath, excelPath, browser, existingLocationName, newLocationName, employeeName, phoneNumber, reason;
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
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for test Data is : " + iTestData,true);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Reporter.log("The Browser for the excecution is : " + browser,true);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}
	@Test
	public void editUser() throws Exception {
		Reporter.log("The Execution started for TC_02_EditUser",true);
		// WEBDRIVER AND TIMESTAMP METHOD

		Login_Page.login(iTestData);

		Utils.screenShot(screenshotPath + "\\Edit_User.jpg", driver);
		
		Home_Page.navigateMenu("Admin", "Organization", "Locations");

		Admin_Page.getLocationData();

		Thread.sleep(3000);

		Admin_Page.validateLocation();

		employeeName = HRM_TestCase_Method.AddEmployee(newLocationName, driver);

		Thread.sleep(10000);
		Home_Page.navigateMenu("PIM","Employee List");
		
		Admin_Page.verifyLocationInEmployeeList(employeeName);

		CommonMethod.logoutJaveExecuter(driver);
		
		
			}

	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
	driver.quit();
	
	if(result.getStatus() == ITestResult.SUCCESS) {
	int rowCount=ExcelConfig.getRowUsed(Constant.sheet_AddEmployeeCases);
	boolean found=false;
	for(int row=1;row<rowCount;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_location, Constant.sheet_AddEmployeeCases);
		if(existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(newLocationName, row, Constant.col_location, Constant.sheet_AddEmployeeCases, excelPath);
			Reporter.log(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet",true);
			found=true;
		}
	}
	
	if(!found){
		Reporter.log(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location,true);
	}
	
	
	int rowCount_TC_03=ExcelConfig.getRowUsed(Constant.sheet_AddVacancyCases);
	boolean found_03=false;
	for(int row=1;row<rowCount_TC_03;row++){
		String addEmployeeLocation=ExcelConfig.getCellData(row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases);
		if(existingLocationName.equalsIgnoreCase(addEmployeeLocation)){
			ExcelConfig.setCellData(newLocationName, row, Constant.col_Vacancy_location, Constant.sheet_AddVacancyCases, excelPath);
			Reporter.log(existingLocationName+" is written as Location Name against to RowNumber "+row+" column Number"+Constant.col_location+" in Add Employee Sheet",true);
			found_03=true;
		}
	}
	
	if(!found_03){
		Reporter.log(existingLocationName+ " is not found in Add Employee sheet against to the column "+Constant.col_location,true);
	}
	// WRITE THE DATA IN THE EXCEL FILE.
	ExcelConfig.setCellData(existingLocationName, iTestData, Constant.col_ExistingLocationName, Constant.sheet_EditUserCases,excelPath);
	Reporter.log("The value "+existingLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);
	
	ExcelConfig.setCellData(newLocationName, iTestData, Constant.col_NewLocationName, Constant.sheet_EditUserCases,excelPath);
	Reporter.log("The value "+newLocationName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);

	ExcelConfig.setCellData(employeeName, iTestData, Constant.col_NewEmployeee, Constant.sheet_EditUserCases,excelPath);
	Reporter.log("The value "+employeeName+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_NewLocationName +" in the "+Constant.sheet_EditUserCases,true);
	
	ExcelConfig.setCellData(phoneNumber, iTestData, Constant.col_NewPhoneNo, Constant.sheet_EditUserCases,excelPath);
	Reporter.log("The value "+phoneNumber+" is written as phone no against to RowNumber "+iTestData +", column Number " +Constant.col_OwnerName	+" in the "+Constant.sheet_EditUserCases,true);

	
	ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);

	
	Reporter.log("Pass is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status	+" in the "+Constant.sheet_TestCases,true);
	ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
	Reporter.log("All step completed successfully is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments +" in the "+Constant.sheet_TestCases,true);

	Reporter.log("The file are closed",true);
	}
	else if(result.getStatus() ==ITestResult.FAILURE){
		ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,excelPath);
		Reporter.log("Fail is written against to RowNumber "+iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
		ExcelConfig.setCellData(reason, iTestCase, Constant.col_Comments, Constant.sheet_TestCases, excelPath);
		Reporter.log(reason +iTestCase +", column Number " +Constant.col_Status+" in the "+Constant.sheet_TestCases,true);
	}else if(result.getStatus() == ITestResult.SKIP){
		Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
	}
	
	Reporter.log("TestCase execution is completed",true);
	}

}
