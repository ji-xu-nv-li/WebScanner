package com.wsbc.httpclient.entity;

import java.sql.Timestamp;

import org.jsoup.nodes.Document;

import com.wsbc.util.parser.HtmlParserToolByJsoup;


public class WsbcHtml {
	
	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 请求的URL
	 */
	private WsbcUrl wsbcUrl;
	
	private WsbcGroup wsbcGroup;
	
	/**
	 * 文本类型，如：text/html，application/pdf
	 */
	private String contentType;
	
	/**
	 * 字符编码
	 */
	private String charset;
	
	/**
	 * 状态码
	 */
	private WsbcStatus wsbcStatus;
	
	/**
	 * 服务器响应时间
	 */
	private Timestamp responseDate;
	
	/**
	 * 服务器
	 */
	private String server;
	
	/**
	 * 文档存放的路径<br/>
	 * 可以作为临时存储 重定向的路径信息（原因：重定向是不保存页面信息到本地的）<br/>
	 * 使用后必须置空
	 */
	private String documentPath;
	
	/**
	 * 页面在数据库的状态
	 * 0、临时保存 
	 * 1、永久保存 
	 * 2、已删除，但还保留在数据库中
	 */
	private Integer flag = 0;
	
	/**
	 * 保存当前页面是否存在漏洞的集合
	 */
	private WsbcLeaks wsbcLeaks;
	
	/**
	 * HTML文本
	 */
	private Document document;
	
	/**
	 * HTTP 的响应头
	 */
//	@Transient
//	private Header[] headers;
	
//	public Header[] getHeaders() {
//		return headers;
//	}
//
//	public void setHeaders(Header[] headers) {
//		this.headers = headers;
//	}

	/**
	 * 方法描述：将字符串页面转化为Document
	 * 创建时间：2016年2月21日  下午7:47:25
	 * 作者：yxm
	 */
	public void setDocument(String document){
		this.document = HtmlParserToolByJsoup.parserHtmlByString(document);
	}
	
	public WsbcHtml() {
		super();
		// TODO Auto-generated constructor stub
		this.flag = 0;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public WsbcStatus getWsbcStatus() {
		return wsbcStatus;
	}

	public void setWsbcStatus(WsbcStatus wsbcStatus) {
		this.wsbcStatus = wsbcStatus;
	}

	public Timestamp getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Timestamp responseDate) {
		this.responseDate = responseDate;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public WsbcUrl getWsbcUrl() {
		return wsbcUrl;
	}

	public void setWsbcUrl(WsbcUrl wsbcUrl) {
		this.wsbcUrl = wsbcUrl;
	}

	public WsbcGroup getWsbcGroup() {
		return wsbcGroup;
	}

	public void setWsbcGroup(WsbcGroup wsbcGroup) {
		this.wsbcGroup = wsbcGroup;
	}

	/**
	 * 文档存放的路径<br/>
	 */
	public String getDocumentPath() {
		return documentPath;
	}

	/**
	 * 文档存放的路径<br/>
	 */
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public Document getDocument() {
		return document;
	}

//	public void setDocument(Document document) {
//		this.document = document;
//	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public WsbcLeaks getWsbcLeaks() {
		return wsbcLeaks;
	}

	public void setWsbcLeaks(WsbcLeaks wsbcLeaks) {
		this.wsbcLeaks = wsbcLeaks;
	}
	
	/**
	 * 方法描述：判断当前页面是否有漏洞，如果没有，则设置 wsbcLeaks = null
	 * @data 2016年4月11日  下午11:33:34
	 * @author yxm
	 */
	public boolean isEmptyLeaks(){
		if( wsbcLeaks == null || wsbcLeaks.isEmptyLeaks()){
			this.wsbcLeaks = null;
			return true;
		}
		return false;
	}

}
