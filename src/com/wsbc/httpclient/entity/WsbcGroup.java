package com.wsbc.httpclient.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class WsbcGroup{

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 执行扫描的用户
	 */
	private WsbcUser wsbcUser;
	/**
	 * 扫描的时间
	 */
	private Timestamp groupTime;
	/**
	 * 扫描时输入的路径
	 */
	private String url;
	/**
	 * 花费时间
	 */
	private Timestamp spendTime;
	/**
	 * 一组下的所有页面
	 */
	private Set<WsbcHtml> wsbcHtmls = new HashSet<WsbcHtml>(0);

	public WsbcGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WsbcUser getWsbcUser() {
		return this.wsbcUser;
	}

	public void setWsbcUser(WsbcUser wsbcUser) {
		this.wsbcUser = wsbcUser;
	}

	public Timestamp getGroupTime() {
		return groupTime;
	}

	public void setGroupTime(Timestamp groupTime) {
		this.groupTime = groupTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<WsbcHtml> getWsbcHtmls() {
		return wsbcHtmls;
	}

	public void setWsbcHtmls(Set<WsbcHtml> wsbcHtmls) {
		this.wsbcHtmls = wsbcHtmls;
	}

	public Timestamp getSpendTime() {
		return spendTime;
	}
	
	/**
	 * 方法描述：获得字符串格式的时间间隔
	 * @data 2016年4月28日  上午11:13:33
	 * @author yxm
	 */
	@SuppressWarnings("deprecation")
	public String getSpendTimeString(){
		Timestamp init = new Timestamp(0);
		int h = spendTime.getHours()-init.getHours();
		String hour = h>9?""+h:"0"+h;
		int m = spendTime.getMinutes()-init.getMinutes();
		String minutes = m>9?""+m:"0"+m;
		int s = spendTime.getSeconds()-init.getSeconds();
		String seconds = s>9?""+s:"0"+s;
		int n = (spendTime.getNanos()-init.getNanos())/1000000;
		String nanos = n<100?(n<10?"00"+n:"0"+n) : n+"";
		return hour+":"+minutes+":"+seconds+"."+nanos;
	}
	
	public void setSpendTime(Timestamp spendTime) {
		this.spendTime = spendTime;
	}
	
}