package com.wsbc.httpclient.entity;

/**
 * 设置扫描的初始参数
 * @author yxm
 *
 */
public class WsbcInitParam{

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 用户
	 */
	private WsbcUser wsbcUser;
	/**
	 * 设置页面的扫描深度
	 * 初始：5
	 */
	private Integer scannerDepth = 5;
	/**
	 * 设置最多扫描的页面数量
	 * 初始：1000
	 */
	private Integer maxnum = 1000;
	/**
	 * 是否只访问URL对应的网站
	 * 初始：只访问 
	 */
	private Integer onlySite = 1;
	/**
	 * 标记：是否为默认配置    
	 * 初始：非默认
	 */
	private Integer tag = 0;
	
	public WsbcInitParam() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScannerDepth() {
		return this.scannerDepth;
	}

	public void setScannerDepth(Integer scannerDepth) {
		this.scannerDepth = scannerDepth;
	}

	public Integer getMaxnum() {
		return this.maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public Integer getOnlySite() {
		return onlySite;
	}

	public void setOnlySite(Integer onlySite) {
		this.onlySite = onlySite;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public WsbcUser getWsbcUser() {
		return wsbcUser;
	}

	public void setWsbcUser(WsbcUser wsbcUser) {
		this.wsbcUser = wsbcUser;
	}

}