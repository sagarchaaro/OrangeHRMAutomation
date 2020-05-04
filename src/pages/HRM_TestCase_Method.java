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
		System.out.println("Executing the AddEmployee Method");
		// Plm Click
		driver.findElement(By.xpath("//span[text()='PIM']")).click();
		System.out.println("The PLM is clicked");
		
		// add employee click
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Add Employee']")));
		driver.findElement(By.xpath("//span[text()='Add Employee']")).click();
		System.out.println("Add Employee is clicked");
		// enter name
		

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		CommonMethod.TestCaseID=CommonMethod.TestCaseID.replace("TC_02", "TC_01");
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID, Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID, Constant.sheet_AddEmployeeCases);
		System.out.println("The row no of test Data is : " + iTestData);

		String firstName = ExcelConfig.getCellData(iTestData, Constant.col_firstName, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
		String middleName = RandomStringUtils.randomAlphanumeric(4);
		driver.findElement(By.xpath("//input[@id='middleName']")).sendKeys(middleName);
		String lastName = ExcelConfig.getCellData(iTestData, Constant.col_lastName, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
		System.out.println("Name entered");
		String employeeName_New = firstName.concat(" " + middleName).concat(" " + lastName);
		// enter the location
		driver.findElement(By.xpath("(//input[@class='select-dropdown'])[2]")).click();
		
		driver.findElement(By.xpath("//span[contains(text(),'" + locationName_New + "')]")).click();
		System.out.println("Location is entered: " + locationName_New);
		// click next button
		
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		System.out.println("Next button is clicked");
		 
		
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
		
		driver.findElement(By.xpath("//span[text()='"+bloodGroup+"']")).click();
		System.out.println("Blood group is entered");
		// select hobbies
		String hobbies = ExcelConfig.getCellData(iTestData, Constant.col_Hobby, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys(hobbies);
		System.out.println("Hobbies is entered");
		
		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println(" Next button is Clicked ");
		
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[text()='Region']/../div/input")));
		//driver.findElement(By.xpath("//label[text()='Region']/../div/input")).click();
		String region = ExcelConfig.getCellData(iTestData, Constant.Region, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//span[text()='"+region+"']")).click();
		System.out.println(" Region is Clicked ");
		
		String FTE = ExcelConfig.getCellData(iTestData, Constant.FTE, Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//label[text()='FTE']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+FTE+"']")).click();
		System.out.println(" 0.5 is Clicked ");
		
		String temp_Department = ExcelConfig.getCellData(iTestData, Constant.Temp_Department,Constant.sheet_AddEmployeeCases);
		driver.findElement(By.xpath("//label[text()='Temporary Department']/../div/input")).click();
		
		driver.findElement(By.xpath("//span[text()='"+temp_Department+"']")).click();
		System.out.println("Subunit is entered");
		
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Save button");
		
		System.out.println("Exit from AddEmployee Method");
		return employeeName_New;

	}
}
