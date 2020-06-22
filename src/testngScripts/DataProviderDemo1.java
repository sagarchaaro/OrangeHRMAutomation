package testngScripts;

import java.util.HashMap;

import org.testng.annotations.DataProvider;

import frameworkScripts.Constant;
import utilities.ExcelConfig;

public class DataProviderDemo1 {
	
	@DataProvider(name="Login")
	public static Object[][] loginData(){
		Object[][] ob= new Object[5][2];
		
		ob[0][0]="Admin";
		ob[0][1]="Admin@123";
		
		ob[1][0]="Sagar";
		ob[1][1]="Test";
		
		ob[2][0]="Aneesh";
		ob[2][1]="Test123@";
		
		ob[3][0]="Lalita";
		ob[3][1]="pwd!234";
		
		ob[4][0]="Selenim";
		ob[4][1]="Testing";
		

		for(int i=0;i<=10;i++){
			//ob[i][0]=ExcelConfig.getCellData(i, Constant.col_UserName, "Login");
			//ob[i][1]=ExcelConfig.getCellData(i, Constant.Col_password, "Login");		
			
		}
				
		return ob;
	}

}
