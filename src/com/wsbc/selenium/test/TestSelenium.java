package com.wsbc.selenium.test;

import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.wsbc.selenium.operation.InputOperation;
import com.wsbc.selenium.operation.OpenSiteOperation;
import com.wsbc.selenium.operation.SaveHtmlOperation;
import com.wsbc.selenium.operation.WaitOperation;
import com.wsbc.selenium.util.SeleniumBaseUtil;
import com.wsbc.selenium.util.SeleniumUtil;
import com.wsbc.util.parser.HtmlParserToolByJsoup;

public class TestSelenium {
	
	public static Log logger = LogFactory.getLog(TestSelenium.class);
	
	public static int count = 0;
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		SeleniumBaseUtil.killBrower();
		RemoteWebDriver webDriver = SeleniumUtil.getDriver();
		OpenSiteOperation.operation("http://localhost/");
		count = 0;
		SaveHtmlOperation.operation("E:/wsbcTest" + (count++) + ".txt");
		int number = 1;
		while(true) {
			switch (number) {
				case 1: 
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							InputOperation inputOperation = new InputOperation("#search-input", "001632");
							inputOperation.handler();
							WaitOperation.operation(1000);
							
							// 解析HTML
							Document document = HtmlParserToolByJsoup.parserHtmlByString(webDriver.getPageSource());
//							Elements elements = document.getElementsByTag("div[class=\"leftpanel seaWidthL\"]");
							Elements elements = document.getElementsByAttributeValue("class", "leftpanel seaWidthL");
//							logger.info("elements 44: " + elements.html());
							for (Element element : elements) {
								Element tbodytr = element.getElementsByTag("tbody").get(0)
										.getElementsByTag("tr").get(0);
								logger.info(String.format("data-submenu = %s, class = %s,",
										tbodytr.attr("data-submenu"), tbodytr.attr("class")));
							}
							
//							WebElement tbodyTr = webDriver.findElement(By.xpath("//*[@class='leftpanel seaWidthL']"))
//									.findElement(By.xpath("child::tbody/tr[1]"));
//							tbodyTr.getAttribute("class");
							
							// 回车触发事件
							InputOperation.operation("#search-input", Keys.ENTER);
//							input.sendKeys(2000,Keys.ENTER); 
							
							// 获取窗口信息
//							Set<String> handles = webDriver.getWindowHandles();
//					logger.info("所有窗口信息：" + handles);
//					logger.debug("所有窗口信息：" + handles);
//					String currentHandle = webDriver.getWindowHandle();
//					for (String handle : handles) {
//						if (!handle.equals(currentHandle)) {
//							webDriver.switchTo().window(handle); // 换窗口位置
//						}
//					}
							SaveHtmlOperation.operation("E:/wsbcTest" + (count++) + ".txt");
//					WebElement submit = webDriver.findElementByClassName("search-submit");
//					logger.info("submit的内容：" + submit);
//					submit.click();
							System.out.println("当前URL:" + webDriver.getCurrentUrl());
							
						}
					}).start();
					break;
				case 0:
					// webDriver.close(); // 关闭当前窗口
					webDriver.quit(); // 关闭所有窗口
					return;
				case 2:
					OpenSiteOperation.operation("http://localhost/");
					System.out.println("当前URL:" + webDriver.getCurrentUrl());
					break;
				case 3:
					break;
			}
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入数字：");
			number = scanner.nextInt();
		}
	}
}
