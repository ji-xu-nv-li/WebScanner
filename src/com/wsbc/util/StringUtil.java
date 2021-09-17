package com.wsbc.util;

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
	
}
