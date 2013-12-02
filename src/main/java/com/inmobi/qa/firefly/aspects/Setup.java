package com.inmobi.qa.firefly.aspects;

import java.io.IOException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inmobi.qa.firefly.core.TestSettings;

@Aspect
public class Setup {

	static Logger logger = LoggerFactory.getLogger(Setup.class);

	@After("onBeforeSuite()")
	public void afterBeforeSuite() {
		if(TestSettings.getAppiumStatus()){
			try{
				System.out.println("STARTING APPIUM SERVER");
				Runtime.getRuntime().exec("appium &");
				System.out.println("STARTED APPIUM SERVER");
			}catch(IOException e){
				System.out.println(e.getStackTrace());
			}
		}
	}

	@Pointcut("@annotation(org.testng.annotations.BeforeSuite)")
	public void onBeforeSuite() {
	}
}