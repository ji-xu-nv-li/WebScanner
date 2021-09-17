package com.wsbc.selenium.operation.system;

import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.result.Result;

/**
 * 获取页面数据，因比较个性化，仅提供方式，具体需自选实现
 */
public abstract class GetDataOperation<T> extends Operation{
	
	@Override
	final public Result handler() {
		long a = System.currentTimeMillis();
		Result result = doHandler();
		long b = System.currentTimeMillis();
		logger.info("获取页面数据结束，耗时：" + (b-a) + "毫秒");
		return result;
	}

	public abstract Result doHandler();

}
