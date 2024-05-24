package com.ip.global;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class fileUtils_deleteFileTest {

	@Test
	public void testDeleteFileSuccess() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp", ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		fileUtils.deleteFile(tempFile);

		assertFalse(tempFile.exists(), "Temp file should be deleted");
	}

	@Test
	public void testDeleteFileFailure() {

	}

	@Test
	public void testDeleteNonexistentFile() {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp", ".txt");
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
		fileUtils.deleteFile(tempFile);

		assertFalse(tempFile.exists(), "Temp file should be deleted");
	}
}
