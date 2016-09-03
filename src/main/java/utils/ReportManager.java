package utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class ReportManager {

	private static Map<Long, ExtentTest> testThread = new HashMap<Long, ExtentTest>();
	private static ExtentReports extent;

	private synchronized static ExtentReports getInstance() {
		if (extent == null) {
			extent = new ExtentReports("./Report.html", true, NetworkMode.ONLINE);
		}
		return extent;
	}

	public synchronized static Map<Long, ExtentTest> startTest(Method m, String testName, String testDescription,
			String browser) {
		Long threadID = Thread.currentThread().getId();
		ExtentTest test = getInstance().startTest(testName, testDescription);
		test.assignCategory(browser);
		for (String gr : getTestGroups(m)) {
			test.assignCategory(gr);
		}
		testThread.put(threadID, test);
		return testThread;
	}

	public synchronized static ExtentTest Logger() {
		ExtentTest logger = null;
		Long threadID = Thread.currentThread().getId();
		if (testThread.containsKey(threadID)) {
			logger = testThread.get(threadID);
		}
		return logger;
	}

	public synchronized static void closeTest() {
		getInstance().endTest(Logger());
	}

	public synchronized static void closeReporter() {
		getInstance().flush();
	}

	public static String getTestName(Method m) {
		String testName = null;
		String address = null;
		String[] testGroups = m.getAnnotation(Test.class).groups();
		for (int i = 0; i < testGroups.length; i++) {
			if (testGroups[i].startsWith("http")) {
				address = testGroups[i];
			}
		}
		if (address != null) {
			testName = "<a href=" + "\"" + address + "\""
					+ "target=_blank alt=This test is linked to test case. Click to open it>"
					+ m.getAnnotation(Test.class).testName() + "</a>";
		} else {
			testName = m.getAnnotation(Test.class).testName();
		}

		if (testName == null || testName.equals("")) {
			testName = m.getName() + new Random().nextInt(10000000);
		}
		return testName;
	}

	private String getTestDescription(Method m) {
		String testDescription = null;
		testDescription = m.getAnnotation(Test.class).description();
		if (testDescription == null || testDescription.equals("")) {
			testDescription = "";
		}
		return testDescription;
	}

	private static String[] getTestGroups(Method m) {
		String[] testGroups = m.getAnnotation(Test.class).groups();
		if (testGroups == null || testGroups.equals("")) {
			testGroups[0] = "";
		}
		return testGroups;
	}

	public void startReporting(Method m, String browser) {
		startTest(m, getTestName(m), getTestDescription(m), browser);
		String testGroups = "";
		for (String gr : getTestGroups(m)) {
			testGroups = testGroups + gr + "; ";
		}
		System.out.println(
				"--------------------------------------------------------------------------------------------------------");
		System.out.println("[INFO] Started test '" + getTestName(m) + "' Current browser '" + browser + "' Groups: '"
				+ testGroups.trim() + "'");
	}

	public void stopReporting(ITestResult result) {
		closeTest();
		int res = result.getStatus();
		switch (res) {
		case 1: {
			System.out.println("[INFO] Test method finished with status: PASSED");
			break;
		}
		case 2: {
			System.out.println("[INFO] Test method finished with status: FAILED");
			break;
		}
		case 3: {
			Logger().log(LogStatus.SKIP, "Skipped: something went wrong");
			System.out.println("[INFO] Test method finished with status: SKIPPED");
			break;
		}
		}
	}

	public static void logInfo(String details) {
		System.out.println("[INFO] " + details);
		Logger().log(LogStatus.INFO, details);
	}

	public static void logStatusPass(String details) {
		Logger().log(LogStatus.PASS, details);
	}

	public static void logStatusFail(String details) {
		Logger().log(LogStatus.FAIL, details);
	}

	public static void logStatusFailWithScreenshot(String details) {
		Logger().log(LogStatus.FAIL, details + "<br><a href=" + "\"" + Screenshot.take() + "\"" + "target=_blank alt>"
				+ "SCREENSHOT" + "</a><br>");
	}

	public static void logStatusFailWithScreenshot(String details, Exception e) {
		String exceptionString = "";
		if (e.getCause() == null) {
			exceptionString = e.getMessage();
		} else {
			exceptionString = e.getCause().toString();
		}
		Logger().log(LogStatus.FAIL, details + "<br><a href=" + "\"" + Screenshot.take() + "\"" + "target=_blank alt>"
				+ "SCREENSHOT" + "</a><br>" + "<pre>" + exceptionString + "</pre>");
	}

	public static void logStatusFailWithScreenshot(String details, AssertionError e) {
		String exceptionString = e.getMessage();
		Logger().log(LogStatus.FAIL, details + "<br><a href=" + "\"" + Screenshot.take() + "\"" + "target=_blank alt>"
				+ "SCREENSHOT" + "</a><br>" + "<pre>" + exceptionString + "</pre>");
	}

}