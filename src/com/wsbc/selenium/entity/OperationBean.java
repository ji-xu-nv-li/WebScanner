package com.wsbc.selenium.entity;

import com.wsbc.selenium.operation.Operation;

public class OperationBean {
	
	/**
	 * 每组任务的唯一ID
	 */
	private int id;
	/**
	 * 每个子任务的序号
	 */
	private String serial;
	
	/**
	 * 当前的任务
	 */
	private Operation operation;
	
	/**
	 * 下一个子任务的序号
	 */
	private String nextSerial;
	
	public OperationBean() {}
	
	public OperationBean(int id, String serial, String nextSerial,
			Operation operation) {
		super();
		this.id = id;
		this.serial = serial;
		this.operation = operation;
		this.nextSerial = nextSerial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}


	public String getNextSerial() {
		return nextSerial;
	}

	public void setNextSerial(String nextSerial) {
		this.nextSerial = nextSerial;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

//	@Override
//	public Result handler() {
//		// TODO Auto-generated method stub
//		return operation.handler();
//	}
//
//	@Override
//	public SeleniumProcess getNext() {
//		// TODO Auto-generated method stub
//		return OperationDB.getData(id, serial);
//	}

}
