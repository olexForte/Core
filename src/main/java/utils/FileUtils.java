package utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

	public static boolean renameFileExtension(String source, String newExtension) {
		String target;
		String currentExtension = getFileExtension(source);

		if (currentExtension.equals("")) {
			target = source + "." + newExtension;
		} else {
			target = source.replaceFirst(Pattern.quote("." + currentExtension) + "$",
					Matcher.quoteReplacement("." + newExtension));

		}
		return new File(source).renameTo(new File(target));
	}

	public static String getFileExtension(String f) {
		String ext = "";
		int i = f.lastIndexOf('.');
		if (i > 0 && i < f.length() - 1) {
			ext = f.substring(i + 1);
		}
		return ext;
	}
	
	public static void deleteFolder(String path) {
		File file = new File(path);
		System.out.println("[INFO] Trying to delete folder " + file.getAbsolutePath());
		try {
			File[] files = file.listFiles();
			for (File f : files) {
				f.delete();
			}
			if (file.isDirectory()) {
				file.delete();
				System.out.println("[INFO] Deleted folder " + file.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println("[INFO] No such file or directory " + file.getAbsolutePath());
		}
	}
	
	public static String getRootFolderPath()
    {
        String root = "";

        try
        {
            root = System.getProperty("user.dir");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return root;
    }

}
