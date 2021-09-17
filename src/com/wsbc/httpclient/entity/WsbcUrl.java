package com.wsbc.httpclient.entity;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;

public class WsbcUrl implements Comparable<WsbcUrl>{
	public static Log log = LogFactory.getLog(WsbcUrl.class);
	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 请求提交方式
	 */
	private String method;
	
	/**
	 * 远程主机
	 */
	private String host;
	
	/**
	 * 路径
	 */
	private String path;
	
	/**
	 * 传输协议
	 */
	private String protocol;
	
	/**
	 * URL :www.baidu.com?name=yang
	 */
	private String url;
	
	/**
	 * 优先级，从 1 开始，紧急优先的设置为 0 ，也可设置为与上一个访问的相同，强调优先
	 */
	private int priority;
	
	/**
	 * 扫描深度：当前URL位于整体扫描的位置
	 */
	private int depth;
	
	/**
	 * 请求参数
	 */
	private WsbcParam param;

	public WsbcUrl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get 方式请求的URL
	 * @throws Exception 
	 */
	public WsbcUrl(String url) {
		this.method = "get";
		this.url = url;
		initByUrl();
		isEmptyParam();
	}
	
	/**
	 * post 方式请求的URL
	 * @throws Exception 
	 */
	public WsbcUrl(String url,List<NameValuePair> params) {
		this.method = "post";
		this.url = url;
		initByUrl();
		this.param = new WsbcParam(params);
		isEmptyParam();
	}
	
	/**
	 * post 方式请求的URL
	 * @throws Exception 
	 */
	public WsbcUrl(String url,String parameters) {
		this.method = "post";
		this.url = url;
		initByUrl();
		this.param = new WsbcParam(parameters);
		isEmptyParam();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		if(method == null || method.equals("")){
			//默认为get方式提交
			method = "get";
		}
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public WsbcParam getParam() {
		return param;
	}

	public void setParam(WsbcParam param) {
		if(param != null && param.isEmptyParam()){
			param = null;
		}
		this.param = param;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * 方法描述：获取URL <br/> http://localhost:8080?name=yang<br/>
	 * 创建时间：2016年3月27日  上午8:51:43
	 * 作者：yxm
	 */
	public String getUrl(){
		if(url == null || url.equals("")){
			url = protocol + "://"+ host + (path==null?"":path);
			if("get".equals(method) && param != null){
				String p = param.getParameters();
				if( p!= null && !p.equals("")){
					url += "?" + p;
				}
			}
		}
		return url;
	}
	/**
	 *
	 * 方法描述：获取不含协议的URL  www.baidu.com?name=yang
	 * @data 2016年4月29日  下午2:25:12
	 * @author yxm
	 */
	public String getNotProtocolUrl(){
		if(url == null || url.equals("")){
			getUrl();
		}
		String temp = url.split("://")[1];
		return temp;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	/**
	 * 方法描述：根据url初始化其他信息
	 * 创建时间：2016年3月27日  上午10:25:44
	 * 作者：yxm
	 */
	public void initByUrl() {
		// (http|ftp|https)://(\\w)
		System.out.println("initByUrl:"+ url);
		if(url != null && !url.equals("")){
			//get : http://localhost:8080/taoshu/book?method=details&id=12
			
			//temp[0]:http
			//temp[1]:localhost:8080/taoshu/book?method=details&id=12
			String[] temp = url.split("://");
			this.protocol = temp[0];
			int index = temp[1].indexOf("/");
			if(index == -1){
				this.host = temp[1];
				return;
			}
			//0 - index : localhost:8080
			this.host = temp[1].substring(0, index);
			//        t : /taoshu/book?method=details&id=12
			String t = temp[1].substring(index, temp[1].length());
			if( "post".equals(method)){
				this.path = t;
			}else{
				//0 - index : taoshu/book
				//index+1 - length : method=details&id=12
				index = t.indexOf("?");
				if( index == -1){
					this.path = t;
				}else{
					this.path = t.substring(0, index);
					this.param = new WsbcParam(t.substring(index+1,t.length()));
				}
			}
		}else{
			throw new RuntimeException("url路径错误");
		}
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.url.hashCode()+this.method.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if( obj == null)
			return false;
		if(! (obj instanceof WsbcUrl))
			return false;
		WsbcUrl wsbcUrl = (WsbcUrl)obj;
		if(wsbcUrl.getUrl().equals(this.url) && wsbcUrl.getMethod().equals(this.method))
			return true;
		
		return false;
	}

	//返回的结果小，说明优先级高
	@Override
	public int compareTo(WsbcUrl wsbcUrl) {
		// TODO Auto-generated method stub
//		int temp = this.priority - wsbcUrl.getPriority();
//		if(temp > 0){
//			return 1;
//		}
//		if(temp == 0){
//			return 0;
//		}
//		return -1;
		return this.priority - wsbcUrl.getPriority();
	}
	
//	/**
//	 * 方法描述：检查URL是否符合格式
//	 * @data 2016年4月11日  上午11:16:11
//	 * @author yxm
//	 * @return true 合格      false 不合格
//	 */
//	public static boolean checkedUrl(String url){
//		if( url == null)
//			return false;
//		//String regex = "^(http|ftp|https)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?$";
//		return url.matches("^http[s]?://([\\w-]+.)$");
//	}
	
	/**
	 * 方法描述：判断当前URL的参数是否为空的，为空时，设置wsbcParam = null;
	 * @data 2016年4月11日  下午10:48:15
	 * @author yxm
	 */
	public boolean isEmptyParam(){
		if(this.param == null || param.isEmptyParam()){
			this.param = null;
			return true;
		}
		return false;
	}
	
	public String getURI() {
		return protocol + "://" + host;
	}
}
