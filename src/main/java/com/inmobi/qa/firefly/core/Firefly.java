package com.inmobi.qa.firefly.core;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

import com.inmobi.qa.firefly.utils.Utility;
import com.thoughtworks.selenium.Selenium;

public class Firefly {

	private WebDriver webDriver;
	private Selenium selenium;

	public Firefly() {
		try {
			if (TestSettings.isUsingRemoteWebDriver()) {
				String urlString = "http://" + TestSettings.getHost() + ":"
						+ TestSettings.getPort() + "/wd/hub";
				URL serverurl = new URL(urlString);
				webDriver = new RemoteWebDriver(serverurl,
						getDesiredCapabilities());
			} else {
				webDriver = getLocalWebDriver();
			}
			this.selenium = new WebDriverBackedSelenium(webDriver,
					TestSettings.getBaseurl());
		} catch (MalformedURLException e) {
			Assert.fail(Arrays.toString(e.getStackTrace()));
		}
	}

	public Firefly(String iOsApp) {
		File appFile = new File("src/test/resources", iOsApp);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String version = iOsApp.split("\\(|\\)")[1];

		capabilities.setCapability(CapabilityType.BROWSER_NAME, "iOS");
		capabilities.setCapability(CapabilityType.PLATFORM,
				TestSettings.getPlatform());
		capabilities.setCapability(CapabilityType.VERSION, version);
		capabilities.setCapability("app", appFile);
		try {
			webDriver = new SwipeableWebDriver(new URL("http://" + "localhost"
					+ ":" + "4723" + "/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			Assert.fail("Appium Server URL is giving MalformedURLException.\n"
					+ "http://" + "localhost" + ":" + "4723" + "/wd/hub");
		}
	}

	public Firefly(String androidApp, String appPackageActivity) {
		File app = new File("src/test/resources", androidApp);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String version = androidApp.split("[\\(\\)]")[1];

		String[] fragments = appPackageActivity.split(".");
		String appActivity = "." + fragments[fragments.length - 1];

		List<String> fragList = new ArrayList<String>(Arrays.asList(fragments));
		fragList.remove(fragList.size() - 1);

		String appPackage = StringUtils.join(fragList, ".");

		capabilities.setCapability("device", "Android");
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		capabilities.setCapability(CapabilityType.VERSION, version);
		capabilities.setCapability(CapabilityType.PLATFORM,
				TestSettings.getPlatform());
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("app-package", appPackage);
		capabilities.setCapability("app-activity", appActivity);

		try {
			webDriver = new SwipeableWebDriver(new URL("http://" + "localhost"
					+ ":" + "4723" + "/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			Assert.fail("Appium Server URL is giving MalformedURLException.\n"
					+ "http://" + "localhost" + ":" + "4723" + "/wd/hub");
		}
	}

	public WebDriver getAppiumDriver() {
		return webDriver;
	}

	public WebDriver getDriver() {
		Utility.sleep(3000);
		webDriver.get(TestSettings.getBaseurl());
		webDriver.manage().window().maximize();
		Utility.sleep(3000);
		return webDriver;
	}

	public Selenium getSelenium() {
		selenium.open(TestSettings.getBaseurl());
		selenium.windowFocus();
		selenium.windowMaximize();
		return selenium;
	}

	/**
	 * Returns a web driver instance for executing tests without selenium server
	 * running
	 * 
	 * @return
	 */
	private WebDriver getLocalWebDriver() {
		switch (TestSettings.getBrowser()) {
		case FIREFOX:
			return new FirefoxDriver(getFirefoxCapabilities());
		case CHROME:
			return new ChromeDriver(getChromeCapabilities());
		case IE:
			return new InternetExplorerDriver(getInternetExplorerCapabilities());
		case ANDROID:
			return new AndroidDriver(getAndroidCapabilities());
		case SAFARI:
			return new SafariDriver(getSafariCapabilities());
		}
		throw new RuntimeException(TestSettings.getBrowser()
				+ ": is invalid value for Browser.");
	}

	/**
	 * Returns the DesiredCapabilities required for the WebDriver. Reads the
	 * browser property from pom.xml
	 */
	private DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		switch (TestSettings.getBrowser()) {
		case FIREFOX:
			desiredCapabilities = getFirefoxCapabilities();
			break;
		case CHROME:
			desiredCapabilities = getChromeCapabilities();
			break;
		case IE:
			desiredCapabilities = getInternetExplorerCapabilities();
			break;
		case ANDROID:
			desiredCapabilities = getAndroidCapabilities();
			break;
		case SAFARI:
			desiredCapabilities = getSafariCapabilities();
		}
		return desiredCapabilities;
	}

	private DesiredCapabilities getFirefoxCapabilities() {
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
		if (TestSettings.isPerformanceLoggingEnabled()) {
			try {
				firefoxProfile.addExtension(new File(TestSettings
						.getExtensionPath() + "firebug.xpi"));
				firefoxProfile.addExtension(new File(TestSettings
						.getExtensionPath() + "netExport.xpi"));
				firefoxProfile.setPreference("app.update.enabled", false);
				String domain = "extensions.firebug.";

				firefoxProfile.setPreference(domain + "currentVersion",
						"1.11.0");
				firefoxProfile.setPreference(domain + "allPagesActivation",
						"on");
				firefoxProfile
						.setPreference(domain + "defaultPanelName", "net");
				firefoxProfile.setPreference(domain + "net.enableSites", true);

				firefoxProfile.setPreference(domain
						+ "netexport.alwaysEnableAutoExport", true);
				firefoxProfile.setPreference(domain + "netexport.showPreview",
						false);
				firefoxProfile.setPreference(
						domain + "netexport.defaultLogDir",
						TestSettings.getHarDumpLocation());
				desiredCapabilities.setCapability(FirefoxDriver.PROFILE,
						firefoxProfile);
			} catch (IOException e) {
				System.err
						.println("Encountered error while loading and configuring firefox extensions:\n"
								+ e.getStackTrace());
			}
		}
		return desiredCapabilities;
	}

	private DesiredCapabilities getChromeCapabilities() {
		System.setProperty("webdriver.chrome.driver",
				TestSettings.getChromeDriverPath());
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(Arrays.asList("--start-maximized",
				"allow-running-insecure-content", "ignore-certificate-errors"));
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
		desiredCapabilities.setCapability(ChromeOptions.CAPABILITY,
				chromeOptions);
		if (TestSettings.isPerformanceLoggingEnabled()) {
			desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS,
					getLoggingPreferences());
		}
		return desiredCapabilities;
	}

	private DesiredCapabilities getInternetExplorerCapabilities() {
		return DesiredCapabilities.internetExplorer();
	}

	private DesiredCapabilities getAndroidCapabilities() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();
		return desiredCapabilities;
	}

	private LoggingPreferences getLoggingPreferences() {
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		// logPrefs.enable(LogType.PROFILER, Level.INFO);
		return logPrefs;
	}
	
	private DesiredCapabilities getSafariCapabilities() {
		return DesiredCapabilities.safari();
	}
}
