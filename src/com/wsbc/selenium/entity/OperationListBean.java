package com.wsbc.selenium.entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OperationListBean extends OperationBean{
	
	public static final Log logger = LogFactory.getLog(OperationListBean.class);
	
//	private List<Operation> list = new ArrayList<Operation>();
	
//	@Override
//	public Result handler() {
//		if ( list == null || list.size() == 0)
//			return Result.SUCCESS;
//		for (Operation operation : list) {
//			Result result = operation.handler();
//			if (!Result.SUCCESS.equals(result)) {
//				logger.info("流程组执行失败。。。");
//				return result;
//			}
//		}
//		return Result.SUCCESS;
//	}

}
