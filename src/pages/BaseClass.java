package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	public static WebDriver driver;
	public static WebDriverWait wait;
		public BaseClass(WebDriver driver){
		this.driver=driver;
		wait = new WebDriverWait(this.driver,30);
		
	}
}
