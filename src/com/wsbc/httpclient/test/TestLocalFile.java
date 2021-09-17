package com.wsbc.httpclient.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.nodes.Document;

import com.wsbc.httpclient.entity.WsbcForm;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.util.HttpClientServer;
import com.wsbc.util.html.WsbcFormUtil;
import com.wsbc.util.html.WsbcParamUtil;
import com.wsbc.util.parser.HtmlParserToolByJsoup;

/**
 * 解析本地文件的form内容
 * @author yxm
 *
 */
public class TestLocalFile {
	
	public static Log log = LogFactory.getLog(TestLocalFile.class);
	
	public static void main(String[] args) {
		HttpClientServer client = new HttpClientServer();
		WsbcUrl wsbcUrl = new WsbcUrl("http://localhost:8080/");
		Document document = HtmlParserToolByJsoup.parserHtmlByFile("E:/test.html", "Unicode");
		List<WsbcForm> forms = HtmlParserToolByJsoup.parserFormInDocument(wsbcUrl, document);
		for (WsbcForm form : forms) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", "030030");
			WsbcParamUtil.changeParams(form.getParam(), map);
			log.info(WsbcFormUtil.toStringForm(form));
			log.info(client.request(form).getDocument());
		}
	}
}
