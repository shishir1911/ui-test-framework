package com.inmobi.qa.firefly.tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.inmobi.qa.firefly.core.TestSettings;
import com.inmobi.qa.firefly.enums.Browser;

public class SetUpTest {

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		TestSettings.setBrowser(Browser.FIREFOX);
		TestSettings.runAppiumTests(true);
		try {
			FileUtils.deleteDirectory(new File(TestSettings
					.getScreenshotFolder() + File.separator + "screenshots"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
