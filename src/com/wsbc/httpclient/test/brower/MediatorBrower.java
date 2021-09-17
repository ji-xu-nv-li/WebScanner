package com.wsbc.httpclient.test.brower;

/**
 * 中间者浏览器，主要处理转换等事务
 */
public class MediatorBrower {
	
	public static String SYS_URL = "http://localhost:8080/mediator";
	
	/**
	 * 方法描述：将网页中，目标网站的地址替换为本系统指定网址
	 * 作者： yxm
	 * 创建时间： 2018年7月29日  下午4:01:25
	 * @return
	 * @throws
	 */
	public static String changUrl(String html, String sourceUrl, String desUrl) {
		return html.replaceAll(sourceUrl, desUrl);
	}
}
