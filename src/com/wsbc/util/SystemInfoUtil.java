package com.wsbc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 获取操作系统信息
 */
public class SystemInfoUtil {
	
	private static Log logger = LogFactory.getLog(SystemInfoUtil.class);
	
	public final static String WINDOWS = "WINDOWS";
	
	public final static String LINUX = "LINUX";


	
	/**
	 * 获取当前操作系统window、linux
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年11月20日  下午11:50:09
	 * @return GetSystem.WINDOWS、GetSystem.LINUX
	 */
	public static String getSystemOS() {
		String OS = System.getProperty("os.name", WINDOWS).toUpperCase();
		logger.debug("当前操作系统为：OS = " + OS);
		if (OS.contains("WINDOWS")) {
			return WINDOWS;
		} if (OS.contains("LINUX")) {
			return LINUX;
		}
		return WINDOWS;
	}
}
