package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Leave_Page extends BaseClass{
	
	public Leave_Page(WebDriver driver) {
		super(driver);
		
	}
	By dd_LeaveType = By.xpath("//input[@class='select-dropdown']");
	By txtbx_LeaveType = By.xpath("//div[@class='input-field col s12 m12 l12']/textarea");
	By click_DateFrom = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[1]");
	By click_DateTo = By.xpath("(//i[@class='material-icons action-icon date-picker-open-icon'])[2]");
	By btn_Save= By.xpath("//button[@class='btn waves-effect waves-green']");
	By msg_Warnning= By.xpath("//div[@class='modal-heading']/h4");
	By btn_Close= By.xpath("//a[@class='modal-action waves-effect btn']");
	By btn_Ok= By.xpath("//a[@class='modal-action waves-effect btn primary-btn']");
	By msg_Success= By.xpath("//div[@class='toast toast-success']");

}
