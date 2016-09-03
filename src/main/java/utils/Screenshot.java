package utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Screenshot {

	public static String take() {
		String path = "./screenshots/" + ""+ new Random().nextInt(100000) + ".png";
		TakesScreenshot screen = (TakesScreenshot) WebBrowser.Driver();
		File source = screen.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}