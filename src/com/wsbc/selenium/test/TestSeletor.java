package com.wsbc.selenium.test;

import java.util.List;
import java.util.Properties;
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
import com.wsbc.util.file.WsbcPropertiesUtil;

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
		String systemCode = "";
		String projectPath = "";
		if (args != null && args.length > 0) {
			number = "8";
			systemCode = args[0];
			Properties properties = WsbcPropertiesUtil.readProperties("app.properties");
			projectPath = properties.getProperty("project_path") == null ? "" : properties.getProperty("project_path");
		}
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
							
							// ??????????????????
							InputOperation.operation("#search-input", Keys.ENTER);
							SaveHtmlOperation.operation("E:/wsbcTest" + (count++) + ".txt");
							System.out.println("??????URL:" + SeleniumUtil.getDriver().getCurrentUrl());
						}
					});
					t.start();
					t.join();
					break;
				case "0":
					if (SeleniumUtil.checkDriver()) {
						// SeleniumUtil.getDriver().close(); // ??????????????????
						SeleniumUtil.getDriver().quit(); // ??????????????????
					}
					SeleniumBaseUtil.killBrower();
					return;
				case "2":
					OpenSiteOperation.operation("http://localhost/");
					System.out.println("??????URL:" + SeleniumUtil.getDriver().getCurrentUrl());
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
							System.out.println("???????????????:");
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
							System.out.println("??????????????????????????????");
							String type = scanner.nextLine();
							SeletorOperation seletor = new SeletorOperation(type);
							SeletorResult result = seletor.handler();
							System.out.println("???????????????");
							List<WebElement> list = result.getWebElements();
							for (int index = 0 ; index < list.size() ; index++) {
								System.out.println("??????" +(index+1)+ "???" + list.get(index).findElement(By.xpath("..")).getText());
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
					ShellTTJJ.??????????????????????????????().handler();
					break;
				case "8":
//					Process process = new Process("operation/ttjj/fund.xml");
					Process process = new Process(projectPath + "operation/start.xml");
					process.handler();
					break;
				default:
					break;
					
				}
			} catch (Exception e) {
				logger.info("???????????????????????????", e);
			}
			if (systemCode.isEmpty()) {
				number = scannerNumber();
			} else {
				number = "0";
			}
		}
	}
	
	public static String scannerNumber() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("????????????????????????8??????");
		System.out.println("0???????????????");
		System.out.println("1???????????????");
		System.out.println("2????????????????????????");
		System.out.println("3???????????????????????????");
		System.out.println("4?????????????????????");
		System.out.println("5??????????????????");
		System.out.println("6???????????????????????????");
		System.out.println("7?????????????????????????????????");
		System.out.println("8???process????????????????????????????????????");
		String number = scanner.next();
		return number;
	}
}
