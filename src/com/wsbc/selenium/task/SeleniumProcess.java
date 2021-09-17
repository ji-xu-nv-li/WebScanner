package com.wsbc.selenium.task;

import com.wsbc.selenium.result.Result;

public interface SeleniumProcess {
	
	public Result handler();
	
	public SeleniumProcess getNext();

}
