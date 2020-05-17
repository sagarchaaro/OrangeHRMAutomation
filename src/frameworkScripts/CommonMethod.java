package frameworkScripts;


import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.yaml.snakeyaml.Yaml;
import utilities.ExcelConfig;

/*THE "CommonMethod" CLASS IS DEFINED FOR THE  HRM TEST CASES
 * ALL THE COMMON METHOD THAT CAN BE USED ACCROSS HRM TEST CASE, IS WRITTEN HERE
 */

public class CommonMethod {

	// CLASS VARIABLE DECLARATION

	public static String pathExcel;
	public static String url;
	public static String screenshotPath;
	public static String projectpath;
	
	
	/*
	 * DATA READING FROM THR YAML FILE. This will read the data into the
	 * class variable input: properties file path It return the Properties
	 * "prop" THROW EXCEPTION FOR FILE NOT FOUND
	 */
	public static Map<String, String> yamlFileRead(String yamlFilePath) throws Exception {
		
		FileInputStream fis = new FileInputStream(yamlFilePath);

		Yaml yaml=new Yaml();
		Map<String, String> map=yaml.load(fis);
		pathExcel = projectpath.concat(map.get("PathExcel"));
		url = map.get("orangeHRMURL");
		screenshotPath = projectpath.concat(map.get("screenshotPath"));
		System.out.println("The Yaml file is read in the method yamlFileRead");	
		System.out.println("The excel Path read is:"+CommonMethod.pathExcel);
		System.out.println("The url read is:"+CommonMethod.url);
		System.out.println("The screenshotPath read is:"+CommonMethod.screenshotPath);		
		return map;
		
	}


	/*
	 * DATA READING FROM THR PROPERTIES FILE. This will read the data into the
	 * class variable input: properties file path It return the Properties
	 * "prop" THROW EXCEPTION FOR FILE NOT FOUND
	 */
	public static Properties propertilesRead(String propFile) throws Exception {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propFile);
		prop.load(fis);
		pathExcel = projectpath.concat(prop.getProperty("PathExcel"));
		url = prop.getProperty("orangehrmURL");
		screenshotPath = projectpath.concat(prop.getProperty("screenshotPath"));
		System.out.println("The Properties file is read in the method propertilesRead");
		return prop;
		
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

	public static void validation(String string1, String string2, int iTestCase) throws Exception {

		if (string1.equalsIgnoreCase(string2)) {
			System.out.println("The string " + string2 + " is verified with "+string1);
		} else {
			System.out.println("The string " + string2 + " is not verified with "+string1);
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases, pathExcel);
			System.out.println("Fail is written as Status against to RowNumber "+iTestCase +", column Number " +Constant.col_Status
					+" in the "+Constant.sheet_TestCases);
			ExcelConfig.setCellData(string2 + " is not validated with , Test case is failed", iTestCase,
					Constant.col_Comments, Constant.sheet_TestCases, pathExcel);
			System.out.println(string2 + " is not validated with , Test case is failed is written as comment against to RowNumber "+iTestCase +", column Number " +Constant.col_Comments
					+" in the "+Constant.sheet_TestCases);

			throw new Exception();

		}

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
