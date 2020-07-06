package frameworkScripts;


import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import org.yaml.snakeyaml.Yaml;
import utilities.ExcelConfig;
import utilities.Log;

/*THE "CommonMethod" CLASS IS DEFINED FOR THE  HRM TEST CASES
 * ALL THE COMMON METHOD THAT CAN BE USED ACCROSS HRM TEST CASE, IS WRITTEN HERE
 */

public class CommonMethod {
	

	static By link_year = By.xpath("//div[@class='select-wrapper picker__select--year']/input");
	static By dd_select=By.xpath("//span[text()='{0}']");
	static By link_month = By.xpath("//div[@class='select-wrapper picker__select--month']/input");
	static By size_row = By.xpath("(//table[@class='picker__table'])[{0}]/tbody/tr");
	static By size_cols = By.xpath("(//table[@class='picker__table'])[{0}]/tbody/tr[1]/td");
	static By txt_calDate = By.xpath("(//table[@class='picker__table'])[{0}]/tbody/tr[{0}]/td[{0}]/div");
	static By link_year_08 = By.xpath("(//div[@class='select-wrapper picker__select--year']/input)[{0}]");
	static By link_month_08 = By.xpath("(//div[@class='select-wrapper picker__select--month']/input)[{0}]");
	static By link_logout = By.xpath("//*[@id='account-job']/i");
	static By click_logout = By.id("logoutLink");

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
			Log.info("DATA PROVIDER Executed");
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
		driver.findElement(link_year).click();
		driver.findElement(CommonMethod.formatLocator(dd_select, date[2])).click();		
		Log.info("The Year selected from calender is:" + date[2]);
		// Select Month
		driver.findElement(link_month).click();
		driver.findElement(CommonMethod.formatLocator(dd_select, date[0])).click();	
		
		Log.info("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(CommonMethod.formatLocator(size_row, calenderNo)).size();
		int cols = driver.findElements(CommonMethod.formatLocator(size_cols, calenderNo)).size();
		Log.info("The no of cols is:" + cols + ", The no of rows is:" + rows);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
		for (int colsNo = 1; colsNo <= cols; colsNo++) {

		//*		String calDate = driver.findElement(CommonMethod.formatLocator(txt_calDate, calenderNo, rowNo, colsNo )).getText();
				String calDate = driver.findElement(By.xpath("(//table[@class='picker__table'])["+calenderNo+"]/tbody/tr["+rowNo+"]/td["+colsNo+"]/div")).getText();
				
				if (calDate.equalsIgnoreCase(date[1])) {
					driver.findElement(By.xpath("(//table[@class='picker__table'])["+calenderNo+"]/tbody/tr["+rowNo+"]/td["+colsNo+"]/div")).click();
				//*	driver.findElement(CommonMethod.formatLocator(txt_calDate, calenderNo, rowNo, colsNo )).click();
					Log.info("The Date is selected from calender is:" + dateToSelect);
					break rows;
				}

			}
		}

	}
	
	public static void date_HRM_08(String dateToSelect, WebDriver driver, int calenderNo) {
		String date[] = dateToSelect.split("/");
		// Select Year
		driver.findElement(CommonMethod.formatLocator(link_year_08,calenderNo )).click();
		WebElement element = driver.findElement(CommonMethod.formatLocator(dd_select, date[2]));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		Log.info("The Year selected from calender is:" + date[2]);
		// Select Month
		driver.findElement(CommonMethod.formatLocator(link_month_08,calenderNo )).click();
		driver.findElement(CommonMethod.formatLocator(dd_select, date[0])).click();
		Log.info("The Month selected from calender is:" + date[0]);
		// Select Date
		int rows = driver.findElements(CommonMethod.formatLocator(size_row, calenderNo)).size();
		int cols = driver.findElements(CommonMethod.formatLocator(size_cols, calenderNo)).size();
		Log.info("The no of cols is:" + cols + ", The no of rows is:" + rows);
		rows: for (int rowNo = 1; rowNo <= rows; rowNo++) {
			Log.info("entered row for loop");
		for (int colsNo = 1; colsNo <= cols; colsNo++) {
			Log.info("entered column for loop");
				String calDate = driver.findElement(CommonMethod.formatLocator(txt_calDate, calenderNo, rowNo, colsNo )).getText();
				Log.info(calDate);
				if (calDate.equalsIgnoreCase(date[1])) {
					Log.info("entered if loop");
					driver.findElement(CommonMethod.formatLocator(txt_calDate, calenderNo, rowNo, colsNo )).click();
					Log.info("The Date is selected from calender is:" + dateToSelect);
					break rows;
				}

			}
		}

	}
	
	
	/*
	 * THE METHOD "logout" TO LOGOUT FROM THE HOME PAGE WEBDRIVER TAKE AS INPUT
	 */
	public static void logoutJaveExecuter(WebDriver driver) throws Exception {
		Thread.sleep(2000);
		WebElement element = driver.findElement(link_logout);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		Thread.sleep(3000);
		driver.findElement(click_logout).click();
		Log.info("Click action is performed on Logout button");
	}
	
	public static By formatLocator(By by, final Object... substitutions) {
		By returnBy;
		final String locator = by.toString().split(":")[0];
		String locatorValue = by.toString().replaceAll(locator, "").replaceFirst(":", "").trim();

		Pattern pattern = Pattern.compile("([{][0-9]+[}])");
		Matcher matcher = pattern.matcher(locatorValue);
		int count = 0;
		while (matcher.find()) {
			++count;
		}
		
		for (int i = 0; i < count; ++i) {
			pattern = Pattern.compile("([{]" + i + "[}])");
			matcher = pattern.matcher(locatorValue);
			locatorValue = matcher.replaceAll(Matcher.quoteReplacement(substitutions[i].toString()));
		}
		
		switch (locator) {
		case "By.cssSelector":
			returnBy = By.cssSelector(locatorValue);
			break;
		case "By.xpath":
			returnBy = By.xpath(locatorValue);
			break;
		case "By.className":
			returnBy = By.className(locatorValue);
			break;
		case "By.id":
			returnBy = By.id(locatorValue);
			break;
		case "By.tagName":
			returnBy = By.tagName(locatorValue);
			break;
		case "By.name":
			returnBy = By.name(locatorValue);
			break;
		case "By.linkText":
			returnBy = By.linkText(locatorValue);
			break;
		case "By.partialLinkText":
			returnBy = By.partialLinkText(locatorValue);
			break;
		default:
			throw new RuntimeException("invalid locator: " + by.toString());
		}
		return returnBy;
	}


}
