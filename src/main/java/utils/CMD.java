package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

public class CMD {

	public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
	
	public static String executeCommandAndProceed(String command) {
		StringBuffer output = new StringBuffer();
		Runtime r = Runtime.getRuntime();
		try {
			Process p = r.exec(command);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public static void RunUftTest(String testName) throws FileNotFoundException {
		System.out.println("[INFO] Trying to launch UFT Test");
		ReportManager.Logger().log(LogStatus.INFO, "Trying to launch UFT Test");
		executeCommand("cmdrv -usr \"D:\\UFTworkspace\\HappyPath\\" + testName + "\\" + testName + ".usr\"");
			if (!TxtFilesUtils.searchInFile("D:\\UFTworkspace\\HappyPath\\" + testName + "\\output.txt", "Error:")) {
				ReportManager.Logger().log(LogStatus.PASS, "UFT Test '" + testName + "' Passed");
				System.out.println("[INFO] UFT Test Passed");
			} else {
				ReportManager.Logger().log(LogStatus.FAIL, "UFT Test '" + testName + "' Failed");
				System.out.println("[FAILED] UFT Test Failed");
				Assert.fail("UFT Test " + testName + " Failed");
			}
		ReportManager.Logger().log(LogStatus.INFO, "UFT Test finished");
		System.out.println("[INFO] UFT Test finished");
	}

}
