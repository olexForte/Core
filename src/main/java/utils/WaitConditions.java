package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WaitConditions {

	public static String waitUntilTextAppearsInElement(By locator, String textToAppear, int timeOutSec) {
		System.out.println("[INFO] waitUntilTextAppearsInElement condition is invoked");
		boolean b = false;
		String result = null;
		WebDriverWait wait = new WebDriverWait(WebBrowser.Driver(), timeOutSec);
		try {
			b = wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, textToAppear));
			if (b) {
				result = textToAppear;
			} else {
				result = null;
			}
		} catch (Exception e) {
			ReportManager.logStatusFailWithScreenshot(
					"Cannot find element with text: '" + textToAppear + "' after " + timeOutSec + " of waiting", e);
			System.out.println(
					"[INFO] Cannot find element with text: '" + textToAppear + "' after " + timeOutSec + " of waiting");
			Assert.fail("Fail", e.getCause());
		}
		return result;
	}

}
