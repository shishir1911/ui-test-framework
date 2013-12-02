package com.inmobi.qa.firefly.pages;

import org.openqa.selenium.By;
import static org.andidev.webdriverextension.Bot.*;
import org.openqa.selenium.WebDriver;

import com.inmobi.qa.firefly.core.Page;

public class LoginPage extends Page {

	public By emailInput = By.name("Email");
	public By passwordInput = By.name("Passwd");
	public By signInButton = By.name("signIn");
	public By createAnAccountButton = By.id("link-signup");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public InBoxPage login(String email, String password) {
		type(email, driver.findElement(emailInput));
		type(password, driver.findElement(passwordInput));
		click(driver.findElement(signInButton));
		return new InBoxPage(driver);
	}

}
