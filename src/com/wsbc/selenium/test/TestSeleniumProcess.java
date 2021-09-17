package com.wsbc.selenium.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.db.OperationDB;
import com.wsbc.selenium.entity.OperationBean;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.util.SeleniumBaseUtil;

public class TestSeleniumProcess {
	
	public static Log logger = LogFactory.getLog(TestSeleniumProcess.class);
	
	public static int count = 0;
	
//	public static void main(String[] args) throws InterruptedException {
//		KillBrowerOperation.handler("firefox.exe *32");
//		int id = 2;
//		// 第一个操作动作从 1 开始
//		String serial = "1"; 
//		while(true) {
//			OperationBean ob = OperationDB.getData(id, serial);
//			if (ob == null) {
//				logger.info("未查找到此操作步骤：id = " + id + " serial = " + serial);
//				return;
//			}
//			Operation operation = ob.getOperation();
//			operation.handler();
//			serial = ob.getNextSerial();
//			if (serial == null || "".equals(serial)) {
//				logger.info("已正常完成任务");
//			}
//		}
//	}
	
	public static void main(String[] args) throws InterruptedException {
		SeleniumBaseUtil.killBrower();
		TestSeleniumProcess.executeProcess(2);
	}
	
	public static void executeProcess(int id) {
		// 第一个操作动作从 1 开始
		String serial = "1"; 
		while(true) {
			OperationBean ob = OperationDB.getData(id, serial);
			if (ob == null) {
				logger.info("未查找到此操作步骤：id = " + id + " serial = " + serial);
				return;
			}
			Operation operation = ob.getOperation();
			operation.handler();
			serial = ob.getNextSerial();
			if (serial == null || "".equals(serial)) {
				logger.info("已正常完成任务");
				return;
			}
		}
	}
}
