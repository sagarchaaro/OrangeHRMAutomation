package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import frameworkScripts.Constant;
import utilities.ExcelConfig;
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
		Reporter.log("Number of iframes are : " + size,true);

		WebElement element_iframe = driver.findElement(By.xpath("//iframe[@id='noncoreIframe']"));
		driver.switchTo().frame(element_iframe);

		Reporter.log("Switched into iframe",true);
		
		driver.findElement(By.xpath("(//i[text()='add'])[1]")).click();
		Reporter.log("Click action is performed on Add or import vacancy button",true);

		driver.findElement(By.xpath("(//i[text()='add'])[2]")).click();
		Reporter.log("Click action is performed on Add or import vacancy to Add",true);
	}
	
	public static void vacancyDetails(int iTestData) throws Exception{
		vacancy_Name = "Testing_" + RandomGenerator.randomAlphabetic(6);
		driver.findElement(txtbx_vacancyName).sendKeys(vacancy_Name);
		Reporter.log("The value "+ vacancy_Name+" is entered as vacancy_Name in the text-box",true);

		//driver.findElement(By.id("textarea_addJobVacancy_jobTitle")).click();
		vacancy_JobTitle = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_JobTitle,Constant.sheet_AddVacancyCases);
		Reporter.log("The vacancy_JobTitle read from excel is : " + vacancy_JobTitle,true);
		driver.findElement(dd_vacancyJobTitle).click();
		driver.findElement(By.xpath("(//p[text()='" + vacancy_JobTitle.trim() + "'])[1]")).click();
		Reporter.log("The value "+ vacancy_JobTitle+" is selected as vacancy_JobTitle in the dropdown",true);

		driver.findElement(dd_vacancyLocation).click();
		vacancy_location = ExcelConfig.getCellData(iTestData, Constant.col_Vacancy_location,Constant.sheet_AddVacancyCases);
		Reporter.log("The vacancy_location read from excel is : " + vacancy_location,true);
		driver.findElement(By.xpath("(//p[contains(text(),'" + vacancy_location.trim() + "')])[1]")).click();
		Reporter.log("The value "+ vacancy_location+" is selected as vacancy_location in the dropdown",true);

		driver.findElement(dd_vacancySubUnit).click();
		subUnit = ExcelConfig.getCellData(iTestData, Constant.col_subUnit, Constant.sheet_AddVacancyCases);
		Reporter.log("The subUnit read from excel is : " + subUnit,true);
		driver.findElement(By.xpath("(//p[text()='" + subUnit + "'])[1]")).click();
		Reporter.log("The value "+ subUnit+" is selected as subUnit in the dropdown",true);

		driver.findElement(By.id("textarea_addJobVacancy_hiringManagers")).click();
		hiringManagers = ExcelConfig.getCellData(iTestData, Constant.col_HiringManagers,Constant.sheet_AddVacancyCases);
		Reporter.log("The hiringManagers read from excel is : " + hiringManagers,true);
		driver.findElement(By.xpath("//p[text()='" + hiringManagers + "']")).click();
		Reporter.log("The value "+ hiringManagers+" is selected as hiringManagers in the dropdown",true);

		noOfPositions = ExcelConfig.getCellData(iTestData, Constant.col_NoOfPositions,Constant.sheet_AddVacancyCases);
		Reporter.log("The noOfPositions read from excel is : " + noOfPositions,true);
		driver.findElement(txtbx_noOfPositions).sendKeys(noOfPositions);
		Reporter.log("The value "+ noOfPositions+" is entered as noOfPositions in the text-box",true);

		driver.findElement(btn_save).click();
		Reporter.log("Click action is performed on Save Button",true);
		Thread.sleep(2000);
	}
	
	public static void verifyJobAndGoToMenu() throws Exception{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[text()='search']")).click();
		Reporter.log("Search button is clicked");
		
		driver.findElement(By.id("vacancySearch_jobVacancy")).sendKeys(vacancy_Name);
		Reporter.log("The value " +vacancy_Name+ " is entered in Vacancy name in the text box", true);
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		Reporter.log("Search button is clicked");
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//i[@class='material-icons mdi-navigation-menu']")).click();

		driver.findElement(By.linkText("Home")).click();
		Reporter.log("Click action is performed on Home link",true);
	}
}
