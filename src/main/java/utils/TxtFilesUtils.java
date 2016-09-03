package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TxtFilesUtils {

	public static boolean searchInFile(String fileName, String searchStr) throws FileNotFoundException {
		boolean b = false;
		Scanner scan = new Scanner(new File(fileName));
		while (scan.hasNext()) {
			String line = scan.nextLine().toString();
			if (line.contains(searchStr)) {
				System.out.println("[INFO] " + line);
				b = true;
			}
		}
		scan.close();
		return b;
	}

}
