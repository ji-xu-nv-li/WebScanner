package com.wsbc.selenium.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ParseSelectorUtil {
	
	public static WebElement findElement(String selecotr) {
		List<WebElement> list = findElements(selecotr);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	public static List<WebElement> findElements(String selecotr) {
		if (selecotr == null || "".equals(selecotr)) {
			return new ArrayList<WebElement>();
		}
		List<WebElement> list = new ArrayList<WebElement>();
		String[] strs = null;
		if (selecotr.startsWith("/")) {
			strs = new String[]{selecotr};
		} else {
			strs = selecotr.split(" ");
		}
		for (int index = 0; index < strs.length ; index++) {
			List<WebElement> temp = new ArrayList<WebElement>();
			SearchContext searchContext = null;
			if (index == 0) {
				searchContext = SeleniumUtil.getDriver();
				List<WebElement> webElements = findElements(searchContext, strs[index]);
				list = webElements;
			} else {
				if (list == null || list.size() == 0) {
					return new ArrayList<WebElement>();
				}
				for (WebElement webElement : list) {
					List<WebElement> webElements = findElements(webElement, strs[index]);
					if (webElements != null && webElements.size() > 0) {
						temp.addAll(webElements);
					}
				}
				list = temp;
			}
		}
		return list;
	}
	
	/**
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午3:02:38
	 * @param searchContext
	 * @param oneSeletor 只有一个选择器
	 * @return
	 * @throws
	 */
	public static WebElement findElement(SearchContext searchContext, String oneSeletor) {
		List<WebElement> webElements = findElements(searchContext, oneSeletor);
		if (webElements == null || webElements.size() == 0) {
			return null;
		}
		return webElements.get(0);
	}
	
	/**
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午3:02:38
	 * @param searchContext
	 * @param oneSeletor 只有一个选择器
	 * @return
	 * @throws
	 */
	public static List<WebElement> findElements(SearchContext searchContext, String oneSeletor) {
		List<WebElement> webElements = new ArrayList<WebElement>();
		if (oneSeletor.startsWith("#")) {
			WebElement webElement = searchContext.findElement(By.id(oneSeletor.substring(1)));
			webElements.add(webElement);
		} else if (oneSeletor.startsWith(".")){
			webElements = searchContext.findElements(By.className(oneSeletor.substring(1)));
		} else if (oneSeletor.startsWith("/")){
			// xpaht方式选取
			webElements = searchContext.findElements(By.xpath(oneSeletor));
		}
		return webElements;
	}
	
	public static List<WebElement> findElementsByXpath(SearchContext searchContext, String xpath) {
		return searchContext.findElements(By.xpath(xpath));
	}

}
