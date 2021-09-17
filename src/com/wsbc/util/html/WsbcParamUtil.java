package com.wsbc.util.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.wsbc.httpclient.entity.WsbcParam;

public class WsbcParamUtil {

	/**
	 * 方法描述：将字符串格式的参数转换为数组格式参数
	 * 创建时间：2016年3月26日  上午10:21:34
	 * 作者：yxm
	 */
	public static List<NameValuePair> paramStringToList(String parameters){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(parameters != null && !"".equals(parameters)){
			String[] ps = parameters.split("&");
			for(String p : ps){
				//考虑到参数中可能带有 = 
				int temp = p.indexOf("=");
				String pname = p.substring(0, temp);
				String pvalue = p.substring(temp+1);
				NameValuePair value = new BasicNameValuePair(pname,pvalue);
				params.add(value);
			}
		}
		return params;
	}
	
	/**
	 * 方法描述：将数组参数 转换为 字符串参数
	 * 创建时间：2016年3月26日  下午12:29:00
	 * 作者：yxm
	 */
	public static String paramListToString(List<NameValuePair> params){
		String parameters = null;
		if(params != null && params.size()!=0){
			StringBuilder str = new StringBuilder();
			for(NameValuePair value : params){
				str.append(value.getName()+"="+value.getValue()+"&");
			}
			if( str.length() != 0){
				parameters = str.substring(0, str.length()-1);
			}
		}
		return parameters;
	}
	
	/**
	 * 
	 * 方法描述：将字符串参数，转换为Map格式
	 * 作者： yxm
	 * 创建时间： 2018年7月24日  下午10:45:30
	 * @return
	 */
	public static Map<String, String> paramStringToMap(String parameters) {
		Map<String, String> map = new HashMap<String, String>();
		if(parameters != null && !"".equals(parameters)){
			String[] ps = parameters.split("&");
			for(String p : ps){
				//考虑到参数中可能带有 = 
				int temp = p.indexOf("=");
				String pname = p.substring(0, temp);
				String pvalue = p.substring(temp+1);
				map.put(pname, pvalue);
			}
		}
		return map;
	}
	
	/**
	 * 方法描述：将Map格式参数转换为字符串格式
	 * 作者： yxm
	 * 创建时间： 2018年7月24日  下午10:52:52
	 * @param map
	 * @return
	 * @throws
	 */
	public static String paramMapToString(Map<String, String> map) {
		if (map == null || map.size() == 0) {
			return "";
		}
		String temp = "";
		for (Entry<String, String> entry : map.entrySet()) {
			temp += "&" + entry.getKey() + "=" + entry.getValue();
		}
		if (temp != null && !"".equals(temp)) {
			return temp.substring(1);
		}
		return "";
	}
	
	/**
	 * 方法描述：修改params参数的值，如不存在，则增加这个参数值
	 * 作者： yxm
	 * 创建时间： 2018年7月24日  下午10:08:20
	 * @param params 数组形式的，正确应该用参数对象，理论上还应该修改字符串格式
	 * @param map
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unused")
	private static void changeParams(List<NameValuePair> params, Map<String, String> map) {
		Map<String, String> temp = new HashMap<String, String>();
		for (int index = 0 ; index < params.size() ; index++) {
			String name = params.get(index).getName();
			if (map.containsKey(name)) {
				// 如果原有参数存在，则修改value值
				NameValuePair p = new BasicNameValuePair(name, map.get(name));
				params.set(index, p);
				
				// 同时记录已修改的，为后续不存在的直接添加
				temp.put(name, map.get(name));
			}
		}
		for (Entry<String, String> entry : map.entrySet()) {
			if (!temp.containsKey(entry.getKey())) {
				// 为未修改的key直接添加参数信息
				NameValuePair p = new BasicNameValuePair(entry.getKey(), entry.getValue());
				params.add(p);
			}
		}
	}
	
	/**
	 * 方法描述：修改params参数的值，如不存在，则增加这个参数值
	 * 作者： yxm
	 * 创建时间： 2018年7月24日  下午10:57:44
	 * @param param
	 * @param map 修改参数的key-value
	 * @throws
	 */
	public static void changeParams(WsbcParam param, Map<String, String> map) {
		if (param != null) {
			Map<String, String> mapParams = paramStringToMap(param.getParameters());
			mapParams.putAll(map);
			param.setParameters(paramMapToString(mapParams));
		}
	}
}
