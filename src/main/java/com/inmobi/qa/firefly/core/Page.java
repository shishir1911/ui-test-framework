package com.inmobi.qa.firefly.core;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class Page {
	protected WebDriver driver;

	public Page(WebDriver driver) {
		this.driver = driver;
	}

	public void close() {
		driver.close();
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	public Page get(String url) {
		driver.get(url);
		return this;
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void waitForElementPresent(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (TimeoutException e) {
			Assert.fail("Element not found: " + by.toString());
		}
	}

	public void waitForElementVisible(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException e) {
			Assert.fail("Element not visible: " + by.toString());
		}
	}

	public void waitForElementClickable(By by) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (TimeoutException e) {
			Assert.fail("Element not clickable: " + by.toString());
		}
	}

	public void waitForElementToHaveText(By by, String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.textToBePresentInElement(by, text));
		} catch (TimeoutException e) {
			Assert.fail("Element " + by.toString() + " did not have text: "
					+ text);
		}
	}

	// private void verifyRequiredElementsPresent() {
	// List<By> missingElements = new ArrayList<By>();
	// for (Field field : this.getClass().getFields()) {
	// try {
	// if (field.isAnnotationPresent(Required.class)) {
	// try {
	// WebDriverWait wait = new WebDriverWait(driver, 30);
	// wait.until(ExpectedConditions
	// .presenceOfElementLocated((By) field
	// .get(new Object())));
	// } catch (TimeoutException e) {
	// missingElements.add((By) field.get(this));
	// }
	// }
	// } catch (IllegalAccessException e) {
	// Assert.fail("Raised illegal access exception while verifying the required element: "
	// + field.getName());
	// }
	// }
	//
	// if (missingElements.size() > 0) {
	// Assert.fail("Missing required elements on page.\n"
	// + StringUtils.join(missingElements.toArray()));
	// }
	// }
}