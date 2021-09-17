package com.wsbc.util.crawler;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import com.wsbc.httpclient.entity.WsbcUrl;

public class LinkQueue {
	
	//已访问的url集合
	private Set<WsbcUrl> visitedUrl = new HashSet<WsbcUrl>();
	
	//待访问的url集合
	private Queue<WsbcUrl> unVisitedUrl = new PriorityBlockingQueue<WsbcUrl>();
	
	//优先级，从 1 开始
	private int priority = 1;
	
	/**
	 * 方法描述：获取当前优先级，并使优先级加 1
	 * @data 2016年4月8日  下午6:31:33
	 * @author yxm
	 */
	public synchronized int getPriority(){
		return this.priority++;
	}

	public synchronized Queue<WsbcUrl> getUnVisitedUrl(){
		return unVisitedUrl;
	}

	/**
	 * 方法描述：添加到访问过的URL队列中
	 * @author yxm
	 */
	public synchronized void addVisitedUrl(WsbcUrl wsbcUrl){
		visitedUrl.add(wsbcUrl);
	}
	
	/**
	 * 方法描述：移除访问过的URL
	 * @author yxm
	 */
	public synchronized void removeVisitedUrl(WsbcUrl wsbcUrl){
		visitedUrl.remove(wsbcUrl);
	}
	
	/**
	 * 方法描述：未访问的URL出队列
	 * @author yxm
	 */
	public synchronized WsbcUrl unVisitedUrlDeQueue(){
		return unVisitedUrl.poll();
	}
	
	/**
	 * 添加未访问过的URL<br>
	 * 保证每个URL只被访问一次<br>
	 * 拒绝空的URL
	 * @author yxm
	 */
	public synchronized boolean isAddUnvisitedUrl(WsbcUrl wsbcUrl){
		if(wsbcUrl != null && wsbcUrl.getUrl() != null && !wsbcUrl.getUrl().trim().equals("")&& 
			!visitedUrl.contains(wsbcUrl) && !unVisitedUrl.contains(wsbcUrl)){
			return true;
		}
		return false;
	}
	
	/**
	 * 添加新的未访问路径
	 * @author yxm
	 */
	public synchronized boolean addUnvisitedUrl(WsbcUrl wsbcUrl){
		if(isAddUnvisitedUrl(wsbcUrl)){
			return unVisitedUrl.add(wsbcUrl);
		}
		return false;
	}
	
	/**
	 * 方法描述：获得已访问的URL数目
	 * @author yxm
	 */
	public synchronized int getVisitedUrlNum(){
		return visitedUrl.size();
	}
	
	//
	/**
	 * 方法描述：判断未访问的URL队列是否为空
	 * @author yxm
	 */
	public synchronized boolean unVisitedUrlIsEmpty(){
		return unVisitedUrl.isEmpty();
	}
}
