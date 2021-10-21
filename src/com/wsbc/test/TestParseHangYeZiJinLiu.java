package com.wsbc.test;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wsbc.util.file.WsbcFileUtil;

public class TestParseHangYeZiJinLiu {

	public static void main(String[] args) {
		String data = WsbcFileUtil.readFile("");
		JSONArray list = JSONArray.parseArray(data.split("----------")[0]);
		StringBuilder sb = new StringBuilder();
		StringBuilder error = new StringBuilder();
		Map<String, String> columns = new LinkedHashMap<String, String>();
		columns.put("name", "名称");
		columns.put("priceLimitPercent", "今日涨跌幅");
		columns.put("mainNetInflow", "主力净流入净额");
		columns.put("largestNetInflow", "超大单净流入净额");
		columns.put("largeNetInflow", "大单净流入净额");
		columns.put("middleNetInflow", "中单净流入净额");
		columns.put("smallNetInflow", "小单净流入净额");
		columns.put("largestStock", "今日主力净流入最大股");
		for (Entry<String, String> entry : columns.entrySet()) {
			sb.append(entry.getValue() + "\t");
		}
		sb.append("\n");
		
		for (int index = 0; index < list.size(); index++) {
			JSONArray trs = list.getJSONObject(index).getJSONArray("trs");
			for (int k = 0; k < trs.size(); k++) {
				JSONObject map = trs.getJSONObject(k);
				boolean flag = false;
				for (Entry<String, String> entry : columns.entrySet()) {
					String key = entry.getKey();
					String value = map.getString(key);
					if (value == null) {
						key = entry.getValue();
						value = map.getString(key);
					}
					if (value == null && !flag) {
						error.append(map.toJSONString()+"\n");
						sb.append("\t");
						flag = true;
					} else {
						if (key.contains("流入净额") || key.contains("NetInflow")) {
							if (value.contains("亿")) {
								BigDecimal bigDecimal = BigDecimal.valueOf(Double.valueOf(value.replace("亿", "")));
								bigDecimal = bigDecimal.multiply(new BigDecimal(10000));
								value = bigDecimal.toString();
							} else {
								value = value.replace("万", "");
							}
						}
						sb.append(value + "\t");
					}
				}
				sb.append("\n");
			}
		}
		System.out.println(sb);
//		System.out.println("问题数据：" + error.toString());
	}
}
