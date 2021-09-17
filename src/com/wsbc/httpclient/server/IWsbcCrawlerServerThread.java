package com.wsbc.httpclient.server;

import java.util.List;

import com.wsbc.httpclient.entity.WsbcGroup;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.util.HttpClientServerThread;

public interface IWsbcCrawlerServerThread {
	/**
	 * 方法描述：单个路径
	 * 创建时间：2016年3月30日  下午5:04:58
	 * 作者：yxm
	 */
	public List<WsbcHtml> crawlering(WsbcUrl seed);
	
	/**
	 * 抓取 多个页面
	 */
	public List<WsbcHtml> crawlering(WsbcUrl[] seeds) ;
	
	/**
	 * 方法描述：爬去路径对应的网页信息 
	 * @param httpClientServer 多线程管理HttpClient
	 * @param wsbcGroup 组信息
	 * @data 2016年4月11日  下午1:57:43
	 * @author yxm
	 */
	public List<WsbcHtml> crawler(WsbcUrl wsbcUrl,WsbcGroup wsbcGroup,HttpClientServerThread httpClientServer);

}
