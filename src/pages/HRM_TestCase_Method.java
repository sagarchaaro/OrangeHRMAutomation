package pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.CommonMethod;
import utilities.Constant;

import utilities.ExcelConfig;

public class HRM_TestCase_Method {
	public static String AddEmployee(String locationName_New, WebDriver driver) throws Exception {
		System.out.println("Executing the AddEmployee Method");
		// Plm Click
		driver.findElement(By.xpath("//html/body/div/div/div/div/div[2]/div/div/div[4]/ul/li[2]/a/span[2]")).click();
		System.out.println("The PLM is clicked");
		Thread.sleep(3000);
		// add employee click
		driver.findElement(
				By.xpath("//html/body/div/div/div/div/div[2]/div/div/div[4]/ul/li[2]/div/ul/li[3]/a/span[2]")).click();
		System.out.println("Add Employee is clicked");
		// enter name
		Thread.sleep(10000);

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,
				Constant.sheet_AddEmployeeCases);
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
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[contains(text(),'" + locationName_New + "')]")).click();
		System.out.println("Location is entered: " + locationName_New);
		// click next button
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[@id='systemUserSaveBtn']")).click();
		System.out.println("Next button is clicked");
		
		WebElement webelement_ceoEnable = driver.findElement(By.xpath("//label[text()='eeo_applicable']"));
		if (webelement_ceoEnable.isEnabled()) { 
			driver.findElement(By.xpath("//label[text()='eeo_applicable']/../div/input")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//span[text()='Native']")).click();

		} 
		// select blood group
		Thread.sleep(10000);
		driver.findElement(By.xpath("//label[text()='Blood Group']/../div/input")).click();

		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='B']")).click();
		System.out.println("Blood group is entered");
		// select hobbies
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='5']")).sendKeys("Reading Books & Playing Soccer");
		System.out.println("Hobbies is entered");
		Thread.sleep(3000);
		// next button
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println(" Next button is Clicked ");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//label[text()='Region']/../div/input")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Region-2']")).click();
		System.out.println(" Region is Clicked ");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[text()='FTE']/../div/input")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='0.5']")).click();
		System.out.println(" 0.5 is Clicked ");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[text()='Temporary Department']/../div/input")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Sub unit-1']")).click();
		System.out.println("Subunit is entered");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@class='btn waves-effect waves-light right']")).click();
		System.out.println("Save button");
		Thread.sleep(3000);
		System.out.println("Exit from AddEmployee Method");
		return employeeName_New;

	}
}
