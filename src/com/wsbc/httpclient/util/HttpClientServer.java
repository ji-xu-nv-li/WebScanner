package com.wsbc.httpclient.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.wsbc.httpclient.entity.WsbcForm;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcParam;
import com.wsbc.httpclient.entity.WsbcStatus;
import com.wsbc.httpclient.entity.WsbcUrl;


//GetMethod与PostMethod 共同父类 HttpClientBase

public class HttpClientServer {
	
	public static Log log = LogFactory.getLog(HttpClientServer.class);
	
	/**
	 * cookie 信息
	 */
	private List<Header> cookies = new ArrayList<Header>();
	
	private static HttpClientServer client;
	
	/**
	 * 方法描述：单例构造浏览器
	 * 作者： yxm
	 * 创建时间： 2018年7月29日  下午3:40:03
	 * @return
	 * @throws
	 */
	public static HttpClientServer getInstance() {
		if (client == null) {
			synchronized (HttpClientServer.class) {
				if ( client == null) {
					client = new HttpClientServer();
				}
			}
		}
		return client;
	}
	/**
	 * 方法描述：get方式访问网页 返回响应的HTML页面
	 * 创建时间：2016年2月17日  下午12:45:56
	 * 作者：yxm
	 */
	public WsbcHtml get(String url){
		if (url == null || "".equals(url)) {
			throw new IllegalArgumentException("get请求url值无效");
		}
		HttpClient httpClient = null;
		HttpGet get = null;
		WsbcHtml html=null;
		try {
			//1、创建client
			httpClient = new DefaultHttpClient();
			//设置URL编码
			url = new String(url.getBytes(),"utf-8");
			//2、创建get  
			get = new HttpGet(url);
//	        System.out.println("executing request :" + get.getURI());  
			//设置Cookie
			for(Header cookie : cookies){
				get.setHeader(cookie);
			}
			//设置不自动跳转
			get.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
			//3、 执行get请求.   
	        HttpResponse response=httpClient.execute(get);
			//4. 根据响应状态码获得响应页面
	        html = getResponseHtml(response);
			html.setWsbcUrl(new WsbcUrl(url));
			analyseStatusCode(html,response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//3.释放资源
			close(httpClient, get);
		}
        return html;
	}
	
	/**
	 * 方法描述：post方式访问网页，可携带相应的参数  返回响应的HTML页面
	 * 创建时间：2016年2月17日  下午12:46:23
	 * 作者：yxm
	 */
	public WsbcHtml post(String url,List<NameValuePair> params){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = null;
		WsbcHtml html=null;
		try {
			//1、创建client
			httpClient = new DefaultHttpClient();
			//2.生成 post 对象
			post = new HttpPost(url);
			HttpResponse response = null;
			//3.设置参数
			HttpEntity entity = new UrlEncodedFormEntity(params,"utf-8");
			//设置Cookie
			for(Header cookie : cookies){
				post.setHeader(cookie);
			}
			post.setEntity(entity);
			//4.执行 HTTP POST请求
			response = httpClient.execute(post);
			//5.根据响应状态码获得响应页面
			html = getResponseHtml(response);
			html.setWsbcUrl(new WsbcUrl(url, params));
			analyseStatusCode(html,response);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//3.释放资源
			close(httpClient, post);
		}
		return html;
	}
	
	/**
	 * 方法描述：wsbcUrl 访问
	 * 创建时间：2016年4月5日  下午4:38:41
	 * 作者：yxm
	 */
	public WsbcHtml request(WsbcUrl wsbcUrl){
		WsbcHtml html=null;
		String url = wsbcUrl.getUrl();
		if("get".equals(wsbcUrl.getMethod())){
			html = get(url);
		}else{
			if(wsbcUrl.isEmptyParam()){
				html = post(url, new ArrayList<NameValuePair>());
			}else{
				html = post(url, wsbcUrl.getParam().getParams());
			}
		}
		html.setWsbcUrl(wsbcUrl);
		return html;
	}
	
	/**
	 * 方法描述：表单方式访问
	 * 创建时间：2016年4月5日  下午4:39:56
	 * 作者：yxm
	 */
	public WsbcHtml request(String action,String method,WsbcParam wsbcParam){
		WsbcUrl wsbcUrl = null;
		try {
			if(method.equals("get")){
				if(wsbcParam != null&&wsbcParam.isEmptyParam()){
					wsbcUrl = new WsbcUrl(action+"?"+wsbcParam.getParameters());
				}else{
					wsbcUrl = new WsbcUrl(action);
				}
			}else{
				wsbcUrl = new WsbcUrl(action, wsbcParam.getParams());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request(wsbcUrl);
	}
	
	/**
	 * 方法描述：
	 * @data 2016年4月29日  上午11:14:40
	 * @author yxm
	 */
	public WsbcHtml request(WsbcForm wsbcForm){
		return request(wsbcForm.getAction(), wsbcForm.getMethod(), wsbcForm.getParam());
	}
	
	
//	/**
//	 * 方法描述：发送GET方式请求，返回response响应实体
//	 * 创建时间：2016年2月19日  下午11:18:29
//	 * 作者：yxm
//	 */
//	private static HttpResponse sendGetRequest(String url,HttpClient httpClient,HttpGet get) throws ClientProtocolException, IOException{
//		//1、创建client
//		httpClient = new DefaultHttpClient();
//		url = new String(url.getBytes(),"utf-8");
//		//2、创建get  
//		get = new HttpGet(url);
////        System.out.println("executing request :" + get.getURI());  
//		get.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
//		
//        HttpResponse response=null;
//		//3、 执行get请求.    
//		response = httpClient.execute(get);
//        return response;
//            
//	}
	
	/**
	 * 方法描述：发送POST请求，返回response响应实体
	 * 创建时间：2016年2月19日  下午11:25:10
	 * 作者：yxm
	 */
//	private static HttpResponse sendPostRequest(String url,List<NameValuePair> params,HttpClient httpClient,HttpPost post) throws ClientProtocolException, IOException{
//		//1.生成HttpClient
//		httpClient = new DefaultHttpClient();
//		
//		//同时启动重定向处理，get方式默认开启
//		//httpClient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, true);
//		
//		//2.生成 post 对象
//		post = new HttpPost(url);
//		HttpResponse response = null;
//		//3.设置参数
//		HttpEntity entity = new UrlEncodedFormEntity(params,"utf-8");
//		post.setEntity(entity);
//		//4.执行 HTTP POST请求
//		response = httpClient.execute(post);
//		return response;
//	}
	
	/**
	 * 方法描述：获得响应的页面文档
	 * 创建时间：2016年2月19日  下午11:39:13
	 * 作者：yxm
	 */
	public static WsbcHtml getResponseHtml(HttpResponse response){
		WsbcHtml html = new WsbcHtml();
		WsbcStatus wsbcStatus = new WsbcStatus();
		//设置时间
		html.setResponseDate(new Timestamp(new Date().getTime()));
		if( response == null){
			//没有响应，说明请求失败
			html.setCharset("utf-8");
			wsbcStatus.setId(404);
			wsbcStatus.setStatusCode(404);
			wsbcStatus.setEnglish("Not Found");
			html.setWsbcStatus(wsbcStatus);
			return html;
		}
		//获得响应内容
		HttpEntity entity = response.getEntity();
		//获得编码方式
		String charset = getCharSet(response);
		html.setCharset(charset);
		//获得文本类型
		Header contentTypeHeader = response.getFirstHeader("Content-Type");
		String contentType = contentTypeHeader == null?"":contentTypeHeader.getValue();
		html.setContentType(contentType);
		
		//设置状态码
		int statusCode = response.getStatusLine().getStatusCode();
		wsbcStatus.setStatusCode(statusCode);
		wsbcStatus.setId(statusCode);
		wsbcStatus.setEnglish(response.getStatusLine().getReasonPhrase());
		html.setWsbcStatus(wsbcStatus);
		//设置服务器
		//多个server，获取第一个
		Header serverHeader = response.getFirstHeader("Server");
		html.setServer(serverHeader.getValue());
		try {
			//显示头信息
//			showHeaders(response.getAllHeaders());
			showHeaders(response.headerIterator());
			//获取文本信息
			html.setDocument(EntityUtils.toString(entity,charset));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
	}
	
	/**
	 * 方法描述：分析状态码，设置相关属性，方便其他地方获取数据<br/>
	 * 302 重定向的路径保存在 wsbcHtml.document属性中<br/>
	 * 创建时间：2016年2月18日  下午8:08:14
	 * 作者：yxm
	 */
	public static void analyseStatusCode(WsbcHtml wsbcHtml,HttpResponse response){
		System.out.println(response.getStatusLine().getStatusCode());
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
			//302
			Header header = response.getFirstHeader("location");
			System.out.println("重定向："+header.getValue());
			String html = "重定向至：<a href=\""+header.getValue() +"\">"+header.getValue()+"</a>";
			wsbcHtml.setDocument(html);
		}
	}
	
	/**
	 * 方法描述：释放请求连接
	 * 创建时间：2016年2月20日  下午1:37:20
	 * 作者：yxm
	 */
	private static void close(HttpRequestBase request){
		if(request != null)
			request.abort();
	}
	
	/**
	 * 方法描述：释放客户端连接
	 * 创建时间：2016年2月20日  下午1:39:19
	 * 作者：yxm
	 */
	private static void close(HttpClient httpClient){
		if(httpClient != null){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * 方法描述：释放资源
	 * 创建时间：2016年2月20日  下午1:40:41
	 * 作者：yxm
	 */
	private static void close(HttpClient httpClient,HttpRequestBase request){
		close(request);
		close(httpClient);
	}
	
	/**
	 * 方法描述：显示所有头部信息
	 * 创建时间：2016年2月20日  下午10:47:55
	 * 作者：yxm
	 */
	public static void showHeaders(Header[] headers){
		for(Header h:headers){
			System.out.println(h.getName()+"："+h.getValue());
		}
	}
	
	public static void showHeaders(HeaderIterator iterator){
		while (iterator.hasNext()){
			Header h = (Header) iterator.next();
			System.out.println(h.getName()+"："+h.getValue());
		}
	}
	
	/**
	 * 方法描述：获得响应内容的字符编码
	 * 创建时间：2016年2月20日  下午10:56:37
	 * 作者：yxm
	 */
	private static String getCharSet(HttpResponse response){
		String charset = null;
		if(response == null){
			return "utf-8";
		}
		//System.out.println("char:"+response.getParams().getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET));
		
		Header header = response.getEntity().getContentEncoding();
		if(header != null){
			charset = header.getValue();
			return charset;
		}
		charset = EntityUtils.getContentCharSet(response.getEntity());
		if(charset != null){
			return charset;
		}
		header = response.getFirstHeader("Content-Type");
		if(header != null){
			final String key = "charset=";
			String contentType = header.getValue().toLowerCase();
			if(contentType.contains(key)){
				charset = contentType.substring(contentType.indexOf(key)+key.length(), contentType.length());
				return charset;
			}
		}
		if(charset == null){
			charset = "utf-8";
		}
		return charset;
	}

	public void setCookies(List<Header> cookies) {
		this.cookies = cookies;
	}
}
