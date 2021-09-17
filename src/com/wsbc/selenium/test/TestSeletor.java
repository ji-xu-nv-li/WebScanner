package com.wsbc.selenium.test;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.wsbc.process.Process;
import com.wsbc.selenium.operation.InputOperation;
import com.wsbc.selenium.operation.OpenSiteOperation;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.operation.SaveHtmlOperation;
import com.wsbc.selenium.operation.SeletorOperation;
import com.wsbc.selenium.operation.WaitOperation;
import com.wsbc.selenium.operation.factory.OperationFactory;
import com.wsbc.selenium.result.SeletorResult;
import com.wsbc.selenium.ttjj.task.ShellTTJJ;
import com.wsbc.selenium.util.SeleniumBaseUtil;
import com.wsbc.selenium.util.SeleniumUtil;

public class TestSeletor {
	
	public static Log logger = LogFactory.getLog(TestSeletor.class);
	
	public static int count = 0;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		SeleniumBaseUtil.killBrower();
//		RemoteWebDriver webDriver = SeleniumUtil.getDriver();
		
		count = 0;
//		SaveHtmlOperation.operation("E:/wsbcTest" + (count++) + ".txt");
		String number = "-1";
		while(true) {
			try {
				switch (number) {
				case "1": 
					OpenSiteOperation.operation("http://localhost/");
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							InputOperation inputOperation = new InputOperation("#search-input", "001632");
							inputOperation.handler();
							WaitOperation.operation(2000);
							
							// 回车触发事件
							InputOperation.operation("#search-input", Keys.ENTER);
							SaveHtmlOperation.operation("E:/wsbcTest" + (count++) + ".txt");
							System.out.println("当前URL:" + SeleniumUtil.getDriver().getCurrentUrl());
						}
					});
					t.start();
					t.join();
					break;
				case "0":
					if (SeleniumUtil.checkDriver()) {
						// SeleniumUtil.getDriver().close(); // 关闭当前窗口
						SeleniumUtil.getDriver().quit(); // 关闭所有窗口
					}
					return;
				case "2":
					OpenSiteOperation.operation("http://localhost/");
					System.out.println("当前URL:" + SeleniumUtil.getDriver().getCurrentUrl());
					break;
				case "3":
					System.out.println(System.getProperties());
					System.out.println(System.getProperty("os.name"));
//					System.out.println(SystemInfoUtil.getSystemOS());
					break;
				case "4":
					Thread operationThread = new Thread(){
						public void run() {
							Scanner scanner = new Scanner(System.in);
							System.out.println("请输入参数:");
							String params = scanner.nextLine();
							String[] strs = new String[]{"", "", "", "", ""};
							String[] temp = params.split("\\|", -1);
							if (temp.length < 5) {
								System.arraycopy(temp, 0, strs, 0, temp.length);
							} else {
								strs = temp;
							}
							Operation op = OperationFactory.getOperation(strs[0], strs[1], strs[2], strs[3], strs[4]);
							if (op != null)
								op.handler();
						};
					};
					operationThread.start();
					operationThread.join();
					break;
				case "5":
					Thread select = new Thread(){
						public void run() {
							Scanner scanner = new Scanner(System.in);
							System.out.println("请输入元素选择方式：");
							String type = scanner.nextLine();
							SeletorOperation seletor = new SeletorOperation(type);
							SeletorResult result = seletor.handler();
							System.out.println("选择结果：");
							List<WebElement> list = result.getWebElements();
							for (int index = 0 ; index < list.size() ; index++) {
								System.out.println("结果" +(index+1)+ "：" + list.get(index).findElement(By.xpath("..")).getText());
							}
						};
					};
					select.start();
					select.join();
					break;
				case "6":
					TestSeleniumProcess.executeProcess(2);
					break;
				case "7":
					ShellTTJJ.获取自选基金数据操作().handler();
					break;
				case "8":
//					Process process = new Process("operation/ttjj/fund.xml");
					Process process = new Process("operation/start.xml");
					process.handler();
					break;
				default:
					break;
					
				}
			} catch (Exception e) {
				logger.info("执行异常，原因为：", e);
			}
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入数字（首选8）：");
			System.out.println("0：退出程序");
			System.out.println("1：初始操作");
			System.out.println("2：打开另一个网址");
			System.out.println("3：查看当前操作系统");
			System.out.println("4：输入操作指令");
			System.out.println("5：元素选择器");
			System.out.println("6：执行添加自选流程");
			System.out.println("7：获取自选基金数据操作");
			System.out.println("8：process方式获取自选基金数据操作");
			number = scanner.next();
		}
			
	}
}
