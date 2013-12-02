package com.inmobi.qa.firefly.tests;

import net.sf.testng.databinding.DataBinding;
import net.sf.testng.databinding.TestInput;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.inmobi.qa.firefly.core.Firefly;
import com.inmobi.qa.firefly.pages.InBoxPage;
import com.inmobi.qa.firefly.pages.LoginPage;

@DataBinding
public class DataBindingCSVTest {
	WebDriver driver;

	@BeforeMethod
	public void beforeTest() {
		driver = new Firefly().getDriver();
	}

	@Test
	public void verifyWebDriver(@TestInput(name = "email") final String email,
			@TestInput(name = "password") final String password) {
		LoginPage loginPage = new LoginPage(driver);
		InBoxPage inBoxPage = loginPage.login(email, password);
		inBoxPage.composeMail();
		loginPage = inBoxPage.signOut();
	}

	@AfterMethod
	public void afterTest() {
		driver.quit();
	}
}
