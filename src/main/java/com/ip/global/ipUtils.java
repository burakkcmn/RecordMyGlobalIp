package com.ip.global;

import java.net.MalformedURLException;
import java.net.URL;

public class ipUtils {

	public static URL getGlobalIpAddress() throws MalformedURLException {
		return new URL("http://checkip.amazonaws.com");
	}
}
