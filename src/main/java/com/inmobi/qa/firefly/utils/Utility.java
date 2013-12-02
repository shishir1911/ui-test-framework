package com.inmobi.qa.firefly.utils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Utility {
	public static void highlightElement(WebDriver driver, WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				webElement, "color: yellow; border: 2px solid yellow;");

	}

	public static void unhighlightElement(WebDriver driver,
			WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				webElement, "");
	}

	public static String stamp(String str) {
		return str
				+ new Timestamp(new Date().getTime()).toString().replaceAll(
						":| |-|\\.", "");
	}

	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Assert.fail(Arrays.toString(e.getStackTrace()));
		}
	}

	public static String charSequenceArrayToString(CharSequence[] charSequences) {
		String val = "";
		for (int i = 0; i < charSequences.length; i++)
			val = val + charSequences[i].toString();
		return val;
	}
}
