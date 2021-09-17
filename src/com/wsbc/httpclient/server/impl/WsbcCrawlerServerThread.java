package com.wsbc.httpclient.server.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;

import org.apache.http.HttpStatus;

import com.wsbc.httpclient.entity.WsbcGroup;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcInitParam;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.server.IWsbcCrawlerServerThread;
import com.wsbc.httpclient.server.IWsbcInitParamServer;
import com.wsbc.httpclient.util.HttpClientServerThread;
import com.wsbc.util.crawler.LinkFilter;
import com.wsbc.util.crawler.LinkQueue;
import com.wsbc.util.crawler.OnlySiteFilter;
import com.wsbc.util.parser.HtmlParserToolByJsoup;

public class WsbcCrawlerServerThread implements IWsbcCrawlerServerThread{

	private HttpClientServerThread httpClientServer;
	
	@Resource(name="wsbcInitParamServer")
	private IWsbcInitParamServer wsbcInitParamServer;
	
	private LinkQueue linkQueue;
	
	/**
	 * 扫描系统设置
	 */
	private WsbcInitParam wsbcInitParam;
	
	//只扫描当前网站的过滤器
	private LinkFilter filter = null;
	
	/**
	 * 同一组扫描的页面
	 */
	private WsbcGroup wsbcGroup;
	
	@Override
	public List<WsbcHtml> crawlering(WsbcUrl seed) {
		// TODO Auto-generated method stub
		//初始化爬虫基本信息
		init(seed);
		List<WsbcHtml> htmls = new ArrayList<WsbcHtml>();
//		Vector<FutureTask<WsbcHtml>> tasks = new Vector<FutureTask<WsbcHtml>>();
		Set<FutureTask<WsbcHtml>> tasks = new HashSet<FutureTask<WsbcHtml>>();
		while(isContinueCraw() || tasks.size()>0){
			//启动 获取URL页面的线程
			while(isContinueCraw()){
				// 队头URL出队列
				WsbcUrl tempUrl = linkQueue.unVisitedUrlDeQueue();
				//获得当前扫描的深度
				int currentDepth = tempUrl.getDepth();
				if(currentDepth<=wsbcInitParam.getScannerDepth()){
					//未超过扫描深度，可以扫描
					System.out.println();
					System.out.println("扫描深度："+tempUrl.getDepth()+"  visitURL: Priority "+tempUrl.getPriority()+" "+tempUrl.getUrl());
					System.out.println();
					FutureTask<WsbcHtml> task = httpClientServer.requestTask(tempUrl);
					tasks.add(task);
					// 该 url 放入到已访问的 URL 中
					linkQueue.addVisitedUrl(tempUrl);
				}
			}
			
			try {
				//遍历所有任务，对已完成的任务进行操作
				//获取页面，并移除已完成的任务
				//同时分析页面，获取下一个爬取路径的节点
				Iterator<FutureTask<WsbcHtml>> iterator = tasks.iterator();
				while(iterator.hasNext()){
					FutureTask<WsbcHtml> task = iterator.next();
					if(task.isDone()){
						WsbcHtml wsbcHtml = task.get();
						//设置为爬取的同一组
						wsbcHtml.setWsbcGroup(wsbcGroup);
						//添加网页进数组
						htmls.add(wsbcHtml);
						//根据网页返回的不同状态，决定爬虫的执行过程
						analyseStatusCode(wsbcHtml);
						iterator.remove();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return htmls;
	}

	/**
	 * 方法描述：初始化爬虫基本信息
	 * @data 2016年5月4日  上午10:24:20
	 * @author yxm
	 */
	private void init(WsbcUrl tempUrl) {
		// TODO Auto-generated method stub
		if(httpClientServer == null){
			httpClientServer = new HttpClientServerThread();
		}
		linkQueue = new LinkQueue();
		// 初始化 URL 队列
		initCrawlerWithSeeds(new WsbcUrl[]{tempUrl});
		// 获得默认的配置信息
		wsbcInitParam = new WsbcInitParam();
		if(wsbcGroup != null){
			//获得用户设置的系统基本信息
			wsbcInitParam = wsbcInitParamServer.getDefaultInitParam(wsbcGroup.getWsbcUser());
		}
		if(wsbcInitParam.getOnlySite() == 1){
			// 定义过滤器，提取以http://www.baidu.com开头的链接
			filter = new OnlySiteFilter(tempUrl);
		}
	}

	/**
	 * 方法描述：判断是否继续扫描
	 * @data 2016年5月4日  上午9:36:53
	 * @author yxm
	 */
	private boolean isContinueCraw(){
		//未访问URL不为空
		if(!linkQueue.unVisitedUrlIsEmpty()
				//已访问的页面未超过总访问页面数
				&& linkQueue.getVisitedUrlNum() < wsbcInitParam.getMaxnum()) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<WsbcHtml> crawlering(WsbcUrl[] seeds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WsbcHtml> crawler(WsbcUrl wsbcUrl, WsbcGroup wsbcGroup,HttpClientServerThread httpClientServer) {
		// TODO Auto-generated method stub
		this.httpClientServer = httpClientServer;
		this.wsbcGroup = wsbcGroup;
		return crawlering(wsbcUrl);
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
	
	/**
	 * 方法描述：根据网页返回的不同状态，决定爬虫的执行过程
	 * @data 2016年4月17日  上午12:43:21
	 * @author yxm
	 */
	private void analyseStatusCode(WsbcHtml html){
		int statusCode = html.getWsbcStatus().getStatusCode();
		WsbcUrl currentUrl = html.getWsbcUrl();
		// 提取出下载网页中的 URL
		//为了不提取相同的路径，所以使用Set集合，因此每次扫描结果的顺序会有所不同
		Set<WsbcUrl> links = HtmlParserToolByJsoup.extracLinks(html, filter);
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
