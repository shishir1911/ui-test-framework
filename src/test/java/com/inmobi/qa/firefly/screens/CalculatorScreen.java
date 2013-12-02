package com.inmobi.qa.firefly.screens;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.inmobi.qa.firefly.core.Page;

public class CalculatorScreen extends Page {

	public By textFields = By.tagName("textField");
	public By computeSumButton = By.tagName("button");
	public By showAlertButton = By.xpath("//button[2]");
	public By sumLabel = By.tagName("staticText");

	public CalculatorScreen(WebDriver driver) {
		super(driver);
	}

	public void populate() {
		int MINIMUM = 0;
		int MAXIMUM = 10;

		Random random = new Random();

		for (WebElement textField : driver.findElements(textFields)) {
			int rndNum = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
			textField.sendKeys(String.valueOf(rndNum));
		}
	}

	public CalculatorScreen clickComputeSum() {
		driver.findElement(computeSumButton).click();
		return new CalculatorScreen(driver);
	}

	public CalculatorScreen clickShowAlert() {
		driver.findElement(showAlertButton);
		return new CalculatorScreen(driver);
	}

	public int getExpectedSum() {
		int expectedSum = 0;
		for (WebElement textField : driver.findElements(textFields))
			expectedSum += Integer.parseInt(textField.getText());
		return expectedSum;
	}
}