package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageUtils {

	public static boolean compareImages(String img1Path, String img2Path) {
		int c = 1;
		int b = 0;
		int g = 0;
		float passPercentage = 0;
		boolean f = false;
		int height = 0;
		int width = 0;
		File img1 = new File(img1Path);
		File img2 = new File(img2Path);
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		try {
			System.out.println("[INFO] trying to get image: '" + img1Path + "'");
			image1 = ImageIO.read(img1);
			System.out.println("[INFO] Gotten image " + img1Path);
			System.out.println("[INFO] trying to get image " + img2Path + "'");
			image2 = ImageIO.read(img2);
			System.out.println("[INFO] Gotten image " + img2Path);
			System.out.println("[INFO] Comparing images...");
			if (!(image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth())) {
				System.out.println("[FAILED] Images are not the same: dimensions are different");
			} else {
				System.out.println("[INFO] Using pixel comparison...");
				height = image1.getHeight();
				width = image1.getWidth();
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						if (image1.getRGB(x, y) == image2.getRGB(x, y)) {
							b = 1;
							g = g + b;
						} else {
							b = 0;
						}
						c = c * b;
					}
				}
			}
			System.out.println("[INFO] Comparison finished");
		} catch (IOException e) {
			System.out.println("[FAILED] Cannot get image");
			e.printStackTrace();
		}
		passPercentage = (g * 100) / (height * width);
		if (c == 1) {
			f = true;
			System.out.println("[INFO] Images are equal with " + passPercentage + " macthing");
		} else {
			f = false;
			System.out.println("[FAILED] Images are not equal with " + passPercentage + " matching");
		}
		return f;
	}

	public static String saveImageFromUrl(String imageUrl, String destinationFile) {
		int length = 0;
		URL url = null;
		OutputStream os = null;
		byte[] b = new byte[2048];
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStream is = null;
		try {
			is = url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			os = new FileOutputStream(destinationFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationFile;
	}

}
