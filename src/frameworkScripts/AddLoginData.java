package frameworkScripts;

import java.io.FileInputStream;
import java.util.List;

import java.util.Map;

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
import utilities.RandomGenerator;
import utilities.Utils;

public class AddLoginData {
		public static String timestamp, excelPath, screenshotPath, browser, reason, newPassword;
		public static Map<String, String> yaml, userName, passWord;
		public static int iTestCase, iTestData;
		public static WebDriver driver;

	@BeforeClass
	public void execute_Prerequisites() throws Exception {
		CommonMethod.projectpath = System.getProperty("user.dir");
		Reporter.log("The Project Path is:"+CommonMethod.projectpath,true);
		
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		
		screenshotPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		screenshotPath = CommonMethod.projectpath+ screenshotPath + timestamp;
		Utils.createDir(screenshotPath);
	}

	@BeforeMethod()
	public void browserLaunch(@Optional(Constant.TestCaseID) String testID) throws Exception {

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
		Reporter.log("The value Admin is entered as userName in the text-box", true);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		Reporter.log("The value Admin@123 is entered as password in the text-box", true);
		driver.findElement(By.id("btnLogin")).submit();
		Reporter.log("Click action is performed on Login button for Admin", true);
		// CHANGING USER PASSWORD

		driver.findElement(By.xpath("//span[text()='Admin']")).click();
		Reporter.log("Click action is performed on Admin in the Menu bar", true);

		driver.findElement(By.xpath("//span[text()='User Management']")).click();
		Reporter.log("Click action is performed on User Management in the Menu bar", true);

		driver.findElement(By.xpath("//span[text()='Users']")).click();
		Reporter.log("Click action is performed on Users in the Menu bar", true);

		Thread.sleep(5000);
		List<WebElement> employeeName = driver
				.findElements(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span"));
		Reporter.log("All EmployeeName are stored in the WebElement", true);
		String[] empName = Utils.dataIntoArray(employeeName, 50);
		Reporter.log("All EmployeeName are stored in the Array", true);
		int rowCount=ExcelConfig.getRowUsed(Constant.sheet_Login);
		System.out.println("rowCount" +rowCount);
		int datacount=1;
		iTestData=1;
		nextUser:while (datacount <= 10){
			String newUser = Utils.selectWithRandomIndex(50, empName);
			Reporter.log("The EmployeeName is selected by random no is :" + newUser, true);
			for(int row=1;row<rowCount;row++){
				String existingUser=ExcelConfig.getCellData(row, Constant.col_UserName, Constant.sheet_Login);
				System.out.println("existingUser" +existingUser);
				if(newUser.equalsIgnoreCase(existingUser)){
					//ExcelConfig.setCellData(employee_Name, iTestData, Constant.col_NewUserName, Constant.sheet_Login,CommonMethod.pathExcel);
					Reporter.log("The value "+newUser+" is already set up");
					continue nextUser;
				}

			}			
		//	driver.findElement(By.xpath("//span[text()='Users']")).click();
		//	Reporter.log("Click action is performed on Users in the Menu bar", true);
			Thread.sleep(5000);
			WebElement element = driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"+ newUser + "']/../../../td[8]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			
		//	driver.findElement(By.xpath("//table[@class='highlight bordered']/tbody/tr/td[2]/ng-include/span[text()='"+ newUser + "']/../../../td[8]")).click();
			Reporter.log("Click action is performed on Edit Link", true);

			driver.findElement(By.xpath("//label[@for='changepassword']")).click();
			Reporter.log("Click action is performed  on change password checkbox", true);
		
			newPassword = RandomGenerator.randomAlphaNumeric(8);
			Reporter.log("New password is :"+newPassword);

			driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[8]")).sendKeys(newPassword);
			Reporter.log("The value " + newPassword + " is entered as password in the text-box", true);

			driver.findElement(By.xpath("(//div[@id='modal1']/form//child::input)[9]")).sendKeys(newPassword);
			Reporter.log("The value " + newPassword + " is entered as Confirm password in the text-box", true);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a[text()='Save']")).click();		
			
			Reporter.log("Click action is performed on Save button", true);	
			Thread.sleep(5000);
			ExcelConfig.setCellData(newPassword, iTestData, Constant.col_Password, Constant.sheet_Login,excelPath);
			Reporter.log(newPassword+ "is written as password against to RowNumber "+iTestData +", column Number " +Constant.col_Password +" in the "+Constant.sheet_Login,true);
			ExcelConfig.setCellData(newUser, iTestData, Constant.col_UserName, Constant.sheet_Login,excelPath);
			Reporter.log(newUser+ "is written as newUser against to RowNumber "+iTestData +", column Number " +Constant.col_UserName +" in the "+Constant.sheet_Login,true);
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
			Reporter.log("All test data written into the excel file", true);	
		}else if(result.getStatus() ==ITestResult.FAILURE){
			Reporter.log("All test data written into the excel file", true);
		}else if(result.getStatus() == ITestResult.SKIP){
			Reporter.log("Testcase is Skipped with the reason as :"+reason,true);
		}
		
		Reporter.log("TestCase execution is completed",true);

	}

}

