package com.wsbc.selenium.ttjj.task;

import java.util.List;

import com.wsbc.entity.ttjj.Fund;
import com.wsbc.selenium.operation.InputOperation;
import com.wsbc.selenium.operation.OnClickOperation;
import com.wsbc.selenium.operation.OpenSiteOperation;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.operation.WaitOperation;
import com.wsbc.selenium.operation.system.GetDataOperation;
import com.wsbc.selenium.operation.system.ListOperation;

/**
 * 基金相关脚本
 */
public class ShellTTJJ {
	
	public static Operation 打开自选基金页面() {
		return new OpenSiteOperation("http://localhost/");
	}
	
	public static ListOperation 添加自选基金操作(String... 基金代码s) {
		ListOperation operationList = new ListOperation();
		for (String code : 基金代码s) {
			operationList.addOperationList(new InputOperation("#search-input1", code))
						.addOperationList(new WaitOperation(1000))
						.addOperationList(new OnClickOperation("//*[@class='search-submit btn']"))
						.addOperationList(new WaitOperation(1000));
		}
		return operationList;
	}
	
	public static GetDataOperation<List<Fund>> 获取自选基金数据操作() {
		return new GetCustomizeFundData();
	}

}
