package com.wsbc.httpclient.entity;

/**
 * 状态码字典表
 * @author yxm
 */
public class WsbcStatus{

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 状态码
	 */
	private Integer statusCode;
	/**
	 * 英文
	 */
	private String english;
	/**
	 * 描述
	 */
	private String description;

	public WsbcStatus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnglish() {
		return this.english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	
}