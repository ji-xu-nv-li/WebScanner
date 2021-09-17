package com.wsbc.httpclient.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;














import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.wsbc.httpclient.entity.WsbcForm;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcParam;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.util.parser.HtmlParserToolByJsoup;

public class HttpClientServerThread {

//	private PoolingHttpClientConnectionManager cm = null;
//	private ThreadSafeClientConnManager cm = null;
	
	private CloseableHttpClient httpClient = null;
	
	/**
	 * 登录信息的表单
	 */
	private WsbcForm wsbcForm;
	
	/**
	 * 使用浏览器的会话
	 */
//	private Cookie jsessionId;
	
	public WsbcForm getWsbcForm() {
		return wsbcForm;
	}

	/**
	 * 方法描述：只有当登录成功时，才设置该属性
	 * @data 2016年5月12日  下午2:06:00
	 * @author yxm
	 */
	public void setWsbcForm(WsbcForm wsbcForm) {
		this.wsbcForm = wsbcForm;
	}

//	public Cookie getJsessionId() {
//		return jsessionId;
//	}
	public HttpClientServerThread(){
		httpClient = PoolingClientConnection.getClient();
	}

	public HttpClientServerThread(javax.servlet.http.Cookie jsessionId){
		if(jsessionId != null){
	//		this.jsessionId = jsessionId;
			BasicCookieStore cookieStore = new BasicCookieStore();
														  //返回cookie的名称
			BasicClientCookie temp = new BasicClientCookie(jsessionId.getName(),jsessionId.getValue());
			//设置域的coolie适用
			temp.setDomain(jsessionId.getDomain());
			//规定了注释
			temp.setComment(jsessionId.getComment());
	//		temp.setExpiryDate(jsessionId.g);
			//设定cookie的路径,如果不指定路径，cookie是相同的目录以及当前页面的所有子目录中的所有URL返回
			temp.setPath(jsessionId.getPath());
			//是否应该只发送加密连接
			temp.setSecure(jsessionId.getSecure());
			temp.setVersion(jsessionId.getVersion());
			//设置cookie过期之前多少时间，不设置cookie将持续只对当前会话，-1表示cookie将继续下去，直到浏览器关闭
	//		temp.setAttribute(ClientCookie.MAX_AGE_ATTR, Integer.toString(jsessionId.getMaxAge()));
			cookieStore.addCookie(temp);
			httpClient = PoolingClientConnection.getClient(cookieStore);
		}else{
			httpClient = PoolingClientConnection.getClient();
		}
	}
	
	/**
	 * 方法描述：开启获取多个路径对应的网页的任务
	 * @data 2016年4月29日  下午4:49:09
	 * @author yxm
	 */
	public List<FutureTask<WsbcHtml>> requestTask(List<WsbcUrl> wsbcUrls){
		List<FutureTask<WsbcHtml>> threads = new ArrayList<FutureTask<WsbcHtml>>();
		try {
			//创建获取页面的线程
			for(WsbcUrl wsbcUrl : wsbcUrls){
				Runnable runable = null;
				if("get".equals(wsbcUrl.getMethod())){
					runable = new GetThread(httpClient, wsbcUrl);
				}else{
					runable = new PostThread(httpClient, wsbcUrl);
				}
				FutureTask<WsbcHtml> futureTask = new FutureTask<WsbcHtml>(runable, null);
				new Thread(futureTask).start();
				threads.add(futureTask);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return threads;
	}
	
	/**
	 * 方法描述：开启获取路径对应的网页的任务
	 * @data 2016年4月29日  下午4:49:52
	 * @author yxm
	 */
	public FutureTask<WsbcHtml> requestTask(WsbcUrl wsbcUrl){
		try {
			System.out.println("httpClient："+httpClient);
			//创建获取页面的线程
			Callable<WsbcHtml> callable = null;
			if("get".equals(wsbcUrl.getMethod())){
				callable = new GetThread(httpClient, wsbcUrl);
			}else{
				callable = new PostThread(httpClient, wsbcUrl);
			}
			FutureTask<WsbcHtml> futureTask = new FutureTask<WsbcHtml>(callable);
			new Thread(futureTask).start();
			return futureTask;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 方法描述：开启表单提交的任务
	 * @data 2016年4月29日  下午4:49:52
	 * @author yxm
	 */
	public FutureTask<WsbcHtml> requestTask(WsbcForm wsbcForm){
		return requestTask(WsbcForm.translateWsbcForm(wsbcForm));
	}
	
	/**
	 * 方法描述：开启测试表单提交的任务
	 * @data 2016年4月29日  下午4:49:52
	 * @author yxm
	 */
	public FutureTask<WsbcHtml> requestTask(String action,String method,WsbcParam wsbcParam){
		WsbcForm wsbcForm = new WsbcForm();
		wsbcForm.setAction(action);
		wsbcForm.setMethod(method);
		wsbcForm.setParam(wsbcParam);
		return requestTask(wsbcForm);
	}
	
	/**
	 * 方法描述：获取任务里的网页信息，该方法会阻塞线程
	 * @data 2016年5月4日  下午4:11:56
	 * @author yxm
	 */
	public WsbcHtml getHtmlByTask(FutureTask<WsbcHtml> task){
		WsbcHtml wsbcHtml = null;
		try {
			wsbcHtml = task.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wsbcHtml;
	}
	
	/**
	 * 方法描述：获取多个路径对应的网页信息
	 * @data 2016年5月4日  下午4:09:30
	 * @author yxm
	 */
	public List<WsbcHtml> requestHtml(List<WsbcUrl> wsbcUrls){
		List<FutureTask<WsbcHtml>> tasks = requestTask(wsbcUrls);
		List<WsbcHtml> wsbcHtmls = new ArrayList<WsbcHtml>();
		for(FutureTask<WsbcHtml> task : tasks){
			WsbcHtml wsbcHtml = getHtmlByTask(task);
			wsbcHtmls.add(wsbcHtml);
		}
		return wsbcHtmls;
	}
	
	/**
	 * 方法描述：获取路径对应的网页信息
	 * @data 2016年4月29日  下午4:49:52
	 * @author yxm
	 */
	public WsbcHtml requestHtml(WsbcUrl wsbcUrl){
		FutureTask<WsbcHtml> task = requestTask(wsbcUrl);
		return getHtmlByTask(task);
	}
	
	/**
	 * 方法描述：获取表单提交后的网页信息
	 * @data 2016年4月29日  下午4:49:52
	 * @author yxm
	 */
	public WsbcHtml requestHtml(WsbcForm wsbcForm){
		FutureTask<WsbcHtml> task = requestTask(WsbcForm.translateWsbcForm(wsbcForm));
		return getHtmlByTask(task);
	}
	
	private void config(HttpRequestBase httpRequestBase){
		//配置请求超时
		RequestConfig requestConfig = RequestConfig.custom()
									  .setConnectionRequestTimeout(3000)
									  //设置连接超时时间
									  .setConnectTimeout(3000)
									  //等待数据超时时间
									  .setSocketTimeout(3000)
									  //设置不自动跳转
									  .setRedirectsEnabled(false).build();
		httpRequestBase.setConfig(requestConfig);
	}
	
	/**
	 * post请求线程
	 * @author yxm
	 */
	class PostThread implements Runnable,Callable<WsbcHtml>{
		private CloseableHttpClient httpClient;
		private HttpPost post;
		//访问的URL路径
		private WsbcUrl wsbcUrl;
		//返回的结果页面
		private WsbcHtml wsbcHtml;
		//标志位，用于保证只登录一次用户，防止登录失败时的重复登录而导致的死循环
		private int flag=1;
		
		public PostThread(CloseableHttpClient httpClient , WsbcUrl wsbcUrl){
			this.httpClient = httpClient;
			this.wsbcUrl = wsbcUrl;
		}
		
		public PostThread(CloseableHttpClient httpClient , String url , List<NameValuePair> params) {
			try {
				this.httpClient = httpClient;
				this.wsbcUrl = new WsbcUrl(url, params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			CloseableHttpResponse response = null;
			try {
				//1.生成 post 对象
				post = new HttpPost(wsbcUrl.getUrl());
				//配置超时
				config(post);
				//2.设置参数
				if(!wsbcUrl.isEmptyParam()){
					HttpEntity entity = new UrlEncodedFormEntity(wsbcUrl.getParam().getParams(),"utf-8");
					post.setEntity(entity);
				}
				//3.执行 HTTP POST请求
				response = httpClient.execute(post,HttpClientContext.create());
				//4.根据响应状态码获得响应页面
				wsbcHtml = HttpClientServer.getResponseHtml(response);
				wsbcHtml.setWsbcUrl(wsbcUrl);
				HttpClientServer.analyseStatusCode(wsbcHtml, response);
				if(response != null){
					response.close();
					response = null;
				}
				if(wsbcForm != null && wsbcHtml.getWsbcStatus().getStatusCode() == 302 && flag > 0 ){
					flag--;
					//重定向到登录页面
					Iterator<WsbcUrl> it = HtmlParserToolByJsoup.extracLinks(wsbcHtml, null).iterator();
					while(it.hasNext()){
						WsbcUrl tempWsbcUrl = it.next();
						WsbcHtml tempWsbcHtml = requestHtml(tempWsbcUrl);
						boolean temp = HtmlParserToolByJsoup.isLoginHtml(tempWsbcHtml, wsbcForm);
						if(temp){
							//先登录
							requestHtml(wsbcForm);
							//再发一次请求
							run();
						}
					}
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("网络请求失败！");
				wsbcHtml = HttpClientServer.getResponseHtml(response);
				wsbcHtml.setWsbcUrl(wsbcUrl);
			} finally{
				if(post != null){
					post.abort();
					post = null;
				}
			}
		}
		
		@Override
		public WsbcHtml call() throws Exception {
			// TODO Auto-generated method stub
			run();
			return wsbcHtml;
		}
	}
	
	/**
	 * get请求线程
	 * @author yxm
	 */
	class GetThread implements Runnable,Callable<WsbcHtml>{
		private CloseableHttpClient httpClient;
		private HttpGet get;
		//访问的URL路径
		private WsbcUrl wsbcUrl;
		//返回的结果页面
		private WsbcHtml result;
		//标志位，用于保证只登录一次用户，防止登录失败时的重复登录而导致的死循环
		private int flag=1;
		
		public GetThread(CloseableHttpClient httpClient , WsbcUrl wsbcUrl){
			this.httpClient = httpClient;
			this.wsbcUrl = wsbcUrl;
		}
		
		public GetThread(CloseableHttpClient httpClient , String url){
			this.httpClient = httpClient;
			try {
				this.wsbcUrl = new WsbcUrl(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			CloseableHttpResponse response = null;
			try {
				System.out.println("get方式请求");
				//对URL设置编码
				String url = new String(wsbcUrl.getUrl().getBytes(),"utf-8");
				get = new HttpGet(url);
				config(get);
				//设置不自动跳转
//				get.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
				//执行HTTP GET方式请求
				response = httpClient.execute(get,HttpClientContext.create());
				result = HttpClientServer.getResponseHtml(response);
				result.setWsbcUrl(wsbcUrl);
				HttpClientServer.analyseStatusCode(result, response);
				if(response != null){
					response.close();
					response = null;
				}
				if(wsbcForm != null && result.getWsbcStatus().getStatusCode() == 302 && flag > 0 ){
					flag--;
					//重定向到登录页面
					Iterator<WsbcUrl> it = HtmlParserToolByJsoup.extracLinks(result, null).iterator();
					while(it.hasNext()){
						WsbcUrl tempWsbcUrl = it.next();
						WsbcHtml wsbcHtml = requestHtml(tempWsbcUrl);
						boolean temp = HtmlParserToolByJsoup.isLoginHtml(wsbcHtml, wsbcForm);
						if(temp){
							//先登录
							requestHtml(wsbcForm);
							//再发一次请求
							run();
						}
					}
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//网络连接异常
//				e.printStackTrace();
				System.out.println("网络请求失败！");
				result = HttpClientServer.getResponseHtml(response);
				result.setWsbcUrl(wsbcUrl);
				
			}finally{
				if(get != null){
					get.abort();
					get = null;
				}
			}
		}
		
		@Override
		public WsbcHtml call() throws Exception {
			// TODO Auto-generated method stub
			run();
			return result;
		}
	}

	public void close(){
		if(httpClient != null){
			httpClient = null;
		}
	}
	
}
