package com.wsbc.selenium.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.SeleniumUtil;
import com.wsbc.util.file.WsbcFileUtil;

public class SaveHtmlOperation extends Operation {
	
	private static final Log logger = LogFactory.getLog(SaveHtmlOperation.class);
	
	private String localPath;

	public SaveHtmlOperation() {}
	
	public SaveHtmlOperation(String localPath) {
		this.localPath = localPath;
	}
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	@Override
	public Result handler() {
		// TODO Auto-generated method stub
		return operation(localPath);
	}
	
	public static Result operation(String localPath) {
		long a = System.currentTimeMillis();
		logger.info("保存网站开始，文件路径：" + localPath );
		
		WsbcFileUtil.writeFile(SeleniumUtil.getDriver().getPageSource(), localPath, false);
		
		long b = System.currentTimeMillis();
		logger.info("保存网站结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
