package com.inmobi.qa.firefly.tests;

import net.sf.testng.databinding.DataBinding;
import net.sf.testng.databinding.TestInput;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.inmobi.qa.firefly.core.Firefly;
import com.thoughtworks.selenium.Selenium;

@DataBinding
public class SeleniumTest {
	public Selenium selenium;
	public String emailInput = "name:Email";
	public String passwordInput = "name:Passwd";
	public String signInButton = "name:signIn";
	public String createAnAccountButton = "id:link-signup";

	@BeforeMethod
	public void setUp() {
		selenium = new Firefly().getSelenium();
	}

	@Test
	public void verifySelenium(@TestInput(name = "email") final String email,
			@TestInput(name="password") final String password) {
		selenium.type(emailInput, email);
		selenium.type(passwordInput, password);
		selenium.click(signInButton);
	}

	@AfterMethod
	public void tearDown() {
		selenium.close();
	}
}