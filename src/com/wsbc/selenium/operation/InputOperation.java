package com.wsbc.selenium.operation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.wsbc.selenium.result.Result;
import com.wsbc.selenium.util.ParseSelectorUtil;

public class InputOperation extends Operation {
	
	private static final Log logger = LogFactory.getLog(InputOperation.class);
	
	private String value;
	
	private String location;
	
	public InputOperation() {}
	
	public InputOperation(String location, String value) {
		this.location = location;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public Result handler() {
		return operation(location, value);
	}
	
	/**
	 * 方法描述：输入信息操作
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午3:47:12
	 * @param location 定位输入域
	 * @param value 输入值
	 * @return 0-操作成功
	 */
	public static Result operation(String location, CharSequence... value) {
		long a = System.currentTimeMillis();
		logger.info("输入信息开始，定位：" + location + ", 内容：");
		for (int index = 0 ; index < value.length ; index++) {
			logger.info(index + ": " + value[index]);
		}
		
		WebElement input = ParseSelectorUtil.findElement(location);
		
		input.click(); // 获得焦点
		if (value.length == 1 && value[0].equals(Keys.ENTER)) {
			// 不清空数据
		} else {
			// 清空选项内容
//			input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE); 
			input.clear();
		}
		input.sendKeys(value);
		
		long b = System.currentTimeMillis();
		logger.info("输入信息结束，耗时：" + (b-a) + "毫秒");
		return Result.SUCCESS;
	}

}
