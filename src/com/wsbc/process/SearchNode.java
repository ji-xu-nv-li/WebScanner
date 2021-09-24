package com.wsbc.process;

import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import com.wsbc.process.constant.ProcessConstant;
import com.wsbc.selenium.util.ParseSelectorUtil;
import com.wsbc.selenium.util.SeleniumUtil;

public class SearchNode extends ListNode{
	
	public final String xpath = "xpath";
	
	public static final String SEARCH_DATA = "_search_data_";

	@Override
	protected void doHanlder() {

		String type = this.getAttribute(ProcessConstant.Attribute.TYPE);
		String condition = this.getAttribute(ProcessConstant.Search.CONDITION);
		String alias = this.getAttribute(ProcessConstant.Search.ALIAS);
//		String multiple = this.getAttribute(ProcessConstant.Search.MULTIPLE);
		List<WebElement> list = null;
		SearchContext searchContext = SeleniumUtil.getDriver();
		if (getRequest().get(SEARCH_DATA) != null) {
			List<WebElement> preSearchData = (List<WebElement>)getRequest().get(SEARCH_DATA);
			if (!preSearchData.isEmpty()) {
				searchContext = preSearchData.get(0);
			}
		}
		if (xpath.equals(type)) {
			list= ParseSelectorUtil.findElementsByXpath(searchContext, condition);
		} else {
			return;
		}
		if (list.isEmpty()) {
			return;
		}
		// 给内部节点传递匹配的元素信息
		for (Entry<String, Node> entry : this.getNodes().entrySet()) {
			Node node = entry.getValue();
			node.putRequest(SEARCH_DATA, list);
			if (alias != null && !alias.trim().isEmpty()) {
				node.putRequest(alias, list);
			}
		}
		
	}

}
