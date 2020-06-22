package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManage {
	public static ExtentHtmlReporter getExtentHtmlReport(String reportPath){
		
		String extentPath= reportPath;
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentPath+"\\OrangeHRMTestResult.html");
		htmlReporter.config().setDocumentTitle("ExtentReport");
		htmlReporter.config().setReportName("OrangeHRM Test Execution Result");
		htmlReporter.config().setTheme(Theme.DARK);
		return htmlReporter;
		
	}
	
	public static ExtentReports getExtentReport(ExtentHtmlReporter htmlReporter){
		ExtentReports report = new ExtentReports();
		
		report.attachReporter(htmlReporter);	
		report.setSystemInfo("OS", "Windows10");
		report.setSystemInfo("SeleniumVersion", "3.141.59");
		report.setSystemInfo("Environment", "QA");
		
		return report;
	}
	
	public static ExtentTest getExtentTest(ExtentReports report,String TestCaseName ){
		ExtentTest logger=report.createTest(TestCaseName);
		return logger;
	}
	
}
