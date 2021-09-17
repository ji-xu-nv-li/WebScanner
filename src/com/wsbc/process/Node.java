package com.wsbc.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wsbc.process.util.ProcessUtil;

public abstract class Node {
	
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	private Map<String, String> request = new HashMap<String, String>();
	
	private Map<String, String> response = new HashMap<String, String>();
	
	/**
	 * key: get方式获取数据的方法
	 * value: response 中的key值，方便其他节点获取数据
	 */
	private Map<String, String> out = new HashMap<String, String>();
	
	/**
	 * 当前节点所在的节点列表
	 */
	private ListNode nodes = null;
	
	public void putAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public String getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void putRequest(String key, String value) {
		request.put(key, value);
	}
	
	public String getRequest(String key) {
		return request.get(key);
	}
	
	public Map<String, String> getRequest() {
		return request;
	}
	
	public void putResponse(String key, String value) {
		response.put(key, value);
	}
	
	public String getResponse(String key) {
		return response.get(key);
	}
	
	public Map<String, String> getResponse() {
		return response;
	}
	
	public void putOut(String key, String value) {
		out.put(key, value);
	}
	
	public String getOut(String key) {
		return out.get(key);
	}
	
	public Map<String, String> getOut() {
		return out;
	}
	
	public ListNode getNodes() {
		return nodes;
	}

	public void setNodes(ListNode nodes) {
		this.nodes = nodes;
	}
	
	public void handler() {
		preHanlder();
		doHanlder();
		postHandler();
	}

	protected void preHanlder() {
		handlerRequest();
		response.clear();
	}
	
	protected abstract void doHanlder();
	
	protected void postHandler() {
	}
	
	private void handlerRequest() {
		Map<String, String> request = this.getRequest();
		Map<String, String> back = new HashMap<String, String>();
		for (Entry<String, String> entry : request.entrySet()) {
			String value = entry.getValue();
			// ${value} 为需要替换的数据，故重新获取值
			if (value != null && value.matches("^\\$\\{.*\\}$")) {
				value = value.replaceAll("[$|{|}]", "").trim();
				back.put(value, ProcessUtil.getResponseValue(this, value));
			}
		}
		request.putAll(back);
	}

}
