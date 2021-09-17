package com.wsbc.selenium.util;

import java.util.Properties;

import com.wsbc.selenium.operation.system.KillOperation;

public class SeleniumBaseUtil {

	/**
	 * 方法描述：强行关闭之前遗留的进程
	 * 作者： yxm
	 * 创建时间： 2019年11月16日  下午4:18:13
	 * @throws
	 */
	public static void killBrower() {
		Properties sbp = SeleniumUtil.getProperties();
		String bin = sbp.getProperty("webdriver.firefox.bin");
		String driver = sbp.getProperty("webdriver.gecko.driver");
		int index = bin.lastIndexOf("/");
		bin = bin.substring(index + 1);
		KillOperation.handler(bin);
		
		index = driver.lastIndexOf("/");
		driver = driver.substring(index + 1);
		KillOperation.handler(driver);
	}
}
