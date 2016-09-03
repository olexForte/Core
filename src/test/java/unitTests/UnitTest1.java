package unitTests;

import org.testng.annotations.Test;
import utils.MainClass;

public class UnitTest1 extends MainClass {
	
		
	@Test(testName = "Google3", description = "This is unit Test", groups = {"Unit", "Unit 1", "Unit 2"})
	public void unitTest1() {
		getPage("http://google.com");
	}
}
