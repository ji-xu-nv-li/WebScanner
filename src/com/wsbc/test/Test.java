package com.wsbc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Test {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties sbp = new Properties();
		File file = new File("WebScanSelenium.properties");
		System.out.println(file.getAbsolutePath());
		if (file.exists()) {
			sbp.load(new FileInputStream(file));
			System.setProperty("webdriver.firefox.bin",
					sbp.getProperty("webdriver.firefox.bin", "D:/Mozilla Firefox/firefox.exe"));
			System.setProperty("webdriver.gecko.driver",
					sbp.getProperty("webdriver.gecko.driver", "D:/Mozilla Firefox/geckodriver.exe"));
		}
	}

}
