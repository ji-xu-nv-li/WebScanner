package com.wsbc.util.html;

import com.wsbc.httpclient.entity.WsbcForm;

public class WsbcFormUtil {
	
	public static String toStringForm(WsbcForm form) {
		return form.getAction() + "?" + form.getParam().getParameters();
	}

}
