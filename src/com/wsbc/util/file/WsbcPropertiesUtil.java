package com.wsbc.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WsbcPropertiesUtil {
	
	public static Log logger = LogFactory.getLog(WsbcPropertiesUtil.class);
	
	private static Map<String, Properties> allProperties = new ConcurrentHashMap<String, Properties>();
	
	/**
	 * 方法描述：读取配置文件信息
	 * 作者： yxm
	 * 创建时间： 2018年12月31日  上午11:27:22
	 * @param filePath 文件路径
	 */
	public static Properties readProperties(String filePath) {
		Properties properties = new Properties();
		File file = new File(filePath);
		if (!file.exists()) {
			return properties;
		}
		InputStream is = null;
		try {
			is = WsbcPropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
			properties.load(is);
		} catch (Exception e) {
			logger.info("读取配置文件异常，原因为：", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return properties;
	}
	
	/**
	 * 
	 * 方法描述：读取配置文件信息
	 * 作者： yxm
	 * 创建时间： 2020年2月22日  下午1:21:16
	 * @param filePath 文件路径
	 * @param reload 是否需要重新读取
	 */
	public static Properties readProperties(String filePath, boolean reload) {
		Properties properties = null;
		if (!reload) {
			properties = allProperties.get(filePath);
		}
		if (properties != null) {
			return properties;
		}
		
		// 重新加载：
		properties = readProperties(filePath);
		allProperties.put(filePath, properties);
		return properties;
	}

}
