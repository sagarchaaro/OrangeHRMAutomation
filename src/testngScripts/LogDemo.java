package testngScripts;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.Test;

public class LogDemo {
	@Test
	public void test(){
		Logger Log=Logger.getLogger(testngScripts.LogDemo.class);
		//DOMConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.xml");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"//test-resources//Log4j.properties");
		
		Log.info("This is the LogDemo");
		
		Log.warn("This is the Log4J Warning message");
		
		Log.trace("This is the Log trace message");
		
		Log.error("This is the Log error message");
		Log.fatal("This is the Fatal message");
		
	}
}
