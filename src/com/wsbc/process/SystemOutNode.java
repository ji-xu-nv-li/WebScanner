package com.wsbc.process;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SystemOutNode extends Node{

	@Override
	protected void doHanlder() {
		Map<String, Object> request = getRequest();
		System.out.println(JSONObject.toJSONString(request));
	}
	
}
