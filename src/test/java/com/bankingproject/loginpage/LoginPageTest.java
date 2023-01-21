package com.bankingproject.loginpage;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bankingproject.base.BaseClass;

public class LoginPageTest extends BaseClass {
	
	@BeforeClass
	public void setUp() {
		launchWebsite();
	}
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	@Test
	public void testTitle() {
		String title = driver.getTitle();
		if(title.equalsIgnoreCase("GTPL Bank Home Page")) {
			System.out.println("We are on correct page");
		}
		else {
			System.out.println("wrong");
		}
	}
	@Test
	public void testUrl() {
		String url = driver.getCurrentUrl();
		if(url.equalsIgnoreCase("https://demo.guru99.com/V1/index.php")) {
			System.out.println("Same url");
		}
		else {
			System.out.println("wrong url");
		}
	}
}
