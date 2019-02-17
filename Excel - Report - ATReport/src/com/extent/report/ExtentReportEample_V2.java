/**
 * this class is use to generate the report using extent report version2 
 * 
 * main steps going to perform
 * - create test and add the logger
 * - capture screenshoots and add into the report
 * - add screenshot in failure
 * - open same report in same browser 
 * 
 * 
 * <!-- pom.xml -->
 * http://extentreports.com/docs/versions/2/java/#maven
	<!-- https://mvnrepository.com/artifact/com.relevantcodes/extentreports -->
	<dependency>
    	<groupId>com.relevantcodes</groupId>
    	<artifactId>extentreports</artifactId>
    	<version>2.05</version>
	</dependency>

 
 * or copy artifacts id and seach in maven central repositroy then select "Extents report for selenium"  and select your version (2.05)  

 */

/*
 * Follow below 2 stpes to generate extent report version 2
 * Step1: create 2 variable from extentreport.jar file
 * Step2: Follow next 3 steps only
 */
package com.extent.report;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.at.report.impl.MyATReport;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportEample_V2 {

	WebDriver driver = null;

	//create 2 variable from extentreport.jar file
	ExtentReports reports = null;
	ExtentTest logger = null;
	String myReport = null;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "H:\\Software\\JARS\\driver\\chromedriver_win32_2.46.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

		//Follow next 3 steps only
		//First: is used to generate report in particular folder in .html form
		myReport = System.getProperty("user.dir") + "/myExtentReport/myTestReport.html";
		reports = new ExtentReports(myReport, true);

	}

	@Test
	public void verifyPageTitme() throws IOException, InterruptedException {

		//Second: start the report processing part and return logger object
		logger = reports.startTest("verifyPageTitme", "This is used to validate the title of the page");

		logger.log(LogStatus.INFO, "Browser Started");

		driver.get("https://www.google.com/");
		logger.log(LogStatus.INFO, "Navigate to the browser");

		String getTitle = driver.getTitle();
		logger.log(LogStatus.INFO, "Getting the title");

		Assert.assertTrue(getTitle.contains("Google"));
		logger.log(LogStatus.PASS, "Title Verified", getTitle);

		String currentUrl = driver.getCurrentUrl();
		logger.log(LogStatus.INFO, "Currnet URL", currentUrl);

		WebElement search = driver.findElement(By.name("q"));
		search.sendKeys("Selenium", Keys.ENTER);
		logger.log(LogStatus.INFO, "Enterd Input");

		String result = driver.findElement(By.id("resultStats")).getText();
		//logger.log(LogStatus.PASS, "Result Time", result);
		logger.log(LogStatus.FAIL, "Result Time", result);

		/*		driver.get(
						"file:///C:/Users/Shankar/eclipse-workspace-oxygen/Excel%20-%20Report%20-%20ATReport/test-output/index.html");
		
				driver.navigate().refresh();*/
		String screensPath = getScreenshot("executionTime", driver);
		String path = logger.addScreenCapture(screensPath);
		logger.log(LogStatus.INFO, "Stored Screenshots in path : ", path);

		// to print the environemtal information
		reports.addSystemInfo("Host Name : ", "Shankar System");
		reports.addSystemInfo("Environment: ", "Selenium");
		reports.addSystemInfo("Architecuture", "64Bit");

		Thread.sleep(5000);
		//navigate to that report 
		driver.navigate().to(myReport);

	}

	@AfterMethod

	public void tearDown(ITestResult result) throws InterruptedException, IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			//add the screenshots like
			String failPath = getScreenshot(result.getName(), driver);
			logger.log(LogStatus.FAIL, "Someting went wrong in Title Verification", failPath);

		}

		//Thrid: End the report and write content into the report using flush mathod
		reports.endTest(logger);
		reports.flush(); //<- flush = write or update test information to your reporter

		//Thread.sleep(60000);
		driver.close();

	}

	public static String getScreenshot(String screenshotsName, WebDriver driver) throws IOException {

		String path = System.getProperty("user.dir") + "/screenshots/" + screenshotsName + ".png";

		TakesScreenshot ts = (TakesScreenshot) driver;
		File file = ts.getScreenshotAs(OutputType.FILE);
		//CopyUtils.(file, new File(""));
		FileUtils.copyFile(file, new File(path));
		return path;
	}
}
