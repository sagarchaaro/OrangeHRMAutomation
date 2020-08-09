package testngScripts;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FileUploadDemo {
	@Test
	public void test() throws Exception{
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Catalogue\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://smallpdf.com/pdf-to-word");
		Thread.sleep(2000);
		
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//span[text()='Choose Files']")).click();
		Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\Selenium Catalogue\\AutoIT Files\\FileuploadConverter.exe");
		Runtime.getRuntime().exec("C:\\Selenium Catalogue\\AutoIT Files\\MultiFileUpload.exe");
		
		//String filepath="C:\\Users\\schakil\\Desktop\\WindowAutomation\\xyz.pdf";
		//ProcessBuilder pb = new ProcessBuilder("C:\\Selenium Catalogue\\AutoIT Files\\FileuploadConverter.exe",filepath);
		//ProcessBuilder pb = new ProcessBuilder("C:\\Selenium Catalogue\\AutoIT Files\\FileuploadConverter_1.exe");
		
		//pb.start();
		
		//Thread.sleep(10000);
		
		//driver.findElement(By.xpath("//input[@value='converter_normal']//following-sibling::div[1]")).click();
		
	}
}
