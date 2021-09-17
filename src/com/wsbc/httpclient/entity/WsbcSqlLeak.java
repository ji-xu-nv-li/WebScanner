package com.wsbc.httpclient.entity;

import java.sql.Timestamp;

public class WsbcSqlLeak{

	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 发现漏洞的时间
	 */
	private Timestamp findDate;
	
	/**
	 * 表单提交的数据：
	 * 表单的请求方式、路径、参数等
	 */
	private WsbcForm dataForm;
	
	/**
	 * 表单所在的页面，即含有漏洞的页面
	 */
//	private WsbcHtml wsbcHtml;
	
	/**
	 * 删除标识
	 * 1、永久保存 
	 * 2、已删除，但还保留在数据库中
	 */
	private Integer flag = 1;
	

	public WsbcSqlLeak() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getFindDate() {
		return findDate;
	}

	public void setFindDate(Timestamp findDate) {
		this.findDate = findDate;
	}

	public WsbcForm getDataForm() {
		return dataForm;
	}

	public void setDataForm(WsbcForm dataForm) {
		this.dataForm = dataForm;
	}

//	public WsbcHtml getWsbcHtml() {
//		return wsbcHtml;
//	}
//
//	public void setWsbcHtml(WsbcHtml wsbcHtml) {
//		this.wsbcHtml = wsbcHtml;
//	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
