package com.wsbc.httpclient.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.test.brower.MediatorBrower;
import com.wsbc.httpclient.util.HttpClientServer;

/**
 * 处理要访问的网站
 */
@SuppressWarnings("serial")
public class UrlServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = (String)request.getParameter("url");
		WsbcUrl wsbcUrl = new WsbcUrl(url);
		WsbcHtml html = HttpClientServer.getInstance().request(wsbcUrl);
		request.setAttribute("html", MediatorBrower.changUrl(html.getDocument().html(), wsbcUrl.getURI(), MediatorBrower.SYS_URL));
		request.getRequestDispatcher("showhtml.jsp").forward(request, response);
	}
}
