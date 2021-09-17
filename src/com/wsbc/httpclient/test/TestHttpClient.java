package com.wsbc.httpclient.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.util.HttpClientServer;
import com.wsbc.util.file.WsbcFileUtil;

/**
 * 根据URL解析表单内容
 * @author yxm
 *
 */
public class TestHttpClient {
	public static Log log = LogFactory.getLog(TestHttpClient.class);
	
	public static void main(String[] args) {
		WsbcUrl wsbcUrl = new WsbcUrl("http://localhost:8080/");
		HttpClientServer client = new HttpClientServer();
		WsbcHtml html = client.request(wsbcUrl);
		if (html == null) {
			log.error("网页信息获取失败!");
			return;
		}
		log.info("网页路径：" + html.getWsbcUrl().getUrl());
		log.info("网页内容：" + html.getDocument().html());
		WsbcFileUtil.writeFile(html.getDocument().html(), "E:/wsbcTest.txt");
		
	}
}
