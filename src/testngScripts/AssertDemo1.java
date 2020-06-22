package testngScripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertDemo1 {
	@Test
	public void demo(){
		
		SoftAssert assertion=new SoftAssert();
		
		int x=10, y=20;
		int z=x+y;
		System.out.println("Hello");
		assertion.assertEquals(z,60,"This is assertEquals comparison");
		System.out.println("Hello....Hi..!!!");
		
		assertion.assertNotEquals(z, 50);
		System.out.println("Hi..!!!");
		assertion.assertTrue(z<50);
		System.out.println("Core Java...!!!");
		assertion.assertFalse(z==30);
		System.out.println("Advanced Java...!!!");
		
		String s1="Hello", s2="Hi";
		
		assertion.assertNotSame(s1,s2);
		System.out.println("Java ME...!!!");
		
		assertion.assertAll();
	}
}
