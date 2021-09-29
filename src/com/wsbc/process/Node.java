package com.wsbc.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.wsbc.process.util.ProcessUtil;

public abstract class Node {
	
	
	private Map<String, String> attributes = new HashMap<String, String>();
	
	private Map<String, Object> request = new HashMap<String, Object>();
	
	private Map<String, Object> response = new HashMap<String, Object>();
	
	/**
	 * key: get方式获取数据的方法
	 * value: response 中的key值，方便其他节点获取数据
	 */
	private Map<String, String> out = new HashMap<String, String>();
	
	/**
	 * 当前节点所在的父节点
	 */
	private ListNode parent = null;
	
	public void putAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public String getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void putRequest(String key, Object value) {
		request.put(key, value);
	}
	
	public Object getRequest(String key) {
		if (key.matches("^\\$\\{.*\\}$")) {
			key = key.replaceAll("[$|{|}]", "").trim();
		}
		Object value = request.get(key);
		if (value.toString().matches("^\\$\\{.*\\}$")) {
			value = value.toString().replaceAll("[$|{|}]", "").trim();
			value = request.get(value);
		}
		return value;
	}
	
	public Map<String, Object> getRequest() {
		return request;
	}
	
	public void putResponse(String key, Object value) {
		response.put(key, value);
	}
	
	public Object getResponse(String key) {
		return response.get(key);
	}
	
	public Map<String, Object> getResponse() {
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
	
	public ListNode getParent() {
		return parent;
	}

	public void setParent(ListNode parent) {
		this.parent = parent;
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
		Map<String, Object> request = this.getRequest();
		Map<String, Object> back = new HashMap<String, Object>();
		for (Entry<String, Object> entry : request.entrySet()) {
			String value = entry.getValue().toString();
			// ${value} 为需要替换的数据，故重新获取值
			if (value != null && value.matches("^\\$\\{.*\\}$")) {
				value = value.replaceAll("[$|{|}]", "").trim();
				back.put(value, ProcessUtil.getResponseValue(this, value));
			}
		}
		request.putAll(back);
	}

}
