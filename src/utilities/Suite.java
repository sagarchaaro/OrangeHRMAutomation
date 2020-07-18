package utilities;

import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import frameworkScripts.CommonMethod;

public class Suite {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static String reportPath, timestamp;
	
	@BeforeSuite
	public void setup() throws Exception{
		CommonMethod.projectpath = System.getProperty("user.dir");
				
		CommonMethod.loadYamlFile(CommonMethod.projectpath + "\\Test-Resources\\test-info.yaml");
		reportPath=CommonMethod.getYamlData("screenshotPath");		
		timestamp = Utils.timeStamp("YYYY-MM-dd-hhmmss");
		reportPath = CommonMethod.projectpath+ reportPath + "\\Log\\" + timestamp;
		Utils.createDir(reportPath);
		
		htmlReporter = ExtentManage.getExtentHtmlReport(reportPath);
		report=ExtentManage.getExtentReport(htmlReporter);
	}
}
