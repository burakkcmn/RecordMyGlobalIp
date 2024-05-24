package com.ip.global;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class fileUtils_CreateIfNotExistsTest {

	private String existingFilePath;
	private String nonExistingFilePath;
	private File existingFile;
	private File nonExistingFile;

	@BeforeEach
	public void setUp() {
		existingFilePath = "existingFile.txt";
		nonExistingFilePath = "nonExistingFile.txt";

		existingFile = new File(existingFilePath);
		if (!existingFile.exists()) {
			try {
				assertTrue(existingFile.createNewFile());
			} catch (IOException e) {
				e.printStackTrace();
				fail();
			}
		}

		nonExistingFile = new File(nonExistingFilePath);
		if (nonExistingFile.exists()) {
			assertTrue(nonExistingFile.delete());
		}
	}

	@AfterEach
	public void tearDown() {
		if (existingFile.exists()) {
			assertTrue(existingFile.delete());
		}
		if (nonExistingFile.exists()) {
			assertTrue(nonExistingFile.delete());
		}
	}

	@org.junit.jupiter.api.Test
	public void testCreateIfNotExists_FileAlreadyExists() {
		boolean result = false;
		try {
			result = fileUtils.createIfNotExists(existingFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(result);
		assertTrue(existingFile.exists());
	}

	@org.junit.jupiter.api.Test
	public void testCreateIfNotExists_FileDoesNotExist() {
		boolean result = false;
		try {
			result = fileUtils.createIfNotExists(nonExistingFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(result);
		assertTrue(nonExistingFile.exists());
	}

	@org.junit.jupiter.api.Test
	public void testCreateIfNotExists_FileCannotBeCreated() {
		String invalidPath = "/invalid/path/to/file.txt";
		assertThrows(IOException.class, () -> fileUtils.createIfNotExists(invalidPath));
	}

}
