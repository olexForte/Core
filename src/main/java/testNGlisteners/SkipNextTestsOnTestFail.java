package testNGlisteners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import com.relevantcodes.extentreports.LogStatus;
import utils.ReportManager;

public class SkipNextTestsOnTestFail implements ITestListener {

	private boolean b = true;


	public void onTestStart(ITestResult result) {
		if (b == false) {
			ReportManager.Logger().log(LogStatus.SKIP, "Skipped because one of the tests has FAILED");
			throw new SkipException("Skipped");
		}

	}

	public void onTestSuccess(ITestResult result) {
		b = true;

	}

	public void onTestFailure(ITestResult result) {
		System.out.println(
				"[INFO] Invoked " + getClass().getSimpleName() + " listener: this test has FAILED. Next tests will be skipped");		
		b = false;
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}


	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}


	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}


	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
