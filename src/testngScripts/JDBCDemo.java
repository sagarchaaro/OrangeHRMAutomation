package testngScripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.testng.annotations.Test;

public class JDBCDemo {
	@Test
	public void jdbcDemo() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/orangehrm", "root", "root");
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select * from orangehrm.TC_01_Login");
		/*rs.next();
		String un=rs.getString("username");
		System.out.println("Username from database is :"+un);
		String pwd=rs.getString("password");
		System.out.println("Password from database is :"+pwd);*/
		while(rs.next()){
			System.out.println(rs.getString("username")+"---------"+rs.getString("password"));
		}
		
	}
}
