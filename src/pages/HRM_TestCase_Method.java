package pages;

import org.apache.commons.lang3.RandomStringUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import frameworkScripts.CommonMethod;
import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.Utils;

public class HRM_TestCase_Method {
	public static String excelPath;
	public static String AddEmployee(String locationName_New, WebDriver driver) throws Exception {
		Log.info("The Execution started for HRM_TestCase_Method" );
		// PIM Click
		WebDriverWait wait = new WebDriverWait(driver, 30);

		Thread.sleep(3000);
		
		Home_Page.navigateMenu("PIM", "Add Employee");
		
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");
		ExcelConfig.setExcelFile(excelPath);
		
		String testID=Constant.TestCaseID.replace("TC_02", "TC_01");
		int iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_TestCases);
		Log.info("The row no for Test Case is : " + iTestCase );
		int iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_AddEmployeeCases);
		Log.info("The row no for  test Data is : " + iTestData );

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		Log.info("The firstName read from excel is : " + firstName );
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		Log.info("The value "+ firstName+" is entered as firstName in the text-box" );
		String middleName = RandomStringUtils.randomAlphanumeric(4);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		Log.info("The value "+ middleName+" is entered as middleName in the text-box" );
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		Log.info("The lastName read from excel is : " + lastName );
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		Log.info("The value "+ lastName+" is entered as lastName in the text-box" );
		String employeeName = firstName.concat(" " + middleName).concat(" " + lastName);
		Log.info("employee name is "+employeeName );
		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		
		driver.findElement(By.xpath("//span[contains(text(),'" + locationName_New + "')]")).click();
		Log.info("The value "+ locationName_New+" is selected as locationName_New in the text-box" );
		// click next button
		
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		Log.info("Click action is performed on Next button" );
		 
		
		//EEO Race and Ethnicity
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")));
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")).click();
			Log.info("EEO Race and Ethnicity is clicked" );
		
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::ul[1]/li/span[text()='White']")).click();
			Log.info("White is selected from EEO Race and Ethnicity drop-down" );
			
		}catch(Exception e){
			Log.info("EEO Race and Ethnicity is not available for selection" );
			
		}
		// select blood group
		
		driver.findElement(By.xpath("//label[text()='Blood Group']/../div/input")).click();
		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup, Constant.sheet_AddEmployeeCases);
		Log.info("The bloodgroup read from excel is : " + bloodGroup );
		driver.findElement(By.xpath("//span[text()='"+bloodGroup+"']")).click();
		Log.info("The value "+ bloodGroup+" is selected as bloodgroup in the dropdown" );
		// select hobbies
		String hobbies = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		Log.info("The hobby read from excel is : " + hobbies );
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobbies);
		Log.info("The value "+ hobbies+" is entered as hobby in the text-box" );
		
		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Log.info(" Click action is performed on Next button" );
		
		Thread.sleep(10000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Region']/../div/input")));
		Utils.retry(driver, By.xpath("//label[text()='Region']/../div/input"), "scrollintoview");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		String region = ExcelConfig.getCellData(iTestData, Constant.col_Region, Constant.sheet_AddEmployeeCases);
		Log.info("The Region read from excel is : " + region );
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+region+"']")));
		driver.findElement(By.xpath("//span[text()='"+region+"']")).click();
		Log.info("The value "+ region+" is selected as Region in the dropdown" );
		
		String FTE = ExcelConfig.getCellData(iTestData, Constant.col_FTE, Constant.sheet_AddEmployeeCases);
		Log.info("The FTE read from excel is : " + FTE );
		driver.findElement(By.xpath("//label[text()='FTE']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+FTE+"']")).click();
		Log.info("The value "+ FTE+" is selected as FTE in the dropdown" );
		
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.col_Temp_Department,Constant.sheet_AddEmployeeCases);
		Log.info("The temp_Department read from excel is : " + temp_Department );
		driver.findElement(By.xpath("//label[text()='Temporary Department']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+temp_Department+"']")).click();
		Log.info("The value "+ temp_Department+" is selected as temp_Department in the dropdown" );
		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Log.info("Click action is performed on Save button" );
		
		Log.info("Exit from AddEmployee Method" );
		return employeeName;

	}
}
