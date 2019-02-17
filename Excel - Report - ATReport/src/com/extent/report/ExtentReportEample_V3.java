package com.extent.report;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExtentReportEample_V3 {

	WebDriver driver = null;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "H:\\Software\\JARS\\driver\\chromedriver_win32_2.46.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);

	}

	@Test
	public void verifyPageTitme() {

		driver.get("https://www.google.com/");
		String getTitle = driver.getTitle();

		Assert.assertTrue(getTitle.contains(""), "Title Not Matching");

		String currentUrl = driver.getCurrentUrl();
		System.out.println("Current URL : " + currentUrl);

		WebElement search = driver.findElement(By.name("q"));
		search.sendKeys("Selenium", Keys.ENTER);

		String result = driver.findElement(By.id("resultStats")).getText();
		System.out.println("My Result : " + result);

		driver.get(
				"file:///C:/Users/Shankar/eclipse-workspace-oxygen/Excel%20-%20Report%20-%20ATReport/test-output/index.html");

		driver.navigate().refresh();
	}

	@AfterMethod
	public void tearDown() throws InterruptedException {

		Thread.sleep(60000);
		driver.close();

	}
}
