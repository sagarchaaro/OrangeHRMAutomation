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
import utilities.Utils;

public class HRM_TestCase_Method {
	public static String excelPath;
	public static String AddEmployee(String locationName_New, WebDriver driver) throws Exception {
		Reporter.log("The Execution started for HRM_TestCase_Method",true);
		// PIM Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		Reporter.log("Click action is performed on PIM in the Menu bar",true);
		
		// add employee click
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Add Employee']")));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()='Add Employee']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		Reporter.log("Click action is performed on Add Employee in the Menu bar",true);
		// enter name
		
		excelPath = CommonMethod.projectpath+CommonMethod.getYamlData("excelPath");
		ExcelConfig.setExcelFile(excelPath);
		
		String testID=Constant.TestCaseID.replace("TC_02", "TC_01");
		int iTestCase = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_TestCases);
		Reporter.log("The row no for Test Case is : " + iTestCase,true);
		int iTestData = ExcelConfig.getRowContains(testID, Constant.col_TestID, Constant.sheet_AddEmployeeCases);
		Reporter.log("The row no for  test Data is : " + iTestData,true);

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The firstName read from excel is : " + firstName,true);
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		Reporter.log("The value "+ firstName+" is entered as firstName in the text-box",true);
		String middleName = RandomStringUtils.randomAlphanumeric(4);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		Reporter.log("The value "+ middleName+" is entered as middleName in the text-box",true);
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		Reporter.log("The lastName read from excel is : " + lastName,true);
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		Reporter.log("The value "+ lastName+" is entered as lastName in the text-box",true);
		String employeeName = firstName.concat(" " + middleName).concat(" " + lastName);
		Reporter.log("employee name is "+employeeName,true);
		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		
		driver.findElement(By.xpath("//span[contains(text(),'" + locationName_New + "')]")).click();
		Reporter.log("The value "+ locationName_New+" is selected as locationName_New in the text-box",true);
		// click next button
		
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		Reporter.log("Click action is performed on Next button",true);
		 
		
		//EEO Race and Ethnicity
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")));
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")).click();
			Reporter.log("EEO Race and Ethnicity is clicked",true);
		
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::ul[1]/li/span[text()='White']")).click();
			Reporter.log("White is selected from EEO Race and Ethnicity drop-down",true);
			
		}catch(Exception e){
			Reporter.log("EEO Race and Ethnicity is not available for selection",true);
			
		}
		// select blood group
		
		driver.findElement(By.xpath("//label[text()='Blood Group']/../div/input")).click();
		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup, Constant.sheet_AddEmployeeCases);
		Reporter.log("The bloodgroup read from excel is : " + bloodGroup,true);
		driver.findElement(By.xpath("//span[text()='"+bloodGroup+"']")).click();
		Reporter.log("The value "+ bloodGroup+" is selected as bloodgroup in the dropdown",true);
		// select hobbies
		String hobbies = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		Reporter.log("The hobby read from excel is : " + hobbies,true);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobbies);
		Reporter.log("The value "+ hobbies+" is entered as hobby in the text-box",true);
		
		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Reporter.log(" Click action is performed on Next button",true);
		
		//((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement());
		Utils.retry(driver, By.xpath("//label[text()='Region']/../div/input"), "scrollintoview");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		//driver.findElement(By.xpath("//label[text()='Region']/../div/input")).click();
		String region = ExcelConfig.getCellData(iTestData, Constant.col_Region, Constant.sheet_AddEmployeeCases);
		Reporter.log("The Region read from excel is : " + region,true);
		driver.findElement(By.xpath("//span[text()='"+region+"']")).click();
		Reporter.log("The value "+ region+" is selected as Region in the dropdown",true);
		
		String FTE = ExcelConfig.getCellData(iTestData, Constant.col_FTE, Constant.sheet_AddEmployeeCases);
		Reporter.log("The FTE read from excel is : " + FTE,true);
		driver.findElement(By.xpath("//label[text()='FTE']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+FTE+"']")).click();
		Reporter.log("The value "+ FTE+" is selected as FTE in the dropdown",true);
		
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.col_Temp_Department,Constant.sheet_AddEmployeeCases);
		Reporter.log("The temp_Department read from excel is : " + temp_Department,true);
		driver.findElement(By.xpath("//label[text()='Temporary Department']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+temp_Department+"']")).click();
		Reporter.log("The value "+ temp_Department+" is selected as temp_Department in the dropdown",true);
		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		Reporter.log("Click action is performed on Save button",true);
		
		Reporter.log("Exit from AddEmployee Method",true);
		return employeeName;

	}
}
