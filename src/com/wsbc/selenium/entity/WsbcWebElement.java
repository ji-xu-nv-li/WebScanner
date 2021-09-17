package com.wsbc.selenium.entity;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * 为实现 sendKeys操作需要休眠的功能
 */
public class WsbcWebElement implements WebElement{
	
	private WebElement webElement = null;
	
	public WsbcWebElement(WebElement webElement) {
		// TODO Auto-generated constructor stub
		this.webElement = webElement;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target)
			throws WebDriverException {
		// TODO Auto-generated method stub
		return webElement.getScreenshotAs(target);
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		webElement.click();
	}

	@Override
	public void submit() {
		// TODO Auto-generated method stub
		webElement.submit();
	}

	/**
	 * 每次模拟键盘操作，睡眠100毫秒
	 */
	@Override
	public void sendKeys(CharSequence... keysToSend) {
		// TODO Auto-generated method stub
		// 每次睡眠 100毫秒
		sendKeys(100, keysToSend);
	}
	
	/**
	 * 方法描述：指定快捷键操作睡眠的时间
	 * 作者： yxm
	 * 创建时间： 2018年12月31日  下午1:23:23
	 * @param sleepTime 睡眠时间
	 * @param keysToSend
	 * @throws
	 */
	public void sendKeys(long sleepTime, CharSequence... keysToSend) {
		if (sleepTime < 0) throw new IllegalArgumentException("睡眠时间无效：sleepTime = " + sleepTime);
		if (keysToSend == null) return;
		for (CharSequence chars : keysToSend) {
			webElement.sendKeys(chars);
			if (sleepTime != 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		webElement.clear();
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return webElement.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		// TODO Auto-generated method stub
		return webElement.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return webElement.isSelected();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return webElement.isEnabled();
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return webElement.getText();
	}

	@Override
	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		return webElement.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		return webElement.findElement(by);
	}

	@Override
	public boolean isDisplayed() {
		// TODO Auto-generated method stub
		return webElement.isDisplayed();
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return webElement.getLocation();
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return webElement.getSize();
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return webElement.getRect();
	}

	@Override
	public String getCssValue(String propertyName) {
		// TODO Auto-generated method stub
		return webElement.getCssValue(propertyName);
	}

}
