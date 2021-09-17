package com.wsbc.httpclient.entity;

import java.util.List;

import org.apache.http.NameValuePair;

import com.wsbc.util.file.WsbcFileUtil;
import com.wsbc.util.html.WsbcParamUtil;


/**
 * 说明：对于从数据库获取的参数，需要手动调用init()方法初始化其他信息
 * @author yxm
 *
 */
public class WsbcParam {

	/**
	 * 编号
	 */
	private Integer id;
	
	/**
	 * 参数，长度超过100时，存放到文件中
	 */
	private String parameters;
	
	/**
	 * 参数存放的文件路径
	 * 网页参数：url+_param.txt
	 * 表单参数：action + _param.txt
	 */
	private String parametersPath;

	public WsbcParam() {
		
	}
	
	/**
	 * 方法描述：从数据库获取的参数，需要手动调用init()方法初始化其他信息
	 * 创建时间：2016年3月26日  下午1:36:25
	 * 作者：yxm
	 */
	public void init(){
		getParamsByFile();
	}
	
	/**
	 * 数组格式的参数构造方法
	 */
	public WsbcParam(List<NameValuePair> params){
		this.parameters = WsbcParamUtil.paramListToString(params);
	}
	
	/**
	 * 字符串格式的参数构造方法
	 */
	public WsbcParam(String parameters){
		this.parameters = parameters;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getParametersPath() {
		return this.parametersPath;
	}

	public void setParametersPath(String parametersPath) {
		this.parametersPath = parametersPath;
	}

	/**
	 * 生成一个新的数组，不会影响原来的参数
	 */
	public List<NameValuePair> getParams(){
		return WsbcParamUtil.paramStringToList(parameters);
	}
	
	/**
	 * 方法描述：从文件中获取参数
	 * 创建时间：2016年3月26日  上午10:24:51
	 * 作者：yxm
	 */
	private void getParamsByFile(){
		if(parameters == null || parameters.equals("")){
			if(parametersPath != null && !parametersPath.equals("")){
				parameters = WsbcFileUtil.readFile(parametersPath);
			}
		}
	}
	
	/**
	 * 方法描述：判断当前参数是否为空
	 * @data 2016年4月11日  下午10:44:07
	 * @author yxm
	 */
	public boolean isEmptyParam(){
		if(id != null){
			//数据库查询时，只有ID是有值的
			return false;
		}
		if( parameters == null || parameters.equals("")){
			if( parametersPath == null || parametersPath.equals("")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 方法描述：判断参数长度是否超过 100
	 * @data 2016年4月11日  下午11:12:31
	 * @author yxm
	 */
	public boolean isToLength(){
		if(parameters != null && parameters.length()>100){
			return true;
		}
		return false;
	}
	
}