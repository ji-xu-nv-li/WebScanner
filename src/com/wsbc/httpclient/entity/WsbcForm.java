package com.wsbc.httpclient.entity;

/**
 * 表单信息
 * @author yxm
 */
public class WsbcForm extends WsbcUrl{
	
	/**
	 * 表单跳转路径
	 */
	private String action;
	
	/**
	 * 表单命名
	 */
	private String name;
	
	/**
	 * 表单ID
	 */
	private String formId;
	
	public WsbcForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String id) {
		this.formId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 方法描述：将 表单信息 转换成 URL信息
	 * @data 2016年4月29日  下午5:09:51
	 * @author yxm
	 */
	public static WsbcUrl translateWsbcForm(WsbcForm wsbcForm){
		WsbcUrl wsbcUrl = null;
		try {
			if(wsbcForm.getMethod().equals("get")){
				if(wsbcForm.isEmptyParam()){
					wsbcUrl = new WsbcUrl(wsbcForm.getAction()+"?"+wsbcForm.getParam().getParameters());
				}else{
					wsbcUrl = new WsbcUrl(wsbcForm.getAction());
				}
			}else{
				wsbcUrl = new WsbcUrl(wsbcForm.getAction(), wsbcForm.getParam().getParams());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wsbcUrl;
	}
	
}
