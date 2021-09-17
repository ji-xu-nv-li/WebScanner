package com.wsbc.selenium.db;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;

import com.wsbc.selenium.entity.OperationBean;
import com.wsbc.selenium.operation.ExitOperation;
import com.wsbc.selenium.operation.InputOperation;
import com.wsbc.selenium.operation.OpenSiteOperation;
import com.wsbc.selenium.operation.SaveHtmlOperation;
import com.wsbc.selenium.operation.WaitOperation;
import com.wsbc.selenium.ttjj.task.ShellTTJJ;

public class OperationDB {
	
	private static List<OperationBean> list = new ArrayList<OperationBean>();
	
	static {
		list.add(new OperationBean(1, "1", "2", new OpenSiteOperation("http://localhost/")));
		list.add(new OperationBean(1, "2", "3", new SaveHtmlOperation("E:/wsbcTest0.txt")));
		list.add(new OperationBean(1, "3", "4", new InputOperation("#search-input", "001632")));
		list.add(new OperationBean(1, "4", "5", new WaitOperation(2000)));
		list.add(new OperationBean(1, "5", "6", new InputOperation("#search-input", Keys.ENTER + "")));
		list.add(new OperationBean(1, "6", "8", new SaveHtmlOperation("E:/wsbcTest1.txt")));
		list.add(new OperationBean(1, "8", "7", new WaitOperation(10000)));
		list.add(new OperationBean(1, "7", "9", new ExitOperation(ExitOperation.CURRENT)));
		list.add(new OperationBean(1, "9", "99", new WaitOperation(5000)));
//		list.add(new OperationBean(1, "99", Dictionary.OperName.EXIT, "",
//				ExitOperation.ALL, "", "", "0", ""));
		
		list.add(new OperationBean(2, "1", "2", ShellTTJJ.打开自选基金页面()));
		list.add(new OperationBean(2, "2", "3", ShellTTJJ.添加自选基金操作(
//				"001632", "001557", "003027", "002611", "002147",
//				"160629", "005592", "502013", "002199", "001618",
//				"001630", "160628", "001553", "004070", "001593",
				"003096", "005918", "001595")));
		list.add(new OperationBean(2, "3", "4", ShellTTJJ.获取自选基金数据操作()));
		
	}
	
	/**
	 * 方法描述：获取同一批的所有操作步骤
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午5:26:09
	 * @param id
	 * @return
	 * @throws
	 */
	public static List<OperationBean> getData(int id) {
		List<OperationBean> temp = new ArrayList<OperationBean>();
		for (OperationBean oper : list) {
			if (oper.getId() == id) {
				temp.add(oper);
			}
		}
		return temp;
	}
	
	/**
	 * 
	 * 方法描述：
	 * 作者： yxm
	 * 创建时间： 2019年8月11日  上午5:31:51
	 * @param id
	 * @param serial 指定序号，最初始的从 1 开始
	 * @return
	 */
	public static OperationBean getData(int id, String serial) {
		for (OperationBean oper : list) {
			if (oper.getId() == id && oper.getSerial().equals(serial)) {
				return oper;
			}
		}
		return null;
	}

}
