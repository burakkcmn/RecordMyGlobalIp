package com.ip.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ipRecorder_ParseParametersTest {

	private static Method parseParametersMethod;

	@BeforeAll
	public static void setUp() throws NoSuchMethodException {
		parseParametersMethod = ipRecorder.class.getDeclaredMethod("parseParameters", String[].class);
		parseParametersMethod.setAccessible(true);
	}

	@Test
	public void testParseParametersWithValidInput() throws InvocationTargetException, IllegalAccessException {
		String[] args = { "key1=value1,key2=value2,key3=value3" };
		Map<String, String> result = (Map<String, String>) parseParametersMethod.invoke(new ipRecorder(),
				(Object) args);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertEquals("value1", result.get("key1"));
		assertEquals("value2", result.get("key2"));
		assertEquals("value3", result.get("key3"));
	}

	@Test
	public void testParseParametersWithEmptyInput() throws InvocationTargetException, IllegalAccessException {
		String[] args = { "" };

		Exception exception = assertThrows(InvocationTargetException.class, () -> {
			parseParametersMethod.invoke(new ipRecorder(), (Object) args);
		});

		Throwable cause = exception.getCause();
		assertTrue(cause instanceof IllegalArgumentException);
		assertEquals("Chunk [] is not a valid entry", cause.getMessage());
	}

	@Test
	public void testParseParametersWithNullInput() throws InvocationTargetException, IllegalAccessException {
		String[] args = {};
		Map<String, String> result = (Map<String, String>) parseParametersMethod.invoke(new ipRecorder(),
				(Object) args);

		assertNull(result);
	}

	@Test
	public void testParseParametersWithInvalidInput() throws InvocationTargetException, IllegalAccessException {
		String[] args = { "key1=value1,key2,key3=value3" };

		Exception exception = assertThrows(InvocationTargetException.class, () -> {
			parseParametersMethod.invoke(new ipRecorder(), (Object) args);
		});

		Throwable cause = exception.getCause();
		assertTrue(cause instanceof IllegalArgumentException);
		assertEquals("Chunk [key2] is not a valid entry", cause.getMessage());
	}
}
