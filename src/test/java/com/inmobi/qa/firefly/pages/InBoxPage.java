package com.inmobi.qa.firefly.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.inmobi.qa.firefly.core.Page;

public class InBoxPage extends Page {

	public By composeButton = By.xpath("//div[text()='COMPOSE']");
	public By closeButton = By.xpath("//img[@data-tooltip='Save & Close']");
	public By userNameLink = By
			.xpath("//a[@href='https://plus.google.com/u/0/me?tab=mX']");
	public By signOutButton = By.id("gb_71");

	public InBoxPage(WebDriver driver) {
		super(driver);
	}

	public void composeMail() {
		waitForElementPresent(composeButton);
		driver.findElement(composeButton).click();
		waitForElementPresent(closeButton);
		driver.findElement(closeButton).click();
	}

	public LoginPage signOut() {
		driver.findElement(userNameLink).click();
		driver.findElement(signOutButton).click();
		return new LoginPage(driver);
	}
}