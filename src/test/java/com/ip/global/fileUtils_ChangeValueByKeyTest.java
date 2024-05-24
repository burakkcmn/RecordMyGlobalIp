package com.ip.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class fileUtils_ChangeValueByKeyTest {

	@TempDir
	static File tempFolder;

	private File testFile;

	@BeforeEach
	public void setUp() throws Exception {
		testFile = new File(tempFolder, "test.properties");
		writeStringToFile(testFile, "key1 = value1\nkey2 = value2\nkey3 = value3");
	}

	@AfterEach
	public void deleteTestFile() throws IOException {
		testFile.delete();
	}

	@BeforeAll
	public static void createTempFolder() throws IOException {
		tempFolder = new File("temp-files");
		if (tempFolder.exists()) {
			deleteDirectory(tempFolder);
		}
		tempFolder.mkdirs();

	}

	@AfterAll
	public static void deleteTempFolder() throws IOException {
		deleteDirectory(tempFolder);
	}

	@Test
	public void testKeyFoundValueChangeWithExistingPairs() throws IOException {
		fileUtils.changeValueByKey(testFile, "key2", "newValue2");

		String content = readStringFromFile(testFile);
		assertEquals("key1 = value1\nkey2 = newValue2\nkey3 = value3", content.trim());
	}

	@Test
	public void testKeyNotFoundNewKeyValueAddedWithExistingPairs() throws IOException {
		fileUtils.changeValueByKey(testFile, "key4", "newValue4");

		String content = readStringFromFile(testFile);
		assertTrue(content.trim().contains("key1 = value1\nkey2 = value2\nkey3 = value3\nkey4 = newValue4"));
	}

	@Test
	public void testKeyFoundValueChange() throws IOException {
		writeStringToFile(testFile, "key1=value1");
		fileUtils.changeValueByKey(testFile, "key1", "newValue");

		String content = readStringFromFile(testFile);
		assertEquals("key1 = newValue", content.trim());
	}

	@Test
	public void testKeyFoundValueUnchanged() throws IOException {
		writeStringToFile(testFile, "key1 = value1");
		fileUtils.changeValueByKey(testFile, "key1", "value1");

		String content = readStringFromFile(testFile);
		assertEquals("key1 = value1", content.trim());
	}

	@Test
	public void testKeyNotFoundNewKeyValueAdded() throws IOException {
		writeStringToFile(testFile, "key2 = value2");
		fileUtils.changeValueByKey(testFile, "key1", "value1");

		String content = readStringFromFile(testFile);
		assertTrue(content.trim().contains("key1 = value1"));
	}

	@Test
	public void testEmptyFile() throws IOException {
		try (FileOutputStream fos = new FileOutputStream(new File(tempFolder, "test.properties"))) {
			fos.write("".getBytes()); // Write empty string to clear contents
		}

		fileUtils.changeValueByKey(testFile, "key1", "value1");

		String content = readStringFromFile(testFile);
		assertEquals("key1 = value1", content.trim());
	}

	@Test
	public void testIOExceptionHandling() throws IOException {
		testFile.setReadOnly(); // Simulate IOException (e.g., read-only file)
		assertThrows(IOException.class, () -> fileUtils.changeValueByKey(testFile, "key1", "value1"));
	}

	private void writeStringToFile(File file, String content) throws IOException {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
		}
	}

	private String readStringFromFile(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		try (FileReader reader = new FileReader(file)) {
			int c;
			while ((c = reader.read()) != -1) {
				content.append((char) c);
			}
		}
		return content.toString();
	}

	private static void deleteDirectory(File directory) throws IOException {
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				deleteDirectory(file);
			}
		}
		directory.delete();
	}
}
