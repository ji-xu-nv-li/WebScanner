package com.wsbc.util.crawler;

import com.wsbc.httpclient.server.IWsbcHtmlServer;

public class MyCrawler {
	
	private IWsbcHtmlServer htmlServer;
	
	private LinkQueue linkQueue = new LinkQueue();
	
	//只访问当前网站
	private String filterOnly;
	//网页的最大数量
	private int maxNum = 11;
	
	public IWsbcHtmlServer getHtmlServer() {
		return htmlServer;
	}

	public void setHtmlServer(IWsbcHtmlServer htmlServer) {
		this.htmlServer = htmlServer;
	}

	public LinkQueue getLinkQueue() {
		return linkQueue;
	}

	public void setLinkQueue(LinkQueue linkQueue) {
		this.linkQueue = linkQueue;
	}

	public String getFilterOnly() {
		return filterOnly;
	}

	public void setFilterOnly(String filterOnly) {
		this.filterOnly = filterOnly;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	/**
	 * 使用种子初始化 URL 队列
	 * @param seeds 种子URL
	 */
//	private void initCrawlerWithSeeds(String[] seeds) {
//		for (int i = 0; i < seeds.length; i++)
//			linkQueue.addUnvisitedUrl(seeds[i]);
//	}
//
//	/**
//	 * 方法描述：单个路径
//	 * 创建时间：2016年3月30日  下午5:04:58
//	 * 作者：yxm
//	 */
//	public List<WsbcHtml> crawling(String seed){
//		return crawling(new String[]{seed});
//	}
	
	/**
	 * 抓取过程
	 */
//	public List<WsbcHtml> crawling(String[] seeds) { 
//		
//		List<WsbcHtml> htmls = new ArrayList<WsbcHtml>();
//		// 初始化 URL 队列
//		initCrawlerWithSeeds(seeds);
//		// 循环条件：待抓取的链接不空且抓取的网页不多于1000
//		while (!linkQueue.unVisitedUrlIsEmpty()
//				&& linkQueue.getVisitedUrlNum() <= maxNum) {
//			// 队头URL出队列
//			String visitUrl = (String) linkQueue.unVisitedUrlDeQueue();
//			if (visitUrl == null)
//				continue;
//			WsbcUrl tempUrl = new WsbcUrl(visitUrl);
//			filterOnly = tempUrl.getProtocol()+"://"+tempUrl.getHost();
//			// 定义过滤器，提取以http://www.baidu.com开头的链接
//			LinkFilter filter = new LinkFilter() {
//				public boolean accept(String url) {
//					if (url.startsWith(filterOnly))
//						return true;
//					else
//						return false;
//				}
//			};
//			System.out.println("visitURL:"+visitUrl);
//			//获取网页
//			WsbcHtml html = HttpClientServer.get(visitUrl);
//			// 保存网页
////			WsbcFileUtil.saveWsbcHtml(html);
//			htmlServer.save(html);
//			// 该 url 放入到已访问的 URL 中
//			linkQueue.addVisitedUrl(visitUrl);
//			// 提取出下载网页中的 URL
//			Set<String> links = HtmlParserToolByJsoup.extracLinks(html, filter);
//			// 新的未访问的 URL 入队
//			for (String link : links) {
//				linkQueue.addUnvisitedUrl(link);
//			}
//			htmls.add(html);
//		}
//		return htmls;
//	}
	
}
