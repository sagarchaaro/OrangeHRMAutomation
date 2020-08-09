package testngScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class AuthenticationDemo {
	@Test
	public static void test() throws Exception{
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Catalogue\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		//driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		driver.get("http://the-internet.herokuapp.com/basic_auth");
		//driver.findElement(By.xpath("//a[text()='Basic Auth']")).click();
		
		Thread.sleep(3000);
		
		//Runtime.getRuntime().exec("C:\\Selenium Catalogue\\AutoIT Files\\Authentication.exe");
		
		System.out.println("Script Execution completed");
		
	}
}
