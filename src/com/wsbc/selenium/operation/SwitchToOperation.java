package com.wsbc.selenium.operation;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.SeleniumUtil;

public class SwitchToOperation extends Operation{
	
	private static final Log logger = LogFactory.getLog(SwitchToOperation.class);
	
//	private String destWindowHandler;
	
	public SwitchToOperation() {}

	@Override
	public Result handler() {
		long a = System.currentTimeMillis();
		String currentWH = SeleniumUtil.getDriver().getWindowHandle();
		logger.info("切换窗口开始，当前窗口为：" +  currentWH);
		// 获取窗口信息
		Set<String> handles = SeleniumUtil.getDriver().getWindowHandles();
		if (handles.size() > 1) {
			logger.info("当前窗口数量为：" +  handles.size());
			for (String handle : handles) {
				if (!handle.equals(currentWH)) {
					SeleniumUtil.getDriver().switchTo().window(handle); // 换窗口位置
				}
			}
		} else {
			logger.info("当前仅有一个窗口，切换失败！");
			return Result.FAILED;
		}
		long b = System.currentTimeMillis();
		logger.info("切换窗口结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
