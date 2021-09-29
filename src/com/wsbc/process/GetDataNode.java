package com.wsbc.process;

import java.util.List;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.wsbc.process.constant.ProcessConstant;
import com.wsbc.selenium.dic.ByType;
import com.wsbc.selenium.util.ParseSelectorUtil;
import com.wsbc.selenium.util.SeleniumUtil;

public class GetDataNode extends Node{

	@Override
	protected void doHanlder() {
		// TODO Auto-generated method stub
		String name = this.getAttribute(ProcessConstant.Attribute.NAME);
		String attribute = this.getAttribute(ProcessConstant.Attribute.ATTRIBUTE);
		String type = this.getAttribute(ProcessConstant.Attribute.TYPE);
		String condition = this.getAttribute(ProcessConstant.Search.CONDITION);
		SearchContext searchContext = SeleniumUtil.getDriver();
		if (getRequest().get(SearchNode.SEARCH_DATA) != null) {
			Object searchData = getRequest().get(SearchNode.SEARCH_DATA);
			if (searchData instanceof List) {
				List<WebElement> preSearchData = (List<WebElement>)searchData;
				if (!preSearchData.isEmpty()) {
					searchContext = preSearchData.get(0);
				}
			} else {
				searchContext = (WebElement)searchData;
			}
		}
		ByType byType = ByType.getByType(type);
		List<WebElement> list= ParseSelectorUtil.findElements(searchContext, byType, condition);
		if (list.isEmpty()) {
			System.out.println("无匹配的元素信息：" + condition);
			return;
		}
		String value = "";
		if (attribute == null || "".equals(attribute)) {
			value = list.get(0).getText();
		} else {
			value = list.get(0).getAttribute(attribute);
		}
		getResponse().put(name, value);
	}

}
