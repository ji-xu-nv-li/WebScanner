package com.wsbc.selenium.operation;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.result.Result;

public class WaitOperation extends Operation {
	
	private static final Log logger = LogFactory.getLog(WaitOperation.class);
	
	// 毫秒
	private long time;
	
	public WaitOperation() {}
	
	public WaitOperation(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public void setTime(String time) {
		this.time = Long.valueOf(time);
	}

	@Override
	public Result handler() {
		return operation(time);
	}
	
	/**
	 * 
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午3:46:33
	 * @param time 毫秒
	 * @return 0-操作成功
	 */
	public static Result operation(long time) {
		long a = System.currentTimeMillis();
		logger.info("延迟操作开始，时间为：" + time );
		
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			long b = System.currentTimeMillis();
			logger.info("延迟操作被打断，耗时：" + (b-a) + "毫秒");
			return Result.EXCEPTION;
		}
		
		long b = System.currentTimeMillis();
		logger.info("延迟操作结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
