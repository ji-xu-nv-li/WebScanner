package com.wsbc.process;

import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.wsbc.util.StringUtil;
import com.wsbc.util.file.WsbcFileUtil;

public class SystemOutNode extends Node{
	
	private final String filePath = "filePath";
	
	private final String printPre = "print";

	@Override
	protected void doHanlder() {
		Map<String, Object> request = getRequest();
		String fp = getRequestString(filePath);
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : request.entrySet()) {
			if (entry.getKey().startsWith(printPre)) {
				Object value = entry.getValue();
				if (value.toString().matches("^\\$\\{.*\\}$")) {
					sb.append(JSONObject.toJSONString(getRequest(value.toString())));
				} else {
					sb.append(JSONObject.toJSONString(value));
				}
				sb.append("\n----------\n"); // 添加分隔符
			}
		}
		if (fp == null || fp.isEmpty()) {
			System.out.println(sb.toString());
		} else {
			fp = StringUtil.convertString(fp);
			WsbcFileUtil.writeFile(sb.toString(), fp, false);
		}
	}
	
}
