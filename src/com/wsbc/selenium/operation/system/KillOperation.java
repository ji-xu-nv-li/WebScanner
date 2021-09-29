package com.wsbc.selenium.operation.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.result.Result;
import com.wsbc.util.SystemInfoUtil;

/**
 * 关闭进程
 */
public class KillOperation extends Operation{
	
	private static final Log logger = LogFactory.getLog(KillOperation.class);
	
	private String name;
	
	public KillOperation() {}
	
	public KillOperation(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Result handler() {
		// TODO Auto-generated method stub
		return handler(name);
	}

	/**
	 * 方法描述：关闭进程
	 * 作者： yxm
	 * 创建时间： 2019年11月21日  上午12:14:14
	 * @param name 进程名
	 * @return
	 */
	public static Result handler(String name) {
		try {
			if (name == null || "".equals(name.trim())) {
				return Result.SUCCESS;
			}
			String commond = "";
			if (SystemInfoUtil.WINDOWS.equals(SystemInfoUtil.getSystemOS())) {
				commond = "taskkill /f /im " + name;
			} else {
				commond = "pkill -9 " + name;
				return Result.SUCCESS;
			}
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commond);
			final InputStream is = process.getInputStream();
			new Thread() {
				public void run() {
					byte[] bytes = new byte[1024];
					try {
						while((is.read(bytes) != -1)) {
							logger.info("关闭进程输出信息：" + new String(bytes));
						}
					} catch (IOException e) {
							logger.info("关闭进程读取输出信息异常，原因为：", e);
					} finally {
						try {
							is.close();
						} catch (IOException e) {
						}
					}
					
				};
			};
			
			final InputStream es = process.getErrorStream();
			new Thread() {
				public void run() {
					byte[] bytes = new byte[1024];
					try {
						while((es.read(bytes) != -1)) {
							logger.info("关闭进程错误信息：" + new String(bytes));
						}
					} catch (IOException e) {
							logger.info("关闭进程读取错误信息异常，原因为：", e);
					} finally {
						try {
							es.close();
						} catch (IOException e) {
						}
					}
					
				};
			};
			process.waitFor(60, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.info("关闭进程异常，原因为：", e);
		}
		return Result.SUCCESS;
	}

}
