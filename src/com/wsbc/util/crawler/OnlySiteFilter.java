package com.wsbc.util.crawler;

import com.wsbc.httpclient.entity.WsbcUrl;

/**
 * 只访问当前网站的过滤器
 * @author yxm
 */
public class OnlySiteFilter implements LinkFilter {
	
	private String filterOnly;

	/**
	 * @param wsbcUrl 当前网站
	 */
	public OnlySiteFilter(WsbcUrl wsbcUrl){
		this.filterOnly = wsbcUrl.getProtocol()+"://"+wsbcUrl.getHost();
	}
	
	@Override
	public boolean accept(String url) {
		// TODO Auto-generated method stub
		if (url != null && url.startsWith(filterOnly))
			return true;
		else
			return false;
	}

}
