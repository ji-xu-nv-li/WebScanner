package com.wsbc.selenium.ttjj.task;

import java.util.ArrayList;
import java.util.List;

import com.wsbc.database.JdbcTemplate;
import com.wsbc.entity.ttjj.Fund;
import com.wsbc.selenium.operation.Operation;
import com.wsbc.selenium.result.Result;
import com.wsbc.util.StringUtil;

public class SaveCustomizeFundData extends Operation {

	private List<Fund> funds = new ArrayList<Fund>();
	
	public List<Fund> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}
	
	@SuppressWarnings("unchecked")
	public void setFunds(String json) {
		funds = (List<Fund>) StringUtil.StringToArray(json, Fund.class);
	}

	@Override
	public Result handler() {
		if (funds != null && funds.size() > 0) {
			List<List<Object>> lists = new ArrayList<List<Object>>();
			for(Fund fund : funds) {
				List<Object> l = new ArrayList<Object>();
				l.add(fund.getDate());
				l.add(fund.getFundCode());
				l.add(fund.getFundName());
				l.add(fund.getEstimates());
				l.add(fund.getEstimatesPercent());
				l.add(fund.getUnitValue());
				l.add(fund.getNetValue());
				l.add(fund.getDayGrowth());
				l.add(fund.getDayGrowthRate());
				lists.add(l);
			}
			String sql = "REPLACE INTO `Fund` VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			JdbcTemplate.executeBatch(sql, lists);
		}
		return Result.SUCCESS;
	}

}
