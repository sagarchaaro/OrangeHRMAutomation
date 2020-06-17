package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import frameworkScripts.Constant;
import utilities.ExcelConfig;
import utilities.Log;
import utilities.RandomGenerator;

public class Recruitment_Page extends BaseClass{
	
	public Recruitment_Page(WebDriver driver) {
		super(driver);
		
	}
	static By txtbx_vacancyName=By.id("addJobVacancy_name");
	static By dd_vacancyJobTitle = By.id("textarea_addJobVacancy_jobTitle");
	static By dd_vacancyLocation = By.id("textarea_addJobVacancy_location");
	static By dd_vacancySubUnit = By.id("textarea_addJobVacancy_sub_unit");
	static By dd_hiringManagers = By.id("textarea_addJobVacancy_hiringManagers");
	static By txtbx_noOfPositions = By.id("addJobVacancy_noOfPositions");
	static By btn_save = By.id("saveVacancy");
	
	public static String vacancy_Name, vacancy_JobTitle, vacancy_location, subUnit, hiringManagers, noOfPositions;
	
	public static void navigateIntoFrames() throws Exception {
		Thread.sleep(10000);

		int size = driver.findElements(By.tagName("iframe")).size();
		Log.info("Number of iframes are : " + size );

		WebElement element_iframe = driver.findElement(By.xpath("//iframe[@id='noncoreIframe']"));
		driver.switchTo().frame(element_iframe);

		Log.info("Switched into iframe" );
		
		driver.findElement(By.xpath("(//i[text()='add'])[1]")).click();
		Log.info("Click action is performed on Add or import vacancy button" );

		driver.findElement(By.xpath("(//i[text()='add'])[2]")).click();
		Log.info("Click action is performed on Add or import vacancy to Add" );
	}
	
	public static void vacancyDetails(int iTestData) throws Exception{
		vacancy_Name = "Testing_" + RandomGenerator.randomAlphabetic(6);
		driver.findElement(txtbx_vacancyName).sendKeys(vacancy_Name);
		Log.info("The value "+ vacancy_Name+" is entered as vacancy_Name in the text-box" );

		//driver.findElement(By.id("textarea_addJobVacancy_jobTitle")).click();
		vacancy_JobTitle = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_JobTitle,Constant.sheet_AddVacancyCases);
		Log.info("The vacancy_JobTitle read from excel is : " + vacancy_JobTitle );
		driver.findElement(dd_vacancyJobTitle).click();
		driver.findElement(By.xpath("(//p[text()='" + vacancy_JobTitle.trim() + "'])[1]")).click();
		Log.info("The value "+ vacancy_JobTitle+" is selected as vacancy_JobTitle in the dropdown" );

		driver.findElement(dd_vacancyLocation).click();
		vacancy_location = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_location,Constant.sheet_AddVacancyCases);
		Log.info("The vacancy_location read from excel is : " + vacancy_location );
		driver.findElement(By.xpath("(//p[contains(text(),'" + vacancy_location.trim() + "')])[1]")).click();
		Log.info("The value "+ vacancy_location+" is selected as vacancy_location in the dropdown" );

		driver.findElement(dd_vacancySubUnit).click();
		subUnit = ExcelConfig.getCellData(iTestData, Constant.col_subUnit, Constant.sheet_AddVacancyCases);
		Log.info("The subUnit read from excel is : " + subUnit );
		driver.findElement(By.xpath("(//p[text()='" + subUnit + "'])[1]")).click();
		Log.info("The value "+ subUnit+" is selected as subUnit in the dropdown" );

		driver.findElement(By.id("textarea_addJobVacancy_hiringManagers")).click();
		hiringManagers = ExcelConfig.getCellData(iTestData, Constant.col_HiringManagers,Constant.sheet_AddVacancyCases);
		Log.info("The hiringManagers read from excel is : " + hiringManagers );
		driver.findElement(By.xpath("//p[text()='" + hiringManagers + "']")).click();
		Log.info("The value "+ hiringManagers+" is selected as hiringManagers in the dropdown" );

		noOfPositions = ExcelConfig.getCellData(iTestData, Constant.col_NoOfPositions,Constant.sheet_AddVacancyCases);
		Log.info("The noOfPositions read from excel is : " + noOfPositions );
		driver.findElement(txtbx_noOfPositions).sendKeys(noOfPositions);
		Log.info("The value "+ noOfPositions+" is entered as noOfPositions in the text-box" );

		driver.findElement(btn_save).click();
		Log.info("Click action is performed on Save Button" );
		Thread.sleep(2000);
	}
	
	public static void verifyJobAndGoToMenu() throws Exception{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[text()='search']")).click();
		Log.info("Search button is clicked");
		
		driver.findElement(By.id("vacancySearch_jobVacancy")).sendKeys(vacancy_Name);
		Log.info("The value " +vacancy_Name+ " is entered in Vacancy name in the text box" );
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		Log.info("Search button is clicked");
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[@class='material-icons mdi-navigation-menu']")).click();

		driver.findElement(By.linkText("Home")).click();
		Log.info("Click action is performed on Home link" );
	}
}
