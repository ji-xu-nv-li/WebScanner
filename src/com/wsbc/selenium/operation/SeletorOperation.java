package com.wsbc.selenium.operation;

import com.wsbc.selenium.result.SeletorResult;
import com.wsbc.selenium.util.ParseSelectorUtil;

public class SeletorOperation extends Operation{
	
	private String location;
	
	public SeletorOperation() {}

	public SeletorOperation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public SeletorResult handler() {
		// TODO Auto-generated method stub
		return operation(location);
	}
	
	public static SeletorResult operation(String location) {
		long a = System.currentTimeMillis();
		logger.info("元素定位开始，定位内容为：" + location );
		
		SeletorResult result = new SeletorResult(0, ParseSelectorUtil.findElements(location));
		
		long b = System.currentTimeMillis();
		logger.info("元素定位结束，耗时：" + (b-a) + "毫秒");
		return result;
	}

}
