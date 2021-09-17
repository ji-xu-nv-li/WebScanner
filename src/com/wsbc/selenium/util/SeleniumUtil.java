package com.wsbc.selenium.util;

import java.util.Properties;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.wsbc.util.SystemInfoUtil;
import com.wsbc.util.file.WsbcPropertiesUtil;

public class SeleniumUtil {
	
	private static RemoteWebDriver webDriver = null;
	
	public static Properties getProperties() {
		Properties sbp = null;
		if (SystemInfoUtil.WINDOWS.equals(SystemInfoUtil.getSystemOS())) {
			sbp = WsbcPropertiesUtil.readProperties("WebScanSelenium.properties", false);
		} else {
			sbp = WsbcPropertiesUtil.readProperties("WebScanSelenium_linux.properties", false);
		}
		return sbp;
	}
	
	public static RemoteWebDriver getDriver() {
		if (webDriver == null) {
			synchronized (SeleniumUtil.class) {
				if (webDriver == null) {
					// 读取配置文件信息
					Properties sbp = SeleniumUtil.getProperties();
					System.setProperty("webdriver.firefox.bin",
							sbp.getProperty("webdriver.firefox.bin"));
					System.setProperty("webdriver.gecko.driver",
							sbp.getProperty("webdriver.gecko.driver"));
					
					// 设置浏览器参数
					FirefoxOptions options = new FirefoxOptions();
					/*
					 * 可以 为驱动启动增加参数
					 */
					if ("false".equals(sbp.getProperty("OpenBrowerEnable", "false"))) {
						options.addArguments("--headless"); // 禁止打开浏览器
					}
					/*
					 * 可以 为浏览器属性设置参数 
					 */
					if ("2".equals(sbp.getProperty("permissions.default.image", "2"))) {
						options.addPreference("permissions.default.image", 2); // 禁止加载图片
					}
//					options.setCapability(key, value);
					
					webDriver = new FirefoxDriver(options);
				}
			}
		}
		return webDriver;
	}
	
	/**
	 * 检查浏览器驱动是否已经创建过
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年11月20日  下午11:45:03
	 */
	public static boolean checkDriver() {
		if (webDriver == null) {
			return false;
		}
		return true;
	}
	
	
}
