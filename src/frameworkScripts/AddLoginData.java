package frameworkScripts;

import java.io.FileInputStream;
import java.util.List;

import java.util.Map;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import pages.BaseClass;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.RandomGenerator;
import utilities.Utils;

public class AddLoginData {
		public static String timestamp, excelPath, screenshotPath, browser, reason, newPassword, testName;
		public static Map<String, String> yaml, userName, passWord;
		public static int iTestCase, iTestData;
		public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception {
		CommonMethod.projectpath = System.getProperty("user.dir");
		DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		Log.info("The Project Path is:"+CommonMethod.projectpath);
		
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		screenshotPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.projectpath+ screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
	}

	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception {
		testName=Thread.currentThread().getStackTrace()[1].getClassName().substring(Thread.currentThread().getStackTrace()[1].getClassName().indexOf('.')+1)+"_"+testID;
		Log.startTestCase(testName);
		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.
		Reporter.log("The AddLoginData test case is executing",true);
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");		
		ExcelConfig.setExcelFile(excelPath);
		Log.info("The Testcase id executing is :"+testID);
		iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase);
		iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID,Constant.sheet_AddEmployeeCases);
		Log.info("The row no for test Data is : " + iTestData);
		browser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		Log.info("The Browser for the excecution is : " + browser);
		driver = Utils.openBrowser(CommonMethod.yamlData, browser);
		new BaseClass(driver);
	}

	@Test
	public void addLogin()throws Exception {
		// LOGIN AND DASHBOARD VALDATION
		String title = driver.getTitle();
		CommonMethod.verifyData(title, "OrangeHRM");
		String projPath=System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(projPath+"\\test-resources\\test-info.yaml");
		
		Yaml yaml=new Yaml();
		Map<String, String> map=yaml.load(fis);
		
		String username=map.get("userName");
		System.out.println(username);
		
		String password=map.get("password");
		System.out.println(password);
		driver.findElement(By.id("txtUsername")).sendKeys(username);
		Log.info("The value Admin is entered as userName in the text-box");
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Log.info("The value Admin@123 is entered as password in the text-box");
		driver.findElement(By.id("btnLogin")).submit();
		Log.info("Click action is performed on Login button for Admin");
		// CHANGING USER PASSWORD

		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		Log.info("Click action is performed on Admin in the Menu bar");

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		Log.info("Click action is performed on User Management in the Menu bar");

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		Log.info("Click action is performed on Users in the Menu bar");

		Thread.sleep(5000);
		List<WebElement> employeeName = driver
				.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		Log.info("All EmployeeName are stored in the WebElement");
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		Log.info("All EmployeeName are stored in the Array");
		int rowCount=ExcelConfig.getRowUsed(Constant.sheet_Login);
		System.out.println("rowCount" +rowCount);
		int datacount=1;
		iTestData=1;
		nextUser:while (datacount <= 10){
			String newUser = Utils.selectWithRandomIndex(50, empName);
			Log.info("The EmployeeName is selected by random no is :" + newUser);
			for(int row=1;row<rowCount;row++){
				String existingUser=ExcelConfig.getCellData(row, Constant.col_UserName, Constant.sheet_Login);
				System.out.println("existingUser" +existingUser);
				if(newUser.equalsIgnoreCase(existingUser)){
					//ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_NewUserName, Constant.sheet_Login,CommonMethod.pathExcel);
					Log.info("The value "+newUser+" is already set up");
					continue nextUser;
				}

			}			
		//	driver.findElement(By.xpath("//span[text()='Users']")).click();
		//	Log.info("Click action is performed on Users in the Menu bar");
			Thread.sleep(5000);
			WebElement element = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"+ newUser + "']/../../../td[8]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			
		//	driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"+ newUser + "']/../../../td[8]")).click();
			Log.info("Click action is performed on Edit Link");

			driver.findElement(By.xpath("//label[@for='changepassword']")).click();
			Log.info("Click action is performed  on change password checkbox");
		
			newPassword = RandomGenerator.randomAlphaNumeric(8);
			Log.info("New password is :"+newPassword);

			driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(newPassword);
			Log.info("The value " + newPassword + " is entered as password in the text-box");

			driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(newPassword);
			Log.info("The value " + newPassword + " is entered as Confirm password in the text-box");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a[text()='Save']")).click();		
			
			Log.info("Click action is performed on Save button");	
			Thread.sleep(5000);
			ExcelConfig.setCellData(newPassword, iTestData, Constant.col_Password, Constant.sheet_Login,excelPath);
			Log.info(newPassword+ "is written as password against to RowNumber "+iTestData +", column Number " +Constant.col_Password +" in the "+Constant.sheet_Login);
			ExcelConfig.setCellData(newUser, iTestData, Constant.col_UserName, Constant.sheet_Login,excelPath);
			Log.info(newUser+ "is written as newUser against to RowNumber "+iTestData +", column Number " +Constant.col_UserName +" in the "+Constant.sheet_Login);
			iTestData++;	
			datacount++;
			System.out.println(datacount);
		}
		System.out.println("out of while loop");
		Thread.sleep(5000);
		CommonMethod.logoutJaveExecuter(driver);
	}
	@AfterMethod
	public void tearDown(ITestResult result) throws Exception{
		driver.quit();
		// ENTERING IN EXCEL SHEET
		
		if(result.getStatus() == ITestResult.SUCCESS){
			Log.info("All test data written into the excel file");	
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Log.info("All test data written into the excel file");
		}else if(result.getStatus() == ITestResult.SKIP){
			Log.info("Testcase is Skipped with the reason as :"+reason);
		}
		
		Reporter.log("TestCase execution is completed",true);
		Log.endTestCase();

	}

}

