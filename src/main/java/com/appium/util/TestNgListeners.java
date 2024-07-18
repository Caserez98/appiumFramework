package com.appium.util;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.appium.setup.AppTestCase;
import com.appium.util.actiondriver.BaseActionDriver;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestNgListeners implements ITestListener{
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static ThreadLocal<CommonUtil> common = new ThreadLocal<>();
	private static ThreadLocal<BaseActionDriver> actionDriver = new ThreadLocal<>();

	@Override
	public void onFinish(ITestContext arg0) {
		if (AppTestCase.reports != null) {
			AppTestCase.reports.flush();
		}
		test.remove();
		common.remove();
		actionDriver.remove();
	}

	@Override
	public void onStart(ITestContext arg0) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		actionDriver.remove();
		common.remove();
		test.remove();
		AppTestCase currentClass = (AppTestCase) arg0.getInstance();
		actionDriver.set(currentClass.actionDriver);
		common.set(currentClass.common);
		test.set(currentClass.test);
		common.get().log(arg0.getMethod().getMethodName() + " Failed!");
		test.get().log(LogStatus.FAIL, arg0.getThrowable());
		String deviceName = actionDriver.get().getDeviceName().trim();
		common.get().failSeleniumTest(arg0.getThrowable(), actionDriver.get(), 0, deviceName);
		// common.get().analyzeLog();
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		actionDriver.remove();
		common.remove();
		test.remove();
		AppTestCase currentClass = (AppTestCase) arg0.getInstance();
		actionDriver.set(currentClass.actionDriver);
		common.set(currentClass.common);
		test.set(currentClass.test);
		common.get().log(arg0.getMethod().getMethodName() + " skipped!");
		test.get().log(LogStatus.SKIP, arg0.getThrowable());
		String deviceName = actionDriver.get().getDeviceName().trim();
		common.get().skipSeleniumTest(arg0.getThrowable(), actionDriver.get(), 0, deviceName);
		// common.get().analyzeLog();
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		AppTestCase currentClass = (AppTestCase) arg0.getInstance();
		actionDriver.set(currentClass.actionDriver);
		common.set(currentClass.common);
		test.set(currentClass.test);
		common.get().log("Executing Test Script" + currentClass.testCaseName);

		// actionDriver.set(SelTestCase.getActionDriver());
		// common.set(SelTestCase.getCommon());
		// test.set(SelTestCase.getTest());
		// common.get().log("Execute test case " + currentClass.testCaseName);
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// common.get().log("Test method success.....");
		// test.get().log(LogStatus.PASS, "Test passed");
		AppTestCase currentClass = (AppTestCase) arg0.getInstance();
		actionDriver.set(currentClass.actionDriver);
		common.set(currentClass.common);
		test.set(currentClass.test);
		common.get().log("Test Script successfully executed " + currentClass.testCaseName);
		String deviceName = actionDriver.get().getDeviceName().trim();
		common.get().passSeleniumTest(arg0.getThrowable(), actionDriver.get(), 0, deviceName);
	}

	
	
}
