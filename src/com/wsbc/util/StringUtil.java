package com.wsbc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class StringUtil {
	
	/**
	 * 方法描述：字符串首字母转为大写
	 * 作者： yxm
	 * 创建时间： 2020年2月7日  上午9:59:56
	 */
	public static String firstUpperCase(String str) {
		if (str != null && str.length() > 0) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}
	
	public static String objectToString(Object object) {
		if (object == null)
			return null;
		if (object instanceof String) {
			return (String)object;
		}
		if (object instanceof Integer) {
			return object.toString();
		}
		if (object instanceof List) {
			return JSONArray.toJSONString(object);
		}
		return JSONObject.toJSONString(object);
	}
	
	public static Object stringToObject(String str, Class<?> clazz) {
		if (str == null) {
			return null;
		}
		if (clazz == String.class) {
			return str;
		}
		if (clazz == Integer.class) {
			return Integer.valueOf(str);
		}
		return JSONObject.parseObject(str, clazz);
	}
	
	public static Object StringToArray(String str, Class<?> clazz) {
		return JSONObject.parseArray(str, clazz);
	}
	
	/**
	 * 日期快捷格式转换
	 * %d 一个月的第几天（01..31）
	 * %D 日期（yyyyMMdd） 
	 */
	public static String convertString(String str) {
		if (!str.contains("%")) {
			return str;
		}
		Calendar calendar = Calendar.getInstance();
		// %d 一个月的第几天（01..31）
		if (str.contains("%d")) {
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			str = str.replace("%d", String.format("%02d", day));
		}
		//%D 日期（yyyyMMdd） 
		if (str.contains("%D")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(calendar.getTime());
			str = str.replace("%D", date);
		}
		
		/**
		 * % H 小时（00..23） 
% I 小时（01..12） 
% k 小时（0..23） 
% l 小时（1..12） 
% M 分（00..59） 
% p 显示出AM或PM 
% r 时间（hh：mm：ss AM或PM），12小时 
% s 从1970年1月1日00：00：00到目前经历的秒数 
% S 秒（00..59） 
% T 时间（24小时制）（hh:mm:ss） 
% X 显示时间的格式（％H:％M:％S） 
% Z 时区 日期域 
% a 星期几的简称（ Sun..Sat） 
% A 星期几的全称（ Sunday..Saturday） 
% b 月的简称（Jan..Dec） 
% B 月的全称（January..December） 
% c 日期和时间（ Mon Nov 8 14：12：46 CST 1999） 


% h 和%b选项相同 
% j 一年的第几天（001..366） 
% m 月（01..12） 
% w 一个星期的第几天（0代表星期天） 
% W 一年的第几个星期（00..53，星期一为第一天） 
% x 显示日期的格式（mm/dd/yy） 
% y 年的最后两个数字（ 1999则是99） 
% Y 年（例如：1970，1996等） 
		 */
		return str;
	}
	
}
