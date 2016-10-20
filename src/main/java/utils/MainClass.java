package utils;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

public class MainClass extends WebBrowser {

	private static final int WAITTIMEOUT = 3;

	private static WebElement getEl(By by) {
		System.out.println("[INFO] Trying to get element Selector: '" + by.toString() + "'");
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		System.out.println("[INFO] Gotten element Selector: '" + by.toString() + "'");
		return element;
	}

	public static void getPage(String pageAddress) {
		try {
			Driver().get(pageAddress);
			logStatusPass("Redirected to '" + pageAddress + "'");
			System.out.println("[INFO] Redirected to '" + pageAddress + "'");
		} catch (Exception e) {
			logInfo("Redirecting to '" + pageAddress + "' ...");
			System.out.println("[INFO] Redirecting to '" + pageAddress + "' ...");
			logStatusFailWithScreenshot("Page is not available", e);
			System.out.println("[FAIL] Page is not available");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}

	}

	public static String getCurrUrl() {
		String currAddress = Driver().getCurrentUrl();
		String title = Driver().getTitle();
		if (title.contains("is not available") || title.contains("Problem loading page")) {
			Logger().log(LogStatus.FATAL, title + Logger().addScreenCapture(Screenshot.take()));
		}
		return currAddress;
	}

	public static WebElement getElement(By by) {
		WebElement element = null;
		try {
			element = getEl(by);
			System.out.println("[INFO] Gotten element Selector: '" + by.toString() + "'");
		} catch (Exception e) {
			logInfo("Trying to get element Selector: '" + by.toString() + "'");
			System.out.println("[INFO] Trying to get element Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot get element", e);
			System.out.println("[FAIL] Cannot get element Selector: '" + by.toString() + "'");
			e.printStackTrace();
		}
		return element;
	}

	public static List<WebElement> getElements(By by) {
		List<WebElement> elements = null;
		try {
			WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
			elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			System.out.println("[INFO] Gotten elements Selector: '" + by.toString() + "'");
		} catch (Exception e) {
			logInfo("Trying to get element Selector: '" + by.toString() + "'");
			System.out.println("[INFO] Trying to get element Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot get elements", e);
			System.out.println("[FAIL] Cannot get element Selector: '" + by.toString() + "'");
			e.printStackTrace();
		}
		return elements;
	}

	public static void clickOn(By by, String elementName) {
		WebElement element = null;

		boolean b = false;
		WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
		try {
			element = wait.until(ExpectedConditions.elementToBeClickable(getEl(by)));
			for (int i = 1; i <= WAITTIMEOUT; i++) {
				try {
					element.click();
					logStatusPass("Clicked on '" + elementName + "'");
					System.out.println("[INFO] Clicked on '" + elementName + "' Selector: '" + by.toString() + "'");
					break;
				} catch (Exception e1) {
					System.out.println("[INFO] Waiting until element to be clickable...");
					Thread.sleep(1000);
					if (i == WAITTIMEOUT) {
						logInfo("Trying to click on '" + elementName + "' ...");
						System.out.println(
								"[INFO] Trying to click on '" + elementName + "' Selector: '" + by.toString() + "'");
						logStatusFailWithScreenshot("Cannot click on element '" + elementName + "'", e1);
						System.out.println("[FAIL] Cannot click on element '" + elementName + "' Selector: '"
								+ by.toString() + "'");
						e1.printStackTrace();
						Assert.fail("Failed", e1.fillInStackTrace());
					}
				}
			}
		} catch (Exception e) {
			logInfo("Trying to click on '" + elementName + "' ...");
			System.out.println("[INFO] Trying to click on '" + elementName + "' Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot click on element '" + elementName + "'", e);
			System.out
					.println("[FAIL] Cannot click on element '" + elementName + "' Selector: '" + by.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
	}

	public static void enterText(By by, String text) {

		WebElement element = null;
		try {
			element = getEl(by);
			element.clear();
			element.sendKeys(text);
			logStatusPass("Entered text '" + text + "'");
			System.out.println("[INFO] Entered text '" + text + "' to element Selector: '" + by.toString() + "'");
		} catch (Exception e) {
			logInfo("Trying to enter text '" + text + "'...");
			System.out
					.println("[INFO] Trying to enter text '" + text + "' to element Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot enter text '" + text + "'", e);
			System.out.println("[FAIL] Cannot enter text '" + text + "' to element Selector: '" + by.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
	}

	public static void switchToFrame(String frameId) {
		try {
			System.out.println("[INFO] Trying to switch to frame '" + frameId + "'");
			Driver().switchTo().frame(frameId);
			System.out.println("[INFO] Switched to frame '" + frameId + "'");
		} catch (Exception e) {
			logInfo("Trying to switch to frame '" + frameId + "'");
			System.out.println("[INFO] Trying to switch to frame '" + frameId + "'");
			logStatusFailWithScreenshot("Cannot find frame: '" + frameId + "'", e);
			System.out.println("[FAILED] Cannot find frame: '" + frameId + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
	}

	public static void sleepFor(int msec) {
		try {
			System.out.println("[INFO] Sleeping for " + msec + " milliseconds");
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver switchToTab(int i) {
		WebDriver tab = null;
		try {
			System.out.println("[INFO] Trying to switch to tab " + i);
			tab = Driver().switchTo().window(getTabs().get(i));
			logStatusPass("Switched to tab '" + tab.getTitle() + "'");
			System.out.println("[INFO] Switched to tab '" + tab.getTitle() + "'");
		} catch (Exception e) {
			logInfo("Trying to switch to tab " + i);
			System.out.println("[INFO] Trying to switch to tab " + i);
			logStatusFailWithScreenshot("Cannot switch to tab " + i, e);
			System.out.println("[FAIL] Cannot switch to tab " + i);
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
		return tab;
	}

	private static List<String> getTabs() {
		List<String> windows = new ArrayList<String>(Driver().getWindowHandles());
		return windows;
	}

	public static void closeTab(int i) {
		switchToTab(i).close();
		logStatusPass("Tab closed");
		System.out.println("[INFO] Tab closed");
	}

	public static String getElementAtt(By by, String attName) {

		String att = null;
		try {
			att = getEl(by).getAttribute(attName);
			System.out.println("[INFO] Gotten attribute '" + attName + ". Value: '" + att
					+ "' from elemnent (selector: '" + by.toString() + "')");
		} catch (Exception e) {
			logInfo("Trying to get attribute '" + attName + "'");
			System.out.println(
					"[INFO] Trying to get attribute '" + attName + "' from elemnent Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot get attribute '" + attName + "'", e);
			System.out.println(
					"[FAIL] Cannot get attribute '" + attName + "' from elemnent Selector: '" + by.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
		return att;
	}

	public static String getElementText(By by) {

		String text = null;
		try {
			text = getEl(by).getText();
			System.out.println("[INFO] Gotten text: '" + text + "' from elemnent Selector: '" + by.toString() + "')");
		} catch (Exception e) {
			logInfo("Trying to get text...");
			System.out.println("Trying to get text '" + text + "' from elemnent Selector: '" + by.toString() + "'");
			logStatusFailWithScreenshot("Cannot get text", e);
			System.out
					.println("[FAILED] Cannot get text '" + text + "' from elemnent Selector: '" + by.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
		return text;
	}

	public static void switchToDefaultFrame() {
		Driver().switchTo().defaultContent();
		System.out.println("[INFO] Switched to default frame");
	}

	public static void assertEquals(Object actual, Object expected) {
		System.out.println("[INFO] Assertion method is invoked.");
		try {
			Assert.assertEquals(actual, expected);
			logStatusPass("TRUE");
			System.out.println("[INFO] Assertion status: TRUE");
		} catch (AssertionError e) {
			logStatusFailWithScreenshot("Expected: '" + expected + "' Actual: '" + actual + "'", e);
			System.out.println("[FAIL] Expected: '" + expected + "' Actual: '" + actual + "'");
			Assert.fail("Failed", e.fillInStackTrace());
		}
	}

	public static void assertTrue(boolean actual) {
		System.out.println("[INFO] Assertion method is invoked.");
		try {
			Assert.assertTrue(actual);
			logStatusPass("TRUE");
			System.out.println("[INFO] Assertion status: TRUE");
		} catch (AssertionError e) {
			logStatusFailWithScreenshot("FALSE", e);
			System.out.println("[FAIL] Assertion status: FALSE");
			e.printStackTrace();
			Assert.fail("Failed", e.fillInStackTrace());
		}
	}

	public static boolean isElementDisplayed(By by) {
		WebElement element = null;
		boolean b = false;
		try {
			element = getEl(by);
		} catch (Exception e) {
		}
		if (element.isDisplayed()) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	public static void selectFromDropdownText(By dropDownIdent, String text) {

		try {
			Select oSelection = new Select(getEl(dropDownIdent));
			oSelection.selectByVisibleText(text);
			logStatusPass("Selected '" + text + "'");
			System.out.println(
					"[INFO] Selected '" + text + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
		} catch (Exception e) {
			logInfo("Trying to select '" + text + "' from dropdown");
			System.out.println("[INFO] Trying to select '" + text + "' from dropdown Selector: '"
					+ dropDownIdent.toString() + "'");
			logStatusFailWithScreenshot("Cannot select '" + text + "' from dropdown", e);
			System.out.println(
					"[FAILED] Cannot select '" + text + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.getCause());
		}
	}

	public static void selectFromDropdownValue(By dropDownIdent, String value) {

		try {
			Select oSelection = new Select(getEl(dropDownIdent));
			oSelection.selectByValue(value);
			logStatusPass("Selected '" + value + "' from dropdown");
			System.out.println(
					"[INFO] Selected '" + value + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
		} catch (Exception e) {
			logInfo("Trying to select '" + value + "' from dropdown Selector: '" + dropDownIdent.toString() + "'");
			System.out.println("[FAILED] Trying to select '" + value + "' from dropdown Selector: '"
					+ dropDownIdent.toString() + "'");
			logStatusFailWithScreenshot(
					"Cannot select '" + value + "' from dropdown Selector: '" + dropDownIdent.toString() + "'", e);
			System.out.println("[FAILED] Cannot select '" + value + "' from dropdown Selector: '"
					+ dropDownIdent.toString() + "'");
			e.printStackTrace();
			Assert.fail("Failed", e.getCause());
		}
	}

	public static void waitForAlert() {
		WebDriverWait wait = new WebDriverWait(Driver(), WAITTIMEOUT);
		wait.until(ExpectedConditions.alertIsPresent());
		System.out.println("[INFO] Waiting for alert...");
		Alert alert = Driver().switchTo().alert();
		alert.accept();
		System.out.println("[INFO] Alert accepted");
	}

	public static void verifyTrue(boolean verification) {
		System.out.println("[INFO] Verification method is invoked.");
		if (verification) {
			logStatusPass("TRUE");
			System.out.println("[INFO] Verification status: TRUE");
		} else {
			logStatusFailWithScreenshot("FALSE");
			System.out.println("[FAIL] Verification status: FALSE");
			getSoftAssert().fail();
		}
	}

	public static void verifyEquals(Object actual, Object expected) {
		System.out.println("[INFO] Verification method is invoked.");
		if (actual.equals(expected)) {
			logStatusPass("TRUE");
			System.out.println("[INFO] Verification status: TRUE");
		} else {
			logStatusFailWithScreenshot("FALSE. Expected: '" + expected + "' Actual: '" + actual + "'");
			System.out.println("[FAIL] Expected: '" + expected + "' Actual: '" + actual + "'");
			getSoftAssert().assertEquals(actual, expected);
		}
	}

	public static boolean isElementDisplayed(By by, boolean b) {
		WebElement element = null;
		try {
			element = getEl(by);
		} catch (Exception e) {
		} finally {
			if (element == null && b == false) {
				b = true;
			} else if (element != null && b == true) {
				try {
					b = element.isDisplayed();
				} catch (Exception e) {
					b = false;
				}
			} else {
				b = false;
			}
		}
		return b;
	}

	public static void verifyAll() {
		getSoftAssert().assertAll();
	}

}