package com.wsbc.selenium.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.result.Result;

public abstract class Operation{
	
	public static final Log logger = LogFactory.getLog(Operation.class);
	
	/**
	 * 方法描述：0-操作成功
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午2:13:17
	 * @return
	 * @throws
	 */
	public abstract Result handler();
	
}