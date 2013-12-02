package com.inmobi.qa.firefly.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.inmobi.qa.firefly.core.Firefly;
import com.inmobi.qa.firefly.screens.CalculatorScreen;

public class AppiumTest {
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		String app = "TestApp(6.0).zip";
		driver = new Firefly(app).getAppiumDriver();
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
	}

	@Test
	public void testUIComputation() throws Exception {
		CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
		calculatorScreen.populate();
		calculatorScreen.clickComputeSum();
		assertEquals(driver.findElement(calculatorScreen.sumLabel).getText(),
				String.valueOf(calculatorScreen.getExpectedSum()));
	}

	@Test
	public void testActive() throws Exception {
		CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
		assertTrue(driver.findElement(calculatorScreen.computeSumButton)
				.isDisplayed());
		assertTrue(driver.findElement(calculatorScreen.sumLabel).isDisplayed());
	}

	@Test
	public void testBasicAlert() throws Exception {
		CalculatorScreen calculatorScreen = new CalculatorScreen(driver);
		calculatorScreen.clickShowAlert();
		Alert alert = driver.switchTo().alert();
		assertEquals(alert.getText(), "Cool title");
		alert.accept();
	}
}