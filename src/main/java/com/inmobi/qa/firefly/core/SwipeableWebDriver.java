package com.inmobi.qa.firefly.core;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SwipeableWebDriver extends RemoteWebDriver implements
		HasTouchScreen {
	private RemoteTouchScreen touch;

	public SwipeableWebDriver(URL remoteAddress,
			Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
		touch = new RemoteTouchScreen(getExecuteMethod());
	}

	public RemoteTouchScreen getTouch() {
		return touch;
	}
}
