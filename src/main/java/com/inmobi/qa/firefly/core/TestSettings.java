package com.inmobi.qa.firefly.core;

import java.io.File;

import com.inmobi.qa.firefly.enums.Browser;

public class TestSettings {
	private static String baseurl = "http://mail.google.com";
	private static Browser browser = Browser.FIREFOX;
	private static String chromeDriverPath = "src/main/resources/chromedriver";
	private static String extensionPath = "src/main/resources/";
	private static String harDumpLocation = "/tmp" + File.separator
			+ "perf-logs";
	private static String host = "localhost";
	private static Boolean isPerfLog = false;
	private static Boolean isRemote = false;
	private static String platform = "MAC";
	private static String port = "4444";
	private static String screenshotFolder = "target";

	private static String testClassPrefix = "com.inmobi.qa.firefly.tests";
	private static Boolean appiumStatus = false;  

	public static void runAppiumTests(Boolean flag){
		appiumStatus = flag;
	}
	
	public static Boolean getAppiumStatus(){
		return appiumStatus;
	}
	
	
	public static void enablePerformanceLogging(Boolean enabled) {
		isPerfLog = enabled;
	}

	public static String getBaseurl() {
		return baseurl;
	}

	public static Browser getBrowser() {
		return browser;
	}

	public static String getChromeDriverPath() {
		return chromeDriverPath;
	}

	public static String getExtensionPath() {
		return extensionPath;
	}

	public static String getHarDumpLocation() {
		return harDumpLocation;
	}

	public static String getHost() {
		return host;
	}

	public static String getPlatform() {
		return platform;
	}

	public static String getPort() {
		return port;
	}

	public static String getScreenshotFolder() {
		return screenshotFolder;
	}

	public static String getTestClassPrefix() {
		return testClassPrefix;
	}

	public static Boolean isPerformanceLoggingEnabled() {
		return isPerfLog;
	}

	public static Boolean isUsingRemoteWebDriver() {
		return isRemote;
	}

	public static void setBaseurl(String baseurl) {
		TestSettings.baseurl = baseurl;
	}

	public static void setBrowser(Browser browser) {
		TestSettings.browser = browser;
	}

	public static void setChromeDriverPath(String chromeDriverPath) {
		TestSettings.chromeDriverPath = chromeDriverPath;
	}

	public static void setExtensionPath(String extensionPath) {
		TestSettings.extensionPath = extensionPath;
	}

	public static void setHarDumpLocation(String harDumpLocation) {
		TestSettings.harDumpLocation = harDumpLocation;
	}

	public static void setHost(String host) {
		TestSettings.host = host;
	}

	public static void setPlatform(String platform) {
		TestSettings.platform = platform;
	}

	public static void setPort(String port) {
		TestSettings.port = port;
	}

	public static void setScreenshotFolder(String screenshotFolder) {
		TestSettings.screenshotFolder = screenshotFolder;
	}

	public static void setTestClassPrefix(String testClassSuffix) {
		TestSettings.testClassPrefix = testClassSuffix;
	}

	public static void useRemoteWebDriver(Boolean useRemoteWebDriver) {
		TestSettings.isRemote = useRemoteWebDriver;
	}
}
