package com.wsbc.entity.ttjj;

/**
 * 基金信息
 */
public class Fund {

	/**
	 * 日期
	 */
	private String date;
	/**
	 * 基金代码
	 */
	private String fundCode;
	/**
	 * 基金名称
	 */
	private String fundName;
	/**
	 * 净值估值
	 */
	private String estimates;
	
	/**
	 * 估算涨幅 
	 */
	private String estimatesPercent;
	
	/**
	 * 单位净值
	 */
	private String unitValue;
	
	/**
	 * 累计净值
	 */
	private String netValue;
	
	/**
	 * 日增长值
	 */
	private String dayGrowth;
	
	/**
	 * 日增长率
	 */
	private String dayGrowthRate;
	
	public Fund() {}
	
	/**
	 * 获取日期
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 获取日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 获取基金代码
	 */
	public String getFundCode() {
		return fundCode;
	}
	/**
	 * 设置基金代码
	 */
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	/**
	 * 获取基金名称
	 */
	public String getFundName() {
		return fundName;
	}
	/**
	 * 设置基金名称
	 */
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	/**
	 * 获取净值估值
	 */
	public String getEstimates() {
		return estimates;
	}
	/**
	 * 设置净值估值
	 */
	public void setEstimates(String estimates) {
		this.estimates = estimates;
	}
	/**
	 * 获取单位净值
	 */
	public String getUnitValue() {
		return unitValue;
	}
	/**
	 * 设置单位净值
	 */
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
	/**
	 * 获取估值涨幅
	 */
	public String getEstimatesPercent() {
		return estimatesPercent;
	}
	/**
	 * 设置估值涨幅
	 */
	public void setEstimatesPercent(String estimatesPercent) {
		this.estimatesPercent = estimatesPercent;
	}
	/**
	 * 获取日增长值
	 */
	public String getDayGrowth() {
		return dayGrowth;
	}
	/**
	 * 设置日增长值
	 */
	public void setDayGrowth(String dayGrowth) {
		this.dayGrowth = dayGrowth;
	}
	/**
	 * 获取累计净值
	 */
	public String getNetValue() {
		return netValue;
	}
	/**
	 * 设置累计净值
	 */
	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}
	/**
	 * 获取日增长率
	 */
	public String getDayGrowthRate() {
		return dayGrowthRate;
	}
	/**
	 * 设置日增长率
	 */
	public void setDayGrowthRate(String dayGrowthRate) {
		this.dayGrowthRate = dayGrowthRate;
	}
	
}
