package com.wsbc.httpclient.server;

import java.io.Serializable;
import java.util.List;

import com.wsbc.httpclient.entity.WsbcInitParam;
import com.wsbc.httpclient.entity.WsbcUser;

public interface IWsbcInitParamServer {

	/**
	 * 方法描述：获得在线用户的默认基本配置信息
	 * @data 2016年4月11日  下午2:19:59
	 * @author yxm
	 */
	public WsbcInitParam getDefaultInitParam(WsbcUser onlineUser);
	
	/**
	 * 方法描述：设置用户的默认基本配置信息
	 * @param defaultInitParamId 默认配置的ID
	 * @data 2016年4月14日  下午4:00:04
	 * @author yxm
	 */
	public void setDefaultInitParam(WsbcUser onlineUser,Integer defaultInitParamId);
	
	/**
	 * 方法描述：获得用户下的所有基本配置信息，并打默认配置信息放在第一位
	 * @data 2016年4月14日  下午3:12:59
	 * @author yxm
	 */
	public List<WsbcInitParam> getUserInitParam(WsbcUser onlineUser);
	
	/**
	 * 方法描述：修改基本信息配置
	 * @data 2016年4月14日  下午3:55:37
	 * @author yxm
	 */
	public void update(WsbcInitParam wsbcInitParam);
	
	/**
	 * 方法描述：添加基本信息配置
	 * @data 2016年4月14日  下午3:56:20
	 * @author yxm
	 */
	public Serializable add(WsbcInitParam wsbcInitParam);
	
	/**
	 * 方法描述：删除基本信息设置
	 * @data 2016年4月14日  下午3:57:11
	 * @author yxm
	 */
	public void delete(WsbcInitParam wsbcInitParam);
	
	/**
	 * 方法描述：获得用户已经配置的系统基本信息数目
	 * @data 2016年4月14日  下午4:06:47
	 * @author yxm
	 */
	public int getUserInitParamCount(WsbcUser onlineUser);
}
