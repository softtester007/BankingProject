package com.bankingproject.loginpage;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.bankingproject.base.BaseClass;
import com.bankingproject.pom.LoginPagePom;
import com.bankingproject.pom.ManagerPagePom;
import com.bankingproject.utility.Utility;



public class LoginPageTest extends BaseClass {
	
	LoginPagePom loginPagePom;
	Utility util;
	ExtentSparkReporter extentSparkReporter;
	ExtentReports extentReports;
	ExtentTest extentTest;
	
	@BeforeClass
	public void setUp() {
		launchWebsite();
		extentSparkReporter = new ExtentSparkReporter(projectpath+"//ExtentReport//extent.html");
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		switch (result.getStatus()) {
		case 1:
			extentTest.log(Status.PASS, "test case pass with correct url");
			break;
		case 2:
			extentTest.log(Status.FAIL, "test case fail with incorrect url");
			break;
			
		case 3:
			extentTest.log(Status.SKIP, "test case skipped ...");
			break;

		default:
			break;
		}
		extentReports.flush();
		driver.close();
	}
	
	
	@Test
	public void testUrl() throws IOException {
		
		
		String url = driver.getCurrentUrl();
		
		extentTest = extentReports.createTest("testUrl");
		extentTest.addScreenCaptureFromPath(Utility.getScreenshot("testUrl"), url);
		
		Assert.assertEquals(url, "https://demo.guru99.com/V1/index.php");
		
		/*if(url.equalsIgnoreCase("https://demo.guru99.com/V1/index.php")) {
			System.out.println("Same url");
		}
		else {
			System.out.println("wrong url");
		}  */
	}
	@Test
	public void testTitle() throws IOException {
		String title = driver.getTitle();
		extentTest = extentReports.createTest("testUrl");
		extentTest.addScreenCaptureFromPath(Utility.getScreenshot("testTitle"), title);
		
		Assert.assertEquals(title, "GTPL Bank Home Page");
		/*if(title.equalsIgnoreCase("GTPL Bank Home Page")) {
			System.out.println("We are on correct page");
		}
		else {
			System.out.println("wrong");
		}   */
		
		
	}
	
	@Test
	public void testValidLogin() throws EncryptedDocumentException, IOException, InterruptedException {
		util = new Utility();
		loginPagePom = new LoginPagePom();
		
		/*String pass = (String) util.getSingleCellDataFromExcel(1, 1, "Sheet1");
		loginPagePom.setPassword(pass);
		Thread.sleep(10000);
		String user = (String) util.getSingleCellDataFromExcel(1, 0, "Sheet1");
		*/
		String[][] data=util.getAllExcelDataArray("Sheet1");
		String userid = data[3][0];
		String pass = data[3][1];
		
		loginPagePom.setUserId(userid);
		loginPagePom.setPassword(pass);
		ManagerPagePom managerPagePom = loginPagePom.clickOnLoginButton();		
		//this.clickOnLoginButton();
		
		
		String title = driver.getTitle();
		Assert.assertEquals(title, "GTPL Bank Manager HomePage");
		Assert.assertTrue(true, title);
		util.getScreenshot("testValidLogin1");
		
		extentTest = extentReports.createTest("testUrl");
		extentTest.addScreenCaptureFromPath(Utility.getScreenshot("testValidLogin"));
		
	}
	
	@Test
	public void testBlankLogin() {
		loginPagePom = new LoginPagePom();
		loginPagePom.clickOnLoginButton();
		loginPagePom.HandleAlert();
		String errorAlert = loginPagePom.error;
		Assert.assertEquals(errorAlert, "User is not valid");
	}
	
	@Test
	public void testInvalidLogin() throws EncryptedDocumentException, IOException, InterruptedException {
		util = new Utility();
		loginPagePom = new LoginPagePom();
			
		String[][] data=util.getAllExcelDataArray("Sheet1");
		String userid = data[4][0];
		String pass = data[4][1];
		
		loginPagePom.setUserId(userid);
		loginPagePom.setPassword(pass);
		loginPagePom.clickOnLoginButton();		
		//this.clickOnLoginButton();
		loginPagePom.HandleAlert();
		String errorAlert = loginPagePom.error;
		
		Assert.assertEquals(errorAlert, "User is not valid");
		System.out.println(errorAlert);
		
	}
}
