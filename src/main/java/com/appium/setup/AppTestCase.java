package com.appium.setup;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.appium.utill.CommonUtil;
import com.appium.utill.TestNgListeners;
import com.appium.utill.actiondriver.BaseActionDriver;
import com.appium.utill.actiondriver.MobileActionDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

@Listeners(TestNgListeners.class)
public abstract class AppTestCase {
	protected abstract void cleanPageObjects();
	public AppiumDriverLocalService service;
	public String deviceName;
	public BaseActionDriver actionDriver;
	public CommonUtil common;
	public ExtentTest test;
	public static int WAIT_TIMEOUT;
	public String testCaseName;
	public Throwable Error = null;
	public int counter;
	private String testId;

	public static ExtentReports reports;


	protected static ThreadLocal<BaseActionDriver> actionDriverThread = new ThreadLocal<BaseActionDriver>();
	protected static ThreadLocal<CommonUtil> commonThread = new ThreadLocal<CommonUtil>();
	protected static ThreadLocal<ExtentTest> testThread = new ThreadLocal<ExtentTest>();
	
	public static ExtentTest getTest() {
		return testThread.get();
	}

	public static CommonUtil getCommon() {
		return commonThread.get();
	}

	public static BaseActionDriver getActionDriver() {
		return actionDriverThread.get();
	}
	
	@BeforeClass
	public void configureAppium() throws IOException {
		
		Map<String , String> env = new HashMap<String , String>(System.getenv());
		env.put("ANDROID_HOME", "/Users/cruyfj/Library/Android/sdk");
		env.put("JAVA_HOME", "/Applications/Android Studio.app/Contents/jbr/Contents/Home");
		env.put("PATH", "/usr/local/bin");
		env.put("SDKROOT", "/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/SDKs/iPhoneOS.sdk");
		env.put("PATH", "/Applications/Xcode.app");
		env.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
		this.service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
				.withIPAddress("127.0.0.1").usingPort(4723).withEnvironment(env).withTimeout(Duration.ofSeconds(300)).build();
		service.start();




	}
	
	@BeforeMethod(groups = { "smoke", "prod" }, alwaysRun = true)
	@Parameters({ "device" })
	public synchronized void setUp(@Optional String device, Method method) throws Exception {
		try {

			testCaseName = method.getName();
			actionDriver.setDeviceName(deviceName);
			actionDriver.setTestCaseName(testCaseName);
			testThread.set(reports.startTest(actionDriver.getDeviceName() + " " + common.testCaseId + "-" + testCaseName, ""));
			test = testThread.get();
			common.initializeLogs(test);
			counter = counter + 1;
			if(actionDriver.getDeviceName().equalsIgnoreCase("Android")) {
				common.initializeAppiumDriver(deviceName);
			}else if(actionDriver.getDeviceName().equalsIgnoreCase("iOS")) {
				common.initializeAppiumDriver(deviceName);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			if (actionDriver.getAppiumDriver() != null) {
				actionDriver.quit();
			}

			throw new SkipException("Skipping test case due to following error : " + t.getMessage());
		}

	}

	public void initializeAppTest(String testId, String device) throws Exception {
		actionDriverThread.set(new MobileActionDriver());
		commonThread.set(new CommonUtil(getActionDriver()));
		getActionDriver().initializeLogging();
		getCommon().testCaseId = testId;
		this.setTestId(testId);
		CommonUtil.setInitialConfigurations(device);
		deviceName =device;
		this.common = getCommon();
		this.actionDriver = getActionDriver();
	}
	

	

	@AfterMethod(alwaysRun = true)
	public synchronized void tearDown() {
		reports.flush();
		//closeDriver();

	}

	public void closeDriver() {
		this.actionDriver.getAppiumDriver().quit();

	}
	
	@AfterClass(alwaysRun=true)
	public synchronized void stopService() {
		service.stop();
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	


}
