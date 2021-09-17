package com.wsbc.util.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wsbc.httpclient.entity.WsbcForm;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcParam;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.util.HttpClientServer;
import com.wsbc.util.crawler.LinkFilter;
import com.wsbc.util.html.WsbcParamUtil;

public class HtmlParserToolByJsoup {
	public static Log log = LogFactory.getLog(HtmlParserToolByJsoup.class);
	//通过URL解析网页
	//返回值为NULL时，表示解析失败
	public static Document parserHtmlByURL(String url){
		Document document = null;
		try {
			//从一个URL加载一个Document对象
			document = Jsoup.connect(url).get();
			//显示html
			//System.out.println(document.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文档解析失败！");
		}
		return document;
	}
	
	//通过网页字符串解析网页
	public static Document parserHtmlByString(String html){
		//空的不用解析
		if(html == null)
			return null;
		//将输入的HTML解析为一个新的文档 
		//返回一个结构合理的文档，其中包含(至少) 一个head和一个body元素
		Document document = Jsoup.parse(html);
//		System.out.println(document.html());
		return document;
	}
	
	//通过本地文件解析网页
	public static Document parserHtmlByFile(String filePath,String charsetName){
		File file = new File(filePath);
		Document document = null;
		try {
			document = Jsoup.parse(file, charsetName);
//			System.out.println(document.html());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文档解析失败！");
		}
		return document;
	}
	
	public static List<WsbcForm> parserFormInURL(WsbcUrl wsbcUrl) {
		HttpClientServer client = new HttpClientServer();
		return parserFormInDocument(wsbcUrl, client.request(wsbcUrl).getDocument());
	}
	
	/**
	 * 方法描述：解析文档中表单（文档中可能有多个表单）
	 * 创建时间：2016年2月14日  下午8:25:59
	 * 作者：yxm
	 */
	public static List<WsbcForm> parserFormInDocument(WsbcUrl wsbcUrl , Document document){
		List<WsbcForm> forms = new ArrayList<WsbcForm>();
		//根据标签选择器获得表单form标签
		if(document == null){
			return forms;
		}
		Elements elements = document.getElementsByTag("form");
		log.info("表单标签：" + elements);
		for(Element element:elements){
			//根据选择器获取标签信息
			//只获取input标签中含有name属性的
			//System.out.println(document.absUrl(null));
			forms.add(parserForm(wsbcUrl,element));
		}
		return forms;
	}
	
	public static List<WsbcForm> parserFormInDocument(String url, Document document){
		WsbcUrl wsbcUrl = null;
		try {
			wsbcUrl = new WsbcUrl(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parserFormInDocument(wsbcUrl, document);
	}
	/**
	 * 方法描述：解析一个表单，
	 * 创建时间：2016年2月14日  下午10:06:17
	 * 作者：yxm
	 */
	private static WsbcForm parserForm(WsbcUrl wsbcUrl,Element form){
		WsbcForm f = new WsbcForm();
		Elements eles = form.select("input[name]");
		
		//保存表单内的所有参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(Element ele:eles){
			//获得name属性的值
			String name = ele.attr("name");
			//获得value属性的值
			String value = ele.attr("value");
			//所有没有赋值的默认为1
			if(value == null || value.equals("")){
				value="1";
			}
			NameValuePair param = new BasicNameValuePair(name, value);
			params.add(param);
		}
		
		//获得表单action中跳转的路径
		String action = "";
		if(form.baseUri() == null || form.baseUri().equals("")){
			if(wsbcUrl != null){
				String path = wsbcUrl.getPath();
				if(path != null){
					int index = path.lastIndexOf("/");
					if(index<0){
						action = wsbcUrl.getProtocol()+"://"
								+wsbcUrl.getHost()
								+wsbcUrl.getPath().substring(0, index+1)
								+form.attr("action");
					}
				}
			}
			
		}else{
			action = form.attr("abs:action");
		}
		
		log.info("表单跳转路径：" + action + "\n表单参数：" + 
										WsbcParamUtil.paramListToString(params));
		// 组装表单对象数据
		f.setAction(action);
		f.setFormId(form.attr("id"));
		f.setMethod(form.attr("method"));
		f.setName(form.attr("name"));
		f.setParam(new WsbcParam(params));
		return f;
	}
	
	/**
	 * 方法描述：获得当前页面的所有链接
	 * 创建时间：2016年3月26日  下午2:00:28
	 * 作者：yxm
	 */
	public static Set<WsbcUrl> extracLinks(WsbcHtml html,LinkFilter filter) {
		Set<WsbcUrl> links = new HashSet<WsbcUrl>();
		
		//1.获取文档
		Document document = html.getDocument();
		if(document == null){
			return links;
		}
		//2.获得文档内的所有  a 标签链接
		Elements aElements = document.select("a[href]");
		for(Element e: aElements){
			String href = e.attr("abs:href");
			WsbcUrl tempUrl = null;
			try {
				tempUrl = new WsbcUrl(href);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("href:"+href+"不符合格式" );
				continue;
			}
			if(filter == null){
				links.add(tempUrl);
			}else
			if(filter.accept(tempUrl.getUrl())){
				links.add(tempUrl);
			}
		}
		
		//3.获得所有 frame 标签的链接
		Elements frames = document.select("frame[src]");
		for(Element frame: frames){
			String src = frame.attr("abs:src");
			System.out.println("frame src:"+src);
			WsbcUrl tempUrl=null;
			try {
				tempUrl = new WsbcUrl(src);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
			}
			if(filter == null){
				links.add(tempUrl);
			}else
			if(filter.accept(tempUrl.getUrl())){
				links.add(tempUrl);
			}
		}
		return links;
	}
	
	/**
	 * 方法描述：只获取能够爬取到的url
	 * 创建时间：2016年3月30日  下午5:19:54
	 * 作者：yxm
	 */
	public static Set<WsbcUrl> extracLinks(String url,LinkFilter filter){
		HttpClientServer client = new HttpClientServer();
		WsbcHtml html = client.get(url);
		return extracLinks(html, filter);
	}
	
	public static String getBody(Document document){
		String body = "";
		if(document == null){
			return body;
		}
		Elements elements = document.getElementsByTag("body");
		for(Element e : elements){
			body = e.html();
		}
		return body;
	}
	
	/**
	 * 方法描述：判断当前页面是否为登录页面
	 * @param wsbcHtml  被判断的页面
	 * @param wsbcForm  登录表单的信息
	 * @data 2016年5月18日  下午3:27:38
	 * @author yxm
	 */
	public static boolean isLoginHtml(WsbcHtml wsbcHtml,WsbcForm wsbcForm){
		if(wsbcForm == null || wsbcForm.isEmptyParam()){
			//登录表单信息不正确，判断结果为不是登录页面
			return false;
		}
		List<WsbcForm> list = HtmlParserToolByJsoup.parserFormInDocument(wsbcHtml.getWsbcUrl(),wsbcHtml.getDocument());
		boolean result = false;
		if(list.size()>0){
			WsbcForm tempForm = list.get(0);
			List<NameValuePair> params = wsbcForm.getParam().getParams();
			List<NameValuePair> tempParams = tempForm.getParam().getParams();
			int size = tempParams.size();
			if(size == params.size()){
				int i=0;
				for( ; i<size ; i++){
					if(!tempParams.get(i).getName().equals(params.get(i).getName())){
						//表单元素的name 属性不一致，不是登录页面
						break;
					}
				}
				if( i == size){
					//所有属性都一致，是登录页面
					result = true;
				}
			}
			//表单元素数量不一致，不是登录页面
		}
		//不存在表单，不是登录页面
		return result;
	}
}
