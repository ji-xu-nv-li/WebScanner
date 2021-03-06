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
import com.wsbc.util.StringUtil;

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
	public static Operation getOperation(String operationType, Map<String, Object> params){
		Operation operation = null;
		try {
			Class<? extends Operation> clazz = null;
			if (Dictionary.OperName.CUSTOMER_OPERATION.equals(operationType)) {
				// ??????????????????????????????????????????????????????????????????
				String clazzName = (String)params.get("class");
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
				Object value = params.get(fieldName);
				if (value != null) {
					// ${value} ?????????????????????????????????????????????
					if (value.toString().matches("^\\$\\{.*\\}$")) {
						value = value.toString().replaceAll("[$|{|}]", "").trim();
						value = params.get(value);
					}
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Method method = clazz.getDeclaredMethod(setMethodName, String.class);
					if (method != null) {
						method.setAccessible(true);
						Class<?> clazzField = field.getType();
						method.invoke(operation, StringUtil.objectToString(value));
					}
				}
			}
		} catch (Exception e) {
			logger.error("??????????????????[" + operationType + "]?????????????????????", e);
			new RuntimeException("??????????????????[" + operationType + "]??????");
		}
		return operation;
	}
	
	
//	public static Operation getOperation(OperationBean ob) {
//		return getOperation(ob.getOperation(), ob.getLocation(), ob.getValue1(),
//				ob.getValue2(), ob.getValue3());
//	}

}
