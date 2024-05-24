package com.ip.global;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.google.common.base.Splitter;

public class ipRecorder {

	private static ipRecorder IpRecorder;

	public long TimeInterval = 60000;

	public static void main(String[] args) {
		IpRecorder = new ipRecorder();
		Map<String, String> params = IpRecorder.parseParameters(args);
		if (params == null) {
			System.out.println("Command-line arguments must be entered");
			return;
		}
		IpRecorder.setGlobalSymbols(params);

		IpRecorder.runApp();
	}

	private void runApp() {
		System.out.println("File Directory: " + globalSymols.absolutePath);

		while (TimeInterval > 0) {
			try {
				URL whatIsMyIp = ipUtils.getGlobalIpAddress();
				BufferedReader in = new BufferedReader( //
						new InputStreamReader( //
								whatIsMyIp.openStream() //
						) //
				);

				globalSymols.IpAddress = in.readLine();

				updateValueForKey(globalSymols.absolutePath);

				System.out.println("Global IP save to : " + globalSymols.absolutePath);
				Thread.sleep(TimeInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateValueForKey(String absolutePath) throws IOException {
		fileUtils.createIfNotExists(absolutePath);

		File tempFile = new File(absolutePath);
		fileUtils.changeValueByKey(tempFile, globalSymols.Key, globalSymols.IpAddress);
	}

	private Map<String, String> parseParameters(String[] args) {
		Map<String, String> params = null;
		if (args.length > 0) {
			params = Splitter.on(",").withKeyValueSeparator("=").split(args[0]);
		}
		return params;
	}

	private void setGlobalSymbols(Map<String, String> params) {
		if (params.containsKey("timeinterval")) {
			TimeInterval = Long.valueOf(params.get("timeinterval"));
			System.out.println("TimeInterval: " + TimeInterval);
		}
		if (params.containsKey("absolutepath")) {
			globalSymols.absolutePath = params.get("absolutepath");
			System.out.println("Folder Directory: " + globalSymols.absolutePath);
		}
		if (params.containsKey("key")) {
			globalSymols.Key = params.get("key");
			System.out.println("Key: " + globalSymols.Key);
		}
	}
}
