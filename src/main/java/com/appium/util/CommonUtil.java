package com.appium.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

import org.testng.Reporter;

import com.appium.setup.AppTestCase;
import com.appium.util.actiondriver.BaseActionDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class CommonUtil {

	private BaseActionDriver actionDriver;
	public ExtentTest test;
	public String testCaseId = null;
	public static boolean isInitialConfigurationDone = false;
	public static boolean isReportInitialized = false;
	public static String MAIN_DIR;
	public static Properties CONFIG;
	private String sessionId;
	public static FileInputStream fn;
	public String screenShotName = null;
	public String deviceName;

	public CommonUtil(BaseActionDriver actionDriver) {
		this.actionDriver = actionDriver;
	}
	
	public CommonUtil() {
		
	}
	
	public synchronized static void setInitialConfigurations(String device) throws Exception {
		if (!isInitialConfigurationDone) {
			isInitialConfigurationDone = true;
			System.out.println("Initializing Selenium files path");
			try {
				CONFIG = new Properties();
				String baseFilePath = System.getProperty("user.dir") + "/src/main/resources/config/";
				String filePath = null;
				filePath = baseFilePath + "config.properties";
				fn = new FileInputStream(filePath);
				CONFIG.load(fn);

				AppTestCase.WAIT_TIMEOUT = Integer.parseInt("10");
			} catch (Exception e) {
				throw new Exception("Environment configuration is not set " + e.getMessage());
			} finally {
				isInitialConfigurationDone = true;
			}

		}
		if (!isReportInitialized) {
			isReportInitialized = true;
			// reports cleaning
			CommonUtil.cleanOldReports(device);
			System.out.println("Setting up extent Report object");
			
			AppTestCase.reports = new ExtentReports("./"+device + "AutomationReport/" + "/index.html");

			
		}
	}

	public static String getKeyValue(String key) {
			return CONFIG.getProperty(key);

	}

	public void initializeAppiumDriver(String device) throws Exception {
		log("Initializing Appium Driver");

		try {
			deviceName=device;
			actionDriver.setDeviceName(deviceName);

			if(device.equals("Android")){
				
				UiAutomator2Options options=new UiAutomator2Options();
				options.setDeviceName(CommonUtil.CONFIG.getProperty(ConfigKey.DEVICE_NAME));
				options.setPlatformName("Android");
				options.setCapability("platformVersion",CommonUtil.CONFIG.getProperty(ConfigKey.OS_VERSION));
				options.setAutomationName("UiAutomator2");
				options.setCapability("unicodeKeyboard", true);
				options.setCapability(UiAutomator2Options.AUTO_GRANT_PERMISSIONS_OPTION, true);
				options.setCapability(UiAutomator2Options.NO_RESET_OPTION, true);
				options.setCapability(UiAutomator2Options.FULL_RESET_OPTION, false);
				options.setCapability(UiAutomator2Options.APP_PACKAGE_OPTION,CommonUtil.CONFIG.getProperty(ConfigKey.APP_PACKAGE));
				options.setCapability(UiAutomator2Options.APP_ACTIVITY_OPTION,CommonUtil.CONFIG.getProperty(ConfigKey.MAIN_ACTIVITY));
				
				//create object for AndroidDriver/ IOSDriver 
				actionDriver.setAppiumDriver(new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options));
				
			}else if(device.equals("iOS")) {
				XCUITestOptions options = new XCUITestOptions();
				options.setPlatformName("iOS");
				
				options.setDeviceName("iPhone");
				options.setPlatformVersion("17.0");
				options.setApp("./src/test/java/resources/HeyHorse.app");
				options.setCapability("automationName", "XCUITest");
				//options.setCapability(XCUITestOptions.USE_JSON_SOURCE_OPTION, true);
				options.setCapability(XCUITestOptions.SIMPLE_IS_VISIBLE_CHECK_OPTION, true);
				options.setCapability("autoAcceptAlerts", true); // Automatically accept system alerts

				/*
				 * options.setCapability("noReset", true);
				 * options.setCapability("usePrebuiltWDA", true);
				 * options.setCapability("useNewWDA", true);
				 */
				
				
				actionDriver.setAppiumDriver(new IOSDriver(new URI("http://0.0.0.0:4723").toURL(),options));
			}	
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Exception(t);
		}
	}
	
	public void testNgLogger(Object log) {
		Reporter.log(log + "---------<p>", true);
	}

	public void log(Object log) {
		Reporter.log(log + "---------<p>", true);
		if (test != null) {
			test.log(LogStatus.INFO, log.toString());

		}

	}
	

	public void logScreen(Object log) {
		Reporter.log(log + "---------<p>", true);
		if (test != null) {
			test.log(LogStatus.INFO, log.toString());

		}
		addScreenShot(deviceName);

	}

	public void logFail(Object log) {
		AppTestCase.getTest().log(LogStatus.FAIL, log.toString());
		addScreenShot(deviceName);
	}


	
	public void addScreenShot(String deviceName) {
		if (deviceName != null) {

			if (sessionId == null) {
				screenShotName = testCaseId + "_" + deviceName + "_" + RandomUtil.getRandomNumber(12) + ".jpeg";
			} else {
				screenShotName = sessionId + ".jpeg";
			}
			actionDriver.takeScreenShot(MAIN_DIR + screenShotName);
			try {
				test.log(LogStatus.INFO, "Screenshot attached below: " + test.addScreenCapture(screenShotName));
			} catch (Throwable e) {
				e.printStackTrace();
				test.log(LogStatus.FAIL, "Error while attaching screenshot " + e.getMessage());
			}
		}
	}

	
	public void initializeLogs(ExtentTest test) {
		this.test = test;
	}

	public String getXpath(String text) {
		return "//*[contains(text()," + "\"" + text + "\"" + ")]";
	}
	
	public static void cleanOldReports(String deviceName) throws Exception {
		try {
			System.out.println("Cleaning older selenium reports");
			try {
				MAIN_DIR = System.getProperty("user.dir") + "/"+deviceName + "AutomationReport/";
				System.out.println("MAIN_DIR " + MAIN_DIR);
				File dir1 = new File(MAIN_DIR);
				boolean exists = dir1.exists();
				if (!exists) {
					System.out.println("the main directory you are searching does not exist : " + exists);
					dir1.mkdir(); // creating main directory if it doesn't exist
					// new File(subDir).mkdir(); // creating time-stamped
					// sub-directory
				} else {
					System.out.println("the main directory you are searching does exist : " + exists);
					// new File(subDir).mkdir();
				}
			} catch (Throwable t) {
				t.printStackTrace();
				System.out.println("FAILS TO CREATE REPORT FOLDERS");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			throw new Exception(t);
		}

		deleteDir(new File(MAIN_DIR));

	}
	

	public static boolean deleteDir(final File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = new File(dir, children[i]).delete();
				if (!success) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void updateSeleniumReport(Throwable t, BaseActionDriver actionDriver, int counter, String deviceName) {
		t.printStackTrace();

		addScreenShot(deviceName);
	}

	public void failSeleniumTest(Throwable t, BaseActionDriver actionDriver, int counter, String deviceName) {
		//t.printStackTrace();

		addScreenShot(deviceName);
	}

	public void skipSeleniumTest(Throwable t, BaseActionDriver actionDriver, int counter, String deviceName) {
		t.printStackTrace();

		addScreenShot(deviceName);
	}
	
	@SuppressWarnings("unused")
	public void passSeleniumTest(Throwable t, BaseActionDriver actionDriver, int counter, String deviceName) {

		test.log(LogStatus.PASS, "Ejecucion finalizada con exito");
		addScreenShot(deviceName);
	}


	
}
