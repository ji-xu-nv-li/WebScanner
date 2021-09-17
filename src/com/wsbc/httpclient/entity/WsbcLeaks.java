package com.wsbc.httpclient.entity;

import com.wsbc.util.file.WsbcFileUtil;

/**
 * 存放所有漏洞的集合
 * @author yxm
 */
public class WsbcLeaks {
	
	private Integer id;
	
	private WsbcSqlLeak wsbcSqlLeak;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WsbcSqlLeak getWsbcSqlLeak() {
		return wsbcSqlLeak;
	}

	public void setWsbcSqlLeak(WsbcSqlLeak wsbcSqlLeak) {
		this.wsbcSqlLeak = wsbcSqlLeak;
	}
	
	public boolean isEmptyLeaks(){
		if(id != null){
			return false;
		}
		if(wsbcSqlLeak == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 方法描述：保存测试数据到本地文件
	 * @data 2016年4月25日  上午11:12:21
	 * @author yxm
	 */
	public void saveLeaksTestData(){
		if(wsbcSqlLeak != null){
//			String t = wsbcSqlLeak.getDataForm().getParam().getParameters() +"ddddddddddddd"
//					+ "d d d d d d d ddddddddddddddddddddddddddddddddddddd"
//					+ "dddddddddddddddddddddddddddddddddddddddddddddd"
//					+ "dddddddddddddddddddddddddddddddddddddddddddd"
//					+ "ddddddddddddddddddddddddddddddddddddddddddddd";
//			wsbcSqlLeak.getDataForm().getParam().setParameters(t);
			WsbcFileUtil.saveWsbcParam(wsbcSqlLeak.getDataForm());
		}
	}
	
	/**
	 * 方法描述：删除本地文件的测试数据
	 * @data 2016年4月25日  上午11:12:57
	 * @author yxm
	 */
	public void deleteLeaksTestData(){
		if(wsbcSqlLeak != null){
			WsbcFileUtil.deleteFile(wsbcSqlLeak.getDataForm().getParam().getParametersPath());
		}
	}
}
