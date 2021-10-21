package com.wsbc.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.wsbc.process.constant.ProcessConstant;


public class ForEachListNode extends ListNode {

	@SuppressWarnings("unchecked")
	@Override
	public void doHanlder() {
		String startName = this.getAttribute(ProcessConstant.Attribute.START);
		String items = this.getAttribute(ProcessConstant.ForEach.ITEMS);
		List<Object> list = null;
		if (items != null) {
			if (items.matches("^\\$\\{.*\\}$")) {
				Object itemsValue = this.getRequest(items);
				if (itemsValue instanceof List) {
					list = (List<Object>) itemsValue;
				} else if (itemsValue instanceof String) {
					list = Arrays.asList((Object[])itemsValue.toString().split(","));
				} else {
					list = new ArrayList<Object>();
					list.add(itemsValue);
				}
			} else {
				list = Arrays.asList((Object[])items.toString().split(","));
			}
		}
		
		if (startName == null || "".equals(startName)) {
			handlerOrder(list);
		} else {
			handlerStart(startName, list);
		}
	}

	/**
	 * start方式循环
	 * @param startName 起始节点
	 * @param value 
	 */
	public void handlerStart(String startName, List<Object> list) {
		String mergeData = this.getAttribute(ProcessConstant.ForEach.MERGE_DATA);
		// 循环内节点数据统一收集
		List<Map<String, Object>> listMergeData = new ArrayList<Map<String, Object>>();
		String var = this.getAttribute(ProcessConstant.ForEach.VAR);
		// 支持通过次数循环
		int count = 0;
		if (list == null) {
			String c = this.getAttribute(ProcessConstant.ForEach.COUNT);
			if (c != null && c.matches("^\\d{1,}$")) {
				count = Integer.valueOf(c);
			}
		} else {
			count = list.size();
		}
		for (int index = 0; index < count; index++) {
			Map<String, Object> responseAll = new HashMap<String, Object>();
			String nextName = startName;
			while (nextName != null && !"".equals(nextName)) {
				Node node = this.getNode(nextName);
				if (list != null) {
					if (this.getParent() instanceof SearchNode) {
						node.putRequest(SearchNode.SEARCH_DATA, list.get(index));
					}
					node.putRequest(var, list.get(index));
				}
				node.putRequest(ProcessConstant.ForEach.INDEX, index + "");
				node.putRequest(ProcessConstant.ForEach.COUNT, count + "");
				node.handler();
				nextName = node.getAttribute(ProcessConstant.Attribute.NEXT);
				
				responseAll.putAll(node.getResponse());
			}
			listMergeData.add(responseAll);
			// 重置，进入下一循环
			nextName = startName;
		}
		if (mergeData != null) {
			getResponse().put(mergeData, listMergeData);
		}
	}
	/**
	 * 节点顺序循环
	 */
	public void handlerOrder(List<Object> list) {
		String mergeData = this.getAttribute(ProcessConstant.ForEach.MERGE_DATA);
		// 循环内节点数据统一收集
		List<Map<String, Object>> listMergeData = new ArrayList<Map<String, Object>>();
		String var = this.getAttribute(ProcessConstant.ForEach.VAR);
		// 支持通过次数循环
		int count = 0;
		if (list == null) {
			String c = this.getAttribute(ProcessConstant.ForEach.COUNT);
			if (c != null && c.matches("^\\d{1,}$")) {
				count = Integer.valueOf(c);
			}
		} else {
			count = list.size();
		}
		for (int index = 0; index < count; index++) {
			Map<String, Object> responseAll = new HashMap<String, Object>();
			// 顺序遍历节点内容
			LinkedHashMap<String,Node> nodes = this.getNodes();
			for (Entry<String, Node> entry : nodes.entrySet()) {
				Node node = entry.getValue();
				if (list != null) {
					if (this.getParent() instanceof SearchNode) {
						node.putRequest(SearchNode.SEARCH_DATA, list.get(index));
					}
					node.putRequest(var, list.get(index));
				}
				node.putRequest(ProcessConstant.ForEach.INDEX, index + "");
				node.putRequest(ProcessConstant.ForEach.COUNT, count + "");
				node.handler();
				
				responseAll.putAll(node.getResponse());
			}
			listMergeData.add(responseAll);
		}
		if (mergeData != null) {
			getResponse().put(mergeData, listMergeData);
		}
	}
}
