package com.wsbc.selenium.result;

import java.util.HashMap;
import java.util.Map;

public class Result {
	
	public static final Result SUCCESS = new Result(0);
	
	public static final Result FAILED = new Result(1);
	
	public static final Result EXCEPTION = new Result(2);
	
	private int returnCode = 0;
	
	private Map<String, Object> data = new HashMap<String, Object>();
	
	public Result() {}

	public Result(int returnCode) {
		this.returnCode = returnCode;
	}
	
	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) return false;
		if (this == obj) return true;
		
		if (obj instanceof Result) {
			Result objResult = (Result)obj;
			return this.returnCode == objResult.getReturnCode();
		}
		return false;
	}
	
}
