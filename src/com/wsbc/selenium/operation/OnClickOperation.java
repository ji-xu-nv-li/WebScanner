package com.wsbc.selenium.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.ParseSelectorUtil;

public class OnClickOperation extends Operation{
	
	private static final Log logger = LogFactory.getLog(OnClickOperation.class);
	
	private String location;
	
	public OnClickOperation() {}
	
	public OnClickOperation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public Result handler() {
		// TODO Auto-generated method stub
		return handler(location);
	}
	
	public static Result handler(String location) {
		long a = System.currentTimeMillis();
		logger.info("点击事件开始，定位：" + location);
		
		WebElement div = ParseSelectorUtil.findElement(location);
		if ( div == null ) {
			logger.info("点击事件异常，元素定位失败！");
			return Result.FAILED;
		}
		div.click();
		long b = System.currentTimeMillis();
		logger.info("点击事件结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
