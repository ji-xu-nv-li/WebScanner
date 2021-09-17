package com.wsbc.constant;

public class Name {
	public static String TEST = "http://localhost:8080/taoshu";
	public static String URL = "http://localhost:8080/taoshu/book?method=details&id=12";
	
	public static String TAO_SHU_LOGIN = "http://localhost:8080/taoshu/page/login/login.jsp";
	public static String TAO_SHU_BOOK_DETAILS = "http://localhost:8080/taoshu/book?method=details&id=12";
	public static String BAI_DU = "http://www.baidu.com";
	public static String FILTER = "http://www.baidu.com";
	
	
	/**
	 * 在线用户
	 */
	public static final String ONLINE_USER = "online_wsbcUser";
	
	/**
	 * 本地文件保存的起始位置
	 */
	public static final String LOCAL_URL = "";
	
	/**
	 * 一组标志
	 */
	public static final String GROUP_TAG = "group";
	
	/**
	 * 每个用户的最多系统基本信息配置为 3 
	 */
	public static final Integer INIT_PARAM_MAX_SIZE = 3;
	
	/**
	 * 存放到值栈中的 JSON 属性命名
	 */
	public static final String JSON = "json";
	
	/**
	 * session中保留登录表单的信息名称
	 */
	public static final String WSBC_FORM_LOGIN = "wsbcFormLogin";
	
	/**
	 * 扫描网站 用户的Cookie信息
	 */
	public static final String COOKIES = "cookies";
	
	/**
	 * 保留扫描客户端的信息
	 */
	public static final String HTTP_CLIENT_SERVER = "httpClientServer";
}
