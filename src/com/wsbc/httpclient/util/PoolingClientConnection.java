package com.wsbc.httpclient.util;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class PoolingClientConnection {

	private static PoolingHttpClientConnectionManager cm = null;
	
	private static synchronized PoolingHttpClientConnectionManager getInstance(){
		if(cm == null){
			cm = new PoolingHttpClientConnectionManager();
			//设置整个连接池中线程数最大值
			cm.setMaxTotal(100);
			//设置每个路由上连接数，原则上，不大于整个线程数量
			cm.setDefaultMaxPerRoute(100);
			
		}
		return cm;
	}
//	System.out.println("MaxTotal:"+cm.getMaxTotal());
//	System.out.println("DefaultMaxPerRoute:"+cm.getDefaultMaxPerRoute());
	public static CloseableHttpClient getClient(){
		if( cm == null){
			getInstance();
		}
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
		return client;
	}
	
	public static CloseableHttpClient getClient(BasicCookieStore cookieStore){
		if( cm == null){
			getInstance();
		}
		CloseableHttpClient client = HttpClients.custom()
				  .setConnectionManager(cm)
				  .setDefaultCookieStore(cookieStore)
				  .build();
		return client;
	}
}
