package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;
import io.appium.java_client.android.AndroidDriver;

public class WebBrowser extends ReportManager {

	private static final String driverExecutablePath = FileUtils.getRootFolderPath() + File.separator + ".."
			+ File.separator + "Core" + File.separator + "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "drivers" + File.separator;
	private String browserName;
	private WebDriver driver;
	private AppiumServer appiumServer;
	private SoftAssert softAssert;
	private String appiumPort;
	private static ThreadLocal<String> threadLocalAppiumPort = new ThreadLocal<String>();
	private static ThreadLocal<AppiumServer> threadLocalAppiumServer = new ThreadLocal<AppiumServer>();
	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<String> threadLocalBrowserName = new ThreadLocal<String>();
	private static ThreadLocal<SoftAssert> threadLocalSoftAssert = new ThreadLocal<SoftAssert>();

	@BeforeSuite
	public void deleteScreenShotFolder() {
		System.out.println("[INFO] Clearing screenshot folder...");
		FileUtils.deleteFolder("./screenshots");
		System.out.println("[INFO] Screenshot folder cleared");
	}

	@Parameters({ "appiumPort", "bootstrapPort", "chromePort", "browser", "deviceName", "deviceId", "emulatorPort" })
	@BeforeTest(alwaysRun = true)
	public void runAppiumServer(@Optional(value = "") String appiumPort, @Optional(value = "") String bootstrapPort,
			@Optional(value = "") String chromePort, @Optional(value = "") String browser,
			@Optional(value = "") String deviceName, @Optional(value = "") String deviceId,
			@Optional(value = "") String emulatorPort) {
		if (browser.equalsIgnoreCase("Mobile") && !deviceId.equalsIgnoreCase("")) {
			AndroidDevicesUtils.startDevice(deviceName, emulatorPort, deviceId, 30000);
			startAppiumServer(appiumPort, bootstrapPort, chromePort);
			this.appiumPort = appiumPort;
			threadLocalAppiumPort.set(this.appiumPort);
			System.out.println("[INFO] Appium server started on port " + getCurrentAppiumPort());
		}
	}

	@SuppressWarnings("rawtypes")
	@Parameters({ "browser", "deviceId", "OSv" })
	@BeforeMethod(alwaysRun = true)
	public void initWebBrowser(@Optional(value = "Chrome") String browser, @Optional(value = "") String deviceId,
			@Optional(value = "") String OSv, Method m) {
		if (System.getProperty("browser") != null) {
			if (System.getProperty("browser").equals("Default")) {
				browser = "Chrome";
			} else {
				browser = System.getProperty("browser");
			}
		}
		startReporting(m, browser);
		browserName = browser;
		if (browser.equalsIgnoreCase("Firefox")) {
			driver = new FirefoxDriver();
			System.out.println("[INFO] Firefox has started");
		} else if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", driverExecutablePath + "chromedriver.exe");
			// String downloadFilePath = "D:\\workspace\\";
			// HashMap<String, Object> chromePrefs = new HashMap<String,
			// Object>();
			// chromePrefs.put("profile.default_content_settings.popups", 0);
			// chromePrefs.put("download.default_directory", downloadFilePath);
			// options.setExperimentalOption("prefs", chromePrefs);
			// DesiredCapabilities cap = DesiredCapabilities.chrome();
			// cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			// cap.setCapability(ChromeOptions.CAPABILITY, options);
			// options.addArguments("test-type");
			// driver = new ChromeDriver(cap);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("no-sandbox");
			driver = new ChromeDriver(options);
			System.out.println("[INFO] Chrome has started");
		} else if (browser.equalsIgnoreCase("IE11")) {
			System.setProperty("webdriver.ie.driver", driverExecutablePath + "IEDriverServer.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("nativeEvents", false);
			driver = new InternetExplorerDriver(capabilities);
			System.out.println("[INFO] Internet Explorer has started");
		} else if (browser.equalsIgnoreCase("Mobile")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", deviceId);
			capabilities.setCapability("udid", deviceId);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", OSv);
			capabilities.setCapability("browserName", "Chrome");
			try {
				driver = new AndroidDriver(new URL("http://127.0.0.1:" + getCurrentAppiumPort() + "/wd/hub"),
						capabilities);
				System.out.println("[INFO] Mobile browser started");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		softAssert = new SoftAssert();
		threadLocalSoftAssert.set(softAssert);
		threadLocalDriver.set(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if (!browser.equalsIgnoreCase("Mobile")) {
			driver.manage().window().setSize(new Dimension(1600, 900));
		}
		threadLocalBrowserName.set(browserName);

	}

	public static WebDriver Driver() {
		return threadLocalDriver.get();
	}

	public static SoftAssert getSoftAssert() {
		return threadLocalSoftAssert.get();
	}

	public static String getCurrentBrowserName() {
		return threadLocalBrowserName.get();
	}

	@AfterMethod(alwaysRun = true)
	public void closeWebBrowser(ITestResult result) {
		if (driver != null) {
			driver.quit();
			try {
				Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			} catch (IOException e) {
				e.printStackTrace();
			}
			threadLocalDriver.remove();
			System.out.println("[INFO] Browser closed");
		}
		stopReporting(result);
	}

	@Parameters({ "deviceId" })
	@AfterTest(alwaysRun = true)
	public void stopAppiumServer(@Optional(value = "") String deviceId) {
		if (appiumServer != null) {
			if (appiumServer.isServerRunning()) {
				appiumServer.stopServer();
				threadLocalAppiumServer.remove();
				threadLocalAppiumPort.remove();
				System.out.println("[INFO] Appium Server stopped");
			}
		}
		if (deviceId.toLowerCase().contains("emulator")) {
			CMD.executeCommand("adb -s " + deviceId + " emu kill");
			System.out.println("[INFO] Emulator " + deviceId + " turned off");
		}
	}

	@AfterSuite(alwaysRun = true)
	public void flushReporter() {
		closeReporter();
	}

	private void startAppiumServer(String appiumPort, String bootstrapPort, String chromePort) {
		ServerArguments serverArguments = new ServerArguments();
		serverArguments.setArgument("--address", "127.0.0.1");
		serverArguments.setArgument("--chromedriver-port", chromePort);
		serverArguments.setArgument("--bootstrap-port", bootstrapPort);
		serverArguments.setArgument("--port", appiumPort);
		appiumServer = new AppiumServer(serverArguments);
		threadLocalAppiumServer.set(appiumServer);
		threadLocalAppiumServer.get().startServer();
	}

	private String getCurrentAppiumPort() {
		return threadLocalAppiumPort.get();
	}
}