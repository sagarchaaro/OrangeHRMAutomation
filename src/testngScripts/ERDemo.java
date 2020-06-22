package testngScripts;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ERDemo {
	
	static ExtentHtmlReporter htmlReporter;
	static ExtentReports report;
	static ExtentTest logger;
	static WebDriver driver;
	
	
	@Test
	public static void login() throws Exception{
		
		
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\Reports\\login.html");
		htmlReporter.config().setDocumentTitle("OrangeHRMAutomation");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setReportName("OrangeHRMlogin");
		
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		
		report.setSystemInfo("OS", "Windows10");
		report.setSystemInfo("AuthorName", "Sagar");
		report.setSystemInfo("SeleniumVersion", "3.141.59");
		report.setSystemInfo("Environment", "QA");
		
		logger=report.createTest("Login_OrangeHRM");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Catalogue\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		
		driver.get("https://testersselenium-trials65101.orangehrmlive.com");
		File src =ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir")+"\\Reports\\HomePage.jpg");
		FileUtils.copyFile(src, dest);
		//logger.log(Status.INFO, "Orange HRM URL is loaded"+logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Reports\\HomePage.jpg"));
		logger.log(Status.INFO, "Orange HRM URL is loaded", MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\Reports\\HomePage.jpg").build());
		
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		logger.log(Status.INFO, "UserName is entered as Admin");
		
		driver.findElement(By.id("txtPassword")).sendKeys("Admin@123");
		logger.log(Status.INFO, "Password is entered as Admin@123");
		
		driver.findElement(By.id("btnLogin")).click();
		//logger.log(Status.INFO, "Login button is clicked");
		Thread.sleep(5000);
		
		File src1 =ts.getScreenshotAs(OutputType.FILE);
		File dest1 = new File(System.getProperty("user.dir")+"\\Reports\\LoginPage.jpg");
		FileUtils.copyFile(src1, dest1);
		//logger.log(Status.INFO, "Orange HRM URL is loaded"+logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Reports\\LoginPage.jpg"));
		logger.log(Status.INFO, "Login button is clicked", MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir")+"\\Reports\\LoginPage.jpg").build());
		
		WebElement element = driver.findElement(By.xpath("//*[@id='account-job']/i"));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	    logger.log(Status.INFO, "Username is clicked");
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	        
	    driver.findElement(By.id("logoutLink")).click();
	    logger.log(Status.INFO, "Logout button is clicked");
	    
	    
		
	}
	
	@AfterMethod
	public static void aftermethod(ITestResult result){
		if(result.getStatus() == ITestResult.SUCCESS){
			logger.log(Status.PASS, "Testcase is passed "+result.getName());
		}else if(result.getStatus() == ITestResult.FAILURE){
			logger.log(Status.FAIL, "Testcase is Failed "+result.getName());
			logger.log(Status.FAIL, "Testcase is Failed "+result.getThrowable());
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, "Testcase is Skipped "+result.getName());
			logger.log(Status.SKIP, "Testcase is Skipped "+result.getThrowable());
		}
		 driver.close();
		 report.flush();
	}
}
