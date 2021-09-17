package com.wsbc.process;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.process.constant.ProcessConstant;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.operation.factory.OperationFactory;
import com.wsbc.selenium.result.Result;
import com.wsbc.util.StringUtil;

public class OperationNode extends Node {
	
	private static final Log logger = LogFactory.getLog(OperationNode.class);
	
	private Operation operation;
	
	private Result result;
	
	public String getType() {
		return this.getAttribute(ProcessConstant.TYPE);
	}

	@Override
	public void doHanlder() {
		result = operation.handler();
	}
	
	@Override
	protected void preHanlder() {
		super.preHanlder();
		String type = this.getType();
		operation = OperationFactory.getOperation(type, getRequest());
	}
	
	@Override
	protected void postHandler() {
		super.postHandler();
		Map<String, String> out = getOut();
		if (out.size() > 0) {
			for (Entry<String, String> entry : out.entrySet()) {
				Object value = result.getData().get(entry.getKey());
				this.putResponse(entry.getValue(), StringUtil.objectToString(value));
			}
		}
	}
}
