package com.ip.global;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class fileUtils {

	public static boolean createIfNotExists(String absolutePath) throws IOException {
		boolean isExists = true;

		File inputFile = new File(absolutePath);
		if (!inputFile.exists()) {
			if (!inputFile.createNewFile()) {
				System.out.println("Could not create the file.");
				isExists = false;
			}
		}

		return isExists;
	}

	public static void deleteFile(File tempFile) {
		if (tempFile.exists()) {
			if (tempFile.delete()) {
				System.out.println("Temp file deleted successfully.");
			} else {
				System.out.println("Temp file could not be deleted.");
			}
		} else {
			System.out.println("Temp file does not exist.");
		}
	}

	public static void changeValueByKey(File file, String pkey, String pvalue) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder modifiedContent = new StringBuilder();

		String currentLine;
		boolean keyFound = false;

		// Read line by line
		while ((currentLine = reader.readLine()) != null) {
			String[] parts = currentLine.split("=");
			if (parts.length == 2) {
				String key = parts[0].trim();
				// String value = parts[1].trim();

				if (key.equals(pkey)) {
					keyFound = true;
					modifiedContent.append(pkey + " = " + pvalue).append("\n");
				} else {
					modifiedContent.append(currentLine).append("\n");
				}
			} else {
				modifiedContent.append(currentLine).append("\n");
			}
		}

		reader.close();

		if (!keyFound) {
			modifiedContent.append(pkey + " = " + pvalue).append("\n");
		}

		// Overwrite the file content with the modified string
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(modifiedContent.toString());
		}
	}
}
