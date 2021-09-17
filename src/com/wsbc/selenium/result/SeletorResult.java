package com.wsbc.selenium.result;

import java.util.List;

import org.openqa.selenium.WebElement;

public class SeletorResult extends Result {
	
	private List<WebElement> webElements;

	public SeletorResult(int returnCode, List<WebElement> webElements) {
		super(returnCode);
		this.webElements = webElements;
	}

	public List<WebElement> getWebElements() {
		return webElements;
	}

	public void setWebElements(List<WebElement> webElements) {
		this.webElements = webElements;
	}
	
	public WebElement getWebElement() {
		if (webElements != null && webElements.size() > 0)
			return webElements.get(0);
		return null;
	}
	
}
