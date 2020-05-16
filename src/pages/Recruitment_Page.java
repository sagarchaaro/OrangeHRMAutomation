package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Recruitment_Page extends BaseClass{
	
	public Recruitment_Page(WebDriver driver) {
		super(driver);
		
	}
	By txtbx_vacancyName=By.id("addJobVacancy_name");
	By dd_vacancyJobTitle = By.id("textarea_addJobVacancy_jobTitle");
	By dd_vacancyLocation = By.id("textarea_addJobVacancy_location");
	By dd_vacancySubUnit = By.id("textarea_addJobVacancy_sub_unit");
	By dd_hiringManagers = By.id("textarea_addJobVacancy_hiringManagers");
}
