package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonMethod;
import utilities.Constant;

import utilities.ExcelConfig;

public class HRM_TestCase_Method {
	public static String AddEmployee(String locationName_New, WebDriver driver) throws Exception {
		System.out.println("The Execution started for HRM_TestCase_Method");
		// Plm Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		System.out.println("Click action is performed on PLM in the Menu bar");
		
		// add employee click
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Add Employee']")));
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		System.out.println("Click action is performed on Add Employee in the Menu bar");
		// enter name
		

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		CommonMethod.TestCaseID=CommonMethod.TestCaseID.replace("TC_02", "TC_01");
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID, Constant.sheet_TestCases);
		System.out.println("The row no for Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID, Constant.sheet_AddEmployeeCases);
		System.out.println("The row no for  test Data is : " + iTestData);

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		System.out.println("The firstName read from excel is : " + firstName);
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		System.out.println("The value "+ firstName+" is entered as firstName in the text-box");
		String middleName = RandomStringUtils.randomAlphanumeric(4);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		System.out.println("The value "+ middleName+" is entered as middleName in the text-box");
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		System.out.println("The lastName read from excel is : " + lastName);
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		System.out.println("The value "+ lastName+" is entered as lastName in the text-box");
		String employeeName_New = firstName.concat(" " + middleName).concat(" " + lastName);
		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		
		driver.findElement(By.xpath("//span[contains(text(),'" + locationName_New + "')]")).click();
		System.out.println("The value "+ locationName_New+" is selected as locationName_New in the text-box");
		// click next button
		
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		System.out.println("Click action is performed on Next button");
		 
		
		//EEO Race and Ethnicity
		try{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")));
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::input[1]")).click();
			System.out.println("EEO Race and Ethnicity is clicked");
		
			driver.findElement(By.xpath("//label[text()='EEO Race and Ethnicity']//parent::div//child::ul[1]/li/span[text()='White']")).click();
			System.out.println("White is selected from EEO Race and Ethnicity drop-down");
			
		}catch(Exception e){
			System.out.println("EEO Race and Ethnicity is not available for selection");
			
		}
		// select blood group
		
		driver.findElement(By.xpath("//label[text()='Blood Group']/../div/input")).click();
		String bloodGroup = ExcelConfig.getCellData(iTestData, Constant.col_Bloodgroup, Constant.sheet_AddEmployeeCases);
		System.out.println("The bloodgroup read from excel is : " + bloodGroup);
		driver.findElement(By.xpath("//span[text()='"+bloodGroup+"']")).click();
		System.out.println("The value "+ bloodGroup+" is selected as bloodgroup in the dropdown");
		// select hobbies
		String hobbies = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		System.out.println("The hobby read from excel is : " + hobbies);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobbies);
		System.out.println("The value "+ hobbies+" is entered as hobby in the text-box");
		
		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println(" Click action is performed on Next button");
		
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		//driver.findElement(By.xpath("//label[text()='Region']/../div/input")).click();
		String region = ExcelConfig.getCellData(iTestData, Constant.Region, Constant.sheet_AddEmployeeCases);
		System.out.println("The Region read from excel is : " + region);
		driver.findElement(By.xpath("//span[text()='"+region+"']")).click();
		System.out.println("The value "+ region+" is selected as Region in the dropdown");
		
		String FTE = ExcelConfig.getCellData(iTestData, Constant.FTE, Constant.sheet_AddEmployeeCases);
		System.out.println("The FTE read from excel is : " + FTE);
		driver.findElement(By.xpath("//label[text()='FTE']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+FTE+"']")).click();
		System.out.println("The value "+ FTE+" is selected as FTE in the dropdown");
		
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.Temp_Department,Constant.sheet_AddEmployeeCases);
		System.out.println("The temp_Department read from excel is : " + temp_Department);
		driver.findElement(By.xpath("//label[text()='Temporary Department']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+temp_Department+"']")).click();
		System.out.println("The value "+ temp_Department+" is selected as temp_Department in the dropdown");
		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Click action is performed on Save button");
		
		System.out.println("Exit from AddEmployee Method");
		return employeeName_New;

	}
}
