package com.wsbc.selenium.operation.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.result.Result;

/**
 * 多个操作和为一组执行
 */
public class ListOperation extends Operation{
	
	private static final Log logger = LogFactory.getLog(ListOperation.class);
	
	private List<Operation> operationList = new ArrayList<Operation>();
	
	public ListOperation() {
	}

	public ListOperation(List<Operation> operationList) {
		super();
		this.operationList = operationList;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}
	
	public ListOperation addOperationList(Operation operation) {
		operationList.add(operation);
		return this;
	}

	@Override
	public Result handler() {
		if ( operationList == null || operationList.size() == 0)
			return Result.SUCCESS;
		for (Operation operation : operationList) {
			Result result = operation.handler();
			if (!Result.SUCCESS.equals(result)) {
				logger.info("操作组执行失败。。。");
				return result;
			}
		}
		return Result.SUCCESS;
	}

}
