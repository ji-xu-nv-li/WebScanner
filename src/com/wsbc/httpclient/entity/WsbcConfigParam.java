package com.wsbc.httpclient.entity;

public class WsbcConfigParam {

	private String url;
	
	private String method;
	
	private String parameters;
	
	private String[] leaks;

	public WsbcConfigParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String[] getLeaks() {
		return leaks;
	}

	public void setLeaks(String[] leaks) {
		this.leaks = leaks;
	}
	
	
}
