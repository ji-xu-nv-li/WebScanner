package com.wsbc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class WsbcDateFormat {

	
	/**
	 * 方法描述：将Date转换为String 格式：2016-03-26 18:19:46
	 * 创建时间：2016年3月26日  下午6:19:46
	 * 作者：yxm
	 */
	public static String dateFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 方法描述：将String 转换为 Date 格式：2016-03-26 18:19:46
	 * 创建时间：2016年3月26日  下午6:22:46
	 * 作者：yxm
	 */
	public static Date dateParse(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d=null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 方法描述：将String 转换为 Date 格式：Sat, 26 Mar 2016 10:13:37 GMT
	 * 创建时间：2016年3月26日  下午6:26:32
	 * 作者：yxm
	 */
//	public Date StringParse(String date){
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
//		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//		Date d=null;
//		try {
//			d = sdf.parse(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return d;
//	}
	
}
