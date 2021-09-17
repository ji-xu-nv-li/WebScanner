package com.wsbc.selenium.operation;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.SeleniumUtil;

public class OpenSiteOperation extends Operation {
	
	private String url;
	
	public OpenSiteOperation() {}
	
	public OpenSiteOperation(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Result handler() {
		// TODO Auto-generated method stub
		return operation(url);
	}
	
	public static Result operation(String url) {
		long a = System.currentTimeMillis();
		logger.info("打开网站开始，路径：" + url );
		
		SeleniumUtil.getDriver().get(url);
		
		long b = System.currentTimeMillis();
		logger.info("打开网站结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
