package testCases;

import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.CommonMethod;
import utilities.Utils;
import utilities.Constant;
import utilities.ExcelConfig;

public class TC_03_AddVacancy {

	public static void main(String[] args) throws Exception {
		System.out.println("The Execution started for TC_03_AddVacancy");
		// LOAD AND READ THE PROPERTIES FILE
		CommonMethod.projectpath = System.getProperty("user.dir");
		System.out.println(CommonMethod.projectpath);
		Properties prop = CommonMethod.PropertilesRead(CommonMethod.projectpath + "\\Test-Resources\\TestInfo.properties");
		System.out.println("The Data read from Properties file.");
		System.out.println("The Testcase id executing is :"+CommonMethod.TestCaseID);

		// SETTING THE ROW NO FOR TEST CASE ID IN EXCEL FILE.

		ExcelConfig.setExcelFile(CommonMethod.PathExcel);
		int iTestCase = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_TestCases);
		System.out.println("The row no Test Case is : " + iTestCase);
		int iTestData = ExcelConfig.getRowContains(CommonMethod.TestCaseID, Constant.col_TestID,Constant.sheet_AddVacancyCases);
		System.out.println("The row no of test Data is : " + iTestData);
		String iBrowser = ExcelConfig.getCellData(iTestCase, Constant.col_Browser, Constant.sheet_TestCases);
		System.out.println("The Browser for the excecution is : " + iBrowser);

		// WEBDRIVER AND TIMESTAMP METHOD
		//String driverPath = CommonMethod.selectDriverPath(iBrowser, prop);
		WebDriver driver = Utils.OpenBrowser(prop, iBrowser);
		String timestamp = Utils.TimeStamp("YYYY-MM-dd-hhmmss");
		String screenshotPath = CommonMethod.screenshotPath + CommonMethod.TestCaseID + timestamp;
		Utils.createDir(screenshotPath);

		// LOGIN AND DASHBOARD VALDATION

		String title = driver.getTitle();
		CommonMethod.Validation("OrangeHRM", title, iTestCase);

		String userName = ExcelConfig.getCellData(iTestData, Constant.col_UserName, Constant.sheet_AddVacancyCases);
		System.out.println("The value of userName is : " + userName);
		String password = ExcelConfig.getCellData(iTestData, Constant.col_Password, Constant.sheet_AddVacancyCases);
		System.out.println("The value of password is : " + password);

		driver.findElement(By.id("txtUsername")).sendKeys(userName);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnLogin")).submit();
		System.out.println("Click on log in button ");

		try {

			driver.findElement(By.xpath("//li[text()='Dashboard']"));
			Utils.ScreenShot(screenshotPath + "\\Add_Vacancy.jpg", driver);
			System.out.println("Dashboard is avilable, Screen Print taken");

		} catch (Exception user) {
			System.out.println("Dashboard is not available, Test case is failed");
			ExcelConfig.setCellData("Fail", iTestCase, Constant.col_Status, Constant.sheet_TestCases,
					CommonMethod.PathExcel);
			ExcelConfig.setCellData("Dashboard is not available, Test case is failed", iTestCase, Constant.col_Comments,
					Constant.sheet_TestCases, CommonMethod.PathExcel);
			throw new Exception();
		}

		driver.findElement(By.xpath("//span[text()='Recruitment']")).click();
		System.out.println("Select Recruitment");
		Thread.sleep(10000);

		int size = driver.findElements(By.tagName("iframe")).size();
		System.out.println("Number of iframes are : " + size);

		// driver.switchTo().frame(0);
		// driver.switchTo().frame("noncoreIframe");
		WebElement element_iframe = driver.findElement(By.xpath("//iframe[@id='noncoreIframe']"));
		driver.switchTo().frame(element_iframe);

		System.out.println("Switched into iframe");
		driver.findElement(By.xpath("(//i[text()='add'])[1]")).click();
		System.out.println("Add or import vacancy is clicked");

		driver.findElement(By.xpath("(//i[text()='add'])[2]")).click();
		System.out.println("Add or import vacancy is clicked");

		String vacancy_Name = "Testing_" + RandomStringUtils.randomAlphabetic(6);
		driver.findElement(By.id("addJobVacancy_name")).sendKeys(vacancy_Name);
		System.out.println("Requirement is added");

		driver.findElement(By.id("textarea_addJobVacancy_jobTitle")).click();
		String vacancy_JobTitle = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_JobTitle,
				Constant.sheet_AddVacancyCases);
		driver.findElement(By.xpath("//div[@id='textarea_addJobVacancy_jobTitle']")).click();
		driver.findElement(By.xpath("(//p[text()='" + vacancy_JobTitle.trim() + "'])[1]")).click();
		System.out.println("job title is added");

		driver.findElement(By.id("textarea_addJobVacancy_location")).click();
		String vacancy_location = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_location,
				Constant.sheet_AddVacancyCases);
		System.out.println("Location is" + vacancy_location);
		driver.findElement(By.xpath("(//p[contains(text(),'" + vacancy_location.trim() + "')])[1]")).click();
		System.out.println("jobLocation is added");

		driver.findElement(By.id("textarea_addJobVacancy_sub_unit")).click();
		String subUnit = ExcelConfig.getCellData(iTestData, Constant.col_subUnit, Constant.sheet_AddVacancyCases);
		driver.findElement(By.xpath("(//p[text()='" + subUnit + "'])[1]")).click();
		System.out.println("jobSubUnit is added");

		driver.findElement(By.id("textarea_addJobVacancy_hiringManagers")).click();
		String hiringManagers = ExcelConfig.getCellData(iTestData, Constant.col_HiringManagers,
				Constant.sheet_AddVacancyCases);
		driver.findElement(By.xpath("//p[text()='" + hiringManagers + "']")).click();
		System.out.println("HiringManager is added");

		String noOfPositions = ExcelConfig.getCellData(iTestData, Constant.col_NoOfPositions,
				Constant.sheet_AddVacancyCases);
		driver.findElement(By.id("addJobVacancy_noOfPositions")).sendKeys(noOfPositions);
		System.out.println("No of positions is added");

		driver.findElement(By.id("saveVacancy")).click();
		System.out.println("Save Button is clicked");
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//i[@class='material-icons mdi-navigation-menu']")).click();

		driver.findElement(By.linkText("Home")).click();
		System.out.println("Home link is clicked");

		// LOGOUT AND CLOSING THE BROWSER.
		CommonMethod.logoutJaveExecuter(driver);
		driver.quit();
		
		// ENTERING RANDOM VACANCY NAME IN EXCEL SHEET
		
		ExcelConfig.setCellData(vacancy_Name, iTestData, Constant.col_Vacancy_name, Constant.sheet_AddVacancyCases,CommonMethod.PathExcel);
		
		
		ExcelConfig.setCellData("Pass", iTestCase, Constant.col_Status, Constant.sheet_TestCases,CommonMethod.PathExcel);
		ExcelConfig.setCellData("All step completed successfully", iTestCase, Constant.col_Comments,Constant.sheet_TestCases, CommonMethod.PathExcel);

		System.out.println("The file are closed");
	}

}
