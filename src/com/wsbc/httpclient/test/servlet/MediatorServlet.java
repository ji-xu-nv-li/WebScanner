package com.wsbc.httpclient.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class MediatorServlet extends HttpServlet {
	
	public static Log log = LogFactory.getLog(MediatorServlet.class);
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		log.info("servlet启动了");
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
		StringBuffer url = request.getRequestURL();
		log.info("请求的URL路径：" + url );
//		WsbcUrl wsbcUrl = new WsbcUrl();
		request.getRequestDispatcher("index.jsp");
	}
	
}
