package com.wsbc.selenium.operation.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wsbc.selenium.dic.Dictionary;
import com.wsbc.selenium.operation.ExitOperation;
import com.wsbc.selenium.operation.InputOperation;
import com.wsbc.selenium.operation.OnClickOperation;
import com.wsbc.selenium.operation.OpenSiteOperation;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.operation.SaveHtmlOperation;
import com.wsbc.selenium.operation.SeletorOperation;
import com.wsbc.selenium.operation.SwitchToOperation;
import com.wsbc.selenium.operation.WaitOperation;
import com.wsbc.selenium.operation.system.KillOperation;
import com.wsbc.selenium.operation.system.ListOperation;

public class OperationFactory {
	
	private static final Log logger = LogFactory.getLog(OperationFactory.class);
	
	private static final Map<String, Class<? extends Operation>> operations = new HashMap<String, Class<? extends Operation>>();
	
	static {
		operations.put(Dictionary.OperName.EXIT_OPERATION, ExitOperation.class);
		operations.put(Dictionary.OperName.INPUT_OPERATION, InputOperation.class);
		operations.put(Dictionary.OperName.OPEN_SITE_OPERATION, OpenSiteOperation.class);
		operations.put(Dictionary.OperName.SAVE_HTML_OPERATION, SaveHtmlOperation.class);
		operations.put(Dictionary.OperName.WAIT_OPERATION, WaitOperation.class);
		operations.put(Dictionary.OperName.SELETOR_OPERATION, SeletorOperation.class);
		operations.put(Dictionary.OperName.SWITCH_TO_OPERATION, SwitchToOperation.class);
		operations.put(Dictionary.OperName.ON_CLICK_OPERATION, OnClickOperation.class);
		operations.put(Dictionary.OperName.KILL_BROWER_OPERATION, KillOperation.class);
		operations.put(Dictionary.OperName.LIST_OPERATION, ListOperation.class);
//		operations.put(Dictionary.OperName.GET_DATA_OPERATION, ExitOperation.class);
	}
	
	public static Operation getOperation(String operation, String... values) {
		if (Dictionary.OperName.INPUT_OPERATION.equals(operation)) {
			return new InputOperation(values[0], values[1]);
		} else if (Dictionary.OperName.OPEN_SITE_OPERATION.equals(operation)) {
			return new OpenSiteOperation(values[0]);
		} else if (Dictionary.OperName.SAVE_HTML_OPERATION.equals(operation)) {
			return new SaveHtmlOperation(values[0]);
		} else if (Dictionary.OperName.WAIT_OPERATION.equals(operation)) {
			return new WaitOperation(Integer.valueOf(values[0]));
		} else if (Dictionary.OperName.EXIT_OPERATION.equals(operation)) {
			return new ExitOperation(values[0]);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Operation getOperation(String operationType, Map<String, String> params){
		Operation operation = null;
		try {
			Class<? extends Operation> clazz = null;
			if (Dictionary.OperName.CUSTOMER_OPERATION.equals(operationType)) {
				// 获取数据的对象，操作多变，有参数传递类型操作
				String clazzName = params.get("class");
				clazz = (Class<? extends Operation>) OperationFactory.class
							.getClassLoader().loadClass(clazzName);
			} else {
				clazz = operations.get(operationType);
			}
			operation = clazz.newInstance();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				String value = params.get(fieldName);
				// ${value} 为需要替换的数据，故重新获取值
				if (value != null && value.matches("^\\$\\{.*\\}$")) {
					value = value.replaceAll("[$|{|}]", "").trim();
					value = params.get(value);
				}
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Method method = clazz.getDeclaredMethod(setMethodName, String.class);
				if (method != null) {
					method.setAccessible(true);
					method.invoke(operation, value);
				}
			}
		} catch (Exception e) {
			logger.error("创建操作对象[" + operationType + "]失败，原因为：", e);
			new RuntimeException("创建操作对象[" + operationType + "]失败");
		}
		return operation;
	}
	
//	public static Operation getOperation(OperationBean ob) {
//		return getOperation(ob.getOperation(), ob.getLocation(), ob.getValue1(),
//				ob.getValue2(), ob.getValue3());
//	}

}
