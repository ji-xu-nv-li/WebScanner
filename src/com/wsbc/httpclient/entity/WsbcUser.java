package com.wsbc.httpclient.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class WsbcUser {

	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户名 即为账号
	 */
	private String userName;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	/**
	 * 上次登录时间
	 */
	private Timestamp lastTime;
	
	private Set<WsbcInitParam> wsbcInitParams = new HashSet<WsbcInitParam>(0);

	public WsbcUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 复制一份
	 */
	public WsbcUser(WsbcUser wsbcUser){
		this.id = wsbcUser.getId();
		this.createTime = wsbcUser.getCreateTime();
		this.email = wsbcUser.getEmail();
		this.lastTime = wsbcUser.getLastTime();
		this.password = wsbcUser.getPassword();
		this.userName = wsbcUser.getUserName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public Set<WsbcInitParam> getWsbcInitParams() {
		return wsbcInitParams;
	}

	public void setWsbcInitParams(Set<WsbcInitParam> wsbcInitParam) {
		this.wsbcInitParams = wsbcInitParam;
	}
	
}
