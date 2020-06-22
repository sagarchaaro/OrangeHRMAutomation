package testngScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertDemo {
	@Test
	public void demo(){
		int x=10, y=20;
		int z=x+y;
		System.out.println("Hello");
		//Assert.assertEquals(z,50 );
		//System.out.println("Hi..!!!");
		
		Assert.assertNotEquals(z, 50);
		System.out.println("Hi..!!!");
		Assert.assertTrue(z<50);
		System.out.println("Core Java...!!!");
		Assert.assertFalse(z==60);
		System.out.println("Advanced Java...!!!");
		
		String s1="Hello", s2="Hi";
		
		Assert.assertNotSame(s1,s2);
		System.out.println("Java ME...!!!");
		
	}
}
