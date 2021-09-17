package com.wsbc.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {

	/**
	 * 方法描述：获取泛型信息
	 * 创建时间： 2020年2月7日  下午7:51:22
	 */
	public static Class<?> getComponentType(Class<?> clazz) {
		/**
		 * getClass().getGenericSuperclass()返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type
		 * 然后将其转换ParameterizedType。
		 * getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
		 * [0]就是这个数组中第一个了。
		 * 简而言之就是获得超类的泛型参数的实际类型。
		 */
		
		Type type = clazz.getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        return (Class<?>) trueType;
	}
}
