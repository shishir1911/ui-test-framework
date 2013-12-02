package com.inmobi.qa.firefly.aspects;

import java.sql.Timestamp;
import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inmobi.qa.firefly.core.TestSettings;
import com.inmobi.qa.firefly.utils.Utility;

@Aspect
public class StepLogger {
	static Logger logger = LoggerFactory.getLogger(StepLogger.class);

	@Pointcut("(call(void org.openqa.selenium.WebElement.*()))")
	public void onWebElementNoArgActions() {
	}

	@Pointcut("(call(void org.openqa.selenium.WebElement.*(CharSequence...)))")
	public void onWebElementSingleArgActions() {
	}

	@After("onWebElementNoArgActions() && target(webelement)")
	public void afterWebElementNoArgAction(WebElement webelement) {
		TestStep testStep = new TestStep(Thread.currentThread().getStackTrace());
		logger.info(
				"Step: {}\t{}\t{}\t{}\t{}\tOn: {}",
				testStep.getTestClassName(),
				testStep.getTestMethodName(),
				new Timestamp((new Date()).getTime()),
				testStep.getStepClassName(),
				testStep.getStepClassName(),
				"["
						+ webelement.toString().substring(
								webelement.toString().indexOf("->") + 3));
	}

	@After("onWebElementSingleArgActions() && target(webelement) && args(charsequences[])")
	public void afterWebElementSingleArgAction(WebElement webelement,
			CharSequence charsequences[]) {
		TestStep testStep = new TestStep(Thread.currentThread().getStackTrace());
		logger.info(
				"Step: {}\t{}\t{}\t{}\t{}\tOn: {}\tWith: {}",
				testStep.getTestClassName(),
				testStep.getTestMethodName(),
				new Timestamp((new Date()).getTime()),
				testStep.getStepClassName(),
				testStep.getStepMethodName(),
				"["
						+ webelement.toString().substring(
								webelement.toString().indexOf("->") + 3),
				Utility.charSequenceArrayToString(charsequences));
	}

	@Pointcut("(execution(void com.thoughtworks.selenium.Selenium.*(String)))")
	public void onSeleniumSingleArgAction() {
	}

	@Pointcut("(execution(void com.thoughtworks.selenium.Selenium.*(String,String)))")
	public void onSeleniumDoubleArgAction() {
	}

	@After("onSeleniumSingleArgAction() && args(name)")
	public void afterSeleniumSingleArgAction(String name) {
		TestStep testStep = new TestStep(Thread.currentThread().getStackTrace());
		logger.info("Step: {}\t{}\t{}\t{}\t{}\tOn: {}",
				testStep.getTestClassName(), testStep.getTestMethodName(),
				new Timestamp((new Date()).getTime()),
				testStep.getStepClassName(), testStep.getStepMethodName(), "["
						+ name + "]");
	}

	@After("onSeleniumDoubleArgAction() && args(name,str)")
	public void afterSeleniumDoubleArgAction(String name, String str) {
		TestStep testStep = new TestStep(Thread.currentThread().getStackTrace());
		logger.info("Step: {}\t{}\t{}\t{}\t{}\tOn: {}\tWith: {}",
				testStep.getTestClassName(), testStep.getTestMethodName(),
				new Timestamp((new Date()).getTime()),
				testStep.getStepClassName(), testStep.getStepMethodName(), "["
						+ name + "]", str);
	}
}

/**
 * Utility class for TestStep container. This is consumed by StepLogger aspect
 * class
 * 
 * @author priyadarshi.kunal
 */
class TestStep {
	private String stepClassName = "";
	private String stepMethodName = "";
	private String testClassName = "";
	private String testMethodName = "";

	public TestStep(StackTraceElement[] stackTraceElements) {
		for (int i = 1; i < stackTraceElements.length; i++) {
			if (stackTraceElements[i].toString().startsWith(
					TestSettings.getTestClassPrefix())) {
				testClassName = stackTraceElements[i].getClassName();
				testMethodName = stackTraceElements[i].getMethodName();
				stepClassName = stackTraceElements[i - 1].getClassName();
				stepMethodName = stackTraceElements[i - 1].getMethodName();
			}
		}
	}

	public String getStepClassName() {
		return stepClassName;
	}

	public String getStepMethodName() {
		return stepMethodName;
	}

	public String getTestClassName() {
		return testClassName;
	}

	public String getTestMethodName() {
		return testMethodName;
	}
}
