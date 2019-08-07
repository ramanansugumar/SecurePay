package org.securepay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.securepay.pages.SecurepayPage;
import org.securepay.utilclass.UtilClass;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestRunner {
	
	WebDriver driver;
	ExtentReports extReport;
	ExtentTest test;
	ITestResult result;
	UtilClass util;

	public void setBrowser() throws MalformedURLException {
		
		String browserType = util.getAppPropValue("browser");

		// to facilitate multi browser settings and check for dockerflag also

		if (browserType.equalsIgnoreCase("chrome")) {
			/*
			 * to open chrome browser in incognito mode 
			 */

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/resources/drivers/chromedriver_3.exe");
			driver = new ChromeDriver(options);
		}
		/*
		 *  add firefox or ie browser
		 *   we can start remote webdriver also. From there we can integrate it with Dockers(selenoid)
		 */
		//
	}

	// to intialize the reports
	@BeforeClass
	public void initAll() {
        util= new UtilClass();
		String date =UtilClass.getDate();
		extReport = new ExtentReports("ExtentReports/report_" + date + ".html");
	}

	// Driver object opens here and test is started
	@Test(priority = 0)
	public void test1() throws FileNotFoundException, IOException, InterruptedException {
		util= new UtilClass();
		setBrowser();
		test = extReport.startTest("SecurePay");
		try {
			driver.get(util.getAppPropValue("googleurl"));
			SecurepayPage page = new SecurepayPage(driver);
			page.clickSecurePay();
			test.log(LogStatus.PASS, "Search SecurePay");
			page.gotoSupport();
			page.gotoContact();
			page.contactPageVerification();
			test.log(LogStatus.PASS, "Contact Page Verification");
			page.enterInformation();
			page.enterPh();
			page.enterUrl();
			page.selectEnquiry();
			test.log(LogStatus.PASS, "Information Entered");
		} catch (Exception e) {
			e.printStackTrace();
			test.log(LogStatus.FAIL, "TC Failed");
			Assert.fail();
		}
		finally
		{
			driver.close();
		}
		Thread.sleep(3000);
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenShotPath = UtilClass.capture(driver, "screenShotName");
			test.log(LogStatus.FAIL, result.getThrowable());
			test.log(LogStatus.FAIL, "Snapshot below: " + test.addScreenCapture(screenShotPath));
		}

		driver.quit();
		extReport.endTest(test);
	}


	@AfterClass
	public void flushAll() {
		extReport.flush();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
	{
		System.out.println(System.getProperty("user.dir"));
		TestRunner run= new TestRunner();
		run.test1();
	}

}
