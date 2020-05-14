package utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	
	/*
	 * BASIC WEBDRIVER LUNCH STEPS FOR ALL WEBDRIVER
	 * IT READING PROPERTIES FILE FOR DRIVER PATH AND GETTING BROWSER AS ARGUMENT 
	 * IT SET DERVER, LUNCH THE URL AND IMPLICITY TIME AND MAXIMISE THE SCREEN
	 */
	public static WebDriver openBrowser(Properties prop, String browser) throws Exception{
		browser.trim();
		WebDriver driver;
		if (browser.equalsIgnoreCase("Chrome")) {
			String path = prop.getProperty("chromePath");
			System.setProperty("webdriver.chrome.driver", path);
			driver= new ChromeDriver();
			System.out.println("Chrome Browser is launched");
		}else if(browser.equalsIgnoreCase("Firefox")) {
			String path = prop.getProperty("geckoPath");
			System.setProperty("webdriver.gecko.driver", path);
			driver = new FirefoxDriver();			
			System.out.println("Firefox browser is launched");
		}else if(browser.equalsIgnoreCase("Opera")){
			String path = prop.getProperty("operaPath");
			System.setProperty("webdriver.opera.driver", path);
			driver = new OperaDriver();
			System.out.println("Opera brower is launched");
		}else if(browser.equalsIgnoreCase("Edge")){
			String path = prop.getProperty("internateExplorePath");
			System.setProperty("webdriver.edge.driver", path);
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			System.out.println("Edge browser is launched");
		}else if(browser.equalsIgnoreCase("InternateExplore")){
			String path = prop.getProperty("edgePath");
			System.setProperty("webdriver.ie.driver", path);
			driver = new InternetExplorerDriver();
			System.out.println("InternateExplore browser is launched");
		}else{
			System.out.println("Invalid Browser "+browser);
			throw new Exception();
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(CommonMethod.url);
		driver.manage().window().maximize();
		return driver;
	
	}

	// COMMON METHOD TO GET TIMESTAMP

	public static String timeStamp(String requiredFormat) {
		DateFormat df = new SimpleDateFormat(requiredFormat);
		Date date1 = new Date();
		String timestamp = df.format(date1);
		System.out.println("The value of TIMESTAMP is : " + timestamp);
		return timestamp;
	}

	// Take Screen shot

	public static void screenShot(String filename, WebDriver driver) throws IOException {
		File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcfile, new File(filename));
	}

	/*
	 * THE METHODE READ THE LIST OF WEBELEMENT AND WRITE INTO THE ARRAY
	 */
	public static String[] dataIntoArray(List<WebElement> elements, int arraySize) {

		int indexNo = 0;
		String storedValue[] = new String[arraySize];

		for (WebElement ele : elements) {
			storedValue[indexNo] = ele.getText();
			indexNo++;
		}
		return storedValue;
	}

	/*
	 * SELECT RANDOM DATA FROM THE ARRAY IT TAKE THE INPUT AS THE RANDOM NO
	 * RANGE TO CREATE
	 */

	public static String selectWithRandomIndex(int randomNoSize, String[] storedValue) {
		Random random = new Random();
		int RandomNo = random.nextInt(randomNoSize);
		String returnValue = storedValue[RandomNo];
		return returnValue;
	}

	/*
	 * CREATE A FOLDER IN GIVEN PATH IT TAKE DIRECTORY PATH AS INPUT
	 */

	public static void createDir(String dirPath) {
		File file = new File(dirPath);
		boolean bool = file.mkdir();
		if (bool) {
			System.out.println("The Directory created successfully");
		} else {
			System.out.println("The specified directory couldn't created");
		}
	}
	
	public static void retry(WebDriver driver, By by, String arg) throws Exception{		
		int attempts = 0;
		while(attempts < 2) {
			try {
					if(arg.toLowerCase().contains("click")){
						driver.findElement(by).click();
					}else if(arg.toLowerCase().contains("scrollintoview")){
						((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
					}else{
						Reporter.log("Invalid argument provided ", true);
						throw new Exception();
					}
					
		            break;
			} catch(StaleElementReferenceException e) {
			
			}
			attempts++;
		}		
	}
	
}
