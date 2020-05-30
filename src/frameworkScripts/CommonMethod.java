package frameworkScripts;


import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import org.yaml.snakeyaml.Yaml;
import utilities.ExcelConfig;

/*THE "CommonMethod" CLASS IS DEFINED FOR THE  HRM TEST CASES
 * ALL THE COMMON METHOD THAT CAN BE USED ACCROSS HRM TEST CASE, IS WRITTEN HERE
 */

public class CommonMethod {

	// CLASS VARIABLE DECLARATION
	public static String projectpath, reason;
	public static Map<String, String> yamlData;
	public static Properties prop;
	
	public static Map<String, String> loadYamlFile(String yamlFilePath) throws Exception {		
		FileInputStream fis = new FileInputStream(yamlFilePath);
		Yaml yaml=new Yaml();
		yamlData=yaml.load(fis);	
		return yamlData;		
	}
	
	public static String getYamlData(String key){
		String value=yamlData.get(key);
		return value;
	}
	
	
	/*
	 * DATA PROVIDER FOR LOGIN. IT READ THE eXCEL fILE AND STORE THE DATA IN THE 
	 * OBJECT FOR EXECUTION
	 */	
		
		@DataProvider(name="Login")
		public static Object[][] loginData() throws Exception{
			System.out.println("DATA PROVIDER Executed");
			Object[][] ob= new Object[10][3];
			int excelRow=0;
			
			for(int arryRow=0;arryRow<10;arryRow++){
				excelRow++;
				ob[arryRow][0]=ExcelConfig.getCellData(excelRow, Constant.col_UserName, Constant.sheet_Login);
				ob[arryRow][1]=ExcelConfig.getCellData(excelRow, Constant.col_Password, Constant.sheet_Login);	
				ob[arryRow][2]=ExcelConfig.getCellData(excelRow, Constant.col_TestID,Constant.sheet_Login);				
				
			}
					
			return ob;
		
	}



	/*
	 * LOAD THE PROPERTIES FILE. 
	 * 
	 */
	public static Properties getPropertyLoad(String excelPath, String propFile) throws Exception {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propFile);
		prop.load(fis);
		return prop;
		
	}
	
	/*
	 * DATA READING FROM THR PROPERTIES FILE. 
	 */
	public static String getPropertyData(String key){
		String value=prop.getProperty(key);
		return value;
	}

	/*
	 * THE METHOD "selectDropDown" WILL SELECT DATA FROM DROP DOWN INPUT TAKE AS
	 * THE WEBELEMENT AND THE VALUE TO SELECT
	 */
	public static void selectDropDown(String value, List<WebElement> elements) {

		for (WebElement ele : elements) {
			String textValue = ele.getText();
			if (value.equalsIgnoreCase(textValue)) {
				ele.click();
				break;
			}
		}
	}

	/*
	 * THE METHOD Validation TAKE TWO STRING AS INPUT AND COMPARE. IT THROWS
	 * EXCEPTION IF THE STRINGS VALUE ARE NOT EQUAL. IT ALSO WRITE THE FALIURE
	 * MESSAGE INTO THE EXCEL FILE FOR THE TEST CASE ID
	 */

	public static String verifyData(String actualValue, String expectedValue) {
		
		SoftAssert assertion= new SoftAssert();
		reason="Actual value is not matched with Expected value :"+actualValue;
		assertion.assertEquals(actualValue, expectedValue, reason);
		assertion.assertAll();
		
		return reason;

	}

	/*
	 * THE Date_HRM METHOD TAKE THE DATE IN THE FORMAT OF "MMM/DD/YYYY" SELECT
	 * THE DATE IN THE CALENDER
	 */

	public static void date_HRM(String dateToSelect, WebDriver driver, int calenderNo) {
		String date[] = dateToSelect.split("/");
		// Select Year
		driver.findElement(By.xpath("//div[@class='select-wrapper picker__select--year']/input")).click();
		driver.findElement(By.xpath("//span[text()='" + date[2] + "']")).click();
		System.out.println("The Year selected from calender is:" + date[2]);
		// Select Month
		driver.findElement(By.xpath("//div[@class='select-wrapper picker__select--month']/input")).click();
		driver.findElement(By.xpath("//span[text()='" + date[0] + "']")).click();
		System.out.println("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(By.xpath("(//table[@class='picker__table'])[" + calenderNo + "]/tbody/tr")).size();
		int cols = driver.findElements(By.xpath("(//table[@class='picker__table'])[" + calenderNo + "]/tbody/tr[1]/td")).size();
		System.out.println("The no of cols is:" + cols + ", The no of rows is:" + rows);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
		for (int colsNo = 1; colsNo <= cols; colsNo++) {

				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table'])[" + calenderNo+ "]/tbody/tr[" + rowNo + "]/td[" + colsNo + "]/div")).getText();
				if (calDate.equalsIgnoreCase(date[1])) {
					driver.findElement(By.xpath("(//table[@class='picker__table'])[" + calenderNo + "]/tbody/tr["+ rowNo + "]/td[" + colsNo + "]/div")).click();
					System.out.println("The Date is selected from calender is:" + dateToSelect);
					break rows;
				}

			}
		}

	}
	
	public static void date_HRM_08(String dateToSelect, WebDriver driver, int calenderNo) {
		String date[] = dateToSelect.split("/");
		// Select Year
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[" + calenderNo + "]")).click();
		WebElement element = driver.findElement(By.xpath("//span[text()='" + date[2] + "']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		System.out.println("The Year selected from calender is:" + date[2]);
		// Select Month
		driver.findElement(By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[" + calenderNo + "]")).click();
		driver.findElement(By.xpath("//span[text()='" + date[0] + "']")).click();
		System.out.println("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(By.xpath("(//table[@class='picker__table'])[" + calenderNo + "]/tbody/tr")).size();
		int cols = driver.findElements(By.xpath("(//table[@class='picker__table'])[" + calenderNo + "]/tbody/tr[1]/td")).size();
		System.out.println("The no of cols is:" + cols + ", The no of rows is:" + rows);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
			System.out.println("entered row for loop");
		for (int colsNo = 1; colsNo <= cols; colsNo++) {
			System.out.println("entered column for loop");
				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr[" + rowNo + "]/td[" + colsNo + "]/div)[2]")).getText();
				System.out.println(calDate);
				if (calDate.equalsIgnoreCase(date[1])) {
					System.out.println("entered if loop");
					driver.findElement(By.xpath("(//table[@class='picker__table']/tbody/tr["+ rowNo + "]/td[" + colsNo + "]/div)[2]")).click();
					System.out.println("The Date is selected from calender is:" + dateToSelect);
					break rows;
				}

			}
		}

	}
	
	
	/*
	 * THE METHOD "logout" TO LOGOUT FROM THE HOME PAGE WEBDRIVER TAKE AS INPUT
	 */
	public static void logoutJaveExecuter(WebDriver driver) throws Exception {

		WebElement element = driver.findElement(By.xpath("//*[@id='account-job']/i"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		Thread.sleep(3000);
		driver.findElement(By.id("logoutLink")).click();
		System.out.println("Click action is performed on Logout button");
	}

}
