package utils;

import java.io.IOException;

public class AndroidDevicesUtils {

	public static void startDevice(String deviceName, String emulatorPort, String deviceId, int timeOutMillSec) {
		String output = "";
		if (deviceId.toLowerCase().contains("emulator")) {
			System.out.println(
					"[INFO] Waiting until emulator " + deviceId + " will start for " + timeOutMillSec / 1000 + " sec");
			try {
				Runtime.getRuntime().exec("emulator -port " + emulatorPort + " -avd " + deviceName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// for (int i = 0; i < timeOutMillSec/1000; i++) {
			// String consoleOutput = CMD.executeCommandAndProceed("adb
			// devices");
			// System.out.println(consoleOutput);
			// if (!consoleOutput.contains(deviceId + " device")) {
			// System.out.println("[INFO] Emulator " + deviceId + " started");
			// break;
			// } else {
			// MainClass.sleepFor(1000);
			// continue;
			// }
			// }
			MainClass.sleepFor(timeOutMillSec);
			output = CMD.executeCommand("adb devices");
			if (output.toLowerCase().replaceAll("	", "").contains(deviceId + "device")) {
				System.out.println("[INFO] Connection with emulator " + deviceId + " established");
				System.out.println("[INFO] Emulator " + deviceId + " started on port " + emulatorPort);
			}
		} else {
			System.out.println("[INFO] Checking connection with device: device name  " + deviceName + " (device id "
					+ deviceId + ")...");
			output = CMD.executeCommand("adb devices");
			System.out.println(output);
			if (output.toLowerCase().replaceAll("	", "").contains(deviceId + "device")) {
				System.out.println("[INFO] Connection with device: device name " + deviceName + "(device id " + deviceId
						+ ") established");
			}
		}
	}

}
