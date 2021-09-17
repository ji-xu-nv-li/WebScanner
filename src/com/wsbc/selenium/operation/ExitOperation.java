package com.wsbc.selenium.operation;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.SeleniumUtil;

public class ExitOperation extends Operation {
	
	public static final String CURRENT = "current";
	
	public static final String ALL = "all";
	
	/**
	 * 类型：当前窗口、还是所有（即退出浏览器）
	 */
	private String type;
	
	public ExitOperation() {}
	
	public ExitOperation(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Result handler() {
		return operation(type);
	}
	
	public static Result operation(String type) {
		long a = System.currentTimeMillis();
		logger.info("关闭窗口开始，类型：" + type);
		
		if (CURRENT.equals(type)) {
			 SeleniumUtil.getDriver().close(); // 关闭当前窗口
		} else {
			SeleniumUtil.getDriver().quit(); // 关闭所有窗口
		}
		
		long b = System.currentTimeMillis();
		logger.info("关闭窗口结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
