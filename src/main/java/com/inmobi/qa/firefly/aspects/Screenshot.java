package com.inmobi.qa.firefly.aspects;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inmobi.qa.firefly.core.TestSettings;
import com.thoughtworks.selenium.Selenium;

@Aspect
public class Screenshot {

	static Logger logger = LoggerFactory.getLogger(Screenshot.class);

	@Pointcut("call(* org.openqa.selenium.WebElement.*(..))")
	public void onWebElementAction() {

	}

	@Before("onWebElementAction() && target(webelement)")
	public void beforeWebElementAction(JoinPoint joinPoint,
			WebElement webelement) {
		try {
			WebDriver driver = ((WrapsDriver) webelement).getWrappedDriver();
			takeScreenshot(new WebDriverBackedSelenium(driver,
					driver.getCurrentUrl()));
		} catch (WebDriverException e) {
			logger.warn("Error while taking screenshot for WebDriver action.\n"
					+ e.getStackTrace());
		}
	}

	@Pointcut("(execution(void com.thoughtworks.selenium.Selenium.*(..)))")
	public void onSeleniumAction() {
	}

	@Before("onSeleniumAction() && this(selenium)")
	public void beforeSeleniumAction(Selenium selenium) {
		try {
			takeScreenshot(selenium);
		} catch (WebDriverException e) {
			logger.warn("Error while taking screenshot for Selenium action.\n"
					+ StringUtils.join(e.getStackTrace(), "\n"));
		}
	}

	private void takeScreenshot(Selenium selenium) {
		WebDriver driver = ((WrapsDriver) (selenium)).getWrappedDriver();
		File screenshot;
		if (TestSettings.isUsingRemoteWebDriver()) {
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			screenshot = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs(OutputType.FILE);
		} else {
			screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
		}
		String className = "";
		String methodName = "";

		for (StackTraceElement stackTraceElement : Thread.currentThread()
				.getStackTrace()) {
			className = stackTraceElement.getClassName();
			methodName = stackTraceElement.getMethodName();
			if (className.startsWith(TestSettings.getTestClassPrefix()))
				break;
		}
		try {
			FileUtils.copyFile(
					screenshot,
					new File(TestSettings.getScreenshotFolder()
							+ File.separator + "screenshots" + File.separator
							+ className + File.separator + methodName
							+ File.separator
							+ String.valueOf(System.currentTimeMillis())
							+ ".png"));
		} catch (IOException e) {
			logger.error("Error while saving screenshot for {}.{}", className,
					methodName);
		}
	}
}