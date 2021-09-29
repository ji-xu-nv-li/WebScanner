package com.wsbc.selenium.dic;

/**
 * 查找元素的方式
 */
public enum ByType {
	xpath,tagName,name,id,cssSelector,className;
	
	public static ByType getByType(String byType) {
		ByType[] byTypes = ByType.values();
		for (ByType by : byTypes) {
			if (by.toString().equals(byType)) {
				return by;
			}
		}
		return null;
	}
}
