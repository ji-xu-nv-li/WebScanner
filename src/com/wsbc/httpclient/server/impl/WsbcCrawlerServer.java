package com.wsbc.httpclient.server.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpStatus;

import com.wsbc.httpclient.entity.WsbcGroup;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcInitParam;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.server.IWsbcCrawlerServer;
import com.wsbc.httpclient.server.IWsbcInitParamServer;
import com.wsbc.httpclient.util.HttpClientServer;
import com.wsbc.util.crawler.LinkFilter;
import com.wsbc.util.crawler.LinkQueue;
import com.wsbc.util.crawler.OnlySiteFilter;
import com.wsbc.util.parser.HtmlParserToolByJsoup;

public class WsbcCrawlerServer implements IWsbcCrawlerServer{
	
	private IWsbcInitParamServer wsbcInitParamServer;
	
	private HttpClientServer client = new HttpClientServer();
	
	private LinkQueue linkQueue;
	
	//只扫描当前网站的过滤器
	private LinkFilter filter = null;
	
	/**
	 * 同一组扫描的页面
	 */
	private WsbcGroup wsbcGroup;

	@Override
	public List<WsbcHtml> crawlering(WsbcUrl seed) {
		// TODO Auto-generated method stub
		return crawlering(new WsbcUrl[]{seed});
	}

	@Override
	public List<WsbcHtml> crawlering(WsbcUrl[] seeds) {
		// TODO Auto-generated method stub
		linkQueue = new LinkQueue();
		List<WsbcHtml> htmls = new ArrayList<WsbcHtml>();
		// 初始化 URL 队列
		initCrawlerWithSeeds(seeds);
		// 获得默认的配置信息
		WsbcInitParam wsbcInitParam = new WsbcInitParam();
		if(wsbcGroup != null){
			wsbcInitParam = wsbcInitParamServer.getDefaultInitParam(wsbcGroup.getWsbcUser());
		}
		// 循环条件：待抓取的链接不空且抓取的网页不多于1000
		while (!linkQueue.unVisitedUrlIsEmpty()
				&& linkQueue.getVisitedUrlNum() < wsbcInitParam.getMaxnum()) {
			// 队头URL出队列
			WsbcUrl tempUrl = linkQueue.unVisitedUrlDeQueue();
			//获得当前扫描的深度
			int currentDepth = tempUrl.getDepth();
			if(currentDepth>wsbcInitParam.getScannerDepth()){
				//超过扫描深度，停止扫描
				break;
			}
			filter = null;
			if(wsbcInitParam.getOnlySite() == 1){
				// 定义过滤器，提取以http://www.baidu.com开头的链接
				filter = new OnlySiteFilter(tempUrl);
			}
			System.out.println();
			System.out.println("扫描深度："+currentDepth+"  visitURL: Priority "+tempUrl.getPriority()+" "+tempUrl.getUrl());
			System.out.println();
			//获取网页
			WsbcHtml html = client.request(tempUrl);
			//设置为爬取的同一组
			html.setWsbcGroup(wsbcGroup);
			// 该 url 放入到已访问的 URL 中
			linkQueue.addVisitedUrl(tempUrl);
			//添加网页进数组
			htmls.add(html);
			//根据网页返回的不同状态，决定爬虫的执行过程
			analyseStatusCode(html);
		}
		return htmls;
	}

	/**
	 * 使用种子初始化 URL 队列
	 * @param seeds 种子URL
	 */
	private void initCrawlerWithSeeds(WsbcUrl[] seeds) {
		for (int i = 0; i < seeds.length; i++){
			seeds[i].setPriority(linkQueue.getPriority());
			seeds[i].setDepth(1);
			linkQueue.addUnvisitedUrl(seeds[i]);
		}
	}

	@Override
	public List<WsbcHtml> crawler(WsbcUrl wsbcUrl,WsbcGroup group) {
		// TODO Auto-generated method stub
		//设置组信息
		this.wsbcGroup = group;
		return crawlering(wsbcUrl);
	}
	
	/**
	 * 方法描述：根据网页返回的不同状态，决定爬虫的执行过程
	 * @data 2016年4月17日  上午12:43:21
	 * @author yxm
	 */
	private void analyseStatusCode(WsbcHtml wsbcHtml){
		int statusCode = wsbcHtml.getWsbcStatus().getStatusCode();
		WsbcUrl currentUrl = wsbcHtml.getWsbcUrl();
		// 提取出下载网页中的 URL
		//为了不提取相同的路径，所以使用Set集合，因此每次扫描结果的顺序会有所不同
		Set<WsbcUrl> links = HtmlParserToolByJsoup.extracLinks(wsbcHtml, filter);
		// 新的未访问的 URL 入队
		for (WsbcUrl link : links) {
			//判断当前路径能否添加进未访问的队列
			if(linkQueue.isAddUnvisitedUrl(link)){
				if(statusCode == HttpStatus.SC_MOVED_TEMPORARILY){
					//302 重定向  爬取过程：获得重定向的路径，尽早重定向
					//设置优先级与当前相同
					link.setPriority(currentUrl.getPriority());
					//设置扫描深度与当前相同
					link.setDepth(currentUrl.getDepth());
				}else{
					//设置优先级
					int priority = linkQueue.getPriority();
					System.out.println("当前优先级："+priority+"  新添的路径："+link.getUrl());
					link.setPriority(priority);
					//设置扫描深度：当前扫描深度的基础上 +1
					link.setDepth(currentUrl.getDepth()+1);
				}
				linkQueue.addUnvisitedUrl(link);
			}
		}
	}
}
